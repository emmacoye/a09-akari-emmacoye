package com.comp301.a09akari.model;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class ModelImpl implements Model {

  private final PuzzleLibrary puzzleLibrary;
  private int puzzleIndex;
  private final Set<int[]> lampSpots;
  private final List<ModelObserver> observers;

  public ModelImpl(PuzzleLibrary puzzleLibrary) {
    if (puzzleLibrary == null || puzzleLibrary.size() == 0) {
      throw new IllegalArgumentException("Puzzle library cannot be null or empty");
    }

    this.puzzleLibrary = puzzleLibrary;
    this.puzzleIndex = 0;
    this.lampSpots = new HashSet<>();
    this.observers = new ArrayList<>();
  }

  @Override
  public void addLamp(int r, int c) {

    Puzzle puzzle = getActivePuzzle();
    if (r < 0 || r >= puzzle.getHeight() || c < 0 || c >= puzzle.getWidth()) {
      throw new IndexOutOfBoundsException("out of bounds");
    }

    if (puzzle.getCellType(r, c) != CellType.CORRIDOR) {
      throw new IllegalArgumentException("cell type is not sufficient for lamps");
    }

    if (isLamp(r, c)) {
      return;
    }

    lampSpots.add(new int[] {r, c});
    notifyObservers();
  }

  @Override
  public void removeLamp(int r, int c) {

    Puzzle puzzle = getActivePuzzle();
    if (r < 0 || r >= puzzle.getHeight() || c < 0 || c >= puzzle.getWidth()) {
      throw new IndexOutOfBoundsException("out of bounds");
    }

    if (puzzle.getCellType(r, c) != CellType.CORRIDOR) {
      throw new IllegalArgumentException("cell type is not sufficient for lamps");
    }

    // found online to account for not creating a new object for removing
    lampSpots.removeIf(loc -> loc[0] == r && loc[1] == c);
    notifyObservers();
  }

  @Override
  public boolean isLit(int r, int c) {

    Puzzle puzzle = getActivePuzzle();
    if (r < 0 || r >= puzzle.getHeight() || c < 0 || c >= puzzle.getWidth()) {
      throw new IndexOutOfBoundsException("out of bounds");
    }

    if (isLamp(r, c)) {
      return true;
    }

    return directionLit(r, c, -1, 0)
        || directionLit(r, c, 1, 0)
        || directionLit(r, c, 0, -1)
        || directionLit(r, c, 0, 1);
  }

  @Override
  public boolean isLamp(int r, int c) {

    Puzzle puzzle = getActivePuzzle();
    if (r < 0 || r >= puzzle.getHeight() || c < 0 || c >= puzzle.getWidth()) {
      throw new IndexOutOfBoundsException("out of bounds");
    }

    if (puzzle.getCellType(r, c) != CellType.CORRIDOR) {
      throw new IllegalArgumentException("cell type is not sufficient for lamps");
    }

    return lampSpots.stream().anyMatch(loc -> loc[0] == r && loc[1] == c);
  }

  @Override
  public boolean isLampIllegal(int r, int c) {

    if (!isLamp(r, c)) {
      throw new IllegalArgumentException("no lamp");
    }

    return directionLit(r, c, -1, 0)
        || directionLit(r, c, 1, 0)
        || directionLit(r, c, 0, -1)
        || directionLit(r, c, 0, 1);
  }

  @Override
  public Puzzle getActivePuzzle() {
    return puzzleLibrary.getPuzzle(puzzleIndex);
  }

  @Override
  public int getActivePuzzleIndex() {
    return puzzleIndex;
  }

  @Override
  public void setActivePuzzleIndex(int index) {
    if (index < 0 || index >= puzzleLibrary.size()) {
      throw new IndexOutOfBoundsException("out of bounds");
    }

    puzzleIndex = index;
    resetPuzzle();
  }

  @Override
  public int getPuzzleLibrarySize() {
    return puzzleLibrary.size();
  }

  @Override
  public void resetPuzzle() {
    lampSpots.clear();
    notifyObservers();
  }

  @Override
  public boolean isSolved() {

    Puzzle puzzle = getActivePuzzle();
    for (int r = 0; r < puzzle.getHeight(); r++) {
      for (int c = 0; c < puzzle.getWidth(); c++) {
        CellType cellType = puzzle.getCellType(r, c);
        if (cellType == CellType.CORRIDOR && !isLit(r, c)) {
          return false;
        }
        if (cellType == CellType.CLUE && !isClueSatisfied(r, c)) {
          return false;
        }
      }
    }

    for (int[] lamp : lampSpots) {
      if (isLampIllegal(lamp[0], lamp[1])) {
        return false;
      }
    }

    return true;
  }

  @Override
  public boolean isClueSatisfied(int r, int c) {
    Puzzle puzzle = getActivePuzzle();
    if (r < 0 || r >= puzzle.getHeight() || c < 0 || c >= puzzle.getWidth()) {
      throw new IndexOutOfBoundsException("out of bounds");
    }

    if (puzzle.getCellType(r, c) != CellType.CLUE) {
      throw new IllegalArgumentException("must be a clue");
    }

    int clueVal = puzzle.getClue(r, c);
    int lampAmount = 0;
    int[][] directions = {{-1, 0}, {1, 0}, {0, -1}, {0, 1}};

    for (int[] direction : directions) {
      int x = r + direction[0];
      int y = c + direction[1];

      if (x >= 0 && x < puzzle.getHeight() && y >= 0 && y < puzzle.getWidth()) {
        CellType adjacentType = puzzle.getCellType(x, y);
        // Check if the adjacent cell is a corridor and contains a lamp
        if (adjacentType == CellType.CORRIDOR && isLamp(x, y)) {
          lampAmount++;
        }
      }
    }

    return lampAmount == clueVal;
  }

  @Override
  public void addObserver(ModelObserver observer) {
    observers.add(observer);
  }

  @Override
  public void removeObserver(ModelObserver observer) {
    observers.remove(observer);
  }

  private void notifyObservers() {
    for (ModelObserver observer : observers) {
      observer.update(this);
    }
  }

  private boolean directionLit(int r, int c, int dr, int dc) {
    Puzzle puzzle = getActivePuzzle();
    int x = r + dr;
    int y = c + dc;
    while (x >= 0 && x < puzzle.getHeight() && y >= 0 && y < puzzle.getWidth()) {
      CellType cellType = puzzle.getCellType(x, y);
      if (cellType == CellType.WALL || cellType == CellType.CLUE) {
        break;
      }
      if (isLamp(x, y)) {
        return true;
      }
      x += dr;
      y += dc;
    }
    return false;
  }
}
