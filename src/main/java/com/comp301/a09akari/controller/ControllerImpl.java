package com.comp301.a09akari.controller;

import com.comp301.a09akari.model.Model;

import java.util.Random;

public class ControllerImpl implements ClassicMvcController {

  private final Model model;

  public ControllerImpl(Model model) {
    if (model == null) {
      throw new IllegalArgumentException("model cannot be null");
    }
    this.model = model;
  }

  @Override
  public void clickNextPuzzle() {

    int currentIdx = model.getActivePuzzleIndex();
    int size = model.getPuzzleLibrarySize();
    int nextPuzzleIdx = (currentIdx + 1) % size;

    model.setActivePuzzleIndex(nextPuzzleIdx);
  }

  @Override
  public void clickPrevPuzzle() {

    int currentIdx = model.getActivePuzzleIndex();
    int size = model.getPuzzleLibrarySize();

    model.setActivePuzzleIndex((currentIdx - 1 + size) % size);
  }

  @Override
  public void clickRandPuzzle() {

    int size = model.getPuzzleLibrarySize();
    Random random = new Random();
    int randomIdx = random.nextInt(size);
    model.setActivePuzzleIndex(randomIdx);
  }

  @Override
  public void clickResetPuzzle() {

    model.resetPuzzle();
  }

  @Override
  public void clickCell(int r, int c) {

    try {
      if (model.isLamp(r, c)) {
        model.removeLamp(r, c);
      } else {
        model.addLamp(r, c);
      }
    } catch (IllegalArgumentException | IndexOutOfBoundsException e) {
      // just catches doesn't do anything
    }
  }
}
