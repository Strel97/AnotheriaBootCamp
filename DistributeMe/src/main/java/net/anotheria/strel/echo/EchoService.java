package net.anotheria.strel.echo;

import net.anotheria.anoprise.metafactory.Service;
import org.distributeme.annotation.DistributeMe;

/**
 * @author Strel97
 */
@DistributeMe
public interface EchoService extends Service {
    String echoMessage(String message);
}
