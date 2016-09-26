package net.anotheria.strel.fts;

import net.anotheria.strel.fts.communication.Request;
import net.anotheria.strel.fts.communication.Response;
import net.anotheria.strel.fts.requests.DirRequestHandler;
import net.anotheria.strel.fts.requests.GetRequestHandler;
import net.anotheria.strel.fts.requests.PutRequestHandler;
import net.anotheria.strel.fts.requests.RequestHandler;
import org.apache.log4j.BasicConfigurator;
import org.apache.log4j.Logger;

import java.io.*;
import java.net.Socket;
import java.util.HashMap;
import java.util.Map;

/**
 * Client handler is special class used for processing requests from one client.
 * Server creates client handler for each client connected to the file server.
 *
 * @author Strel97
 */
public class ClientHandler implements Runnable {

    /**
     * Request port which is used for receiving and responding
     * client commands
     */
    private Socket cmdSocket;

    /**
     * File socket used for transmitting files between client and server
     */
    private Socket fileSocket;

    /**
     * Reference to file server
     */
    private FileTransferServer server;

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

    /**
     * Predefined handlers for each request supported
     * by {@link FileTransferServer}
     */
    private Map<String, RequestHandler> handlers;

    private Logger log;


    /**
     * Creates new handler for client with given sockets for communication.
     *
     * @param cmdSocket     Request socket
     * @param fileSocket    File socket
     * @param server        File transfer server
     */
    public ClientHandler(Socket cmdSocket, Socket fileSocket, FileTransferServer server) {
        // Set up a simple configuration that logs on the console.
        BasicConfigurator.configure();

        log = Logger.getLogger(ClientHandler.class.getName());

        this.cmdSocket = cmdSocket;
        this.fileSocket = fileSocket;
        this.server = server;

        prepareStreams();

        // Initialize handlers for each command
        handlers = new HashMap<>();
        handlers.put("DIR", new DirRequestHandler(server));
        handlers.put("GET", new GetRequestHandler(server, fileOutStream));
        handlers.put("PUT", new PutRequestHandler(server, fileInStream));
    }

    private void prepareStreams() {
        try {
            cmdOutStream = new ObjectOutputStream(cmdSocket.getOutputStream());
            cmdInStream = new ObjectInputStream(cmdSocket.getInputStream());

            fileInStream = fileSocket.getInputStream();
            fileOutStream = fileSocket.getOutputStream();
        }
        catch (IOException ex) {
            log.error("Can't initialize I/O streams");
        }
    }

    /**
     * Starts client handler. Handler begins to listen for
     * commands from client and responds to it.
     */
    @Override
    public void run() {
        try {
            Request command = (Request) cmdInStream.readObject();
            while (!"EXIT".equalsIgnoreCase(command.getContent())) {
                log.info(String.format("Received command: %s", command.getContent()));

                // Searching for appropriate handler for received request
                RequestHandler request = handlers.get(command.getContent());
                if (request != null) {
                    // Processing request and writing response
                    Response response = request.handle(command);
                    cmdOutStream.writeObject(response);
                }
                else {
                    cmdOutStream.writeObject(new Response("Incorrect request. Please try again."));
                }

                // Read next command
                command = (Request) cmdInStream.readObject();
            }

            cmdInStream.close();
            cmdOutStream.close();

            fileInStream.close();
            fileOutStream.close();

            cmdSocket.close();
            fileSocket.close();
        }
        catch (ClassNotFoundException ex) {
            log.error("Class can't be found! " + ex.getMessage());
        }
        catch (IOException ex) {
            log.error(ex.getMessage());
        }
    }
}
