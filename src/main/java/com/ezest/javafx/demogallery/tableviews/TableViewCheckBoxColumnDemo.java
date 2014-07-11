package com.ezest.javafx.demogallery.tableviews;

import com.javafx.experiments.scenicview.ScenicView;

import javafx.application.Application;
import javafx.beans.InvalidationListener;
import javafx.beans.Observable;
import javafx.beans.binding.ListBinding;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonBuilder;
import javafx.scene.control.CheckBox;
import javafx.scene.control.CheckBoxBuilder;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.layout.VBoxBuilder;
import javafx.stage.Stage;
import javafx.util.Callback;

/**
 * 
 * A simple table that uses cell factories to add a control to a table column.
 * 
 * @see javafx.scene.control.TableCell
 * @see javafx.scene.control.TableColumn
 * @see javafx.scene.control.TablePosition
 * @see javafx.scene.control.TableRow
 * @see javafx.scene.control.TableView
 */

public class TableViewCheckBoxColumnDemo extends Application {

	private CheckBox selectAllCheckBox;
	private TableView<Employee> employeeTable;
	private Button exportButton;

	private final EventHandler<ActionEvent> event = new EventHandler<ActionEvent>() {
		@Override
		public void handle(ActionEvent event) {

			// Checking for an unselected employee in the table view.
			boolean unSelectedFlag = false;
			for (Employee item : getEmployeeTable().getItems()) {
				if (!item.getSelected()) {
					unSelectedFlag = true;
					break;
				}
			}
			/*
			 * If at least one employee is not selected, then deselecting the check box in the table column header, else if all employees
			 * are selected, then selecting the check box in the header.
			 */
			if (unSelectedFlag) {
				getSelectAllCheckBox().setSelected(false);
			} else {
				getSelectAllCheckBox().setSelected(true);
			}

			// Checking for a selected employee in the table view.
			boolean selectedFlag = false;
			for (Employee item : getEmployeeTable().getItems()) {
				if (item.getSelected()) {
					selectedFlag = true;
					break;
				}
			}
			/*
			 * If at least one employee is selected, then enabling the "Export" button, else if none of the employees are selected, then
			 * disabling the "Export" button.
			 */
			if (selectedFlag) {
				enableExportButton();
			} else {
				disableExportButton();
			}
		}
	};

	private void init(Stage primaryStage) {
		StackPane root = new StackPane();
		Scene s = new Scene(root);
		primaryStage.setScene(s);

		VBox vb = VBoxBuilder.create().padding(new Insets(15)).spacing(15).build();
		vb.getChildren().addAll(getEmployeeTable(), getExportButton());

		root.getChildren().addAll(vb);
		ScenicView.show(s);
	}

	/**
	 * Lazy getter for the selectAllCheckBox.
	 * 
	 * @return selectAllCheckBox
	 */
	public CheckBox getSelectAllCheckBox() {
		if (selectAllCheckBox == null) {
			final CheckBox selectAllCheckBox = CheckBoxBuilder.create().build();

			// Adding EventHandler to the CheckBox to select/deselect all employees in table.
			selectAllCheckBox.setOnAction(new EventHandler<ActionEvent>() {
				@Override
				public void handle(ActionEvent event) {
					// Setting the value in all the employees.
					for (Employee item : getEmployeeTable().getItems()) {
						item.setSelected(selectAllCheckBox.isSelected());
					}
					getExportButton().setDisable(!selectAllCheckBox.isSelected());
				}
			});

			this.selectAllCheckBox = selectAllCheckBox;
		}
		return selectAllCheckBox;
	}

