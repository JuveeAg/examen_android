package maps.prueba.juvee.nearbyapp.Views

import android.app.Activity
import android.content.Context
import android.view.View
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.model.Marker
import kotlinx.android.synthetic.main.custom_layout.view.*
import maps.prueba.juvee.nearbyapp.Objetos.InfoWindowData
import maps.prueba.juvee.nearbyapp.R

class CustomInfoWindow (val context: Context): GoogleMap.InfoWindowAdapter {

    override fun getInfoContents(p0: Marker?): View {
        var mInfoView = (context as Activity).layoutInflater.inflate(R.layout.custom_layout, null)
        var mInfoWindow: InfoWindowData? = p0?.tag as InfoWindowData?

        mInfoView.txtLocMarkerName.text = mInfoWindow?.mLocatioName
        mInfoView.txtLocMarkerAddress.text = mInfoWindow?.mLocationAddres
        mInfoView.txtLocMarkerEmail.text = mInfoWindow?.mLocationEmail
        mInfoView.txtLocMarkerPhone.text = mInfoWindow?.mLocationPhone
        mInfoView.txtOpenningHoursValue.text = mInfoWindow?.mLocationHours
        mInfoView.txtBranchMarkerValue.text = mInfoWindow?.mLocationBranch

        return mInfoView
    }

    override fun getInfoWindow(p0: Marker?): View? {
        return null
    }
}