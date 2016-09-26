package net.anotheria.strel.rest;

import net.anotheria.strel.basic.magicsquares.MagicSquare;
import org.glassfish.grizzly.http.server.HttpServer;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.GenericType;

import java.util.List;

import static org.junit.Assert.assertEquals;

public class MagicSquaresResourceTest {

    private HttpServer server;
    private WebTarget target;


    @Before
    public void setUp() throws Exception {
        // start the server
        server = Main.startServer();
        // create the client
        Client c = ClientBuilder.newClient();

        // uncomment the following line if you want to enable
        // support for JSON in the client (you also have to uncomment
        // dependency on jersey-media-json module in pom.xml and Main.startServer())
        // --
        // c.configuration().enable(new org.glassfish.jersey.media.json.JsonJaxbFeature());

        target = c.target(Main.BASE_URI);
    }

    @After
    public void tearDown() throws Exception {
        server.stop();
    }

    /**
     * Test to see that the message "Got it!" is sent in the response.
     */
    @Test
    public void testGetIt() {
        String responseMsg = target.path("myresource").request().get(String.class);
        assertEquals("Got it!", responseMsg);
    }

    @Test
    public void getMagicSquares() {
        List<MagicSquare> squares = target.path("magicsquare").request().get(new GenericType<List<MagicSquare>>() {});
        System.out.println(squares);
    }

    @Test
    public void createMagicSquares() {

    }
}