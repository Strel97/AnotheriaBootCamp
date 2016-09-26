package net.anotheria.strel.fts.requests;


import net.anotheria.strel.fts.FileTransferServer;
import net.anotheria.strel.fts.communication.Request;
import net.anotheria.strel.fts.communication.Response;

import java.io.File;
import java.io.IOException;
import java.io.OutputStream;
import java.nio.file.Files;

/**
 * Handler for GET command. Sends file to {@link net.anotheria.strel.fts.Client} and
 * returns result of operation in {@link Response} object.
 *
 * @author Strel97
 */
public class GetRequestHandler implements RequestHandler {

    private FileTransferServer server;
    private OutputStream        out;


    public GetRequestHandler(FileTransferServer server, OutputStream out) {
        this.server = server;
        this.out = out;
    }

    @Override
    public Response handle(Request request) {
        String fileName = request.getArgument();
        File file = server.getFile(fileName);

        if (file != null) {
            try {
                byte[] fData = Files.readAllBytes(file.toPath());
                out.write(fData, 0, fData.length);
                out.flush();

                return new Response("File " + fileName + " was sent");
            }
            catch (IOException ex) {
                System.err.println("Can't read file " + file.getName());
            }
        }

        return new Response("File download failed");
    }
}
