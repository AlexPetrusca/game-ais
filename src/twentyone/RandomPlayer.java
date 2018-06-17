package twentyone;

import java.util.ArrayList;

public class RandomPlayer extends Player {
  ArrayList<Turn> turns;

  public RandomPlayer() {
    turns = new ArrayList<Turn>();
  }

  public int makeMove(ArrayList<Integer> board) {
    int left = board.size();
    int taken = (int) (Math.random() * 3 + 1);
    if (taken > board.size()) {
      taken = board.size();
    }
    Turn turn = new Turn(left, taken);
    turns.add(turn);
    while (!board.isEmpty() && taken != 0) {
      board.remove(0);
      taken--;
    }
    return turn.getNumberTaken();
  }

  public void addToFitness(int[][] fitnessCounts) {
    for(int i = 0; i < turns.size(); i++) {
      Turn turn = turns.get(i);
      fitnessCounts[turn.getNumberLeft() - 1][turn.getNumberTaken() - 1]++;
    }
  }

  public void clearTurns() {
    turns = new ArrayList<Turn>();
  }

  private class Turn {
    private int numberLeft;
    private int numberTaken;

    private Turn(int numberLeft, int numberTaken) {
      this.numberLeft = numberLeft;
      this.numberTaken = numberTaken;
    }

    public int getNumberLeft() {
      return numberLeft;
    }

    public int getNumberTaken() {
      return numberTaken;
    }
  }
}
