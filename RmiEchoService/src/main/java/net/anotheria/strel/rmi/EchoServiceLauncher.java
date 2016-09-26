package net.anotheria.strel.rmi;

import org.apache.log4j.BasicConfigurator;
import org.apache.log4j.Logger;

import java.rmi.AlreadyBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;

/**
 * Launches echo service and binds it to specified port and name so
 * it can be accessed remotely.
 *
 * @author Strel97
 */
public class EchoServiceLauncher {

    private static Logger log = Logger.getLogger(EchoServiceLauncher.class);


    /**
     * Prepares echo service for requests from client.
     * @param port  Service port
     */
    public void launch(int port) {
        try {
            Registry registry = LocateRegistry.createRegistry( port );

            EchoService service = new ReversedEchoServiceImpl();
            EchoService stub = (EchoService) UnicastRemoteObject.exportObject(service, 0);

            registry.bind("com.anotheria.strel.rmi.EchoService", stub);

            log.info(String.format("Service is now accessible on port %d with name com.anotheria.strel.rmi.EchoService", port));
        }
        catch (RemoteException ex) {
            log.fatal("Remote Exception: " + ex.getMessage());
        }
        catch (AlreadyBoundException ex) {
            log.fatal("Echo service is already bound with given name: " + ex.getMessage());
        }
    }

    public static void main(String[] args) {
        // Set up a simple configuration that logs on the console.
        BasicConfigurator.configure();

        if (args.length < 1) {
            log.error("Please specify port number for Echo service");
            System.exit(1);
        }

        EchoServiceLauncher echoService = new EchoServiceLauncher();
        echoService.launch( Integer.valueOf(args[0]) );
    }
}