	@SuppressWarnings("unchecked")
	public TableView<Employee> getEmployeeTable() {
		if (employeeTable == null) {
			// "Selected" column
			TableColumn<Employee, Boolean> selectedCol = new TableColumn<Employee, Boolean>();
			selectedCol.setMinWidth(50);
			selectedCol.setGraphic(getSelectAllCheckBox());
			selectedCol.setCellValueFactory(new PropertyValueFactory<Employee, Boolean>("selected"));
			selectedCol.setCellFactory(new Callback<TableColumn<Employee, Boolean>, TableCell<Employee, Boolean>>() {
				public TableCell<Employee, Boolean> call(TableColumn<Employee, Boolean> p) {
					final TableCell<Employee, Boolean> cell = new TableCell<Employee, Boolean>() {
						@Override
						public void updateItem(final Boolean item, boolean empty) {
							if (item == null)
								return;
							super.updateItem(item, empty);
							if (!isEmpty()) {
								final Employee employee = getTableView().getItems().get(getIndex());
								CheckBox checkBox = new CheckBox();
								checkBox.selectedProperty().bindBidirectional(employee.selectedProperty());
								// checkBox.setOnAction(event);
								setGraphic(checkBox);
							}
						}
					};
					cell.setAlignment(Pos.CENTER);
					return cell;
				}
			});
			// "First Name" column
			TableColumn<Employee, String> firstNameCol = new TableColumn<Employee, String>();
			firstNameCol.setMinWidth(200);
			firstNameCol.setText("First Name");
			firstNameCol.setCellValueFactory(new PropertyValueFactory<Employee, String>("firstName"));

			// "Last Name" column
			TableColumn<Employee, String> lastNameCol = new TableColumn<Employee, String>();
			lastNameCol.setMinWidth(200);
			lastNameCol.setText("Last Name");
			lastNameCol.setCellValueFactory(new PropertyValueFactory<Employee, String>("lastName"));

			// "Email" column
			TableColumn<Employee, String> emailCol = new TableColumn<Employee, String>();
			emailCol.setText("Email");
			emailCol.setMinWidth(200);
			emailCol.setCellValueFactory(new PropertyValueFactory<Employee, String>("email"));

			final TableView<Employee> tableView = new TableView<Employee>();
			tableView.setItems(getEmployeeData());
			tableView.getColumns().addAll(selectedCol, firstNameCol, lastNameCol, emailCol);

			ListBinding<Boolean> lb = new ListBinding<Boolean>() {
				{
					bind(tableView.getItems());
				}

				@Override
				protected ObservableList<Boolean> computeValue() {
					ObservableList<Boolean> list = FXCollections.observableArrayList();
					for (Employee p : tableView.getItems()) {
						list.add(p.getSelected());
					}
					return list;
				}
			};

			lb.addListener(new ChangeListener<ObservableList<Boolean>>() {
				@Override
				public void changed(ObservableValue<? extends ObservableList<Boolean>> arg0, ObservableList<Boolean> arg1,
						ObservableList<Boolean> l) {
					// Checking for an unselected employee in the table view.
					boolean unSelectedFlag = false;
					for (boolean b : l) {
						if (!b) {
							unSelectedFlag = true;
							break;
						}
					}
					/*
					 * If at least one employee is not selected, then deselecting the check box in the table column header, else if all
					 * employees are selected, then selecting the check box in the header.
					 */
					if (unSelectedFlag) {
						getSelectAllCheckBox().setSelected(false);
					} else {
						getSelectAllCheckBox().setSelected(true);
					}

					// Checking for a selected employee in the table view.
					boolean selectedFlag = false;
					for (boolean b : l) {
						if (!b) {
							selectedFlag = true;
							break;
						}
					}

					/*
					 * If at least one employee is selected, then enabling the "Export" button, else if none of the employees are selected,
					 * then disabling the "Export" button.
					 */
					if (selectedFlag) {
						enableExportButton();
					} else {
						disableExportButton();
					}
				}
			});

			tableView.getItems().addListener(new InvalidationListener() {

				@Override
				public void invalidated(Observable arg0) {
					System.out.println("invalidated");
				}
			});
			tableView.getItems().addListener(new ListChangeListener<Employee>() {

				@Override
				public void onChanged(javafx.collections.ListChangeListener.Change<? extends Employee> arg0) {
					System.out.println("changed");
				}
			});
			this.employeeTable = tableView;

		}
		return employeeTable;
	}

	public Button getExportButton() {
		if (this.exportButton == null) {
			final Button exportButton = ButtonBuilder.create().text("Export").build();
			exportButton.setOnAction(new EventHandler<ActionEvent>() {
				@Override
				public void handle(ActionEvent arg0) {
					System.out.print("employees Selected : [");
					for (Employee employee : getEmployeeTable().getItems()) {
						if (employee.getSelected()) {
							System.out.print(employee.getFirstName() + ", ");
						}
					}
					System.out.print(" ]\n");
				}
			});
			exportButton.setDisable(true);
			this.exportButton = exportButton;
		}
		return this.exportButton;
	}

	/**
	 * Enables the "Export" button.
	 */
	public void enableExportButton() {
		getExportButton().setDisable(false);
	}

	/**
	 * Disables the "Export" button.
	 */
	public void disableExportButton() {
		getExportButton().setDisable(true);
	}

	// Employee object
	public static class Employee {

		private SimpleBooleanProperty selected;
		private StringProperty firstName;
		private StringProperty lastName;
		private StringProperty email;

		private Employee(boolean selected, String fName, String lName, String email) {
			this.selected = new SimpleBooleanProperty(selected);
			this.firstName = new SimpleStringProperty(fName);
			this.lastName = new SimpleStringProperty(lName);
			this.email = new SimpleStringProperty(email);
		}

		public BooleanProperty selectedProperty() {
			return selected;
		}

		public boolean getSelected() {
			return selected.get();
		}

		public void setSelected(boolean selected) {
			this.selected.set(selected);
		}

		public StringProperty firstNameProperty() {
			return firstName;
		}

		public String getFirstName() {
			return firstName.get();
		}

		public String getLastName() {
			return lastName.get();
		}

		public String getEmail() {
			return email.get();
		}

		public StringProperty lastNameProperty() {
			return lastName;
		}

		public StringProperty emailProperty() {
			return email;
		}

		public void setLastName(String lastName) {
			this.lastName.set(lastName);
		}

		public void setFirstName(String firstName) {
			this.firstName.set(firstName);
		}

		public void setEmail(String email) {
			this.email.set(email);
		}
	}

	private ObservableList<Employee> getEmployeeData() {
		ObservableList<Employee> list = FXCollections.observableArrayList();
		for (int i = 0; i < 1; i++) {
			list.add(new Employee(false, "Jacob", "Smith", "jacob.smith@example.com"));
			list.add(new Employee(false, "Jacob", "Smith", "ethan.williams@example.com"));
			list.add(new Employee(false, "Isabella", "Johnson", "isabella.johnson@example.com"));
			list.add(new Employee(false, "Ethan", "Williams", "ethan.williams@example.com"));
			list.add(new Employee(false, "Emma", "Jones", "emma.jones@example.com"));
		}
		return list;
	}

	@Override
	public void start(Stage primaryStage) throws Exception {
		init(primaryStage);
		primaryStage.show();
	}

	public static void main(String[] args) {
		launch(args);
	}

}
