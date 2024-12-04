package com.comp301.a09akari.model;

public class PuzzleImpl implements Puzzle {

  private final int[][] board;
  private final int height;
  private final int width;

  public PuzzleImpl(int[][] board) {

    if (board == null || board.length == 0 || board[0].length == 0) {
      throw new IllegalArgumentException("board is null or empty");
    }

    this.board = board;
    this.height = board.length;
    this.width = board[0].length;
  }

  @Override
  public int getWidth() {
    return width;
  }

  @Override
  public int getHeight() {
    return height;
  }

  @Override
  public CellType getCellType(int r, int c) {

    if (r < 0 || r >= height || c < 0 || c >= width) {
      throw new IndexOutOfBoundsException("invalid position");
    }

    int cellType = board[r][c];
    switch (cellType) {
      case 0:
      case 1:
      case 2:
      case 3:
      case 4:
        return CellType.CLUE;
      case 5:
        return CellType.WALL;
      case 6:
        return CellType.CORRIDOR;
      default:
        throw new IllegalArgumentException("invalid cell type");
    }
  }

  @Override
  public int getClue(int r, int c) {

    if (r < 0 || r >= height || c < 0 || c >= width) {
      throw new IndexOutOfBoundsException("invalid position");
    }

    if (getCellType(r, c) != CellType.CLUE) {
      throw new IllegalArgumentException("invalid cell type");
    }

    return board[r][c];
  }
}
