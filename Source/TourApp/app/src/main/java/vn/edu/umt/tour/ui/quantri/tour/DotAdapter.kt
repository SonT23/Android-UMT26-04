package vn.edu.umt.tour.ui.quantri.tour

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.LinearLayout
import android.widget.ProgressBar
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import vn.edu.umt.tour.R

// Adapter cho danh sách đợt khởi hành ở A15 (item_a15_dot.xml)
class DotAdapter(private val dots: List<DotMau>) :
    RecyclerView.Adapter<DotAdapter.ViewHolder>() {

    // Fragment gán ba hàm này để biết người dùng bấm nút nào của đợt nào (truyền vị trí trong danh sách)
    var onSuaClick: ((Int) -> Unit)? = null
    var onDongClick: ((Int) -> Unit)? = null
    var onHuyClick: ((Int) -> Unit)? = null

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val ngayTV = itemView.findViewById<TextView>(R.id.ngayTV)
        val trangThaiTV = itemView.findViewById<TextView>(R.id.trangThaiTV)
        val giaTV = itemView.findViewById<TextView>(R.id.giaTV)
        val choPB = itemView.findViewById<ProgressBar>(R.id.choPB)
        val soChoTV = itemView.findViewById<TextView>(R.id.soChoTV)
        val nutLL = itemView.findViewById<LinearLayout>(R.id.nutLL)
        val huyBtn = itemView.findViewById<Button>(R.id.huyBtn)

        init {
            itemView.findViewById<Button>(R.id.suaBtn).setOnClickListener {
                onSuaClick?.invoke(bindingAdapterPosition)
            }
            itemView.findViewById<Button>(R.id.dongBtn).setOnClickListener {
                onDongClick?.invoke(bindingAdapterPosition)
            }
            huyBtn.setOnClickListener {
                onHuyClick?.invoke(bindingAdapterPosition)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val view = inflater.inflate(R.layout.item_a15_dot, parent, false)
        return ViewHolder(view)
    }

    override fun getItemCount(): Int {
        return dots.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val dot = dots[position]
        val context = holder.itemView.context
        holder.ngayTV.text = dot.ngay
        holder.giaTV.text = dot.gia

        // Thanh tiến độ: tổng số chỗ là max, số chỗ đã đặt là progress
        holder.choPB.max = dot.tongCho
        holder.choPB.progress = dot.daDat

        val conLai = dot.tongCho - dot.daDat
        if (dot.daDat > 0 && conLai > 0) {
            holder.soChoTV.text = context.getString(R.string.a15_da_dat_con, dot.daDat, dot.tongCho, conLai)
        } else {
            holder.soChoTV.text = context.getString(R.string.a15_da_dat, dot.daDat, dot.tongCho)
        }

        // Nhãn và hàng nút theo trạng thái. Dòng được dùng lại khi cuộn nên gán đủ mọi trường hợp.
        when (dot.trangThai) {
            DOT_MO -> {
                holder.trangThaiTV.setText(R.string.a15_mo)
                holder.trangThaiTV.setBackgroundResource(R.drawable.bg_nhan_xanh)
                holder.trangThaiTV.setTextColor(ContextCompat.getColor(context, R.color.on_trang_thai_tot))
                holder.nutLL.visibility = View.VISIBLE
                // Chỉ hủy được đợt chưa có ai đặt
                if (dot.daDat == 0) {
                    holder.huyBtn.visibility = View.VISIBLE
                } else {
                    holder.huyBtn.visibility = View.GONE
                }
            }
            DOT_DONG -> {
                holder.trangThaiTV.setText(R.string.a15_da_dong)
                holder.trangThaiTV.setBackgroundResource(R.drawable.bg_nhan_xam)
                holder.trangThaiTV.setTextColor(ContextCompat.getColor(context, R.color.on_surface_variant))
                holder.nutLL.visibility = View.GONE
            }
            else -> {
                holder.trangThaiTV.setText(R.string.a15_da_huy)
                holder.trangThaiTV.setBackgroundResource(R.drawable.bg_nhan_do)
                holder.trangThaiTV.setTextColor(ContextCompat.getColor(context, R.color.error))
                holder.nutLL.visibility = View.GONE
            }
        }

        // Đợt đã đóng hoặc đã hủy thì làm mờ chữ ngày cho khác đợt đang mở
        if (dot.trangThai == DOT_MO) {
            holder.ngayTV.alpha = 1f
        } else {
            holder.ngayTV.alpha = 0.6f
        }
    }
}
