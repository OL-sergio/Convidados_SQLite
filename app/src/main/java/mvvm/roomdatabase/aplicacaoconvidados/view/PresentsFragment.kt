package mvvm.roomdatabase.aplicacaoconvidados.view

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import mvvm.roomdatabase.aplicacaoconvidados.R
import mvvm.roomdatabase.aplicacaoconvidados.databinding.FragmentAllGuestsBinding
import mvvm.roomdatabase.aplicacaoconvidados.databinding.FragmentPresentsBinding
import mvvm.roomdatabase.aplicacaoconvidados.service.constants.GuestConstants
import mvvm.roomdatabase.aplicacaoconvidados.view.adapter.GuestAdapter
import mvvm.roomdatabase.aplicacaoconvidados.view.listener.GuestListener
import mvvm.roomdatabase.aplicacaoconvidados.view.viewmodel.GuestsViewModel


class PresentsFragment : Fragment() {

    private var _binding: FragmentPresentsBinding? = null

    private var mGuestsViewModel: GuestsViewModel? = null
    private var mListener: GuestListener? = null
    private val mAdapter: GuestAdapter = GuestAdapter()
    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        mGuestsViewModel = ViewModelProvider(this)[GuestsViewModel::class.java]
        _binding = FragmentPresentsBinding.inflate(inflater, container, false)

        binding.recyclerViewPresents.layoutManager = LinearLayoutManager(context)

        binding.recyclerViewPresents.adapter = mAdapter

        mListener = object : GuestListener{
            override fun onClick(id: Int) {

                val intent = Intent(context, GuestFormActivity::class.java)

                val bundle = Bundle()
                bundle.putInt(GuestConstants.GUEST_ID ,id)

                intent.putExtras(bundle)
                startActivity(intent)

            }

            override fun onDelete(id: Int) {
                mGuestsViewModel!!.deleteGuest(id)
                mGuestsViewModel!!.loadRepository(GuestConstants.FILTER.PRESENT)
            }
        }

        mAdapter.attachListener(mListener!!)
        observer()

        return binding.root
    }

    override fun onResume() {
        super.onResume()
        mAdapter.attachListener(mListener!!)
        mGuestsViewModel!!.loadRepository(GuestConstants.FILTER.PRESENT)
    }

    private fun observer() {
        mGuestsViewModel!!.guestList.observe(viewLifecycleOwner, Observer {
            mAdapter.updateGuest(it)
        })
    }
}