package net.anotheria.strel.echo;

import org.distributeme.core.ServiceLocator;

/**
 * @author Strel97
 */
public class RemoteClient {
    public static void main(String[] args) {
        EchoService service = ServiceLocator.getRemote(EchoService.class);
        System.out.println(service.echoMessage("Message in Blood"));
    }
}
