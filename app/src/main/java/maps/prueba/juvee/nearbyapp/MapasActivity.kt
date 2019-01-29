package maps.prueba.juvee.nearbyapp

import android.content.pm.PackageManager
import android.location.Location
import android.os.AsyncTask
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v4.app.ActivityCompat
import android.util.Log
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.BitmapDescriptorFactory
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.Marker
import com.google.android.gms.maps.model.MarkerOptions
import kotlinx.android.synthetic.main.activity_mapas.*
import maps.prueba.juvee.nearbyapp.Objetos.InfoWindowData
import maps.prueba.juvee.nearbyapp.R.id.map
import maps.prueba.juvee.nearbyapp.Utilidades.ParserTask
import maps.prueba.juvee.nearbyapp.Views.CustomInfoWindow
import java.io.*
import java.net.HttpURLConnection
import java.net.URL

class MapasActivity : AppCompatActivity(), OnMapReadyCallback,
    GoogleMap.OnMarkerClickListener  {

    private lateinit var lastLocation: Location
    private lateinit var map: GoogleMap
    private lateinit var fusedLocationClient: FusedLocationProviderClient


    private var lat = "21.8809129"
    private var lng ="-102.2774799"

    lateinit var currentLatLng : LatLng
    lateinit var estadioLatLng : LatLng

    override fun onMarkerClick(p0: Marker?) = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_mapas)

        fusedLocationClient = LocationServices.getFusedLocationProviderClient(this)

        val mapFragment = supportFragmentManager.findFragmentById(R.id.map) as SupportMapFragment
        mapFragment.getMapAsync(this)


    }


    companion object {
        private const val LOCATION_PERMISSION_REQUEST_CODE = 1
    }

    private fun setUpMap() {
        if (ActivityCompat.checkSelfPermission(this,
                android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this,
                arrayOf(android.Manifest.permission.ACCESS_FINE_LOCATION,android.Manifest.permission.ACCESS_COARSE_LOCATION), LOCATION_PERMISSION_REQUEST_CODE)
            return
        }

        map.isMyLocationEnabled = true

        fusedLocationClient.lastLocation.addOnSuccessListener(this) { location ->
            if (location != null) {
                lastLocation = location
                currentLatLng = LatLng(location.latitude, location.longitude)
                placeMarkerOnMap(currentLatLng)
                map.animateCamera(CameraUpdateFactory.newLatLngZoom(currentLatLng, 12f))
            }else{
                Log.e("data","esta mierda es null!!!!!!")
            }
        }

        floatingActionButton.setOnClickListener {
            estadioLatLng = LatLng(lat.toDouble(), lng.toDouble())
            placeMarkerOnMap(estadioLatLng)
            map.animateCamera(CameraUpdateFactory.newLatLngZoom(estadioLatLng, 12f))

            placeMarkerOnMap(estadioLatLng)

                val url = getUrl(currentLatLng, estadioLatLng)
                val FetchUrl = FetchUrl()

                FetchUrl.execute(url)

                var markers = arrayListOf<LatLng>(
                    LatLng("21.8815975".toDouble(),"-102.2767963".toDouble()),
                    LatLng("21.881714".toDouble(), "-102.275568".toDouble()),
                    LatLng("21.881530".toDouble(), "-102.276947".toDouble()),
                    LatLng("21.880818".toDouble(), "-102.276813".toDouble()),
                    LatLng("21.879957".toDouble(), "-102.276561".toDouble())
                )

                map!!.moveCamera(CameraUpdateFactory.newLatLng(estadioLatLng))
                map!!.animateCamera(CameraUpdateFactory.zoomTo(17f))



            for (mark in markers){
                val markerOptions = MarkerOptions().title("Marker Title").icon(BitmapDescriptorFactory.fromResource(R.drawable.ic_pin))

                markerOptions.position(mark)

                val info = InfoWindowData("Juvenal", "Aguado",
                    "juve.ncx@gmail.com",
                    "4425599798",
                    "9 AM to 7 PM",
                    "796358"
                )


                val customInfoWindow = CustomInfoWindow(this)

                val marker = map!!.addMarker(markerOptions)
                marker.tag = info
                map!!.setInfoWindowAdapter(customInfoWindow)


            }

        }
    }

    private fun placeMarkerOnMap(location: LatLng) {
        val markerOptions = MarkerOptions().position(location)
        map.addMarker(markerOptions)
    }

    override fun onMapReady(googleMap: GoogleMap) {
        map = googleMap

        map.uiSettings.isZoomControlsEnabled = true
        map.setOnMarkerClickListener(this)

        setUpMap()


    }

    private fun getUrl(origin: LatLng, dest: LatLng): String {
        val str_origin = "origin=" + origin.latitude + "," + origin.longitude
        val str_dest = "destination=" + dest.latitude + "," + dest.longitude
        val sensor = "sensor=false"
        val parameters = "$str_origin&$str_dest&$sensor"
        val output = "json"
        val url = "https://maps.googleapis.com/maps/api/directions/$output?$parameters&key=AIzaSyC_9nBaZJA5O65Sp9ZWt8PGRiTZ2pPYpI0"
        return url
    }

    private inner class FetchUrl : AsyncTask<String, Void, String>() {
        override fun doInBackground(vararg url: String): String {
            var data = ""
            try {
                data = downloadUrl(url[0])
            } catch (e: Exception) {
            }
            return data
        }
        override fun onPostExecute(result: String) {
            super.onPostExecute(result)
            val parserTask = ParserTask( map )
            parserTask.execute(result)
        }
    }

    @Throws(IOException::class)
    private fun downloadUrl(strUrl: String): String {
        var data = ""
        var iStream: InputStream? = null
        var urlConnection: HttpURLConnection? = null
        val sb = StringBuffer()
        val br : BufferedReader ?
        try {
            val url = URL(strUrl)
            urlConnection = url.openConnection() as HttpURLConnection
            urlConnection.connect()
            iStream = urlConnection.inputStream
             br = BufferedReader(InputStreamReader(iStream!!))

            var line = ""
            while(line!=null){
                line = br.readLine()
                sb.append(line)
            }
            data = sb.toString()
            br.close()

        } catch (e: Exception) {
            Log.d("Exception", e.toString())
            data = sb.toString()
        } finally {
            iStream!!.close()
            urlConnection!!.disconnect()
        }
        return data
    }

}
