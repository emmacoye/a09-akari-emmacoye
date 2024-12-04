package com.comp301.a09akari.view;

import com.comp301.a09akari.SamplePuzzles;
import com.comp301.a09akari.controller.ControllerImpl;
import com.comp301.a09akari.model.*;
import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;

import java.util.Objects;

public class AppLauncher extends Application {
  @Override
  public void start(Stage stage) {
    // TODO: Create your Model, View, and Controller instances and launch your GUI
    PuzzleLibrary library = new PuzzleLibraryImpl();
    library.addPuzzle(new PuzzleImpl(SamplePuzzles.PUZZLE_01));
    library.addPuzzle(new PuzzleImpl(SamplePuzzles.PUZZLE_02));
    library.addPuzzle(new PuzzleImpl(SamplePuzzles.PUZZLE_03));
    library.addPuzzle(new PuzzleImpl(SamplePuzzles.PUZZLE_04));
    library.addPuzzle(new PuzzleImpl(SamplePuzzles.PUZZLE_05));

    ModelImpl model = new ModelImpl(library);
    ControllerImpl controller = new ControllerImpl(model);
    PuzzleView puzzleView = new PuzzleView(controller, model);
    ControllerView controllerView = new ControllerView(controller);
    MessageBoxView messageBoxView = new MessageBoxView(model, controller);

    BorderPane root = new BorderPane();

    root.setTop(messageBoxView.render());

    StackPane centerPane = new StackPane();
    centerPane.getChildren().add(puzzleView.render());
    StackPane.setAlignment(puzzleView.render(), Pos.CENTER);
    centerPane.setPadding(new Insets(10, 10, 10, 10));
    root.setCenter(centerPane);

    root.setBottom(controllerView.render());

    Scene scene = new Scene(root, 600, 600);
    stage.setScene(scene);
    stage.setTitle("Akari Game");

    stage.show();

    model.addObserver(
        new ModelObserver() {
          @Override
          public void update(Model model) {
            root.setCenter(puzzleView.render());
            root.setTop(messageBoxView.render());
          }
        });
  }

  public static void main(String[] args) {
    launch();
  }
}
