package vn.edu.umt.tour.ui.auth

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import vn.edu.umt.tour.R

// A00 Khởi động: logo, tên ứng dụng, vòng xoay "Đang kiểm tra đăng nhập…".
// Theo Proposal màn hình này là SplashActivity; giờ dựng thành Fragment để xem thử,
// khi làm khung điều hướng thì dùng lại nguyên file layout.
class KhoiDongFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_a00_khoi_dong, container, false)
    }
}
