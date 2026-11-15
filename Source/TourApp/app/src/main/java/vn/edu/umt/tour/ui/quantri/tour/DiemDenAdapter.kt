package vn.edu.umt.tour.ui.quantri.tour

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import vn.edu.umt.tour.R

// Adapter cho danh sách điểm đến ở A12 (item_a12_diem_den.xml)
class DiemDenAdapter(private val diemDen: List<DiemDenMau>) :
    RecyclerView.Adapter<DiemDenAdapter.ViewHolder>() {

    // Fragment gán hai hàm này để biết người dùng bấm vào dòng hay bấm nút tùy chọn
    var onDiemDenClick: ((DiemDenMau) -> Unit)? = null
    var onTuyChonClick: ((DiemDenMau) -> Unit)? = null

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val tenTV = itemView.findViewById<TextView>(R.id.tenTV)
        val moTaTV = itemView.findViewById<TextView>(R.id.moTaTV)
        val tuyChonIV = itemView.findViewById<ImageView>(R.id.tuyChonIV)

        init {
            itemView.setOnClickListener {
                onDiemDenClick?.invoke(diemDen[bindingAdapterPosition])
            }
            tuyChonIV.setOnClickListener {
                onTuyChonClick?.invoke(diemDen[bindingAdapterPosition])
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val view = inflater.inflate(R.layout.item_a12_diem_den, parent, false)
        return ViewHolder(view)
    }

    override fun getItemCount(): Int {
        return diemDen.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val diem = diemDen[position]
        holder.tenTV.text = diem.ten
        // Chuỗi a12_mo_ta_dong là "%1$s, %2$d tour" nên ra "Lâm Đồng, 2 tour"
        holder.moTaTV.text = holder.itemView.context.getString(
            R.string.a12_mo_ta_dong, diem.tinh, diem.soTour
        )
    }
}
