#!/usr/bin/env bash
# Kiểm tra cài đặt Git của từng thành viên. Chạy trong thư mục repo:
#   bash kiem-tra-git.sh
# Lần 1: sau khi clone (mục 1 CONTRIBUTING.md). Lần 2: sau khi push commit thử (mục 2).

REPO="SonT23/Android-UMT26-04"
loi=0

ok()    { printf '  [OK]   %s\n' "$1"; }
canh()  { printf '  [!]    %s\n' "$1"; }
sai()   { printf '  [LỖI]  %s\n' "$1"; loi=$((loi + 1)); }
goi_y() { printf '         -> %s\n' "$1"; }
thuong() { printf '%s' "$1" | tr 'A-Z' 'a-z'; }

echo "1. Git và repo"
if ! command -v git >/dev/null 2>&1; then
  sai "Chưa cài Git"; goi_y "Xem mục 1.2"; exit 1
fi
ok "$(git --version)"
if ! git rev-parse --is-inside-work-tree >/dev/null 2>&1; then
  sai "Đang không đứng trong thư mục repo"; goi_y "cd Android-UMT26-04 rồi chạy lại"; exit 1
fi
url=$(git remote get-url origin 2>/dev/null)
case "$(thuong "$url")" in
  *sont23/android-umt26-04*) ok "Remote origin: $url" ;;
  *sont23/mobile_project_2026*)
    canh "Remote còn dùng tên repo cũ: $url"
    goi_y "git remote set-url origin git@github.com:$REPO.git" ;;
  *) sai "Remote origin không phải repo nhóm: ${url:-(trống)}"; goi_y "Clone lại theo mục 1.5" ;;
esac

echo "2. Tên và email commit"
ten=$(git config user.name)
email=$(git config user.email)
if [ -z "$ten" ]; then
  sai "Chưa đặt user.name"; goi_y "Mục 1.3"
else
  case "$ten" in
    *" "*) ok "Tên: $ten" ;;
    *) canh "Tên \"$ten\" có vẻ chưa phải họ tên đầy đủ"
       goi_y "git config --global user.name \"Họ Tên Đầy Đủ\"" ;;
  esac
fi
case "$email" in
  "") sai "Chưa đặt user.email"; goi_y "Mục 1.3" ;;
  *@*.*) ok "Email: $email" ;;
  *) sai "Email không hợp lệ: $email"; goi_y "Mục 1.3" ;;
esac
if [ "$(git config pull.rebase)" != "false" ]; then
  canh "Chưa đặt pull.rebase"; goi_y "git config --global pull.rebase false"
fi

echo "3. Kết nối GitHub"
gh_user=""
case "$(thuong "$url")" in
  git@github.com:*|ssh://*)
    ssh_out=$(ssh -T -o ConnectTimeout=15 git@github.com 2>&1)
    gh_user=$(printf '%s\n' "$ssh_out" | sed -n 's/^Hi \([^!]*\)!.*/\1/p' | head -n 1)
    if [ -n "$gh_user" ]; then
      ok "SSH đăng nhập GitHub bằng tài khoản: $gh_user"
    else
      sai "SSH chưa kết nối được GitHub"
      goi_y "$(printf '%s\n' "$ssh_out" | tail -n 1)"
      goi_y "Mục 1.4"
    fi ;;
  https://*) ok "Đang dùng HTTPS, bỏ qua kiểm tra SSH" ;;
esac
push_out=$(GIT_TERMINAL_PROMPT=0 git push --dry-run origin HEAD:refs/heads/kiem-tra-quyen-ghi 2>&1)
if [ $? -eq 0 ]; then
  ok "Có quyền push vào repo (chạy thử, không tạo gì trên GitHub)"
else
  sai "Chưa push được vào repo"
  case "$push_out" in
    *"Permission to"*|*403*) goi_y "Chưa nhận lời mời collaborator -> mục 1.5" ;;
    *publickey*) goi_y "SSH key chưa được thêm vào GitHub -> mục 1.4" ;;
    *) goi_y "$(printf '%s\n' "$push_out" | grep -v '^[[:space:]]*$' | tail -n 1)" ;;
  esac
