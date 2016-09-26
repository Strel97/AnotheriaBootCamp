package net.anotheria.strel.fts.requests;


import net.anotheria.strel.fts.FileTransferServer;
import net.anotheria.strel.fts.communication.Request;
import net.anotheria.strel.fts.communication.Response;

/**
 * Handler for DIR command. Returns contents of server
 * file directory in {@link Response} object.
 *
 * @author Strel97
 */
public class DirRequestHandler implements RequestHandler {

    private FileTransferServer server;


    public DirRequestHandler(FileTransferServer server) {
        this.server = server;
    }

    @Override
    public Response handle(Request request) {
        return new Response(server.dir());
    }
}
