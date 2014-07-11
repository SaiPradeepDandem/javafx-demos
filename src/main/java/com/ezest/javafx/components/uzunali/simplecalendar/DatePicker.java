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

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

import javafx.beans.property.SimpleStringProperty;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.shape.LineTo;
import javafx.scene.shape.MoveTo;
import javafx.scene.shape.Path;

public class DatePicker extends StackPane {
	private boolean isSundayFirstDay = true;
	private Calendar today;
	private SimpleStringProperty dateProperty;
	private final DayCell[] dayCells;
	private int month;
	private int year;
	private final static int COLUMN_NUMBER = 7;
	private final static int ROW_NUMBER = 6;
	private final static double CELL_HEIGHT = 20;
	private final static double CELL_WIDTH = 25;
	private final static double ARROW_SIZE = CELL_HEIGHT * 0.3;
	private final static String DATEPICKER_OTHERMONTH = "datepicker-othermonth-cell";
	private final static String DATEPICKER_TODAY = "datepicker-today-cell";
	private final static String DATEPICKER_MONTH = "datepicker-month-cell";
	private final static String DATEPICKER_WEEKDAY = "datepicker-weekday-row";
	private final static String DATEPICKER_MONTHYEAR = "datepicker-monthyear-row";
	private final static String DATEPICKER_ARROW = "DatepickerArrow";
	private final static String DATEPICKER_DAYCELL = "DatepickerDayCell";
	private String[] cellStyleList = {DATEPICKER_OTHERMONTH, DATEPICKER_TODAY, DATEPICKER_MONTH};
	
	/**
	 * Constructor of DatePicker class
	 */
	public DatePicker(){
		super();
		VBox pickerBox = new VBox();
		setId("DatePicker");
		today = Calendar.getInstance();
		// Check whether Sunday is the first day of the week
		isSundayFirstDay = today.getFirstDayOfWeek() == 1 ? true : false;
		month = today.get(Calendar.MONTH);
		year = today.get(Calendar.YEAR);
		dateProperty = new SimpleStringProperty("");
		
		// First block of the picker pane, which is constructed to show month and year and to change them by decreasing and increasing.
		HBox monthYearRow = new HBox();
		final Label monthYear = new Label(getMonthYear((new GregorianCalendar()).getTime()));
		monthYear.setMinHeight(CELL_HEIGHT * 1.5);
		monthYear.setMinWidth(CELL_WIDTH * 6.0);
		monthYear.setAlignment(Pos.CENTER);
		monthYearRow.getStyleClass().add(DATEPICKER_MONTHYEAR);
		Path decrementArrow = new Path();
		decrementArrow.setId(DATEPICKER_ARROW);
		decrementArrow.getElements().addAll(new MoveTo(0, ARROW_SIZE), new LineTo(0, -ARROW_SIZE), 
				new LineTo(-ARROW_SIZE, 0), new LineTo(0, ARROW_SIZE));
		decrementArrow.setOnMouseClicked(new EventHandler<MouseEvent>() {
			@Override
			public void handle(MouseEvent me) {
				month--;
				if(month < 0) {
					month = Calendar.DECEMBER;
					year--;
				}
				monthYear.setText(getMonthYear((new GregorianCalendar(year, month, 1)).getTime()));
				setDayCells();
				me.consume();
			}
		});
		Path inreamentArrow = new Path();
		inreamentArrow.setId(DATEPICKER_ARROW);
		inreamentArrow.getElements().addAll(new MoveTo(0, ARROW_SIZE), new LineTo(0, -ARROW_SIZE), 
				new LineTo(ARROW_SIZE, 0), new LineTo(0, ARROW_SIZE));
		inreamentArrow.setOnMouseClicked(new EventHandler<MouseEvent>() {
			@Override
			public void handle(MouseEvent me) {
				month++;
				if(month > Calendar.DECEMBER) {
					month = Calendar.JANUARY;
					year++;
				}
				monthYear.setText(getMonthYear((new GregorianCalendar(year, month, 1)).getTime()));
				setDayCells();
				me.consume();
			}
		});
		monthYearRow.getChildren().addAll(decrementArrow, monthYear, inreamentArrow);
		monthYearRow.setAlignment(Pos.CENTER);
		
		// Second block of the picker pane, which is constructed to show the first letter of names of days in the week.		
		HBox firstLetterOfDayRow = new HBox();
		firstLetterOfDayRow.getStyleClass().add(DATEPICKER_WEEKDAY);
		String[] weekDays = getFirstLettersOfDays();
		for (int i = 0; i < weekDays.length; i++) {
			Label cell = new Label(weekDays[i]);
			cell.setMinHeight(CELL_HEIGHT);
			cell.setMinWidth(CELL_WIDTH);
			cell.setAlignment(Pos.CENTER);
			firstLetterOfDayRow.getChildren().add(cell);
		}
		pickerBox.getChildren().addAll(monthYearRow, firstLetterOfDayRow);
		
		// Third block of the picker pane, which is constructed to show the days in a ROW_NUMBER by COLUMN_NUMBER matrix.
		// The matrix constitutes of DayCell class which extends Label class and holds date information.
		dayCells = new DayCell[COLUMN_NUMBER * ROW_NUMBER];
		int index = 0;
		for (int i = 0; i < ROW_NUMBER; i++) {
			HBox row = new HBox();
			for (int j = 0; j < COLUMN_NUMBER; j++) {
				DayCell cell = createCell(0, 0, 0);
				row.getChildren().add(cell);
				cell.setId(DATEPICKER_DAYCELL);
				dayCells[index++] = cell; 
			}
			pickerBox.getChildren().add(row);
		}
		
		getChildren().add(pickerBox);
		
		// Setter of the day cells
		setDayCells();
	}