fi

echo "4. Commit thử của bạn (mục 2)"
nhanh=$(git rev-parse --abbrev-ref HEAD)
sha=$(git rev-parse HEAD)
email_commit=$(git log -1 --format=%ae)
tieu_de=$(git log -1 --format=%s)
co_commit=0
da_gan=0
if [ "$nhanh" = "main" ]; then
  canh "Đang ở branch main, commit thử phải nằm trên branch riêng (vd. docs/thanh-vien-<ten>)"
  goi_y "Làm mục 2 trong CONTRIBUTING.md rồi chạy lại script ngay trên branch đó"
elif [ -z "$email" ] || [ "$(thuong "$email_commit")" != "$(thuong "$email")" ]; then
  canh "Commit mới nhất trên branch $nhanh không phải của bạn"
  goi_y "Làm mục 2 trong CONTRIBUTING.md rồi chạy lại script"
else
  co_commit=1
  case "$tieu_de" in
    "[+] "*|"[-] "*|"[m] "*) ok "Message đúng quy định: $tieu_de" ;;
    *) canh "Message thiếu tiền tố [+] / [-] / [m]: $tieu_de"
       goi_y "Commit sau nhớ có tiền tố (đã push rồi thì để nguyên, không sửa lịch sử)" ;;
  esac
  if ! git branch -r --contains "$sha" 2>/dev/null | grep -q .; then
    canh "Commit chưa được push lên GitHub"
    goi_y "git push -u origin $nhanh rồi chạy lại script"
  elif ! command -v curl >/dev/null 2>&1; then
    canh "Máy không có curl, tự kiểm tra trên GitHub"
    goi_y "https://github.com/$REPO/commits/$nhanh phải có avatar và username của bạn"
  else
    json=$(curl -sL --max-time 15 "https://api.github.com/repos/$REPO/commits/$sha")
    login=$(printf '%s\n' "$json" | sed -n '/^  "author": {/,/^  }/s/^    "login": "\([^"]*\)".*/\1/p' | head -n 1)
    if [ -n "$login" ]; then
      ok "Commit $(git rev-parse --short HEAD) gắn đúng vào tài khoản GitHub: $login"
      da_gan=1
      if [ -n "$gh_user" ] && [ "$(thuong "$login")" != "$(thuong "$gh_user")" ]; then
        canh "Tài khoản của commit ($login) khác tài khoản SSH ($gh_user)"
        goi_y "Kiểm tra lại email ở mục 1.1 và 1.3"
      fi
    elif printf '%s\n' "$json" | grep -q '^  "author": null'; then
      sai "Commit đã lên GitHub nhưng KHÔNG gắn vào tài khoản nào"
      goi_y "Thêm và xác minh email $email ở GitHub -> Settings -> Emails (mục 1.1), rồi chạy lại script"
    else
      canh "Không kiểm tra được qua GitHub API (mạng hoặc bị giới hạn lượt gọi)"
      goi_y "Tự mở https://github.com/$REPO/commits/$nhanh xem commit có avatar và username của bạn không"
    fi
  fi
fi

echo
if [ "$loi" -gt 0 ]; then
  echo "==> Còn $loi lỗi. Sửa theo gợi ý rồi chạy lại: bash kiem-tra-git.sh"
  exit 1
elif [ "$co_commit" -eq 0 ]; then
  echo "==> Cài đặt xong. Tiếp theo: làm mục 2 trong CONTRIBUTING.md rồi chạy lại script."
elif [ "$da_gan" -eq 0 ]; then
  echo "==> Chưa xác nhận được commit thử. Làm theo các dòng [!] ở mục 4 rồi chạy lại script."
else
  echo "==> Hoạt động tốt. Tiếp theo: mở pull request (mục 3.3) và báo nhóm."
fi
