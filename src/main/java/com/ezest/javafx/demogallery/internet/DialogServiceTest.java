package com.ezest.javafx.demogallery.internet;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.concurrent.Service;
import javafx.concurrent.Task;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonBuilder;
import javafx.scene.control.PasswordField;
import javafx.scene.control.PasswordFieldBuilder;
import javafx.scene.control.TextField;
import javafx.scene.control.TextFieldBuilder;
import javafx.scene.effect.ColorAdjustBuilder;
import javafx.scene.effect.Effect;
import javafx.scene.image.Image;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.VBox;
import javafx.scene.layout.VBoxBuilder;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import javafx.scene.text.TextBuilder;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

/**
 * {@linkplain DialogService} Demo
 */
public class DialogServiceTest extends Application {

	private DialogService dialogService;

	/**
	 * Main {@linkplain Application} entry point
	 *
	 * @param args
	 *            passed arguments
	 */
	public static void main(final String[] args) {
		try {
			Application.launch(DialogServiceTest.class, args);
		} catch (final Throwable t) {
			t.printStackTrace();
		}
	}

	/**
	 * Shows an example usage of a {@linkplain DialogService} that displays a
	 * login screen
	 *
	 * @param primaryStage
	 *            the primary application {@linkplain Stage}
	 * @throws Exception
	 *             when something goes wrong
	 */
	@Override
	public void start(final Stage primaryStage) throws Exception {
		// setup the primary stage with a simple button that will open
		final VBox rootNode = new VBox();
		rootNode.setAlignment(Pos.CENTER);
		final Button btn = new Button("Launch Login Dialog Window");
		btn.setOnMouseClicked(new EventHandler<MouseEvent>() {
			@Override
			public void handle(final MouseEvent event) {
				if (dialogService != null) {
					dialogService.hide();
				}
				dialogService = createLoginDialog(primaryStage);
		        dialogService.start();
			}
		});
		rootNode.getChildren().add(btn);
		primaryStage.setTitle("Dialog Service Demo");
		primaryStage.setScene(new Scene(rootNode, 800, 500, Color.BLACK));
		primaryStage.getScene().getStylesheets().add(
				DialogServiceTest.class.getResource("/com/ezest/javafx/demogallery/internet/dialog.css").toExternalForm());
		primaryStage.show();
	}

	/**
	 * Creates a {@linkplain DialogService} that displays a
	 * login screen
	 *
	 * @param primaryStage
	 *            the primary application {@linkplain Stage}
	 */
	public DialogService createLoginDialog(final Stage primaryStage) {
        final TextField username = TextFieldBuilder.create().promptText(
				"Username").build();
		final PasswordField password = PasswordFieldBuilder.create().promptText(
				"Password").build();
		final Button closeBtn = ButtonBuilder.create().text("Close").build();
		final Service<Void> submitService = new Service<Void>() {
			@Override
			protected Task<Void> createTask() {
				return new Task<Void>() {
					@Override
					protected Void call() throws Exception {
						final boolean hasUsername = !username.getText()
								.isEmpty();
						final boolean hasPassword = !password.getText()
								.isEmpty();
						if (hasUsername && hasPassword) {
							// TODO : perform some sort of authentication here
							// or you can throw an exception to see the error
							// message in the dialog window
						} else {
							final String invalidFields = (!hasUsername ? username
									.getPromptText() : "")
									+ ' '
									+ (!hasPassword ? password.getPromptText()
											: "");
							throw new RuntimeException("Invalid "
									+ invalidFields);
						}
						return null;
					}
				};
			}
		};
		final DialogService dialogService = dialog(primaryStage,
				"Test Dialog Window",
				"Please provide a username and password to access the application",
				null, "Login", 550d, 300d, submitService, closeBtn, username, password);
		if (closeBtn != null) {
		      closeBtn.setOnMouseClicked(new EventHandler<MouseEvent>() {
		            @Override
		            public void handle(final MouseEvent event) {
		                  dialogService.hide();
		            }
		      });
		}
		return dialogService;
	}

