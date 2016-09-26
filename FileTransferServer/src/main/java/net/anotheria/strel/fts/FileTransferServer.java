package net.anotheria.strel.fts;

import org.apache.log4j.BasicConfigurator;
import org.apache.log4j.Logger;

import java.io.File;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * File transfer server is implementation of server application that
 * can manage requests from variety of client applications.
 *
 * User can make requests to server in form of console commands.
 * Allowed commands are:
 * - DIR                - prints the content of server file directory.
 * - GET <file_name>    - gets specified file from server file directory and
 *                        sends it to user.
 * - PUT <file_name>    - uploads a file with given name to the server.
 *
 * @author Strel97
 */
public class FileTransferServer {

    /**
     * Server file directory name, where all uploaded and downloaded files
     * are stored
     */
    private static final String FILE_DIR_NAME = "FILES";

    /**
     * Request socket used for receiving command from client
     * and sending response
     */
    private ServerSocket commandSocket;

    /**
     * Data socket used for transmitting files
     */
    private ServerSocket dataTransferSocket;

    /**
     * Directory that contains all uploaded and requested
     * files
     */
    private File fileDir;

    /**
     * Logger used for writing all exceptions, faults and debug messages
     */
    private Logger log;


    /**
     * Creates new file transfer server application at given port for commands and
     * file transfer.
     *
     * @param cmdPort   Request port
     * @param filePort  File transfer port
     */
    public FileTransferServer(int cmdPort, int filePort) {

        log = Logger.getLogger(FileTransferServer.class.getName());

        try {
            commandSocket = new ServerSocket(cmdPort);
            dataTransferSocket = new ServerSocket(filePort);

            // Creating server file directory
            fileDir = new File(FILE_DIR_NAME);
            fileDir.mkdir();

            log.info(String.format("Starting server at port for commands [%d] and port for file [%d].", cmdPort, filePort));
        }
        catch (IOException ex) {
            log.fatal("File Transfer Server couldn't be started!");
        }
    }

    /**
     * Prints the contents of the server file directory.
     * @return  String that represents contents of server file directory
     */
    public String dir() {
        StringBuilder builder = new StringBuilder("Contents of " + fileDir.getName() + " directory: \n");
        for( String fName : fileDir.list() ) {
            builder.append(" -> ");
            builder.append(fName);
            builder.append("\n");
        }

        return builder.toString();
    }

    /**
     * Returns file with given name from server file directory.
     * @param fName File name
     * @return      File
     */
    public File getFile(String fName) {
        File file = new File(FILE_DIR_NAME + "/" + fName);
        if (file.exists() && file.isFile())
            return file;

        return null;
    }

    public String getFilesDirName() {
        return fileDir.getName();
    }

    /**
     * Starts file server
     */
    public void start() {
        while (true) {
            try {
                Socket clientCmdSocket = commandSocket.accept();
                Socket clientFileSocket = dataTransferSocket.accept();
                new Thread(new ClientHandler(clientCmdSocket, clientFileSocket, this)).start();
            }
            catch (IOException ex) {
                log.error("Can't handle client");
            }
        }
    }

    public static void main(String[] ar)    {
        // Set up a simple configuration that logs on the console.
        BasicConfigurator.configure();

        FileTransferServer server = new FileTransferServer(6666, 4545);

        System.out.printf("Starting server at ports [ %d, %d ]", 6666, 4545);
        server.start();
    }
}
