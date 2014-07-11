package com.ezest.javafx.demogallery.controls;


import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextArea;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class HTMLEditorSample extends Application {
	
	@Override
	public void start(Stage stage) {
		stage.setTitle("HTMLEditor Sample");
		stage.setWidth(500);
		stage.setHeight(500);
		Scene scene = new Scene(new Group());

		VBox root = new VBox();
		root.setPadding(new Insets(8, 8, 8, 8));
		root.setSpacing(5);
		root.setAlignment(Pos.BOTTOM_LEFT);

		final HTMLEditorControl htmlEditor = new HTMLEditorControl();
		htmlEditor.setPrefHeight(245);
		//htmlEditor.setHtmlText("");
		
		//htmlEditor.setHtmlText(null);
		htmlEditor.setHtmlText("<html><body><head></head>Hello<body></html>");
		
		
		
		final TextArea htmlCode = new TextArea();
		htmlCode.setWrapText(true);

		ScrollPane scrollPane = new ScrollPane();
		scrollPane.getStyleClass().add("noborder-scroll-pane");
		scrollPane.setContent(htmlCode);
		scrollPane.setFitToWidth(true);
		scrollPane.setPrefHeight(180);

		Button showHTMLButton = new Button("Produce HTML Code");
		root.setAlignment(Pos.CENTER);
		showHTMLButton.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent arg0) {
				htmlCode.setText(htmlEditor.getHtmlText());
			}
		});

		root.getChildren().addAll(htmlEditor, showHTMLButton, scrollPane);
		scene.setRoot(root);
		stage.setScene(scene);
		stage.show();
	}

	public static void main(String[] args) {
		launch(args);
	}
}


