package vn.edu.umt.tour.ui.quantri.tour

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.MotionEvent
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import vn.edu.umt.tour.R

// Adapter cho lịch trình nhiều ngày ở A14 (item_a14_ngay.xml)
class NgayAdapter(private val ngay: List<NgayMau>) :
    RecyclerView.Adapter<NgayAdapter.ViewHolder>() {

    // Fragment gán các hàm này:
    // onTuyChonClick: bấm nút tùy chọn của ngày nào (nut để mở menu ngay dưới nút đó)
    // onBatDauKeo: chạm vào biểu tượng kéo của dòng nào, để ItemTouchHelper bắt đầu kéo dòng đó
    var onTuyChonClick: ((nut: View, viTri: Int) -> Unit)? = null
    var onBatDauKeo: ((RecyclerView.ViewHolder) -> Unit)? = null

    @SuppressLint("ClickableViewAccessibility") // biểu tượng kéo chỉ nhận chạm, không phải nút bấm
    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val ngayTV = itemView.findViewById<TextView>(R.id.ngayTV)
        val tieuDeTV = itemView.findViewById<TextView>(R.id.tieuDeTV)

        init {
            val tuyChonIV = itemView.findViewById<ImageView>(R.id.tuyChonIV)
            tuyChonIV.setOnClickListener {
                val viTri = bindingAdapterPosition
                if (viTri != RecyclerView.NO_POSITION) onTuyChonClick?.invoke(tuyChonIV, viTri)
            }
            itemView.findViewById<ImageView>(R.id.keoIV).setOnTouchListener { _, suKien ->
                if (suKien.actionMasked == MotionEvent.ACTION_DOWN) onBatDauKeo?.invoke(this)
                false
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
