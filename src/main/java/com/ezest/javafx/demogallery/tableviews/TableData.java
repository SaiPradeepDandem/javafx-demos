package com.ezest.javafx.demogallery.tableviews;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import com.ezest.javafx.domain.LockingTableDTO;
import com.ezest.javafx.domain.MyDomain;

public class TableData {

	public static ObservableList<MyDomain> getData(){
		ObservableList<MyDomain> data = FXCollections.observableArrayList(
				 new MyDomain("Sai","This is for check 1."),
				 new MyDomain("Pradeep",null)
			 );
		
		return data;
	}
	
	public static ObservableList<LockingTableDTO> getLockingTableData(){
		ObservableList<LockingTableDTO> data = FXCollections.observableArrayList();
		
		List<Date> dateList = new ArrayList<Date>();
		Date dt = new Date();
		for (int i = 0; i < 9; i++) {
			dateList.add(dt);
			dt = incrementDate(dt);
		}
		
		for(double i=1;i<50;i++){
			LockingTableDTO dto = new LockingTableDTO(i,"Description for test "+i, (i%3==0)?"mmHg":"pulse/min");
			dto.setDateList(dateList);
			List<Double> valueList = new ArrayList<Double>();
			for (int j=0;j<dateList.size();j++){
				int t = (int)(Math.random()*200);
				valueList.add(t+0.0);
			}
			dto.setValueList(valueList);
			data.add(dto);
		}
		return data;
	}
	
	private static Date incrementDate(Date date){
		Calendar c = Calendar.getInstance();
		c.setTime(date);
		c.add(Calendar.DATE, 1);  
		return c.getTime();  
	}
}
