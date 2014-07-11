package com.ezest.javafx.demogallery.treeview;

import javafx.application.Application;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.SelectionMode;
import javafx.scene.control.TreeCell;
import javafx.scene.control.TreeItem;
import javafx.scene.control.TreeView;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;
import javafx.util.Callback;

public class TreeViewSample extends Application {

	private final Node rootIcon = new ImageView(
			new Image(getClass().getResourceAsStream("folder_16.png"))
	);

	public static void main(String[] args) {
		launch(args);
	}

	@Override
	public void start(Stage primaryStage) {
		primaryStage.setTitle("Tree View Sample");        

		TreeItem<String> rootItem = new TreeItem<String> ("Inbox", rootIcon);
		rootItem.setExpanded(true);
		for (int i = 1; i < 6; i++) {
			TreeItem<String> item = new TreeItem<String> ("Message" + i);            
			rootItem.getChildren().add(item);
		}        

		TreeView<String> tree = new TreeView<String> (rootItem);        
		tree.getSelectionModel().setSelectionMode(SelectionMode.SINGLE);
		tree.setCellFactory(new Callback<TreeView<String>, TreeCell<String>>() {
			@Override
			public TreeCell<String> call(TreeView<String> paramP) {
				return new TreeCell<String>(){
					@Override
					protected void updateItem(String paramT, boolean paramBoolean) {
						super.updateItem(paramT, paramBoolean);
						if(!isEmpty()){
							Rectangle rect = new Rectangle(20, 20);
							rect.setFill(Color.RED);
							HBox hb = new HBox();
							hb.getChildren().addAll(rect,new Label(paramT));

							setGraphic(hb);
						}
					}
				};
			}
		});
		StackPane root = new StackPane();
		root.getChildren().add(tree);
		primaryStage.setScene(new Scene(root, 300, 250));
		primaryStage.show();
	}
}

/*
 * setGraphic(new Label(paramT));
							final TreeCell<String> this$ = this;
							this.setOnMouseClicked(new EventHandler<MouseEvent>() {
								@Override
								public void handle(MouseEvent event) {
									if(event.getClickCount()==2){
										// Getting the node value.
										String nodeValue = this$.getItem();

										// Opening a new stage by passing this value.
										Group root = new Group();
										root.getChildren().add(new Label(nodeValue));
										Scene scene = new Scene(root, 300, 300, Color.AQUA);
										Stage stg = new Stage();
										stg.setScene(scene);
										stg.show();
									}
								}
							});
 */
