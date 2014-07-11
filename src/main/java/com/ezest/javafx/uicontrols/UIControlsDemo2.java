package com.ezest.javafx.uicontrols;

import javafx.application.Application;
import javafx.beans.property.DoubleProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.TilePane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

public class UIControlsDemo2  extends Application{

	public static void main(String[] args) {
		Application.launch(args);
	}
	
	private SimpleObjectProperty<Person> person = new SimpleObjectProperty<Person>();
	
	@Override
	public void start(Stage stage) throws Exception {
		Group root  = new Group();
		Scene scene = new Scene(root, 1050, 600, Color.BISQUE);
		scene.getStylesheets().add("styles/sample.css");
		
		TilePane tp = new TilePane();
		tp.setPrefColumns(3);
		
		tp.getChildren().add( ScrollBarElement.getScrollPane());
		//tp.getChildren().add( ListElement.getSimpleList());
		//tp.getChildren().add( ListElement.getHSimpleList());
		tp.getChildren().add( TableElement.getTable());
		//tp.getChildren().add( getForm());
		//tp.getChildren().add( TableElement.getSimpleTable());
		
		
		root.getChildren().addAll(tp);
		
		stage.setTitle("JavaFx UI Controls Demo");
		stage.setScene(scene);
		stage.show();
	}

	private VBox getForm(){
		VBox vb = new VBox();
		person.setValue( new Person("Sai Pradeep", "Dandem", "sai.dandem@example.com"));
		
		/*final SimpleObjectProperty<Person> person2 = new SimpleObjectProperty<Person>();
		person2.bind(person);
		
		*/
		final SimpleStringProperty checkA = new SimpleStringProperty();
		final DoubleProperty checkB = new SimpleDoubleProperty();
		/*checkA.bind(checkB);
		checkB.set("Raja");
		System.out.println("Check 1 Val : "+checkA.get());
		*/
		final MyField fn = new MyField();
		//fn.textProperty().bind( ((Person)person.getValue()).firstNameProperty() );
		
		//fn.setText( ((Person)person.getValue()).getFirstName());
		//fn.setMyText( ((Person)person.getValue()).getFirstName());
		//fn.myTextProperty().bind( person.getValue().firstNameProperty() );
		checkA.bind( person.getValue().firstNameProperty() );
		//checkB.bind(fn.dbProperty());
		fn.dbProperty().bind(person.getValue().ageProperty());
		fn.setPromptText("First name");
		
		final TextField ln = new TextField();
		ln.setText( ((Person)person.getValue()).getLastName());
		ln.setPromptText("Last name");
		
		ln.textProperty().addListener(new ChangeListener<Object>() {
			@Override
			public void changed(ObservableValue arg0, Object arg1, Object arg2) {
				person.getValue().lastNameProperty().bind(arg0);
			}
		});
		
		final TextField em = new TextField();
		em.setText( ((Person)person.getValue()).getEmail());
		em.setPromptText("Email");
		
		ChangeListener<Object> lsitn= new ChangeListener<Object>() {
			@Override
			public void changed(ObservableValue var, Object oldValue, Object newValue) {
				fn.setMyText( ((Person)var.getValue()).getFirstName());
				ln.setText( ((Person)var.getValue()).getLastName());
				em.setText( ((Person)var.getValue()).getEmail());
			}
		};
		
		person.addListener(lsitn);
		
		Button btn = new Button("Add");
        btn.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent arg0) {
				/*person.setValue( new Person("Michael", "Brown", "michael.brown@example.com",45d));
				//fn.setDb(20d);
				//checkB.set(30);
				//System.out.println(((Person)person2.getValue()).getFirstName());
				System.out.println(fn.getMyText());
				System.out.println("Check 2 Val : "+fn.getDb());*/
				
				System.out.println("O/p : "+person.getValue().lastNameProperty());
			}
		});
        
        vb.getChildren().addAll(fn,ln,em,btn);
        
        return vb;
	}
}

class MyField extends TextField{
	SimpleStringProperty myText = new SimpleStringProperty();
	DoubleProperty db = new SimpleDoubleProperty();
	
	MyField this$0;
	public MyField(){
		super();
		this$0 = this;
		ChangeListener<Object> listner= new ChangeListener<Object>() {
			@Override
			public void changed(ObservableValue var, Object oldValue, Object newValue) {
				//System.out.println("Old : "+oldValue.toString());
				//System.out.println("New : "+newValue.toString());
				String dum = ((SimpleStringProperty)var).get();
				this$0.setText(dum);
			}
		};
		myText.addListener(listner);
	}
	
	
	
	public String getMyText() {
        return myText.get();
    }
    
    public SimpleStringProperty myTextProperty(){
    	return myText;
    }

    public void setMyText(String myText) {
    	//this.myText.set(myText);
    	//super.setText(this.myText.get());
    	//System.out.println("FINAL : "+super.getText());
    }
    
    public Double getDb() {
        return db.get();
    }
    
    public DoubleProperty dbProperty(){
    	return this.db;
    }

    public void setDb(Double db) {
    	this.db.set(db);
    }
}