	/**
	 * Creates a dialog window {@linkplain Stage} that is shown when the
	 * {@linkplain DialogService#start()} is called and hidden when the submit
	 * {@linkplain Service#restart()} returns {@linkplain State#SUCCEEDED}. When
	 * a {@linkplain Task} throws an {@linkplain Exception} the
	 * {@linkplain Exception#getMessage()} will be used to update the
	 * messageHeader of the dialog.
	 *
	 * @param parent
	 *            the parent {@linkplain Stage}
	 * @param title
	 *            the text for the {@linkplain Stage#setTitle(String)}
	 * @param headerText
	 *            the text for the {@linkplain Text#setText(String)} header
	 * @param icon
	 *            the icon of the {@linkplain Stage}
	 * @param submitLabel
	 *            the text for the submit {@linkplain Button#setText(String)}
	 * @param width
	 *            the width of the {@linkplain Stage}
	 * @param height
	 *            the height of the {@linkplain Stage}
	 * @param submitService
	 *            the {@linkplain Service} ran whenever the submit
	 *            {@linkplain Button} is clicked
	 * @param children
	 *            the child {@linkplain Node}s that will be added between the
	 *            messageHeader and submit {@linkplain Button} (if any). If any
	 *            of the {@linkplain Node}s are {@linkplain Button}s they will
	 *            be added to the internal {@linkplain Button}
	 *            {@linkplain FlowPane} added to the bottom of the dialog.
	 * @return the {@linkplain DialogService}
	 */
	public static DialogService dialog(final Stage parent, final String title,
			final String headerText, final Image icon, final String submitLabel,
			final double width, final double height, final Service<Void> submitService,
			final Node... children) {
		final Stage window = new Stage();
		final Text header = TextBuilder.create().text(headerText).styleClass(
				"dialog-title").wrappingWidth(width / 1.2d).build();
		final Text messageHeader = TextBuilder.create().styleClass("dialog-message"
				).wrappingWidth(width / 1.2d).build();
		final DialogService service = new DialogService(parent, window,
				messageHeader, submitService);
		window.initModality(Modality.APPLICATION_MODAL);
		window.initStyle(StageStyle.TRANSPARENT);
		if (icon != null) {
			window.getIcons().add(icon);
		}
		if (title != null) {
			window.setTitle(title);
		}
		final VBox content = VBoxBuilder.create().styleClass("dialog").build();
		content.setMaxSize(width, height);
		window.setScene(new Scene(content, width, height, Color.TRANSPARENT));
		if (parent != null) {
			window.getScene().getStylesheets().setAll(parent.getScene().getStylesheets());
		}
		final Button submitBtn = ButtonBuilder.create().text(submitLabel).defaultButton(
				true).onAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(final ActionEvent actionEvent) {
				submitService.restart();
			}
		}).build();
		final FlowPane flowPane = new FlowPane();
		flowPane.setAlignment(Pos.CENTER);
		flowPane.setVgap(20d);
		flowPane.setHgap(10d);
		flowPane.setPrefWrapLength(width);
		flowPane.getChildren().add(submitBtn);
		content.getChildren().addAll(header, messageHeader);
		if (children != null && children.length > 0) {
			for (final Node node : children) {
				if (node == null) {
					continue;
				}
				if (node instanceof Button) {
					flowPane.getChildren().add(node);
				} else {
					content.getChildren().add(node);
				}
			}
		}
		content.getChildren().addAll(flowPane);
		return service;
	}

	/**
	 * A {@linkplain Service} for showing and hiding a {@linkplain Stage}
	 */
	public static class DialogService extends Service<Void> {

		private final Stage window;
		private final Stage parent;
		private final Effect origEffect;
		private final Service<Void> submitService;

		/**
		 * Creates a dialog service for showing and hiding a {@linkplain Stage}
		 *
		 * @param parent
		 *            the parent {@linkplain Stage}
		 * @param window
		 *            the window {@linkplain Stage} that will be shown/hidden
		 * @param messageHeader
		 *            the messageHeader {@linkplain Text} used for the service
		 *            that will be updated with exception information as the
		 *            submitService informs the {@linkplain DialogService} of
		 * @param submitService
		 *            the {@linkplain Service} that will be listened to for
		 *            {@linkplain State#SUCCEEDED} at which point the
		 *            {@linkplain DialogService} window {@linkplain Stage} will
		 *            be hidden
		 */
		protected DialogService(final Stage parent, final Stage window,
				final Text messageHeader, final Service<Void> submitService) {
			this.window = window;
			this.parent = parent;
			this.origEffect = hasParentSceneRoot() ? this.parent.getScene(
					).getRoot().getEffect() : null;
			this.submitService = submitService;
			this.submitService.stateProperty().addListener(new ChangeListener<State>() {
				@Override
				public void changed(final ObservableValue<? extends State> observable,
						final State oldValue, final State newValue) {
					if (submitService.getException() != null) {
						// service indicated that an error occurred
						messageHeader.setText(submitService.getException().getMessage());
					} else if (newValue == State.SUCCEEDED) {
						window.getScene().getRoot().setEffect(
								ColorAdjustBuilder.create().brightness(-0.5d).build());
						Platform.runLater(createHideTask());
					}
				}
			});
		}

		/**
		 * {@inheritDoc}
		 */
		@Override
		protected Task<Void> createTask() {
			return window.isShowing() ? createHideTask() : createShowTask();
		}

		/**
		 * @return a task that will show the service {@linkplain Stage}
		 */
		protected Task<Void> createShowTask() {
			final Task<Void> showTask = new Task<Void>() {
				@Override
				protected Void call() throws Exception {
					Platform.runLater(new Runnable() {
						public void run() {
							if (hasParentSceneRoot()) {
								parent.getScene().getRoot().setEffect(
										ColorAdjustBuilder.create().brightness(-0.5d).build());
							}
							window.show();
							window.centerOnScreen();
						}
					});
					return null;
				}
			};
			showTask.stateProperty().addListener(new ChangeListener<State>() {
				@Override
				public void changed(final ObservableValue<? extends State> observable,
						final State oldValue, final State newValue) {
					if (newValue == State.FAILED || newValue == State.CANCELLED) {
						Platform.runLater(createHideTask());
					}
				}
			});
			return showTask;
		}

		/**
		 * @return a task that will hide the service {@linkplain Stage}
		 */
		protected Task<Void> createHideTask() {
			final Task<Void> closeTask = new Task<Void>() {
				@Override
				protected Void call() throws Exception {
					window.hide();
					if (hasParentSceneRoot()) {
						parent.getScene().getRoot().setEffect(origEffect);
					}
					window.getScene().getRoot().setDisable(false);
					return null;
				}
			};
			return closeTask;
		}

		/**
		 * @return true when the parent {@linkplain Stage#getScene()} has a
		 *         valid {@linkplain Scene#getRoot()}
		 */
		private boolean hasParentSceneRoot() {
			return this.parent != null && this.parent.getScene() != null
					&& this.parent.getScene().getRoot() != null;
		}

		/**
		 * Hides the dialog used in the {@linkplain Service}
		 */
		public void hide() {
			Platform.runLater(createHideTask());
		}
	}
}

