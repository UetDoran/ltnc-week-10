import java.io.*;
import java.net.*;
import java.util.logging.*;

public class MultiThreadServer {
    private static final int PORT = 8888;
    private static final Logger logger = Logger.getLogger(MultiThreadServer.class.getName());
    public static void main(String[] args){
        logger.info("Starting server on port " + PORT );
        try(ServerSocket serverSocket = new ServerSocket(PORT)){
            logger.info("Server waiting for clients to connect ");
            while (true){
                Socket clientSocket = serverSocket.accept();
                String clientIP = clientSocket.getInetAddress().getHostAddress();
                logger.info("New connection: client from IP " + clientIP + " connected");
                ClientHandler clientHandler = new ClientHandler(clientSocket);
                new Thread(clientHandler).start();
            }
        } catch (IOException e){
            e.getMessage();
        }
    }
}
class ClientHandler implements Runnable {
    private Socket socket;
    private static final Logger logger = Logger.getLogger(ClientHandler.class.getName());
    public ClientHandler(Socket socket){
        this.socket = socket;
    }
    public void run(){
        String clientIP = socket.getInetAddress().getHostAddress();
        try(
                BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
                PrintWriter out = new PrintWriter(socket.getOutputStream(),true);
                ){
            String message;
            while ((message = in.readLine()) != null){
                logger.info("[Received] from " + clientIP + " successfully");
                out.println(message);
                logger.info("[Response] to " + clientIP + " successfully");
            }
        } catch (IOException e){
            e.printStackTrace();
        } finally {
            try{
                socket.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
            logger.info("Disconnected " + clientIP);
        }
    }
}
