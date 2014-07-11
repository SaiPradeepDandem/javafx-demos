package com.ezest.javafx.demogallery.layouts;

import java.util.Iterator;

import javafx.application.Application;

import javafx.beans.value.*;

import javafx.scene.*;

import javafx.scene.control.*;

import javafx.scene.layout.*;

import javafx.scene.shape.Rectangle;

import javafx.scene.text.Text;

import javafx.stage.Stage;

public class GridClipping extends Application {

	String buttonText[] = "The quick brown fox jumped over the lazy dog".split(" ");

	String[] colors = { "aqua", "coral", "cornsilk", "cornflowerblue" };

	@Override
	public void start(Stage stage) throws Exception {

		final GridPane grid = new GridPane();

		grid.addRow(0, createHBox("aqua"), createHBox("coral"));

		grid.addRow(1, createHBox("cornsilk"), createHBox("cornflowerblue"));

		grid.setStyle("-fx-border-color: red;");

		final GridPane gridWithClippedBoxes = new GridPane();

		gridWithClippedBoxes.addRow(0, createClipWrappedHBox("aqua"), createClipWrappedHBox("coral"));

		gridWithClippedBoxes.addRow(1, createClipWrappedHBox("cornsilk"), createClipWrappedHBox("cornflowerblue"));

		gridWithClippedBoxes.setStyle("-fx-border-color: red;");

		final RadioButton noClip = new RadioButton("No Clip");

		final RadioButton restrictGridSize = new RadioButton("Restrict Max Grid Size (doesn't work)");

		final RadioButton clipGrid = new RadioButton("Clip Grid");

		final RadioButton clipHBoxes = new RadioButton("Clip HBoxes");

		final RadioButton clipWrappedHBoxes = new RadioButton("Clip Wrapped HBoxes");

		final ToggleGroup clipRadios = new ToggleGroup();

		clipRadios.getToggles().setAll(

		noClip,

		restrictGridSize,

		clipGrid,

		clipHBoxes,

		clipWrappedHBoxes

		);

		final Rectangle gridClip = new Rectangle(0, 0, 100, 25);

		clipGrid.selectedProperty().addListener(new ChangeListener<Boolean>() {

			@Override
			public void changed(ObservableValue<? extends Boolean> ov, Boolean wasClipped, Boolean clipped) {

				if (clipped != null) {

					if (clipped) {

						grid.setClip(gridClip);

					} else {

						grid.setClip(null);

					}

				}

			}

		});

		restrictGridSize.selectedProperty().addListener(new ChangeListener<Boolean>() {

			@Override
			public void changed(ObservableValue<? extends Boolean> ov, Boolean wasClipped, Boolean clipped) {

				if (clipped != null) {

					if (clipped) {

						// this does not work in our case.

						// the minimum size of the grid components > the max size of the grid.

						// so the grid expands in size to fit the minimum size of it's components anyway.

						grid.setMaxSize(100, 25);

					} else {

						grid.setMaxSize(Double.MAX_VALUE, Double.MAX_VALUE);

					}

				}

			}

		});

		clipHBoxes.selectedProperty().addListener(new ChangeListener<Boolean>() {

			@Override
			public void changed(ObservableValue<? extends Boolean> ov, Boolean wasClipped, Boolean clipped) {

				if (clipped != null) {

					if (clipped) {

						for (Node gridCell : grid.getChildren()) {

							Rectangle cellClip = new Rectangle(100, 12);

							gridCell.setClip(cellClip);

						}

					} else {

						for (Node gridCell : grid.getChildren()) {

							gridCell.setClip(null);

						}

					}

				}

			}

		});

		final VBox layout = new VBox(10);

		clipWrappedHBoxes.selectedProperty().addListener(new ChangeListener<Boolean>() {

			@Override
			public void changed(ObservableValue<? extends Boolean> ov, Boolean wasClipped, Boolean clipped) {

				if (clipped != null) {

					if (clipped) {

						layout.getChildren().set(0, gridWithClippedBoxes);

					} else {

						layout.getChildren().set(0, grid);

					}

				}

			}

		});

		noClip.fire();

		layout.setStyle("-fx-background-color: cornsilk; -fx-padding: 10;");

		layout.getChildren().setAll(

		grid,

		noClip,

		restrictGridSize,

		clipGrid,

		clipHBoxes,

		clipWrappedHBoxes

		);

		stage.setScene(new Scene(layout));

		stage.show();

	}

	private Region createHBox(String webColor) {

		HBox box = new HBox(5);

		box.setStyle("-fx-background-color: " + webColor + ";");

		for (String text : buttonText) {

			box.getChildren().add(new Text(text));

		}

		box.setOpacity(0.5);

		return box;

	}

	private Region createClipWrappedHBox(String webColor) {

		return new ClippedNode(createHBox(webColor), 100, 12);

	}

	class ClippedNode extends Region {

		private final Node content;

		private final double width;

		private final double height;

		ClippedNode(Node content, double width, double height) {

			this.content = content;

			this.width = width;

			this.height = height;

			content.setClip(new Rectangle(width, height));

			getChildren().setAll(content);

		}

		@Override
		protected double computeMinWidth(double d) {
			return width;
		}

		@Override
		protected double computeMinHeight(double d) {
			return height;
		}

		@Override
		protected double computePrefWidth(double d) {
			return width;
		}

		@Override
		protected double computePrefHeight(double d) {
			return height;
		}

		@Override
		protected double computeMaxWidth(double d) {
			return width;
		}

		@Override
		protected double computeMaxHeight(double d) {
			return height;
		}

	}

	public static void main(String[] args) {
		Application.launch(args);
	}

}
