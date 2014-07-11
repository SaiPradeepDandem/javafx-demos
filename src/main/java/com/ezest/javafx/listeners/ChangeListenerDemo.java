package com.ezest.javafx.listeners;

import java.util.HashMap;
import java.util.Map;

import javafx.application.Application;
import javafx.beans.InvalidationListener;
import javafx.beans.Observable;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

public class ChangeListenerDemo extends Application {

	Stage stage;
	Scene scene;
	VBox root;
	VBox messages;
	SimpleBooleanProperty flag = new SimpleBooleanProperty();
	private SimpleObjectProperty<Map<String,Boolean>> responseMap = new SimpleObjectProperty<Map<String,Boolean>>();
	
	public static void main(String[] args) {
		Application.launch(args);
	}
	
	@Override
	public void start(Stage stage) throws Exception {
		this.stage = stage;
		configureScene();
		configureStage();
		configureListeners();
		configureMapListeners();
	}

	private void configureMapListeners() {
		final Map<String, Boolean> map = new HashMap<String, Boolean>();
		map.put("ONE", false);
		map.put("TWO", false);
		map.put("THREE", false);
		responseMap.set(map);
		responseMap.addListener(new InvalidationListener() {
			@Override
			public void invalidated(Observable paramObservable) {
				System.out.println("Invalidated...");
			}
		});
		HBox hb = new HBox();
		Button oneBtn = new Button("Set ONE");
		oneBtn.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent paramT) {
				map.put("ONE", true);
				System.out.println(map.toString());
			}
		});
		
		Button twoBtn = new Button("Set TWO");
		twoBtn.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent paramT) {
				map.put("TWO", true);
				System.out.println(map.toString());
			}
		});
		
		Button threeBtn = new Button("Set THREE");
		threeBtn.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent paramT) {
				map.put("THREE", true);
				System.out.println(map.toString());
			}
		});
		
		hb.getChildren().addAll(oneBtn, twoBtn, threeBtn);
		hb.setSpacing(20);
		
		root.getChildren().addAll(hb);
	}

	private void configureListeners() {
		flag.addListener(new ChangeListener<Boolean>() {
			@Override
			public void changed(
					ObservableValue<? extends Boolean> paramObservableValue,
					Boolean paramT1, Boolean paramT2) {
				System.out.println("One");
			}
		});
		flag.addListener(new ChangeListener<Boolean>() {
			@Override
			public void changed(
					ObservableValue<? extends Boolean> paramObservableValue,
					Boolean paramT1, Boolean paramT2) {
				System.out.println("Two");
			}
		});
		flag.addListener(new ChangeListener<Boolean>() {
			@Override
			public void changed(
					ObservableValue<? extends Boolean> paramObservableValue,
					Boolean paramT1, Boolean paramT2) {
				System.out.println("Three");
			}
		});
		HBox hb = new HBox();
		Button nullBtn = new Button("Set Null");
		nullBtn.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent paramT) {
				setValue();
			}
		});
		
		Button trueBtn = new Button("Set True");
		trueBtn.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent paramT) {
				setValue();
			}
		});
		
		Button falseBtn = new Button("Set False");
		falseBtn.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent paramT) {
				setValue();
			}
		});
		
		hb.getChildren().addAll(nullBtn, trueBtn, falseBtn);
		hb.setSpacing(20);
		
		
		
		ScrollPane pane = new ScrollPane();
		pane.setMaxSize(200,200);
		messages = new VBox();
		messages.setSpacing(10);
		pane.setContent(messages);
		
		root.getChildren().addAll(hb,pane);
		
	}
	
	private void setValue(){
		messages.getChildren().add( new Label("Hello"));
		flag.set(true);
	}

	private void configureStage(){
		stage.setTitle(this.getClass().getSimpleName());
		stage.setScene(this.scene);
	    stage.show();
	}
	
	private void configureScene(){
		root = new VBox();
		//root.setPadding(new Insets(15));
		root.autosize();
		this.scene = new Scene(root, Color.LINEN);
		
	}

}


