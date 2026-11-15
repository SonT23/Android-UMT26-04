package vn.edu.umt.tour.ui.khach.khampha

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import vn.edu.umt.tour.R

// Adapter cho danh sách đợt khởi hành ở A03
class DotKhoiHanhAdapter(private val dot: List<DotMau>) :
    RecyclerView.Adapter<DotKhoiHanhAdapter.ViewHolder>() {

    // Fragment gán hàm này để biết người dùng bấm "Đặt" ở đợt nào
    var onDatClick: ((DotMau) -> Unit)? = null

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val ngayTV = itemView.findViewById<TextView>(R.id.ngayTV)
        val giaTV = itemView.findViewById<TextView>(R.id.giaTV)
        val nhanTV = itemView.findViewById<TextView>(R.id.nhanTV)
        val datBtn = itemView.findViewById<Button>(R.id.datBtn)

        init {
            datBtn.setOnClickListener {
                onDatClick?.invoke(dot[bindingAdapterPosition])
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val view = inflater.inflate(R.layout.item_a03_dot_khoi_hanh, parent, false)
        return ViewHolder(view)
    }

    override fun getItemCount(): Int {
        return dot.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val mot = dot[position]
        val context = holder.itemView.context
        holder.ngayTV.text = mot.ngay
        holder.giaTV.text = mot.gia

        // Ba trường hợp: hết chỗ (xám, khóa nút), còn ít (cam), còn nhiều (xanh)
        if (mot.soCho == 0) {
            holder.nhanTV.text = context.getString(R.string.a03_het_cho)
            holder.nhanTV.setBackgroundResource(R.drawable.bg_nhan_xam)
            holder.nhanTV.setTextColor(ContextCompat.getColor(context, R.color.on_surface_variant))
            holder.ngayTV.setTextColor(ContextCompat.getColor(context, R.color.outline))
            holder.datBtn.isEnabled = false
        } else if (mot.soCho <= 5) {
            holder.nhanTV.text = context.getString(R.string.a03_con_cho, mot.soCho)
            holder.nhanTV.setBackgroundResource(R.drawable.bg_nhan)
            holder.nhanTV.setTextColor(ContextCompat.getColor(context, R.color.on_tertiary_container))
            holder.ngayTV.setTextColor(ContextCompat.getColor(context, R.color.on_surface))
            holder.datBtn.isEnabled = true
        } else {
            holder.nhanTV.text = context.getString(R.string.a03_con_cho, mot.soCho)
            holder.nhanTV.setBackgroundResource(R.drawable.bg_nhan_xanh)
            holder.nhanTV.setTextColor(ContextCompat.getColor(context, R.color.on_trang_thai_tot))
            holder.ngayTV.setTextColor(ContextCompat.getColor(context, R.color.on_surface))
            holder.datBtn.isEnabled = true
        }
    }
}
