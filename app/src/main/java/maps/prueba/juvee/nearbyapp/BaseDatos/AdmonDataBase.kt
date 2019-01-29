package maps.prueba.juvee.nearbyapp.BaseDatos

import android.content.ContentValues
import android.content.Context
import maps.prueba.juvee.nearbyapp.BaseDatos.DataBaseHandler.Companion.DESC
import maps.prueba.juvee.nearbyapp.BaseDatos.DataBaseHandler.Companion.TABLE_NAME
import maps.prueba.juvee.nearbyapp.Objetos.Personajes.Result
import maps.prueba.juvee.nearbyapp.Objetos.Personajes.Thumbnail
import maps.prueba.juvee.nearbyapp.Objetos.Personajes.Url

class AdmonDataBase (val context : Context) {

    var db : DataBaseHandler?=null


    fun inserta( result:Result) :Boolean {
        db  =DataBaseHandler.getInstance(context);

        val wdb = db!!.writableDatabase
        val values = ContentValues()
        values.put(DataBaseHandler.ID, result.id)
        values.put(DataBaseHandler.NAME, result.name)
        values.put(DataBaseHandler.DESC, result.description)
        values.put(DataBaseHandler.IMAGE, result.thumbnail!!.path+"/portrait_uncanny."+result.thumbnail!!.extension)
        values.put(DataBaseHandler.URL, result.urls!!.get(1).url)
        val _success = wdb.insert(DataBaseHandler.TABLE_NAME, null, values)
        wdb.close()
        return (Integer.parseInt("$_success") != -1)
    }

    val obtenerTodo: List<Result>
        get() {
            val resultList = ArrayList<Result>()

            db  =DataBaseHandler.getInstance(context);

            val wdb = db!!.writableDatabase

            val selectQuery = "SELECT  * FROM ${DataBaseHandler.TABLE_NAME} ORDER BY ${DataBaseHandler.NAME} ASC"
            val cursor = wdb.rawQuery(selectQuery, null)
            if (cursor != null) {
                cursor.moveToFirst()
                while (cursor.moveToNext()) {
                    val result = Result()
                    result.id =cursor.getString(cursor.getColumnIndex(DataBaseHandler.ID))
                    result.name = cursor.getString(cursor.getColumnIndex(DataBaseHandler.NAME))
                    result.description = cursor.getString(cursor.getColumnIndex(DataBaseHandler.DESC))


                    var cont =  Url()
                    cont.url =cursor.getString(cursor.getColumnIndex(DataBaseHandler.URL))
                    var urls = listOf<Url>(cont)
                    result.urls=urls

                    var thumbnail = Thumbnail()
                    thumbnail.path = cursor.getString(cursor.getColumnIndex(DataBaseHandler.IMAGE))

                    result.thumbnail=thumbnail

                    resultList.add(result)
                }
            }
            cursor.close()
            return resultList
        }


    fun getOne(_id: String): Result? {

        db  =DataBaseHandler.getInstance(context);
        val wdb = db!!.writableDatabase
        val selectQuery = "SELECT * FROM ${DataBaseHandler.TABLE_NAME} WHERE ${DataBaseHandler.ID} = ?"
        wdb.rawQuery(selectQuery, arrayOf(_id)).use { // .use requires API 16
            if (it.moveToFirst()) {
                val result = Result()
                result.id =it.getString(it.getColumnIndex(DataBaseHandler.ID))
                result.name = it.getString(it.getColumnIndex(DataBaseHandler.NAME))
                result.description = it.getString(it.getColumnIndex(DataBaseHandler.DESC))

                var cont =  Url()
                cont.url =it.getString(it.getColumnIndex(DataBaseHandler.URL))
                var urls = listOf<Url>(cont)
                result.urls=urls

                var thumbnail = Thumbnail()
                thumbnail.path = it.getString(it.getColumnIndex(DataBaseHandler.IMAGE))

                result.thumbnail=thumbnail
                return result
            }
        }
        return null
    }

}