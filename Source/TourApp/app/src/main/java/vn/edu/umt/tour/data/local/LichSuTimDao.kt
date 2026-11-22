package vn.edu.umt.tour.data.local

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

// Các câu lệnh đọc, ghi bảng lich_su_tim. Gọi trong lifecycleScope.launch { } như YeuThichDao.
@Dao
interface LichSuTimDao {

    // Lưu một từ khóa; đã có thì ghi đè (cập nhật thời điểm tìm)
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun them(lichSuTim: LichSuTim)

    // Xóa các từ cũ, chỉ giữ 10 từ tìm gần nhất
    @Query("DELETE FROM lich_su_tim WHERE tu_khoa NOT IN (SELECT tu_khoa FROM lich_su_tim ORDER BY tim_luc DESC LIMIT 10)")
    suspend fun chiGiu10()

    // Các từ khóa đã tìm, mới nhất trước
    @Query("SELECT tu_khoa FROM lich_su_tim ORDER BY tim_luc DESC LIMIT 10")
    suspend fun layGanDay(): List<String>

    // Bấm dấu x trên một chip lịch sử
    @Query("DELETE FROM lich_su_tim WHERE tu_khoa = :tuKhoa")
    suspend fun xoa(tuKhoa: String)
}
