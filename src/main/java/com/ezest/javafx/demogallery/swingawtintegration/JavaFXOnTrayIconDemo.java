package com.ezest.javafx.demogallery.swingawtintegration;

import java.awt.AWTException;
import java.awt.MenuItem;
import java.awt.PopupMenu;
import java.awt.SystemTray;
import java.awt.Toolkit;
import java.awt.TrayIcon;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.ButtonBuilder;
import javafx.scene.control.Label;
import javafx.scene.control.TextFieldBuilder;
import javafx.scene.image.Image;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.StackPaneBuilder;
import javafx.scene.paint.Color;
import javafx.stage.Screen;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.stage.WindowEvent;

public class JavaFXOnTrayIconDemo extends Application {

	Stage stage;
	Scene scene;
	StackPane root;
	private EventHandler<MouseEvent> mousePressEvent;
	private EventHandler<MouseEvent> mouseDraggedEvent;
	private double startDragX;
	private double startDragY;
	private double startNodeX;
	private double startNodeY;

	private TrayIcon trayIcon;

	public static void main(String[] args) {
		Application.launch(args);
	}

	@Override
	public void start(final Stage stage) throws Exception {
		this.stage = stage;
		Platform.setImplicitExit(false);
		configureScene();
		configureStage();

		StackPane sp = StackPaneBuilder
				.create()
				.maxHeight(40)
				.maxWidth(100)
				.style("-fx-background-color:red;-fx-border-width:1px;-fx-border-color:black;-fx-background-radius:5px;-fx-border-radius:5px;-fx-cursor:hand;")
				.children(new Label("Add Note")).build();
		sp.setOnMouseClicked(new EventHandler<MouseEvent>() {
			@Override
			public void handle(MouseEvent arg0) {
				CustomPopUp p = new CustomPopUp(root);
				p.setTranslateX(600);
				p.setTranslateY(200);
				initEventHandlers(p);
				root.getChildren().add(p);
			}
		});
		root.getChildren().add(sp);
	}

	private void configureStage() {
		stage.setTitle(this.getClass().getSimpleName());
		stage.setX(0);
		stage.setY(0);
		stage.setWidth(Screen.getPrimary().getVisualBounds().getWidth());
		stage.setHeight(Screen.getPrimary().getVisualBounds().getHeight());
		stage.setScene(this.scene);
		stage.initStyle(StageStyle.TRANSPARENT);
		stage.show();
		createTrayIcon(stage);
	}

	private void configureScene() {
		root = StackPaneBuilder.create().style("-fx-border-width:0px;-fx-border-color:red;").alignment(Pos.TOP_LEFT).build();
		BorderPane bp = new BorderPane();
		bp.setCenter(root);
		bp.autosize();
		this.scene = new Scene(bp, Color.TRANSPARENT);
	}

