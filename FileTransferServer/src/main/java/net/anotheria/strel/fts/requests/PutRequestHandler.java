package net.anotheria.strel.fts.requests;

import net.anotheria.strel.fts.FileTransferServer;
import net.anotheria.strel.fts.communication.Request;
import net.anotheria.strel.fts.communication.Response;
import org.apache.commons.io.IOUtils;

import java.io.*;

/**
 * Handler for PUT command. Tries to download file from {@link net.anotheria.strel.fts.Client} into
 * server file directory and returns result of operation in {@link Response} object.
 *
 * @author Strel97
 */
public class PutRequestHandler implements RequestHandler {

    private FileTransferServer server;
    private InputStream in;


    public PutRequestHandler(FileTransferServer server, InputStream in) {
        this.server = server;
        this.in = in;
    }

    @Override
    public Response handle(Request request) {
        try {
            if (in.available() <= 0)
                return new Response("Can't receive file");

            File file = new File(server.getFilesDirName() + "/" + request.getArgument());
            OutputStream fOut = new FileOutputStream(file);

            byte[] data = new byte[in.available()];
            IOUtils.readFully(in, data);

            fOut.write(data);
            fOut.flush();

            fOut.close();

            return new Response("File " + file.getName() + " was uploaded on server");
        }
        catch (IOException ex) {
            System.err.println("Can't download from client.");
        }

        return new Response("File upload failed");
    }
}
