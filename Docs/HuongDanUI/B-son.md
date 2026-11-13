# Module B: Khám phá tour (Sơn)

Đọc [README.md](README.md) của thư mục này trước. File này chỉ ghi phần riêng của module B.

## Nơi làm việc

| Mục | Giá trị |
|---|---|
| Branch | `feature/layout-kham-pha` |
| Package Kotlin | `Source/TourApp/app/src/main/java/vn/edu/umt/tour/ui/khach/khampha/` |
| File đăng ký màn hình | `ui/khach/khampha/ManHinhB.kt` |
| File chuỗi | `res/values/strings_khampha.xml` và `res/values-en/strings_khampha.xml` (đã có) |
| Ảnh Figma | [Docs/Figma/B-kham-pha/](../Figma/B-kham-pha/) |

## Các màn hình cần làm

| Mã | Ảnh Figma | Layout | Lớp Kotlin | Trạng thái |
|---|---|---|---|---|
| A01 | [A01-trang-chu.png](../Figma/B-kham-pha/A01-trang-chu.png) | `fragment_a01_trang_chu.xml`, `item_a01_tour_noi_bat.xml`, `item_a01_diem_den.xml` | `TrangChuFragment`, `TourNoiBatAdapter`, `DiemDenAdapter` | Đã xong, dùng làm mẫu cho cả nhóm |
| A02 | [A02-tim-tour.png](../Figma/B-kham-pha/A02-tim-tour.png) | `fragment_a02_tim_tour.xml`, `item_a02_tour.xml` | `TimTourFragment`, `TourAdapter` | Chưa làm |
| A02 | [A02-tim-tour-bo-loc.png](../Figma/B-kham-pha/A02-tim-tour-bo-loc.png) | `dialog_a02_bo_loc.xml` | `BoLocFragment` | Chưa làm |
| A03 | [A03-chi-tiet-tour.png](../Figma/B-kham-pha/A03-chi-tiet-tour.png) | `fragment_a03_chi_tiet_tour.xml`, `item_a03_lich_trinh.xml`, `item_a03_dot_khoi_hanh.xml` | `ChiTietTourFragment`, `LichTrinhAdapter`, `DotKhoiHanhAdapter` | Chưa làm |
| A21 | [A21-yeu-thich-va-da-xem.png](../Figma/B-kham-pha/A21-yeu-thich-va-da-xem.png) | `fragment_a21_yeu_thich.xml`, `item_a21_da_xem.xml` | `YeuThichFragment`, `DaXemAdapter` | Chưa làm |
| A22 | [A22-ban-do-lich-trinh.png](../Figma/B-kham-pha/A22-ban-do-lich-trinh.png) | `fragment_a22_ban_do_lich_trinh.xml`, `item_a22_diem_dung.xml` | `BanDoLichTrinhFragment`, `DiemDungAdapter` | Chưa làm |

Dữ liệu mẫu của cả module đặt trong `DuLieuMau.kt` (đã có lớp `TourMau`; cần thêm trường cho A02 như ngày khởi hành).

## Chỉ dẫn từng màn hình

### A02 Tìm tour

- Tiêu đề, ô tìm (`EditText` nền `@drawable/bg_o_tim`, biểu tượng `ic_search` bên trái, `ic_close` bên phải).
- Hàng chip lọc: `HorizontalScrollView` chứa `ChipGroup`. Chip "Bộ lọc (2)" có `app:chipIcon="@drawable/ic_tune"`.
- Dòng "12 tour" bên trái, "Giá tăng dần" bên phải (`TextView` màu `?attr/colorPrimary`).
- `RecyclerView` dọc chiếm phần còn lại. Mỗi dòng (`item_a02_tour.xml`) là `MaterialCardView`: ảnh giữ chỗ 80dp bên trái; bên phải là tên, dòng mô tả, giá (`?attr/colorTertiary`), nhãn "Còn N chỗ" (`bg_nhan`) hoặc "Hết chỗ" (`bg_nhan_xam`); biểu tượng tim ở góc phải.
- Dòng "Đang tải thêm…" ở cuối danh sách làm khi nối API.

