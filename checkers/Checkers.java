package checkers;

import checkers.players.HumanPlayer;
import checkers.players.SkilledOpponent;

import javax.swing.JFrame;
import javax.swing.JPanel;
import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.RenderingHints;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.ArrayList;

public class Checkers extends JPanel {
  CheckerPiece[][] board;
  CheckerPiece selected = null;
  int[][] highlights;
  public final static int length = 100;
  public static boolean moveMade = false;

  public Checkers() {
    highlights = new int[8][8];
    board = new CheckerPiece[8][8];
    resetBoardAndPlayers();
    JFrame f = new JFrame("Checkers v1.0");
    f.setBounds(150, 100, 832, 889);
    f.addWindowListener(new WindowAdapter() {
      @Override
      public void windowClosing(WindowEvent e) {
        System.exit(0);
      }
    });
    addMouseListener(new MouseAdapter() {
      @Override
      public void mouseClicked(MouseEvent e) {
        if (selected == null) {
          selected = board[e.getY() / length][e.getX() / length];
          if (selected == null || selected.getMoveSet(board, true).isEmpty()) {
            selected = null;
          }
        } else {
          if (e.getY() / length != selected.position.y || e.getX() / length != selected.position.x) {
            ArrayList<Point> moves = selected.getMoveSet(board, true);
            for (Point move : moves) {
              if (e.getX() / length == move.x && e.getY() / length == move.y) {
                performMove(move);
                repaint();
                return;
              }
            }
            selected = null;
          }
        }
      }
    });
    f.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
    f.setContentPane(this);
    f.setVisible(true);
  }

  protected void paintComponent(Graphics g) {
    super.paintComponent(g);

    Graphics2D g2 = (Graphics2D)g;
    RenderingHints rh = new RenderingHints(
        RenderingHints.KEY_TEXT_ANTIALIASING,
        RenderingHints.VALUE_TEXT_ANTIALIAS_ON);
    g2.setRenderingHints(rh);

    for(int x = 1; x < 900; x+=100) {
      g2.drawLine(x, 0, x, 800);
    }
    for(int y = 1; y < 900; y+=100) {
      g2.drawLine(0, y, 800, y);
    }

    boolean isWhiteFirst = true;
    for(int y = 0; y < 8; y++) {
      for(int x = 0; x < 8; x++) {
        if(!isWhiteFirst && x % 2 == 0) {
          g2.setColor(Color.GRAY.darker());
          g2.fillRect(length*x + 1, length*y + 1, length, length);
        } else if(isWhiteFirst && x % 2 == 1) {
          g2.setColor(Color.GRAY.darker());
          g2.fillRect(length*x + 1, length*y + 1, length, length);
        } else {
          g2.setColor(Color.LIGHT_GRAY);
          g2.fillRect(length*x + 1, length*y + 1, length, length);
        }
      }
      isWhiteFirst = !isWhiteFirst;
    }

    highlightAllPossible(g2);
    drawBoard(g2);
  }

  private void performMove(Point point) {
    if (board[point.y][point.x] != null && board[point.y][point.x].isBlack()) {
      Point dirVec = new Point(point.x - selected.position.x, point.y - selected.position.y);
      selected.moveTo(selected.position.y + 2*dirVec.y, selected.position.x + 2*dirVec.x, board);
      board[point.y][point.x] = null;
      if(!selected.shouldWhiteEliminate(board)) {
        moveMade = true;
      } else if(selected.shouldWhiteEliminate(board) && selected.getMoveSet(board, true).isEmpty()) {
        moveMade = true;
      }
    } else if (board[point.y][point.x] == null) {
      selected.moveTo(point.y, point.x, board);
      moveMade = true;
    }
    selected = null;
  }

