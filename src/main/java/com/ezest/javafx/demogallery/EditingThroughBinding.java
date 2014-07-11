package com.ezest.javafx.demogallery;

import javafx.application.Application;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;
import javafx.util.Callback;

/**
 *
 * @author jKaufmann
 */
public class EditingThroughBinding extends Application {
  public static class TableData {
    private SimpleStringProperty firstName, lastName, phone, email;
    private ObjectProperty<SimpleStringProperty> firstNameObject;

    public TableData(String firstName, String lastName, String phone, String email) {
        this.firstName = new SimpleStringProperty(firstName);
        this.firstNameObject = new SimpleObjectProperty(firstNameObject);
        this.lastName = new SimpleStringProperty(lastName);
        this.phone = new SimpleStringProperty(phone);
        this.email = new SimpleStringProperty(email);
    }

    public String getEmail() {
        return email.get();
    }
    public void setEmail(String email) {
        this.email.set(email);
    }
    public SimpleStringProperty emailProperty() { return email; } 

    public String getFirstName() {
        return firstName.get();
    }
    public SimpleStringProperty getFirstNameObject() {
        return firstNameObject.get();
    }
    public void setFirstNameObject(SimpleStringProperty firstNameObject) {
        this.firstNameObject.set(firstNameObject);
    }
    public ObjectProperty<SimpleStringProperty> firstNameObjectProperty() { return firstNameObject; }

    public void setFirstName(String firstName) {
        this.firstName.set(firstName);
    }
    public SimpleStringProperty firstNameProperty() { 
        return firstName; 
    }
    public String getLastName() {
        return lastName.get();
    }
    public void setLastName(String lastName) {
        this.lastName.set(lastName);
    }
    public SimpleStringProperty lastNameProperty() { return lastName; }