### A02 Bộ lọc

- Trong ảnh đây là bảng trượt từ dưới lên. Giai đoạn này dựng thành một màn hình riêng (`BoLocFragment`), đăng ký là `ManHinh("A02", "Bộ lọc")`.
- "Điểm đến": `Spinner`. "Khoảng giá": hai `EditText` nhập giá thấp nhất và cao nhất (thanh kéo hai đầu làm sau).
- "Tháng khởi hành", "Số ngày", "Sắp xếp": mỗi mục một `ChipGroup`.
- Hai nút cuối: nút chữ "Xóa lọc" và nút chính "Xem 12 tour".

### A03 Chi tiết tour

- Bọc trong `ScrollView`. Ảnh đầu trang: `ImageView` cao 260dp nền `?attr/colorSurfaceVariant` (vuốt ngang nhiều ảnh làm sau). Ba nút tròn đè lên ảnh: `ic_arrow_back`, `ic_share`, `ic_favorite` trên nền `bg_tron_trang`.
- Tên tour, dòng địa điểm có `ic_place`, giá.
- "LỊCH TRÌNH": `RecyclerView` với `android:nestedScrollingEnabled="false"`, mỗi dòng gồm "Ngày 1" và tiêu đề ngày.
- Nút viền "Xem bản đồ lịch trình" có `app:icon="@drawable/ic_map"`.
- "ĐỢT KHỞI HÀNH": `RecyclerView` thứ hai, mỗi dòng gồm ngày, giá, nhãn chỗ trống và nút "Đặt". Đợt hết chỗ: nút đặt `android:enabled="false"`.

### A21 Yêu thích và đã xem

- Hai tab "Yêu thích (3)" và "Đã xem gần đây": hai `TextView` nằm ngang, tab đang chọn có `View` gạch chân màu `?attr/colorPrimary`.
- Dải báo mất mạng: `TextView` nền `?attr/colorTertiaryContainer`.
- Danh sách yêu thích: `RecyclerView` dùng lại `item_a02_tour.xml` và `TourAdapter`.
- "ĐÃ XEM GẦN ĐÂY": `RecyclerView` ngang, mỗi ô (`item_a21_da_xem.xml`) gồm ảnh giữ chỗ, tên, thời điểm xem có `ic_schedule`.

### A22 Bản đồ lịch trình

- Bản đồ thật (Google Maps) làm ở tuần 9. Giờ dùng một `View` nền `?attr/colorSurfaceVariant` chiếm cả màn hình làm hình giữ chỗ, đặt vài `ImageView` `ic_place` lên trên.
- Ô tiêu đề bo tròn phía trên có `ic_arrow_back`, hàng chip chọn ngày, nút tròn `ic_my_location`.
- Khối thông tin phía dưới: tên ngày, `RecyclerView` các điểm dừng (số thứ tự trong vòng tròn `bg_tron_nhat`, tên, giờ), nút chính "Chỉ đường bằng Google Maps".

## Đăng ký màn hình và commit

```kotlin
val manHinhB: List<ManHinh> = listOf(
    ManHinh("A01", "Trang chủ") { TrangChuFragment() },
    ManHinh("A02", "Tìm tour") { TimTourFragment() },
)
```

Mỗi màn hình một commit: `[+] Layout A02 Tìm tour`, `[+] Layout A02 Bộ lọc`, `[+] Layout A03 Chi tiết tour`, `[+] Layout A21 Yêu thích và đã xem`, `[+] Layout A22 Bản đồ lịch trình`.

## Việc làm sau, không thuộc giai đoạn này

Gọi API, kéo xuống để làm mới, tải thêm khi cuộn, Room (yêu thích, đã xem, lịch sử tìm), chia sẻ tour, bản đồ thật.
