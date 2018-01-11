package checkers.players;

import checkers.CheckerPiece;
import checkers.TypeColor;

import java.awt.Point;
import java.util.ArrayList;

public class SkilledOpponent {

  public static byte[][] convertRealBoard(CheckerPiece[][] brd) {
    byte[][] copy = new byte[brd.length][brd[0].length];
    for (int y = 0; y < brd.length; y++) {
      for (int x = 0; x < brd[y].length; x++) {
        if (brd[y][x] != null) {
          if (brd[y][x].isWhite()) {
            if (brd[y][x].isKing()) {
              copy[y][x] = 'W';
            } else {
              copy[y][x] = 'w';
            }
          } else {
            if (brd[y][x].isKing()) {
              copy[y][x] = 'B';
            } else {
              copy[y][x] = 'b';
            }
          }
        } else {
          copy[y][x] = '_';
        }
      }
    }
    return copy;
  }

  public static CheckerPiece[][] convertCharBoard(byte[][] brd) {
    CheckerPiece[][] copy = new CheckerPiece[brd.length][brd[0].length];
    for (int y = 0; y < brd.length; y++) {
      for (int x = 0; x < brd[y].length; x++) {
        if (brd[y][x] == '_') {
          copy[y][x] = null;
        } else if (brd[y][x] == 'W') {
          copy[y][x] = new CheckerPiece(TypeColor.WHITE, new Point(x, y));
          copy[y][x].setKing(true);
        } else if (brd[y][x] == 'w') {
          copy[y][x] = new CheckerPiece(TypeColor.WHITE, new Point(x, y));
        } else if (brd[y][x] == 'B') {
          copy[y][x] = new CheckerPiece(TypeColor.BLACK, new Point(x, y));
          copy[y][x].setKing(true);
        } else if (brd[y][x] == 'b') {
          copy[y][x] = new CheckerPiece(TypeColor.BLACK, new Point(x, y));
        }
      }
    }
    return copy;
  }

  public void makeMove(int depth, CheckerPiece[][] board) {
    BoardNode root = new BoardNode(convertRealBoard(board));
////    createMoveTree(root, depth, true);
//    int bestMove = miniMax(root, depth, true, convertRealBoard(board));
    int bestMove = miniMaxAB(root, depth, -1000000000, 1000000000, true, convertRealBoard(board));
////    printMoveTree(root, depth, true);
    performBestMove(root, bestMove, board);
  }

  private void performBestMove(BoardNode root, int bestMove, CheckerPiece[][] board) {
    ArrayList<BoardNode> children = root.getChildren();
    for (BoardNode child : children) {
      if (child.getScore() == bestMove) {
        CheckerPiece[][] brd = convertCharBoard(child.getBoard());
        for (int y = 0; y < brd.length; y++) {
          System.arraycopy(brd[y], 0, board[y], 0, brd[y].length);
        }
        return;
      }
    }
  }

//  private void createMoveTree(BoardNode root, int depth, boolean maximizingPlayer) {
//    if(depth == 0) {
//      return;
//    }
//    root.calculateChildren(maximizingPlayer);
//    ArrayList<BoardNode> children = root.getChildren();
//    for (BoardNode child : children) {
//      createMoveTree(child, depth - 1, !maximizingPlayer);
//    }
//  }
//
//  private void printMoveTree(BoardNode root, int depth, boolean maximizingPlayer) {
//    if(maximizingPlayer) {
//      System.out.println("||||||||||    MAX    ||||||||||");
//    } else {
//      System.out.println("||||||||||    MIN    ||||||||||");
//    }
//    System.out.println("Depth: " + depth);
//    System.out.println("Score: " + root.getScore());
//    root.printBoard();
//
//    if(depth == 0) {
//      return;
//    }
//
//    ArrayList<BoardNode> children = root.getChildren();
//    for (BoardNode child : children) {
//      printMoveTree(child, depth - 1, !maximizingPlayer);
//    }
//  }

//  public int miniMax(BoardNode node, int depth, boolean maximizingPlayer, byte[][] board) {
//    if(depth == 0 || node.isTerminalNode()) {
//      return node.heuristicValue(board);
//    }
//
//    if(maximizingPlayer) {
//      int bestValue = -1000000000;
//      for(BoardNode child : node.getChildren()) {
//        int v = miniMax(child, depth - 1, false, board);
//        bestValue = Math.max(bestValue, v);
//        node.score = bestValue;
//      }
//      return bestValue;
//    } else {
//      int bestValue = 1000000000;
//      for(BoardNode child : node.getChildren()) {
//        int v = miniMax(child, depth - 1, true, board);
//        bestValue = Math.min(bestValue, v);
//        node.score = bestValue;
//      }
//      return bestValue;
//    }
//  }

  public int miniMaxAB(BoardNode node, int depth, int alpha, int beta, boolean maximizingPlayer, byte[][] board) {
    node.calculateChildren(maximizingPlayer);

    if (depth == 0) {
      return node.heuristicValue(board);
    }

    if (maximizingPlayer) {
      int v = -1000000000;
      for (BoardNode child : node.getChildren()) {
        v = Math.max(v, miniMaxAB(child, depth - 1, alpha, beta, false, board));
        alpha = Math.max(alpha, v);
        if (beta <= alpha) {
          break;
        }
        node.score = v;
      }
      return v;
    } else {
      int v = 1000000000;
      for (BoardNode child : node.getChildren()) {
        v = Math.min(v, miniMaxAB(child, depth - 1, alpha, beta, true, board));
        beta = Math.min(beta, v);
        if (beta <= alpha) {
          break;
        }
        node.score = v;
      }
      return v;
    }
  }

//  if(depth == 0) {
//    return;
//  }
//  root.calculateChildren(maximizingPlayer);
//  ArrayList<BoardNode> children = root.getChildren();
//  for (BoardNode child : children) {
//    createMoveTree(child, depth - 1, !maximizingPlayer);
//  }

  public ArrayList<CheckerPiece> getAllPieces(CheckerPiece[][] board) {
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


}
