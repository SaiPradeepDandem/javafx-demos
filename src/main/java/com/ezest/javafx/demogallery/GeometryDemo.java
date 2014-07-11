package com.ezest.javafx.demogallery;

import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.geometry.VPos;
import javafx.scene.Cursor;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Tooltip;
import javafx.scene.effect.Reflection;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.TilePane;
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


public class GeometryDemo extends Application{

	public static void main(String[] args) {
		Application.launch(args);
	}

	@Override
	public void start(Stage stage) throws Exception {
		Group root  = new Group();
		root.getStyleClass().add("mainStage");
		Scene scene = new Scene(root, 1024, 600, Color.CORNSILK);
		scene.getStylesheets().add("styles/geometrydemo.css");
		
		
	    TilePane tp = new TilePane();
		tp.setPrefColumns(2);
		tp.setVgap(30);
		tp.setHgap(15);
		tp.setPadding(new Insets(10));
		
		tp.getChildren().addAll(getNode1());
		tp.getChildren().addAll(getNode2());
		tp.getChildren().addAll(getNode3());
		
	    root.getChildren().addAll( tp );
	    stage.setTitle("JavaFx Geometry Demo");
		stage.setScene(scene);
		stage.show();
	}
	
	public Node getNode1(){
		Group group = new Group();
		
		HBox hbox = new HBox();
		hbox.setPadding(new Insets(15, 12, 15, 12));
		hbox.setSpacing(10);
		hbox.setStyle("-fx-background-color: rosybrown");
		hbox.getStyleClass().add("roundedCorner");
		hbox.setPrefWidth(300);
		hbox.setPrefHeight(80);
		
		Label tx = new Label("StackPane Positioning");
		tx.setTooltip( new Tooltip("Hbox aligning to Pos.BOTTOM_RIGHT"));
		hbox.getChildren().addAll(tx);
		
		StackPane stack = new StackPane();
		Button b = new Button("X");
		stack.getChildren().add(b);
		stack.setAlignment(Pos.CENTER_RIGHT);     // Right-justify nodes in stack
        HBox.setHgrow(stack, Priority.ALWAYS);    // Give stack any extra space
		hbox.getChildren().add(stack);            // Add to HBox from Example 2
		
		hbox.setAlignment(Pos.BOTTOM_RIGHT);
		
		//hbox.setNodeVpos(VPos.BASELINE);
		
		group.getChildren().add(hbox);
		return group;
	}

	public Node getNode2(){
		HBox hbox = new HBox();
		hbox.setPadding(new Insets(15, 12, 15, 12));
		hbox.setSpacing(10);
		hbox.setStyle("-fx-background-color:gold;");
		hbox.getStyleClass().add("roundedCorner");
		hbox.setPrefWidth(300);
		hbox.setPrefHeight(200);
		
		Text tx = new Text("Button Aligning");
		
		hbox.getChildren().add(tx);
		
		 
		Button b = new Button("X");
		b.setPrefHeight(50);
		b.setPrefWidth(100);
		b.setAlignment(Pos.TOP_RIGHT);
		hbox.getChildren().add(b);           
		hbox.getChildren().add(new Text("Chekcing"));            
		
		hbox.setAlignment(Pos.BOTTOM_RIGHT); 
		return hbox;
	}

	public Node getNode3(){
		HBox hbox = new HBox();
		hbox.setPadding(new Insets(15, 12, 15, 12));
		hbox.setSpacing(10);
		hbox.setStyle("-fx-background-color:darkkhaki;");
		hbox.getStyleClass().add("roundedCorner");
		hbox.setPrefWidth(300);
		hbox.setPrefHeight(200);
		
		Text tx = new Text("Button Aligning");
		hbox.getChildren().add(tx);
		
		Button b = new Button("X");
		b.setPrefHeight(50);
		b.setPrefWidth(100);
		b.setAlignment(Pos.TOP_RIGHT);
		
		
		hbox.getChildren().add(b);           
		System.out.println("Total : "+hbox.getPrefWidth());
		System.out.println("Label : "+tx.getLayoutBounds().getWidth());
		System.out.println("Button: "+b.getPrefWidth());
		System.out.println("Total: "+((hbox.getPrefWidth()-tx.getLayoutBounds().getWidth()) - (b.getPrefWidth()+5)));
		b.setTranslateX( (hbox.getPrefWidth()-tx.getLayoutBounds().getWidth()-25) - (b.getPrefWidth()+5));
		//b.setTranslateY(5);
		
		return hbox;
	}

	
}

