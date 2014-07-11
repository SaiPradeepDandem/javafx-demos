package com.ezest.javafx.demogallery.treeview;

import java.util.List;

import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.TreeCell;
import javafx.scene.control.TreeItem;
import javafx.scene.control.TreeItem.TreeModificationEvent;
import javafx.scene.control.TreeView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javafx.util.Callback;

public class TreeViewDynamicLoadingDemo extends Application {

	public static void main(String[] args) {
		launch(args);
	}

	@Override
	public void start(Stage stage) {
		// Creating the rootNode and rootItem
		Employee rootNode = new Employee(0, "Root Node");
		TreeItem<Employee> rootItem = new TreeItem<Employee>(rootNode);
		rootItem.addEventHandler(TreeItem.<Employee> branchExpandedEvent(), new EventHandler<TreeItem.TreeModificationEvent<Employee>>() {
			@Override
			public void handle(TreeModificationEvent<Employee> event) {
				TreeItem<Employee> item = event.getTreeItem();
				Employee node = item.getValue();
				List<Employee> children = EmployeeDataBase.getChidren(node);
				item.getChildren().clear();
				if (children != null && children.size() > 0) {
					for (Employee employee : children) {
						TreeItem<Employee> itm = new TreeItem<Employee>(employee);
						itm.addEventHandler(TreeItem.<Employee> branchExpandedEvent(), this);
						item.getChildren().add(itm);
					}
					
					for (TreeItem<Employee> childItem : item.getChildren()) {
						List<Employee> grandchildren = EmployeeDataBase.getChidren(childItem.getValue());
						for (Employee gemp : grandchildren) {
							TreeItem<Employee> gitm = new TreeItem<Employee>(gemp);
							gitm.addEventHandler(TreeItem.<Employee> branchExpandedEvent(), this);
							childItem.getChildren().add(gitm);
						}
					}
				}
			}
		});
		rootItem.setExpanded(true);
		
		// Creating the treeview and setting the rootitem.
		TreeView<Employee> treeView = new TreeView<Employee>();
		treeView.setShowRoot(true);
		treeView.setRoot(rootItem);
		treeView.setCellFactory(new Callback<TreeView<Employee>, TreeCell<Employee>>() {
			@Override
			public TreeCell<Employee> call(TreeView<Employee> paramP) {
				return new TreeCell<Employee>(){
					@Override
					protected void updateItem(Employee paramT, boolean empty) {
						super.updateItem(paramT, empty);
						if(!empty){
							setText(paramT.getName()+" : "+paramT.getId());
							final TreeCell<Employee> thisCell = this;
							setOnMouseClicked(new EventHandler<MouseEvent>() {
								@Override
								public void handle(MouseEvent e) {
									TreeItem<Employee> item = thisCell.getTreeItem();
									System.out.println("");
									System.out.print(item.getValue().getName());
									while(item.getParent()!=null){
										item = item.getParent();
										System.out.print(" > "+item.getValue().getName());
									}
								}
							});
						}
					}
				};
			}
		});
		VBox box = new VBox();
		box.getChildren().add(treeView);
		Scene scene = new Scene(box, 400, 300, Color.LIGHTGRAY);
		stage.setTitle("Tree View Sample");
		stage.setScene(scene);
		stage.show();
	}
}
