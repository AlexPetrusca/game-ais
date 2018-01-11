package connectfour;

import java.util.ArrayList;

import static connectfour.Piece.*;

public class Board {
  Piece[][] board;

  private static final int[] dx = {+1, +0, +1, -1};
  private static final int[] dy = {+0, +1, +1, +1};
  public static final int HORIZONTAL = 0;
  public static final int VERTICAL = 1;
  public static final int DIAGONAL_LEFT = 2;
  public static final int DIAGONAL_RIGHT = 3;

  public Board(int rows, int cols) {
    board = new Piece[rows][cols];
    for (int y = 0; y < board.length; y++) {
      for (int x = 0; x < board[y].length; x++) {
        board[y][x] = EMPTY;
      }
    }
  }

  public Board(Board board) {
    this.board = new Piece[board.height()][board.width()];
    for (int y = 0; y < board.height(); y++) {
      for (int x = 0; x < board.width(); x++) {
        this.board[y][x] = board.get(y, x);
      }
    }
  }

  private Piece get(int y, int x) {
    return board[y][x];
  }

  public int width() {
    return board[0].length;
  }

  public int height() {
    return board.length;
  }

  public Piece[][] getBoard() {
    return board;
  }

  public String toString() {
    String s = "";
    for (int row = 0; row < 7; ++row) {
      s += "| ";
      for (int col = 0; col < 7; ++col)
        s += (board[row][col] == null ? ' ' : board[row][col]) + " | ";
      s += "\n";
    }

    for (int col = 0; col < 7; ++col)
      s += "----";
    s += "--";
    s += "\n";
    return s;
  }

  public void print() {
    System.out.print(toString());
    System.out.println("| 1 | 2 | 3 | 4 | 5 | 6 | 7 |\n");
  }

  public int findNumWaysToWin(Piece color) {
    int numWays = 0;
    for (int y = 0; y < height(); y++) {
      for (int x = 0; x < width(); x++) {
        if (isEmpty(y, x)) {
          Board temp = set(y, x, color);
          numWays += temp.numMakesFour(y, x, color);
        }
      }
    }
    return numWays;
  }

  public int numMakesFour(int y, int x, Piece color) {
    int numWays = 0;
    for (int i = 0; i < 4; i++) {
      if (makesFour(y, x, color, i)) {
        numWays++;
      }
    }
    return numWays;
  }

  private boolean makesFour(int y, int x, Piece color, int dir) {
    int count = 0;
    int x1 = x;
    int y1 = y;
    while (isValid(y1, x1) && is(y1, x1, color)) {
      x1 += dx[dir];
      y1 += dy[dir];
      count++;
    }
    x1 = x;
    y1 = y;
    while (isValid(y1, x1) && is(y1, x1, color)) {
      x1 -= dx[dir];
      y1 -= dy[dir];
      count++;
    }
    return count - 1 >= 4;
  }

  public boolean hasWon(Piece color) {
    for (int y = 0; y < 7; ++y)
      for (int x = 0; x < 7; ++x)
        if (numMakesFour(y, x, color) > 0)
          return true;

    return false;
  }

  public Board putDisk(int col, Piece color) {
    for (int row = 0; row < 7; ++row) {
      if (!isEmpty(row, col)) {
        return set(row - 1, col, color);
      }
    }
    return set(height() - 1, col, color);
  }

  private Board set(int y, int x, Piece color) {
    Board newBrd = new Board(this);
    Piece[][] copy = newBrd.getBoard();
    copy[y][x] = color;
    return newBrd;
  }

  public boolean isEmpty(int y, int x) {
    return board[y][x] == EMPTY;
  }

  public boolean is(int y, int x, Piece color) {
    return board[y][x] == color;
  }

  private boolean isValid(int y, int x) {
    return y >= 0 && y < 7 && x >= 0 && x < 7;
  }

  public ArrayList<Integer> getMoveSet(Piece color) {
    ArrayList<Integer> moveSet = new ArrayList<>();

    boolean redToWin = isRedToWin(moveSet);
    if(redToWin && color == RED) {
      return moveSet;
    }
    boolean yellowToWin = isYellowToWin(moveSet);
    if(yellowToWin && color == YELLOW) {
      return moveSet;
    }

    if(!(redToWin || yellowToWin)) {
      for(int x = 0; x < width(); x++) {
        if(!isFullColumn(x)) {
          moveSet.add(x);
        }
      }
    }

    return moveSet;
  }

  public boolean isRedToWin(ArrayList<Integer> moveSet) {
    Board temp;
    for(int x = 0; x < width(); x++) {
      if(!isFullColumn(x)) {
        temp = putDisk(x, RED);
        if(temp.hasWon(RED)) {
          moveSet.add(x);
          return true;
        }
      }
    }
    return false;
  }

  public boolean isYellowToWin(ArrayList<Integer> moveSet) {
    Board temp;
    for(int x = 0; x < width(); x++) {
      if(!isFullColumn(x)) {
        temp = putDisk(x, YELLOW);
        if(temp.hasWon(YELLOW)) {
          moveSet.add(x);
          return true;
        }
      }
    }
    return false;
  }

  private boolean isFullColumn(int colX) {
    return board[0][colX] != EMPTY;
  }

  public int findNumThrees(Piece color) {
    int numWays = 0;
    for (int y = 0; y < height(); y++) {
      for (int x = 0; x < width(); x++) {
        if (isEmpty(y, x)) {
          Board temp = set(y, x, color);
          numWays += temp.numMakesThree(y, x, color);
        }
      }
    }
    return numWays;
  }

  private int numMakesThree(int y, int x, Piece color) {
    int numWays = 0;
    for (int i = 0; i < 4; i++) {
      if (makesThree(y, x, color, i)) {
        numWays++;
      }
    }
    return numWays;
  }

  private boolean makesThree(int y, int x, Piece color, int dir) {
    int count = 0;
    int x1 = x;
    int y1 = y;
    while (isValid(y1, x1) && is(y1, x1, color)) {
      x1 += dx[dir];
      y1 += dy[dir];
      count++;
    }
    x1 = x;
    y1 = y;
    while (isValid(y1, x1) && is(y1, x1, color)) {
      x1 -= dx[dir];
      y1 -= dy[dir];
      count++;
    }
    return count - 1 >= 3;
  }

  public ArrayList<Integer> getPlayerMoveSet(Piece color) {
    ArrayList<Integer> moveSet = new ArrayList<>();

    for(int x = 0; x < width(); x++) {
      if(!isFullColumn(x)) {
        moveSet.add(x);
      }
    }

    return moveSet;
  }
}
