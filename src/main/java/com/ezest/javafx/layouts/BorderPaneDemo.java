package com.ezest.javafx.layouts;

import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.HPos;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.paint.CycleMethod;
import javafx.scene.paint.LinearGradient;
import javafx.scene.paint.Stop;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.stage.Stage;

public class BorderPaneDemo extends Application {

	@Override
	public void start(Stage stage) throws Exception {
		Group root  = new Group();
		Scene scene = new Scene(root, 500, 500, Color.BISQUE);
		
		//Dimension dimension = Toolkit.getDefaultToolkit().getScreenSize();
		
		root.getChildren().add( getBorderPane(scene) );
		stage.setTitle("JavaFx Layout Demo");
		stage.setScene(scene);
		//stage.setResizable(false);
		stage.show();
	}
	
	private Node getBorderPane(Scene scene){
		BorderPane layout = new BorderPane();
		layout.setTop(getHeader(scene));
		layout.setBottom(new Rectangle(scene.getWidth(), 50, Color.DARKCYAN));
		layout.setCenter(getCenter(scene));
		layout.setLeft(getLeftPane(scene));
		layout.setRight(new Rectangle(50, scene.getHeight()-100, Color.DARKTURQUOISE));
		
		
		return layout;
	}

	private Node getHeader(Scene scene){
		HBox hb = new HBox();
		hb.setPadding(new Insets(15, 12, 15, 12));
		hb.setSpacing(10);
		hb.setMaxWidth(scene.getWidth());
		hb.setMaxHeight(50);
		hb.setStyle("-fx-background-color: #336699");
		
		Button buttonCurrent = new Button("Current");
		buttonCurrent.setMaxSize(100, 10);
		buttonCurrent.setPrefWidth(100);

		Button buttonProjected = new Button("Projected");
		buttonProjected.setMaxSize(100, 10);
		buttonProjected.setPrefWidth(100);
		
		hb.getChildren().addAll(buttonCurrent, buttonProjected);
		
		Rectangle helpIcon = new Rectangle(35.0, 25.0);
		helpIcon.setFill(new LinearGradient(0,0,0,1, true, CycleMethod.NO_CYCLE,
		    new Stop[]{
		    new Stop(0,Color.web("#4977A3")),
		    new Stop(0.5, Color.web("#B0C6DA")),
		    new Stop(1,Color.web("#9CB6CF")),}));
		helpIcon.setStroke(Color.web("#D0E6FA"));
		helpIcon.setArcHeight(3.5);
		helpIcon.setArcWidth(3.5);
		
		Text helpText = new Text("?   ");
		helpText.setFont(Font.font("Amble Cn", FontWeight.BOLD, 18));
		helpText.setFill(Color.WHITE);
		helpText.setStroke(Color.web("#7080A0")); 
		
		StackPane stack = new StackPane();
		stack.getChildren().addAll(helpIcon,helpText);
		stack.setAlignment(Pos.CENTER_RIGHT);
		
		HBox.setHgrow(stack, Priority.ALWAYS);
		
		hb.getChildren().add(stack);
		
		return hb;
	}
	
	private Node getLeftPane(Scene scene){
		VBox vb = new VBox();
		
		Text title = new Text("Data");
		title.setFont(Font.font("Amble CN", FontWeight.BOLD, 14));
		vb.getChildren().add(title);
		
		Text options[] = new Text[] {
                new Text("  Sales"),
                new Text("  Marketing"),
                new Text("  Distribution"),
                new Text("  Costs")};

		for (int i=0; i<4; i++) {
		    vb.getChildren().add(options[i]);
		}
		return vb;
	}
	
	private Node getCenter(Scene scene){
		GridPane grid = new GridPane();
		grid.setHgap(10);
		grid.setVgap(30);
		//grid.setGridLinesVisible(true);
		grid.setPadding(new Insets(0, 0, 0, 10));
		
		
		grid.add(new Text("Name"), 0, 0, 1, 2); // 1st column, 1st row , col span, row span
		grid.add(new Text(":"), 1, 0); // 2nd column, 1st row
		grid.add(new Text("Sai Pradeep Dandem"), 2, 0); // 3rd column, 1st row
		
		//grid.add(new Text("Address"), 0, 1); // 1st column, 2nd row
		grid.add(new Text(":"), 1, 1); // 2nd column, 2nd row
		Text tt = new Text("Fatima Nagar in in in in in in  in in in in in in  in in in in in in  in in in in in in ");
		tt.setWrappingWidth(100);
		grid.add(tt, 2, 1, 2, 1); // 3rd column, 2nd row , col span, row span
		
		Button btn1 = new Button("1One");
		btn1.setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent paramT) {
				System.out.println("i am one..");
				
			}
		});
		Button btn2 = new Button("Two 2");
		btn2.setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent paramT) {
				System.out.println("i am two..");
				
			}
		});
		
		grid.add(btn1, 0, 2); // 1st column, 3rd row
		grid.add(new Text(":"), 1, 2); // 2nd column, 3rd row
		grid.add(btn2, 2, 2); // 3rd column, 3rd row
		grid.add(new Text("Out"), 3, 2); // 4th column, 3rd row
		grid.setGridLinesVisible(true);
		grid.setOnMouseClicked(new EventHandler<MouseEvent>() {
			@Override
			public void handle(MouseEvent t) {
				if (t.getClickCount() >= 2) {
					System.out.println( ((GridPane)t.getSource()).getRowIndex((Node)t.getTarget()));
					System.out.println( t.getTarget());
					System.out.println( ((GridPane)t.getSource()).getRowConstraints());
				}
			}
		});
		
		ColumnConstraints c = new ColumnConstraints();
		c.setHalignment(HPos.LEFT);
		grid.getColumnConstraints().add(c);
		
		return grid;
	}
	public static void main(String[] args) {
		Application.launch(args);
	}

}
