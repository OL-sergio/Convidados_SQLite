package mvvm.roomdatabase.aplicacaoconvidados.service.constants

class DataBaseConstants private constructor(){

   class GUEST {
       object COLUMNS {
            const val ID = "id"
            const val NAME = "name"
            const val PRESENCE = "presence"
        }

       companion object {
           const val TABLE_NAME = "Guest"
       }
   }
}