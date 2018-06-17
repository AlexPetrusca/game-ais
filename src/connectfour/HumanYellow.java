package connectfour;

import java.awt.*;

public class HumanYellow {
    public Board makeMove(Board board, Point point) {
        return board.putDisk(point.y, Piece.YELLOW);
    }
}
