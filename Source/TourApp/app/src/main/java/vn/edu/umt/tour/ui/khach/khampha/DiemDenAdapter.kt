package vn.edu.umt.tour.ui.khach.khampha

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import vn.edu.umt.tour.R

// Adapter cho danh sách điểm đến cuộn ngang ở A01
class DiemDenAdapter(private val diemDen: List<String>) :
    RecyclerView.Adapter<DiemDenAdapter.ViewHolder>() {

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val tenTV = itemView.findViewById<TextView>(R.id.tenTV)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val view = inflater.inflate(R.layout.item_a01_diem_den, parent, false)
        return ViewHolder(view)
    }

    override fun getItemCount(): Int {
        return diemDen.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.tenTV.text = diemDen[position]
    }
}
