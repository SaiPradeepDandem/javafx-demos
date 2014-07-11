package com.ezest.javafx.demogallery.tableviews;

import java.lang.reflect.Method;
import java.util.Timer;
import java.util.TimerTask;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonBuilder;
import javafx.scene.control.Label;
import javafx.scene.control.LabelBuilder;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.control.TabPaneBuilder;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TablePosition;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.GridPaneBuilder;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;
import javafx.scene.layout.VBoxBuilder;
import javafx.stage.Stage;

import com.sun.javafx.scene.control.behavior.TableCellBehavior;

public class TableViewMemoryLeakFix extends Application {

	public static void main(String[] args) {
		Application.launch(args);
	}

	@Override
	public void start(Stage stage) throws Exception {
		VBox root = new VBox();
		root.setSpacing(5);
		root.autosize();
		Scene scene = new Scene(root);
		stage.setTitle("TableView Demo");
		stage.setWidth(700);
		stage.setHeight(400);
		stage.setScene(scene);
		stage.show();
		root.getChildren().addAll(configureMemoryAnalyzer(), configureTabPane());
	}

	private GridPane configureMemoryAnalyzer() {
		GridPane gp = GridPaneBuilder.create().vgap(8).hgap(8).build();
		Label usedLbl = LabelBuilder.create().build();
		Label freeLbl = LabelBuilder.create().build();
		Label totalLbl = LabelBuilder.create().build();
		Label maxLbl = LabelBuilder.create().build();

		gp.addRow(0, new Label("Used  :"), usedLbl);
		gp.addRow(1, new Label("Free  :"), freeLbl);
		gp.addRow(2, new Label("Total :"), totalLbl);
		gp.addRow(3, new Label("Max   :"), maxLbl);
		read(usedLbl, freeLbl, totalLbl, maxLbl);
		return gp;
	}

	int mb = 1024 * 1024;
	Runtime runtime = Runtime.getRuntime();

	private void read(final Label usedLbl, final Label freeLbl, final Label totalLbl, final Label maxLbl) {
		Timer t = new Timer();
		t.scheduleAtFixedRate(new TimerTask() {
			@Override
			public void run() {
				Platform.runLater(new Runnable() {
					@Override
					public void run() {
						usedLbl.setText(""+((runtime.totalMemory() - runtime.freeMemory()) / mb)+" MB");
						freeLbl.setText(""+(runtime.freeMemory() / mb)+" MB");
						totalLbl.setText(""+(runtime.totalMemory() / mb)+" MB");
						maxLbl.setText(""+(runtime.maxMemory() / mb)+" MB");
						//read(usedLbl, freeLbl, totalLbl, maxLbl);
					}
				});
			}
		}, 1000, 1000);
	}

	private VBox configureTabPane() {
		final TabPane tb = TabPaneBuilder.create().build();
		VBox vb = new VBox();
		vb.setSpacing(10);
		vb.getChildren().addAll(ButtonBuilder.create().text("Add Tab").onAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent arg0) {
				Tab tab = new Tab("  New Tab  ");
				final VBox v = configureTable();
				final TableView<MyDomain> tableView = (TableView<MyDomain>) v.getChildren().get(2);
				tab.setContent(configureTable());
				tab.setOnClosed(new EventHandler<Event>() {
					@Override
					public void handle(Event arg0) {
						cleanTable(v);
					}
				});
				tb.getTabs().add(tab);
			}
		}).build(), tb);
		return vb;
	}

	@SuppressWarnings("unchecked")
	private VBox configureTable() {
		final ObservableList<MyDomain> data = FXCollections.observableArrayList();
		for (int i = 0; i < 21; i++) {
			data.add(new MyDomain("Apple", "This is a fruit.", "Red"));
			data.add(new MyDomain("Orange", "This is also a fruit.", "Orange"));
			data.add(new MyDomain("Apple", "This is a fruit.", "Red"));
		}
		final TableView<MyDomain> table;
		TableColumn<MyDomain, String> col1;
		TableColumn<MyDomain, String> col2;
		TableColumn<MyDomain, String> col3;

		table = new TableView<MyDomain>();

		col1 = new TableColumn<MyDomain, String>("Name");
		col1.setCellValueFactory(new PropertyValueFactory<MyDomain, String>("name"));

		col2 = new TableColumn<MyDomain, String>("Description");
		col2.setCellValueFactory(new PropertyValueFactory<MyDomain, String>("description"));

		col3 = new TableColumn<MyDomain, String>("Color");
		col3.setCellValueFactory(new PropertyValueFactory<MyDomain, String>("color"));

		table.getColumns().addAll(col1, col2, col3);
		table.setItems(data);

		Button btn = new Button("Add");
		btn.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent paramT) {
				table.getItems().add(new MyDomain("Apple", "This is a fruit.", "Red"));
			}
		});
		Button del = new Button("Del");
		del.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent paramT) {
				data.remove(0);
			}
		});

		VBox vb = VBoxBuilder.create().children(btn, del, table).spacing(5).build();
		VBox.setVgrow(table, Priority.ALWAYS);
		return vb;
	}

	private void cleanTable(VBox v) {
		TableView<MyDomain> table = (TableView<MyDomain>) v.getChildren().get(2);

		table.getFocusModel().focus(null);
		Class tcbClass = TableCellBehavior.class;
		try {
			Method anchorMethod = tcbClass.getDeclaredMethod("setAnchor", TableView.class, TablePosition.class);
			anchorMethod.setAccessible(true);
			anchorMethod.invoke(null, table, null);
		} catch (Throwable t) {
			throw new RuntimeException();
		}

		table.setOnMouseClicked(null);
		// table.getSelectionModel().getSelectedIndices().removeListener(listener);
		table.setSelectionModel(null);
		table.getColumns().clear();

		v.getChildren().clear();
		table.setItems(FXCollections.<MyDomain> observableArrayList());
		table = null;
	}

	/**
	 * Domain Object.
	 */
	public class MyDomain {
		private SimpleStringProperty name = new SimpleStringProperty();
		private SimpleStringProperty description = new SimpleStringProperty();
		private SimpleStringProperty color = new SimpleStringProperty();

		public MyDomain(String name, String desc, String color) {
			this.name.set(name);
			this.description.set(desc);
			this.color.set(color);
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

		public String getColor() {
			return color.get();
		}

		public SimpleStringProperty colorProperty() {
			return color;
		}
	}
}
