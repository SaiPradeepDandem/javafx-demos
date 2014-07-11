package com.ezest.javafx.layouts;

import javafx.application.Application;
import javafx.beans.binding.StringBinding;
import javafx.beans.property.DoubleProperty;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.ScrollPaneBuilder;
import javafx.scene.control.SplitPane;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.image.ImageViewBuilder;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.GridPaneBuilder;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.StackPaneBuilder;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javafx.util.StringConverter;

public class ImageLayoutsDemo extends Application {

	Stage stage;
	Scene scene;
	StackPane root;
	ScrollPane propScroll;
	ImageView image =ImageViewBuilder.create().image(new Image(ImageLayoutsDemo.class.getResourceAsStream("/images/Koala.jpg"))).build();
	
	StackPane dock = StackPaneBuilder.create().alignment(Pos.CENTER).build();

	public static void main(String[] args) {
		Application.launch(args);
	}

	@Override
	public void start(Stage stage) throws Exception {
		this.stage = stage;
		configureScene();
		configureStage();
		// Logic starts
		propScroll = ScrollPaneBuilder.create().fitToHeight(true).fitToWidth(true).build();
		dock.getChildren().add(image);
		SplitPane sp = new SplitPane();
		sp.getItems().addAll(dock,propScroll);
		
		GridPane gp = GridPaneBuilder.create().vgap(10).hgap(10).padding(new Insets(10)).build();
		propScroll.setContent(gp);
		root.getChildren().add(sp);
		
		gp.addRow(0, new Label("X : "),getBindedTextField(image.xProperty()));
		gp.addRow(1, new Label("Y : "),getBindedTextField(image.yProperty()));
		gp.addRow(2, new Label("Layout-X : "),getBindedTextField(image.layoutXProperty()));
		gp.addRow(3, new Label("Layout-Y : "),getBindedTextField(image.layoutYProperty()));
		gp.addRow(4, new Label("Translate-X : "),getBindedTextField(image.translateXProperty()));
		gp.addRow(5, new Label("Translate-Y : "),getBindedTextField(image.translateYProperty()));
		
		gp.addRow(6, new Label("Fit Width : "),getBindedTextField(image.fitWidthProperty()));
		gp.addRow(7, new Label("Fit Height : "),getBindedTextField(image.fitHeightProperty()));
		gp.addRow(8, new Label("Scale-X : "),getBindedTextField(image.scaleXProperty()));
		gp.addRow(9, new Label("Scale-Y : "),getBindedTextField(image.scaleYProperty()));
		
		CheckBox cb = new CheckBox();
		image.preserveRatioProperty().bindBidirectional(cb.selectedProperty());
		gp.addRow(10, new Label("Preserve ratio : "),cb);
		}

	private TextField getBindedTextField(DoubleProperty property){
		TextField xField = new TextField();
		xField.textProperty().bindBidirectional(property, new StringConverter<Number>() {
            @Override
            public Number fromString(String s) {
                double d = 0.0D;
                if (s != null && !s.trim().isEmpty()) {
                    try {
                        d = Double.parseDouble(s.trim());
                    } catch (NumberFormatException e) {
                        // ignore
                    }
                }
                return d;
            }
            @Override
            public String toString(Number n) {
                return n.toString();
            }
        });
		return xField;
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

