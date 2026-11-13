# Module C: Đặt tour, đơn của khách (Thịnh)

Đọc [README.md](README.md) của thư mục này trước. File này chỉ ghi phần riêng của module C.

## Nơi làm việc

| Mục | Giá trị |
|---|---|
| Branch | `feature/layout-dat-tour` |
| Package Kotlin | `Source/TourApp/app/src/main/java/vn/edu/umt/tour/ui/khach/dattour/` |
| File đăng ký màn hình | `ui/khach/dattour/ManHinhC.kt` |
| File chuỗi | `res/values/strings_dattour.xml` (tự tạo; bản tiếng Anh ở `res/values-en/` làm sau cũng được) |
| Ảnh Figma | [Docs/Figma/C-dat-tour/](../Figma/C-dat-tour/) |

## Các màn hình cần làm

| Mã | Ảnh Figma | Layout | Lớp Kotlin |
|---|---|---|---|
| A05 | [A05-dat-thanh-cong.png](../Figma/C-dat-tour/A05-dat-thanh-cong.png) | `fragment_a05_dat_thanh_cong.xml` | `DatThanhCongFragment` |
| A09 | [A09-chi-tiet-don.png](../Figma/C-dat-tour/A09-chi-tiet-don.png) | `fragment_a09_chi_tiet_don.xml` | `ChiTietDonFragment` |
| A08 | [A08-don-cua-toi.png](../Figma/C-dat-tour/A08-don-cua-toi.png) | `fragment_a08_don_cua_toi.xml`, `item_a08_don.xml` | `DonCuaToiFragment`, `DonAdapter` |
| A04 | [A04-dat-tour.png](../Figma/C-dat-tour/A04-dat-tour.png) | `fragment_a04_dat_tour.xml` | `DatTourFragment` |

Nên làm theo thứ tự trong bảng: A05 và A09 chỉ có chữ và nút, A08 có danh sách, A04 có thêm phần bấm cộng trừ.

Dữ liệu mẫu đặt trong file `DuLieuMau.kt` của package mình, ví dụ lớp `DonMau(val ma: String, val tenTour: String, val ngay: String, val soNguoi: String, val tongTien: String, val trangThai: String)` và hàm `taoDonMau()` trả về danh sách 4 đơn như trong ảnh. Xem cách viết ở `ui/khach/khampha/DuLieuMau.kt`.

## Chỉ dẫn từng màn hình

### A05 Đặt thành công

- Nút đóng `ic_close` ở góc trên trái.
- Vòng tròn xanh có dấu kiểm: `ImageView` 96dp dùng `@drawable/ic_check`. Nền tròn tự tạo `res/drawable/bg_a05_tron_xanh.xml` (chép `bg_tron_nhat.xml`, đổi màu thành `@color/trang_thai_tot`); màu dấu kiểm `@color/on_trang_thai_tot`.
- Tiêu đề và dòng mô tả căn giữa.
- Thẻ thông tin đơn: `MaterialCardView` chứa 4 dòng. Mỗi dòng là `LinearLayout` ngang gồm 2 `TextView`: nhãn bên trái (`layout_weight="1"`), giá trị bên phải. Tổng tiền màu `?attr/colorTertiary`.
- Thẻ "Hướng dẫn chuyển khoản": `LinearLayout` dọc có `android:background="@drawable/bg_anh_trong"` thì ra nền xám; muốn nền cam như ảnh thì tự tạo `bg_a05_chuyen_khoan.xml` với màu `?attr/colorTertiaryContainer`.
- Hai nút đáy: nút chính "Xem đơn của tôi", nút viền "Về trang chủ".

### A09 Chi tiết đơn

- Thanh trên: `ic_arrow_back`, tiêu đề, `ic_share`.
- Thẻ mã QR: nhãn trạng thái ở góc, ô vuông giữ chỗ cho mã QR (`ImageView` 128dp nền `@drawable/bg_anh_trong`), mã đơn chữ đậm, dòng hướng dẫn. Mã QR thật (chức năng C5) làm sau.
- 6 dòng thông tin kiểu nhãn và giá trị như A05, giữa các dòng có `View` cao 1dp làm đường kẻ.
- Nút "Thêm vào Lịch": nút nhạt có `app:icon="@drawable/ic_calendar"`.
- Nút "Hủy đơn": nút viền, thêm `android:textColor="?attr/colorError"`.
- Hai dòng ghi chú cuối căn giữa.

