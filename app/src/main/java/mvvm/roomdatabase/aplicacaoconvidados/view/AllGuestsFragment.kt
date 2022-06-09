package mvvm.roomdatabase.aplicacaoconvidados.view

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import mvvm.roomdatabase.aplicacaoconvidados.R
import mvvm.roomdatabase.aplicacaoconvidados.databinding.FragmentAllGuestsBinding
import mvvm.roomdatabase.aplicacaoconvidados.service.constants.GuestConstants
import mvvm.roomdatabase.aplicacaoconvidados.view.adapter.GuestAdapter
import mvvm.roomdatabase.aplicacaoconvidados.view.listener.GuestListener
import mvvm.roomdatabase.aplicacaoconvidados.view.viewmodel.GuestsViewModel


class AllGuestsFragment: Fragment() {

    private var _binding: FragmentAllGuestsBinding? = null

    private lateinit var mGuestsViewModel: GuestsViewModel
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
       mGuestsViewModel = ViewModelProvider(this).get(GuestsViewModel::class.java)
        _binding = FragmentAllGuestsBinding.inflate(inflater, container, false)
        val root: View = binding.root

        //Recyclerview
        // 1 - Create one Recyclerview
        val allGuestsRecyclerView = root.findViewById<RecyclerView>(R.id.recyclerView_all_guests)
        // 2 - Define one Layout
        allGuestsRecyclerView.layoutManager = LinearLayoutManager(context)
        // 3 - Define one Adapter
        allGuestsRecyclerView.adapter = mAdapter

        mListener = object : GuestListener{
            override fun onClick(id: Int) {

                val intent = Intent(context, GuestFormActivity::class.java)
                val bundle = Bundle()

                bundle.putInt(GuestConstants.GUEST_ID ,id)
                intent.putExtras(bundle)
                startActivity(intent)

            }

            override fun onDelete(id: Int) {
                mGuestsViewModel.deleteGuest(id)
                mGuestsViewModel.loadRepository(GuestConstants.FILTER.EMPTY)
            }
        }

        mAdapter.attachListener(mListener!!)
        observer()

        return root
    }

    override fun onResume() {
        mGuestsViewModel.loadRepository(GuestConstants.FILTER.EMPTY)
        super.onResume()
    }

    private fun observer() {
        mGuestsViewModel.guestList.observe(viewLifecycleOwner) {
            mAdapter.updateGuest(it)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}