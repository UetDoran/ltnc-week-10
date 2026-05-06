import java.io.*;
import java.net.*;
public class CommandClient {
    public static void main(String[] args){
        try (Socket socket = new Socket("localhost",5000);
             PrintWriter out = new PrintWriter(socket.getOutputStream(),true)) {
            System.out.println("Connected to the server");
            System.out.println("Đang gửi lệnh: START");
            out.println("START");
            Thread.sleep(2000);
            System.out.println("Đang gửi lệnh: SHUTDOWN");
            out.println("SHUTDOWN");

        } catch (ConnectException e){
            System.err.println("Error: Remote server is offline!");
        } catch (IOException | InterruptedException e){
            System.err.println("Error: " + e.getMessage());
        }
    }
}
