package net.anotheria.strel.basic.magicsquares;

import java.io.BufferedWriter;
import java.io.IOException;
import java.io.StreamCorruptedException;
import java.io.Writer;
import java.util.Collection;

/**
 * Used for printing collection of one-dimensional arrays as squares
 * of given dimension.
 *
 * @author Strel97
 */
public class MagicSquaresPrinter {

    private BufferedWriter out;


    public MagicSquaresPrinter(Writer out) throws IOException {
        if (out != null) {
            this.out = new BufferedWriter(out);
        }
        else {
            throw new StreamCorruptedException("Stream doesn't exists");
        }
    }


    /**
     * Represents all arrays from collection as squares with given dimensions
     * and prints them to the given output stream.
     *
     * @param squares       Collection of squares
     */
    public void print(Collection<MagicSquare> squares)
            throws IOException
    {
        for (MagicSquare square : squares) {
            printAsSquare(square);
        }
    }

    /**
     * Prints one dimensional array as square with given dimensions
     * to the given output stream.
     *
     * @param square    Magic square to present
     */
    private void printAsSquare(MagicSquare square)
            throws IOException
    {
        int[] arr = square.getNumbers();

        out.newLine();
        for (int i = 0; i < arr.length; i++) {
            if (i % square.getSize() == 0)
                out.newLine();

            out.write(arr[i] + " \t");
        }
        out.newLine();
        out.flush();
    }
}
