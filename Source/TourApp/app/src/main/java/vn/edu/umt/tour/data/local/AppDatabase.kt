package vn.edu.umt.tour.data.local

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

/**
 * Cơ sở dữ liệu Room dùng chung cả app, lưu trên máy (file tour_app.db).
 * Ai cần thêm bảng: thêm lớp @Entity vào danh sách entities, thêm hàm lấy DAO bên dưới,
 * tăng version lên 1 và báo nhóm (đây là file dùng chung).
 *
 * Khi version tăng, fallbackToDestructiveMigration xóa các bảng cũ rồi tạo lại,
 * nên dữ liệu Room trên máy (yêu thích, đã xem…) mất một lần. Chấp nhận được khi đang làm đồ án.
 *
 * Bảng hiện có: yeu_thich (Sơn, B5), da_xem (Sơn, B6), lich_su_tim (Sơn, B4).
 */
@Database(
    entities = [YeuThich::class, DaXem::class, LichSuTim::class],
    version = 3,
    exportSchema = false
)
abstract class AppDatabase : RoomDatabase() {

    abstract fun yeuThichDao(): YeuThichDao

    abstract fun daXemDao(): DaXemDao

    abstract fun lichSuTimDao(): LichSuTimDao

    companion object {
        // Chỉ tạo một database cho cả app; @Volatile để các luồng đều thấy giá trị mới nhất
        @Volatile
        private var INSTANCE: AppDatabase? = null

        fun layDatabase(context: Context): AppDatabase {
            return INSTANCE ?: synchronized(this) {
                val db = Room.databaseBuilder(
                    context.applicationContext,
                    AppDatabase::class.java,
                    "tour_app.db"
                ).fallbackToDestructiveMigration(true).build()
                INSTANCE = db
                db
            }
        }
    }
}
