import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;

public class RestChatClient {
    private static final String SERVER_URL = "http://localhost:8080";
    private static String clientId;
    private static int messagesReadCount = 0; // Đếm số lượng tin nhắn đã đọc để không in lặp

    public static void main(String[] args) {
        try {
            // Bước 1: Tham gia phòng và nhận ID
            clientId = sendGetRequest(SERVER_URL + "/join");
            System.out.println("Đã tham gia phòng chat! ID của bạn là: " + clientId);

            // Bước 2: Khởi động luồng lấy tin nhắn định kỳ (Polling)
            startPollingThread();

            // Bước 3: Vòng lặp chính để người dùng nhập tin nhắn
            BufferedReader stdIn = new BufferedReader(new InputStreamReader(System.in));
            String userInput;
            System.out.println("Nhập tin nhắn để gửi (Gõ 'exit' để thoát):");

            while ((userInput = stdIn.readLine()) != null) {
                if ("exit".equalsIgnoreCase(userInput)) {
                    System.out.println("Đang thoát...");
                    System.exit(0);
                }

                // Gắn thẻ tin nhắn với ID
                String formattedMessage = "[Client " + clientId + "]: " + userInput;
                sendPostRequest(SERVER_URL + "/submit", formattedMessage);
            }
        } catch (Exception e) {
            System.err.println("Lỗi Client: " + e.getMessage());
        }
    }

    // Hàm chạy ngầm, cứ 1 giây gọi server 1 lần để lấy tin nhắn mới
    private static void startPollingThread() {
        Thread pollingThread = new Thread(() -> {
            while (true) {
                try {
                    String allMessagesStr = sendGetRequest(SERVER_URL + "/messages");
                    if (!allMessagesStr.isEmpty()) {
                        String[] messages = allMessagesStr.split("\n");
                        // Chỉ in ra những tin nhắn mới (dựa vào số đếm)
                        for (int i = messagesReadCount; i < messages.length; i++) {
                            System.out.println(messages[i]);
                        }
                        messagesReadCount = messages.length; // Cập nhật lại số đếm
                    }
                    Thread.sleep(1000); // Tạm dừng 1 giây trước khi hỏi lại (Polling)
                } catch (Exception e) {
                    // Im lặng bỏ qua nếu server tạm thời không phản hồi
                }
            }
        });
        pollingThread.setDaemon(true); // Để luồng tự chết khi chương trình chính kết thúc
        pollingThread.start();
    }

    //Các hàm hỗ trợ HTTP Request

    private static String sendGetRequest(String targetUrl) throws IOException {
        URL url = new URL(targetUrl);
        HttpURLConnection con = (HttpURLConnection) url.openConnection();
        con.setRequestMethod("GET");
        return readResponse(con);
    }

    private static void sendPostRequest(String targetUrl, String payload) throws IOException {
        URL url = new URL(targetUrl);
        HttpURLConnection con = (HttpURLConnection) url.openConnection();
        con.setRequestMethod("POST");
        con.setDoOutput(true);

        try (OutputStream os = con.getOutputStream()) {
            byte[] input = payload.getBytes("utf-8");
            os.write(input, 0, input.length);
        }
        readResponse(con); // Cần đọc response để hoàn tất kết nối
    }

    private static String readResponse(HttpURLConnection con) throws IOException {
        int status = con.getResponseCode();
        if (status != 200) return "";

        BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream(), "utf-8"));
        StringBuilder content = new StringBuilder();
        String inputLine;
        while ((inputLine = in.readLine()) != null) {
            content.append(inputLine).append("\n");
        }
        in.close();
        return content.toString().trim();
    }
}