package vn.edu.umt.tour.ui.quantri.don.uie

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import vn.edu.umt.tour.R

class DonAdapter(private val danhSachDon: List<DonDatTour>) :
    RecyclerView.Adapter<DonAdapter.DonViewHolder>() {

    var onDonClick: ((DonDatTour) -> Unit)? = null

    class DonViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val maDonTV: TextView = itemView.findViewById(R.id.maDonTV)
        val trangThaiTV: TextView = itemView.findViewById(R.id.trangThaiTV)
        val tenKhachTV: TextView = itemView.findViewById(R.id.tenKhachTV)
        val tenTourTV: TextView = itemView.findViewById(R.id.tenTourTV)
        val tongTienTV: TextView = itemView.findViewById(R.id.tongTienTV)
        val soKhachTV: TextView = itemView.findViewById(R.id.soKhachTV)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DonViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_e_don, parent, false)
        return DonViewHolder(view)
    }

    override fun onBindViewHolder(holder: DonViewHolder, position: Int) {
        val don = danhSachDon[position]
        holder.maDonTV.text = don.maDon
        holder.trangThaiTV.text = don.trangThai
        holder.tenKhachTV.text = "${don.tenKhachHang} · ${don.sdt}"
        holder.tenTourTV.text = "${don.tenTour} · ${don.ngayKhoiHanh}"
        holder.tongTienTV.text = don.tongTien
        holder.soKhachTV.text = "${don.soKhach} khách"

        val bgRes = when (don.trangThai) {
            "Đã xác nhận", "Hoàn thành" -> R.drawable.bg_nhan_xanh_duong
            "Đã hủy" -> R.drawable.bg_nhan_do
            else -> R.drawable.bg_nhan
        }
        holder.trangThaiTV.setBackgroundResource(bgRes)

        holder.itemView.setOnClickListener {
            onDonClick?.invoke(don)
        }
    }

    override fun getItemCount(): Int = danhSachDon.size
}
