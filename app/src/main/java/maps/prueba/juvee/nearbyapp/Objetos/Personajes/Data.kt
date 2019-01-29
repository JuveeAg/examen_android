package maps.prueba.juvee.nearbyapp.Objetos.Personajes

import com.google.gson.annotations.SerializedName

class Data {

    @SerializedName("count")
    var count: Long? = null
    @SerializedName("limit")
    var limit: Long? = null
    @SerializedName("offset")
    var offset: Long? = null
    @SerializedName("results")
    var results: List<Result>? = null
    @SerializedName("total")
    var total: Long? = null

}
