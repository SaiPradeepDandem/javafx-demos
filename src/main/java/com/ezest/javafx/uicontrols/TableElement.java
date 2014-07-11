package com.ezest.javafx.uicontrols;

import javafx.beans.property.SimpleObjectProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.control.SelectionMode;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableRow;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.util.Callback;

import com.ezest.javafx.components.EMRTableView;
import com.ezest.javafx.components.SelectableTableCell;
import com.ezest.javafx.domain.MyDomain;

public class TableElement {

	public static Node getTable(){
		VBox vb = new VBox();
		vb.setSpacing(5);
		vb.setPadding(new Insets(10, 0, 0, 10));
		 
		Label lbl = new Label("Address Book");
		lbl.setFont(new Font("Arial", 20));
		
		//TableView table = new TableView();
		final TableView<Person> table = new TableView<Person>();
		table.getStyleClass().add("myTable");
		//table.setStyle("-fx-base: #ff7f2a;");
		table.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);
		
		final EventHandler<MouseEvent> mouseClickEvent = new EventHandler<MouseEvent>() {
			@Override
			public void handle(MouseEvent t) {
				if (t.getClickCount() >= 2) {
					TableCell cell = (TableCell)t.getSource();
					TableRow row = cell.getTableRow();
					System.out.println("hello"+row.getIndex());
				} 
			}
		};
		
		
		TableColumn firstNameCol = new TableColumn("First Name");
		Callback<TableColumn, TableCell> cellFactory1 = new Callback<TableColumn, TableCell>() {
			@Override
			public TableCell call(final TableColumn param) {
				final MyCheckBox checkBox = new MyCheckBox();
				//checkBox.setParentElement(this);
				
				final TableCell cell = new TableCell() {
	
					
					@Override
					public void updateItem(Object item, boolean empty) {
						final TableCell this$ = this;
						checkBox.setOnMouseClicked(new EventHandler<MouseEvent>() {
							@Override
							public void handle(MouseEvent e) {
								System.out.println("hello"+checkBox.getParentElement());
							}
						});
						
						super.updateItem(item, empty);
						if (item == null) {
							checkBox.setDisable(true);
							checkBox.setSelected(false);
						} else {
							checkBox.setDisable(false);
							checkBox.setSelected(item.toString().equals("Yes") ? true : false);
							commitEdit(checkBox.isSelected() ? "Yes" : "No");
						}
						checkBox.setOnMouseClicked(new EventHandler<MouseEvent>() {

							@Override
							public void handle(MouseEvent paramT) {
								int index = this$.getTableRow().getIndex();
								table.getItems().remove(index);
								table.getSelectionModel().clearSelection();
							}
						});
						HBox hb = new HBox();
						hb.getChildren().addAll(checkBox,new Text(item.toString()));
						setGraphic(hb);
					}
				};
				checkBox.setParentElement(cell);
				
				cell.addEventHandler(MouseEvent.MOUSE_CLICKED, mouseClickEvent);
				return cell;
			}
		};
		firstNameCol.setCellFactory(cellFactory1);
		
        TableColumn lastNameCol = new TableColumn("Last Name");
        
        Callback<TableColumn<Person,String>, TableCell<Person,String>> cellFactory = new Callback<TableColumn<Person,String>, TableCell<Person,String>>() {
			public TableCell call(TableColumn<Person,String> p) {
				return new SelectableTableCell(mouseClickEvent);
			}
		};
		lastNameCol.setCellFactory(cellFactory);
		
        TableColumn emailCol = new TableColumn("Email");
          
        TableColumn firstEmailCol = new TableColumn("Primary");
        //firstEmailCol.setMinWidth(200);
        TableColumn secondEmailCol = new TableColumn("Secondary");
        //firstEmailCol.setMinWidth(150);
        
        emailCol.getColumns().addAll(firstEmailCol, secondEmailCol);
        
        table.getColumns().addAll(firstNameCol, lastNameCol, emailCol);
        
