package vn.edu.umt.tour.ui.quantri.don

import vn.edu.umt.tour.xemthu.ManHinh

/**
 * Các màn hình của module E (Trúc Đào). Làm xong màn hình nào thì thêm một dòng vào đây
 * để nó hiện trong danh sách xem thử, ví dụ:
 *     ManHinh("A16", "Danh sách đơn") { DanhSachDonFragment() },
 */
val manHinhE: List<ManHinh> = listOf(
    ManHinh("A11", "Tổng quan đơn hàng") { TongQuanFragment() },
    ManHinh("A16", "Danh sách đơn đặt tour") { DanhSachDonFragment() },
    ManHinh("A17", "Xử lý đơn đặt tour") { XuLyDonFragment() },
    ManHinh("A18", "Quản lý khách hàng") { KhachHangFragment() },
    ManHinh("A23", "Quét QR Check-in") { QuetQrFragment() },
)
