package vn.edu.umt.tour.xemthu

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import vn.edu.umt.tour.R

// Mở một Fragment để xem thử khi chưa có khung điều hướng.
// Khi KhachActivity và QuanTriActivity xong, Fragment được gắn vào đó mà không phải sửa gì.
class XemThuActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_xem_thu)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.khung)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        // Lấy vị trí màn hình mà MainActivity gửi sang
        val viTri = intent.getIntExtra(DanhSachManHinh.VI_TRI, 0)
        val manHinh = DanhSachManHinh.tatCa[viTri]

        // savedInstanceState khác null khi Activity được tạo lại (vd. xoay máy):
        // lúc đó Fragment cũ đã được khôi phục, không gắn thêm lần nữa.
        if (savedInstanceState == null) {
            supportFragmentManager.beginTransaction()
                .replace(R.id.khung, manHinh.tao())
                .commit()
        }
    }
}
