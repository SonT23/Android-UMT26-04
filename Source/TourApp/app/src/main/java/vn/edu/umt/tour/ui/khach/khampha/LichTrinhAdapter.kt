package vn.edu.umt.tour.ui.khach.khampha

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import vn.edu.umt.tour.R

// Adapter cho lịch trình từng ngày ở A03. Bấm vào một ngày để mở hoặc đóng phần mô tả.
class LichTrinhAdapter(private val ngay: List<LichTrinhMau>) :
    RecyclerView.Adapter<LichTrinhAdapter.ViewHolder>() {

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val ngayTV = itemView.findViewById<TextView>(R.id.ngayTV)
        val tieuDeTV = itemView.findViewById<TextView>(R.id.tieuDeTV)
        val moTaTV = itemView.findViewById<TextView>(R.id.moTaTV)
        val muiTenIV = itemView.findViewById<ImageView>(R.id.muiTenIV)

        init {
            itemView.setOnClickListener {
                val viTri = bindingAdapterPosition
                ngay[viTri].moRong = !ngay[viTri].moRong
                notifyItemChanged(viTri)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val view = inflater.inflate(R.layout.item_a03_lich_trinh, parent, false)
        return ViewHolder(view)
    }

    override fun getItemCount(): Int {
        return ngay.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val mot = ngay[position]
        holder.ngayTV.text = mot.ngay
        holder.tieuDeTV.text = mot.tieuDe
        holder.moTaTV.text = mot.moTa
        if (mot.moRong) {
            holder.moTaTV.visibility = View.VISIBLE
            holder.muiTenIV.rotation = 270f
        } else {
            holder.moTaTV.visibility = View.GONE
            holder.muiTenIV.rotation = 90f
        }
    }
}
