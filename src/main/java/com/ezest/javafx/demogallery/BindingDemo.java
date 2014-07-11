package com.ezest.javafx.demogallery;

import javafx.application.Application;
import javafx.beans.binding.ObjectBinding;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.paint.Color;
import javafx.stage.Screen;
import javafx.stage.Stage;

public class BindingDemo extends Application {

	Stage stage;
	Scene scene;
	Group root;

	public static void main(String[] args) {
		Application.launch(args);
	}

	@Override
	public void start(Stage stage) throws Exception {
		this.stage = stage;
		configureScene();
		configureStage();
		final Person person = new Person();
		person.setName("Sai Pradeep");

		TextField txt = new TextField();
		txt.textProperty().bindBidirectional(person.nameProperty());

		Button btn = new Button("Show");
		btn.setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent arg0) {
				System.out.println("Name : " + person.getName());
			}
		});

		HBox hb = new HBox();
		hb.getChildren().addAll(txt, btn);
		root.getChildren().add(hb);
		
		Person p = new Person("Rahol", 50, 3);
		System.out.println(p.getGrade());
	}

	private void configureStage() {
		stage.setTitle("Binding Demo");
		stage.setX(0);
		stage.setY(0);
		stage.setWidth(Screen.getPrimary().getVisualBounds().getWidth());
		stage.setHeight(Screen.getPrimary().getVisualBounds().getHeight());
		stage.setScene(this.scene);
		stage.show();
	}

	private void configureScene() {
		root = new Group();
		root.autosize();
		this.scene = new Scene(root, Color.LINEN);
		scene.getStylesheets().add("styles/template.css");
	}

}

class Person {
	public Person() {

	}

	public Person(String name, int m, int c) {
		setName(name);
		setMarks(m);
		setCaste(c);
		grade.bind(new ObjectBinding<Grade>() {
			{
				bind(marks);
				bind(caste);
			}

			@Override
			protected Grade computeValue() {
				switch (caste.get()) {
				case 1:
					if (marks.get() >= 40) {
						return Grade.A;
					} else if (marks.get() >= 30 && marks.get() < 40) {
						return Grade.B;
					} else {
						return Grade.C;
					}
				case 2:
					if (marks.get() >= 60) {
						return Grade.A;
					} else if (marks.get() >= 40 && marks.get() < 60) {
						return Grade.B;
					} else {
						return Grade.C;
					}
				default:
					if (marks.get() >= 80) {
						return Grade.A;
					} else if (marks.get() >= 70 && marks.get() < 80) {
						return Grade.B;
					} else {
						return Grade.C;
					}
				}
			}
		});
	}

	private SimpleStringProperty name = new SimpleStringProperty();
	private final SimpleIntegerProperty marks = new SimpleIntegerProperty();
	private final SimpleIntegerProperty caste = new SimpleIntegerProperty();
	private SimpleObjectProperty<Grade> grade = new SimpleObjectProperty<Grade>();

	/**
	 * @return the name
	 */
	public SimpleStringProperty nameProperty() {
		return name;
	}

	/**
	 * @return the name
	 */
	public String getName() {
		return name.get();
	}

	/**
	 * @param name
	 *            the name to set
	 */
	public void setName(String name) {
		this.name.set(name);
	}

	/**
	 * @param name
	 *            the name to set
	 */
	public void setMarks(int marks) {
		this.marks.set(marks);
	}

	/**
	 * @param name
	 *            the name to set
	 */
	public void setCaste(int caste) {
		this.caste.set(caste);
	}

	public Grade getGrade(){
		return grade.get();
	}
}

enum Grade {
	A, B, C
}
