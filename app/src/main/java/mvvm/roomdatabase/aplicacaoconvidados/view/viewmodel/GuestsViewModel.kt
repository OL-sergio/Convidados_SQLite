package mvvm.roomdatabase.aplicacaoconvidados.view.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import mvvm.roomdatabase.aplicacaoconvidados.service.constants.GuestConstants
import mvvm.roomdatabase.aplicacaoconvidados.service.model.GuestModel
import mvvm.roomdatabase.aplicacaoconvidados.service.repository.GuestRepository

class GuestsViewModel(application: Application) : AndroidViewModel(application) {

    private val mGuestRepository = GuestRepository(application.applicationContext)

    private val mGuestList = MutableLiveData<List<GuestModel>>()
    val guestList : LiveData<List<GuestModel>> = mGuestList

    fun loadRepository(filter: Int) {

        if (filter == GuestConstants.FILTER.EMPTY){
            mGuestList.value = mGuestRepository.getAllGuestRepository()
        } else if (filter == GuestConstants.FILTER.PRESENT){
            mGuestList.value = mGuestRepository.getPresentGuestsRepository()
        }else{
            mGuestList.value = mGuestRepository.getAbsentGuestsRepository()
        }
    }

    fun deleteGuest(id : Int) {
         mGuestRepository.deleteRepository(id)
    }
}