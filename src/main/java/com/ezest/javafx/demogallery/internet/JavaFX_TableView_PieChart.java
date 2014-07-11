package com.ezest.javafx.demogallery.internet;

import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.EventHandler;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.chart.PieChart;
import javafx.scene.control.ContentDisplay;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;
import javafx.util.Callback;
 
/**
*
* @web http://java-buddy.blogspot.com/
*/
public class JavaFX_TableView_PieChart extends Application {
   
   private TableView<PieChart.Data> tableView = new TableView<PieChart.Data>();
   
   private ObservableList<PieChart.Data> dataList =
           FXCollections.observableArrayList(          
               new PieChart.Data("January", 100),
               new PieChart.Data("February", 200),
               new PieChart.Data("March", 50),
               new PieChart.Data("April", 75),
               new PieChart.Data("May", 110),
               new PieChart.Data("June", 300),
               new PieChart.Data("July", 111),
               new PieChart.Data("August", 30),
               new PieChart.Data("September", 75),
               new PieChart.Data("October", 55),
               new PieChart.Data("November", 225),
               new PieChart.Data("December", 99));
   
   /**
    * @param args the command line arguments
    */
   public static void main(String[] args) {
       launch(args);
   }
   
   @Override
   public void start(Stage primaryStage) {
       primaryStage.setTitle("java-buddy.blogspot.com");
       
       Group root = new Group();
       
       tableView.setEditable(true);
       Callback<TableColumn, TableCell> cellFactory =
               new Callback<TableColumn, TableCell>() {
           @Override
                   public TableCell call(TableColumn p) {
                       return new EditingCell();
                   }
               };
 
       TableColumn columnMonth = new TableColumn("Month");
       columnMonth.setCellValueFactory(
               new PropertyValueFactory<PieChart.Data,String>("name"));
 
       TableColumn columnValue = new TableColumn("Value");
       columnValue.setCellValueFactory(
               new PropertyValueFactory<PieChart.Data,Double>("pieValue"));
       
       //--- Add for Editable Cell of Value field, in Double
       columnValue.setCellFactory(cellFactory);
       columnValue.setOnEditCommit(
               new EventHandler<TableColumn.CellEditEvent<PieChart.Data, Double>>() {
                   @Override public void handle(TableColumn.CellEditEvent<PieChart.Data, Double> t) {
                       ((PieChart.Data)t.getTableView().getItems().get(
                               t.getTablePosition().getRow())).setPieValue(t.getNewValue());
                   }
               });
       //---
       
       //--- Prepare PieChart
       final PieChart pieChart = new PieChart(dataList);
       pieChart.setTitle("PieChart");
       
       //---
       
       tableView.setItems(dataList);
       tableView.getColumns().addAll(columnMonth, columnValue);
       
       //---
       
       HBox hBox = new HBox();
       hBox.setSpacing(10);
       hBox.getChildren().addAll(tableView, pieChart);
 
       root.getChildren().add(hBox);
       
       primaryStage.setScene(new Scene(root, 640, 400));
       primaryStage.show();
   }
   
   class EditingCell extends TableCell<PieChart.Data, Double> {
 
       private TextField textField;
       
       public EditingCell() {}
       
       @Override
       public void startEdit() {
           super.startEdit();
           
           if (textField == null) {
               createTextField();
           }
           
           setGraphic(textField);
           setContentDisplay(ContentDisplay.GRAPHIC_ONLY);
           textField.selectAll();
       }
       
       @Override
       public void cancelEdit() {
           super.cancelEdit();
           
           setText(String.valueOf(getItem()));
           setContentDisplay(ContentDisplay.TEXT_ONLY);
       }
 
       @Override
       public void updateItem(Double item, boolean empty) {
           super.updateItem(item, empty);
           
           if (empty) {
               setText(null);
               setGraphic(null);
           } else {
               if (isEditing()) {
                   if (textField != null) {
                       textField.setText(getString());
                   }
                   setGraphic(textField);
                   setContentDisplay(ContentDisplay.GRAPHIC_ONLY);
               } else {
                   setText(getString());
                   setContentDisplay(ContentDisplay.TEXT_ONLY);
               }
           }
       }
 
       private void createTextField() {
           textField = new TextField(getString());
           textField.setMinWidth(this.getWidth() - this.getGraphicTextGap()*2);
           textField.setOnKeyPressed(new EventHandler<KeyEvent>() {
               
               @Override
               public void handle(KeyEvent t) {
                   if (t.getCode() == KeyCode.ENTER) {
                       commitEdit(Double.parseDouble(textField.getText()));
                   } else if (t.getCode() == KeyCode.ESCAPE) {
                       cancelEdit();
                   }
               }
           });
       }
       
       private String getString() {
           return getItem() == null ? "" : getItem().toString();
       }
   }
  
}