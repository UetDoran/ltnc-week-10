import java.net.*;

public class SensorSender {
    public static void main(String[] args) {
        try (DatagramSocket socket = new DatagramSocket()) {
            String data = "Temp: 28°C, Humidity: 65%";
            byte[] buffer = data.getBytes();

            InetAddress receiverAddress = InetAddress.getByName("localhost");
            DatagramPacket packet = new DatagramPacket(buffer, buffer.length, receiverAddress, 6000);

            socket.send(packet);
            System.out.println("Broadcast dữ liệu: " + data);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}