package com.ezest.javafx.sscce;

import java.util.ArrayList;
import java.util.List;

import javafx.application.Application;
import javafx.beans.InvalidationListener;
import javafx.beans.Observable;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class VBoxLayoutingDemo extends Application {

    public static void main(String[] args) {
        Application.launch(args);
    }

    
    public void start(Stage primaryStage) {
        primaryStage.setTitle("VBox Layouting Test");
        VBox outerVB = new VBox();
        
        VBox vb1 = new VBox();
        HBox hb1 = new HBox();
        hb1.getChildren().addAll( new ClickableImage(false, getChildNodes(1), vb1) , new Label("Parent Node 1"));
        outerVB.getChildren().addAll(hb1, vb1);
        
        VBox vb2 = new VBox();
        HBox hb2 = new HBox();
        hb2.getChildren().addAll( new ClickableImage(false, getChildNodes(2), vb2) , new Label("Parent Node 2"));
        outerVB.getChildren().addAll(hb2, vb2);
        
        Group root = new Group();
        Scene scene = new Scene(root, 600, 150);
        root.getChildren().add(outerVB);
        primaryStage.setScene(scene);
        primaryStage.show();
    }
    
    private List<Node> getChildNodes(int k ){
    	List<Node> list= new ArrayList<Node>();
    	for(int i=0;i<3;i++){
    		list.add(new Label("Child node "+i+" of parent node "+k));
    	}
    	return list;
    }
    
    class ClickableImage extends StackPane {
    	private final SimpleBooleanProperty expanded = new SimpleBooleanProperty();
    	private final Label icon = new Label();
    	
    	public ClickableImage(final boolean expand, final List<Node> nodes, final VBox vb){
    		super();
    		setPrefSize(20, 20);
    		setPadding(new Insets(2,5,2,5));
    		setStyle("-fx-background-color:#FFFFFF;-fx-cursor:hand;");
    		getChildren().add(icon);
    		this.expanded.addListener(new InvalidationListener() {
				@Override
				public void invalidated(Observable paramObservable) {
					SimpleBooleanProperty expanded = (SimpleBooleanProperty) paramObservable;
					if (expanded.get()) {
						icon.setText("-");
						vb.getChildren().addAll(nodes);
					} else {
						icon.setText("+");
						vb.getChildren().clear();
						//vb.requestLayout(); // Fix implemented after taking JavaFx 2.1 b07 updates
					}
				}
			});
    		this.expanded.set(expand);
    		this.setOnMouseClicked(new EventHandler<MouseEvent>() {
				@Override
				public void handle(MouseEvent paramT) {
					expanded.set(!expanded.get());
				}
			});
    	}
    }
}

