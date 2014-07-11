package com.ezest.javafx.sscce;

import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.Timeline;
import javafx.application.Application;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.geometry.Rectangle2D;
import javafx.scene.GroupBuilder;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ContentDisplay;
import javafx.scene.control.Label;
import javafx.scene.control.LabelBuilder;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.HBoxBuilder;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.StackPaneBuilder;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.scene.text.TextBuilder;
import javafx.stage.Stage;
import javafx.util.Duration;

public class SearchSlideDemo extends Application {

	private Rectangle2D boxBounds = new Rectangle2D(100, 100, 300, 180);
	private double ACTION_BOX_HGT = 30;
	private SimpleBooleanProperty isExpanded = new SimpleBooleanProperty();
	private VBox searchPane;
	private TextField searchField;
	private TableView<Employee> table;
	private Rectangle clipRect;
	private Timeline timelineUp;
	private Timeline timelineDown;
	private StackPane downArrow = StackPaneBuilder.create().style("-fx-padding: 8px 5px 0px 5px;-fx-background-color: black;-fx-shape: \"M0 0 L1 0 L.5 1 Z\";").maxHeight(10).maxWidth(15).build();
	private StackPane upArrow = StackPaneBuilder.create().style("-fx-padding: 8px 5px 0px 5px;-fx-background-color: black;-fx-shape: \"M0 1 L1 1 L.5 0 Z\";").maxHeight(10).maxWidth(15).build();
	private Label searchLbl = LabelBuilder.create().text("Search").graphic(downArrow).contentDisplay(ContentDisplay.RIGHT).build();
	
	private ObservableList<Employee> list = FXCollections.observableArrayList(
												new Employee("John", "Davis", "United States"),
												new Employee("Abriella", "Babin", "France"),
												new Employee("Sai", "Dandem", "India"),
												new Employee("Ranatunga", "D'Silva", "Sri Lanka"),
												new Employee("Jackie", "Chan", "China"),
												new Employee("Ramudu", "Ravanudu", "India"),
												new Employee("Kate", "Zones", "Australia"),
												new Employee("Kings", "Queens", "Kingdom")
												 );
	@Override
	public void start(Stage stage) throws Exception {
		StackPane root = new StackPane();
		root.setPadding(new Insets(30));
		root.setAlignment(Pos.TOP_RIGHT);
		root.autosize();
		Scene scene = new Scene(root,Color.web("#C4C09F"));
		stage.setTitle("Search Slide Demo");
		stage.setWidth(650);
	    stage.setHeight(400);
	    stage.setScene(scene);
	    stage.show();
	    
		configureTable(root);
		configureSearch(root);
		setAnimation();
		
		isExpanded.addListener(new ChangeListener<Boolean>() {
			@Override
			public void changed(ObservableValue<? extends Boolean> paramObservableValue,Boolean paramT1, Boolean paramT2) {
				if(paramT2){
					// To expand
					timelineDown.play();
					searchLbl.setGraphic(upArrow);
					searchField.requestFocus();
				}else{
					// To close
					timelineUp.play();
					searchLbl.setGraphic(downArrow);
				}
			}
		});
		
		table.focusedProperty().addListener(new ChangeListener<Boolean>() {
			@Override
			public void changed(ObservableValue<? extends Boolean> paramObservableValue,Boolean paramT1, Boolean paramT2) {
				if(paramT2){
					// To close
					timelineUp.play();
					searchLbl.setGraphic(downArrow);
				}
			}
		});
	}

	private void setAnimation(){
		/* Initial position setting for Top Pane*/
		clipRect = new Rectangle();
        clipRect.setWidth(boxBounds.getWidth());
		clipRect.setHeight(ACTION_BOX_HGT);
		clipRect.translateYProperty().set(boxBounds.getHeight()-ACTION_BOX_HGT);
		searchPane.setClip(clipRect);
		searchPane.translateYProperty().set(-(boxBounds.getHeight()-ACTION_BOX_HGT));
			
		/* Animation for bouncing effect. */
		final Timeline timelineDown1 = new Timeline();
		timelineDown1.setCycleCount(2);
		timelineDown1.setAutoReverse(true);
		final KeyValue kv1 = new KeyValue(clipRect.heightProperty(), (boxBounds.getHeight()-15));
		final KeyValue kv2 = new KeyValue(clipRect.translateYProperty(), 15);
		final KeyValue kv3 = new KeyValue(searchPane.translateYProperty(), -15);
		final KeyFrame kf1 = new KeyFrame(Duration.millis(100), kv1, kv2, kv3);
		timelineDown1.getKeyFrames().add(kf1);
		
		/* Event handler to call bouncing effect after the scroll down is finished. */
		EventHandler<ActionEvent> onFinished = new EventHandler<ActionEvent>() {
            public void handle(ActionEvent t) {
            	timelineDown1.play();
            }
        };
        
        timelineDown = new Timeline();
        timelineUp = new Timeline();
        
        /* Animation for scroll down. */
		timelineDown.setCycleCount(1);
		timelineDown.setAutoReverse(true);
		final KeyValue kvDwn1 = new KeyValue(clipRect.heightProperty(), boxBounds.getHeight());
		final KeyValue kvDwn2 = new KeyValue(clipRect.translateYProperty(), 0);
		final KeyValue kvDwn3 = new KeyValue(searchPane.translateYProperty(), 0);
		final KeyFrame kfDwn = new KeyFrame(Duration.millis(200), onFinished, kvDwn1, kvDwn2, kvDwn3);
		timelineDown.getKeyFrames().add(kfDwn);
		
		/* Animation for scroll up. */
		timelineUp.setCycleCount(1); 
		timelineUp.setAutoReverse(true);
		final KeyValue kvUp1 = new KeyValue(clipRect.heightProperty(), ACTION_BOX_HGT);
		final KeyValue kvUp2 = new KeyValue(clipRect.translateYProperty(), boxBounds.getHeight()-ACTION_BOX_HGT);
		final KeyValue kvUp3 = new KeyValue(searchPane.translateYProperty(), -(boxBounds.getHeight()-ACTION_BOX_HGT));
		final KeyFrame kfUp = new KeyFrame(Duration.millis(200), kvUp1, kvUp2, kvUp3);
		timelineUp.getKeyFrames().add(kfUp);
	}
	
