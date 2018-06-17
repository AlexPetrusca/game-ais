package checkers.players;

import checkers.CheckerPiece;

import java.awt.Point;
import java.util.ArrayList;
import java.util.Scanner;

public class HumanPlayer {

  public void makeMove(CheckerPiece[][] board) {
    ArrayList<CheckerPiece> pieces = getMoveablePieces(board);
    if(!pieces.isEmpty()) {
      if(performPossibleJumps(board) != 0) {
        return;
      }
    }

    Scanner scanner = new Scanner(System.in);
    CheckerPiece selected = null;
    Point moveTo = null;
    while (selected == null) {
      System.out.println("Type in location of Piece (y x): ");
      int y = scanner.nextInt();
      int x = scanner.nextInt();
      if(isValid(y, x) && board[y][x] != null && board[y][x].isWhite() && board[y][x].getMoveSet(board, true).size() != 0) {
        selected = board[y][x];
      }
    }

    scanner = new Scanner(System.in);
    while (moveTo == null) {
      System.out.println("Type in location to move piece to (y x): ");
      int y = scanner.nextInt();
      int x = scanner.nextInt();
      ArrayList<Point> points = selected.getMoveSet(board, true);
      Point p = new Point(x, y);
      if(points.contains(p)) {
        selected.moveTo(y, x, board);
        moveTo = p;
      }
    }
    return;
  }

  private int performPossibleJumps(CheckerPiece[][] board) {
    boolean rightUp = false;
    boolean leftUp = false;
    boolean rightDown = false;
    boolean leftDown = false;
    CheckerPiece toJump = null;

    ArrayList<CheckerPiece> pieces = getMoveablePieces(board);
    for (CheckerPiece piece : pieces) {
      ArrayList<Point> pieceMoves = piece.getMoveSet(board, true);
      for (Point move : pieceMoves) {
        if (getCheckerAt(move, board) != null && getCheckerAt(move, board).isBlack()) {
          if (getCheckerAt(move, board).isRightAndUpOf(piece, board)) {
            rightUp = true;
            toJump = piece;
          } else if (getCheckerAt(move, board).isLeftAndUpOf(piece, board)) {
            leftUp = true;
            toJump = piece;
          }
          if (piece.isKing) {
            if (getCheckerAt(move, board).isRightAndDownOf(piece, board)) {
              rightDown = true;
              toJump = piece;
            } else if (getCheckerAt(move, board).isLeftAndDownOf(piece, board)) {
              leftDown = true;
              toJump = piece;
            }
          }
        }
      }
    }

    if(toJump != null) {
      int choice = pauseBeforeKill(toJump, rightUp, leftUp, rightDown, leftDown);
      if (choice == 1) {
        toJump.moveTo(toJump.position.y - 2, toJump.position.x + 2, board);
        kill(toJump.position.y - 1, toJump.position.x + 1, board);
        return 1 + performPossibleJumps(board);
      } else if (choice == 2) {
        toJump.moveTo(toJump.position.y - 2, toJump.position.x - 2, board);
        kill(toJump.position.y - 1, toJump.position.x - 1, board);
        return 1 + performPossibleJumps(board);
      } else if (choice == 3) {
        toJump.moveTo(toJump.position.y + 2, toJump.position.x + 2, board);
        kill(toJump.position.y + 1, toJump.position.x + 1, board);
        return 1 + performPossibleJumps(board);
      } else if (choice == 4) {
        toJump.moveTo(toJump.position.y + 2, toJump.position.x - 2, board);
        kill(toJump.position.y + 1, toJump.position.x - 1, board);
        return 1 + performPossibleJumps(board);
      }
    }
    return 0;
  }

  public void kill(int y, int x, CheckerPiece[][] board) {
    board[y][x] = null;
  }

  public CheckerPiece getCheckerAt(Point move, CheckerPiece[][] board) {
    return board[move.y][move.x];
  }

  public ArrayList<CheckerPiece> getAllPieces(CheckerPiece[][] board) {
    ArrayList<CheckerPiece> pieces = new ArrayList<>();
    for (CheckerPiece[] checkerPieces : board) {
      for (CheckerPiece checkerPiece : checkerPieces) {
        if(checkerPiece != null && checkerPiece.isWhite()) {
//          System.out.println("all: " + checkerPiece.position.y + " " + checkerPiece.position.x);
          pieces.add(checkerPiece);
        }
      }
    }
    return pieces;
  }

  public ArrayList<CheckerPiece> getMoveablePieces(CheckerPiece[][] board) {
//    System.out.println("movableWHITE:");
    ArrayList<CheckerPiece> allPieces = getAllPieces(board);
    ArrayList<CheckerPiece> movablePieces = new ArrayList<>();
    for (CheckerPiece piece : allPieces) {
      if(!piece.getMoveSet(board, true).isEmpty()) {
//        System.out.println("moveable: " + piece.position.y + " " + piece.position.x);
        movablePieces.add(piece);
      }
    }
    return movablePieces;
  }

  public ArrayList<CheckerPiece> getAllKings(CheckerPiece[][] board) {
    ArrayList<CheckerPiece> pieces = new ArrayList<>();
    for (CheckerPiece[] checkerPieces : board) {
      for (CheckerPiece checkerPiece : checkerPieces) {
        if(checkerPiece != null && checkerPiece.isWhite() && checkerPiece.isKing) {
          pieces.add(checkerPiece);
        }
      }
    }
    return pieces;
  }

  private int pauseBeforeKill(CheckerPiece piece, boolean upRight, boolean upLeft, boolean downRight, boolean downLeft) {
    Scanner scanner = new Scanner(System.in);
    System.out.printf("A possible jump was detected for the piece at position (%d,%d). \n", piece.position.y, piece.position.x);
    if(upRight) {
      System.out.println("1. Jump right and up: ");
    }
    if(upLeft) {
      System.out.println("2. Jump left and up: ");
    }
    if(downRight) {
      System.out.println("3. Jump right and down: ");
    }
    if(downLeft) {
      System.out.println("4. Jump left and down: ");
    }

    System.out.print("Enter number to make the jump (1-4): ");
    int returnInt = -9999;
    while(!(returnInt == 1 && upRight) && !(returnInt == 2 && upLeft) && !(returnInt == 3 && downRight) && !(returnInt == 4 && downLeft)) {
      if(returnInt != -9999) {
        System.out.print("\nTry again: ");
      }
      returnInt = scanner.nextInt();
    }
    return returnInt;
  }

  private boolean isValid(int a, int b) {
    return (a >= 0 && a <= 7) && (b >= 0 && b <= 7);
  }

}
