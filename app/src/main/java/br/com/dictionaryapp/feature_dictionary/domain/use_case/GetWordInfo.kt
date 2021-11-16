package br.com.dictionaryapp.feature_dictionary.domain.use_case

import br.com.dictionaryapp.core.util.Resource
import br.com.dictionaryapp.feature_dictionary.domain.model.WordInfo
import br.com.dictionaryapp.feature_dictionary.domain.repository.WordInfoRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class GetWordInfo(
    private val repository: WordInfoRepository
) {

    /**
     * funcao de operador para subistituir a funcao invoke, dessa forma, podemos chamar o caso de uso
     * como se fosse uma função (GetWordInfo() )
     */

    operator fun invoke(word: String): Flow<Resource<List<WordInfo>>>{
        if(word.isBlank()){
            return flow {  }
        }
        return repository.getWordInfo(word)
    }
}