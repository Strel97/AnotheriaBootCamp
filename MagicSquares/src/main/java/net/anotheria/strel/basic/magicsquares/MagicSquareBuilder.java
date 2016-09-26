package net.anotheria.strel.basic.magicsquares;

import net.anotheria.strel.basic.magicsquares.util.Bound;
import net.anotheria.strel.basic.magicsquares.util.Sampler;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Strel97
 */
public class MagicSquareBuilder {

    /**
     * Stores numbers of magic square
     */
    private int[] square;

    /**
     * Dimension of magic square
     */
    private int dimensions;

    /**
     * Size of magic square or number of elements in square
     */
    private int totalNumbersCnt;

    private int magicConst;

    /**
     * Counter for tracking the number of elements added to magic square
     */
    private int numbersAdded;

    /**
     * Special object Sampler is responsible for storing and managing
     * the set of numbers that are not currently used in magic square.
     */
    private Sampler sampler;

    /**
     * Stores sum of elements in each column
     */
    private int[] columnSums;

    /**
     * Stores sum of elements in each row
     */
    private int[] rowSums;

    /**
     * Stores all possible magic squares for given dimensions
     */
    private List<MagicSquare> solutions;

    public static long insertions = 0;


    public MagicSquareBuilder(int dimensions) {
        this.dimensions = dimensions;
        square = new int[dimensions * dimensions];

        numbersAdded = 0;
        totalNumbersCnt = dimensions * dimensions;

        columnSums = new int[dimensions];
        rowSums = new int[dimensions];

        magicConst = dimensions * (totalNumbersCnt + 1) / 2;
    }

    /**
     * Builds all possible traditional magic squares for given dimensions
     * and returns it.
     *
     * @return All possible magic squares for given dimensions
     */
    public List<MagicSquare> buildMagicSquares() {
        solutions = new ArrayList<>();
        sampler = new Sampler(Bound.getAllElements(1, totalNumbersCnt));

        for (int possibleNumber : sampler.getNumbers(new Bound(1, totalNumbersCnt))) {
            if (sampler.contains(possibleNumber)) {
                buildRow(sampler.getNumber(possibleNumber), 0, 0);
            }
        }

        return solutions;
    }

    /**
     * @return Returns the number of squares fonded by algorithm.
     */
    public int getSquaresCount() {
        return solutions.size();
    }


    private void buildColumn(int number, int row, int column) {

        // Adding number
        addNumber(number, row, column);
        rowSums[row] += number;
        columnSums[column] += number;
        insertions++;

        if (isSquareCompleted() && checkMainDiagonal()) {
            solutions.add(new MagicSquare(dimensions, square));
        } else {
            if (isColumnComplete(column)) {
                if (column + 1 == dimensions - 1) {
                    int num = magicConst - rowSums[row];
                    if (sampler.contains(num)) {
                        buildRow(sampler.getNumber(num), row, column + 1);
                    }
                } else {
                    for (int possibleNum : sampler.getNumbers(new Bound(1, totalNumbersCnt))) {
                        if (sampler.contains(possibleNum)) {
                            buildRow(sampler.getNumber(possibleNum), column + 1, column + 1);
                        }
                    }
                }
            } else {
                if (row + 1 == dimensions - 1) {
                    // Simply calculate it
                    int num = magicConst - columnSums[column];
                    if (sampler.contains(num)) {
                        buildColumn(sampler.getNumber(num), row + 1, column);
                    }
                } else {
                    for (int possibleNum : sampler.getNumbers(new Bound(1, totalNumbersCnt))) {
                        if (sampler.contains(possibleNum)) {
                            buildColumn(sampler.getNumber(possibleNum), row + 1, column);
                        }
                    }
                }
            }
        }

        // Undo move
        sampler.returnNumber(number);

        rowSums[row] -= number;
        columnSums[column] -= number;

        undoMove(row, column);
    }


    private void buildRow(int number, int row, int column) {

        // Adding number
        addNumber(number, row, column);
        rowSums[row] += number;
        columnSums[column] += number;
        insertions++;

        if (isSquareCompleted() && checkMainDiagonal()) {
            solutions.add(new MagicSquare(dimensions, square));
        } else {
            if (isSecondDiagonal(row, column) && !checkSecondaryDiagonal()) {

            } else {
                if (isRowComplete(row)) {
                    if (row + 1 == dimensions - 1) {
                        int num = magicConst - columnSums[column - 1];
                        if (sampler.contains(num)) {
                            buildColumn(sampler.getNumber(num), row + 1, column - 1);
                        }
                    } else {
                        for (int possibleNum : sampler.getNumbers(new Bound(1, totalNumbersCnt))) {
                            if (sampler.contains(possibleNum)) {
                                buildColumn(sampler.getNumber(possibleNum), row + 1, row);
                            }
                        }
                    }
                } else {
                    if (column + 1 == dimensions - 1) {
                        // Simply calculate it
                        int num = magicConst - rowSums[row];
                        if (sampler.contains(num)) {
                            buildRow(sampler.getNumber(num), row, column + 1);
                        }
                    } else {
                        for (int possibleNum : sampler.getNumbers(new Bound(1, totalNumbersCnt))) {
                            if (sampler.contains(possibleNum)) {
                                buildRow(sampler.getNumber(possibleNum), row, column + 1);
                            }
                        }
                    }
                }
            }
        }

        // Undo move
        sampler.returnNumber(number);

        rowSums[row] -= number;
        columnSums[column] -= number;

        undoMove(row, column);
    }

    private boolean isSecondDiagonal(int row, int column) {
        return row == dimensions / 2 && column == dimensions / 2;
    }

    private boolean isRowComplete(int row) {
        return rowSums[row] == magicConst;
    }

    private boolean isColumnComplete(int column) {
        return columnSums[column] == magicConst;
    }


    /**
     * Checks the sum of elements on secondary diagonal of magic square.
     * It should be equal to magic constant to consider square as magic square.
     * Secondary diagonal is diagonal that goes from left bottom to right top of square
     * positions (1, n); (2, n - 1) ... and so on.
     *
     * @return Whether the sum of elements on secondary diagonal is equal to magic constant
     */
    private boolean checkSecondaryDiagonal() {
        int diagonalSum = 0;
        for (int i = 0; i < dimensions; i++) {
            diagonalSum += square[(dimensions - 1) * i + dimensions - 1];
        }

        return diagonalSum == magicConst;
    }


    /**
     * Checks the sum of elements on main diagonal of magic square.
     * It should be equal to magic constant to consider square as magic square.
     * Main diagonal is diagonal that goes from left top to right bottom of square
     * positions (1, 1); (2, 2) ... and so on.
     *
     * @return Whether the sum of elements on main diagonal is equal to magic constant
     */
    private boolean checkMainDiagonal() {
        int diagonalSum = 0;
        for (int i = 0; i < dimensions; i++) {
            diagonalSum += square[i + dimensions * i];
        }

        return diagonalSum == magicConst;
    }


    /**
     * Removes last added number from the square.
     */
    private void undoMove(int row, int column) {
        numbersAdded--;
        square[row * dimensions + column] = 0;
    }


    /**
     * Adds number to the square at the first empty position.
     *
     * @param num Number to add
     */
    private void addNumber(int num, int row, int column) {
        numbersAdded++;
        square[row * dimensions + column] = num;
    }


    /**
     * @return Determines, whether square is fulfilled by numbers
     */
    private boolean isSquareCompleted() {
        return numbersAdded == totalNumbersCnt;
    }
}
