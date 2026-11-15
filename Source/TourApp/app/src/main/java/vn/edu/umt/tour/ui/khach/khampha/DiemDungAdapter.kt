package vn.edu.umt.tour.ui.khach.khampha

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import vn.edu.umt.tour.R

// Adapter cho danh sách điểm dừng ở A22. Số thứ tự lấy theo vị trí trong danh sách.
class DiemDungAdapter(private val diemDung: List<DiemDungMau>) :
    RecyclerView.Adapter<DiemDungAdapter.ViewHolder>() {

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val sttTV = itemView.findViewById<TextView>(R.id.sttTV)
        val tenTV = itemView.findViewById<TextView>(R.id.tenTV)
        val moTaTV = itemView.findViewById<TextView>(R.id.moTaTV)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val view = inflater.inflate(R.layout.item_a22_diem_dung, parent, false)
        return ViewHolder(view)
    }

    override fun getItemCount(): Int {
        return diemDung.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.sttTV.text = (position + 1).toString()
        holder.tenTV.text = diemDung[position].ten
        holder.moTaTV.text = diemDung[position].moTa
    }
}
