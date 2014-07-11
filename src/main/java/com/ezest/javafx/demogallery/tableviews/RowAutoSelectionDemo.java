/**
 * 
 */
package com.ezest.javafx.demogallery.tableviews;

import java.util.Timer;
import java.util.TimerTask;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableRow;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.HBoxBuilder;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javafx.util.Callback;

/**
 * Demo class to demonstrate whether row selection is happened while data is added in tableView.
 * 
 * @author Sai.Dandem
 * 
 */
public class RowAutoSelectionDemo extends Application {

	StackPane root;
	Label lbl = new Label();

	@Override
	public void start(Stage stage) throws Exception {
		root = new StackPane();
		root.autosize();
		Scene scene = new Scene(root, Color.LINEN);

		stage.setTitle("Row Auto Selection Demo");
		stage.setWidth(800);
		stage.setHeight(300);
		stage.setScene(scene);
		stage.show();

		configureTable();
	}

	public static void main(String[] args) {
		Application.launch(args);
	}

	@SuppressWarnings("unchecked")
	private void configureTable() {
		final ObservableList<RASDomain> data = FXCollections.observableArrayList();
		int id = 1;
		for (int i = 1; i <= 1; i++) {
			data.add(new RASDomain(id++, "First Row", "This is for check.", 1));
			data.add(new RASDomain(id++, "Second Row", null, 2));
			data.add(new RASDomain(id++, "Third Row", "This is for check.", 3));
			data.add(new RASDomain(id++, "Fourth Row", "dil", 4));
		}

		final TableView<RASDomain> tableView = new TableView<RASDomain>();
		tableView.setMaxWidth(500);
		tableView.getStyleClass().add("myTable");
		

		tableView.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<RASDomain>() {
			@Override
			public void changed(ObservableValue<? extends RASDomain> arg0, RASDomain arg1, RASDomain obj) {
				lbl.setText(getFromDataBase(obj));
			}
		});
		tableView.setRowFactory(new Callback<TableView<RASDomain>, TableRow<RASDomain>>() {
			@Override
			public TableRow<RASDomain> call(TableView<RASDomain> paramP) {
				return new TableRow<RASDomain>() {
					{
						
						setOnMouseClicked(new EventHandler<MouseEvent>() {
							@Override
							public void handle(MouseEvent mouseEvent) {
								if (getItem()!=null && (mouseEvent.getButton().equals(MouseButton.PRIMARY)) && mouseEvent.getClickCount() >= 2 ) {
									lbl.setText(getFromDataBase(getItem()));
								}
							}
						});
					}

					@Override
					protected void updateItem(RASDomain paramT, boolean paramBoolean) {
						super.updateItem(paramT, paramBoolean);
						if (!isEmpty()) {
							
						}
					}
				};
			}
		});

		TableColumn<RASDomain, Integer> column0 = new TableColumn<RASDomain, Integer>("Id");
		column0.setCellValueFactory(new PropertyValueFactory<RASDomain, Integer>("id"));

		TableColumn<RASDomain, String> column1 = new TableColumn<RASDomain, String>("Title");
		column1.setCellValueFactory(new PropertyValueFactory<RASDomain, String>("name"));

		TableColumn<RASDomain, String> column2 = new TableColumn<RASDomain, String>("Description");
		column2.setCellValueFactory(new PropertyValueFactory<RASDomain, String>("description"));
		
		TableColumn<RASDomain, Number> column3 = new TableColumn<RASDomain, Number>("Status");
		column3.setPrefWidth(55);
		column3.setCellValueFactory(new PropertyValueFactory<RASDomain, Number>("status"));

		tableView.getColumns().addAll(column0, column1, column2, column3);
		
		
		HBox hb = HBoxBuilder.create().spacing(10).alignment(Pos.CENTER_LEFT).children(tableView,lbl).build();
		
		this.root.getChildren().add(hb);

		Timer t = new Timer();
		t.schedule(new TimerTask() {
			@Override
			public void run() {
				Platform.runLater(new Runnable() {
					@Override
					public void run() {
						tableView.setItems(data);
						tableView.getSelectionModel().select(0);
					}
				});
			}
		}, 3000);
	}

	public String getFromDataBase(RASDomain obj){
		System.out.println("ghi");
		if(obj!=null){
			return obj.getName()+" : "+obj.getDescription()+" : "+obj.getId();
		}
		return "";
	}
	
	/**
	 * Domain Model for this demo.
	 */
	public class RASDomain {
		private SimpleIntegerProperty id = new SimpleIntegerProperty();
		private SimpleStringProperty name = new SimpleStringProperty();
		private SimpleStringProperty description = new SimpleStringProperty();
		private SimpleIntegerProperty status = new SimpleIntegerProperty();

		public RASDomain(int id, String name, String desc, int status) {
			this.id.set(id);
			this.name.set(name);
			this.description.set(desc);
			this.status.set(status);
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

		public int getStatus() {
			return status.get();
		}

		public SimpleIntegerProperty statusProperty() {
			return status;
		}
	}
}
