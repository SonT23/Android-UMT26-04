package vn.edu.umt.tour.ui.quantri.tour

import vn.edu.umt.tour.xemthu.ManHinh

/**
 * Các màn hình của module D (Phúc). Làm xong màn hình nào thì thêm một dòng vào đây
 * để nó hiện trong danh sách xem thử.
 */
val manHinhD: List<ManHinh> = listOf(
    ManHinh("A12", "Điểm đến") { DiemDenFragment() },
    ManHinh("A12", "Sửa điểm đến") { SuaDiemDenFragment() },
    ManHinh("A13", "Danh sách tour") { DanhSachTourFragment() },
    ManHinh("A15", "Đợt khởi hành") { DotKhoiHanhFragment() },
)
