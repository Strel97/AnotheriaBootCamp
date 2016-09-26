package net.anotheria.strel.basics;

import org.apache.log4j.Logger;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;

/**
 * UDP client implementation. Client connects to {@link UdpServer}
 * and tries to send and then receive {@link String} from server.
 *
 * @author Strel97
 */
public class UdpClient {

    private DatagramSocket clientSocket;
    private byte[] sendData = new byte[1024];
    private byte[] receiveData = new byte[1024];

    private Logger log = Logger.getLogger(UdpClient.class.getName());


    public UdpClient() {
        try {
            clientSocket = new DatagramSocket();
        } catch (SocketException ex) {
            log.fatal(String.format("Failed to create client socket: %s", ex.getMessage()));
        }
    }

    /**
     * Sends string to the {@link UdpServer}.
     *
     * @param data          Message to send
     * @param serverIp      Server ip address
     * @param serverPort    Server port
     */
    public void sendData(String data, InetAddress serverIp, int serverPort) {
        try {
            sendData = data.getBytes();
            DatagramPacket sendPacket = new DatagramPacket(sendData, sendData.length, serverIp, serverPort);
            clientSocket.send(sendPacket);
        } catch (IOException ex) {
            log.error(String.format("Could not send data to server with address %s and port %d: %s", serverIp, serverPort, ex.getMessage()));
        }
    }

    /**
     * Tries to receive message from {@link UdpServer}
     * @return  Received message
     */
    public String receiveData() {
        try {
            DatagramPacket receivePacket = new DatagramPacket(receiveData, receiveData.length);
            clientSocket.receive(receivePacket);

            return new String(receivePacket.getData());

        } catch (IOException ex) {
            log.error(String.format("Couldn't receive data from server: %s", ex.getMessage()));
        }

        return null;
    }

    public void stopClient() {
        clientSocket.close();
    }

    public static void main(String args[]) throws Exception {

        InetAddress serverIp = InetAddress.getByName("localhost");
        int serverPort = 4545;
        String message = "Hello Server!";

        UdpClient client = new UdpClient();

        System.out.println("Sending message to server with ip " + serverIp + " and port " + serverPort);
        client.sendData(message, serverIp, serverPort);

        System.out.println("Received message: " + client.receiveData());

        client.stopClient();
    }
}
