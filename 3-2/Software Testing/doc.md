Kiểm thử phần mềm là:
  - Một hoạt động để kiểm tra xem kết quả thực tế phù hợp vói kết quả thiết kế, yêu cầu.
  - Là hoạt động nhằm tìm kiếm, phát hiện các lỗi của phần mềm.
  - Xác định lỗi, nó có thể được thực hiện bằng tay hoặc sử dụng công cụ tự động.
  - Có 2 loại kiểm thử phần mềm: black-box and white-box
Mục tiêu kiểm thử phần mềm:
  - Kiểm chứng (validation testing):
    - Chứng minh cho người phát triển và khách hàng rằng phần mềm thỏa mãn yêu cầu
    - Mong muốn hệ thống thực hiện đúng bằng cách sử dụng một tập các test-case để phản ánh hệ thống thực hiện đúng như mong muốn.
    - Một kiểm tra thành công cho thấy hệ thống hoạt động như mong muốn
  - Kiểm tra khiếm khuyết (detect testing):
Kiểm định (Verification):
  - Phần mềm phải làm đúng với đặc tả của nó
  - Kiểm định (xác minh) là quy trình đánh giá phần mềm để xác định các sản phẩm của một giai đoạn phát triển cụ thể có thỏa mãn các điều kiện bắt buộc được xác định ở đầu giai đoạn này
  - Kiểm định là kiểm tra tĩnh (static testing)
  - Các phương pháp kiểm: Walkthrough, Code Inspection
Kiểm chứng (Validation):
  - Phần mềm phải làm điều mà khách hàng thật sự cần
  - Kiểm chứng (xác thực) là quy trình đánh giá phần mềm trong lúc cuối hoặc cuối giai đoạn phát triển phần mềm để xác định phần mềm có thỏa mãn các yêu cầu quy định.
  - Kiểm chứng là quy trình đánh giá sản phẩm cuối cùng để kiểm tra phần mềm thỏa mãn yêu cầu mong muốn của khách hàng.
  Kiểm chứng là kiểm trong động (dynamic testing)
  - Các phương pháp kiểm chứng: Testing, End Users

Bug:
  - Là kết quả của lỗi lập trình (coding error)
  - Một lỗi được tìm thấy trong môi trường phát triển trước khi giao sản phẩm phần mềm cho khách hàng
  - Lỗi lập trình làm cho chương trình hoạt động kém, tạo ra kết quả sai, hoặc bị crash
  - Một lỗi trong phần mềm hoặc phần cứng làm cho chương trình bị trục trặc (malfunction)
  - Bug là thuật ngữ của người kiểm thử

Đồ thị dòng điều khiển:
  -Đồ thị dòng điều khiển chỉ chứa các nút quyết định có hai nhánh thì được gọi là đồ thị dòng điều khiển nhị phân
  - Một đồ thị dòng điều khiển bất kỳ có thể được biến đổi thành đồ thị dòng điều khiển nhị phân
  - Một điều kiện phức hợp  (bao gồm các toán tử luận lý AND, OR, NAND, NOR) có thể bao gồm các điều kiện đơn chỉ có một toán tử. Điều kiện đơn được gọi là vị từ (predicate)

Mức phủ mã lệnh:
  - Mức phủ mã lệnh (code coverage) là độ đo được dùng để mô tả mã nguồn của một chương trình được kiểm thử bởi một bộ kiểm thử cụ thể.
  - Một chương trình có mức phủ mã lệnh cao nếu toàn bộ chương trình đều được kiểm thử và có thể có ít lỗi sai.
  - Có nhiều độ đo khác nhau được dùng để tính toán mức phủ mã lệnh, thông thường là phần trăm các chương trình con và phần trăm các phát biểu của chương trình được kiểm thử với bộ kiểm thử (test suite)

Các loại mức phủ:
  - Các mức phủ cơ bản:
    - Mức phủ hàm.
    - Mức phủ phát biểu.
    - Mức phủ rẽ nhánh.
    - Mức phủ điều kiện.
  - Mức phủ điều điện / quyết định thay đổi
  - Mức phủ đa điều kiện
  - Mức phủ giá trị tham số


Kiểm thử hộp trắng:
