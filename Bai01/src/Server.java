import java.io.*;
import java.net.*;
public class Server {
    private static final int PORT = 8888;
    public static void main(String[] args){
        System.out.println("Server is starting on port 8888");
        try{
            ServerSocket serverSocket = new ServerSocket(PORT);
            System.out.println("Waiting for client connection");
            Socket clientSocket = serverSocket.accept();
            System.out.println("Client connected: " + clientSocket.getInetAddress().getHostAddress());
            BufferedReader in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
            PrintWriter out = new PrintWriter(clientSocket.getOutputStream(),true); //auto flush
            String message;
            while ((message = in.readLine()) != null){
                System.out.println("Client send: " + message);
                out.println(message);
            }
            System.out.println("Client disconnected.");

        } catch (IOException e){
            e.printStackTrace();
        }
    }
}
