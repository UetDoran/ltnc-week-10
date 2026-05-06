import java.io.*;
import java.net.*;
public class CommandServer {
    public static void main(String[] args) {
        try (ServerSocket serverSocket = new ServerSocket(5000)) {
            serverSocket.setSoTimeout(5000);
            System.out.println("Command Server is waiting on port 5000");
            while (true){
                try{
                    Socket socket = serverSocket.accept();
                    System.out.println("Connected to client : " + socket.getInetAddress());
                    BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
                    String command;
                    while ((command = in.readLine()) != null) {
                        if ("START".equals(command)) {
                            System.out.println("System initialized...");
                        } else if ("SHUTDOWN".equals(command)) {
                            System.out.println("System shutdown...");
                            socket.close();
                            return;
                        }
                    }
                } catch (SocketTimeoutException e){
                    System.out.println("[Timeout] 5seconds no one connecting.");
                }
            }

        } catch (BindException e){
            System.err.println("BindException error!");
        } catch (IOException e){
            System.err.println("IO Exception: " + e.getMessage());
        }
    }
}
