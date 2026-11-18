# Module E: Quản trị đơn, khách, thống kê (Trúc Đào)

Đọc [README.md](README.md) của thư mục này trước. File này chỉ ghi phần riêng của module E.

## Nơi làm việc

| Mục | Giá trị |
|---|---|
| Branch | `feature/layout-quan-tri-don` |
| Package Kotlin | `Source/TourApp/app/src/main/java/vn/edu/umt/tour/ui/quantri/don/` |
| File đăng ký màn hình | `ui/quantri/don/ManHinhE.kt` |
| File chuỗi | `res/values/strings_quantridon.xml` (tự tạo; bản tiếng Anh ở `res/values-en/` làm sau cũng được) |
| Ảnh Figma | [Docs/Figma/E-quan-tri-don/](../Figma/E-quan-tri-don/) |

## Các màn hình cần làm

| Mã | Ảnh Figma | Layout | Lớp Kotlin |
|---|---|---|---|
| A16 | [A16-danh-sach-don.png](../Figma/E-quan-tri-don/A16-danh-sach-don.png) | `fragment_a16_danh_sach_don.xml`, `item_a16_don.xml` | `DanhSachDonFragment`, `DonAdapter` |
| A18 | [A18-khach-hang.png](../Figma/E-quan-tri-don/A18-khach-hang.png) | `fragment_a18_khach_hang.xml`, `item_a18_khach_hang.xml` | `KhachHangFragment`, `KhachHangAdapter` |
| A17 | [A17-xu-ly-don.png](../Figma/E-quan-tri-don/A17-xu-ly-don.png) | `fragment_a17_xu_ly_don.xml` | `XuLyDonFragment` |
| A11 | [A11-tong-quan.png](../Figma/E-quan-tri-don/A11-tong-quan.png) | `fragment_a11_tong_quan.xml`, `item_a11_tour_ban_chay.xml` | `TongQuanFragment`, `TourBanChayAdapter` |
| A23 | [A23-quet-qr-checkin.png](../Figma/E-quan-tri-don/A23-quet-qr-checkin.png) | `fragment_a23_quet_qr.xml` | `QuetQrFragment` |

Nên làm theo thứ tự trong bảng: hai màn hình danh sách trước, rồi màn hình chi tiết A17, sau đó A11 và A23 là hai màn hình có phần giữ chỗ.

Tên lớp `DonAdapter` trùng với lớp của module C nhưng khác package nên không sao. Tên file layout thì không được trùng, vì vậy luôn kèm mã màn hình.

Dữ liệu mẫu đặt trong file `DuLieuMau.kt` của package mình: các lớp `DonMau`, `KhachMau`, `TourBanChayMau` và hàm tạo danh sách như trong ảnh. Xem cách viết ở `ui/khach/khampha/DuLieuMau.kt`.

## Chỉ dẫn từng màn hình

### A16 Danh sách đơn

- Tiêu đề, `ic_filter_list` bên phải.
- Ô tìm: `EditText` nền `@drawable/bg_o_tim` có `ic_search` bên trái (xem ô tìm trong `fragment_a01_trang_chu.xml`).
- Hàng chip lọc: `ChipGroup` 3 chip.
- `RecyclerView` chiếm phần còn lại. Mỗi dòng (`item_a16_don.xml`) là `MaterialCardView`: mã đơn, nhãn trạng thái ở góc phải, tên khách chữ đậm, dòng tour và ngày, số khách, tổng tiền căn phải màu `?attr/colorTertiary`.
- Nhãn trạng thái đổi nền trong `onBindViewHolder` bằng `setBackgroundResource`: "Chờ xác nhận" dùng `R.drawable.bg_nhan`, "Đã xác nhận" dùng `R.drawable.bg_nhan_xanh_duong`, "Đã hủy" dùng `R.drawable.bg_nhan_do`.
- Adapter viết theo mẫu `ui/khach/khampha/TourNoiBatAdapter.kt`.
- **Không vẽ thanh điều hướng dưới** có trong ảnh (áp dụng cho cả A11 và A18).

### A18 Khách hàng

- Tiêu đề, ô tìm, hàng chip 3 chip, `RecyclerView`.
- Mỗi dòng (`item_a18_khach_hang.xml`): ảnh đại diện là `TextView` 40dp chứa chữ cái đầu, nền `@drawable/bg_tron_nhat`, chữ căn giữa; tên; email; dòng "3 đơn"; `ic_phone` màu `?attr/colorPrimary`; `MaterialSwitch` bên phải. Dưới mỗi dòng có `View` cao 1dp làm đường kẻ.
- Khách đã khóa: công tắc tắt, dòng "0 đơn, đã khóa" đổi sang màu đỏ. Đặt trong `onBindViewHolder`, nhớ viết cả nhánh khách không bị khóa vì dòng được dùng lại khi cuộn.
- Bấm công tắc lúc này chỉ cần đổi giá trị trong dữ liệu mẫu và gọi `notifyItemChanged`.

### A17 Xử lý đơn