	public void createTrayIcon(final Stage stage) {
		if (SystemTray.isSupported()) {
			// get the SystemTray instance
			SystemTray tray = SystemTray.getSystemTray();
			// load an image
			java.awt.Image image = Toolkit.getDefaultToolkit().getImage(JavaFXOnTrayIconDemo.class.getResource("/images/mglass.gif"));
			stage.getIcons().add(new Image("/images/notes-icon.png"));
			stage.setOnCloseRequest(new EventHandler<WindowEvent>() {
				@Override
				public void handle(WindowEvent t) {
					Platform.runLater(new Runnable() {
						@Override
						public void run() {
							if (SystemTray.isSupported()) {
								stage.hide();
								showProgramIsMinimizedMsg();
							} else {
								System.exit(0);
							}
						}
					});
				}
			});
			// create a action listener to listen for default action executed on the tray icon
			final ActionListener closeListener = new ActionListener() {
				@Override
				public void actionPerformed(java.awt.event.ActionEvent e) {
					System.exit(0);
				}
			};

			ActionListener showListener = new ActionListener() {
				@Override
				public void actionPerformed(java.awt.event.ActionEvent e) {
					Platform.runLater(new Runnable() {
						@Override
						public void run() {
							stage.show();
							CustomPopUp p = new CustomPopUp(root);
							p.setTranslateX(600);
							p.setTranslateY(200);
							initEventHandlers(p);
							root.getChildren().add(p);
						}
					});
				}
			};

			// create a popup menu
			PopupMenu popup = new PopupMenu();

			MenuItem showItem = new MenuItem("Show");
			showItem.addActionListener(showListener);
			popup.add(showItem);

			MenuItem closeItem = new MenuItem("Exit");
			closeItem.addActionListener(closeListener);
			popup.add(closeItem);

			trayIcon = new TrayIcon(image, "PostItNote", popup);
			trayIcon.addActionListener(showListener);
			trayIcon.addMouseListener(new MouseAdapter() {
				@Override
				public void mouseClicked(java.awt.event.MouseEvent e) {
					System.out.println(e.getButton());
					Platform.runLater(new Runnable() {
						@Override
						public void run() {
							System.out.println(23);
							stage.show();
						}
					});
				}
			});
			
			try {
				tray.add(trayIcon);
			} catch (AWTException e) {
				System.err.println(e);
			}

		}
	}

	public void showProgramIsMinimizedMsg() {
		trayIcon.displayMessage("Message.", "Application is still running.You can access from here.", TrayIcon.MessageType.INFO);
	}

	/**
	 * Sets the mouse events on the provided component.
	 * 
	 * @param comp
	 *            CustomPopUp on which the mouse listeners need to be set.
	 */
	private void initEventHandlers(final CustomPopUp popUp) {
		mousePressEvent = new EventHandler<MouseEvent>() {
			@Override
			public void handle(MouseEvent me) {
				popUp.toFront();
				// Registering the co-ordinates.
				startDragX = me.getSceneX();
				startDragY = me.getSceneY();
				startNodeX = popUp.getTranslateX();
				startNodeY = popUp.getTranslateY();
			}
		};

		mouseDraggedEvent = new EventHandler<MouseEvent>() {
			@Override
			public void handle(MouseEvent me) {
				double xTr = startNodeX + (me.getSceneX() - startDragX);
				double yTr = startNodeY + (me.getSceneY() - startDragY);
				double mxDiff = root.getWidth() - popUp.getWidth();
				double myDiff = root.getHeight() - popUp.getHeight();
				xTr = xTr < 0 ? 0 : (xTr > mxDiff ? mxDiff : xTr);
				yTr = yTr < 0 ? 0 : (yTr > myDiff ? myDiff : yTr);

				popUp.setTranslateX(xTr < 0 ? 0 : xTr);
				popUp.setTranslateY(yTr);
			}
		};

		popUp.addEventHandler(MouseEvent.MOUSE_DRAGGED, mouseDraggedEvent);
		popUp.addEventHandler(MouseEvent.MOUSE_PRESSED, mousePressEvent);
	}

	/**
	 * Custom pop up class.
	 */
	class CustomPopUp extends StackPane {
		final StackPane parent;
		final String style = "-fx-effect: dropshadow( three-pass-box , rgba(0,0,0,0.6) , 8, 0.0 , 0 , 2 );"
				+ "-fx-background-color:yellow;-fx-border-width:1px;-fx-border-color:black;"
				+ "-fx-background-radius:5px;-fx-border-radius:5px;";

		public CustomPopUp(StackPane parentWindow) {
			super();
			this.parent = parentWindow;
			setMaxHeight(200);
			setMaxWidth(200);
			getChildren().add(
					StackPaneBuilder.create().style(style).minHeight(200).minWidth(200).alignment(Pos.TOP_RIGHT)
							.children(ButtonBuilder.create().text("Close").onAction(new EventHandler<ActionEvent>() {
								@Override
								public void handle(ActionEvent paramT) {
									parent.getChildren().remove(CustomPopUp.this);
								}
							}).build(), TextFieldBuilder.create().translateY(20).build()).build());
		}
	}
}
