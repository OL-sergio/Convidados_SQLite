package mvvm.roomdatabase.aplicacaoconvidados.service.repository

import android.annotation.SuppressLint
import android.content.ContentValues
import android.content.Context
import mvvm.roomdatabase.aplicacaoconvidados.service.constants.DataBaseConstants
import mvvm.roomdatabase.aplicacaoconvidados.service.model.GuestModel
import java.lang.Exception

class GuestRepository(context: Context){

    //Singleton
    private var mGuestDatabaseHelper: GuestDatabaseHelper = GuestDatabaseHelper(context)

    companion object{
        private lateinit var repository: GuestRepository

        fun getInstance(context: Context): GuestRepository{
            if (!::repository.isInitialized){
                repository = GuestRepository(context)
            }
            return repository
        }

    }

    //CRUD - Create, Read, Update, Delete
    fun saveRepository(guest : GuestModel): Boolean {
        return try {
            val db = mGuestDatabaseHelper.writableDatabase

            val contentValues = ContentValues()
            contentValues.put(DataBaseConstants.GUEST.COLUMNS.NAME, guest.name)
            contentValues.put(DataBaseConstants.GUEST.COLUMNS.PRESENCE, guest.present)
            db.insert(DataBaseConstants.GUEST.TABLE_NAME, null, contentValues)
            true
        }catch (e: Exception){
            false
        }
    }

    fun updateRepository(guest : GuestModel): Boolean{
        return try {
            val db = mGuestDatabaseHelper.writableDatabase

            val contentValues = ContentValues()
            contentValues.put(DataBaseConstants.GUEST.COLUMNS.NAME, guest.name)
            contentValues.put(DataBaseConstants.GUEST.COLUMNS.PRESENCE, guest.present)

            val selection = DataBaseConstants.GUEST.COLUMNS.ID + " = ? "
            val args = arrayOf(guest.id.toString())

            db.update(DataBaseConstants.GUEST.TABLE_NAME, contentValues,selection, args )

            true
        }catch (e: Exception){
            false
        }
    }

    fun deleteRepository(id : Int) :Boolean{
        return try {

            val db = mGuestDatabaseHelper.writableDatabase
            val selection = DataBaseConstants.GUEST.COLUMNS.ID + " = ?"
            val args = arrayOf(id.toString())

            db.delete(DataBaseConstants.GUEST.TABLE_NAME, selection , args)

            true
        }catch (e: Exception){
            false
        }
    }

    @SuppressLint("Range", "Recycle")
    fun getOneGuestRepository(id : Int) : GuestModel? {

        var guest : GuestModel? = null

        return try {
            val db = mGuestDatabaseHelper.readableDatabase

            //Simple query
            //db.rawQuery("select * from Guest where id = $id",null,"")

            val projection = arrayOf(
                DataBaseConstants.GUEST.COLUMNS.NAME,
                DataBaseConstants.GUEST.COLUMNS.PRESENCE
            )

            val selection = DataBaseConstants.GUEST.COLUMNS.ID + " = ? "
            val args = arrayOf(id.toString())

            val cursor = db.query(
                DataBaseConstants.GUEST.TABLE_NAME,
                projection,
                selection,
                args,
                null, null, null
            )

            if (cursor != null && cursor.count > 0 ){
                cursor.moveToFirst()

               val name = cursor.getString(cursor.getColumnIndex(DataBaseConstants.GUEST.COLUMNS.NAME))
               val present = (cursor.getInt(cursor.getColumnIndex(DataBaseConstants.GUEST.COLUMNS.PRESENCE)) == 1)

                guest = GuestModel(id, name, present)
            }
            cursor!!.close()

           guest
        }catch (e: Exception){
            guest
        }
    }

    @SuppressLint("Range", "Recycle")
    fun getAllGuestRepository() : List<GuestModel> {
        val list : MutableList<GuestModel> = ArrayList()

        //var guest : GuestModel? = null

        return try {
            val db = mGuestDatabaseHelper.readableDatabase


            val projection = arrayOf(
                DataBaseConstants.GUEST.COLUMNS.ID,
                DataBaseConstants.GUEST.COLUMNS.NAME,
                DataBaseConstants.GUEST.COLUMNS.PRESENCE
            )


            val cursor = db.query(DataBaseConstants.GUEST.TABLE_NAME,
                projection,
                null, null, null,
                null, null, null
            )

            if (cursor != null && cursor.count > 0 ){
                while (cursor.moveToNext()){

                    val id =  cursor.getInt(cursor.getColumnIndex(DataBaseConstants.GUEST.COLUMNS.ID))
                    val name = cursor.getString(cursor.getColumnIndex(DataBaseConstants.GUEST.COLUMNS.NAME))
                    val present = (cursor.getInt(cursor.getColumnIndex(DataBaseConstants.GUEST.COLUMNS.PRESENCE)) == 1)

                    val guest = GuestModel(id, name, present)
                    list.add(guest)
                }
            }
            cursor!!.close()

            list
        }catch (e: Exception){
            list
        }
    }

    @SuppressLint("Range", "Recycle")
    fun getPresentGuestsRepository() : List<GuestModel> {
        val list : MutableList<GuestModel> = ArrayList()
        return try {
            val db = mGuestDatabaseHelper.readableDatabase

            val cursor = db.rawQuery("SELECT id, name, presence FROM Guest WHERE presence = 1", null)

            if (cursor != null && cursor.count > 0 ){
                while (cursor.moveToNext()){

                    val id =  cursor.getInt(cursor.getColumnIndex(DataBaseConstants.GUEST.COLUMNS.ID))
                    val name = cursor.getString(cursor.getColumnIndex(DataBaseConstants.GUEST.COLUMNS.NAME))
                    val present = (cursor.getInt(cursor.getColumnIndex(DataBaseConstants.GUEST.COLUMNS.PRESENCE)) == 1)

                    val guest = GuestModel(id, name, present)
                    list.add(guest)
                }
            }
            cursor!!.close()

            list
        }catch (e: Exception){
            list
        }
    }

    @SuppressLint("Range", "Recycle")
    fun getAbsentGuestsRepository() : List<GuestModel> {
        val list : MutableList<GuestModel> = ArrayList()
        return try {
            val db = mGuestDatabaseHelper.readableDatabase

            val cursor = db.rawQuery("SELECT id, name, presence FROM Guest WHERE presence = 0", null)


            if (cursor != null && cursor.count > 0 ){
                while (cursor.moveToNext()){

                    val id =  cursor.getInt(cursor.getColumnIndex(DataBaseConstants.GUEST.COLUMNS.ID))
                    val name = cursor.getString(cursor.getColumnIndex(DataBaseConstants.GUEST.COLUMNS.NAME))
                    val present = (cursor.getInt(cursor.getColumnIndex(DataBaseConstants.GUEST.COLUMNS.PRESENCE)) == 1)

                    val guest = GuestModel(id, name, present)
                    list.add(guest)
                }
            }
            cursor!!.close()

            list
        }catch (e: Exception){
            list
        }
    }
}