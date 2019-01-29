package maps.prueba.juvee.nearbyapp

import android.content.Context
import android.net.ConnectivityManager
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.bumptech.glide.Glide
import kotlinx.android.synthetic.main.activity_detalle_nearby.*
import maps.prueba.juvee.nearbyapp.Adaptadores.NearbyAdaptador
import maps.prueba.juvee.nearbyapp.BaseDatos.AdmonDataBase
import android.content.Intent
import android.net.Uri


class DetalleNearby : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detalle_nearby)

        val id = intent.extras.getString("id")

        val admon = AdmonDataBase(applicationContext)

        val result = admon.getOne(id)

        titulo.text = result!!.name
        descripcion.text =  result!!.description


        if(isNetworkConnected()) {
            var url =""
            if (result!!.thumbnail!!.extension != null) {
                 url = result!!.thumbnail!!.path + "/portrait_uncanny." + result!!.thumbnail!!.extension+
                Glide.with(image.context)
                    .load(url)
                    .into(image)
            } else {
                url = result!!.thumbnail!!.path!!
                Glide.with(applicationContext).load(url).into(image)
            }

            image.setOnClickListener{
                val intent = Intent()
                intent.action = android.content.Intent.ACTION_VIEW
                intent.addCategory(android.content.Intent.CATEGORY_DEFAULT)
                intent.setDataAndType(Uri.parse(url), "image/*")
                startActivity(intent)
            }

        }else{
            image.setImageResource(R.drawable.noconn)
        }

        floatingActionButton.setOnClickListener {
            val intent = Intent()
            intent.action = Intent.ACTION_SEND
            intent.type = "text/plain"
            intent.putExtra("android.intent.extra.TEXT", result!!.urls!![0].url)
            startActivity(Intent.createChooser(intent, getString(R.string.compartir)));
        }
    }
    private fun isNetworkConnected(): Boolean {
        val cm = getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager?
        return cm!!.activeNetworkInfo != null
    }
}
