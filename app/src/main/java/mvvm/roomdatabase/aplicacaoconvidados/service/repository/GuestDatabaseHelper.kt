package mvvm.roomdatabase.aplicacaoconvidados.service.repository

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import mvvm.roomdatabase.aplicacaoconvidados.service.constants.DataBaseConstants


class GuestDatabaseHelper(context: Context) : SQLiteOpenHelper(context,  DATABASE_NAME, null, VERSION) {
    override fun onCreate(db: SQLiteDatabase) {
       db.execSQL(CREATE_TABLE_GUEST)
    }

    override fun onUpgrade(db: SQLiteDatabase?, olVersion: Int, newVersion: Int) { }


    companion object {
        private const val VERSION = 1
        private const val DATABASE_NAME = "Convidados.db"

        private const val CREATE_TABLE_GUEST =
            ("create table "
                    + DataBaseConstants.GUEST.TABLE_NAME + " ("
                    + DataBaseConstants.GUEST.COLUMNS.ID + " integer primary key autoincrement, "
                    + DataBaseConstants.GUEST.COLUMNS.NAME + " text, "
                    + DataBaseConstants.GUEST.COLUMNS.PRESENCE + " integer);")

    }
}