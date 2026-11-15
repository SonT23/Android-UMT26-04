package vn.edu.umt.tour.ui.quantri.tour

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import vn.edu.umt.tour.R

// Adapter cho danh sách tour quản trị ở A13 (item_a13_tour.xml).
// Trùng tên với TourAdapter của module B nhưng khác package nên không sao.
class TourAdapter(private val tours: List<TourMau>) :
    RecyclerView.Adapter<TourAdapter.ViewHolder>() {

    // Fragment gán hai hàm này để biết người dùng bấm vào thẻ hay bấm nút tùy chọn
    var onTourClick: ((TourMau) -> Unit)? = null
    var onTuyChonClick: ((TourMau) -> Unit)? = null

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val tenTV = itemView.findViewById<TextView>(R.id.tenTV)
        val moTaTV = itemView.findViewById<TextView>(R.id.moTaTV)
        val trangThaiTV = itemView.findViewById<TextView>(R.id.trangThaiTV)
        val noiBatTV = itemView.findViewById<TextView>(R.id.noiBatTV)
        val soDotTV = itemView.findViewById<TextView>(R.id.soDotTV)
        val tuyChonIV = itemView.findViewById<ImageView>(R.id.tuyChonIV)

        init {
            itemView.setOnClickListener {
                onTourClick?.invoke(tours[bindingAdapterPosition])
            }
            tuyChonIV.setOnClickListener {
                onTuyChonClick?.invoke(tours[bindingAdapterPosition])
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val view = inflater.inflate(R.layout.item_a13_tour, parent, false)
        return ViewHolder(view)
    }

    override fun getItemCount(): Int {
        return tours.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val tour = tours[position]
        val context = holder.itemView.context
        holder.tenTV.text = tour.ten
        holder.moTaTV.text = tour.moTa
        holder.soDotTV.text = context.getString(R.string.a13_so_dot, tour.soDot)

        // Dòng được dùng lại khi cuộn nên phải gán đủ cả hai trường hợp
        if (tour.dangHien) {
            holder.trangThaiTV.setText(R.string.a13_dang_hien)
            holder.trangThaiTV.setBackgroundResource(R.drawable.bg_nhan_xanh)
            holder.trangThaiTV.setTextColor(ContextCompat.getColor(context, R.color.on_trang_thai_tot))
        } else {
            holder.trangThaiTV.setText(R.string.a13_dang_an)
            holder.trangThaiTV.setBackgroundResource(R.drawable.bg_nhan_xam)
            holder.trangThaiTV.setTextColor(ContextCompat.getColor(context, R.color.on_surface_variant))
        }

        if (tour.noiBat) {
            holder.noiBatTV.visibility = View.VISIBLE
        } else {
            holder.noiBatTV.visibility = View.GONE
        }
    }
}
