package com.ezest.javafx.components.customtextfield;

import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.LabelBuilder;
import javafx.scene.layout.HBox;
import javafx.scene.layout.HBoxBuilder;
import javafx.scene.layout.StackPaneBuilder;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

public class CustomTextFieldDemo extends Application {

	Stage stage;
	Scene scene;
	VBox root;

	VBox vb1;
	VBox vb2;

	public static void main(String[] args) {
		Application.launch(args);
	}

	@Override
	public void start(Stage stage) throws Exception {
		this.stage = stage;
		vb1 = new VBox();
		vb1.setSpacing(8);
		vb2 = new VBox();
		vb2.setSpacing(8);
		configureScene();
		configureStage();
		configureScroller();
		root.getChildren().add(HBoxBuilder.create().spacing(20).children(vb1, vb2).build());
	}

	private void configureScroller() {
		vb1.getChildren().add(
LabelBuilder.create().text("Direct Pattern :").style("-fx-font-weight:bold;-fx-font-size:16px;").build());
		HBox hb1 = HBoxBuilder.create().spacing(10).children(new Label("DEFAULT   : "), new CustomTextField()).build();
		HBox hb2 = HBoxBuilder.create().spacing(10)
				.children(new Label("ALPHABETS   : "), new CustomTextField(CustomTextFieldType.ALPHABETS)).build();
		HBox hb3 = HBoxBuilder.create().spacing(10)
				.children(new Label("SMALL ALPHABETS   : "), new CustomTextField(CustomTextFieldType.ALPHABETS_SMALL)).build();
		HBox hb4 = HBoxBuilder.create().spacing(10)
				.children(new Label("CAPITAL ALPHABETS   : "), new CustomTextField(CustomTextFieldType.ALPHABETS_CAPITAL)).build();
		HBox hb5 = HBoxBuilder.create().spacing(10)
				.children(new Label("INTEGER_ONLY   : "), new CustomTextField(CustomTextFieldType.INTEGER_ONLY)).build();
		HBox hb6 = HBoxBuilder.create().spacing(10)
				.children(new Label("POSITIVE_INTEGER_ONLY   : "), new CustomTextField(CustomTextFieldType.POSITIVE_INTEGER_ONLY)).build();
		HBox hb7 = HBoxBuilder.create().spacing(10).children(new Label("NUMERIC   : "), new CustomTextField(CustomTextFieldType.NUMERIC))
				.build();
		HBox hb8 = HBoxBuilder.create().spacing(10)
				.children(new Label("POSITIVE_NUMERIC_ONLY   : "), new CustomTextField(CustomTextFieldType.POSITIVE_NUMERIC_ONLY)).build();
		HBox hb9 = HBoxBuilder.create().spacing(10)
				.children(new Label("COLOR_CODE_ONLY   : "), new CustomTextField(CustomTextFieldType.COLOR_CODE)).build();

		CustomTextField t = new CustomTextField();
		t.setRestrictSize(10);
		HBox hb10 = HBoxBuilder.create().spacing(10).children(new Label("SIZE_RESTRICT-10   : "), t).build();

		CustomTextField t1 = new CustomTextField();
		t1.setRegex("[a-zA-Z]*");
		HBox hb11 = HBoxBuilder.create().spacing(10).children(new Label("REGEX ([a-zA-Z]*)   : "), t1).build();

		vb1.getChildren().addAll(hb1, hb2, hb3, hb4, hb5, hb6, hb7, hb8, hb9, hb10, hb11);

		vb2.getChildren().add(LabelBuilder.create().text("Builder Pattern :").style("-fx-font-weight:bold;-fx-font-size:16px;").build());
		HBox hbb1 = HBoxBuilder.create().spacing(10).children(new Label("DEFAULT   : "), CustomTextFieldBuilder.create().build()).build();
		HBox hbb2 = HBoxBuilder.create().spacing(10)
				.children(new Label("ALPHABETS   : "), CustomTextFieldBuilder.create().type(CustomTextFieldType.ALPHABETS).build()).build();
		HBox hbb3 = HBoxBuilder
				.create()
				.spacing(10)
				.children(new Label("SMALL ALPHABETS   : "),
						CustomTextFieldBuilder.create().type(CustomTextFieldType.ALPHABETS_SMALL).build()).build();
		HBox hbb4 = HBoxBuilder
				.create()
				.spacing(10)
				.children(new Label("CAPITAL ALPHABETS   : "),
						CustomTextFieldBuilder.create().type(CustomTextFieldType.ALPHABETS_CAPITAL).build()).build();
		HBox hbb5 = HBoxBuilder.create().spacing(10)
				.children(new Label("INTEGER_ONLY   : "), CustomTextFieldBuilder.create().type(CustomTextFieldType.INTEGER_ONLY).build())
				.build();
		HBox hbb6 = HBoxBuilder
				.create()
				.spacing(10)
				.children(new Label("POSITIVE_INTEGER_ONLY   : "),
						CustomTextFieldBuilder.create().type(CustomTextFieldType.POSITIVE_INTEGER_ONLY).build()).build();
		HBox hbb7 = HBoxBuilder.create().spacing(10)
				.children(new Label("NUMERIC   : "), CustomTextFieldBuilder.create().type(CustomTextFieldType.NUMERIC).build()).build();
		HBox hbb8 = HBoxBuilder
				.create()
				.spacing(10)
				.children(new Label("POSITIVE_NUMERIC_ONLY   : "),
						CustomTextFieldBuilder.create().type(CustomTextFieldType.POSITIVE_NUMERIC_ONLY).build()).build();
		HBox hbb9 = HBoxBuilder.create().spacing(10)
				.children(new Label("COLOR_CODE_ONLY   : "), CustomTextFieldBuilder.create().type(CustomTextFieldType.COLOR_CODE).build())
				.build();

		HBox hbb10 = HBoxBuilder.create().spacing(10)
				.children(new Label("SIZE_RESTRICT-10   : "), CustomTextFieldBuilder.create().restrictSize(10).build()).build();

		HBox hbb11 = HBoxBuilder
				.create()
				.spacing(10)
				.children(new Label("REGEX ([a-zA-Z]*)   : "),
						CustomTextFieldBuilder
								.create()
								.regex("(([0-9]|0[0-9]|1[0-9]|2[0-3]))|(([0-9]|0[0-9]|1[0-9]|2[0-3])[:])|(^[0-2]?[0-3]:[0-5]$)|(^[0-2]?[0-3]:[0-5][0-9]$)")
								.build())
				.build();

		HBox hbb12 = HBoxBuilder.create().spacing(10)
				.children(new Label("TIME_ONLY   : "), CustomTextFieldBuilder.create().type(CustomTextFieldType.TIME_HHMM_FORMAT).build())
				.build();

		vb2.getChildren().addAll(hbb1, hbb2, hbb3, hbb4, hbb5, hbb6, hbb7, hbb8, hbb9, hbb10, hbb11, hbb12);
	}

	private void configureStage() {
		stage.setTitle("Custom Text Field Demo");
		stage.setWidth(700);
		stage.setHeight(500);
		stage.setScene(this.scene);
		stage.show();
	}

	private void configureScene() {
		root = new VBox();
		root.setPadding(new Insets(20));
		root.getChildren().add(
				StackPaneBuilder
						.create()
						.padding(new Insets(0, 0, 20, 0))
						.children(
								LabelBuilder.create().text("CustomTextField Demo").style("-fx-font-weight:bold;-fx-font-size:20px;")
										.build())
						.build());
		root.autosize();
		this.scene = new Scene(root, Color.LINEN);
	}

}
