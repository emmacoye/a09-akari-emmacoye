package com.comp301.a09akari.view;

import com.comp301.a09akari.controller.ClassicMvcController;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;

public class ControllerView implements FXComponent {

  private final ClassicMvcController controller;

  public ControllerView(ClassicMvcController controller) {
    this.controller = controller;
  }

  @Override
  public Parent render() {
    HBox controlBox = new HBox(10);
    controlBox.setPadding(new Insets(10));
    controlBox.setAlignment(Pos.BOTTOM_CENTER);

    Button previous = new Button("Previous");
    Button next = new Button("Next");
    Button random = new Button("Random");
    Button reset = new Button("Reset");

    // Set button size
    previous.setPrefWidth(150);
    previous.setPrefHeight(40);
    next.setPrefWidth(150);
    next.setPrefHeight(40);
    random.setPrefWidth(150);
    random.setPrefHeight(40);
    reset.setPrefWidth(150);
    reset.setPrefHeight(40);

    // Set font size for button text
    previous.setStyle("-fx-font-size: 16px;");
    next.setStyle("-fx-font-size: 16px;");
    random.setStyle("-fx-font-size: 16px;");
    reset.setStyle("-fx-font-size: 16px;");

    previous.setOnAction(e -> controller.clickPrevPuzzle());
    next.setOnAction(e -> controller.clickNextPuzzle());
    random.setOnAction(e -> controller.clickRandPuzzle());
    reset.setOnAction(e -> controller.clickResetPuzzle());

    controlBox.getChildren().addAll(previous, next, random, reset);
    return controlBox;
  }
}