  private void highlightAllPossible(Graphics2D g2) {
    if(!moveMade) {
      if (selected == null) {
        HumanPlayer temp = new HumanPlayer();
        ArrayList<CheckerPiece> movables = temp.getMoveablePieces(board);
        for (CheckerPiece movable : movables) {
          int y = movable.position.y;
          int x = movable.position.x;
          g2.setColor(Color.cyan);
          g2.setStroke(new BasicStroke(2));
          g2.drawRect(length * x + 4, length * y + 4, length - 6, length - 6);
        }
      } else {
        ArrayList<Point> moves = selected.getMoveSet(board, true);
        g2.setColor(Color.YELLOW);
        g2.drawRect(length * selected.position.x + 3, length * selected.position.y + 3, length - 5, length - 5);
        for (Point move : moves) {
          int y = move.y;
          int x = move.x;
          g2.setColor(Color.cyan);
          g2.setStroke(new BasicStroke(2));
          g2.drawRect(length * x + 4, length * y + 4, length - 6, length - 6);
        }
      }
      g2.setStroke(new BasicStroke(1));
    }
  }

  private void drawBoard(Graphics2D g2) {
    for (CheckerPiece[] checkerPieces : board) {
      for (CheckerPiece checkerPiece : checkerPieces) {
        if(checkerPiece != null) {
          checkerPiece.paint(g2);
        }
      }
    }
  }

  private void playHumanVsSkilledGame() {
    SkilledOpponent black = new SkilledOpponent();
    HumanPlayer white = new HumanPlayer();
    boolean whitePlayer = true;
    boolean gameOver = false;
    drawBoardToTextWithIndices();
    System.out.println();
    while(!gameOver) {
      if(whitePlayer) {
        while(!moveMade) {
          repaint();
          try {
            Thread.sleep(100);
          } catch (InterruptedException e) {
            e.printStackTrace();
          }
        }
      } else {
        black.makeMove(10, board);
        moveMade = false;
      }

      if(whitePlayer) {
        System.out.println("White Turn:");
        drawBoardToTextWithIndices();
        System.out.println();
      } else {
        System.out.println("Black Turn:");
        drawBoardToTextWithIndices();
        System.out.println();
      }

      whitePlayer = !whitePlayer;

      if(white.getAllPieces(board).isEmpty() || black.getAllPieces(board).isEmpty()) {
        gameOver = true;
      }
    }
  }

  private void drawBoardToTextWithIndices() {
    for (int y = 0; y < board.length; y++) {
      System.out.print(y + "   ");
      for (int x = 0; x < board[y].length; x++) {
        CheckerPiece piece = board[y][x];
        if(piece == null) {
          System.out.print(" " + "_");
        } else if(piece.isWhite()) {
          System.out.print(" " + "W");
        } else if(piece.isBlack()) {
          System.out.print(" " + "B");
        }
      }
      System.out.println();
    }
    System.out.println();
    System.out.println("     0 1 2 3 4 5 6 7");
  }

  private void resetBoardAndPlayers() {
    board = new CheckerPiece[8][8];
    for (int y = 0; y < board.length; y++) {
      for (int x = 0; x < board[y].length; x++) {
        if(y <= 2) {
          if(y % 2 == 0) {
            if(x % 2 == 1) {
              board[y][x] = new CheckerPiece(TypeColor.BLACK, new Point(x, y));
//              board[y][x].setKing(true);
            }
          } else {
            if(x % 2 == 0) {
              board[y][x] = new CheckerPiece(TypeColor.BLACK, new Point(x, y));
//              board[y][x].setKing(true);
            }
          }
        } else if(y >= 5) {
          if(y % 2 == 0) {
            if(x % 2 == 1) {
              board[y][x] = new CheckerPiece(TypeColor.WHITE, new Point(x, y));
//              board[y][x].setKing(true);
            }
          } else {
            if(x % 2 == 0) {
              board[y][x] = new CheckerPiece(TypeColor.WHITE, new Point(x, y));
//              board[y][x].setKing(true);
            }
          }
        }
      }
    }
  }

  public static void main(String[] args) {
    Checkers checkers = new Checkers();
    checkers.playHumanVsSkilledGame();
  }
}