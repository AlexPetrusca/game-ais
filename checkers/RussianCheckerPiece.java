package checkers;

import java.awt.Point;
import java.util.ArrayList;

public class RussianCheckerPiece extends CheckerPiece {
  public RussianCheckerPiece(TypeColor color, Point position) {
    super(color, position);
  }

  public RussianCheckerPiece(CheckerPiece piece) {
    super(piece);
  }

  @Override
  public ArrayList<Point> getMoveSet(CheckerPiece[][] board, boolean limitedByElimination) {
    boolean whiteToEliminate = false;
    boolean blackToEliminate = false;
    if(limitedByElimination) {
      whiteToEliminate = shouldWhiteEliminate(board);
      blackToEliminate = shouldBlackEliminate(board);
    }
    updateKingStatus();
    ArrayList<Point> moveSet = new ArrayList<>();
    if(isWhite()) {
      if(isValid(position.x + 1, position.y - 1)) {
        if (board[position.y - 1][position.x + 1] == null || !board[position.y - 1][position.x + 1].isWhite()) {
          if(board[position.y - 1][position.x + 1] == null) {
            if(!whiteToEliminate) {
              moveSet.add(new Point(position.x + 1, position.y - 1));
            }
          } else if(isValid(position.x + 2, position.y - 2) && board[position.y - 1][position.x + 1].isBlack() && board[position.y - 2][position.x + 2] == null) {
            moveSet.add(new Point(position.x + 1, position.y - 1));
          }
        }
      }
      if(isValid(position.x - 1, position.y - 1)) {
        if (board[position.y - 1][position.x - 1] == null || !board[position.y - 1][position.x - 1].isWhite()) {
          if(board[position.y - 1][position.x - 1] == null) {
            if(!whiteToEliminate) {
              moveSet.add(new Point(position.x - 1, position.y - 1));
            }
          } else if(isValid(position.x - 2, position.y - 2) && board[position.y - 1][position.x - 1].isBlack() && board[position.y - 2][position.x - 2] == null) {
            moveSet.add(new Point(position.x - 1, position.y - 1));
          }
        }
      }
      if(isValid(position.x + 1, position.y + 1)) {
        if (board[position.y + 1][position.x + 1] != null && !board[position.y + 1][position.x + 1].isWhite()) {
          if(isValid(position.x + 2, position.y + 2) && board[position.y + 1][position.x + 1].isBlack() && board[position.y + 2][position.x + 2] == null) {
            moveSet.add(new Point(position.x + 1, position.y + 1));
          }
        }
      }
      if(isValid(position.x - 1, position.y + 1)) {
        if (board[position.y + 1][position.x - 1] != null && !board[position.y + 1][position.x - 1].isWhite()) {
          if(isValid(position.x - 2, position.y + 2) && board[position.y + 1][position.x - 1].isBlack() && board[position.y + 2][position.x - 2] == null) {
            moveSet.add(new Point(position.x - 1, position.y + 1));
          }
        }
      }
      if(isKing) {
        if(isValid(position.x + 1, position.y + 1)) {
          if (board[position.y + 1][position.x + 1] == null || !board[position.y + 1][position.x + 1].isWhite()) {
            if(board[position.y + 1][position.x + 1] == null) {
              if(!whiteToEliminate) {
                moveSet.add(new Point(position.x + 1, position.y + 1));
              }
            } else if(isValid(position.x + 2, position.y + 2) && board[position.y + 1][position.x + 1].isBlack() && board[position.y + 2][position.x + 2] == null) {
              moveSet.add(new Point(position.x + 1, position.y + 1));
            }
          }
        }
        if(isValid(position.x - 1, position.y + 1)) {
          if (board[position.y + 1][position.x - 1] == null || !board[position.y + 1][position.x - 1].isWhite()) {
            if(board[position.y + 1][position.x - 1] == null) {
              if(!whiteToEliminate) {
                moveSet.add(new Point(position.x - 1, position.y + 1));
              }
            } else if(isValid(position.x - 2, position.y + 2) && board[position.y + 1][position.x - 1].isBlack() && board[position.y + 2][position.x - 2] == null) {
              moveSet.add(new Point(position.x - 1, position.y + 1));
            }
          }
        }
      }
    } else if(isBlack()) {
      if(isValid(position.x + 1, position.y + 1)) {
        if (board[position.y + 1][position.x + 1] == null || !board[position.y + 1][position.x + 1].isBlack()) {
          if(board[position.y + 1][position.x + 1] == null) {
            if(!blackToEliminate) {
              moveSet.add(new Point(position.x + 1, position.y + 1));
            }
          } else if(isValid(position.x + 2, position.y + 2) && board[position.y + 1][position.x + 1].isWhite() && board[position.y + 2][position.x + 2] == null) {
            moveSet.add(new Point(position.x + 1, position.y + 1));
          }
        }
      }
      if(isValid(position.x - 1, position.y + 1)) {
        if (board[position.y + 1][position.x - 1] == null || !board[position.y + 1][position.x - 1].isBlack()) {
          if(board[position.y + 1][position.x - 1] == null) {
            if(!blackToEliminate) {
              moveSet.add(new Point(position.x - 1, position.y + 1));
            }
          } else if(isValid(position.x - 2, position.y + 2) && board[position.y + 1][position.x - 1].isWhite() && board[position.y + 2][position.x - 2] == null) {
            moveSet.add(new Point(position.x - 1, position.y + 1));
          }
        }
      }
      if(isValid(position.x + 1, position.y - 1)) {
        if (board[position.y - 1][position.x + 1] != null && !board[position.y - 1][position.x + 1].isBlack()) {
          if(isValid(position.x + 2, position.y - 2) && board[position.y - 1][position.x + 1].isWhite() && board[position.y - 2][position.x + 2] == null) {
            moveSet.add(new Point(position.x + 1, position.y - 1));
          }
        }
      }
      if(isValid(position.x - 1, position.y - 1)) {
        if (board[position.y - 1][position.x - 1] != null && !board[position.y - 1][position.x - 1].isBlack()) {
          if(isValid(position.x - 2, position.y - 2) && board[position.y - 1][position.x - 1].isWhite() && board[position.y - 2][position.x - 2] == null) {
            moveSet.add(new Point(position.x - 1, position.y - 1));
          }
        }
      }
      if(isKing) {
        if(isValid(position.x + 1, position.y - 1)) {
          if (board[position.y - 1][position.x + 1] == null || !board[position.y - 1][position.x + 1].isBlack()) {
            if(board[position.y - 1][position.x + 1] == null) {
              if(!blackToEliminate) {
                moveSet.add(new Point(position.x + 1, position.y - 1));
              }
            } else if(isValid(position.x + 2, position.y - 2) && board[position.y - 1][position.x + 1].isWhite() && board[position.y - 2][position.x + 2] == null) {
              moveSet.add(new Point(position.x + 1, position.y - 1));
            }
          }
        }
        if(isValid(position.x - 1, position.y - 1)) {
          if (board[position.y - 1][position.x - 1] == null || !board[position.y - 1][position.x - 1].isBlack()) {
            if(board[position.y - 1][position.x - 1] == null) {
              if(!blackToEliminate) {
                moveSet.add(new Point(position.x - 1, position.y - 1));
              }
            } else if(isValid(position.x - 2, position.y - 2) && board[position.y - 1][position.x - 1].isWhite() && board[position.y - 2][position.x - 2] == null) {
              moveSet.add(new Point(position.x - 1, position.y - 1));
            }
          }
        }
      }
    }
    return moveSet;
  }

