giải # Thiết kế giao diện (Figma)

**File Figma của nhóm:** _(dán link xem của file Figma vào đây)_

Cả nhóm vẽ trong một file Figma chung. Mỗi người vẽ màn hình của module mình, xuất ảnh PNG vào đúng thư mục bên dưới rồi **tự commit bằng tài khoản của mình**. Lịch sử commit là bằng chứng phần thiết kế do ai làm. Cách commit: mục 7 của [CONTRIBUTING.md](../../CONTRIBUTING.md).

| Thư mục | Module | Người làm | Màn hình (theo Proposal) |
|---|---|---|---|
| `chung/` | Bộ màu, kiểu chữ, component dùng chung | Sơn | — |
| `A-tai-khoan/` | A: Tài khoản, phiên, cài đặt | Hoàng | A00 Khởi động, A06 Đăng nhập, A07 Đăng ký, A10 Tài khoản, A19 Cài đặt, A20 Quên mật khẩu |
| `B-kham-pha/` | B: Khám phá tour | Sơn | A01 Trang chủ, A02 Tìm tour, A03 Chi tiết tour, A21 Yêu thích và đã xem, A22 Bản đồ lịch trình |
| `C-dat-tour/` | C: Đặt tour, đơn của khách | Thịnh | A04 Đặt tour, A05 Đặt thành công, A08 Đơn của tôi, A09 Chi tiết đơn |
| `D-quan-tri-tour/` | D: Quản trị tour | Phúc | A12 Điểm đến, A13 Danh sách tour, A14 Thêm, sửa tour, A15 Đợt khởi hành, A24 Chọn vị trí |
| `E-quan-tri-don/` | E: Quản trị đơn, khách, thống kê | Trúc Đào | A11 Tổng quan, A16 Danh sách đơn, A17 Xử lý đơn, A18 Khách hàng, A23 Quét QR check-in |

## Đặt tên

- Frame trong Figma đặt tên đúng bằng tên file sẽ xuất: mã màn hình + tên không dấu, nối bằng `-`, vd. `A01-trang-chu`. Figma lấy tên frame làm tên file nên ảnh xuất ra sẽ là `A01-trang-chu.png`.
- Màn hình có nhiều trạng thái thì thêm hậu tố: `A02-tim-tour-bo-loc`, `A03-chi-tiet-tour-het-cho`.
- Kích thước frame: preset **Android Compact (412 × 917)**, gần với máy ảo `Medium_Phone` nhóm đang dùng.

## Quy tắc trong file Figma

- Chỉ sửa frame trong phần của module mình. Muốn góp ý màn hình của người khác thì để comment (phím `C`), không sửa trực tiếp.
- Màu và kiểu chữ chỉ lấy từ style chung, giống bộ màu trong `Source/TourApp/app/src/main/res/values/colors.xml`.
- Thêm hoặc sửa component dùng chung thì báo nhóm trước.

## Nguồn tham khảo

Mọi UI kit, icon, ảnh lấy từ bên ngoài đều phải ghi vào đây và vào báo cáo. Template chấm của thầy cho 0 điểm nếu dùng thứ không phải của mình mà không ghi rõ.

| Tài nguyên | Tác giả, link | Dùng cho |
|---|---|---|
| _(vd. tên UI kit)_ | _(tác giả, link Figma Community)_ | _(màn hình hoặc component nào)_ |
