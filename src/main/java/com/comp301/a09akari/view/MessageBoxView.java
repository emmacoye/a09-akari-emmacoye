package com.comp301.a09akari.view;

import com.comp301.a09akari.controller.ClassicMvcController;
import com.comp301.a09akari.model.Model;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
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
    VBox messagePane = new VBox(30);
    messagePane.setPadding(new Insets(10, 0, 0, 0));
    messagePane.setAlignment(Pos.CENTER);

    // Add the puzzle index label
    int currentIndex = model.getActivePuzzleIndex() + 1;
    int totalPuzzles = model.getPuzzleLibrarySize();
    Label puzzleIndexLabel = new Label("Puzzle " + currentIndex + " of " + totalPuzzles);
    puzzleIndexLabel.setStyle(
        "-fx-background-color: #f8f8ff; -fx-border-color: #dcdcdc; -fx-border-width: 2px; -fx-padding: 10px;");

    if (model.isSolved()) {
      Text message = new Text("Puzzle Solved!");
      HBox messageTextBox = new HBox(50);
      message.setStyle("-fx-font-size: 40px; -fx-font-weight: bold;");
      messageTextBox.getChildren().add(message);
      messageTextBox.setAlignment(Pos.CENTER);
      messagePane.getChildren().add(messageTextBox);
    }

    messagePane.getChildren().add(puzzleIndexLabel);

    return messagePane;
  }
}
