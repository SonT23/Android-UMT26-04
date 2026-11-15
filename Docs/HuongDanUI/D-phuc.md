# Module D: Quản trị tour (Phúc)

Đọc [README.md](README.md) của thư mục này trước. File này chỉ ghi phần riêng của module D.

## Nơi làm việc

| Mục | Giá trị |
|---|---|
| Branch | `feature/layout-quan-tri-tour` |
| Package Kotlin | `Source/TourApp/app/src/main/java/vn/edu/umt/tour/ui/quantri/tour/` |
| File đăng ký màn hình | `ui/quantri/tour/ManHinhD.kt` |
| File chuỗi | `res/values/strings_quantritour.xml` (tự tạo; bản tiếng Anh ở `res/values-en/` làm sau cũng được) |
| Ảnh Figma | [Docs/Figma/D-quan-tri-tour/](../Figma/D-quan-tri-tour/) |

## Các màn hình cần làm

| Mã | Ảnh Figma | Layout | Lớp Kotlin |
|---|---|---|---|
| A12 | [A12-diem-den.png](../Figma/D-quan-tri-tour/A12-diem-den.png) | `fragment_a12_diem_den.xml`, `item_a12_diem_den.xml` | `DiemDenFragment`, `DiemDenAdapter` |
| A12 | (bảng "Sửa điểm đến" trong cùng ảnh) | `dialog_a12_sua_diem_den.xml` | `SuaDiemDenFragment` |
| A13 | [A13-danh-sach-tour.png](../Figma/D-quan-tri-tour/A13-danh-sach-tour.png) | `fragment_a13_danh_sach_tour.xml`, `item_a13_tour.xml` | `DanhSachTourFragment`, `TourAdapter` |
| A15 | [A15-dot-khoi-hanh.png](../Figma/D-quan-tri-tour/A15-dot-khoi-hanh.png) | `fragment_a15_dot_khoi_hanh.xml`, `item_a15_dot.xml` | `DotKhoiHanhFragment`, `DotAdapter` |
| A14 | [A14-them-sua-tour.png](../Figma/D-quan-tri-tour/A14-them-sua-tour.png) | `fragment_a14_them_sua_tour.xml`, `item_a14_ngay.xml` | `ThemSuaTourFragment`, `NgayAdapter` |
| A24 | [A24-chon-vi-tri.png](../Figma/D-quan-tri-tour/A24-chon-vi-tri.png) | `fragment_a24_chon_vi_tri.xml` | `ChonViTriFragment` |

Nên làm theo thứ tự trong bảng: ba màn hình danh sách trước, form dài A14 sau, A24 cuối.

Tên lớp `DiemDenAdapter` và `TourAdapter` trùng với lớp của module B nhưng khác package nên không sao. Tên file layout thì không được trùng, vì vậy luôn kèm mã màn hình.

Dữ liệu mẫu đặt trong file `DuLieuMau.kt` của package mình: các lớp `DiemDenMau`, `TourMau`, `DotMau` và hàm tạo danh sách như trong ảnh. Xem cách viết ở `ui/khach/khampha/DuLieuMau.kt`.

## Chỉ dẫn từng màn hình

### A12 Điểm đến

- Thanh trên: `ic_arrow_back`, tiêu đề, `ic_add` bên phải.
- `RecyclerView` dọc. Mỗi dòng (`item_a12_diem_den.xml`): ảnh giữ chỗ 48dp (`ImageView` nền `@drawable/bg_anh_trong`, hình `@drawable/ic_image`), tên điểm đến, dòng "Lâm Đồng, 2 tour", `ic_more_vert` bên phải. Dưới mỗi dòng có `View` cao 1dp làm đường kẻ.
- Adapter viết theo mẫu `ui/khach/khampha/DiemDenAdapter.kt`.

### A12 Sửa điểm đến

- Trong ảnh đây là bảng trượt từ dưới lên. Giai đoạn này dựng thành một màn hình riêng, đăng ký là `ManHinh("A12", "Sửa điểm đến")`.
- Tiêu đề, ô "Tên điểm đến" (`TextInputLayout`), "Tỉnh thành" (`Spinner` với `ArrayAdapter` và mảng tên tỉnh mẫu), ô "Mô tả" nhiều dòng.
- Dòng "Vị trí trên bản đồ" kèm tọa độ bên trái, nút viền "Chọn trên bản đồ" bên phải.
- Hai nút cuối căn phải: nút chữ "Hủy", nút chính "Lưu".

### A13 Danh sách tour

- Tiêu đề, `ic_add` và `ic_more_vert` bên phải.
- Ô tìm: `EditText` nền `@drawable/bg_o_tim` có `ic_search` bên trái (xem ô tìm trong `fragment_a01_trang_chu.xml`).
- Hàng chip lọc: `ChipGroup` 4 chip.
- `RecyclerView` chiếm phần còn lại. Mỗi dòng (`item_a13_tour.xml`) là `MaterialCardView`: ảnh giữ chỗ 80dp; tên; dòng mô tả; hàng nhãn; dòng "2 đợt đang mở"; `ic_more_vert` ở góc phải.
- Nhãn: "Đang hiện" nền `bg_nhan_xanh` chữ `@color/on_trang_thai_tot`; "Nổi bật" nền `bg_nhan`; "Đang ẩn" nền `bg_nhan_xam`. Nhãn "Nổi bật" ẩn hiện bằng `visibility` trong `onBindViewHolder`.
- Nút nổi "Thêm tour": `ExtendedFloatingActionButton` neo góc dưới phải, `app:icon="@drawable/ic_add"`.
- **Không vẽ thanh điều hướng dưới** có trong ảnh.

