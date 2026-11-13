package vn.edu.umt.tour.xemthu

import androidx.fragment.app.Fragment

/**
 * Một màn hình trong danh sách xem thử.
 * ma:  mã màn hình theo Proposal, vd. "A01"
 * ten: tên màn hình, vd. "Trang chủ"
 * tao: đoạn code trong dấu { } tạo ra Fragment của màn hình, chỉ chạy khi người dùng bấm vào
 *
 * Cách dùng:  ManHinh("A01", "Trang chủ") { TrangChuFragment() }
 */
class ManHinh(val ma: String, val ten: String, val tao: () -> Fragment)
