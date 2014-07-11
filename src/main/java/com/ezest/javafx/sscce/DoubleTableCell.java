package com.ezest.javafx.sscce;

import javafx.geometry.Pos;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.util.Callback;

public class DoubleTableCell<T> extends TableCell<T, Double>
{
	@Override
	public void updateItem(Double item, boolean empty){
		super.updateItem(item, empty);
		if (!isEmpty()){
			setAlignment(Pos.CENTER_RIGHT);
			
			//String value = Util.formatDoubleAsString(item);
			
			//setText(value);
		}
	}
}
class AFS{
	/*public void showMe(){
		// Create column and set cell factory
		TableColumn<MortgagePayment, Double> paymentCol = new TableColumn<MortgagePayment, Double>("Payment");
		paymentCol.setCellFactory(createDoubleCellFactory());
	}
	// Create cell factory
	public Callback<TableColumn<MortgagePayment, Double>, TableCell<MortgagePayment, Double>> createDoubleCellFactory(){
		return new Callback<TableColumn<MortgagePayment, Double>, TableCell<MortgagePayment, Double>>(){
			public TableCell<MortgagePayment, Double> call(TableColumn<MortgagePayment, Double> p){
				return new DoubleTableCell<MortgagePayment>();
			}
		};
	}*/
}
