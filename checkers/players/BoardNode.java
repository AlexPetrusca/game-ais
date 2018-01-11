package checkers.players;

import checkers.CheckerPiece;
import checkers.TypeColor;

import java.awt.Point;
import java.util.ArrayList;

import static checkers.players.SkilledOpponent.convertCharBoard;
import static checkers.players.SkilledOpponent.convertRealBoard;

class BoardNode {
  public ArrayList<BoardNode> children;
  public byte[][] board;
  public int score;
  public int[][] positionValues =
      {  {0, 4, 0, 4, 0, 4, 0, 4},
         {4, 0, 3, 0, 3, 0, 3, 0},
         {0, 3, 0, 2, 0, 2, 0, 4},
         {4, 0, 2, 0, 1, 0, 3, 0},
         {0, 3, 0, 1, 0, 2, 0, 4},
         {4, 0, 2, 0, 1, 0, 3, 0},
         {0, 3, 0, 2, 0, 2, 0, 4},
         {4, 0, 4, 0, 4, 0, 4, 0},  };

  public BoardNode(byte[][] board) {
    this.board = board;
    children = new ArrayList<>();
  }

  public CheckerPiece[][] getRealBoard() {
    CheckerPiece[][] copy = new CheckerPiece[board.length][board[0].length];
    for (int y = 0; y < board.length; y++) {
      for (int x = 0; x < board[y].length; x++) {
        if (board[y][x] == '_') {
          copy[y][x] = null;
        } else if (board[y][x] == 'W') {
          copy[y][x] = new CheckerPiece(TypeColor.WHITE, new Point(x, y));
          copy[y][x].setKing(true);
        } else if (board[y][x] == 'w') {
          copy[y][x] = new CheckerPiece(TypeColor.WHITE, new Point(x, y));
        } else if (board[y][x] == 'B') {
          copy[y][x] = new CheckerPiece(TypeColor.BLACK, new Point(x, y));
          copy[y][x].setKing(true);
        } else if (board[y][x] == 'b') {
          copy[y][x] = new CheckerPiece(TypeColor.BLACK, new Point(x, y));
        }
      }
    }
    return copy;
  }

  public void calculateChildren(boolean maximizingPlayer) {
    CheckerPiece[][] brd = getRealBoard();
    if (maximizingPlayer) {
      ArrayList<CheckerPiece> checkers = getBlackMoveablePieces(brd);
      for (int i = 0; i < checkers.size(); i++) {
        ArrayList<Point> moves = checkers.get(i).getMoveSet(brd, true);
        for (Point move : moves) {
          simulateAndAddMove(checkers.get(i), move, convertRealBoard(brd), maximizingPlayer);
          brd = getRealBoard();
          checkers = getBlackMoveablePieces(brd);
        }
      }
    } else {
      ArrayList<CheckerPiece> checkers = getWhiteMoveablePieces(brd);
      for (int i = 0; i < checkers.size(); i++) {
        ArrayList<Point> moves = checkers.get(i).getMoveSet(brd, true);
        for (Point move : moves) {
          simulateAndAddMove(checkers.get(i), move, convertRealBoard(brd), maximizingPlayer);
          brd = getRealBoard();
          checkers = getWhiteMoveablePieces(brd);
        }
      }
    }
  }

  private void simulateAndAddMove(CheckerPiece piece, Point point, byte[][] chars, boolean maximizingPlayer) {
    //black
    if (maximizingPlayer) {
      CheckerPiece[][] realBoard = convertCharBoard(chars);
      if (realBoard[point.y][point.x] != null && realBoard[point.y][point.x].isWhite()) {
        Point dirVec = new Point(point.x - piece.position.x, point.y - piece.position.y);
        piece.moveTo(piece.position.y + 2 * dirVec.y, piece.position.x + 2 * dirVec.x, realBoard);
        realBoard[point.y][point.x] = null;
        addAllPossibleJumps(piece, convertRealBoard(realBoard), maximizingPlayer);
      } else if (realBoard[point.y][point.x] == null) {
        piece.moveTo(point.y, point.x, realBoard);
        children.add(new BoardNode(convertRealBoard(realBoard)));
      }
    }
    //white
    else {
      CheckerPiece[][] realBoard = convertCharBoard(chars);
      if (realBoard[point.y][point.x] != null && realBoard[point.y][point.x].isBlack()) {
        Point dirVec = new Point(point.x - piece.position.x, point.y - piece.position.y);
        piece.moveTo(piece.position.y + 2 * dirVec.y, piece.position.x + 2 * dirVec.x, realBoard);
        realBoard[point.y][point.x] = null;
        addAllPossibleJumps(piece, convertRealBoard(realBoard), maximizingPlayer);
      } else if (realBoard[point.y][point.x] == null) {
        piece.moveTo(point.y, point.x, realBoard);
        children.add(new BoardNode(convertRealBoard(realBoard)));
      }
    }
  }