    public String getPhone() {
        return phone.get();
    }
    public void setPhone(String phone) {
        this.phone.set(phone);
    }
    public SimpleStringProperty phoneProperty() { return phone; }

  }
  public static class TextFieldCellFactory  
     implements Callback<TableColumn<TableData,String>,TableCell<TableData,String>> {

    @Override
    public TableCell<TableData, String> call(TableColumn<TableData, String> param) {
        TextFieldCell textFieldCell = new TextFieldCell();
        return textFieldCell;
    }

    public static class TextFieldCell extends TableCell<TableData,String> {
        private TextField textField;
        private StringProperty boundToCurrently = null;

        public TextFieldCell() {
          String strCss;
          // Padding in Text field cell is not wanted - we want the Textfield itself to "be"
          // The cell.  Though, this is aesthetic only.  to each his own.  comment out
          // to revert back.  
          strCss = "-fx-padding: 0;";


          //this.setStyle(strCss);

          textField = new TextField();

          // 
          // Default style pulled from caspian.css. Used to play around with the inset background colors
          // ---trying to produce a text box without borders
          strCss = "" +
                    //"-fx-background-color: -fx-shadow-highlight-color, -fx-text-box-border, -fx-control-inner-background;" +
                    "-fx-background-color: -fx-control-inner-background;" +
                    //"-fx-background-insets: 0, 1, 2;" +
                    "-fx-background-insets: 0;" +
                    //"-fx-background-radius: 3, 2, 2;" +
                    "-fx-background-radius: 0;" +
                    "-fx-padding: 3 5 3 5;" +   /*Play with this value to center the text depending on cell height??*/
                    //"-fx-padding: 0 0 0 0;" +
                    "-fx-prompt-text-fill: derive(-fx-control-inner-background,-30%);" +
                    "-fx-cursor: text;" +
                    "";
          // Focused and hover states should be set in the CSS.  This is just a test
          // to see what happens when we set the style in code
          textField.focusedProperty().addListener(new ChangeListener<Boolean>() {

            public void changed(ObservableValue<? extends Boolean> observable, Boolean oldValue, Boolean newValue) {
                TextField tf = (TextField)getGraphic();
                String strStyleGotFocus = "-fx-background-color: purple, -fx-text-box-border, -fx-control-inner-background;" +
                            "-fx-background-insets: -0.4, 1, 2;" +
                            "-fx-background-radius: 3.4, 2, 2;";
                String strStyleLostFocus = //"-fx-background-color: -fx-shadow-highlight-color, -fx-text-box-border, -fx-control-inner-background;" +
                                   "-fx-background-color: -fx-control-inner-background;" +
                                 //"-fx-background-insets: 0, 1, 2;" +
                                   "-fx-background-insets: 0;" +
                                 //"-fx-background-radius: 3, 2, 2;" +
                                   "-fx-background-radius: 0;" +
                                   "-fx-padding: 3 5 3 5;" +   /**/
                                   //"-fx-padding: 0 0 0 0;" +
                                   "-fx-prompt-text-fill: derive(-fx-control-inner-background,-30%);" +
                                   "-fx-cursor: text;" +
                                   "";
                if(newValue.booleanValue())
                  tf.setStyle(strStyleGotFocus);
                else
                  tf.setStyle(strStyleLostFocus);             
            }
          });
          textField.hoverProperty().addListener(new ChangeListener<Boolean>() {

            public void changed(ObservableValue<? extends Boolean> observable, Boolean oldValue, Boolean newValue) {
                TextField tf = (TextField)getGraphic();
                String strStyleGotHover = "-fx-background-color: derive(purple,90%), -fx-text-box-border, derive(-fx-control-inner-background, 10%);" +
                            "-fx-background-insets: 1, 2.8, 3.8;" +
                            "-fx-background-radius: 3.4, 2, 2;";
                String strStyleLostHover = //"-fx-background-color: -fx-shadow-highlight-color, -fx-text-box-border, -fx-control-inner-background;" +
                                   "-fx-background-color: -fx-control-inner-background;" +
                                 //"-fx-background-insets: 0, 1, 2;" +
                                   "-fx-background-insets: 0;" +
                                 //"-fx-background-radius: 3, 2, 2;" +
                                   "-fx-background-radius: 0;" +
                                   "-fx-padding: 3 5 3 5;" +   /**/
                                   //"-fx-padding: 0 0 0 0;" +
                                   "-fx-prompt-text-fill: derive(-fx-control-inner-background,-30%);" +
                                   "-fx-cursor: text;" +
                                   "";
                String strStyleHasFocus = "-fx-background-color: purple, -fx-text-box-border, -fx-control-inner-background;" +
                            "-fx-background-insets: -0.4, 1, 2;" +
                            "-fx-background-radius: 3.4, 2, 2;";
                if(newValue.booleanValue()) {
                  tf.setStyle(strStyleGotHover);
                }
                else {
                  if(!tf.focusedProperty().get()) {
                    tf.setStyle(strStyleLostHover);
                  }
                  else {
                    tf.setStyle(strStyleHasFocus);
                  }
                }

            }
          });
          textField.setStyle(strCss);
          this.setGraphic(textField);
        }

        @Override
        protected void updateItem(String item, boolean empty) {
          super.updateItem(item, empty);        
          if(!empty) {
            // Show the Text Field
            this.setContentDisplay(ContentDisplay.GRAPHIC_ONLY);

            // Retrieve the actual String Property that should be bound to the TextField
            // If the TextField is currently bound to a different StringProperty
            // Unbind the old property and rebind to the new one
            ObservableValue<String> ov = getTableColumn().getCellObservableValue(getIndex());
            SimpleStringProperty sp = (SimpleStringProperty)ov;

            if(this.boundToCurrently==null) {
                this.boundToCurrently = sp;
                this.textField.textProperty().bindBidirectional(sp);
            }
            else {
                if(this.boundToCurrently != sp) {
                  this.textField.textProperty().unbindBidirectional(this.boundToCurrently);
                  this.boundToCurrently = sp;
                  this.textField.textProperty().bindBidirectional(this.boundToCurrently);
                }
            }
            System.out.println("item=" + item + " ObservableValue<String>=" + ov.getValue());
            //this.textField.setText(item);  // No longer need this!!!
          }
          else {
            this.setContentDisplay(ContentDisplay.TEXT_ONLY);
          }
        }

    }
  }
  public static void printNodeKidsRecursively(Node n, String tabs) {    
    String toTab = tabs == null ? "" : tabs;
    String msg1 = toTab + n.getClass().getName();
    String msg2 = ":" + n.toString();

    // Spit out and text data from Text classes
    if(javafx.scene.text.Text.class.isAssignableFrom(n.getClass())) {
        javafx.scene.text.Text t = (javafx.scene.text.Text)n;
        msg2 += " \"" +t.getText() + "\"";
    }

    // if this Node does not extend from Parent, then it can't have kids.
    if(!Parent.class.isAssignableFrom(n.getClass())) {
        System.out.println(msg1+msg2);
        return;
    }

    Parent p = (Parent)n;
    System.out.println(toTab + n.getClass().getName() + 
          "(KIDS=" + 
          Integer.toString(p.getChildrenUnmodifiable().size()) + ")" +
          msg2);

    ObservableList<Node> kids = p.getChildrenUnmodifiable();
    toTab +="  ";
    for(Node n2 : kids) {
        printNodeKidsRecursively(n2, toTab);
    }
  }

