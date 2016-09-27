package net.anotheria.strel.rest;


import net.anotheria.moskito.aop.annotation.Monitor;
import net.anotheria.strel.basic.magicsquares.MagicSquare;
import net.anotheria.strel.basic.magicsquares.MagicSquareBuilder;
import net.anotheria.strel.hibernate.magicsquares.MagicSquaresManager;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import java.util.List;

/**
 * Root resource (exposed at "magic" path)
 */
@Monitor
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

    @GET
    @Path("delete")
    @Produces(MediaType.TEXT_PLAIN)
    public String deleteAllMagicSquares() {
        int deletedSquares = squaresManager.deleteAllSquares();
        return String.format("Successfully deleted %d squares", deletedSquares);
    }

    @GET
    @Path("delete/{id}")
    @Produces(MediaType.TEXT_PLAIN)
    public String deleteMagicSquareById(@PathParam("id") int id) {
        squaresManager.deleteSquareById(id);
        return String.format("Square with id=%d was deleted from database", id);
    }

    @GET
    @Path("create/{size}")
    @Produces(MediaType.APPLICATION_XML)
    public List<MagicSquare> createMagicSquares(@PathParam("size") int size) {
        List<MagicSquare> squares = new MagicSquareBuilder(size).buildMagicSquares();
        squaresManager.saveSquares(squares);

        return squares;
    }

    @GET
    @Path("update/{size}")
    @Produces(MediaType.APPLICATION_XML)
    public List<MagicSquare> updateMagicSquares(@PathParam("size") int size) {
        deleteAllMagicSquares();
        return createMagicSquares(size);
    }

    @GET
    @Path("search/{pattern}")
    @Produces(MediaType.APPLICATION_XML)
    public List<MagicSquare> findMagicSquares(@PathParam("pattern") String pattern) {
        return squaresManager.findSquares(pattern);
    }
}
