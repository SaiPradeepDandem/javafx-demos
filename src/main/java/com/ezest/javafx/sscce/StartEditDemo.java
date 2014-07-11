package com.ezest.javafx.sscce;

import javafx.application.Application;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.EventHandler;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableRow;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;
import javafx.util.Callback;

import com.sun.javafx.scene.control.skin.TableRowSkin;

public class StartEditDemo extends Application {

    public static void main(String[] args) {
        Application.launch(args);
    }

    public void start(Stage primaryStage) {
        primaryStage.setTitle("Start Edit Test");
        StackPane root = new StackPane();
        Scene scene = new Scene(root, 600, 150);
        primaryStage.setScene(scene);
        primaryStage.show();
        configureTable(root);
    }

	private void configureTable(StackPane root) {
		final ObservableList<MyDomain> data = FXCollections.observableArrayList(
				 new MyDomain("Apple","This is a fruit","Red"),
				 new MyDomain("PineApple","This is also a fruit","Yellow"),
				 new MyDomain("Potato","This is a vegetable","Brown")
			 );
		
		TableView<MyDomain> table = new TableView<MyDomain>();
		
		TableColumn<MyDomain,String> titleColumn = new TableColumn<MyDomain,String>("Name");
		titleColumn.setPrefWidth(150);
		titleColumn.setCellValueFactory(new PropertyValueFactory<MyDomain,String>("name"));
		titleColumn.setCellFactory(getEditableCallBack(1));
		
		TableColumn<MyDomain,String> descCol = new TableColumn<MyDomain,String>("Description");
		descCol.setPrefWidth(150);
		descCol.setCellValueFactory(new PropertyValueFactory<MyDomain,String>("description"));
		descCol.setCellFactory(getEditableCallBack(2));
		
		TableColumn<MyDomain,String> colorCol = new TableColumn<MyDomain,String>("Color");
		colorCol.setPrefWidth(150);
		colorCol.setCellValueFactory(new PropertyValueFactory<MyDomain,String>("color"));
		colorCol.setCellFactory(getEditableCallBack(0));
		
		table.getColumns().addAll(titleColumn,descCol,colorCol);
		table.setItems(data);
		root.getChildren().add(table);
	}
    
	private Callback<TableColumn<MyDomain, String>, TableCell<MyDomain, String>> getEditableCallBack(final int nextColumn) {
		/* Cell factory call back object */
		return new Callback<TableColumn<MyDomain, String>, TableCell<MyDomain, String>>() {
			@Override
			public TableCell<MyDomain, String> call(TableColumn<MyDomain, String> p) {
				return new EditableCell<MyDomain>(nextColumn);
			}
		};
	}

	public class EditableCell<T> extends TableCell<T, String> {
		private TextField textBox;
		private Label label;
		private int nextCol;
		public EditableCell(int nextcolumn) {
			this.label = new Label();
			this.nextCol = nextcolumn;
			/* Enabling to get the edit cell on single mouse click over the cell. */
			this.setOnMouseClicked(new EventHandler<MouseEvent>() {

				@Override
				public void handle(MouseEvent event) {
					startEdit();
				}
			});
		}
		
		@Override
		public void startEdit() {
			
			super.startEdit();
			
			if (isEmpty()) {
				return;
			}
			if (textBox == null) {
				createTextBox();
			} else {
				textBox.setText(getItem());
			}
			setGraphic(textBox);
			textBox.requestFocus();
			textBox.selectAll();
			setEditable(true);
		}

		@Override
		public void updateItem(String item, boolean empty) {
			super.updateItem(item, empty);
			if (!isEmpty()) {
				if (textBox != null) {
					textBox.setText(item);
				}
				label.setText(item);
				setGraphic(label);
			}
		}