  @Override
  public boolean shouldBlackEliminate(CheckerPiece[][] board) {
    ArrayList<CheckerPiece> blacks = getBlackMoveablePieces(board);
    for (CheckerPiece black : blacks) {
      if(isValid(black.position.x + 1, black.position.y + 1)) {
        if (board[black.position.y + 1][black.position.x + 1] == null || !board[black.position.y + 1][black.position.x + 1].isBlack()) {
          if(isValid(black.position.x + 2, black.position.y + 2) && board[black.position.y + 1][black.position.x + 1] != null && board[black.position.y + 1][black.position.x + 1].isWhite() && board[black.position.y + 2][black.position.x + 2] == null) {
            return true;
          }
        }
      }
      if(isValid(black.position.x - 1, black.position.y + 1)) {
        if (board[black.position.y + 1][black.position.x - 1] == null || !board[black.position.y + 1][black.position.x - 1].isBlack()) {
          if(isValid(black.position.x - 2, black.position.y + 2) && board[black.position.y + 1][black.position.x - 1] != null && board[black.position.y + 1][black.position.x - 1].isWhite() && board[black.position.y + 2][black.position.x - 2] == null) {
            return true;
          }
        }
      }
      if(isValid(black.position.x + 1, black.position.y - 1)) {
        if (board[black.position.y - 1][black.position.x + 1] == null || !board[black.position.y - 1][black.position.x + 1].isBlack()) {
          if(isValid(black.position.x + 2, black.position.y - 2) && board[black.position.y - 1][black.position.x + 1] != null && board[black.position.y - 1][black.position.x + 1].isWhite() && board[black.position.y - 2][black.position.x + 2] == null) {
            return true;
          }
        }
      }
      if(isValid(black.position.x - 1, black.position.y - 1)) {
        if (board[black.position.y - 1][black.position.x - 1] == null || !board[black.position.y - 1][black.position.x - 1].isBlack()) {
          if(isValid(black.position.x - 2, black.position.y - 2) && board[black.position.y - 1][black.position.x - 1] !=null && board[black.position.y - 1][black.position.x - 1].isWhite() && board[black.position.y - 2][black.position.x - 2] == null) {
            return true;
          }
        }
      }
    }
    return false;
  }

