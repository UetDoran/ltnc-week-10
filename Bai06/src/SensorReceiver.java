import java.net.*;

public class SensorReceiver {
    public static void main(String[] args) {
        try (DatagramSocket socket = new DatagramSocket(6000)) {
            System.out.println("UDP waiting on port 6000");

            byte[] buffer = new byte[1024];

            while (true) {
                DatagramPacket packet = new DatagramPacket(buffer, buffer.length);
//ctrinh dung o day cho cac tin toi
                socket.receive(packet);

//giai ma chuyen thanh string
                String receivedData = new String(packet.getData(), 0, packet.getLength());
                System.out.println("Dữ liệu cảm biến nhận được: " + receivedData);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}