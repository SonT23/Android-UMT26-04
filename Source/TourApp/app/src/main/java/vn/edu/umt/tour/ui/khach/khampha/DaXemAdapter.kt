package vn.edu.umt.tour.ui.khach.khampha

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import vn.edu.umt.tour.R

// Adapter cho ô tour đã xem ở A21 (dùng cho cả dải ngang và lưới)
class DaXemAdapter(private val daXem: List<DaXemMau>) :
    RecyclerView.Adapter<DaXemAdapter.ViewHolder>() {

    var onClick: ((DaXemMau) -> Unit)? = null

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val tenTV = itemView.findViewById<TextView>(R.id.tenTV)
        val thoiGianTV = itemView.findViewById<TextView>(R.id.thoiGianTV)

        init {
            itemView.setOnClickListener {
                onClick?.invoke(daXem[bindingAdapterPosition])
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val view = inflater.inflate(R.layout.item_a21_da_xem, parent, false)
        return ViewHolder(view)
    }

    override fun getItemCount(): Int {
        return daXem.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.tenTV.text = daXem[position].ten
        holder.thoiGianTV.text = daXem[position].thoiGian
    }
}
