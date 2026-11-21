package vn.edu.umt.tour.data.local

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

// Một dòng trong bảng yeu_thich: một tour người dùng đã thả tim (B5).
// Lưu kèm tên, nơi đi, giá để tab Yêu thích ở A21 xem được cả khi mất mạng.
@Entity(tableName = "yeu_thich")
data class YeuThich(
    @PrimaryKey
    @ColumnInfo(name = "ma_tour") val maTour: String,
    @ColumnInfo(name = "ten") val ten: String,
    @ColumnInfo(name = "mo_ta") val moTa: String,
    @ColumnInfo(name = "gia") val gia: String,
    // Thời điểm lưu, tính bằng mili giây (System.currentTimeMillis())
    @ColumnInfo(name = "luu_luc") val luuLuc: Long
)
