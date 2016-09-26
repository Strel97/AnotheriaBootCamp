package net.anotheria.strel.basic.magicsquares;

import java.io.BufferedWriter;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;

/**
 * Launches magic square builder and prints results to the text file.
 * @author Strel97
 */
public class MagicSquareBuilderLauncher {
    public static void main(String[] args) {

        if (args.length <= 0) {
            System.err.println("Please specify the dimensions of square in arguments before launching.");
            System.exit(1);
        }

        long startTime = System.nanoTime();
        int dimensions = Integer.parseInt(args[0]);

        MagicSquareBuilder square = new MagicSquareBuilder(dimensions);
        List<MagicSquare> squares = square.buildMagicSquares();

        long timeElapsed = System.nanoTime() - startTime;
        System.out.printf("Found %d squares for %f minutes or %f milliseconds, insertions made %d\n", square.getSquaresCount(), 1.67 * Math.pow(10, -11) * timeElapsed, Math.pow(10, -6) * timeElapsed, MagicSquareBuilder.insertions);


        try (BufferedWriter fOut = new BufferedWriter(new FileWriter("MagicSquares.txt"))) {
            MagicSquaresPrinter printer = new MagicSquaresPrinter(fOut);
            printer.print(squares);
        }
        catch (FileNotFoundException ex) {
            System.err.println("File can't be found");
        }
        catch (IOException ex) {
            ex.printStackTrace();
        }
    }
}
