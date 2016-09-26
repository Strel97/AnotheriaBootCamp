package net.anotheria.strel.rest;


import net.anotheria.strel.basic.magicsquares.MagicSquare;
import net.anotheria.strel.basic.magicsquares.MagicSquareBuilder;
import net.anotheria.strel.hibernate.magicsquares.MagicSquaresManager;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import java.util.List;

/**
 * Root resource (exposed at "magic" path)
 */
@Path("magic")
public class MagicSquaresResource {

    private MagicSquaresManager squaresManager = new MagicSquaresManager();


    @GET
    @Path("get")
    @Produces(MediaType.APPLICATION_XML)
    public List<MagicSquare> getAllMagicSquares() {
        return squaresManager.getAllSquares();
    }

    @GET
    @Path("get/{id}")
    @Produces(MediaType.APPLICATION_XML)
    public MagicSquare getMagicSquareById(@PathParam("id") int id) {
        return squaresManager.getSquareById(id);
    }

    @DELETE
    @Path("delete")
    public void deleteAllMagicSquares() {
        squaresManager.deleteSquares();
    }

    @DELETE
    @Path("delete/{id}")
    public void deleteMagicSquareById(@PathParam("id") int id) {
        squaresManager.deleteSquareById(id);
    }

    @GET
    @Path("create/{size}")
    @Produces(MediaType.APPLICATION_XML)
    public List<MagicSquare> createMagicSquares(@PathParam("size") int size) {
        List<MagicSquare> squares = new MagicSquareBuilder(size).buildMagicSquares();
        squaresManager.saveAllSquares(squares);

        return squares;
    }

    @GET
    @Path("search/{pattern}")
    @Produces(MediaType.APPLICATION_XML)
    public List<MagicSquare> findMagicSquares(@PathParam("pattern") String pattern) {
        return squaresManager.findSquares(pattern);
    }
}
