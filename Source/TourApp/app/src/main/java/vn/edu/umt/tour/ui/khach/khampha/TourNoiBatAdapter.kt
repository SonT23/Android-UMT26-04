package vn.edu.umt.tour.ui.khach.khampha

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import vn.edu.umt.tour.R

// Adapter cho danh sách tour nổi bật ở A01
class TourNoiBatAdapter(private val tours: List<TourMau>) :
    RecyclerView.Adapter<TourNoiBatAdapter.ViewHolder>() {

    // Fragment gán hai hàm này để biết người dùng bấm vào thẻ hay bấm vào tim
    var onTourClick: ((TourMau) -> Unit)? = null
    var onYeuThichClick: ((Int) -> Unit)? = null

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val tenTV = itemView.findViewById<TextView>(R.id.tenTV)
        val moTaTV = itemView.findViewById<TextView>(R.id.moTaTV)
        val giaTV = itemView.findViewById<TextView>(R.id.giaTV)
        val conChoTV = itemView.findViewById<TextView>(R.id.conChoTV)
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
        val view = inflater.inflate(R.layout.item_a01_tour_noi_bat, parent, false)
        return ViewHolder(view)
    }

    override fun getItemCount(): Int {
        return tours.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val tour = tours[position]
        holder.tenTV.text = tour.ten
        holder.moTaTV.text = tour.moTa
        holder.giaTV.text = tour.gia
        holder.conChoTV.text = tour.conCho
        if (tour.yeuThich) {
            holder.yeuThichIV.setImageResource(R.drawable.ic_favorite)
        } else {
            holder.yeuThichIV.setImageResource(R.drawable.ic_favorite_border)
        }
    }
}
