package com.ezest.javafx.demogallery.shapes;

import javafx.application.Application;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Cursor;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.StackPaneBuilder;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.CircleBuilder;
import javafx.scene.shape.LineTo;
import javafx.scene.shape.MoveTo;
import javafx.scene.shape.Path;
import javafx.scene.shape.PathBuilder;
import javafx.stage.Screen;
import javafx.stage.Stage;

public class ShapesDemo extends Application {

	Stage stage;
	Scene scene;
	FlowPane root;
	public static void main(String[] args) {
		Application.launch(args);
	}
	
	@Override
	public void start(Stage stage) throws Exception {
		this.stage = stage;
		configureScene();
		configureStage();
		root.setHgap(10);
		root.setVgap(10);
		createTriangle();
		createPlusShape();
		createRectangle();
		createSquare();
		createOctagone();
		createCurve();
		createCircle();
		createPlus();
		createMinus();
	}

	
	private void createPlus() {
		Path plus = PathBuilder.create()
				.elements(new MoveTo(5, 0),
						new LineTo(5, 5),
						new LineTo(0, 5),
						new LineTo(0, 10),
						new LineTo(5, 10),
						new LineTo(5, 15),
						new LineTo(10, 15),
						new LineTo(10, 10),
						new LineTo(15, 10),
						new LineTo(15, 5),
						new LineTo(10, 5),
						new LineTo(10, 0),
						new LineTo(5, 0))
				.stroke(Color.web("#000000"))
				.fill(Color.web("#FFFFFF"))
				.strokeWidth(1)
				.rotate(45)
				.cursor(Cursor.HAND)
				.build();
		
		Circle c = CircleBuilder.create().radius(13).style("-fx-fill:-fx-base;").build() ;
		StackPane sp = StackPaneBuilder.create()
									   .maxHeight(26).maxWidth(26)
									   .prefHeight(26).prefWidth(26)
									   .children(c,plus).build();
		root.getChildren().add(sp);
	}
	
	private void createMinus() {
		Path minus = PathBuilder.create()
				.elements(new MoveTo(0, 0),
						new LineTo(0, 5),
						new LineTo(15, 5),
						new LineTo(15, 0),
						new LineTo(0, 0))
				.stroke(Color.web("#000000"))
				.fill(Color.web("#FFFFFF"))
				.strokeWidth(1)
				.cursor(Cursor.HAND)
				.build();
		Circle c = CircleBuilder.create().radius(13).style("-fx-fill:-fx-base;").build() ;
		StackPane sp = StackPaneBuilder.create().styleClass("close-btn")
									   .maxHeight(26).maxWidth(26)
									   .prefHeight(26).prefWidth(26)
									   .children(c,minus).build();
		
		root.getChildren().add(sp);
	}

	private void createCircle() {
		StackPane sp = new StackPane();
		sp.setPrefSize(60, 60);
		sp.getStyleClass().add("circle-arc-shape");
		
		StackPane sp1 = new StackPane();
		sp1.setStyle("-fx-border-color:red;-fx-border-width:1px;");
		sp1.getChildren().add(sp);
		root.getChildren().add(sp1);
	}

	private void configureStage(){
		stage.setTitle("Shapes Demo");
		stage.setX(0);
	    stage.setY(0);
	    stage.setWidth(Screen.getPrimary().getVisualBounds().getWidth());
	    stage.setHeight(Screen.getPrimary().getVisualBounds().getHeight());
	    stage.setScene(this.scene);
	    stage.show();
	}
	
	private void configureScene(){
		root = new FlowPane();
		root.autosize();
		this.scene = new Scene(root, Color.LINEN);
		scene.getStylesheets().add("styles/shapes.css");
	}

	private void createTriangle() {
		Group gp = new Group();
		StackPane leftSide = new StackPane();
		leftSide.setPrefSize(18, 30);
		leftSide.getStyleClass().add("triangle-shape");
		gp.getChildren().add(leftSide);
		
		StackPane rightSide = new StackPane();
		rightSide.setPrefSize(18, 30);
		rightSide.getStyleClass().add("triangle-shape");
		rightSide.setRotate(180);
		
		root.getChildren().addAll(gp,rightSide);
	}

	private void createPlusShape() {
		Group gp = new Group();
		StackPane plus1 = new StackPane();
		plus1.setPrefSize(30, 30);
		plus1.getStyleClass().add("plus-shape");
		gp.getChildren().add(plus1);
		
		StackPane plus2 = new StackPane();
		plus2.setPrefSize(18, 30);
		plus2.getStyleClass().add("plus-shape");
		
		StackPane plus3 = new StackPane();
		plus3.setPrefSize(18, 30);
		plus3.getStyleClass().add("plus-shape-with-curve");
		
		StackPane plus4 = new StackPane();
		plus4.setMaxSize(12, 12);
		plus4.setMinSize(12, 12);
		plus4.setRotate(45);
		plus4.getStyleClass().add("plus-shape-with-curve");
		
		root.getChildren().addAll(gp,plus2,plus3,plus4);
	}

	private void createRectangle() {
		StackPane sp = new StackPane();
		sp.setPrefSize(60, 20);
		sp.getStyleClass().add("rectangle-shape");
		root.getChildren().add(sp);
	}
	private void createSquare() {
		StackPane sp = new StackPane();
		sp.setPrefSize(40, 40);
		sp.getStyleClass().add("square-shape");
		root.getChildren().add(sp);
	}
	private void createOctagone() {
		StackPane sp = new StackPane();
		sp.setPrefSize(60, 60);
		sp.getStyleClass().add("octagone-shape");
		root.getChildren().add(sp);
	}
	private void createCurve() {
		StackPane sp = new StackPane();
		sp.setPrefSize(60, 60);
		sp.getStyleClass().add("curve-shape");
		root.getChildren().add(sp);
		
		MainModel model = new MainModel();
		final Button btn1 =new Button("BUtton 1");
		model.flagProperty().bind(btn1.armedProperty());
		
		Button btn2 =new Button("BUtton 2");
		btn2.setOnAction(new EventHandler<ActionEvent>() {
			
			@Override
			public void handle(ActionEvent paramT) {
				System.out.println("Button two clicked");
				btn1.arm();
			}
		});
		root.getChildren().addAll(btn1,btn2);
		
		
	}

}

class MainModel{
	private SimpleBooleanProperty flag = new SimpleBooleanProperty();
	
	public MainModel(){
		flag.addListener(new ChangeListener<Boolean>() {

			@Override
			public void changed(
					ObservableValue<? extends Boolean> paramObservableValue,
					Boolean paramT1, Boolean paramT2) {
				System.out.println("Modle changed....");
				
			}
		});
	}
	
	public SimpleBooleanProperty flagProperty(){
		return flag;
	}
}

