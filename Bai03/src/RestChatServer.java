import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import com.sun.net.httpserver.HttpServer;

import java.io.*;
import java.net.InetSocketAddress;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.atomic.AtomicInteger;
public class RestChatServer {
    private static final AtomicInteger clientIdCounter = new AtomicInteger(0);
    private static final List<String> roomMessages = new CopyOnWriteArrayList<>();
    public static void main(String[] args) throws IOException{
        int PORT = 8888;
        HttpServer server = HttpServer.create(new InetSocketAddress(PORT),0);
        server.createContext("/join", new JoinHandler());
        server.createContext("/submit", new SubmitHandler());
        server.createContext("/messages", new MessagesHandler());

        server.setExecutor(java.util.concurrent.Executors.newCachedThreadPool());
        server.start();
        System.out.println("REST API Chat Server đang chạy tại http://localhost:" + PORT);


    }
    static class JoinHandler implements HttpHandler{
        public void handle(HttpExchange exchange) throws IOException {
            if ("GET".equals(exchange.getRequestMethod())) {
                int newId = clientIdCounter.incrementAndGet();
                String response = String.valueOf(newId);
                System.out.println("Đã cấp phát ID " + newId + " cho Client mới.");
                sendResponse(exchange, response);
            } else {
                exchange.sendResponseHeaders(405, -1); // 405 Method Not Allowed
            }
        }
    }
    static class SubmitHandler implements HttpHandler {
        @Override
        public void handle(HttpExchange exchange) throws IOException {
            if ("POST".equals(exchange.getRequestMethod())) {
                InputStream is = exchange.getRequestBody();
                String message = new String(is.readAllBytes());

                roomMessages.add(message);
                System.out.println("Server nhận tin nhắn: " + message);
                sendResponse(exchange, "OK");
            } else {
                exchange.sendResponseHeaders(405, -1);
            }
        }
    }
    static class MessagesHandler implements HttpHandler {
        @Override
        public void handle(HttpExchange exchange) throws IOException {
            if ("GET".equals(exchange.getRequestMethod())) {
                String response = String.join("\n", roomMessages);
                sendResponse(exchange, response);
            } else {
                exchange.sendResponseHeaders(405, -1);
            }
        }
    }
    private static void sendResponse(HttpExchange exchange, String response) throws IOException {
        byte[] bytes = response.getBytes("UTF-8");
        exchange.sendResponseHeaders(200, bytes.length);
        OutputStream os = exchange.getResponseBody();
        os.write(bytes);
        os.close();
    }

}
