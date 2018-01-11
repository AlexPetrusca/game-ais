package connectfour;

public enum Piece {
  RED('R'),
  YELLOW('Y'),
  EMPTY(' ');

  private char text;
  Piece(char text) {
    this.text = text;
  }

  @Override
  public String toString() {
    return "" + text;
  }
}
