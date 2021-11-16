package br.com.dictionaryapp.feature_dictionary.data.repository

import br.com.dictionaryapp.core.util.Resource
import br.com.dictionaryapp.feature_dictionary.data.local.WordInfoDao
import br.com.dictionaryapp.feature_dictionary.data.remote.DictionaryApi
import br.com.dictionaryapp.feature_dictionary.domain.model.WordInfo
import br.com.dictionaryapp.feature_dictionary.domain.repository.WordInfoRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import retrofit2.HttpException
import java.io.IOException

class WordInfoRepositoryImpl(
    private val api: DictionaryApi,
    private val dao: WordInfoDao
) : WordInfoRepository {

    override fun getWordInfo(word: String): Flow<Resource<List<WordInfo>>> = flow {
        emit(Resource.Loading())
        val wordInfos = dao.getWordInfos(word).map { it.toWordInfo() }
        emit(Resource.Loading(data = wordInfos))

        try {
            val remoteWordInfos = api.getWordInfo(word)
            dao.deleteWordsInfos(remoteWordInfos.map { it.word })
            dao.insertWordInfos(remoteWordInfos.map { it.toWordInfoEntity() })
        } catch (ex: HttpException) {
            emit(Resource.Error(
                message = "Um erro ocorreu",
                data = wordInfos
            ))

        } catch (ex: IOException) {
            emit(Resource.Error(
                message = "Erro no servidor, verifique a conexão com a internet",
                data = wordInfos
            ))
        }

        val newWordInfos = dao.getWordInfos(word).map { it.toWordInfo() }
        emit(Resource.Success(newWordInfos))
    }
}