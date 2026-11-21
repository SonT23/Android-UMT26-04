# Cấu trúc dữ liệu trên Firestore

> **Bản nháp 21/11 (Sơn soạn, Thịnh xem và sửa).** Lớp trong `model/` của Thịnh đặt tên thuộc tính **giống hệt** tên trường dưới đây, để Firestore đọc thẳng vào lớp được (`document.toObject(Tour::class.java)`). Đổi tên trường thì sửa file này trước, báo nhóm, rồi mới sửa code.

## Quy ước chung

| Loại dữ liệu | Kiểu | Ví dụ |
|---|---|---|
| Mã (id) | `String`, cũng là id của document | `"T01"`, `"DH00124"` |
| Tiền | `Long`, tính bằng đồng | `2500000` |
| Ngày giờ | `Long`, mili giây (`System.currentTimeMillis()`) | `1795132800000` |
| Tọa độ | `Double` | `viDo = 11.9404`, `kinhDo = 108.4583` |
| Trạng thái | `String` viết hoa, khai báo hằng số trong `model/` | `"CHO_XAC_NHAN"` |

- Tên collection viết thường, nối bằng `_`. Tên trường viết kiểu `camelCase`, không dấu.
- Mọi thuộc tính trong `model/` có giá trị mặc định (`val ten: String = ""`), vì Firestore tạo đối tượng rỗng rồi mới điền từng trường.
- Giá tiền hiển thị thì định dạng bằng `util/` của Trúc Đào, không lưu chuỗi `"2.500.000 ₫"` vào Firestore.

## Các collection

### `nguoi_dung` — id document = `uid` của Firebase Authentication

| Trường | Kiểu | Ghi chú |
|---|---|---|
| `hoTen` | String | |
| `email` | String | |
| `soDienThoai` | String | 10 số, bắt đầu bằng 0 |
| `vaiTro` | String | `KHACH` hoặc `QUAN_TRI`. A00, A06 đọc trường này để mở `KhachActivity` hay `QuanTriActivity` |
| `biKhoa` | Boolean | E5 khóa, mở tài khoản |
| `anhDaiDien` | String | A5, đường dẫn ảnh, để trống nếu chưa có |
| `taoLuc` | Long | |

### `diem_den` — id `"DD01"`…

| Trường | Kiểu | Ghi chú |
|---|---|---|
| `ten` | String | `"Đà Lạt"` |
| `tinhThanh` | String | `"Lâm Đồng"` |
| `moTa` | String | |
| `viDo`, `kinhDo` | Double | D8 ghi (chọn trên bản đồ ở A24) |
| `anh` | String | |

### `tour` — id `"T01"`…

| Trường | Kiểu | Ghi chú |
|---|---|---|
| `ten` | String | |
| `maDiemDen`, `tenDiemDen` | String | Lưu cả tên để danh sách không phải đọc thêm `diem_den` |
| `soNgay`, `soDem` | Long | |
| `giaTu` | Long | Giá người lớn thấp nhất trong các đợt đang mở |
| `moTa` | String | |
| `anh` | List\<String\> | Ảnh đầu tiên là ảnh đại diện |
| `noiBat` | Boolean | Hiện ở mục Tour nổi bật của A01 |
| `hienThi` | Boolean | `false` thì khách không thấy (D2 ẩn tour) |
| `diemTapTrung` | Map: `ten`, `viDo`, `kinhDo` | D8 ghi, B8 đọc |
| `lichTrinh` | List các Map, mỗi phần tử là một ngày | Xem bên dưới |

Mỗi ngày trong `lichTrinh`: `ngay` (Long, 1, 2, 3…), `tieuDe`, `moTa`, `diemDung` (List các Map: `ten`, `gio` dạng `"08:00"`, `viDo`, `kinhDo`). D5 soạn, B3 và B8 đọc.

### `dot_khoi_hanh` — id `"DT01"`…

| Trường | Kiểu | Ghi chú |
|---|---|---|
| `maTour` | String | |
| `ngayDi`, `ngayVe` | Long | |
| `giaNguoiLon`, `giaTreEm` | Long | |
| `tongCho`, `daDat` | Long | Còn chỗ = `tongCho - daDat` |
| `trangThai` | String | `MO`, `DONG`, `HUY` |

### `don_dat` — id `"DH00124"`…

| Trường | Kiểu | Ghi chú |
|---|---|---|
| `maKhach` | String | `uid` của khách |
| `tenKhach`, `soDienThoai` | String | Thông tin liên hệ nhập ở A04 |
| `maTour`, `tenTour` | String | |
| `maDot` | String | |
| `ngayDi` | Long | Chép từ đợt, để danh sách đơn không phải đọc thêm |
| `soNguoiLon`, `soTreEm` | Long | |
| `tongTien` | Long | |
| `daThanhToan` | Long | Tổng các lần ghi nhận ở E4 |
| `ghiChu` | String | |
| `trangThai` | String | `CHO_XAC_NHAN`, `DA_XAC_NHAN`, `HOAN_THANH`, `DA_HUY` |
| `datLuc` | Long | |
| `daCheckIn` | Boolean | E8 đặt thành `true` |
| `checkInLuc` | Long | `0` nếu chưa check-in |

### `thanh_toan` — id tự sinh

| Trường | Kiểu | Ghi chú |
|---|---|---|
| `maDon` | String | |
| `soTien` | Long | |
| `hinhThuc` | String | `CHUYEN_KHOAN`, `TIEN_MAT` |
| `ghiChu` | String | |
| `ghiNhanLuc` | Long | |
| `ghiNhanBoi` | String | `uid` của quản trị |

## Hai chỗ nối giữa các module (đã chốt)

- **Mã QR của đơn (C5 tạo, E8 quét):** nội dung QR chỉ là **mã đơn**, ví dụ `DH00124`, không kèm gì khác. E8 quét được chuỗi đó thì đọc `don_dat/DH00124`, kiểm tra `maDot` đúng đợt đang check-in rồi đặt `daCheckIn = true`.
- **Tọa độ (D8 ghi, B8 đọc):** trường `viDo`, `kinhDo` (kiểu Double) trong `diem_den`, trong `tour.diemTapTrung` và trong từng `diemDung` của `tour.lichTrinh`.

## Ai đọc, ai ghi

| Collection | Khách | Quản trị |
|---|---|---|
| `nguoi_dung` | Đọc, sửa document của chính mình | Đọc tất cả; sửa `biKhoa` |
| `diem_den`, `tour`, `dot_khoi_hanh` | Chỉ đọc (tour có `hienThi = true`) | Đọc, ghi |
| `don_dat` | Tạo đơn mới; đọc đơn của mình; hủy đơn đang chờ xác nhận | Đọc, ghi tất cả |
| `thanh_toan` | Không | Đọc, ghi |

Bảng này là cơ sở để viết luật bảo mật Firestore ở tuần 9. Trước đó Firestore để ở chế độ thử nghiệm (hết hạn sau 30 ngày kể từ lúc tạo).

## Dữ liệu chỉ lưu trên máy (Room, không lên Firestore)

`yeu_thich` (B5, đã có), `da_xem` (B6), `lich_su_tim` (B4), `trang_thai_don` (C8), `nhap_tour` (D7). Tất cả nằm trong `data/local/AppDatabase.kt`.
