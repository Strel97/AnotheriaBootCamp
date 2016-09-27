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
        server = GrizzlyServerLauncher.startServer();
        // create the client
        Client c = ClientBuilder.newClient();

        // uncomment the following line if you want to enable
        // support for JSON in the client (you also have to uncomment
        // dependency on jersey-media-json module in pom.xml and GrizzlyServerLauncher.startServer())
        // --
        // c.configuration().enable(new org.glassfish.jersey.media.json.JsonJaxbFeature());

        target = c.target(GrizzlyServerLauncher.BASE_URI);
    }

    @After
    public void tearDown() throws Exception {
        server.stop();
    }

    @Test
    public void getMagicSquares() {

    }

    @Test
    public void createMagicSquares() {

    }
}
