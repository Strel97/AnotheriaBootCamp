package net.anotheria.strel.fts.requests;

import net.anotheria.strel.fts.communication.Request;
import net.anotheria.strel.fts.communication.Response;

/**
 * Represents special handler for each command / request
 * in {@link net.anotheria.strel.fts.FileTransferServer}
 *
 * @author Strel97
 */
public interface RequestHandler {
    Response handle(Request request);
}
