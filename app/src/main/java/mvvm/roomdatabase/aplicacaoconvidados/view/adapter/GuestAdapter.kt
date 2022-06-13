package mvvm.roomdatabase.aplicacaoconvidados.view.adapter

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import mvvm.roomdatabase.aplicacaoconvidados.databinding.RowGuestsBinding
import mvvm.roomdatabase.aplicacaoconvidados.service.model.GuestModel
import mvvm.roomdatabase.aplicacaoconvidados.view.listener.GuestListener
import mvvm.roomdatabase.aplicacaoconvidados.view.viewholder.GuestViewHolder

class GuestAdapter : RecyclerView.Adapter<GuestViewHolder>() {

    private var mGuestList: List<GuestModel> = arrayListOf()
    private lateinit var mListener: GuestListener

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): GuestViewHolder {
        val item = RowGuestsBinding.inflate(LayoutInflater.from(parent.context), parent, false )
        return GuestViewHolder(item, mListener)

    }

    override fun onBindViewHolder(holder: GuestViewHolder, position: Int) {
        holder.bind(mGuestList[position])
    }

    override fun getItemCount(): Int {
        return mGuestList.count()
    }

    @SuppressLint("NotifyDataSetChanged")
    fun updateGuest(list: List<GuestModel>){
        mGuestList = list
        notifyDataSetChanged()
    }

    fun attachListener(listener: GuestListener) {
        mListener = listener
    }
}