package maps.prueba.juvee.nearbyapp.Adaptadores

import android.content.Context
import android.content.Intent
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.ViewGroup
import com.bumptech.glide.Glide
import maps.prueba.juvee.nearbyapp.Objetos.Personajes.Result

import maps.prueba.juvee.nearbyapp.R
import java.lang.StringBuilder
import android.support.v4.content.ContextCompat.getSystemService
import android.net.ConnectivityManager
import maps.prueba.juvee.nearbyapp.DetalleNearby


class NearbyAdaptador (internal  var context: Context, internal  var nearbyList:List<Result>)
    :RecyclerView.Adapter<NearbyHolder>(){


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NearbyHolder {
        var itemView = LayoutInflater.from(parent.context).inflate(R.layout.item_nearby,parent,false)
        return NearbyHolder(itemView)
    }

    override fun getItemCount(): Int {
        return nearbyList.size
    }

    override fun onBindViewHolder(holder: NearbyHolder, position: Int) {

        val result: Result = nearbyList[position]

        holder.txt_titulo.text = result.name

        if(isNetworkConnected()) {
            if (result.thumbnail!!.extension != null) {
                Glide.with(holder.img.context)
                    .load(result.thumbnail!!.path + "/portrait_uncanny." + result.thumbnail!!.extension)
                    .into(holder.img)
            } else {
                Glide.with(holder.img.context).load(result.thumbnail!!.path).into(holder.img)
            }
        }else{
            holder.img.setImageResource(R.drawable.noconn)
        }

        holder.card.setOnClickListener{
            val a = Intent(context, DetalleNearby::class.java)
            a.putExtra("id",result.id)
            context.startActivity(a)

        }

    }

    private fun isNetworkConnected(): Boolean {
        val cm = context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager?
        return cm!!.activeNetworkInfo != null
    }

}