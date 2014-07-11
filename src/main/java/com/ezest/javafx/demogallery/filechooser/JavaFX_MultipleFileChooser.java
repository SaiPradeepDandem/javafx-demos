package com.ezest.javafx.demogallery.filechooser;

import java.io.File;
import java.util.List;

import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.stage.DirectoryChooser;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

public class JavaFX_MultipleFileChooser extends Application {

	List<File> fileList;

	/**
	 * @param args
	 *            the command line arguments
	 */
	public static void main(String[] args) {
		launch(args);
	}

	@Override
	public void start(Stage primaryStage) {
		primaryStage.setTitle("Multiple File Chooser");

		final VBox labelFile = new VBox();

		Button btn = new Button();
		btn.setText("Open FileChooser to select multiple files");
		btn.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {

				// Set extension filter
				// FileChooser.ExtensionFilter extFilter = new FileChooser.ExtensionFilter("AVI files (*.avi)", "*.avi");
				FileChooser.ExtensionFilter extFilter = new FileChooser.ExtensionFilter("All files", "*.*");

				FileChooser fileChooser = new FileChooser();
				fileChooser.getExtensionFilters().add(extFilter);

				/* Show open file dialog to select multiple files. */
				fileList = fileChooser.showOpenMultipleDialog(null);
				if (fileList != null && fileList.size() > 0) {
					labelFile.getChildren().clear();
					for (File file : fileList) {
						labelFile.getChildren().add(new Label(file.getPath()));
					}
				}
			}
		});

		VBox vBox = new VBox();
		vBox.setPadding(new Insets(10));
		vBox.setSpacing(10);
		vBox.getChildren().addAll(btn, labelFile);

		StackPane root = new StackPane();
		root.getChildren().add(vBox);
		primaryStage.setScene(new Scene(root, 300, 250));
		primaryStage.show();
	}
}
