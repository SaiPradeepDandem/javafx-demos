package com.ezest.javafx.sscce;

import javafx.application.Application;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Cursor;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.control.Tooltip;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.StackPaneBuilder;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

public class AlertAndConfirmPopUpDemo extends Application {

	Stage stage;
	Scene scene;
	StackPane root;
	public static void main(String[] args) {
		Application.launch(args);
	}

	@Override
	public void start(Stage stage) throws Exception {
		this.stage = stage;
		root = new StackPane();
		root.autosize();
		this.scene = new Scene(root, Color.LINEN);
		stage.setTitle("Pop Ups");
		stage.setWidth(800);
		stage.setHeight(600);
		stage.setScene(this.scene);
		stage.show();

		configurePopUps();
	}

	private void configurePopUps() {
		VBox vb = new VBox();
		vb.setPadding(new Insets(15));

		/** ALERT */
		Label alertLbl = new Label("Alert Demo");
		alertLbl.setTooltip(new Tooltip("Haeildid"));
		alertLbl.setStyle("-fx-font-weight:bold;-fx-font-size:14px;");

		final TextField text = new TextField("This is some default text for alert.");
		text.setPrefWidth(200);

		Button alertBtn = new Button("Show as Alert");
		alertBtn.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent arg0) {
				new Alert(stage, "Alert", text.getText());
			}
		});

		HBox hb1 = new HBox();
		hb1.setSpacing(10);
		hb1.getChildren().addAll(new Label("Enter Some Text : "),text,alertBtn);
		vb.getChildren().addAll(alertLbl,hb1);

		/** CONFIRM */
		Label confirmLbl = new Label("Confirm Demo");
		confirmLbl.setStyle("-fx-font-weight:bold;-fx-font-size:14px;");

		final TextField textc = new TextField("This is some default text for confirm. Are you sure ?");
		textc.setPrefWidth(200);

		final EventHandler<ActionEvent> event = new EventHandler<ActionEvent>() {
			@Override public void handle(ActionEvent actionEvent) {
				System.out.println("Yes i am done.... !!!");
			}
		};
		Button confirmBtn = new Button("Show as Confirm");
		confirmBtn.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent arg0) {
				new Confirm(stage, "Confirm", textc.getText(), event);
			}
		});
		HBox hb2 = new HBox();
		hb2.setSpacing(10);
		hb2.getChildren().addAll(new Label("Enter Some Text : "),textc,confirmBtn);
		vb.getChildren().addAll(confirmLbl,hb2);

		root.getChildren().addAll(vb);
	}

	/**
	 * Alert Class
	 */
	class Alert extends MessagePopUp{
		public Alert(Stage parentStage, String title, String message){
			super(parentStage, title, message);
		}

		public Alert(Stage parentStage, String title, String message, double width){
			super(parentStage, title, message, width);
		}
	}

	/**
	 * Confirm Class
	 */
	class Confirm extends MessagePopUp{

		public Confirm(Stage parentStage, String title, String message, EventHandler<ActionEvent> event) {
			super(parentStage, title, message);
			setActionBox(event);
		}

		public Confirm(Stage parentStage, String title, String message, double width, EventHandler<ActionEvent> event){
			super(parentStage, title, message, width);
			setActionBox(event);
		}

		private void setActionBox(final EventHandler<ActionEvent> event) {
			final HBox actionBox = super.getActionBox();
			actionBox.getChildren().clear();
			actionBox.setSpacing(15);

			Button okBtn = new Button("Ok");
			okBtn.setPrefWidth(75);
			okBtn.setOnAction(new EventHandler<ActionEvent>() {
				@Override public void handle(ActionEvent actionEvent) {
					event.handle(actionEvent);
					closePopUp();
				}
			});

			Button cancelBtn = new Button("Cancel");
			cancelBtn.setPrefWidth(75);
			cancelBtn.setOnAction(new EventHandler<ActionEvent>() {
				@Override public void handle(ActionEvent actionEvent) {
					closePopUp();
				}
			});
			actionBox.getChildren().addAll(okBtn,cancelBtn);
		}
	}

	/**
	 * Super class for Alert and Confirm
	 */
	class MessagePopUp{
		private Stage stage;
		private Scene scene;
		private StackPane root;

		private double startDragX;
		private double startDragY;
		private static final double DEFAULT_WIDTH = 350d;
		private static final double DEFAULT_HEIGHT = 150d;
		private SimpleDoubleProperty wrapWidth = new SimpleDoubleProperty(DEFAULT_WIDTH-20d);
		private HBox actionBox;
		StackPane mask = StackPaneBuilder.create().style("-fx-background-color:black;-fx-opacity:.2;").build();

		public MessagePopUp(Stage parentStage, String title, String message,double width) {
			this(parentStage, title, message);
			setWrapWidth(width);
		}

		public MessagePopUp(Stage parentStage, String title, String message){
			this.root = new StackPane();
			this.root.getStyleClass().add("mainStage");
			this.root.autosize();

			this.scene = new Scene(root, DEFAULT_WIDTH, DEFAULT_HEIGHT, Color.web("#FFFFFF"));
			this.scene.getStylesheets().add("styles/alertNconfirm.css");
			this.scene.setFill(Color.TRANSPARENT);

			this.stage = new Stage();
			this.stage.setScene(scene);
			this.stage.initOwner( parentStage);
			this.stage.initModality(Modality.APPLICATION_MODAL);
			this.stage.setTitle(title);
			this.stage.initStyle(StageStyle.TRANSPARENT);
			show();

			Group mainRoot = new Group();
			StackPane root = new StackPane(){
				@Override
				protected void layoutChildren() {
					super.layoutChildren();
					stage.setWidth(getWidth()+20);
					stage.setHeight(getHeight()+20);
				}
			};

			root.autosize();
			root.getStyleClass().add("popUpStage");
			mainRoot.getChildren().add(root);

			this.root.setPadding(new Insets(0,10,10,0));
			this.root.getChildren().add(mainRoot);

			VBox vb = new VBox();
			/* HEADER */
			HBox header = new HBox();
			header.setAlignment(Pos.CENTER_LEFT);
			header.setPadding(new Insets(0, 2, 0, 5));
			header.getStyleClass().add("popUpHeader");
			header.setPrefHeight(28);
			Button closeBtn = new Button("X");
			closeBtn.setPrefSize(18, 18);
			closeBtn.setCursor(Cursor.HAND);
			closeBtn.setOnAction(new EventHandler<ActionEvent>() {
				@Override public void handle(ActionEvent actionEvent) {
					closePopUp();
				}
			});

			StackPane sp = new StackPane();
			sp.getChildren().add(closeBtn);
			sp.setAlignment(Pos.CENTER_RIGHT);
			Label titleLbl = new Label(title);
			titleLbl.getStyleClass().add("popUpHeaderLbl");

			header.getChildren().addAll(titleLbl,sp);
			HBox.setHgrow(sp, Priority.ALWAYS);
			addDragListeners(header);

			/* CONTENT */
			StackPane content = new StackPane();
			content.setAlignment(Pos.TOP_LEFT);
			content.setPadding(new Insets(8,5,5,5));
			content.getStyleClass().add("popUpBody");
			content.setMinHeight(50);
			Text contentTxt = new Text(message);
			contentTxt.wrappingWidthProperty().bind(this.wrapWidth);
			content.getChildren().add(contentTxt);
			VBox.setVgrow(content, Priority.ALWAYS);

			/* ACTION BOX */
			actionBox = new HBox();
			actionBox.setAlignment(Pos.CENTER);
			actionBox.getStyleClass().add("popUpActionBox");
			actionBox.setPadding(new Insets(5,0,8,0	));
			Button okBtn = new Button("Ok");
			okBtn.setPrefWidth(75);
			okBtn.setOnAction(new EventHandler<ActionEvent>() {
				@Override public void handle(ActionEvent actionEvent) {
					closePopUp();
				}
			});
			actionBox.getChildren().add(okBtn);

			vb.getChildren().addAll(header,content,actionBox);
			root.getChildren().add(vb);

		}

		protected Stage getStage() {
			return this.stage;
		}

		public double getWrapWidth() {
			return wrapWidth.get();
		}

		public void setWrapWidth(double wrapWidth) {
			this.wrapWidth.set(wrapWidth);
		}

		public SimpleDoubleProperty wrapWidthProperty() {
			return wrapWidth;
		}

		public HBox getActionBox() {
			return actionBox;
		}

		public void addDragListeners(final Node n){
			n.setOnMousePressed(new EventHandler<MouseEvent>() {
				public void handle(MouseEvent me) {
					startDragX = me.getSceneX();
					startDragY = me.getSceneY();
					root.setStyle("-fx-opacity:.7;");
				}
			});
			n.setOnMouseReleased(new EventHandler<MouseEvent>() {
				public void handle(MouseEvent me) {
					root.setStyle("-fx-opacity:1;");
				}
			});
			n.setOnMouseDragged(new EventHandler<MouseEvent>() {
				public void handle(MouseEvent me) {
					stage.setX( me.getScreenX() - startDragX );
					stage.setY( me.getScreenY() - startDragY);
				}
			});
		}

		/**
		 * Method to close the pop up and remove the background mask.
		 */
		public void closePopUp() {
			Parent parentRoot = ((Stage) stage.getOwner()).getScene().getRoot();
			if (parentRoot instanceof StackPane) {
				((StackPane) parentRoot).getChildren().remove(mask);
			}
			stage.close();
		}

		/**
		 * Method to open the pop up with the background mask.
		 */
		public void show() {
			StackPane parentRoot = (StackPane) ((Stage) stage.getOwner()).getScene().getRoot();
			parentRoot.getChildren().add(mask);
			stage.show();
		}

	}	
}

