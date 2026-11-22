package vn.edu.umt.tour.data.local

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

// Bảng lich_su_tim (B4): các từ khóa đã tìm ở A02, chỉ giữ 10 từ mới nhất.
// Tìm lại một từ cũ thì ghi đè dòng đó với thời điểm mới, nên từ đó lên đầu.
@Entity(tableName = "lich_su_tim")
data class LichSuTim(
    @PrimaryKey
    @ColumnInfo(name = "tu_khoa") val tuKhoa: String,
    // Thời điểm tìm, tính bằng mili giây
    @ColumnInfo(name = "tim_luc") val timLuc: Long
)
