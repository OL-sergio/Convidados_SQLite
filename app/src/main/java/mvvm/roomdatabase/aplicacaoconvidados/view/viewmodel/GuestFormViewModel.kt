package mvvm.roomdatabase.aplicacaoconvidados.view.viewmodel


import android.annotation.SuppressLint
import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import mvvm.roomdatabase.aplicacaoconvidados.service.model.GuestModel
import mvvm.roomdatabase.aplicacaoconvidados.service.repository.GuestRepository

class GuestFormViewModel(application: Application) : AndroidViewModel(application){


    @SuppressLint("StaticFieldLeak")
    private val mContext = application.applicationContext
    private val mGuestRepository = GuestRepository.getInstance(mContext)

    private var mSaveGuest = MutableLiveData<Boolean>()
    val saveGuest : LiveData<Boolean> = mSaveGuest

    private var mGuest = MutableLiveData<GuestModel>()
    val guest : LiveData<GuestModel> = mGuest

    fun saveFormViewModel(id : Int, name : String, present : Boolean ){
        val guest = GuestModel( id, name, present)

        if (id == 0 ){
            mSaveGuest.value = mGuestRepository.saveRepository(guest)
        }else {
            mSaveGuest.value = mGuestRepository.updateRepository(guest)
        }
    }

    fun load(id :Int){
        mGuest.value = mGuestRepository.getOneGuestRepository(id)
    }
}

