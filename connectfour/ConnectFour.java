package connectfour;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.ArrayList;

import static connectfour.Piece.EMPTY;
import static connectfour.Piece.RED;
import static connectfour.Piece.YELLOW;

public class ConnectFour extends JPanel{
    Board board;
    boolean[][] highlights;
    public final static int length = 114;
    AIRed red = new AIRed();
    HumanYellow yellow = new HumanYellow();
    private boolean moveMade = false;
    private boolean redPlayer = true;

    public ConnectFour() {
        highlights = new boolean[7][7];
        board = new Board(7, 7);
        JFrame f = new JFrame("ConnectFour v1.0");
        f.setBounds(150, 100, 816, 839);
        f.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                System.exit(0);
            }
        });
        addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                Point clicked = new Point(e.getY()/length, e.getX()/length);
                boolean isValid = false;
                for (int j = 0; j < highlights.length && !isValid; j++) {
                    for (int k = 0; k < highlights[j].length && !isValid; k++) {
                        boolean highlighted = highlights[j][k];
                        if(highlighted && j == clicked.x && k == clicked.y) {
                            isValid = true;
                        }
                    }
                }

                if(!redPlayer && isValid) {
                    performMove(clicked);
                }
            }
        });
        f.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        f.setContentPane(this);
        f.setVisible(true);
    }

    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        Graphics2D g2 = (Graphics2D) g;
        RenderingHints rh = new RenderingHints(
                RenderingHints.KEY_TEXT_ANTIALIASING,
                RenderingHints.VALUE_TEXT_ANTIALIAS_ON);
        g2.setRenderingHints(rh);

        boolean isWhiteFirst = true;
        for (int y = 0; y < 7; y++) {
            for (int x = 0; x < 7; x++) {
                if(x % 2 == 0) {
                    g2.setColor(new Color(150, 150, 150));
                    g2.fillRect(length * x + 1, length * y + 1, length, length);
                } else {
                    g2.setColor(Color.LIGHT_GRAY);
                    g2.fillRect(length * x + 1, length * y + 1, length, length);
                }
            }
            isWhiteFirst = !isWhiteFirst;
        }

        g2.setStroke(new BasicStroke(6));
        g2.setColor(Color.BLACK);
        for (int x = 1; x < 800; x += length) {
            g2.drawLine(x, 0, x, 800);
        }
        for (int y = 1; y < 800; y += length) {
            g2.drawLine(0, y, 800, y);
        }

        highlightAllPossible(g2);
        drawBoard(g2);
    }

    private void performMove(Point point) {
        board = yellow.makeMove(board, point);
        moveMade = true;
    }

    private void highlightAllPossible(Graphics2D g2) {
        if(!redPlayer) {
            ArrayList<Integer> nums = board.getPlayerMoveSet(YELLOW);
            for (Integer col : nums) {
                for (int row = 6; row >= 0; --row) {
                    if (board.isEmpty(row, col)) {
                        g2.setStroke(new BasicStroke(3));
                        g2.setColor(Color.CYAN.brighter());
                        g2.drawRect(length * col + 7, length * row + 7, length - 13, length - 13);
                        highlights[row][col] = true;
                        row = -1;
                    }
                }
            }
        }
    }

    private void drawBoard(Graphics2D g2) {
        for (int y = 0; y < board.getBoard().length; y++) {
            for (int x = 0; x < board.getBoard()[0].length; x++) {
                Piece piece = board.getBoard()[y][x];
                if (piece != EMPTY) {
                    int gap = (length - 90)/2;
                    if (piece == RED) {
                        g2.setColor(Color.RED);
                        g2.fillArc(length * x + gap, length * y + gap, 90, 90, 0, 360);
                        g2.setColor(Color.BLACK);
                        g2.setStroke(new BasicStroke(2));
                        g2.drawArc(length * x + gap, length * y + gap, 90, 90, 0, 360);
                    } else {
                        g2.setColor(Color.YELLOW);
                        g2.fillArc(length * x + gap, length *y + gap, 90, 90, 0, 360);
                        g2.setColor(Color.BLACK);
                        g2.setStroke(new BasicStroke(2));
                        g2.drawArc(length * x + gap, length * y + gap, 90, 90, 0, 360);
                    }
                }
            }
        }
    }

    public static void main(String[] args) {
        new ConnectFour().playHumanVsAiGame();
    }

    private void playHumanVsAiGame() {
        boolean gameOver = false;
        while (!gameOver) {
            repaint();
            if (redPlayer) {
                board = red.makeMove(8, board);
                gameOver = board.hasWon(RED);
            } else {
                while (!moveMade) {
                    repaint();
                    try {
                        Thread.sleep(100);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
                moveMade = false;
                gameOver = board.hasWon(YELLOW);
            }

            board.print();
            if (!gameOver) {
                redPlayer = !redPlayer;
            }
        }

            if (redPlayer) {
                System.out.println("Red Has Won!");
            } else {
                System.out.println("Yellow Has Won!");
            }
        }
}
