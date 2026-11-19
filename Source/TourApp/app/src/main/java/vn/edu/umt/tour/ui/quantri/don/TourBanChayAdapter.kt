package vn.edu.umt.tour.ui.quantri.don

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import vn.edu.umt.tour.R

class TourBanChayAdapter(private val danhSach: List<TourBanChayMau>) :
    RecyclerView.Adapter<TourBanChayAdapter.ViewHolder>() {

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val sttTV: TextView = itemView.findViewById(R.id.sttTV)
        val tenTourTV: TextView = itemView.findViewById(R.id.tenTourTV)
        val chiTietTV: TextView = itemView.findViewById(R.id.chiTietTV)
        val doanhThuTV: TextView = itemView.findViewById(R.id.doanhThuTV)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_a11_tour_ban_chay, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val tour = danhSach[position]
        holder.sttTV.text = "${position + 1}"
        holder.tenTourTV.text = tour.ten
        holder.chiTietTV.text = "${tour.soDon} đơn · ${tour.doanhThu}"
        holder.doanhThuTV.text = tour.doanhThu
    }

    override fun getItemCount(): Int = danhSach.size
}