### A15 Đợt khởi hành

- Thanh trên: `ic_arrow_back`, tiêu đề hai dòng (tên màn hình và tên tour), `ic_download` bên phải.
- Hàng chip lọc 4 chip, `RecyclerView`, nút nổi "Thêm đợt".
- Mỗi dòng (`item_a15_dot.xml`) là `MaterialCardView`: ngày đi và về; nhãn "Mở" (`bg_nhan_xanh`) hoặc "Đã đóng" (`bg_nhan_xam`); dòng giá; thanh tiến độ; dòng "Đã đặt 3/4 chỗ, còn 1 chỗ"; hàng nút chữ "Sửa", "Đóng đợt", "Hủy đợt".
- Thanh tiến độ: `ProgressBar` kiểu ngang (đoạn XML ở bảng tra trong README của TourApp). Trong `onBindViewHolder` đặt `max` bằng tổng số chỗ và `progress` bằng số chỗ đã đặt.
- Nút "Hủy đợt" thêm `android:textColor="?attr/colorError"`. Đợt đã đóng thì ẩn cả hàng nút.

### A14 Thêm, sửa tour

- Thanh trên: `ic_arrow_back`, tiêu đề, chữ "Lưu" màu `?attr/colorPrimary` bên phải. Phần còn lại bọc trong `ScrollView`.
- Dải báo lưu nháp: `TextView` nền `?attr/colorTertiaryContainer`, chữ `?attr/colorOnTertiaryContainer`.
- "ẢNH": `HorizontalScrollView` chứa `LinearLayout` ngang gồm 3 ảnh giữ chỗ 72dp và ô "Thêm ảnh" (`ic_add` kèm chữ). Chụp và chọn ảnh thật (D3, D6) làm sau.
- "THÔNG TIN": ô "Tên tour"; "Điểm đến" là `Spinner`; "Số ngày" và "Số đêm" là 2 ô nhập nằm ngang, mỗi ô `layout_weight="1"`, `android:inputType="number"`.
- Hai công tắc "Nổi bật trên trang chủ" và "Hiện tour cho khách": `MaterialSwitch`.
- "LỊCH TRÌNH": `RecyclerView` có `android:nestedScrollingEnabled="false"`. Mỗi dòng (`item_a14_ngay.xml`): `ic_drag_handle`, "Ngày 1", tiêu đề ngày, `ic_more_vert`. Kéo thả đổi thứ tự (D5) làm sau.
- Nút viền "Thêm ngày" có `app:icon="@drawable/ic_add"`. Bấm thì thêm một phần tử vào danh sách và gọi `notifyItemInserted` là thấy dòng mới.

### A24 Chọn vị trí

- Bản đồ thật (Google Maps, chức năng D8) làm ở tuần 9. Giờ dùng một `View` nền `?attr/colorSurfaceVariant` chiếm cả màn hình làm hình giữ chỗ.
- Ô tìm bo tròn phía trên có `ic_arrow_back`; `ImageView` `ic_place` màu `?attr/colorPrimary` ở chính giữa; dòng gợi ý "Kéo bản đồ để đặt ghim"; nút tròn `ic_my_location` (nền `bg_tron_trang`).
- Khối thông tin sát đáy nền `?attr/colorSurface`: nhãn "Vị trí điểm đến", tên địa điểm, tọa độ, nút chính "Dùng vị trí này".

## Đăng ký màn hình và commit

Sau mỗi màn hình, thêm một dòng vào `ui/quantri/tour/ManHinhD.kt`:

```kotlin
val manHinhD: List<ManHinh> = listOf(
    ManHinh("A12", "Điểm đến") { DiemDenFragment() },
    ManHinh("A12", "Sửa điểm đến") { SuaDiemDenFragment() },
)
```

Commit từng màn hình, ví dụ cho A12:

```bash
git add Source/TourApp/app/src/main/res/layout/fragment_a12_diem_den.xml
git add Source/TourApp/app/src/main/res/layout/item_a12_diem_den.xml
git add Source/TourApp/app/src/main/res/values/strings_quantritour.xml
git add Source/TourApp/app/src/main/java/vn/edu/umt/tour/ui/quantri/tour/
git commit -m "[+] Layout A12 Điểm đến"
git push -u origin feature/layout-quan-tri-tour
```

Các commit tiếp theo: `[+] Layout A12 Sửa điểm đến`, `[+] Layout A13 Danh sách tour`, `[+] Layout A15 Đợt khởi hành`, `[+] Layout A14 Thêm, sửa tour`, `[+] Layout A24 Chọn vị trí`.

## Việc làm sau, không thuộc giai đoạn này

`data/api/` (ApiClient, ApiService), gọi API điểm đến và tour, tải ảnh, CameraX, lưu nháp bằng Room, kéo thả lịch trình, bản đồ thật.
