package checkers;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.RenderingHints;
import java.util.ArrayList;

public class CheckerPiece {
  TypeColor color;
  public boolean isKing = false;
  public Point position;

  public CheckerPiece(TypeColor color, Point position) {
    this.color = color;
    this.position = position;
  }

  public CheckerPiece(CheckerPiece piece) {
    this.color = piece.color;
    this.position = new Point(piece.position.x, piece.position.y);
    this.isKing = piece.isKing;
  }

  public boolean isBlack() {
    return color == TypeColor.BLACK;
  }

  public boolean isWhite() {
    return color == TypeColor.WHITE;
  }

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
      if(black.isKing) {
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
    }
    return false;
  }

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
      if (white.isKing) {
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
    }
    return false;
  }

  public ArrayList<CheckerPiece> getWhiteMoveablePieces(CheckerPiece[][] board) {
    ArrayList<CheckerPiece> allPieces = new ArrayList<>();
    for (CheckerPiece[] checkerPieces : board) {
      for (CheckerPiece checkerPiece : checkerPieces) {
        if(checkerPiece != null && checkerPiece.isWhite()) {
          allPieces.add(checkerPiece);
        }
      }
    }
    ArrayList<CheckerPiece> movablePieces = new ArrayList<>();
    for (CheckerPiece piece : allPieces) {
      if(!piece.getMoveSet(board, false).isEmpty()) {
        movablePieces.add(piece);
      }
    }
    return movablePieces;
  }

  public ArrayList<CheckerPiece> getBlackMoveablePieces(CheckerPiece[][] board) {
    ArrayList<CheckerPiece> allPieces = new ArrayList<>();
    for (CheckerPiece[] checkerPieces : board) {
      for (CheckerPiece checkerPiece : checkerPieces) {
        if(checkerPiece != null && checkerPiece.isBlack()) {
          allPieces.add(checkerPiece);
        }
      }
    }
    ArrayList<CheckerPiece> movablePieces = new ArrayList<>();
    for (CheckerPiece piece : allPieces) {
      if(!piece.getMoveSet(board, false).isEmpty()) {
        movablePieces.add(piece);
      }
    }
    return movablePieces;
  }

  public boolean isValid(int a, int b) {
    return (a >= 0 && a <= 7) && (b >= 0 && b <= 7);
  }


  public void setKing(boolean isKing) {
    this.isKing = isKing;
  }

  public boolean isLeftAndUpOf(CheckerPiece piece, CheckerPiece[][] board) {
    int x = piece.position.x - 1;
    int y = piece.position.y - 1;
    if (isValid(x, y) && board[y][x] != null) {
      return board[y][x] == this;
    }
    return false;
  }

  public boolean isLeftAndDownOf(CheckerPiece piece, CheckerPiece[][] board) {
    int x = piece.position.x - 1;
    int y = piece.position.y + 1;
    if (isValid(x, y) && board[y][x] != null) {
      return board[y][x] == this;
    }
    return false;
  }

  public boolean isRightAndUpOf(CheckerPiece piece, CheckerPiece[][] board) {
    int x = piece.position.x + 1;
    int y = piece.position.y - 1;
    if (isValid(x, y) && board[y][x] != null) {
      return board[y][x] == this;
    }
    return false;
  }

  public boolean isRightAndDownOf(CheckerPiece piece, CheckerPiece[][] board) {
    int x = piece.position.x + 1;
    int y = piece.position.y + 1;
    if (isValid(x, y) && board[y][x] != null) {
      return board[y][x] == this;
    }
    return false;
  }

  public void moveTo(int y, int x, CheckerPiece[][] board) {
    board[position.y][position.x] = null;
    position = new Point(x, y);
    board[y][x] = this;
  }

  public boolean isKing() {
    return isKing;
  }

  public void updateKingStatus() {
    if(isWhite()) {
      if(position.y == 0) {
        isKing = true;
      }
    } else if(isBlack()) {
      if(position.y == 7) {
        isKing = true;
      }
    }
  }

  public void paint(Graphics2D g) {
    if(isBlack()) {
      g.setColor(Color.RED.darker());
    } if(isWhite()) {
      g.setColor(Color.WHITE);
    }
    RenderingHints rh = new RenderingHints(
        RenderingHints.KEY_TEXT_ANTIALIASING,
        RenderingHints.VALUE_TEXT_ANTIALIAS_ON);
    g.setRenderingHints(rh);
    g.fillArc(100 * position.x + 11, 100 * position.y + 11, 80, 80, 0, 360);
    g.setColor(Color.BLACK);
    g.setStroke(new BasicStroke(2));
    g.drawArc(100 * position.x + 11, 100 * position.y + 11, 80, 80, 0, 360);
    if(isKing) {
      g.setColor(Color.YELLOW);
      g.drawRect(100 * position.x + 31, 100 * position.y + 31, 40, 40);
    }
  }
}
