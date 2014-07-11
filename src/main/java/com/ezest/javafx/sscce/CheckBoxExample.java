package com.ezest.javafx.sscce;

import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.CheckBox;
import javafx.scene.control.CheckBoxBuilder;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

/**
* Simple example of JavaFX 2.0 Checkbox control.
*
* @author Dustin
*/
public class CheckBoxExample extends Application
{
/**
* Provides a checkbox instance with specified String and supporting three
* states (supporting indeterminate state).
*
* @param checkboxText Text to go with checkbox.
* @return Checkbox with text.
*/
private CheckBox buildTriStateCheckbox(final String checkboxText)
{
// Note that AWT's Checkbox does not have capital 'b'
final CheckBox checkbox =
CheckBoxBuilder.create()
.allowIndeterminate(true)
.indeterminate(true)
.prefHeight(50)
.prefWidth(300)
.text(checkboxText)
.build();

checkbox.setOnMouseClicked(
new EventHandler<MouseEvent>()
{
@Override
public void handle(final MouseEvent mouseEvent)
{
final String newText =
checkbox.isIndeterminate()
? "Indeterminate!"
: checkbox.isSelected() ? "Selected!": "Unselected!";
checkbox.setText(newText);
}
}
);
return checkbox;
}

/**
* Overridden method defined in parent class and used in JavaFX application
* lifecycle.
*
* @param stage
* @throws Exception Exception during JavaFX sample application.
*/
@Override
public void start(final Stage stage) throws Exception
{
stage.setTitle("JavaFX 2 Checkbox Demo");
final Group rootGroup = new Group();
rootGroup.getChildren().add(buildTriStateCheckbox("Tri-State CheckBox"));
final Scene scene = new Scene(rootGroup, 300, 50, Color.CADETBLUE);
stage.setScene(scene);
stage.show();
}

/**
* Main function for running demonstration of JavaFX 2 Checkbox example.
*
* @param arguments Command-line arguments: none expected;
*/
public static void main(final String[] arguments)
{
Application.launch(arguments);
}
}
