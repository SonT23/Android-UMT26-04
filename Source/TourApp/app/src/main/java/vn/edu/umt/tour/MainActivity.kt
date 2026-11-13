package vn.edu.umt.tour

import android.content.Intent
import android.os.Bundle
import android.widget.ArrayAdapter
import android.widget.ListView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import vn.edu.umt.tour.xemthu.DanhSachManHinh
import vn.edu.umt.tour.xemthu.XemThuActivity

// Màn hình tạm: danh sách các màn hình đã làm, bấm vào để xem thử.
// Hoàng thay màn hình khởi động bằng SplashActivity (A00) ở PR khung điều hướng.
class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        // Mỗi dòng của danh sách là "mã · tên", vd. "A01 · Trang chủ"
        val tenManHinh = arrayListOf<String>()
        for (manHinh in DanhSachManHinh.tatCa) {
            tenManHinh.add(manHinh.ma + " · " + manHinh.ten)
        }

        val manHinhLV = findViewById<ListView>(R.id.manHinhLV)
        manHinhLV.emptyView = findViewById(R.id.chuaCoTV)
        manHinhLV.adapter = ArrayAdapter(this, android.R.layout.simple_list_item_1, tenManHinh)
        manHinhLV.setOnItemClickListener { adapterView, view, i, l ->
            val intent = Intent(this, XemThuActivity::class.java)
            intent.putExtra(DanhSachManHinh.VI_TRI, i)
            startActivity(intent)
        }
    }
}
