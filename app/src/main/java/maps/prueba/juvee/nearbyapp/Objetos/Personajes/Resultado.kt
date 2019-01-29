package maps.prueba.juvee.nearbyapp.Objetos.Personajes

import com.google.gson.annotations.SerializedName

class Resultado {

    @SerializedName("attributionHTML")
    var attributionHTML: String? = null
    @SerializedName("attributionText")
    var attributionText: String? = null
    @SerializedName("code")
    var code: Long? = null
    @SerializedName("copyright")
    var copyright: String? = null
    @SerializedName("data")
    var data: Data? = null
    @SerializedName("etag")
    var etag: String? = null
    @SerializedName("status")
    var status: String? = null

}
