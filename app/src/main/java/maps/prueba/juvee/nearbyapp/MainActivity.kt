package maps.prueba.juvee.nearbyapp

import android.content.Context
import android.net.ConnectivityManager
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.support.v7.widget.GridLayoutManager
import android.support.v7.widget.LinearLayoutManager
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import kotlinx.android.synthetic.main.activity_main.*
import maps.prueba.juvee.nearbyapp.Adaptadores.NearbyAdaptador
import maps.prueba.juvee.nearbyapp.BaseDatos.AdmonDataBase
import maps.prueba.juvee.nearbyapp.Client.ClientService
import maps.prueba.juvee.nearbyapp.Client.ClienteListado
import maps.prueba.juvee.nearbyapp.Objetos.Personajes.Result
import maps.prueba.juvee.nearbyapp.Objetos.Personajes.Resultado
import java.util.logging.Logger
import android.widget.Toast



class MainActivity : AppCompatActivity() {

    internal lateinit var jsonAPI:ClienteListado
    internal var compositeteDisposable : CompositeDisposable = CompositeDisposable()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val retrofit = ClientService.instance
        jsonAPI = retrofit.create(ClienteListado::class.java)

        rcv_nearby.setHasFixedSize(true)
        rcv_nearby.layoutManager = GridLayoutManager(this,2)

        val admon = AdmonDataBase(applicationContext)

        val th  = admon.obtenerTodo
        loadData()

        swipe_refresh_layout.setOnRefreshListener {
            loadData()
            swipe_refresh_layout.isRefreshing = false
        }

    }

    private fun loadData() {
        if(isNetworkConnected()) {
            fetchData()
        }else{
            val admon = AdmonDataBase(applicationContext)
            val adapter = NearbyAdaptador(this, admon.obtenerTodo)
            rcv_nearby.adapter =adapter
        }
    }


    private fun fetchData() {
        compositeteDisposable.add(jsonAPI.personajes
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe{locations->displayData(locations)}
        )
    }

    private fun isNetworkConnected(): Boolean {
        val cm = getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager?
        return cm!!.activeNetworkInfo != null
    }

    private fun displayData(locations : Resultado){

        var th = locations.data!!.results

        val admon = AdmonDataBase(applicationContext)

        if(admon.obtenerTodo.size==0) {
            val adapter = NearbyAdaptador(this, locations.data!!.results!!)
            rcv_nearby.adapter =adapter

            for (item: Result in th!!) {
                admon.inserta(item)
                Logger.getLogger(MainActivity::class.java.name).warning ("RESULT INSERT")

            }
        }else{
            val adapter = NearbyAdaptador(this, admon.obtenerTodo)
            rcv_nearby.adapter =adapter
        }

    }

}
