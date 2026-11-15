# Hướng dẫn dựng giao diện cho từng thành viên

Giai đoạn này mỗi người dựng **toàn bộ giao diện** các màn hình của module mình theo thiết kế trong `Docs/Figma/`, dùng dữ liệu mẫu. Chưa cần API, chưa cần database, chưa cần khung điều hướng. Phần nối các màn hình lại với nhau làm sau.

Chỉ dùng những gì đã học đến bài A08: Activity, Fragment, Intent, các view cơ bản, layout, ListView, RecyclerView với Adapter và ViewHolder, Spinner.

## Mỗi người đọc file của mình

| Module | Người làm | File hướng dẫn | Số màn hình |
|---|---|---|---|
| A: Tài khoản, phiên, cài đặt | Hoàng | [A-hoang.md](A-hoang.md) | 6 |
| B: Khám phá tour | Sơn | [B-son.md](B-son.md) | 5 (đã xong A01) |
| C: Đặt tour, đơn của khách | Thịnh | [C-thinh.md](C-thinh.md) | 4 |
| D: Quản trị tour | Phúc | [D-phuc.md](D-phuc.md) | 5 |
| E: Quản trị đơn, khách, thống kê | Trúc Đào | [E-truc-dao.md](E-truc-dao.md) | 5 |

Hai tài liệu chung cần đọc trước:

- [Source/TourApp/README.md](../../Source/TourApp/README.md): cấu trúc project, các bước làm một màn hình, quy tắc đặt tên, bảng tra thành phần giao diện (ô nhập, nút, chip, nhãn…) kèm đoạn XML chép được ngay.
- [CONTRIBUTING.md](../../CONTRIBUTING.md): cách tạo branch, commit, push, mở pull request.

## Quy trình chung

1. Lấy code mới và tạo branch (tên branch ghi trong file hướng dẫn của từng người):

   ```bash
   git switch main
   git pull
   git switch -c feature/layout-<module>
   ```

2. Android Studio → **File → Open** → chọn thư mục `Source/TourApp`. Bấm **Run** một lần để chắc project chạy được: sẽ thấy màn hình "Xem thử màn hình" có dòng "A01 · Trang chủ".
3. Làm từng màn hình theo bảng trong file hướng dẫn của mình. Mở ảnh Figma bên cạnh để so.
4. Xong màn hình nào thì thêm một dòng vào file `ManHinhX.kt` của mình, bấm Run để xem, rồi **commit riêng màn hình đó**.
5. Push và mở pull request. Không cần chờ xong hết mới mở: 2 đến 3 màn hình một PR là vừa.

## Quy tắc chung, ai cũng phải theo

- Chỉ tạo và sửa file trong package của mình và các file tài nguyên mang mã màn hình của mình.
- Không sửa `MainActivity.kt`, package `xemthu/`, `AndroidManifest.xml`, `colors.xml`, `themes.xml`, `dimens.xml`, `strings.xml`, file Gradle. Cần sửa thì nhắn nhóm trước.
- Không vẽ thanh điều hướng dưới trong layout của mình, dù ảnh Figma có vẽ.
- Không gõ chữ cứng trong layout: mọi chữ đặt trong file chuỗi của module. Riêng dữ liệu mẫu (tên tour, tên khách, số tiền) thì để trong file `DuLieuMau.kt` của package mình.
- Phần nào ảnh Figma có mà chưa học (bản đồ, camera, biểu đồ, kéo thả, bảng trượt từ dưới lên): dựng **hình giữ chỗ** theo chỉ dẫn trong file của mình, chức năng thật làm sau.
- Phần ai làm thì người đó tự commit và tự push bằng tài khoản của mình.

## Tự kiểm tra trước khi mở pull request

- [ ] Bấm Run không lỗi; mọi màn hình của mình mở được từ danh sách xem thử.
- [ ] So với ảnh Figma: đủ thành phần, đúng thứ tự, khoảng cách gần giống.
- [ ] Tên file layout, tên chuỗi đều bắt đầu bằng mã màn hình.
- [ ] Không có chữ cứng trong layout (Android Studio tô vàng dòng `android:text="..."` gõ thẳng chữ).
- [ ] Mỗi màn hình một commit, tiền tố `[+]`.
- [ ] `git status` không còn file lạ; không commit thư mục `build/` hay `.idea/`.