	/**
	 * @return DateProperty to bind to parent node 
	 */
	public SimpleStringProperty DateProperty(){
		return dateProperty;
	}
	
	/**
	 * @return an array of first letters of the week days based on default locale. 
	 * Monday is defined as the first day of the week in some locales. This property
	 * is controlled by <code>isSundayFirstDay</code> variable in the constructor of the
	 * <code>DatePicker</code> class. 
	 */
	private String[] getFirstLettersOfDays(){
		String[] firstLettersOfDays = new String[7];
		int d = isSundayFirstDay ? 1 : 2;
		// Use sequential dates to get the names of days of the week.  
		for (int i = 0; i < firstLettersOfDays.length; i++) {
			firstLettersOfDays[i] = 
					(new SimpleDateFormat("EEEE"))
					.format((new GregorianCalendar(0, 0, 3 + d + i))
					.getTime())
					.substring(0, 1);
		}

		return firstLettersOfDays;
	}

	/**
	 * @param date is the Gregorian date to be formatted
	 * @return the formatted date in the form of "Month, Year" like "January, 2012"
	 */
	private String getMonthYear(Date date){

		return (new SimpleDateFormat("MMMM, YYYY")).format(date);
	}
	
	/**
	 * Setter of cells in the matrix based on the month and the year. It takes 1 as the first day of the month
	 * to create a calendar. Once the calendar created it is calculated which day of the week is the first
	 * day of the month. Firstly, the cells for the days of previous month are set if any, and then the cells 
	 * for the days of present month are set, and finally the cells for the next month are set.  
	 */
	private void setDayCells(){
		Calendar calendar = Calendar.getInstance();
		calendar.set(Calendar.YEAR, year);
		calendar.set(Calendar.MONTH, month);
		calendar.set(Calendar.DAY_OF_MONTH, 1);
		// Calendar.DAY_OF_WEEK uses base 1. That's why one is subtracted.
		int firstDayOfMonth = calendar.get(Calendar.DAY_OF_WEEK) - 1;
		// Check whether Sunday is the first day of the week. If not, shift the first day 
		if(!isSundayFirstDay){
			firstDayOfMonth += 6;
			if (firstDayOfMonth > 7)
				firstDayOfMonth -= 7;
		}
		int daysInMonth = calendar.getActualMaximum(Calendar.DAY_OF_MONTH);
		calendar.set(Calendar.MONTH, month - 1);
		int daysInPreviousMonth = calendar.getActualMaximum(Calendar.DAY_OF_MONTH);

		// Set the cells for the days of previous month
		for (int i = 0; i < firstDayOfMonth; i++) {
			int m = month - 1;
			int y = year;
			if (m < 0) {
				m = Calendar.DECEMBER;
				y--;
			}
			DayCell cell = dayCells[i];
			cell.setDate(daysInPreviousMonth - (firstDayOfMonth) + i + 1, m, y);
			cell.getStyleClass().removeAll(cellStyleList);
			cell.getStyleClass().add(DATEPICKER_OTHERMONTH);
		}
		
		// Set the cells for the days of month to be presented 
		int day = 1;
		for (int i = firstDayOfMonth; i < daysInMonth + firstDayOfMonth; i++) {
			DayCell cell = dayCells[i];
			cell.setDate(day++, month, year);
			calendar.set(Calendar.YEAR, year);
			calendar.set(Calendar.MONTH, month);
			calendar.set(Calendar.DAY_OF_MONTH, cell.getDay());
			cell.getStyleClass().removeAll(cellStyleList);
			if (isToday(calendar)) 
				cell.getStyleClass().add(DATEPICKER_TODAY);
			else 
				cell.getStyleClass().add(DATEPICKER_MONTH);
		}
		
		// Set the cells for the days of next month
		day = 1;
		for (int i = firstDayOfMonth + daysInMonth; i < COLUMN_NUMBER * ROW_NUMBER; i++) {
			int m = month + 1;
			int y = year;
			if (m > Calendar.DECEMBER) {
				m = Calendar.JANUARY;
				y++;
			}
			DayCell cell = dayCells[i];
			cell.setDate(day++, m, y);
			cell.getStyleClass().removeAll(cellStyleList);
			cell.getStyleClass().add(DATEPICKER_OTHERMONTH);
		}
	}
	
