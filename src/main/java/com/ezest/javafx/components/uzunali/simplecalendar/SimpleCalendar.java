/**   
*  This file is part of SimpleCalendar.
    
*  SimpleCalendar is free software: you can redistribute it and/or modify   
*  it under the terms of the GNU General Public License as published by   
*  the Free Software Foundation, either version 3 of the License, or   
*  (at your option) any later version.
     
*  SimpleCalendar is distributed in the hope that it will be useful,   
*  but WITHOUT ANY WARRANTY; without even the implied warranty of   
*  MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the   
*  GNU General Public License for more details.

*  <http://www.gnu.org/licenses/>.
     
*/  
package com.ezest.javafx.components.uzunali.simplecalendar;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Point2D;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.layout.VBox;
import javafx.stage.Popup;

/**
 * A simple calendar to pick up a date.
 *
 */
public class SimpleCalendar extends VBox{

	private Popup popup;
	private SimpleStringProperty dateProperty;
	
	public SimpleCalendar() {
		popup = new Popup();
		popup.setAutoHide(true);
		popup.setAutoFix(true);
		popup.setHideOnEscape(true);

		dateProperty = new SimpleStringProperty("");
		
		final DatePicker datePicker = new DatePicker();
		datePicker.DateProperty().addListener(new ChangeListener<String>() {

			@Override
			public void changed(ObservableValue<? extends String> observable,
					String oldDate, String newDate) {
				dateProperty.set(newDate);
				if (popup.isShowing())
					popup.hide();
			}
		});
		popup.getContent().add(datePicker);

		final Button calenderButton = new Button();
		calenderButton.setId("CalenderButton");
		calenderButton.setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent ae) {
				Parent parent = SimpleCalendar.this.getParent();
				// Popup will be shown at upper left corner of calenderbutton
				Point2D point = calenderButton.localToScene(0, 0);
				final double layoutX = parent.getScene().getWindow().getX() + parent.getScene().getX() + point.getX();
				final double layoutY = parent.getScene().getWindow().getY() + parent.getScene().getY() + point.getY();
				popup.show(SimpleCalendar.this, layoutX, layoutY);

			}
		});
		
		getChildren().add(calenderButton);
	}
	
	/**
	 * @return a string bean property to bind the date information to a desired node
	 */
	public SimpleStringProperty dateProperty() {
		return dateProperty;
	}
	
}
