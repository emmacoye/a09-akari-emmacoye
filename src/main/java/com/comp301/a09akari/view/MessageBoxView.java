package com.comp301.a09akari.view;

import com.comp301.a09akari.controller.ClassicMvcController;
import com.comp301.a09akari.model.Model;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.control.Label;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;

public class MessageBoxView implements FXComponent {
  private final ClassicMvcController controller;
  private final Model model;

  public MessageBoxView(Model model, ClassicMvcController controller) {
    this.controller = controller;
    this.model = model;
  }

  @Override
  public Parent render() {
    VBox messagePane = new VBox();
    messagePane.setPadding(new Insets(5));
    messagePane.setAlignment(Pos.CENTER);


    // Add the puzzle index label
    int currentIndex = model.getActivePuzzleIndex() + 1; // Convert to 1-based index
    int totalPuzzles = model.getPuzzleLibrarySize();
    Label puzzleIndexLabel = new Label("Puzzle " + currentIndex + " of " + totalPuzzles);
    puzzleIndexLabel.setStyle("-fx-font-size: 14px; -fx-font-weight: bold;");

    if (model.isSolved()) {
      Text message = new Text("Puzzle Solved!");
      message.setStyle("-fx-font-size: 20px; -fx-font-weight: bold;");
      messagePane.getChildren().add(message);
    }

    messagePane.getChildren().add(puzzleIndexLabel);
    return messagePane;
  }
}
