package net.anotheria.strel.rmi;

import java.rmi.Remote;
import java.rmi.RemoteException;

/**
 * Interface for Echo RMI service. Contains all methods that can be
 * called remotely by client application.
 *
 * @author Strel97
 */
public interface EchoService extends Remote {

    /**
     * Returns string that was on input. Each concrete implementation
     * of com.anotheria.strel.rmi.EchoService can manipulate initial string.
     *
     * @param source    Initial string
     * @return          The same string
     * @throws RemoteException
     */
    String echo(String source) throws RemoteException;
}
