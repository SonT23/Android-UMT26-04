package vn.edu.umt.tour.data.local

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

// Các câu lệnh đọc, ghi bảng yeu_thich. Hàm nào cũng là suspend:
// Room không cho đọc ghi trên luồng giao diện, nên phải gọi trong lifecycleScope.launch { }.
@Dao
interface YeuThichDao {

    // Thả tim: thêm tour; nếu đã có thì ghi đè (cập nhật thời điểm lưu)
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun them(yeuThich: YeuThich)

    // Bỏ tim
    @Query("DELETE FROM yeu_thich WHERE ma_tour = :maTour")
    suspend fun xoa(maTour: String)

    // Toàn bộ tour đã thích, mới lưu nằm trên
    @Query("SELECT * FROM yeu_thich ORDER BY luu_luc DESC")
    suspend fun layTatCa(): List<YeuThich>

    // Chỉ lấy mã, dùng để tô tim cho các danh sách tour
    @Query("SELECT ma_tour FROM yeu_thich")
    suspend fun layTatCaMa(): List<String>

    // Tour này đã được thích chưa
    @Query("SELECT EXISTS(SELECT 1 FROM yeu_thich WHERE ma_tour = :maTour)")
    suspend fun daThich(maTour: String): Boolean
}
