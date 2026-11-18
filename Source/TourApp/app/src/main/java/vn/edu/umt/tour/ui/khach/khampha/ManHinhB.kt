package vn.edu.umt.tour.ui.khach.khampha

import vn.edu.umt.tour.xemthu.ManHinh

/**
 * Các màn hình của module B (Sơn). Làm xong màn hình nào thì thêm một dòng vào đây
 * để nó hiện trong danh sách xem thử.
 */
val manHinhB: List<ManHinh> = listOf(
    ManHinh("A01", "Trang chủ") { TrangChuFragment() },
    ManHinh("A02", "Tìm tour") { TimTourFragment() },
    ManHinh("A02", "Bộ lọc") { BoLocFragment() },
    ManHinh("A03", "Chi tiết tour") { ChiTietTourFragment() },
    ManHinh("A21", "Yêu thích và đã xem") { YeuThichFragment() },
    ManHinh("A22", "Bản đồ lịch trình") { BanDoLichTrinhFragment() },
)
