package maps.prueba.juvee.nearbyapp.Client

import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory

object ClientService {

    private var  ourInstance : Retrofit?=null

    val instance:Retrofit
        get(){
            if(ourInstance==null){
                ourInstance=Retrofit.Builder()
                    .baseUrl("https://gateway.marvel.com/v1/public/")
                    .addConverterFactory(GsonConverterFactory.create())
                    .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                    .build()
            }
            return ourInstance!!
        }
}