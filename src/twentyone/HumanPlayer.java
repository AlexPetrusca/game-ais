package twentyone;

import java.util.ArrayList;
import java.util.Scanner;

public class HumanPlayer extends Player {
  public int makeMove(ArrayList<Integer> board) {
    Scanner scanner = new Scanner(System.in);
    int taken = -1;
    while(taken < 0 || taken > 3 || taken > board.size()) {
      System.out.print("Please type in the number of coins to take out (1, 2, or 3): ");
      taken = scanner.nextInt();
    }
    int count = taken;
    while (!board.isEmpty() && count != 0) {
      board.remove(0);
      count--;
    }
    return taken;
  }
}