	/**
	 * Method to configure the table.
	 * @param root
	 */
	private void configureTable(StackPane root) {
		VBox vb = new VBox();
		vb.setSpacing(8);
		table = new TableView<Employee>();
		table.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
		
		TableColumn<Employee,String> firstNameColumn = new TableColumn<Employee,String>("First Name");
		firstNameColumn.setCellValueFactory(new PropertyValueFactory<Employee,String>("firstName"));
		
		TableColumn<Employee,String> lastNameCol = new TableColumn<Employee,String>("Last Name");
		lastNameCol.setCellValueFactory(new PropertyValueFactory<Employee,String>("lastName"));
		
		TableColumn<Employee,String> countryCol = new TableColumn<Employee,String>("Country");
		countryCol.setCellValueFactory(new PropertyValueFactory<Employee,String>("country"));
		
		table.getColumns().addAll(firstNameColumn,lastNameCol,countryCol);
		table.getItems().addAll(list);
		
		vb.getChildren().addAll(TextBuilder.create().text("Employee Table").font(Font.font("Arial", 22)).build(),table);
		root.getChildren().add(vb);
	}
	
	/**
	 * Method to configure the search pane.
	 * @param root
	 */
	private void configureSearch(StackPane root) {
		searchPane = new VBox();
		searchPane.setPrefSize(boxBounds.getWidth(), boxBounds.getHeight());
		searchPane.setAlignment(Pos.TOP_RIGHT);
		StackPane sp1 = new StackPane();
		sp1.setPadding(new Insets(10));
		sp1.setAlignment(Pos.TOP_LEFT);
		sp1.setStyle("-fx-background-color:#333333,#EAA956;-fx-background-insets:0,1.5;-fx-opacity:.92;-fx-background-radius:0px 0px 0px 5px;");
		sp1.setPrefSize(boxBounds.getWidth(), boxBounds.getHeight()-ACTION_BOX_HGT);
		
		Label searchTitle = LabelBuilder.create().text("Search Employee").font(Font.font("Arial", 20)).build();
		searchField = new TextField();
		HBox hb = HBoxBuilder.create().children(LabelBuilder.create().text("Enter First Name : ").font(Font.font("Arial", 14)).build(), searchField).maxHeight(24).translateY(45).build();
		
		Button searchBtn = new Button("Search");
		searchBtn.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent paramT) {
				String str = searchField.getText();
				table.getItems().clear();
				if(str!=null && str.length()>0){
					for (Employee employee : list) {
						if(employee.getFirstName().toLowerCase().contains(str.toLowerCase())){
							table.getItems().add(employee);
						}
					}
				}else{
					table.getItems().addAll(list);
				}
			}
		});
		Button resetBtn = new Button("Reset");
		resetBtn.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent paramT) {
				table.getItems().clear();
				table.getItems().addAll(list);
			}
		});
		HBox hb2 = HBoxBuilder.create().children(searchBtn, resetBtn).maxHeight(24).spacing(10).translateY(100).build();
		sp1.getChildren().addAll(searchTitle,hb,hb2);
		
		StackPane sp2 = new StackPane();
		sp2.setPrefSize(100, ACTION_BOX_HGT);
		sp2.getChildren().add(searchLbl);
		sp2.setStyle("-fx-cursor:hand;-fx-background-color:#EAA956;-fx-border-width:0px 1px 1px 1px;-fx-border-color:#333333;-fx-opacity:.92;-fx-border-radius:0px 0px 5px 5px;-fx-background-radius:0px 0px 5px 5px;");
		sp2.setOnMouseClicked(new EventHandler<MouseEvent>() {
			@Override
			public void handle(MouseEvent paramT) {
				togglePaneVisibility();
			}
		});
		searchPane.getChildren().addAll(GroupBuilder.create().children(sp1).build(),GroupBuilder.create().children(sp2).build());
		root.getChildren().add(GroupBuilder.create().children(searchPane).build());
	}

	/**
	 * Method to toggle the search pane visibility.
	 */
	private void togglePaneVisibility(){
		if(isExpanded.get()){
			isExpanded.set(false);
		}else{
			isExpanded.set(true);
		}
	}

	public static void main(String[] args) {
		Application.launch(args);
	}
	
	/**
	 * Domain Object.
	 */
	public class Employee{
		private SimpleStringProperty firstName = new SimpleStringProperty();
		private SimpleStringProperty lastName = new SimpleStringProperty();
		private SimpleStringProperty country = new SimpleStringProperty();
		public Employee(String firstName, String lastName, String country){
			this.firstName.set(firstName);
			this.lastName.set(lastName);
			this.country.set(country);
		}
		
		public String getFirstName() {
	        return firstName.get();
	    }
 	    public SimpleStringProperty firstNameProperty(){
	    	return firstName;
	    }
	    public String getLastName() {
	        return lastName.get();
	    }
	    public SimpleStringProperty lastNameProperty(){
	    	return lastName;
	    }
	    public String getCountry() {
	        return country.get();
	    }
	    public SimpleStringProperty countryProperty(){
	    	return country;
	    }
	}
}
