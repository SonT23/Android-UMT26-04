package vn.edu.umt.tour.ui.quantri.don

import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import vn.edu.umt.tour.R

class KhachHangAdapter(private val danhSachKhach: MutableList<KhachHangQuanTri>) :
    RecyclerView.Adapter<KhachHangAdapter.KhachViewHolder>() {

    var onKhachClick: ((KhachHangQuanTri) -> Unit)? = null

    class KhachViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val avatarTV: TextView = itemView.findViewById(R.id.avatarTV)
        val tenTV: TextView = itemView.findViewById(R.id.tenTV)
        val thongTinTV: TextView = itemView.findViewById(R.id.thongTinTV)
        val soTourTV: TextView = itemView.findViewById(R.id.soTourTV)
        val trangThaiTV: TextView = itemView.findViewById(R.id.trangThaiTV)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): KhachViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_a18_khach_hang, parent, false)
        return KhachViewHolder(view)
    }

    override fun onBindViewHolder(holder: KhachViewHolder, position: Int) {
        val khach = danhSachKhach[position]

        holder.avatarTV.text = khach.kyTuDau
        holder.tenTV.text = khach.ten
        holder.thongTinTV.text = "${khach.sdt} · ${khach.email}"

        if (khach.biBanTai) {
            holder.soTourTV.text = "0 đơn, đã khóa"
            holder.soTourTV.setTextColor(Color.parseColor("#FFBA1A1A"))
            holder.trangThaiTV.text = "Bị khóa"
            holder.trangThaiTV.setBackgroundResource(R.drawable.bg_nhan_do)
        } else {
            holder.soTourTV.text = "${khach.soTourDaDat} đơn"
            holder.soTourTV.setTextColor(Color.parseColor("#FF43474E"))
            holder.trangThaiTV.text = "Hoạt động"
            holder.trangThaiTV.setBackgroundResource(R.drawable.bg_nhan_xanh)
        }

        holder.itemView.setOnClickListener {
            onKhachClick?.invoke(khach)
        }
    }

    override fun getItemCount(): Int = danhSachKhach.size
}
