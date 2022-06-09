package mvvm.roomdatabase.aplicacaoconvidados.view

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import mvvm.roomdatabase.aplicacaoconvidados.R
import mvvm.roomdatabase.aplicacaoconvidados.databinding.ActivityGuestFormBinding
import mvvm.roomdatabase.aplicacaoconvidados.service.constants.GuestConstants
import mvvm.roomdatabase.aplicacaoconvidados.view.viewmodel.GuestFormViewModel


class GuestFormActivity : AppCompatActivity(), View.OnClickListener {

    private var _binding: ActivityGuestFormBinding? = null
    private var mViewModel: GuestFormViewModel? = null
    private var mGuestID: Int = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _binding = ActivityGuestFormBinding.inflate(layoutInflater)
        setContentView(_binding!!.root)

        mViewModel = ViewModelProvider(this)[GuestFormViewModel::class.java]

        setListeners()
        observe()
        loadData()

        _binding!!.radioButtonSelectPresent.isChecked = true

    }

    private fun loadData() {
       val bundle = intent.extras
        if (bundle != null){
            mGuestID = bundle.getInt(GuestConstants.GUEST_ID)
            mViewModel!!.load(mGuestID)
        }
    }

    override fun onClick(view : View?) {
        val id = view!!.id
        if (id == R.id.button_SaveInformation){

            val name = _binding!!.editTextWriteName.text.toString()
            val present = _binding!!.radioButtonSelectPresent.isChecked

            mViewModel!!.saveFormViewModel(mGuestID, name, present)
        }
    }

    private fun observe() {
       mViewModel!!.saveGuest.observe(this, Observer {
           if (it){
            Toast.makeText(applicationContext, "Sucesso", Toast.LENGTH_SHORT).show()
           } else {
               Toast.makeText(applicationContext, "Falha", Toast.LENGTH_SHORT).show()
           }
           finish()
       })

        mViewModel!!.guest.observe(this, Observer {
          _binding!!.editTextWriteName.setText(it.name)
            if (it.present) {
               _binding!!.radioButtonSelectPresent.isChecked = true
            } else {
                _binding!!.radioButtonSelectWay.isChecked = true
            }
        })
    }

    private fun setListeners() {
        _binding!!.buttonSaveInformation.setOnClickListener(this)
    }
}