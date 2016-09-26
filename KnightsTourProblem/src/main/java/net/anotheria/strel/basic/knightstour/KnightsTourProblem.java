package net.anotheria.strel.basic.knightstour;

import java.awt.*;
import java.util.ArrayList;
import java.util.Collections;


/**
 * Used for solving Closed Knight's Tour problem. User specifies dimension of chessboard
 * and this class finds solution in form of two dimensional array.
 *
 * It uses selection algorithm with backtracking and some optimizations, which include
 * Warnsdorf rule, finding blocked or dead cells, using knight moves array and so on.
 *
 * @author Strel97
 * @version 1.%I%
 */
public class KnightsTourProblem {

    /**
     *  Stores 8 possible moves (or directions of moves) for knight.
     */
    private static final Point[] POSSIBLE_MOVES = {
            new Point(1, -2), new Point(2, -1), new Point(2, 1), new Point(1, 2),
            new Point(-1, 2), new Point(-2, 1), new Point(-2, -1), new Point(-1, -2)
    };

    /**
     * Width of chessboard
     */
    private int width;

    /**
     * Height of chessboard
     */
    private int height;

    /**
     * Represents chessboard. Stores knight moves in order of moving of knight
     * on the chessboard
     */
    private int[][] board;

    /**
     * Stores the number of steps made by knight. Is used in determining
     * the end of path finding.
     */
    private int stepsMade;

    private Point initialPosition;
    private Point endingPosition;


    /**
     * Creates Closed Knight's Tour problem solver on the chessboard of
     * size (width * height).
     *
     * @param width     Width of chessboard
     * @param height    Height of chessboard
     */
    public KnightsTourProblem(int width, int height) {
        this.width = width;
        this.height = height;

        board = new int[height][width];
        stepsMade = 0;
    }

    /**
     * Recursively finds solution for Knight's Tour beginning from point
     * startPos.
     *
     * @param startPos  Initial point of knight
     * @return          Whether solution is founded or not
     */
    public boolean findSolution(Point startPos) {

        addKnight(startPos);

        if (isSolved()) {
            endingPosition = startPos;
            return true;
        }

        for (Point move : getMovesInSortedOrder(startPos)) {
            if (findSolution(move)) {
                return true;
            }
        }

        undoMove(startPos);
        return false;
    }

    /**
     * Determines, whether the closed solution is possible for current initial parameters, i.e.
     * width, height and initial position of knight.
     *
     * Solution is impossible if the product of chessboard dimensions odd and sum of X and Y
     * coordinate of knight initial position is also odd.
     *
     * @param startPos  Initial position of knight
     * @return          Whether solution exists for given parameters
     */
    public boolean solutionPossible(Point startPos) {
        return ((width * height) % 2 == 0) && ((startPos.x + startPos.y) % 2 == 0);
    }

    /**
     * Returns all possible moves from given point in "Warnsdorf order", i.e. moves that
     * have minimal variants of further moves are in prior.
     *
     * This leads to algorithm process moves with minimal further move positions quicker
     * than other "less successful" moves.
     *
     * @param startPoint    Initial point of knight
     * @return              ArrayList of possible moves from given point in "Warnsdorf order"
     */
    private ArrayList<Point> getMovesInSortedOrder(Point startPoint) {
        ArrayList<Point> moves = findPossibleMoves(startPoint);
        Collections.sort(moves, (p1, p2) -> {
            return calculatePossibleMoves(p1) - calculatePossibleMoves(p2);
        });

        return moves;
    }

    /**
     * Calculates possible moves starting from given point.
     *
     * @param fromPoint Initial point of knight
     * @return          Number of possible moves from given point
     */
    private int calculatePossibleMoves(Point fromPoint) {
        return findPossibleMoves(fromPoint).size();
    }

    /**
     * Returns all possible moves from given point.
     *
     * @param startPoint    Initial point of knight
     * @return              ArrayList of possible moves from given point
     */
    private ArrayList<Point> findPossibleMoves(Point startPoint) {
        ArrayList<Point> possibleMoves = new ArrayList<>();
        for (Point direction : POSSIBLE_MOVES) {
            Point possibleMove = new Point(startPoint.x + direction.x, startPoint.y + direction.y);
            if (isMovePossible(possibleMove)) {
                possibleMoves.add(possibleMove);
            }
        }

        return possibleMoves;
    }

    /**
     * Clears knight's moves.
     */
    private void resetBoard() {
        board = new int[height][width];
        stepsMade = 0;
    }

    /**
     * Reverts last move done by the knight from given position.
     * @param currentPos    Position of knight to revert
     */
    private void undoMove(Point currentPos) {
        board[currentPos.y][currentPos.x] = 0;
        stepsMade--;
    }

    /**
     * Determines whether the move is possible to given point by checking
     * whether given position is in chessboard bounds and whether knight was
     * at given position or not.
     *
     * @param target    Target point of move
     * @return          Whether the move is possible
     */
    private boolean isMovePossible(Point target) {
        return checkPosition(target) && board[target.y][target.x] == 0;
    }

    /**
     * Marks position as traveled by knight.
     *
     * @param pos   Position to mark as traveled
     */
    private void addKnight(Point pos) {
        if (!checkPosition(pos))
            return;

        board[pos.y][pos.x] = ++stepsMade;
    }

    /**
     * Determines whether the Knight's Tour problem was solved.
     */
    private boolean isSolved() {
        return stepsMade >= width * height;
    }

    /**
     * Checks, whether given position is in chessboard bounds.
     *
     * @param pos   Position to test
     * @return      Whether given position is in chessboard bounds
     */
    private boolean checkPosition(Point pos) {
        return pos.x >= 0 && pos.y >= 0 && pos.x < width && pos.y < height;
    }

    /**
     * Determines whether current path is closed by comparing initial
     * and ending position of path.
     *
     * @return  Whether current path is closed
     */
    private boolean isClosedPath() {
        return (Math.abs(initialPosition.x - endingPosition.x) == 1  ||
                    Math.abs(initialPosition.x - endingPosition.x) == 2) &&
                        (Math.abs(initialPosition.y - endingPosition.y) == 1 ||
                            Math.abs(initialPosition.y - endingPosition.y) == 2);
    }

    /**
     * Solves Knight's Tour problem and returns successful case of
     * knight moves in form of two-dimensional array.
     *
     * @return  Chessboard representation with moves of knight as values
     */
    public int[][] solve() {
        for (int i = 0; i < width; i++) {
            for (int j = 0; j < height; j++) {
                initialPosition = new Point(i, j);
                if (solutionPossible(initialPosition)) {
                    resetBoard();
                    findSolution(initialPosition);

                    if (isClosedPath())
                        return board;
                }
            }
        }

        return null;
    }
}