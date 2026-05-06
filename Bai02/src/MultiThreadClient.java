import java.io.*;
import java.net.*;
public class MultiThreadClient {
    public static void main(String[] args){
        String host = "Localhost";
        int PORT = 8888;
        try(Socket socket = new Socket(host,PORT);
            PrintWriter out = new PrintWriter(socket.getOutputStream(),true);
            BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            BufferedReader stdIn = new BufferedReader(new InputStreamReader(System.in))){
            System.out.println("Connected to the server");
            System.out.println("Enter message to send to the server, exit to escape");
            String userInput;
            while ((userInput = stdIn.readLine()) != null){
                if ("exit".equalsIgnoreCase(userInput)) {
                    System.out.println("Disconnecting.");
                    break;
                }
                out.println(userInput);
                String response = in.readLine();
                System.out.println("Server: " + response);
            }
        } catch (UnknownHostException e) {
            System.err.println("Cannot find server" + e.getMessage());
        } catch (IOException e){
            System.err.println("Connection error "  + e.getMessage());
        }
    }
}
