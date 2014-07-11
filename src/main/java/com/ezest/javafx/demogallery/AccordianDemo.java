package com.ezest.javafx.demogallery;


import com.ezest.javafx.components.AccordionComponent;
import com.ezest.javafx.components.TabPaneComponent;
import com.ezest.javafx.components.AccordionComponent.AccordionPane;
import com.ezest.javafx.fxcalendar.FXCalendar;
import com.javafx.experiments.scenicview.ScenicView;

import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Accordion;
import javafx.scene.control.Button;
import javafx.scene.control.Tab;
import javafx.scene.control.TextField;
import javafx.scene.control.TitledPane;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Line;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Text;
import javafx.stage.Screen;
import javafx.stage.Stage;

public class AccordianDemo extends Application {

	Stage stage;
	Scene scene;
	FlowPane root;
	TabPaneComponent detailTabPane ;
	
	public static void main(String[] args) {
		Application.launch(args);
	}
	
	@Override
	public void start(Stage stage) throws Exception {
		this.stage = stage;
		configureScene();
		configureStage();
		
		AccordionComponent comp =  new AccordionComponent();
		VBox vBox = new VBox();
		vBox.getChildren().add(new Text("One"));
		vBox.getChildren().add(new Text("Two"));
		vBox.getChildren().add(new Text("Three"));
		vBox.getChildren().add(new Text("Four"));
		vBox.getChildren().add(new Text("Five"));
		vBox.getChildren().add(new Text("Six"));
		
		AccordionPane pane = comp.createPane( "One",true);
		pane.setContent(vBox);
		
		VBox vBox1 = new VBox();
		vBox1.getChildren().add(new Text("One"));
		vBox1.getChildren().add(new Text("Two"));
		vBox1.getChildren().add(new Text("Three"));
		vBox1.getChildren().add(new Text("Four"));
		vBox1.getChildren().add(new Text("Five"));
		vBox1.getChildren().add(new Text("Six"));
		
		AccordionPane pane2 = comp.createPane( "Two",true);
		pane2.setContent(vBox1);
		
		
		VBox vBox2 = new VBox();
		vBox2.getChildren().add(new Text("One"));
		vBox2.getChildren().add(new Text("Two"));
		vBox2.getChildren().add(new Text("Three"));
		vBox2.getChildren().add(new Text("Four"));
		vBox2.getChildren().add(new Text("Five"));
		vBox2.getChildren().add(new Text("Six"));
		
		TitledPane tp = new TitledPane();
		tp.setContent(vBox2);
		
		Group gp = new Group();
		HBox hb = new HBox();
		
		StackPane back = new StackPane();
		back.getStyleClass().add("dateInput");
		back.setPrefSize(150, 24);
		
		TextField txt = new TextField();
		txt.setTranslateX(1);
		txt.setTranslateY(1);
		txt.setPrefColumnCount(10);
		txt.getStyleClass().add("my-field");
		hb.getChildren().addAll(txt,getDateImage());
		gp.getChildren().addAll(back,hb);
		
		
		
		
		
		createDetailPane();
		
		HBox hb1 = new HBox();
		hb1.getChildren().addAll(new Text("Enter date : "), new FXCalendar());
		
		root.getChildren().addAll(comp,tp,gp,hb1);
		
		buildCusTree();
	}

	private void buildCusTree() {
		VBox vBox = new VBox();
		for (int i = 0; i < 10; i++) {
			VBox vBox2 = new VBox();
			vBox2.getChildren().add(new Text("One"));
			vBox2.getChildren().add(new Text("Two"));
			vBox2.getChildren().add(new Text("Three"));
			vBox2.getChildren().add(new Text("Four"));
			vBox2.getChildren().add(new Text("Five"));
			vBox2.getChildren().add(new Text("Six"));
			
			TitledPane tp = new TitledPane();
			tp.setPrefWidth(200);
			//tp.setExpanded(i%2==0 ? true :false);
			tp.setText("Title Pane "+i);
			tp.setContent(vBox2);
			
			vBox.getChildren().add(tp);
		}
		
		root.getChildren().add(vBox);
	}

	private Group getDateImage(){
		Group gp = new Group();
		StackPane img = new StackPane();
		img.setPrefSize(15.0, 15.0);
		img.getStyleClass().add("imgStyle");
		img.setAlignment(Pos.TOP_LEFT);
		
		Line l = getVerticalLine();
        l.setTranslateX(3.75);
        
        Line l1 = getVerticalLine();
        l1.setTranslateX(7.5);
        
        Line l2 = getVerticalLine();
        l2.setTranslateX(11.25);
        
        Line l3 = getHorizontalLine();
        l3.setTranslateY(3.75);
        
        Line l4 = getHorizontalLine();
        l4.setTranslateY(7.5);
        
        Line l5 = getHorizontalLine();
        l5.setTranslateY(11.25);
        
        Circle c = new Circle();
        c.setRadius(1.875);
        c.setStroke(Color.BLACK);
        c.setTranslateX(11.25);
        c.setTranslateY(3.75);
        c.setFill(Color.TRANSPARENT);
        img.getChildren().addAll(l,l1,l2,l3,l4,l5,c);
        
        gp.getChildren().add(img);
        gp.setTranslateX(5);
        gp.setTranslateY(1);
        return gp;
	}
	private Line getVerticalLine(){
		Line l = new Line();
		l.getStyleClass().add("imgLine");
		l.setStartX(0);
        l.setStartY(0);
        l.setEndX(0);
        l.setEndY(15.0);
        l.setSmooth(true);
        return l;
	}
	
	private Line getHorizontalLine(){
		Line l = new Line();
		l.getStyleClass().add("imgLine");
		l.setStartX(0);
        l.setStartY(0);
        l.setEndX(15.0);
        l.setEndY(0);
        l.setSmooth(true);
        return l;
	}
	private void configureStage(){
		stage.setTitle("Accordion Demo");
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
		scene.getStylesheets().add("styles/sample.css");
		scene.getStylesheets().add("styles/calendar_styles.css");
		ScenicView.show(scene);
	}
	
	private Node createDetailPane() {
		VBox node = new VBox();
		node.setPrefWidth(400d);
		node.setPadding(new Insets(0, 10, 10, 10));
		
		// Creating a new tab pane component for patient.
		detailTabPane = new TabPaneComponent(500,300);
		createTab(380,"_Description");
		createTab(380,"_History");
		createTab(380,"_Overview");
		createTab(380,"_Journal");
		createTab(380,"_Medication");
		createTab(380,"_Description");
		createTab(380,"_History");
		createTab(380,"_Overview");
		createTab(380,"_Journal");
		createTab(380,"_Medication");
		// Selecting the first tab as default.
		detailTabPane.selectFirstTab();
		detailTabPane.getTabs().get(1).setDisable(true);
		
		node.getChildren().add(detailTabPane);
		root.getChildren().add(node);
		return node;
	}
	private Tab createTab(double height,String text){
		Tab tab = detailTabPane.createNewTab(true, text);
		StackPane sp = new StackPane();
		sp.setPrefHeight(height );
		sp.setPrefWidth(800);
		sp.getChildren().add(new Text(text));
		tab.setContent( sp );
		return tab;
	}
	
	

}