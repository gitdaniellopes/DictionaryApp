package br.com.dictionaryapp.feature_dictionary.domain.repository

import br.com.dictionaryapp.core.util.Resource
import br.com.dictionaryapp.feature_dictionary.domain.model.WordInfo
import kotlinx.coroutines.flow.Flow

interface WordInfoRepository {

    fun getWordInfo(word: String): Flow<Resource<List<WordInfo>>>
}