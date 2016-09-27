package net.anotheria.strel.hibernate.magicsquares;


import net.anotheria.strel.basic.magicsquares.MagicSquare;
import net.anotheria.strel.basic.magicsquares.MagicSquareBuilder;

import java.util.List;

/**
 * @author Strel97
 */
public class HibernateLauncher {

    public static void main(String[] args) {
        MagicSquaresManager manager = new MagicSquaresManager();
        MagicSquareBuilder builder = new MagicSquareBuilder(3);

        // Saving solutions
        List<MagicSquare> solutions = builder.buildMagicSquares();
        for (MagicSquare solution : solutions) {
            manager.saveSquare(solution);
        }

        // Retrieving first square (square with id 1)
        MagicSquare firstSquare = manager.getSquareById(1);
        System.out.printf("Square with id = 1: %s", firstSquare);

        // Deleting first square (square with id 1)
        manager.deleteSquareById(1);

        //Finding square by pattern
        for (MagicSquare square : manager.findSquares("2")) {
            System.out.printf("Found square: %s\n", square);
        }

        // Retrieving solutions and printing them
        List<MagicSquare> squares = manager.getAllSquares();
        for (MagicSquare square : squares) {
            System.out.println(square.getSquare());
        }

        // Deleting solutions
        manager.deleteAllSquares();

        manager.stop();
    }
}
