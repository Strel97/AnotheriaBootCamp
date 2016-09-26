package net.anotheria.strel.echo;

/**
 * @author Strel97
 */
public class EchoServiceImpl implements EchoService {
    @Override
    public String echoMessage(String message) {
        return new StringBuilder(message).reverse().toString();
    }
}