  @Override
  public boolean shouldWhiteEliminate(CheckerPiece[][] board) {
    ArrayList<CheckerPiece> whites = getWhiteMoveablePieces(board);
    for (CheckerPiece white : whites) {
      if (isValid(white.position.x + 1, white.position.y - 1)) {
        if (board[white.position.y - 1][white.position.x + 1] == null || !board[white.position.y - 1][white.position.x + 1].isWhite()) {
          if (isValid(white.position.x + 2, white.position.y - 2) && board[white.position.y - 1][white.position.x + 1] != null && board[white.position.y - 1][white.position.x + 1].isBlack() && board[white.position.y - 2][white.position.x + 2] == null) {
            return true;
          }
        }
      }
      if (isValid(white.position.x - 1, white.position.y - 1)) {
        if (board[white.position.y - 1][white.position.x - 1] == null || !board[white.position.y - 1][white.position.x - 1].isWhite()) {
          if (isValid(white.position.x - 2, white.position.y - 2) && board[white.position.y - 1][white.position.x - 1] != null && board[white.position.y - 1][white.position.x - 1].isBlack() && board[white.position.y - 2][white.position.x - 2] == null) {
            return true;
          }
        }
      }
      if (isValid(white.position.x + 1, white.position.y + 1)) {
        if (board[white.position.y + 1][white.position.x + 1] == null || !board[white.position.y + 1][white.position.x + 1].isWhite()) {
          if (isValid(white.position.x + 2, white.position.y + 2) && board[white.position.y + 1][white.position.x + 1] != null && board[white.position.y + 1][white.position.x + 1].isBlack() && board[white.position.y + 2][white.position.x + 2] == null) {
            return true;
          }
        }
      }
      if (isValid(white.position.x - 1, white.position.y + 1)) {
        if (board[white.position.y + 1][white.position.x - 1] == null || !board[white.position.y + 1][white.position.x - 1].isWhite()) {
          if (isValid(white.position.x - 2, white.position.y + 2) && board[white.position.y + 1][white.position.x - 1] != null && board[white.position.y + 1][white.position.x - 1].isBlack() && board[white.position.y + 2][white.position.x - 2] == null) {
            return true;
          }
        }
      }
    }
    return false;
  }
}
