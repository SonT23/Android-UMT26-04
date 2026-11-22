package vn.edu.umt.tour.data.local

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

// Bảng da_xem (B6): các tour người dùng đã mở xem chi tiết (A03), chỉ giữ 20 tour mới nhất.
// Mỗi tour một dòng: xem lại tour cũ thì ghi đè dòng đó với thời điểm mới.
@Entity(tableName = "da_xem")
data class DaXem(
    @PrimaryKey
    @ColumnInfo(name = "ma_tour") val maTour: String,
    @ColumnInfo(name = "ten") val ten: String,
    // Thời điểm xem, tính bằng mili giây (System.currentTimeMillis())
    @ColumnInfo(name = "xem_luc") val xemLuc: Long
)
