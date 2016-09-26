package net.anotheria.strel.rmi;

import org.apache.log4j.Logger;

import java.rmi.RemoteException;

/**
 * Concrete implementation of {@link EchoService} that reverses string
 * received from client.
 *
 * @author Strel97
 */
public class ReversedEchoServiceImpl implements EchoService {

    private static Logger log = Logger.getLogger(ReversedEchoServiceImpl.class);


    /**
     * Reverses string received from client.
     *
     * @param source    Initial string
     * @return          Reversed string
     * @throws RemoteException
     */
    @Override
    public String echo(String source) throws RemoteException {
        log.info(String.format("Client has sent request: %s", source));
        return new StringBuilder(source).reverse().toString();
    }
}
