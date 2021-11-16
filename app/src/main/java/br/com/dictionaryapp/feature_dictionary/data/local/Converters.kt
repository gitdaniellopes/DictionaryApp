package br.com.dictionaryapp.feature_dictionary.data.local

import androidx.room.ProvidedTypeConverter
import androidx.room.TypeConverter
import br.com.dictionaryapp.feature_dictionary.data.util.JsonParser
import br.com.dictionaryapp.feature_dictionary.domain.model.Meaning
import com.google.gson.reflect.TypeToken

/**
 * Usamos a anotção @ProvidedTypeConverter aqui, pois por padrão, as clssses de conversores para o room,
 * não podem ter argumentos no construtor como usamos aqui no caso o jsonParser.
 * como precisamos dele, que é o nosso proprio conversor.
 * */

@ProvidedTypeConverter
class Converters(
    private val jsonParser: JsonParser
) {

    @TypeConverter
    fun fromMeaningsJson(json: String): List<Meaning> {
        return jsonParser.fromJson<ArrayList<Meaning>>(
            json,
            object : TypeToken<ArrayList<Meaning>>() {}.type
        ) ?: emptyList()
    }

    @TypeConverter
    fun toMeaningsJson(meanings: List<Meaning>): String {
        return jsonParser.toJson(
            meanings,
            object : TypeToken<ArrayList<Meaning>>() {}.type
        ) ?: "[]"
    }
}