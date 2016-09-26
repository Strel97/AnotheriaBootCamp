package net.anotheria.strel.basic.queenproblem;

/**
 * Represents Queens problem solution. Contains methods for presenting solution
 * as chessboard positions.
 *
 * @author Strel97
 */
public class QueensProblemSolution {

    /**
     * Solution in form of numerical array, where
     * index of array is row number and value is
     * queens position in current row (or simply column number).
     */
    private int[]   solution;

    /**
     * Solution represented in chessboard format,
     * i.e. Alphabetical character that represents column
     * of chessboard and number, that represents line number.
     */
    private String  chessFormatSolution;


    public QueensProblemSolution(int[] solution) {
        this.solution = solution.clone();
        chessFormatSolution = convertToChessFormat(solution);
    }

    public QueensProblemSolution(String solution) {
        this.chessFormatSolution = solution;
        this.solution = convertToArray(solution);
    }

    private String convertToChessFormat(int[] solution) {
        StringBuilder chessboardPresentation = new StringBuilder();
        for (int i = 0; i < solution.length; i++) {
            chessboardPresentation.append(convertToChessboardPosition(solution[i]));
            chessboardPresentation.append(solution.length - i);
            chessboardPresentation.append(" ");
        }

        return chessboardPresentation.toString();
    }

    private int[] convertToArray(String posStr) {
        String[] positions = posStr.split(" ");
        int[] solution = new int[positions.length];

        for (int i = 0; i < positions.length; i++) {
            solution[i] = getArrayPosition(positions[i].charAt(0));
        }

        return solution;
    }

    private char convertToChessboardPosition(int pos) {
        return (char) ('A' + pos);
    }

    private int getArrayPosition(char chessPos) {
        return chessPos - 'A';
    }

    @Override
    public String toString() {
        return chessFormatSolution;
    }
}
