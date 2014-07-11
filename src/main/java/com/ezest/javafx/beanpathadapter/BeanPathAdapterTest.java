package com.ezest.javafx.beanpathadapter;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.beans.InvalidationListener;
import javafx.beans.Observable;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Control;
import javafx.scene.control.Label;
import javafx.scene.control.Slider;
import javafx.scene.control.SplitPane;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.ToolBar;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;

public class BeanPathAdapterTest extends Application {

	ChoiceBox<String> pBox;
	TextArea pojoTA = new TextArea();
	public static final String[] STATES = new String[] { "AK", "AL", "AR",
			"AS", "AZ", "CA", "CO", "CT", "DC", "DE", "FL", "GA", "GU", "HI",
			"IA", "ID", "IL", "IN", "KS", "KY", "LA", "MA", "MD", "ME", "MH",
			"MI", "MN", "MO", "MS", "MT", "NC", "ND", "NE", "NH", "NJ", "NM",
			"NV", "NY", "OH", "OK", "OR", "PA", "PR", "PW", "RI", "SC", "SD",
			"TN", "TX", "UT", "VA", "VI", "VT", "WA", "WI", "WV", "WY" };
	private static final String P1_LABEL = "Person 1 (initially has data)";
	private static final String P2_LABEL = "Person 2 (initially no data)";
	private static final String P3_LABEL = "Person 3 (initially no data)";
	private final Person person1 = new Person();
	private final Person person2 = new Person();
	private final Person person3 = new Person();
	private final BeanPathAdapter<Person> personPA = new BeanPathAdapter<>(
			person1);

	public static void main(final String[] args) {
		Application.launch(BeanPathAdapterTest.class, args);
	}

	public BeanPathAdapterTest() {
		super();
		person1.setAge(50d);
		person1.setName("Person 1");
		Address addy = new Address();
		Location loc = new Location();
		loc.setCountry(1);
		loc.setInternational(true);
		loc.setState("KY");
		addy.setStreet("123 Test Street");
		addy.setLocation(loc);
		person1.setAddress(addy);
	}

	@Override
	public void start(Stage primaryStage) throws Exception {
		primaryStage.setTitle(BeanPathAdapter.class.getSimpleName() + " TEST");
		pojoTA.setFocusTraversable(false);
		pojoTA.setWrapText(true);
		pojoTA.setEditable(false);
		pBox = new ChoiceBox<>(FXCollections.observableArrayList(P1_LABEL,
				P2_LABEL, P3_LABEL));
		pBox.getSelectionModel().select(0);
		pBox.valueProperty().addListener(new ChangeListener<String>() {
			@Override
			public void changed(ObservableValue<? extends String> observable,
					String oldValue, String newValue) {
				personPA.setBean(newValue == P1_LABEL ? person1
						: newValue == P2_LABEL ? person2 : person3);
			}
		});
		pBox.autosize();
		ToolBar toolBar = new ToolBar();
		toolBar.getItems().add(pBox);
		VBox personBox = new VBox(10);
		personBox.setPadding(new Insets(10, 10, 10, 50));
		VBox beanPane = new VBox(10);
		beanPane.setPadding(new Insets(10, 10, 10, 10));
		final Text title = new Text(
				"Person POJO using auto-generated JavaFX properties. "
						+ "Duplicate field controls exist to demo multiple control binding");
		title.setWrappingWidth(400d);
		personBox.getChildren().addAll(
				beanTF("name", 50, null, "[a-zA-z0-9\\s]*"),
				beanTF("age", 100, Slider.class, null),
				beanTF("age", 100, null, "[0-9]"),
				beanTF("address.street", 50, null, "[a-zA-z0-9\\s]*"),
				beanTF("address.location.state", 2, ComboBox.class, "[a-zA-z]",
						STATES),
				beanTF("address.location.country", 10, null, "[0-9]"),
				beanTF("address.location.country", 2, ComboBox.class, "[0-9]",
						new Integer[]{0, 1, 2, 3}),
				beanTF("address.location.international", 0, CheckBox.class,
						null), new Label("POJO Dump:"), pojoTA);
		beanPane.getChildren().addAll(title, personBox);

		final TextField pojoNameTF = new TextField();
		Button pojoNameBtn = new Button("Set Person's Name");
		pojoNameBtn.setOnMouseClicked(new EventHandler<MouseEvent>() {
			@Override
			public void handle(MouseEvent event) {
				personPA.getBean().setName(pojoNameTF.getText());
				dumpPojo(personPA);
			}
		});
		VBox pojoBox = new VBox(10);
		pojoBox.setPadding(new Insets(10, 10, 10, 10));
		Text lbl = new Text("Set selected person's field data via POJO "
				+ "with unbound controls (not working because without "
				+ "dependency injection instrumentation, java agent, or "
				+ "byte-code manipulation this is not currently possible- "
				+ "maybe a JavaFX life-cycle listener would work?):");
		lbl.setWrappingWidth(300d);
		pojoBox.getChildren().addAll(lbl, new Label("Name:"), pojoNameTF,
				pojoNameBtn);

		SplitPane pojoSplit = new SplitPane();
		pojoSplit.getItems().addAll(beanPane, pojoBox);
		VBox beanBox = new VBox(10);
		beanBox.getChildren().addAll(toolBar, pojoSplit);
		primaryStage.setOnShowing(new EventHandler<WindowEvent>() {
			@Override
			public void handle(WindowEvent event) {
				dumpPojo(personPA);
			}
		});
		primaryStage.setScene(new Scene(beanBox));
		primaryStage.show();
	}

