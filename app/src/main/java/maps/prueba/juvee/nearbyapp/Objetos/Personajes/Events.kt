package maps.prueba.juvee.nearbyapp.Objetos.Personajes

import com.google.gson.annotations.SerializedName

class Events {

    @SerializedName("available")
    var available: Long? = null
    @SerializedName("collectionURI")
    var collectionURI: String? = null
    @SerializedName("items")
    var items: List<Item>? = null
    @SerializedName("returned")
    var returned: Long? = null

}
