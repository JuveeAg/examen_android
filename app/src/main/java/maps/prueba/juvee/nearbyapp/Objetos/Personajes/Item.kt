package maps.prueba.juvee.nearbyapp.Objetos.Personajes


import com.google.gson.annotations.SerializedName

class Item {

    @SerializedName("name")
    var name: String? = null
    @SerializedName("resourceURI")
    var resourceURI: String? = null
    @SerializedName("type")
    var type: String? = null

}
