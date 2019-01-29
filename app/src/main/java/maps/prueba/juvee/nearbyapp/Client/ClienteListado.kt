package maps.prueba.juvee.nearbyapp.Client

import io.reactivex.Observable
import maps.prueba.juvee.nearbyapp.Objetos.Personajes.Resultado
import retrofit2.http.GET

interface ClienteListado {

    @get:GET("characters?apikey=649057da02b405bee1ba7b6781e7c046&ts=1&hash=4e9f49ddb6b90f0941cef93f5ced3c53&limit=50&nameStartsWith=spider")
    val personajes:Observable<Resultado>

}