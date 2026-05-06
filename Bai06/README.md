1. Ngoại lệ xảy ra khi chạy đồng thời hai CommandServer trên cùng một cổng?

Ngoại lệ bắt được: java.net.BindException: Address already in use.

Nguyên nhân gốc rễ: Hệ điều hành quản lý mạng thông qua các "cổng" (Port). Tại một thời điểm, chỉ cho phép một tiến trình (process) duy nhất được quyền đăng ký (bind) để "lắng nghe" trên một cổng cụ thể (ở đây là cổng 5000). Khi Server thứ nhất đã chạy, nó khóa cổng này lại. Server thứ hai cố tình khởi động và giành cổng 5000 sẽ bị hệ điều hành từ chối ngay lập tức, ném ra lỗi BindException.

2. Tại sao Client TCP báo lỗi khi vắng Server, còn Sender UDP thì không?
Sự khác biệt này đến từ bản chất thiết kế của hai giao thức:

Với TCP (CommandClient): TCP là giao thức hướng kết nối (Connection-oriented). Trước khi gửi dữ liệu, Client phải thực hiện quy trình "Bắt tay 3 bước" (3-way handshake) với Server. Nếu Server không bật, không ai phản hồi lại cái "bắt tay" đó, hệ điều hành sẽ lập tức báo lỗi kết nối thất bại ConnectException về cho Client.

Với UDP (SensorSender): UDP là giao thức phi kết nối (Connectionless), hoạt động theo kiểu "Bắn và Quên" (Fire and forget). Sender chỉ việc đóng gói dữ liệu (DatagramPacket), ghi địa chỉ đích (cổng 6000) rồi ném nó ra card mạng là xong nhiệm vụ. Nó hoàn toàn không quan tâm đến việc có ai đang mở ứng dụng SensorReceiver để hứng dữ liệu ở đầu bên kia hay không. Vì không có cơ chế kiểm tra trước kết nối, Java sẽ không ném ra bất kỳ lỗi nào cả.