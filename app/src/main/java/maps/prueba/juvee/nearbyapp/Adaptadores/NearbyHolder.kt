package maps.prueba.juvee.nearbyapp.Adaptadores

import android.support.v7.widget.RecyclerView
import android.view.View
import kotlinx.android.synthetic.main.item_nearby.view.*

class NearbyHolder (itemView: View) : RecyclerView.ViewHolder(itemView) {

    val txt_titulo = itemView.titulo_item
    val img = itemView.image_item
    val card  = itemView.card

}