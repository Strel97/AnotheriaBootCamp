package net.anotheria.strel.basic.knightstour;

/**
 * Class responsible for communicating user, launching Knight's Tour problem solver, getting solution
 * and outputting it.
 *
 * @author Strel97
 */
public class KnightsTourLauncher {
    public static void main(String[] args) {
        int boardWidth = 8;
        int boardHeight = 8;

        KnightsTourProblem problem = new KnightsTourProblem(boardWidth, boardHeight);
        int[][] solution = problem.solve();

        ChessboardVisualizer visualizer = new ChessboardVisualizer("Chessboard", solution, boardWidth, boardHeight);
        visualizer.setVisible(true);
    }
}
