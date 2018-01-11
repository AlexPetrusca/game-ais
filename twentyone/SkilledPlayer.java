package twentyone;

import java.util.ArrayList;

public class SkilledPlayer extends Player {
  int[][] fitnessCounts;

  public SkilledPlayer(int[][] fitnessCounts) {
    this.fitnessCounts = fitnessCounts.clone();
  }

  public int makeMove(ArrayList<Integer> board) {
    int left = board.size();
    int bestMove = 0;
    for(int i = 0; i < fitnessCounts[left-1].length; i++) {
      if(fitnessCounts[left-1][i] > fitnessCounts[left-1][bestMove]) {
        bestMove = i;
      }
    }
    int taken = bestMove + 1;
    while (!board.isEmpty() && taken != 0) {
      board.remove(0);
      taken--;
    }
    return bestMove + 1;
  }
}
