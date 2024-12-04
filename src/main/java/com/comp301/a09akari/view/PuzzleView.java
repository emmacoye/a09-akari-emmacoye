package com.comp301.a09akari.view;

import com.comp301.a09akari.controller.ClassicMvcController;
import com.comp301.a09akari.model.CellType;
import com.comp301.a09akari.model.Model;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Text;

public class PuzzleView implements FXComponent {

  private final ClassicMvcController controller;
  private final Model model;

  public PuzzleView(ClassicMvcController controller, Model model) {
    this.controller = controller;
    this.model = model;
  }

  @Override
  public Parent render() {

    GridPane grid = new GridPane();

    int height = model.getActivePuzzle().getHeight();
    int width = model.getActivePuzzle().getWidth();

    if (height <= 0 || width <= 0) {
      throw new IllegalStateException("Invalid puzzle dimensions");
    }

    grid.getChildren().clear();

    // making the grid
    for (int r = 0; r < height; r++) {
      for (int c = 0; c < width; c++) {
        StackPane cellPane = new StackPane();
        Rectangle cell = new Rectangle(40, 40);
        // border color
        cell.setStroke(Color.BLACK);

        CellType cellType = model.getActivePuzzle().getCellType(r, c);
        if (cellType == CellType.WALL) {
          cell.setFill(Color.GRAY);
          cellPane.getChildren().add(cell);
        } else if (cellType == CellType.CLUE) {
          cell.setFill(Color.BLACK);
          Text clueText = new Text(String.valueOf(model.getActivePuzzle().getClue(r, c)));
          clueText.setFill(Color.WHITE); // Set the text color to white
          cellPane.getChildren().addAll(cell, clueText);

          if (model.isClueSatisfied(r, c)) {
            cell.setFill(Color.BLUE);
          }

        } else if (cellType == CellType.CORRIDOR) {
          if (model.isLamp(r, c)) {
            if (model.isLampIllegal(r, c)) {
              cell.setFill(Color.RED);
            } else {
              cell.setFill(Color.SANDYBROWN);
            }
          } else if (model.isLit(r, c)) {
            cell.setFill(Color.YELLOW);
          } else {
            cell.setFill(Color.WHITE);
          }

          cellPane.getChildren().add(cell);
        }

        int finalRow = r;
        int finalCol = c;

        cell.setOnMouseClicked(e -> controller.clickCell(finalRow, finalCol));

        grid.add(cellPane, c, r);
      }
    }

    grid.setHgap(5);
    grid.setVgap(5);
    grid.setPadding(new Insets(10));

    // Create a StackPane to center the grid
    StackPane centerPane = new StackPane();
    centerPane.getChildren().add(grid);
    centerPane.setAlignment(Pos.CENTER);

    VBox vbox = new VBox();
    vbox.getChildren().add(centerPane);

    vbox.setPadding(new Insets(10, 50, 10, 120));

    return vbox;
  }
}
