package net.anotheria.strel.basics;

import org.apache.log4j.Logger;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;

/**
 * UDP server implementation. Waits for {@link UdpClient} to
 * connect with, receives message from client, processes it
 * and sends back.
 *
 * @author Strel97
 */
public class UdpServer {

    private DatagramSocket serverSocket;
    private byte[] receiveData = new byte[1024];
    private byte[] sendData = new byte[1024];

    private Logger log = Logger.getLogger(UdpServer.class.getName());


    public UdpServer(int port) {
        try {
            serverSocket = new DatagramSocket(port);
        } catch (SocketException ex) {
            log.fatal(String.format("Couldn't create server at port %d: %s", port, ex.getMessage()));
        }
    }

    /**
     * Simply makes all letters in upper case.
     * @param message   Message to process
     * @return          Processed message
     */
    private String processMessage(String message) {
        return message.toUpperCase();
    }

    public void start() {
        while (true) {
            try {
                DatagramPacket clientPacket = new DatagramPacket(receiveData, receiveData.length);
                serverSocket.receive(clientPacket);

                String clientData = new String(clientPacket.getData());
                InetAddress clientIp = clientPacket.getAddress();
                int clientPort = clientPacket.getPort();

                System.out.println("Received from client with ip " + clientIp + " and port " + clientPort + ": " + clientData);
                System.out.println("Sending client's data back");

                sendData = processMessage(clientData).getBytes();
                DatagramPacket sendPacket = new DatagramPacket(sendData, sendData.length, clientIp, clientPort);
                serverSocket.send(sendPacket);

            } catch (IOException ex) {
                log.error("Couldn't send or receive message from client: " + ex.getMessage());
            }
        }
    }

    public static void main(String[] args) {
        UdpServer server = new UdpServer(4545);
        server.start();
    }
}
