/**
 * 
 */
package com.ezest.javafx.demogallery.tableviews;

import javafx.application.Application;
import javafx.beans.binding.StringBinding;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.TableColumn.CellDataFeatures;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.HBox;
import javafx.scene.layout.HBoxBuilder;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.layout.VBoxBuilder;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javafx.util.Callback;

/**
 * @author Sai.Dandem
 *
 */
public class ColumnBindingDemo  extends Application {

	StackPane root;
	final ObservableList<CBDomain> data = FXCollections.observableArrayList();
	
	@Override
	public void start(Stage stage) throws Exception {
		root = new StackPane();
		root.autosize();
		Scene scene = new Scene(root, Color.LINEN);

		stage.setTitle("Column Binding Demo");
		stage.setWidth(700);
		stage.setHeight(700);
		stage.setScene(scene);
		stage.show();

		configureTable();
	}

	public static void main(String[] args) {
		Application.launch(args);
	}

	@SuppressWarnings("unchecked")
	private void configureTable() {
		for (int i = 1; i <= 10; i++) {
			data.add(new CBDomain(i,"Title "+i, "Desc "+i));
		}

		TableView<CBDomain> tableView = new TableView<CBDomain>();
		tableView.getStyleClass().add("myTable");
		tableView.setItems(data);

		TableColumn<CBDomain, Integer> column0 = new TableColumn<CBDomain, Integer>("Id");
		column0.setCellValueFactory(new PropertyValueFactory<CBDomain, Integer>("id"));
		
		TableColumn<CBDomain, String> column1 = new TableColumn<CBDomain, String>("Title");
		column1.setPrefWidth(150);
		column1.setCellValueFactory(new PropertyValueFactory<CBDomain, String>("name"));

		TableColumn<CBDomain, String> column2 = new TableColumn<CBDomain, String>("Description");
		column2.setPrefWidth(250);
		
		//column2.setCellValueFactory(new PropertyValueFactory<CBDomain, String>("description"));
		
		/*column2.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<CBDomain,String>, ObservableValue<String>>() {
			SimpleStringProperty sp = new SimpleStringProperty();
			@Override
			public ObservableValue<String> call(CellDataFeatures<CBDomain, String> cellData) {
				if(cellData.getValue().descriptionProperty()!=null){
					sp.set(cellData.getValue().getDescription());
				}
				return sp;
			}
		});*/
		
		/*column2.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<CBDomain,String>, ObservableValue<String>>() {
			@Override
			public ObservableValue<String> call(CellDataFeatures<CBDomain, String> cellData) {
				return cellData.getValue().descriptionProperty();
			}
		});*/
		
		column2.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<CBDomain,String>, ObservableValue<String>>() {
			SimpleStringProperty sp = new SimpleStringProperty();
			@Override
			public ObservableValue<String> call(CellDataFeatures<CBDomain, String> cellData) {
				if(cellData.getValue().descriptionProperty()!=null){
					sp.set(cellData.getValue().getName()+ " :: "+cellData.getValue().getDescription());
				}
				return sp;
			}
		});
		
		/*column2.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<CBDomain,String>, ObservableValue<String>>() {
			@Override
			public ObservableValue<String> call(final CellDataFeatures<CBDomain, String> cellData) {
				return new StringBinding() {
					{
						cellData.getValue().nameProperty();
						cellData.getValue().descriptionProperty();
					}
					@Override
					protected String computeValue() {
						return cellData.getValue().getName()+ " :: "+cellData.getValue().getDescription();
					}
				};
			}
		});*/
		
		/*column2.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<CBDomain,String>, ObservableValue<String>>() {
			SimpleStringProperty sp = new SimpleStringProperty();
			@Override
			public ObservableValue<String> call(final CellDataFeatures<CBDomain, String> cellData) {
				sp.unbind();
				sp.bind( new StringBinding() {
					{
						cellData.getValue().nameProperty();
						cellData.getValue().descriptionProperty();
					}
					@Override
					protected String computeValue() {
						return cellData.getValue().getName()+ " :: "+cellData.getValue().getDescription();
					}
				});
				return sp;
			}
		});*/
		
		tableView.getColumns().addAll(column0, column1, column2);
		
		VBox v = VBoxBuilder.create().spacing(5).build();
		for (CBDomain obj : data) {
			final TextField tf1 = new TextField();
			final TextField tf2 = new TextField();
			tf1.textProperty().bindBidirectional(obj.nameProperty());
			tf2.textProperty().bindBidirectional(obj.descriptionProperty());
			v.getChildren().add(HBoxBuilder.create().spacing(5).children(tf1,tf2).build());
		}
		VBox vb = VBoxBuilder.create().spacing(15).padding(new Insets(10)).children(v,tableView).build();
		this.root.getChildren().add(vb);
	}

	
	/**
	 * Domain Model for this demo.
	 */
	public class CBDomain {
		private SimpleIntegerProperty id = new SimpleIntegerProperty();
		private SimpleStringProperty name = new SimpleStringProperty();
		private SimpleStringProperty description = new SimpleStringProperty();
		
		public CBDomain(int id,String name, String desc) {
			this.id.set(id);
			this.name.set(name);
			this.description.set(desc);
		}

		public int getId() {
			return id.get();
		}

		public SimpleIntegerProperty idProperty() {
			return id;
		}

		public String getDescription() {
			return description.get();
		}

		public SimpleStringProperty descriptionProperty() {
			return description;
		}

		public String getName() {
			return name.get();
		}

		public SimpleStringProperty nameProperty() {
			return name;
		}
		@Override
		public String toString() {
			return getId()+"";
		}
	}
}

