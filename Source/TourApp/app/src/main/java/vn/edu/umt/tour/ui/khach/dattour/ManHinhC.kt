package vn.edu.umt.tour.ui.khach.dattour

import vn.edu.umt.tour.xemthu.ManHinh

/**
 * Các màn hình của module C (Thịnh). Làm xong màn hình nào thì thêm một dòng vào đây
 * để nó hiện trong danh sách xem thử, ví dụ:
 *     ManHinh("A04", "Đặt tour") { DatTourFragment() },
 */
val manHinhC: List<ManHinh> = listOf(
    ManHinh("A05", "Đặt thành công") { DatThanhCongFragment() },
    ManHinh("A09", "Chi tiết đơn") { ChiTietDonFragment() },
    ManHinh("A08", "Đơn của tôi") { DonCuaToiFragment() },
    ManHinh("A04", "Đặt tour") { DatTourFragment() }
)

