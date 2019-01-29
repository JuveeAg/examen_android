package maps.prueba.juvee.nearbyapp.Utilidades

import android.graphics.Color
import android.os.AsyncTask
import android.util.Log
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.PolylineOptions
import org.json.JSONObject

class ParserTask(var map: GoogleMap) : AsyncTask<String, Int, List<List<HashMap<String, String>>>>() {

    // Parsing the data in non-ui thread
    override fun doInBackground(vararg jsonData: String): List<List<HashMap<String, String>>> {

        val jObject: JSONObject

        try {
            jObject = JSONObject(jsonData[0])
            Log.d("ParserTask", jsonData[0])
            val parser = DataParser()
            Log.d("ParserTask", parser.toString())

            // Starts parsing data
            var routes: List<List<HashMap<String, String>>>  = parser.parse(jObject)
            Log.d("ParserTask", "Executing routes")
            Log.d("ParserTask", routes.toString())
            return routes

        } catch (e: Exception) {
            Log.d("ParserTask", e.toString())
            e.printStackTrace()
        }

        val r:List<List<HashMap<String, String>>> = ArrayList<ArrayList<HashMap<String, String>>>()
        return r
    }

    // Executes in UI thread, after the parsing process
    override fun onPostExecute(result: List<List<HashMap<String, String>>>) {
        var points: ArrayList<LatLng>
        var lineOptions: PolylineOptions? = null

        // Traversing through all the routes
        for (i in result.indices) {
            points = ArrayList<LatLng>()
            lineOptions = PolylineOptions()

            // Fetching i-th route
            val path = result[i]

            // Fetching all the points in i-th route
            for (j in path.indices) {
                val point = path[j]

                val lat = java.lang.Double.parseDouble(point["lat"])
                val lng = java.lang.Double.parseDouble(point["lng"])
                val position = LatLng(lat, lng)

                points.add(position)
            }

            // Adding all the points in the route to LineOptions
            lineOptions.addAll(points)
            lineOptions.width(10f)
            lineOptions.color(Color.RED)

            Log.d("onPostExecute", "onPostExecute lineoptions decoded")

        }

        // Drawing polyline in the Google Map for the i-th route
        if (lineOptions != null) {
            map!!.addPolyline(lineOptions)
        } else {
            Log.d("onPostExecute", "without Polylines drawn")
        }
    }
}
