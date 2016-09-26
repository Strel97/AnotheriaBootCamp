package net.anotheria.strel.rmi;

import org.apache.log4j.BasicConfigurator;
import org.apache.log4j.Logger;

import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.util.Scanner;

/**
 * Client application that make requests to {@link EchoService} and outputs
 * results of service processing.
 *
 * @author Strel97
 */
public class EchoClient {

    private static Logger log = Logger.getLogger(EchoClient.class);


    public static void main(String[] args) {
        BasicConfigurator.configure();

        if (args.length < 2) {
            log.error("Please specify two parameters server ip address and " +
                    "server port during client launching. Usage: java EchoClient <server_ip> <server_port>");
            System.exit(1);
        }

        try {
            Registry registry = LocateRegistry.getRegistry(args[0], Integer.valueOf(args[1]));
            EchoService service = (EchoService) registry.lookup("com.anotheria.strel.rmi.EchoService");

            Scanner in = new Scanner(System.in);
            while (true) {
                System.out.print("Please type in string to process with EchoService: ");
                System.out.println("Service responded: " + service.echo( in.nextLine() ));
            }
        }
        catch (RemoteException ex) {
            log.fatal("Remote exception: " + ex.getMessage());
        }
        catch (NotBoundException ex) {
            log.fatal("Service with given name is not bound: " + ex.getMessage());
        }
    }
}
