package twentyone;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Arrays;

public class TwentyOne {
  private RandomPlayer player;
  private RandomPlayer opponent;
  private Player selectedPlayer;
  private int[][] fitnessCounts;
  private ArrayList<Integer> board;

  public TwentyOne() {
    player = new RandomPlayer();
    opponent = new RandomPlayer();
    fitnessCounts = new int[21][3];
    loadNewBoard();
  }

  private void runSimulations(int numGames) {
    int count = 0;
    while (count < numGames) {
      loadNewBoard();
      resetPlayers();
      selectedPlayer = player;
      runSingleSimulation();
      count++;
    }
  }

  private void loadNewBoard() {
    board = new ArrayList<Integer>();
    for(int i = 0; i < 21; i++) {
      board.add(1);
    }
  }

  private void resetPlayers() {
    player.clearTurns();
    opponent.clearTurns();
  }

  private void runSingleSimulation() {
    boolean isOver = false;
    while(!isOver) {
      selectedPlayer.makeMove(board);

      if (board.isEmpty()) {
        if(selectedPlayer == player) {
          player.addToFitness(fitnessCounts);
        }
        isOver = true;
      }

      if(selectedPlayer == player) {
        selectedPlayer = opponent;
      } else {
        selectedPlayer = player;
      }
    }
  }

  public void printFitnessCounts() {
    System.out.println(Arrays.deepToString(fitnessCounts));
  }

  public void writeFitnessCountsToFile() {
    File file = new File("fitnessCounts.txt");
    try(PrintWriter writer = new PrintWriter(file)) {
      for (int[] fitnessCount : fitnessCounts) {
        for (int i : fitnessCount) {
          writer.print(i + " ");
        }
      }
    } catch (FileNotFoundException e) {
      e.printStackTrace();
    }
  }

  public void playVsHuman() {
    SkilledPlayer ai = new SkilledPlayer(fitnessCounts);
    HumanPlayer human = new HumanPlayer();
    loadNewBoard();
    selectedPlayer = ai;
    while(!board.isEmpty()) {
      String yer = "";
      if(selectedPlayer == ai) {
        yer = "AI";
      } else {
        yer = "your";
      }
      System.out.printf("Start of %s Turn: \n", yer);
      printBoard();
      if(selectedPlayer == ai) {
        System.out.println("AI chose: " + selectedPlayer.makeMove(board));
      } else {
        System.out.println("you chose: " + selectedPlayer.makeMove(board));
      }
      printBoard();
      if(!board.isEmpty()) {
        if (selectedPlayer == ai) {
          selectedPlayer = human;
        } else {
          selectedPlayer = ai;
        }
      }
      System.out.println();
    }
    if(selectedPlayer == human) {
      System.out.println("\n\nYOU WIN!\n\n");
    } else {
      System.out.println("\n\nYOU LOSE!\n\n");
    }
  }

  private void printBoard() {
    for (Integer integer : board) {
      System.out.print(integer + " ");
    }
    System.out.println();
  }

  public static void main(String[] args) {
    TwentyOne game = new TwentyOne();
    game.runSimulations(100000);
//    game.printFitnessCounts();
    game.playVsHuman();
  }
}


