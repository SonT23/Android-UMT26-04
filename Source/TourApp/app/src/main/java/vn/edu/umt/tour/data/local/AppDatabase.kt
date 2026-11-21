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
 * Bảng hiện có: yeu_thich (Sơn, B5).
 */
@Database(entities = [YeuThich::class], version = 1, exportSchema = false)
abstract class AppDatabase : RoomDatabase() {

    abstract fun yeuThichDao(): YeuThichDao

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
                ).build()
                INSTANCE = db
                db
            }
        }
    }
}
