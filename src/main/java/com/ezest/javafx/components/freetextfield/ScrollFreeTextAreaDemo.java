package com.ezest.javafx.components.freetextfield;

import javafx.application.Application;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.geometry.Bounds;
import javafx.geometry.Insets;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.TextArea;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Region;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.layout.VBoxBuilder;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import com.javafx.experiments.scenicview.ScenicView;

public class ScrollFreeTextAreaDemo extends Application {

	Stage stage;
	Scene scene;
	StackPane root;
	private Region content;
	private Group group;
	private SimpleDoubleProperty contentHeight = new SimpleDoubleProperty();
	
	public static void main(String[] args) {
		Application.launch(args);
	}

	@Override
	public void start(Stage stage) throws Exception {
		this.stage = stage;
		configureScene();
		configureStage();
		VBox vb = VBoxBuilder.create().spacing(15).build();

		ScrollFreeTextArea ta = new ScrollFreeTextArea();
		
		TextArea textArea =new TextArea(){
			protected void layoutChildren() {
				super.layoutChildren();
				if(content==null){
					content = (Region)lookup(".content");
					contentHeight.bind(content.heightProperty());
					content.heightProperty().addListener(new ChangeListener<Number>() {
						@Override
						public void changed(ObservableValue<? extends Number> paramObservableValue,	Number paramT1, Number paramT2) {
							System.out.println("Content View Height :"+paramT2.doubleValue());
							System.out.println("Height :"+getHeight());
							//setPrefHeight(paramT2.doubleValue()+4);
						}
					});
				}
				
				if(group ==null){
					Text text = (Text)lookup(".text");
					group = (Group) text.getParent();
					group.layoutBoundsProperty().addListener(new ChangeListener<Bounds>() {
						@Override
						public void changed(ObservableValue<? extends Bounds> arg0, Bounds arg1, Bounds arg2) {
							System.out.println("Bounds changed....."+arg2.getHeight());
							setPrefHeight(arg2.getHeight()+11);
						}
					});
				}
			};
		};
		textArea.setWrapText(true);
		DynamicTextArea dd = new DynamicTextArea();
		dd.setMaxRows(5);
		vb.getChildren().addAll(dd);
		root.getChildren().add(vb);
		
		//ScenicView.show(scene);
	}

	private void configureStage() {
		stage.setTitle(this.getClass().getSimpleName());
		stage.setWidth(600);
		stage.setHeight(600);
		stage.setScene(this.scene);
		stage.show();
	}

	private void configureScene() {
		root = new StackPane();
		root.setPadding(new Insets(15));
		BorderPane bp = new BorderPane();
		bp.setCenter(root);
		bp.autosize();
		this.scene = new Scene(bp, 600d, 600d, Color.LEMONCHIFFON);
	}

}
