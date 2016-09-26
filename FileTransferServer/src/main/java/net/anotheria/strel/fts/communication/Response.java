package net.anotheria.strel.fts.communication;

import java.io.Serializable;

/**
 * Represents response from {@link net.anotheria.strel.fts.FileTransferServer}
 * to {@link net.anotheria.strel.fts.Client}.
 *
 * @author Strel97
 */
public class Response implements Serializable {

    /**
     * Response message
     */
    private String content;


    public Response(String content) {
        this.content = content;
    }

    public String getContent() {
        return content;
    }

    @Override
    public String toString() {
        return content;
    }
}
