package vn.edu.umt.tour.ui.khach.khampha

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import vn.edu.umt.tour.R

// Adapter cho thẻ tour nằm ngang (item_a02_tour.xml). Dùng ở A02 Tìm tour và A21 Yêu thích.
class TourAdapter(private val tours: List<TourMau>) :
    RecyclerView.Adapter<TourAdapter.ViewHolder>() {

    // Fragment gán hai hàm này để biết người dùng bấm vào thẻ hay bấm vào tim
    var onTourClick: ((TourMau) -> Unit)? = null
    var onYeuThichClick: ((Int) -> Unit)? = null

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val tenTV = itemView.findViewById<TextView>(R.id.tenTV)
        val moTaTV = itemView.findViewById<TextView>(R.id.moTaTV)
        val giaTV = itemView.findViewById<TextView>(R.id.giaTV)
        val nhanTV = itemView.findViewById<TextView>(R.id.nhanTV)
        val yeuThichIV = itemView.findViewById<ImageView>(R.id.yeuThichIV)

        init {
            itemView.setOnClickListener {
                onTourClick?.invoke(tours[bindingAdapterPosition])
            }
            yeuThichIV.setOnClickListener {
                onYeuThichClick?.invoke(bindingAdapterPosition)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val view = inflater.inflate(R.layout.item_a02_tour, parent, false)
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
        holder.giaTV.text = tour.gia
        holder.nhanTV.text = tour.conCho

        // Dòng được dùng lại khi cuộn nên phải gán đủ cả hai trường hợp
        if (tour.nhanXam) {
            holder.nhanTV.setBackgroundResource(R.drawable.bg_nhan_xam)
            holder.nhanTV.setTextColor(ContextCompat.getColor(context, R.color.on_surface_variant))
        } else {
            holder.nhanTV.setBackgroundResource(R.drawable.bg_nhan)
            holder.nhanTV.setTextColor(ContextCompat.getColor(context, R.color.on_tertiary_container))
        }

        if (tour.yeuThich) {
            holder.yeuThichIV.setImageResource(R.drawable.ic_favorite)
        } else {
            holder.yeuThichIV.setImageResource(R.drawable.ic_favorite_border)
        }
    }
}
