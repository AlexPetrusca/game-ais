package connectfour;

import java.util.ArrayList;

public class AIRed {
  public Board makeMove(int depth, Board board) {
    BoardNode root = new BoardNode(board);
    int bestMove = miniMaxAB(root, depth, -1000000000, 1000000000, true);
//    int bestMove = miniMax(root, depth, true);
//    printMoveTree(root, depth);
    return performBestMove(root, bestMove);
  }

  private int miniMaxAB(BoardNode node, int depth, int alpha, int beta, boolean maximizingPlayer) {
    node.calculateChildren(maximizingPlayer);

    if (depth == 0 || node.isLeaf() || node.isGameOver()) {
      return node.heuristicScore();
    }

    if (maximizingPlayer) {
      int v = -1000000000;
      for (BoardNode child : node.getChildren()) {
        v = Math.max(v, miniMaxAB(child, depth - 1, alpha, beta, !maximizingPlayer));
        alpha = Math.max(alpha, v);
        node.setScore(v);
        if (beta <= alpha) {
          break;
        }
      }
      return v;
    } else {
      int v = +1000000000;
      for (BoardNode child : node.getChildren()) {
        v = Math.min(v, miniMaxAB(child, depth - 1, alpha, beta, !maximizingPlayer));
        beta = Math.min(beta, v);
        node.setScore(v);
        if (beta <= alpha) {
          break;
        }
      }
      return v;
    }
  }

  private void printMoveTree(BoardNode node, int depth) {
    if (depth == 0) {
      return;
    }

    System.out.println("Depth: " + depth);
    System.out.println("Score: " + node.getScore());
    node.getBoard().print();
    for (BoardNode child : node.getChildren()) {
      printMoveTree(child, depth - 1);
    }
  }

  private Board performBestMove(BoardNode root, int bestMove) {
    ArrayList<BoardNode> children = root.getChildren();
    if(children.size() == 1) {
      return children.get(0).getBoard();
    }
    for (BoardNode child : children) {
      if (child.getScore() == bestMove) {
        return child.getBoard();
      }
    }
    return null;
  }
}
