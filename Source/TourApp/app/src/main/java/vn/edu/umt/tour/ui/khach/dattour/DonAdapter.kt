package vn.edu.umt.tour.ui.khach.dattour

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import vn.edu.umt.tour.R

// Lớp dữ liệu chứa thông tin 1 đơn hàng
data class DonHang(
    val maDon: String,
    val tenTour: String,
    val ngay: String,
    val soNguoi: String,
    val tongTien: String,
    val trangThai: Int // 0: Chờ, 1: Đã xác nhận, 2: Hoàn thành, 3: Đã hủy
)

class DonAdapter(private val danhSachDon: List<DonHang>) : RecyclerView.Adapter<DonAdapter.DonViewHolder>() {

    class DonViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val tvMaDon: TextView = view.findViewById(R.id.tv_ma_don)
        val tvTrangThai: TextView = view.findViewById(R.id.tv_trang_thai)
        val tvTenTour: TextView = view.findViewById(R.id.tv_ten_tour)
        val tvNgay: TextView = view.findViewById(R.id.tv_ngay)
        val tvSoNguoi: TextView = view.findViewById(R.id.tv_so_nguoi)
        val tvTongTien: TextView = view.findViewById(R.id.tv_tong_tien)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DonViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_a08_don, parent, false)
        return DonViewHolder(view)
    }

    override fun onBindViewHolder(holder: DonViewHolder, position: Int) {
        val don = danhSachDon[position]
        holder.tvMaDon.text = don.maDon
        holder.tvTenTour.text = don.tenTour
        holder.tvNgay.text = don.ngay
        holder.tvSoNguoi.text = don.soNguoi
        holder.tvTongTien.text = don.tongTien

        // Xử lý đổi màu nhãn trạng thái theo quy định của nhóm
        when (don.trangThai) {
            0 -> {
                holder.tvTrangThai.text = "Chờ xác nhận"
                holder.tvTrangThai.setBackgroundResource(R.drawable.bg_nhan_xam)
            }
            1 -> {
                holder.tvTrangThai.text = "Đã xác nhận"
                holder.tvTrangThai.setBackgroundResource(R.drawable.bg_nhan_xanh_duong)
            }
            2 -> {
                holder.tvTrangThai.text = "Hoàn thành"
                holder.tvTrangThai.setBackgroundResource(R.drawable.bg_nhan_xanh)
            }
            3 -> {
                holder.tvTrangThai.text = "Đã hủy"
                holder.tvTrangThai.setBackgroundResource(R.drawable.bg_nhan_do)
            }
        }
    }

    override fun getItemCount() = danhSachDon.size
}