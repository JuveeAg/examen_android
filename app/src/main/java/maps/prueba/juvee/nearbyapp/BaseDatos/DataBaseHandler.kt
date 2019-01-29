package maps.prueba.juvee.nearbyapp.BaseDatos

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper

class DataBaseHandler (context: Context):SQLiteOpenHelper(context, DB_NAME,null, DB_VERSION) {


    override fun onCreate(db: SQLiteDatabase?) {
        val CREATE_TABLE = "CREATE TABLE $TABLE_NAME ("+
                ID+" TEXT PRIMARY KEY, "+
                NAME+" TEXT, "+
                DESC+" TEXT, "+
                IMAGE+" TEXT, "+
                URL+" TEXT);"
        db!!.execSQL(CREATE_TABLE)
    }

    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {
        val DROP_TABLE = "DROP TABLE IF EXISTS "+ TABLE_NAME
        db!!.execSQL(DROP_TABLE)
        onCreate(db)
    }


    companion object {

        private val DB_VERSION = 2
        private val DB_NAME = "personajes_marvel"
        public val TABLE_NAME = "personajes"

        public val ID = "_id"
        public val NAME = "name"
        public val DESC = "description"
        public val IMAGE = "image"
        public val URL = "url"


        private var instance: DataBaseHandler? = null
        fun getInstance(context: Context): DataBaseHandler {
            if(instance == null){
                instance = DataBaseHandler(context)
            }

            return instance!!
        }
    }
}