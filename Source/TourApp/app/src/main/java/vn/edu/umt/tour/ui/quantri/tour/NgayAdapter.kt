package vn.edu.umt.tour.ui.quantri.tour

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import vn.edu.umt.tour.R

// Adapter cho lịch trình nhiều ngày ở A14 (item_a14_ngay.xml)
class NgayAdapter(private val ngay: List<NgayMau>) :
    RecyclerView.Adapter<NgayAdapter.ViewHolder>() {

    // Fragment gán hàm này để biết người dùng bấm nút tùy chọn của ngày nào
    var onTuyChonClick: ((NgayMau) -> Unit)? = null

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val ngayTV = itemView.findViewById<TextView>(R.id.ngayTV)
        val tieuDeTV = itemView.findViewById<TextView>(R.id.tieuDeTV)

        init {
            itemView.findViewById<ImageView>(R.id.tuyChonIV).setOnClickListener {
                onTuyChonClick?.invoke(ngay[bindingAdapterPosition])
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val view = inflater.inflate(R.layout.item_a14_ngay, parent, false)
        return ViewHolder(view)
    }

    override fun getItemCount(): Int {
        return ngay.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val mot = ngay[position]
        holder.ngayTV.text = holder.itemView.context.getString(R.string.a14_ngay_so, mot.thuTu)
        holder.tieuDeTV.text = mot.tieuDe
    }
}