  private void addAllPossibleJumps(CheckerPiece piece, byte[][] chars, boolean maximizingPlayer) { //fixme
    CheckerPiece temp = new CheckerPiece(piece);
    CheckerPiece[][] realBoard = convertCharBoard(chars);
    if (maximizingPlayer) {
      boolean keepGoing = piece.shouldBlackEliminate(realBoard);
      ArrayList<Point> moves = piece.getMoveSet(convertCharBoard(chars), true);
      if (keepGoing && !moves.isEmpty()) {
        for (Point move : moves) {
          Point dirVec = new Point(move.x - piece.position.x, move.y - piece.position.y);
          piece.moveTo(piece.position.y + 2 * dirVec.y, piece.position.x + 2 * dirVec.x, realBoard);
          realBoard[move.y][move.x] = null;
          addAllPossibleJumps(piece, convertRealBoard(realBoard), maximizingPlayer);

          piece = new CheckerPiece(temp);
          realBoard = convertCharBoard(chars);
        }
      } else {
        children.add(new BoardNode(convertRealBoard(realBoard)));
      }
    } else {
      boolean keepGoing = piece.shouldWhiteEliminate(realBoard);
      ArrayList<Point> moves = piece.getMoveSet(convertCharBoard(chars), true);
      if (keepGoing && !moves.isEmpty()) {
        for (Point move : moves) {
          Point dirVec = new Point(move.x - piece.position.x, move.y - piece.position.y);
          piece.moveTo(piece.position.y + 2 * dirVec.y, piece.position.x + 2 * dirVec.x, realBoard);
          realBoard[move.y][move.x] = null;
          addAllPossibleJumps(piece, convertRealBoard(realBoard), maximizingPlayer);

          piece = new CheckerPiece(temp);
          realBoard = convertCharBoard(chars);
        }
      } else {
        children.add(new BoardNode(convertRealBoard(realBoard)));
      }
    }
  }

  public ArrayList<CheckerPiece> getAllBlackPieces(CheckerPiece[][] board) {
    ArrayList<CheckerPiece> pieces = new ArrayList<>();
    for (CheckerPiece[] checkerPieces : board) {
      for (CheckerPiece checkerPiece : checkerPieces) {
        if (checkerPiece != null && checkerPiece.isBlack()) {
          pieces.add(checkerPiece);
        }
      }
    }
    return pieces;
  }

  public ArrayList<CheckerPiece> getAllWhitePieces(CheckerPiece[][] board) {
    ArrayList<CheckerPiece> pieces = new ArrayList<>();
    for (CheckerPiece[] checkerPieces : board) {
      for (CheckerPiece checkerPiece : checkerPieces) {
        if (checkerPiece != null && checkerPiece.isWhite()) {
          pieces.add(checkerPiece);
        }
      }
    }
    return pieces;
  }

  public ArrayList<CheckerPiece> getBlackMoveablePieces(CheckerPiece[][] board) {
    ArrayList<CheckerPiece> allPieces = getAllBlackPieces(board);
    ArrayList<CheckerPiece> movablePieces = new ArrayList<>();
    for (CheckerPiece piece : allPieces) {
      if (!piece.getMoveSet(board, true).isEmpty()) {
        movablePieces.add(piece);
      }
    }
    return movablePieces;
  }