		private void createTextBox() {
			textBox = new TextField(getItem());
			/* On focus of the textbox, selecting the row.*/
			textBox.focusedProperty().addListener(new ChangeListener<Boolean>() {
				@Override
				public void changed(ObservableValue<? extends Boolean> paramObservableValue,Boolean paramT1, Boolean paramT2) {
					if(paramT2){
						EditableCell.this.getTableView().getSelectionModel().select(EditableCell.this.getIndex());
					}
				}
			});
			
			textBox.setOnKeyReleased(new EventHandler<KeyEvent>() {
				@Override
				public void handle(KeyEvent t) {
					if (t.getCode() == KeyCode.ENTER) {
						callAction();
					} else if (t.getCode() == KeyCode.ESCAPE) {
						cancelEdit();
					}
				}
			});
			
			textBox.setOnKeyPressed(new EventHandler<KeyEvent>() {
				@Override
				public void handle(KeyEvent t) {
					if (t.getCode() == KeyCode.TAB){
						if(nextCol!=0){
							TableRowSkin<T> rowSkin = (TableRowSkin<T>) EditableCell.this.getParent();
							((EditableCell)rowSkin.getChildren().get(nextCol)).startEdit();
							
							TableRow<T> row= (TableRow<T>)rowSkin.getParent();
							Group grp = (Group)row.getParent();
							for (Node node : grp.getChildren()) {
								System.out.println("Node : "+node);
							}
							
							Group parentGrp =(Group)grp.getParent(); 
							for (Node node : parentGrp.getChildren()) {
								System.out.println("Parent child Node : "+node);
							}
							System.out.println(parentGrp.getParent()); //VirtualFlow$ClippedContainer
							
							for (Node node : parentGrp.getParent().getChildrenUnmodifiable()) {
								System.out.println("Clip Child  : "+node); // Group
								for (Node node1 : ((Group)node).getChildren()) {
									
								}
							}
							
							// Making the next as editable and focused.
							/*TableColumn nextColumn = getTableView().getColumns().get(nextCol);
							getTableView().edit(getTableRow().getIndex(), nextColumn);
							getTableView().getFocusModel().focus(getTableRow().getIndex(), nextColumn);*/
						}else{
							// Making the next row as editable.
							if(getTableRow().getIndex()<getTableView().getItems().size()){
								TableRowSkin<T> rowSkin = (TableRowSkin<T>) EditableCell.this.getParent();
								TableRow<T> row= (TableRow<T>)rowSkin.getParent();
								ObservableList<Node> rows = ((Group)row.getParent()).getChildren();
								
								for(Node nod : rows){
									TableRow<T> nextRow = (TableRow<T>)nod;
									TableRowSkin<T> nextRowSkin = (TableRowSkin<T>) nextRow.getSkin();
									EditableCell cell = ((EditableCell)rowSkin.getChildren().get(0));
									System.out.println(cell.getLabel().getText());
								}
								/*TableRow<T> nextRow = (TableRow<T>)rows.get(getTableRow().getIndex()+2);
								TableRowSkin<T> nextRowSkin = (TableRowSkin<T>) nextRow.getSkin();
								EditableCell cell = ((EditableCell)rowSkin.getChildren().get(0));
								System.out.println(cell.getLabel().getText());*/
								
								/*TableColumn nextColumn = getTableView().getColumns().get(0);
								getTableView().edit(getTableRow().getIndex()+1, nextColumn);
								getTableView().getFocusModel().focus(getTableRow().getIndex()+1, nextColumn);*/
							}	
						}
					}
				}
			});
		}
		
		@Override
		public void cancelEdit() {
			super.cancelEdit();
			textBox = null;
			setGraphic(label);
		}

		@Override
		public void commitEdit(String t) {
			super.commitEdit(t);
			setItem(t);
			textBox = null;
			label.setText(t);
			setGraphic(label);
		}

		@SuppressWarnings("rawtypes")
		public ObservableList<Node> getAllCellsOfSameRow(TableCell cell) {
			TableRowSkin rowSkin = (TableRowSkin) cell.getParent();
			return rowSkin.getChildren();
		}

		public void callAction() {
			TableRowSkin<T> rowSkin = (TableRowSkin<T>) this.getParent();
			ObservableList<Node> cells = rowSkin.getChildren();
			for (Node node : cells) {
				EditableCell<T> cell = (EditableCell<T>) node;
				cell.commitEdit(cell.getTextBox().getText());
			}
		}
		
		public TextField getTextBox() {
			return this.textBox;
		}

		public Label getLabel() {
			return this.label;
		}
	}
	
	public class MyDomain{
		private SimpleStringProperty name = new SimpleStringProperty();
		private SimpleStringProperty description = new SimpleStringProperty();
		private SimpleStringProperty color = new SimpleStringProperty();
		public MyDomain(String name, String desc, String color){
			this.name.set(name);
			this.description.set(desc);
			this.color.set(color);
		}
		
		public String getDescription() {
	        return description.get();
	    }
	    
		public SimpleStringProperty descriptionProperty(){
	    	return description;
	    }
	    
	    public String getName() {
	        return name.get();
	    }
	    
	    public SimpleStringProperty nameProperty(){
	    	return name;
	    }
	    
	    public String getColor() {
	        return color.get();
	    }
	    
	    public SimpleStringProperty colorProperty(){
	    	return color;
	    }

	}
    
}


