package br.com.dictionaryapp.feature_dictionary.data.remote

import br.com.dictionaryapp.feature_dictionary.data.remote.dto.WordInfoDto
import retrofit2.http.GET
import retrofit2.http.Path

interface DictionaryApi {

    companion object{
        const val BASE_URL = "https://api.dictionaryapi.dev/"
    }

    @GET("/api/v2/entries/br/{word}")
    suspend fun getWordInfo(
        @Path("word") word: String
    ): List<WordInfoDto>
}