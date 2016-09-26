package net.anotheria.strel.fts;

import net.anotheria.strel.fts.communication.Request;
import net.anotheria.strel.fts.communication.Response;
import org.apache.commons.io.IOUtils;
import org.apache.log4j.BasicConfigurator;
import org.apache.log4j.Logger;

import java.io.*;
import java.net.InetAddress;
import java.net.Socket;
import java.nio.file.Files;
import java.util.Scanner;

/**
 * Represents client that connects to file transfer server and make requests
 * using command shell.
 *
 * @author Strel97
 */
public class Client {

    private Socket cmdSocket;
    private Socket fileSocket;

    /**
     * Input stream used for receiving commands from client
     */
    private ObjectInputStream cmdInStream;

    /**
     * Output stream used for sending response (result of operations)
     * to client
     */
    private ObjectOutputStream cmdOutStream;


    /**
     * Input stream used for receiving file data from client
     */
    private InputStream fileInStream;

    /**
     * Output stream used for sending file data to the client
     */
    private OutputStream fileOutStream;

    private Logger log;


    public Client() {
        log = Logger.getLogger(Client.class.getName());
    }

    /**
     * Tries to connect to server with given ip, command and file transfer port
     * of server.
     *
     * @param ipAddress Server IP
     * @param cmdPort   Server command port
     * @param filePort  Server file port
     * @return          Result of operation
     */
    public boolean connect(String ipAddress, int cmdPort, int filePort) {
        try {
            cmdSocket = new Socket(InetAddress.getByName(ipAddress), cmdPort);
            fileSocket = new Socket(InetAddress.getByName(ipAddress), filePort);

            cmdOutStream = new ObjectOutputStream(cmdSocket.getOutputStream());
            cmdInStream = new ObjectInputStream(cmdSocket.getInputStream());

            fileInStream = fileSocket.getInputStream();
            fileOutStream = fileSocket.getOutputStream();

            return true;
        }
        catch (IOException ex) {
            log.error(String.format("Couldn't connect to server with ip %s and ports [%d, %d]", ipAddress, cmdPort, filePort));
        }

        return false;
    }

    /**
     * Receives file from server and saves it in 'CLIENT' directory.
     * @param fileName  File to download
     */
    private void receiveFile(String fileName) {
        try {
            if (fileInStream.available() <= 0)
                return;

            // Reading from file stream if server has sent anything
            OutputStream fOut = new FileOutputStream(fileName);

            byte[] data = new byte[fileInStream.available()];
            IOUtils.readFully(fileInStream, data);

            fOut.write(data);
            fOut.flush();

            fOut.close();
        }
        catch (IOException ex) {
            log.error(String.format("Can't receive file %s", fileName));
        }
    }

    /**
     * Sends file with given name to server.
     * @param fileName  File to upload
     */
    private void sendFile(String fileName) {
        try {
            File file = new File(fileName);
            if (file.exists()) {
                byte[] data = Files.readAllBytes(file.toPath());
                fileOutStream.write(data, 0, data.length);
                fileOutStream.flush();
            }
        }
        catch (IOException ex) {
            log.error(String.format("Can't send file %s", fileName));
        }
    }

    /**
     * Gets response from server in form of {@link net.anotheria.strel.fts.communication.Response} object.
     *
     * @return  Server response
     * @see net.anotheria.strel.fts.communication.Response
     */
    private Response getResponse() {
        try {
            return (Response) cmdInStream.readObject();
        }
        catch (IOException ex) {
            log.error("Can't get response from server");
        }
        catch (ClassNotFoundException ex) {
            log.error("Class of response object not found");
        }

        return null;
    }

    /**
     * Sends command to server
     * @param cmd   Request
     */
    public void sendCommand(Request cmd) {
        try {
            cmdOutStream.writeObject(cmd);
            cmdOutStream.flush();
        }
        catch (IOException ex) {
            log.error("Couldn't send request to server");
        }
    }

    public static void main(String[] args) {
        // Set up a simple configuration that logs on the console.
        BasicConfigurator.configure();

        Client client = new Client();
        if (!client.connect("127.0.0.1", 6666, 4545))
            return;

        Scanner in = new Scanner(System.in);

        String cmd = "";
        while (!"EXIT".equalsIgnoreCase(cmd)) {
            System.out.print("Type your command: ");
            cmd = in.nextLine();

            String[] command = cmd.split(" ");

            // Sending request with or without argument
            if (command.length > 1)
                client.sendCommand(new Request(command[0], command[1]));
            else
                client.sendCommand(new Request(cmd));

            if (cmd.matches("GET .*")) {
                client.receiveFile( command[1] );
            }
            else if (cmd.matches("PUT .*")) {
                client.sendFile( command[1] );
            }

            // Server sends response in each case, whether request was incorrect
            // or correct, so we omit response checking and simply outputting it
            System.out.println( client.getResponse() );
            System.out.println();
        }
    }
}