	@SafeVarargs
	public final void dumpPojo(final BeanPathAdapter<Person>... ps) {
		Platform.runLater(new Runnable() {
			@Override
			public void run() {
				String dump = "";
				for (BeanPathAdapter<Person> p : ps) {
					dump += "Person {name="
							+ p.getBean().getName()
							+ ", age="
							+ p.getBean().getAge()
							+ ", address.street="
							+ p.getBean().getAddress().getStreet()
							+ ", address.location.state="
							+ p.getBean().getAddress().getLocation().getState()
							+ ", address.location.country="
							+ p.getBean().getAddress().getLocation()
									.getCountry()
							+ ", address.location.international="
							+ p.getBean().getAddress().getLocation()
									.isInternational() + "}\n";
				}
				pojoTA.setText(dump);
			}
		});
	}

	@SuppressWarnings("unchecked")
	public <T> HBox beanTF(String path, final int maxChars,
			Class<? extends Control> controlType, 
			final String restictTo, T... choices) {
		HBox box = new HBox();
		Control ctrl;
		if (controlType == CheckBox.class) {
			CheckBox cb = new CheckBox();
			cb.selectedProperty().addListener(new ChangeListener<Boolean>() {
				@Override
				public void changed(
						ObservableValue<? extends Boolean> observable,
						Boolean oldValue, Boolean newValue) {
					dumpPojo(personPA);
				}
			});
			// POJO binding magic...
			personPA.bindBidirectional(path, cb.selectedProperty());
			ctrl = cb;
		} else if (controlType == ComboBox.class) {
			ComboBox<T> cb = new ComboBox<>(
					FXCollections.observableArrayList(choices));
			cb.setPromptText("Select State");
			cb.setPrefWidth(100d);
			cb.valueProperty().addListener(new InvalidationListener() {
				@Override
				public void invalidated(Observable observable) {
					dumpPojo(personPA);
				}
			});
			// POJO binding magic (due to erasure of T in 
			// ObjectProperty<T> of cb.valueProperty() we need
			// to also pass in the choice class
			personPA.bindBidirectional(path, cb.valueProperty(), 
					(Class<T>) choices[0].getClass());
			ctrl = cb;
		} else if (controlType == Slider.class) {
			Slider sl = new Slider();
			sl.setShowTickLabels(true);
			sl.setShowTickMarks(true);
			sl.setMajorTickUnit(maxChars / 2);
			sl.setMinorTickCount(7);
			sl.setBlockIncrement(1);
			sl.setMax(maxChars + 1);
			sl.setSnapToTicks(true);
			sl.valueProperty().addListener(new InvalidationListener() {
				@Override
				public void invalidated(Observable observable) {
					dumpPojo(personPA);
				}
			});
			// POJO binding magic...
			personPA.bindBidirectional(path, sl.valueProperty());
			ctrl = sl;
		} else {
			final TextField tf = new TextField() {
				@Override
				public void replaceText(int start, int end, String text) {
					if (matchTest(text)) {
						super.replaceText(start, end, text);
					}
				}

				@Override
				public void replaceSelection(String text) {
					if (matchTest(text)) {
						super.replaceSelection(text);
					}
				}

				private boolean matchTest(String text) {
					return text.isEmpty()
							|| (text.matches(restictTo) && (getText() == null || getText()
									.length() < maxChars));
				}
			};
			tf.textProperty().addListener(new ChangeListener<String>() {
				@Override
				public void changed(
						ObservableValue<? extends String> observable,
						String oldValue, String newValue) {
					dumpPojo(personPA);
				}
			});
			// POJO binding magic...
			personPA.bindBidirectional(path, tf.textProperty());
			ctrl = tf;
		}
		box.getChildren().addAll(new Label(path + " = "), ctrl);
		return box;
	}

	public HBox beanTFW(String startLabel, String endLabel, TextField... tfs) {
		HBox box = new HBox();
		box.getChildren().add(new Label(startLabel + '('));
		box.getChildren().addAll(tfs);
		box.getChildren().add(new Label(endLabel + ");"));
		return box;
	}

	public static class Person {
		private String name;
		private Address address;
		private double age;

		public String getName() {
			return name;
		}

		public void setName(String name) {
			this.name = name;
		}

		public Address getAddress() {
			return address;
		}

		public void setAddress(Address address) {
			this.address = address;
		}

		public double getAge() {
			return age;
		}

		public void setAge(double age) {
			this.age = age;
		}
	}

	public static class Address {
		private String street;
		private Location location;

		public String getStreet() {
			return street;
		}

		public void setStreet(String street) {
			this.street = street;
		}

		public Location getLocation() {
			return location;
		}

		public void setLocation(Location location) {
			this.location = location;
		}
	}

	public static class Location {
		private int country;
		private String state;
		private Boolean isInternational;

		public int getCountry() {
			return country;
		}

		public void setCountry(int country) {
			this.country = country;
		}

		public String getState() {
			return state;
		}

		public void setState(String state) {
			this.state = state;
		}

		public Boolean isInternational() {
			return isInternational;
		}

		public void setInternational(Boolean isInternational) {
			this.isInternational = isInternational;
		}
	}

}