        final ObservableList<Person> data = FXCollections.observableArrayList(
        		new Person("Jacob", "Smith", "jacob.smith@example.com"),
        	    new Person("Isabella", "Johnson", "isabella.johnson@example.com"),
        	    new Person("Ethan", "Williams", "ethan.williams@example.com"),
        	    new Person("Emma", "Jones", "emma.jones@example.com"),
        	    new Person("Jacob", "Smith", "jacob.smith@example.com"),
        	    new Person("Isabella", "Johnson", "isabella.johnson@example.com"),
        	    new Person("Ethan", "Williams", "ethan.williams@example.com"),
        	    new Person("Emma", "Jones", "emma.jones@example.com"),
        	    new Person("Jacob", "Smith", "jacob.smith@example.com"),
        	    new Person("Isabella", "Johnson", "isabella.johnson@example.com"),
        	    new Person("Ethan", "Williams", "ethan.williams@example.com"),
        	    new Person("Emma", "Jones", "emma.jones@example.com"),
        	    new Person("Jacob", "Smith", "jacob.smith@example.com"),
        	    new Person("Isabella", "Johnson", "isabella.johnson@example.com"),
        	    new Person("Ethan", "Williams", "ethan.williams@example.com"),
        	    new Person("Emma", "Jones", "emma.jones@example.com"),
        	    new Person("Jacob", "Smith", "jacob.smith@example.com"),
        	    new Person("Isabella", "Johnson", "isabella.johnson@example.com"),
        	    new Person("Ethan", "Williams", "ethan.williams@example.com"),
        	    new Person("Emma", "Jones", "emma.jones@example.com"),
        	    new Person("Michael", "Brown", "michael.brown@example.com")
        	);
        
        //firstNameCol.setProperty("firstName");
        firstNameCol.setCellValueFactory(new PropertyValueFactory<Person,String>("firstName"));
        lastNameCol.setCellValueFactory(new PropertyValueFactory<Person,String>("lastName"));
        firstEmailCol.setCellValueFactory(new PropertyValueFactory<Person,String>("email"));

       // lastNameCol.setProperty("lastName");
        //firstEmailCol.setProperty("email");
        
        table.setItems(data);
        
        Button btn = new Button("Add");
        btn.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent arg0) {
				data.add(new Person("Sai Pradeep", "Dandem", "sai.dandem@example.com"));
				
			}
		});
        
		vb.getChildren().addAll(lbl,table,btn);
		return vb;
	}

	public static Node getSimpleTable() {
		
		 final ObservableList<MyDomain> data = FXCollections.observableArrayList(
					 new MyDomain("This is for check of the label height."),
					 new MyDomain("This is for check of the label height. This is for check of the label height.")
				 );
		EMRTableView<MyDomain> table = new EMRTableView<MyDomain>(150);
		table.getStyleClass().add("myTable");
		TableColumn<MyDomain,String> descCol = new TableColumn<MyDomain,String>("Description");
		descCol.setPrefWidth(150);
		descCol.setCellValueFactory(new PropertyValueFactory<MyDomain,String>("description"));
		
		Callback<TableColumn<MyDomain,String>, TableCell<MyDomain,String>> cellFactory = new Callback<TableColumn<MyDomain,String>, TableCell<MyDomain,String>>() {

			@Override
			public TableCell<MyDomain, String> call( TableColumn<MyDomain, String> param) {
				final TableCell<MyDomain, String> cell = new TableCell<MyDomain, String>() {
					private Text label;
					@Override
					public void updateItem(String item, boolean empty) {
						super.updateItem(item, empty);
						if (!isEmpty()) {
							label = new Text(item.toString());
							label.setWrappingWidth(70);
							setGraphic(label);
						}
					}
				};
				return cell;
			}
			
		};
		descCol.setCellFactory(cellFactory);
		
		table.getColumns().addAll(descCol);
		table.setItems(data);
		
		return table;
	}
}



class MyCheckBox extends CheckBox{
	SimpleObjectProperty<Node> parentElement = new SimpleObjectProperty<Node>();
	public MyCheckBox(){
		super();
	}
	/**
	 * @return the parent
	 */
	public SimpleObjectProperty<Node> parentElementProperty() {
		return parentElement;
	}
	/**
	 * @return the parent
	 */
	public Node getParentElement() {
		return parentElement.get();
	}
	/**
	 * @param parent the parent to set
	 */
	public void setParentElement(Node parent) {
		this.parentElement.set(parent);
	}
	
}
