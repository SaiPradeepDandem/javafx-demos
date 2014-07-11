package com.ezest.javafx.components.nullcellediting;

import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.EventHandler;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.stage.Stage;
import javafx.util.Callback;
 
public class NullCellEditingExample extends Application {
 
    private TableView table = new TableView();
    private final ObservableList<Person> data =
            FXCollections.observableArrayList( new Person(null, "Smith"), new Person("Isabella", null),
            new Person("Ethan", "Williams"), new Person("Emma", "Jones"), new Person("Michael", "Brown"));
 
    public static void main(String[] args) {
        launch(args);
    }
 
    @Override
    public void start(Stage stage) {
        Scene scene = new Scene(new Group());
 
        TableColumn firstNameCol = createSimpleFirstNameColumn();
        TableColumn lastNameCol = createLastNameColumn();
        table.setItems(data);
        table.getColumns().addAll(firstNameCol, lastNameCol);
        table.setEditable(true);
 
        ((Group) scene.getRoot()).getChildren().addAll(table);
        stage.setScene(scene);
        stage.show();
    }
 
    private TableColumn createSimpleFirstNameColumn() {
        TableColumn firstNameCol = new TableColumn("First Name");
        firstNameCol.setMinWidth(100);
        firstNameCol.setCellValueFactory(new PropertyValueFactory<Person, String>("firstName"));
        firstNameCol.setCellFactory(TextFieldTableCell.forTableColumn());
        firstNameCol.setOnEditCommit(new EventHandler<TableColumn.CellEditEvent<Person, String>>() {
            @Override
            public void handle(TableColumn.CellEditEvent<Person, String> t) {
                t.getRowValue().setFirstName(t.getNewValue());
            }
        });
 
        return firstNameCol;
    }
 
    private TableColumn createLastNameColumn() {
        Callback<TableColumn, TableCell> editableFactory = new Callback<TableColumn, TableCell>() {
            @Override
            public TableCell call(TableColumn p) {
                return new EditingCell();
            }
        };
         
        TableColumn lastNameCol = new TableColumn("Last Name");
        lastNameCol.setMinWidth(100);
        lastNameCol.setCellValueFactory(new PropertyValueFactory<Person, String>("lastName"));
        lastNameCol.setCellFactory(editableFactory);
        lastNameCol.setOnEditCommit(new EventHandler<TableColumn.CellEditEvent<Person, String>>() {
            @Override
            public void handle(TableColumn.CellEditEvent<Person, String> t) {
              System.out.println( "Commiting last name change. Previous: " + t.getOldValue() + "   New: " + t.getNewValue() );
                t.getRowValue().setLastName(t.getNewValue());
            }
        });
 
        return lastNameCol;
    }  
}
