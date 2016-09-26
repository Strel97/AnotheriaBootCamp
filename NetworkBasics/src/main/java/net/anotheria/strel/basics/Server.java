package net.anotheria.strel.basics;

import org.apache.log4j.BasicConfigurator;
import org.apache.log4j.Logger;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * Simple server application waits for client to connect with,
 * receives byte array from him and writes it to the text file.
 *
 * @author Strel97
 */
public class Server {

    private ServerSocket serverSocket;

    private InputStream in;
    private OutputStream out;

    private Logger log = Logger.getLogger(Server.class.getName());


    /**
     * Creates server at given port
     * @param port  Port of server
     */
    public Server(int port) {
        try {
            serverSocket = new ServerSocket(port);
        }
        catch (IOException ex) {
            log.fatal(String.format("Could not create server at port %d", port));
        }
    }

    /**
     * Starts server. Server waits for client and receives file from him.
     */
    public void start() {
        try {
            Socket socket = serverSocket.accept();

            in = socket.getInputStream();
            out = socket.getOutputStream();

            OutputStream fOut = new FileOutputStream("FILES/SERVER_FILE.txt");

            int c = 0;
            byte[] buffer = new byte[1024];
            while((c = in.read(buffer, 0, buffer.length)) > 0) {
                fOut.write(buffer, 0, buffer.length);
                fOut.flush();
            }

            fOut.close();

            in.close();
            out.close();
        }
        catch (IOException ex) {
            log.error("Can't communicate with client: " + ex.getMessage());
        }
    }

    public static void main(String[] args) {
        // Set up a simple configuration that logs on the console.
        BasicConfigurator.configure();

        Server server = new Server(6666);

        System.out.println("Starting server at port " + 6666);
        server.start();
    }
}
