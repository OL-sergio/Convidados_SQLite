package mvvm.roomdatabase.aplicacaoconvidados.view.viewholder

import android.app.AlertDialog
import androidx.recyclerview.widget.RecyclerView
import mvvm.roomdatabase.aplicacaoconvidados.R
import mvvm.roomdatabase.aplicacaoconvidados.databinding.RowGuestsBinding
import mvvm.roomdatabase.aplicacaoconvidados.service.model.GuestModel
import mvvm.roomdatabase.aplicacaoconvidados.view.listener.GuestListener


class GuestViewHolder(private var _binding: RowGuestsBinding, private var listener: GuestListener)
    : RecyclerView.ViewHolder(_binding.root) {


    fun bind(guestModel: GuestModel) {


        _binding.textViewGuestRow.text = guestModel.name

            _binding.textViewGuestRow.setOnClickListener {
            listener.onClick(guestModel.id)
        }
                _binding.textViewGuestRow.setOnLongClickListener {
            AlertDialog.Builder(itemView.context)
                .setTitle(R.string.remocao_convidado)
                .setMessage(R.string.deseja_remover)
                .setPositiveButton(R.string.remover){ dialog, which ->
                    listener.onDelete(guestModel.id)

                }
                .setNegativeButton(R.string.cancelar,null)
                .show()

            true
        }
    }
}