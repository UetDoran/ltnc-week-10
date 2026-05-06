import java.io.*;
import java.net.*;

public class Client {
    public static void main(String[] args) {
        String host = "127.0.0.1";
        int port = 8888;

        try (Socket socket = new Socket(host, port);
             PrintWriter out = new PrintWriter(socket.getOutputStream(), true);
             BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
             BufferedReader stdIn = new BufferedReader(new InputStreamReader(System.in))) {

            System.out.println("Connected to the server " + host + ":" + port);
            System.out.println("Enter message to send to the server(exit to escape)"); //dung ngoac tron de tu dong close khi try ket thuc , try ngoac nhon phai dong bang .close()
            

            String userInput;
            while ((userInput = stdIn.readLine()) != null) {
                if ("exit".equalsIgnoreCase(userInput)) {
                    System.out.println("Disconnecting");
                    break;
                }

                out.println(userInput);
                String response = in.readLine();
                System.out.println("Server responding: " + response);
            }
        } catch (UnknownHostException e) {
            System.err.println("Cant find server " + e.getMessage());
        } catch (IOException e) {
            e.getMessage();
        }
    }
}