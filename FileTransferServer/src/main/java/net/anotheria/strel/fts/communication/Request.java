package net.anotheria.strel.fts.communication;


import java.io.Serializable;

/**
 * Represents request for {@link net.anotheria.strel.fts.FileTransferServer}.
 * As we have only one argument in commands for server, request supports only one
 * argument.
 *
 * @author Strel97
 */
public class Request implements Serializable {

    /**
     * Command for server
     */
    private String      content;
    private String      argument;


    public Request(String content) {
        this.content = content;
    }

    public Request(String content, String argument) {
        this.content = content;
        this.argument = argument;
    }

    public String getArgument() {
        return argument;
    }

    public String getContent() {
        return content;
    }
}
