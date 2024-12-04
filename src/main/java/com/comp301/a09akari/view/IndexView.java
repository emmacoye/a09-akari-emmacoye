package com.comp301.a09akari.view;

import com.comp301.a09akari.controller.ControllerImpl;
import com.comp301.a09akari.model.Model;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.layout.HBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontPosture;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;

public class IndexView implements FXComponent {
    private final ControllerImpl controller;
    private Model model;

    public IndexView(ControllerImpl controller, Model model) {
        this.controller = controller;
        this.model = model;
    }

    @Override
    public Parent render() {
        HBox indexText = new HBox();
        indexText.setAlignment(Pos.TOP_CENTER);

        Text puzzleText = new Text("Puzzle: ");
        puzzleText.setFont(Font.font("verdana", FontWeight.BOLD, FontPosture.REGULAR, 20));
        indexText.getChildren().add(puzzleText);
        puzzleText.setFill(Color.STEELBLUE);

        Text index = new Text(Integer.toString(model.getActivePuzzleIndex() + 1));
        index.setFont(Font.font("verdana", FontWeight.BOLD, FontPosture.REGULAR, 20));
        indexText.getChildren().add(index);
        index.setFill(Color.STEELBLUE);

        Text ofText = new Text(" of ");
        ofText.setFont(Font.font("verdana", FontWeight.BOLD, FontPosture.REGULAR, 20));
        indexText.getChildren().add(ofText);
        ofText.setFill(Color.STEELBLUE);

        Text total = new Text(Integer.toString(model.getPuzzleLibrarySize()));
        total.setFont(Font.font("verdana", FontWeight.BOLD, FontPosture.REGULAR, 20));
        total.setFill(Color.STEELBLUE);

        indexText.getChildren().add(total);

        indexText.setPadding(new Insets(10, 10, 10, 10));

        return indexText;
    }
}
