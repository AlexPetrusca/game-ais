package connectfour;

import java.util.ArrayList;

import static connectfour.Piece.*;

public class BoardNode {
  private final Board board;
  private ArrayList<BoardNode> children;
  private int score;

  public BoardNode(Board board) {
    this.board = board;
    children = new ArrayList<>();
  }

  public boolean isLeaf() {
    return children.isEmpty() || board.hasWon(RED) || board.hasWon(YELLOW);
  }

  public ArrayList<BoardNode> getChildren() {
    return children;
  }


  public void setScore(int score) {
    this.score = score;
  }

  public int getScore() {
    return score;
  }

  public Board getBoard() {
    return board;
  }

  public void calculateChildren(boolean maximizingPlayer) {
    ArrayList<Integer> moveSet;
    if (maximizingPlayer) {
      moveSet = board.getMoveSet(RED);
      for (Integer columnX : moveSet) {
        children.add(new BoardNode(board.putDisk(columnX, RED)));
      }
    } else {
      moveSet = board.getMoveSet(YELLOW);
      for (Integer columnX : moveSet) {
        children.add(new BoardNode(board.putDisk(columnX, YELLOW)));
      }
    }
  }

  public int heuristicScore() {
    int score = 0;
    if(board.isRedToWin(new ArrayList<Integer>()) || board.hasWon(RED)) {
      score = 1000;
        if(board.hasWon(RED))
            score = 1000000;
    } else if(board.isYellowToWin(new ArrayList<Integer>()) || board.hasWon(YELLOW)) {
      score = -1000;
      if(board.hasWon(YELLOW))
          score = -1000000;
    }

    int yellowsFour = board.findNumWaysToWin(YELLOW);
    int redsFour = board.findNumWaysToWin(RED);
    int yellowsThree = board.findNumThrees(YELLOW);
    int redsThree = board.findNumThrees(RED);

    score += 40*(redsFour - yellowsFour) + 10*(redsThree - yellowsThree);

    setScore(score);
    return score;
  }

  public boolean isGameOver() {
    return board.hasWon(RED) || board.hasWon(YELLOW);
  }
}
