package net.anotheria.strel.basic.knightstour;

import javax.swing.*;
import java.awt.*;

/**
 * @author Strel97
 */
public class ChessboardVisualizer extends JFrame {

    private static final int RECT_DIMENSION = 50;

    private JPanel      contentPanel;

    private int[][] board;
    private int boardWidth;
    private int boardHeight;


    public ChessboardVisualizer(String caption, int[][] board, int width, int height) {
        super(caption);

        contentPanel = new JPanel(new BorderLayout());

        this.board = board;
        this.boardWidth = width;
        this.boardHeight = height;

        setContentPane(contentPanel);
        setSize(new Dimension(height * RECT_DIMENSION + RECT_DIMENSION, width * RECT_DIMENSION + RECT_DIMENSION));
        setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
    }

    private Point findNumPos(int num) {
        Point pos = new Point();
        for (int i = 0; i < board.length; i++) {
            for (int j = 0; j < board[i].length; j++) {
                if (board[i][j] == num) {
                    pos = new Point(i, j);
                }
            }
        }

        return pos;
    }

    @Override
    public void paint(Graphics g) {
        super.paint(g);

        Graphics2D g2 = (Graphics2D) contentPanel.getGraphics();

        g2.setStroke(new BasicStroke(RECT_DIMENSION / 20));

        g2.setColor(Color.black);
        g2.fillRect(0, 0, getWidth(), getHeight());

        for (int i = 0; i < board.length; i++) {
            for (int j = 0; j < board[i].length; j++) {
                // Drawing chessboard cells
                g2.setColor(Color.red);
                g2.drawRect(i * RECT_DIMENSION, j * RECT_DIMENSION, RECT_DIMENSION, RECT_DIMENSION);

                // Drawing order of move
                g2.setColor(Color.orange);
                g2.setFont(new Font("TimesRoman", Font.BOLD, RECT_DIMENSION / 3));
                g2.drawString(
                        String.valueOf(board[i][j]),
                        i * RECT_DIMENSION + RECT_DIMENSION / 3,
                        j * RECT_DIMENSION + RECT_DIMENSION / 3
                );
            }
        }

        // Finding and drawing horse movements
        g2.setColor(Color.green);
        for (int i = 1; i < boardHeight * boardWidth; i++) {
            Point from = findNumPos(i);
            Point to = findNumPos(i + 1);

            g2.drawLine(
                    from.x * RECT_DIMENSION + RECT_DIMENSION / 2,
                    from.y * RECT_DIMENSION + RECT_DIMENSION / 2,
                    to.x * RECT_DIMENSION + RECT_DIMENSION / 2,
                    to.y * RECT_DIMENSION + RECT_DIMENSION / 2
            );
        }
    }
}