  public ArrayList<CheckerPiece> getWhiteMoveablePieces(CheckerPiece[][] board) {
    ArrayList<CheckerPiece> allPieces = getAllWhitePieces(board);
    ArrayList<CheckerPiece> movablePieces = new ArrayList<>();
    for (CheckerPiece piece : allPieces) {
      if (!piece.getMoveSet(board, true).isEmpty()) {
        movablePieces.add(piece);
      }
    }
    return movablePieces;
  }

  public ArrayList<BoardNode> getChildren() {
    return children;
  }

  public byte[][] getBoard() {
    return board;
  }

  public int getScore() {
    return score;
  }

  public boolean isTerminalNode() {
    return children == null || children.isEmpty();
  }

  public int heuristicValue(byte[][] originalBoard) {
    if(!children.isEmpty()) {
      int whitesOri = 0;
      int blacksOri = 0;
      int whiteKingsOri = 0;
      int blackKingsOri = 0;
      int boardBOri = 0;
      int boardWOri = 0;

      int whitesFin = 0;
      int blacksFin = 0;
      int whiteKingsFin = 0;
      int blackKingsFin = 0;
      int boardBFin = 0;
      int boardWFin = 0;

      for (byte[] chars : originalBoard) {
        for (byte piece : chars) {
          if (piece == 'w') {
            whitesOri++;
          } else if (piece == 'W') {
            whiteKingsOri++;
          } else if (piece == 'b') {
            blacksOri++;
          } else if (piece == 'B') {
            blackKingsOri++;
          }
        }
      }
      for (byte[] chars : board) {
        for (byte piece : chars) {
          if (piece == 'w') {
            whitesFin++;
          } else if (piece == 'W') {
            whiteKingsFin++;
          } else if (piece == 'b') {
            blacksFin++;
          } else if (piece == 'B') {
            blackKingsFin++;
          }
        }
      }

      for (int y = 0; y < originalBoard.length; y++) {
        for (int x = 0; x < originalBoard[y].length; x++) {
          if (originalBoard[y][x] == 'b' || originalBoard[y][x] == 'B') {
            boardBOri += positionValues[y][x];
          }
        }
      }
      for (int y = 0; y < board.length; y++) {
        for (int x = 0; x < board[y].length; x++) {
          if (board[y][x] == 'b' || board[y][x] == 'B') {
            boardBFin += positionValues[y][x];
          }
        }
      }

      for (int y = 0; y < originalBoard.length; y++) {
        for (int x = 0; x < originalBoard[y].length; x++) {
          if (originalBoard[y][x] == 'w' || originalBoard[y][x] == 'W') {
            boardWOri += positionValues[y][x];
          }
        }
      }
      for (int y = 0; y < board.length; y++) {
        for (int x = 0; x < board[y].length; x++) {
          if (board[y][x] == 'w' || board[y][x] == 'W') {
            boardWFin += positionValues[y][x];
          }
        }
      }

      int deltaW = whitesOri - whitesFin;
      int deltaB = blacksOri - blacksFin;
      int deltaKW = 2 * (whiteKingsOri - whiteKingsFin);
      int deltaKB = 2 * (blackKingsOri - blackKingsFin);

//      int deltaW = 0;
//      int deltaB = 0;
//      int deltaKW = 0;
//      int deltaKB = 0;

      int deltaBoardB = boardBFin - boardBOri;
      int deltaBoardW = boardWFin - boardWOri;

      score = deltaW - deltaB + deltaKW - deltaKB + deltaBoardB - deltaBoardW;
      return deltaW - deltaB + deltaKW - deltaKB + deltaBoardB - deltaBoardW;
    } else if(children.isEmpty() && getAllWhitePieces(convertCharBoard(board)).isEmpty()) {
      return 1000000000;
    } else if(children.isEmpty() && getAllBlackPieces(convertCharBoard(board)).isEmpty()) {
      return -1000000000;
    }
    return -1000000000;
  }

  public void printBoard() {
    for (byte[] chars : board) {
      for (byte c : chars) {
        System.out.print(c + " ");
      }
      System.out.println();
    }
    System.out.println();
    System.out.println();
  }
}
