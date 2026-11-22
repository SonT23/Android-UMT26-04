package vn.edu.umt.tour.data.local

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

// Các câu lệnh đọc, ghi bảng da_xem. Gọi trong lifecycleScope.launch { } như YeuThichDao.
@Dao
interface DaXemDao {

    // Ghi lại một lần xem; tour đã có thì ghi đè (cập nhật thời điểm xem)
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun them(daXem: DaXem)

    // Xóa các tour cũ, chỉ giữ 20 tour xem gần nhất
    @Query("DELETE FROM da_xem WHERE ma_tour NOT IN (SELECT ma_tour FROM da_xem ORDER BY xem_luc DESC LIMIT 20)")
    suspend fun chiGiu20()

    // 20 tour xem gần nhất, mới xem nằm trước
    @Query("SELECT * FROM da_xem ORDER BY xem_luc DESC LIMIT 20")
    suspend fun layGanDay(): List<DaXem>

    // Nút "Xóa lịch sử" ở A21
    @Query("DELETE FROM da_xem")
    suspend fun xoaHet()
}
