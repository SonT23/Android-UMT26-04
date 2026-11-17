package vn.edu.umt.tour.ui.auth

import vn.edu.umt.tour.xemthu.ManHinh

/**
 * Các màn hình của module A (Hoàng). Làm xong màn hình nào thì thêm một dòng vào đây
 * để nó hiện trong danh sách xem thử, ví dụ:
 *     ManHinh("A06", "Đăng nhập") { DangNhapFragment() },
 */
val manHinhA: List<ManHinh> = listOf(
    ManHinh("A00", "Khởi động") { KhoiDongFragment() },
    ManHinh("A06", "Đăng nhập") { DangNhapFragment() },
    ManHinh("A07", "Đăng ký") { DangKyFragment() },
)