  private final TableView<TableData> table = new TableView<TableData>();
  final ObservableList<TableData> ol = 
        FXCollections.observableArrayList(
        new TableData("Wilma","Flintstone","555-123-4567","WFlintstone@gmail.com"),
        new TableData("Fred","Flintstone","555-123-4567","FFlintstone@gmail.com"),
        new TableData("Barney","Flintstone","555-123-4567","Barney@gmail.com"),
        new TableData("Bugs","Bunny","555-123-4567","BugsB@gmail.com"),
        new TableData("Yo","Sam","555-123-4567","ysam@gmail.com"),
        new TableData("Tom","","555-123-4567","tom@gmail.com"),
        new TableData("Jerry","","555-123-4567","Jerry@gmail.com"),
        new TableData("Peter","Pan","555-123-4567","Ppan@gmail.com"),
        new TableData("Daffy","Duck","555-123-4567","dduck@gmail.com"),
        new TableData("Tazmanian","Devil","555-123-4567","tdevil@gmail.com"),
        new TableData("Mickey","Mouse","555-123-4567","mmouse@gmail.com"),
        new TableData("Mighty","Mouse","555-123-4567","mimouse@gmail.com")
        );

  /**
   * @param args the command line arguments
   */
  public static void main(String[] args) {
    Application.launch(args);
  }

  @Override
  public void start(Stage Stage) {    
    Stage.setTitle("Editable Table");
    BorderPane borderPane = new BorderPane();
    Scene scene = new Scene(borderPane, 800, 600);

    // top of border pane
    Button b1 = new Button("Print Scene Graph for table Node");
    Button b2 = new Button("Change value in table list");
    HBox hbox = new HBox(10);
    hbox.setStyle("-fx-background-color: #336699");
    hbox.setAlignment(Pos.BOTTOM_CENTER);
    HBox.setMargin(b2, new Insets(10,0,10,0));
    HBox.setMargin(b1, new Insets(10,0,10,0));
    hbox.getChildren().addAll(b1,b2);
    borderPane.setTop(hbox);
    BorderPane.setAlignment(hbox, Pos.CENTER);

    // Button Events
    b1.setOnAction(new EventHandler<ActionEvent>() {

        public void handle(ActionEvent event) {
          printNodeKidsRecursively(table,"");
        }
    }); 
    b2.setOnAction(new EventHandler<ActionEvent>() {

        @Override
        public void handle(ActionEvent event) {
         String curFirstName = ol.get(0).getFirstName();
         if(curFirstName.contentEquals("Jason"))
             ol.get(0).setFirstName("Paul");
         else
             ol.get(0).setFirstName("Jason");
        }
    });

    table.setItems(ol);
    borderPane.setCenter(table);
    BorderPane.setAlignment(table, Pos.CENTER);
    BorderPane.setMargin(table, new Insets(25));

    // Add columns
    TableColumn<TableData,String> c1 = new TableColumn<TableData,String>("FirstName");
    c1.setCellValueFactory(new PropertyValueFactory<TableData,String>("firstName"));
    c1.setCellFactory(new TextFieldCellFactory());

    TableColumn<TableData,String> c2 = new TableColumn<TableData,String>("LastName");
    c2.setCellValueFactory(new PropertyValueFactory<TableData,String>("lastName"));
    c2.setCellFactory(new TextFieldCellFactory());

    TableColumn<TableData,String> c3 = new TableColumn<TableData,String>("Phone");
    c3.setCellValueFactory(new PropertyValueFactory<TableData,String>("phone"));
    c3.setCellFactory(new TextFieldCellFactory());

    TableColumn<TableData,String> c4 = new TableColumn<TableData,String>("Email");
    c4.setCellValueFactory(new PropertyValueFactory<TableData,String>("email"));
    c4.setCellFactory(new TextFieldCellFactory());

    table.getColumns().addAll(c1,c2,c3,c4);

    Stage.setScene(scene);
    Stage.show();

  }
}