- Gốc là `ConstraintLayout`: hai nút "Hủy đơn" và "Xác nhận đơn" ghim sát đáy, phía trên là `ScrollView` cao `0dp`.
- Thanh trên: `ic_arrow_back`, mã đơn và thời điểm đặt (2 dòng), `ic_more_vert`.
- Nhãn trạng thái "Chờ xác nhận" (`bg_nhan`).
- Thẻ khách: `MaterialCardView` chứa ảnh đại diện chữ cái, tên, số điện thoại, email, và 2 nút tròn `ic_phone`, `ic_message` trên nền `bg_tron_nhat`.
- 5 dòng thông tin: mỗi dòng là `LinearLayout` ngang gồm nhãn bên trái (`layout_weight="1"`) và giá trị bên phải, giữa các dòng có đường kẻ 1dp. Tổng tiền màu `?attr/colorTertiary`.
- "THANH TOÁN": thẻ chứa dòng "Đã nhận 0 / 6.500.000 ₫", `ProgressBar` kiểu ngang, nút nhạt "Ghi nhận thanh toán" có `app:icon="@drawable/ic_add"`.
- Nút "Hủy đơn": nút viền, thêm `android:textColor="?attr/colorError"`. Hai nút đáy nằm ngang, mỗi nút `layout_weight="1"`.

### A11 Tổng quan

- Tiêu đề, `ic_notifications` bên phải. Phần còn lại bọc trong `ScrollView`.
- 4 ô số liệu: 2 `LinearLayout` ngang, mỗi hàng 2 ô `layout_weight="1"`. Mỗi ô là `LinearLayout` dọc gồm nhãn nhỏ và số lớn. Nền bo góc tự tạo, mỗi màu một file: `bg_a11_o_cam.xml` (`?attr/colorTertiaryContainer`), `bg_a11_o_xanh.xml` (`?attr/colorPrimaryContainer`), `bg_a11_o_xam.xml` (`?attr/colorSurfaceVariant`); chép từ `bg_anh_trong.xml` rồi đổi màu.
- Biểu đồ cột: thư viện biểu đồ (chức năng E1) làm ở tuần 9. Giờ dựng hình giữ chỗ: một `LinearLayout` ngang cao 160dp có `android:gravity="bottom"`, bên trong là 6 `View` rộng 24dp với chiều cao khác nhau, nền `?attr/colorPrimaryContainer`, cột cuối `?attr/colorPrimary`. Hàng nhãn T6 đến T11 đặt ngay bên dưới.
- "TOUR BÁN CHẠY": `RecyclerView` có `android:nestedScrollingEnabled="false"`, mỗi dòng gồm số thứ tự, tên tour, số khách căn phải.

### A23 Quét QR check-in

- Camera thật (chức năng E8) làm ở tuần 9, và theo Proposal màn hình này sẽ là `QuetQrActivity`. Giờ dựng thành Fragment để xem thử; sau này dùng lại nguyên file layout.
- Nền cả màn hình màu đen (`android:background="@color/black"`), chữ màu trắng. Thanh trên: `ic_close`, tiêu đề "Check-in đợt 20/11".
- Khung ngắm: `View` vuông 260dp ở giữa nửa trên. Nền tự tạo `bg_a23_khung.xml`: `<shape>` có `<stroke android:width="3dp" android:color="@color/white" />` và không có `<solid>`.
- Dòng "Đưa mã QR của khách vào khung" bên dưới khung.
- Khối kết quả sát đáy nền `?attr/colorSurface`: dòng "Đã đến 12/20 khách", `ProgressBar` kiểu ngang, thẻ báo check-in thành công (nền `bg_nhan_xanh`, có `ic_check`), nút viền "Nhập mã đơn thủ công".

## Đăng ký màn hình và commit

Sau mỗi màn hình, thêm một dòng vào `ui/quantri/don/ManHinhE.kt`:

```kotlin
val manHinhE: List<ManHinh> = listOf(
    ManHinh("A16", "Danh sách đơn") { DanhSachDonFragment() },
    ManHinh("A18", "Khách hàng") { KhachHangFragment() },
)
```

Commit từng màn hình, ví dụ cho A16:

```bash
git add Source/TourApp/app/src/main/res/layout/fragment_a16_danh_sach_don.xml
git add Source/TourApp/app/src/main/res/layout/item_a16_don.xml
git add Source/TourApp/app/src/main/res/values/strings_quantridon.xml
git add Source/TourApp/app/src/main/java/vn/edu/umt/tour/ui/quantri/don/
git commit -m "[+] Layout A16 Danh sách đơn"
git push -u origin feature/layout-quan-tri-don
```

Các commit tiếp theo: `[+] Layout A18 Khách hàng`, `[+] Layout A17 Xử lý đơn`, `[+] Layout A11 Tổng quan`, `[+] Layout A23 Quét QR check-in`.

## Việc làm sau, không thuộc giai đoạn này

`util/` (kiểm tra email, định dạng tiền và ngày), gọi API đơn và khách hàng, ghi nhận thanh toán, gọi và nhắn khách, biểu đồ thật, xuất CSV, quét QR bằng camera.