	/**
	 * @param calendar is to be compared 
	 * @return true if the compared calendar is today
	 */
	private boolean isToday(Calendar calendar){
	
		return today.get(Calendar.YEAR) == calendar.get(Calendar.YEAR) && 
				today.get(Calendar.MONTH) == calendar.get(Calendar.MONTH) &&
				today.get(Calendar.DAY_OF_MONTH) == calendar.get(Calendar.DAY_OF_MONTH);
		
	}
	
	/**
	 * @param dayNumber is the day contained by the cell and used for label text
	 * @param month is the month contained by the cell. Its base is 0.
	 * @param year is the year contained by the cell
	 * @return the created cell which will be located in the matrix.
	 */
	private DayCell createCell(int dayNumber, int month, int year){
		final DayCell cell = new DayCell(dayNumber, month, year);
		cell.setMinHeight(CELL_HEIGHT);
		cell.setMinWidth(CELL_WIDTH);
		cell.setAlignment(Pos.CENTER);
		cell.setOnMouseClicked(new EventHandler<MouseEvent>() {
			@Override
			public void handle(MouseEvent me) {
				dateProperty.set("");
				dateProperty.set(dateBuilder(cell.getDay(), cell.getMonth(), cell.getYear()));
				me.consume();
			}
		});
		
		return cell;
	}
	
	/**
	 * @param day is the day of the date
	 * @param month is the month of the date
	 * @param year is the year of the date
	 * @return a string including the date information separated by "/"
	 */
	private String dateBuilder(int day, int month, int year){
		return String.valueOf(day) + "/" + 
				String.valueOf(month + 1) + "/" + 
				String.valueOf(year);
	}
	
	/**
	 * DayCell class is an extension of Label class and designed to hold the date
	 * information. The day of the date is used to set the text of super class.
	 * @author altug.uzunali
	 * 
	 */
	private class DayCell extends Label {
		private int d;
		private int m;
		private int y;
		 
		public DayCell(int day, int month, int year) {
			 super(String.valueOf(day));
		}

		public int getDay() {
			return d;
		}

		public int getMonth() {
			return m;
		}

		public int getYear() {
			return y;
		}

		public void setDate(int day, int month, int year){
			this.d = day;
			setText(String.valueOf(day));
			this.m = month;
			this.y = year;
		}
	}
}
