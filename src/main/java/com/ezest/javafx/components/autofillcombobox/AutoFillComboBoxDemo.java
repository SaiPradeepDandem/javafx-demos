package com.ezest.javafx.components.autofillcombobox;

import java.util.List;

import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.control.ScrollPaneBuilder;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.HBoxBuilder;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.StackPaneBuilder;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javafx.util.StringConverter;

public class AutoFillComboBoxDemo extends Application {

	Stage stage;
	Scene scene;
	StackPane root;

	public static void main(String[] args) {
		Application.launch(args);
	}

	@Override
	public void start(Stage stage) throws Exception {
		this.stage = stage;
		configureScene();
		configureStage();
		// Logic starts
		VBox vb = new VBox();
		vb.setSpacing(10);

		final AutoFillComboBox<PostalCode> comboBox = new AutoFillComboBox<>();
		comboBox.setRecords(ComboBoxStubData.getCodes());
		comboBox.setOnlyAllowPredefinedItems(true);
		comboBox.setButtonCell(new ListCell<PostalCode>() {
			@Override
			protected void updateItem(PostalCode code, boolean arg1) {
				super.updateItem(code, arg1);
				if (!arg1) {
					setText(code.getCode());
				}
			}
		});
		comboBox.setConverter(new StringConverter<PostalCode>() {
			@Override
			public String toString(PostalCode code) {
				if (code != null) {
					return code.getCode() + " - " + code.getName();
				}
				return null;
			}

			@Override
			public PostalCode fromString(String value) {
				final List<PostalCode> list = comboBox.getUnfilteredList();
				if (!list.isEmpty() && value != null) {
					for (PostalCode postalCode : list) {
						if (postalCode != null && toString(postalCode).equals(value)) {
							return postalCode;
						}
					}
				}
				return null;
			}
		});

		Button okBtn = new Button("Show Value");
		okBtn.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent arg0) {
				System.out.println("Selected Value : " + comboBox.getValue());
			}
		});
		HBox hb = HBoxBuilder.create().spacing(15).children(new Label("Select postal code : "), comboBox, okBtn).build();

		final ComboBox<PostalCode> comboBox2 = new ComboBox<>();
		comboBox2.getItems().addAll(ComboBoxStubData.getCodes());
		comboBox2.setButtonCell(new ListCell<PostalCode>() {
			@Override
			protected void updateItem(PostalCode code, boolean arg1) {
				super.updateItem(code, arg1);
				if (!arg1) {
					setText(code.getCode());
				}
			}
		});
		comboBox2.setConverter(new StringConverter<PostalCode>() {
			@Override
			public String toString(PostalCode code) {
				if (code != null) {
					return code.getCode() + " - " + code.getName();
				}
				return null;
			}

			@Override
			public PostalCode fromString(String value) {
				final List<PostalCode> list = comboBox.getUnfilteredList();
				if (!list.isEmpty() && value != null) {
					for (PostalCode postalCode : list) {
						if (postalCode != null && toString(postalCode).equals(value)) {
							return postalCode;
						}
					}
				}
				return null;
			}
		});
		vb.getChildren().addAll(hb, comboBox2);
		root.getChildren().add(
				ScrollPaneBuilder.create().fitToHeight(true).fitToWidth(true)
						.content(StackPaneBuilder.create().minHeight(600).children(vb).build()).build());
	}

	private void configureStage() {
		stage.setTitle(this.getClass().getSimpleName());
		stage.setWidth(500);
		stage.setHeight(500);
		stage.setScene(this.scene);
		stage.show();
	}

	private void configureScene() {
		root = new StackPane();
		BorderPane bp = new BorderPane();
		bp.setCenter(root);
		bp.autosize();
		this.scene = new Scene(bp, Color.LINEN);
	}

}
