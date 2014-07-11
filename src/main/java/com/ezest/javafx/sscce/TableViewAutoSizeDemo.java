package com.ezest.javafx.sscce;

import com.ezest.javafx.sscce.WrappingTextDemo.MyDomain;

import javafx.application.Application;
import javafx.beans.binding.NumberBinding;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import javafx.scene.Scene;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.StackPane;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.util.Callback;
 
public class TableViewAutoSizeDemo extends Application {
 
 public static void main(String[] args) {
  Application.launch(args);
 }
 
 @Override
 public void start(Stage stage) throws Exception {
  StackPane root = new StackPane();
  root.autosize();
  Scene scene = new Scene(root);
  stage.setTitle("TableView Auto Size Demo");
  stage.setWidth(700);
     stage.setHeight(400);
     stage.setScene(scene);
     stage.show();
 
  configureTable(root);
 }
 
 @SuppressWarnings("unchecked")
 private void configureTable(StackPane root) {
 
  final ObservableList<MyDomain> data = FXCollections.observableArrayList(
     new MyDomain("Apple","This is a fruit. This is a fruit. This is a fruit. This is a fruit. This is a fruit. This is a fruit. This is a fruit. This is a fruit. This is a fruit. This is a fruit. This is a fruit. This is a fruit. ","Red"),
     new MyDomain("Orange","This is also a fruit.","Orange"),
     new MyDomain("Potato","This is a vegetable.","Brown")
     );
 
  CustomTableView<MyDomain> table = new CustomTableView<MyDomain>();
 
  CustomTableColumn<MyDomain,String> titleColumn = new CustomTableColumn<MyDomain,String>("Title");
  titleColumn.setPercentWidth(25);
  titleColumn.setCellValueFactory(new PropertyValueFactory<MyDomain,String>("name"));
 
  CustomTableColumn<MyDomain,String> descCol = new CustomTableColumn<MyDomain,String>("Description");
  descCol.setPercentWidth(55);
  descCol.setCellValueFactory(new PropertyValueFactory<MyDomain,String>("description"));
  descCol.setCellFactory(new Callback<TableColumn<MyDomain,String>, TableCell<MyDomain,String>>() {
		@Override
		public TableCell<MyDomain, String> call( TableColumn<MyDomain, String> param) {
			final TableCell<MyDomain, String> cell = new TableCell<MyDomain, String>() {
				private Text text;
				@Override
				public void updateItem(String item, boolean empty) {
					super.updateItem(item, empty);
					if (!isEmpty()) {
						text = new Text(item.toString());
						//text.setWrappingWidth(200); // Setting the wrapping width to the Text
						text.wrappingWidthProperty().bind(getTableColumn().widthProperty());
						setGraphic(text);
					}
				}
			};
			return cell;
		}
	});
  
  CustomTableColumn<MyDomain,String> colorCol = new CustomTableColumn<MyDomain,String>("Color");
  colorCol.setPercentWidth(20);
  colorCol.setCellValueFactory(new PropertyValueFactory<MyDomain,String>("color"));
 
  table.getTableView().getColumns().addAll(titleColumn,descCol,colorCol);
  table.getTableView().setItems(data);
  root.getChildren().add(table);
 }
 
 /**
  * CustomTableView to hold the table and grid.
  */
 public class CustomTableView<s> extends StackPane{
  private TableView<s> table;
 
  @SuppressWarnings("rawtypes")
  public CustomTableView(){
   this.table = new TableView<s>();
   final GridPane grid = new GridPane();
 
   this.table.getColumns().addListener(new ListChangeListener<TableColumn>(){
    public void onChanged(javafx.collections.ListChangeListener.Change<? extends TableColumn> arg0) {
 
     grid.getColumnConstraints().clear();
     ColumnConstraints[] arr1 = new ColumnConstraints[CustomTableView.this.table.getColumns().size()];
     StackPane[] arr2 = new StackPane[CustomTableView.this.table.getColumns().size()];
 
     int i=0;
     for(TableColumn column : CustomTableView.this.table.getColumns()){
      CustomTableColumn col = (CustomTableColumn)column;
      ColumnConstraints consta = new ColumnConstraints();
      consta.setPercentWidth(col.getPercentWidth());
 
      StackPane sp = new StackPane();
      if(i==0){
       // Quick fix for not showing the horizantal scroll bar.
       NumberBinding diff = sp.widthProperty().subtract(3.75); 
       column.prefWidthProperty().bind(diff);
      }else{
       column.prefWidthProperty().bind(sp.widthProperty());
      }
      arr1[i] = consta;
      arr2[i] = sp;
      i++;
     }
 
     grid.getColumnConstraints().addAll(arr1);
     grid.addRow(0, arr2);
    }
   });
   getChildren().addAll(grid,table);
  }
 
  public TableView<s> getTableView(){
   return this.table;
  }
 }
 
 /**
  * CustomTableColumn to hold the custom percentWidth property.
  */
 public class CustomTableColumn<S,T> extends TableColumn<S, T>{
  private SimpleDoubleProperty percentWidth = new SimpleDoubleProperty();
 
  public CustomTableColumn(String columnName){
   super(columnName);
  }
 
  public SimpleDoubleProperty percentWidth() {
   return percentWidth;
  }
 
  public double getPercentWidth() {
   return percentWidth.get();
  }
 
  public void setPercentWidth(double percentWidth) {
   this.percentWidth.set(percentWidth);
  }
 }
 
 /**
  * Domain Object.
  */
 public class MyDomain{
  private SimpleStringProperty name = new SimpleStringProperty();
  private SimpleStringProperty description = new SimpleStringProperty();
  private SimpleStringProperty color = new SimpleStringProperty();
 
  public MyDomain(String name, String desc,String color){
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