### A08 Đơn của tôi

- Tiêu đề, hàng chip lọc (`HorizontalScrollView` chứa `ChipGroup` 4 chip), bên dưới là `RecyclerView` chiếm phần còn lại.
- Mỗi dòng (`item_a08_don.xml`) là `MaterialCardView` gồm: mã đơn, nhãn trạng thái ở góc phải, tên tour, dòng ngày khởi hành có `ic_calendar`, số người, tổng tiền căn phải màu `?attr/colorTertiary`.
- Nhãn trạng thái có 4 màu. Trong `onBindViewHolder` đổi nền theo trạng thái bằng `holder.trangThaiTV.setBackgroundResource(...)`:

  | Trạng thái | Nền |
  |---|---|
  | Chờ xác nhận | `R.drawable.bg_nhan` |
  | Đã xác nhận | `R.drawable.bg_nhan_xanh_duong` |
  | Hoàn thành | `R.drawable.bg_nhan_xanh` |
  | Đã hủy | `R.drawable.bg_nhan_do` |

- Adapter viết theo mẫu `ui/khach/khampha/TourNoiBatAdapter.kt`.
- **Không vẽ thanh điều hướng dưới** có trong ảnh.

### A04 Đặt tour

- Gốc là `ConstraintLayout`: nút "Xác nhận đặt tour" ghim sát đáy, phía trên là `ScrollView` cao `0dp` chứa toàn bộ form.
- Thẻ tóm tắt tour: ảnh giữ chỗ, tên, đợt khởi hành, nhãn "Còn 5 chỗ" (`bg_nhan`).
- "SỐ NGƯỜI": 2 dòng "Người lớn" và "Trẻ em". Mỗi dòng gồm tên và đơn giá bên trái; bên phải là nút trừ (`ImageView` `ic_remove`), số lượng (`TextView`), nút cộng (`ImageView` `ic_add` nền `bg_tron_nhat`).
- Phần cộng trừ làm luôn được, không cần API: giữ 2 biến `Int`, bấm cộng hoặc trừ thì đổi biến, gọi `setText` cho ô số lượng và tính lại tổng tiền. Không cho số người lớn nhỏ hơn 1, trẻ em nhỏ hơn 0.
- "THÔNG TIN LIÊN HỆ": 3 ô nhập `TextInputLayout`. Ô ghi chú thêm `android:minLines="3"` và `android:gravity="top"` cho ô nhập bên trong.
- Bảng tổng tiền: `LinearLayout` dọc nền `@drawable/bg_anh_trong` gồm 2 dòng chi tiết, một đường kẻ và dòng "Tổng tiền" chữ lớn.

## Đăng ký màn hình và commit

Sau mỗi màn hình, thêm một dòng vào `ui/khach/dattour/ManHinhC.kt`:

```kotlin
val manHinhC: List<ManHinh> = listOf(
    ManHinh("A05", "Đặt thành công") { DatThanhCongFragment() },
    ManHinh("A09", "Chi tiết đơn") { ChiTietDonFragment() },
)
```

Commit từng màn hình, ví dụ cho A05:

```bash
git add Source/TourApp/app/src/main/res/layout/fragment_a05_dat_thanh_cong.xml
git add Source/TourApp/app/src/main/res/drawable/bg_a05_tron_xanh.xml
git add Source/TourApp/app/src/main/res/values/strings_dattour.xml
git add Source/TourApp/app/src/main/java/vn/edu/umt/tour/ui/khach/dattour/
git commit -m "[+] Layout A05 Đặt thành công"
git push -u origin feature/layout-dat-tour
```

Các commit tiếp theo: `[+] Layout A09 Chi tiết đơn`, `[+] Layout A08 Đơn của tôi`, `[+] Layout A04 Đặt tour`.

## Việc làm sau, không thuộc giai đoạn này

Các lớp trong `model/`, gọi API đặt tour và lấy danh sách đơn, mã QR thật, thêm vào Lịch, nhắc lịch, theo dõi đơn chạy nền.
