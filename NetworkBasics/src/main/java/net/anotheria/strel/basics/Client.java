package net.anotheria.strel.basics;

import org.apache.log4j.BasicConfigurator;
import org.apache.log4j.Logger;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.InetAddress;
import java.net.Socket;
import java.nio.file.Files;

/**
 * Simple client application. Client connects to server
 * with given ip and port and tries to upload file to the
 * server.
 *
 * @author Strel97
 */
public class Client {

    private Socket socket;
    private InputStream in;
    private OutputStream out;

    private Logger log = Logger.getLogger(Client.class.getName());


    /**
     * Connects client to server application with given ip and port
     * and prepares input / output streams for further communicating
     * with server.
     *
     * @param ipAddress     IP address of server
     * @param serverPort    Server port
     * @return              Result of operation
     */
    public boolean connect(String ipAddress, int serverPort) {
        try {
            socket = new Socket(InetAddress.getByName(ipAddress), serverPort);
            prepareStreams();

            return true;
        }
        catch (IOException ex) {
            log.error(String.format("Couldn't connect to server with ip %s and port #%d", ipAddress, serverPort));
        }

        return false;
    }

    /**
     * Prepares input / output streams after connection with server.
     * @throws IOException
     */
    private void prepareStreams() throws IOException {
        in = socket.getInputStream();
        out = socket.getOutputStream();
    }

    /**
     * Tries to upload file with given path to the server. File is
     * transmitted as byte array.
     *
     * @param path  Path to the file
     * @return      Result of operation
     */
    public boolean uploadFile(String path) {
        File file = new File(path);
        if (file.exists()) {
            try {
                byte[] data = Files.readAllBytes(file.toPath());
                out.write(data, 0, data.length);
                out.flush();

                out.close();
                in.close();

                return true;
            }
            catch (IOException ex) {
                log.error(String.format("Can't read from file %s", file.getName()));
            }
        }

        return false;
    }


    /**
     * Creates client and tries to upload file given
     * in parameters.
     *
     * @param args  First argument is file path
     */
    public static void main(String[] args) {
        // Set up a simple configuration that logs on the console.
        BasicConfigurator.configure();

        Client client = new Client();
        client.connect("127.0.0.1", 6666);

        if (args.length > 0) {
            System.out.println("Sending file: " + args[0]);
            client.uploadFile(args[0]);
        }
    }
}
