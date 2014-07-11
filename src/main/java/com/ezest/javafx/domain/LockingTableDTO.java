package com.ezest.javafx.domain;

import java.util.Date;
import java.util.List;

import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;

public class LockingTableDTO {
	private SimpleDoubleProperty id = new SimpleDoubleProperty();
	private SimpleStringProperty name = new SimpleStringProperty();
	private SimpleStringProperty unit = new SimpleStringProperty();
	private SimpleObjectProperty<List<Date>> dateList = new SimpleObjectProperty<List<Date>>();
	private SimpleObjectProperty<List<Double>> valueList = new SimpleObjectProperty<List<Double>>();
	
	public LockingTableDTO(double id, String name, String unit){
		this.id.set(id);
		this.name.set(name);
		this.unit.set(unit);
	}
	
	public LockingTableDTO(){}
	
	public Double getId() {
        return id.get();
    }
    
	public void setId(double id) {
        this.id.set(id);
    }
    
    public SimpleDoubleProperty idProperty(){
    	return id;
    }
    
    public String getUnit() {
        return unit.get();
    }
    
    public SimpleStringProperty unitProperty(){
    	return unit;
    }
    
    public String getName() {
        return name.get();
    }
    
    public SimpleStringProperty nameProperty(){
    	return name;
    }

   public SimpleObjectProperty<List<Date>> dateListProperty() {
		return dateList;
	}
	
    public List<Date> getDateList() {
		return dateList.get();
	}
	
	public void setDateList(List<Date> dateList) {
		this.dateList.set(dateList);
	}
	
	public SimpleObjectProperty<List<Double>> valueListProperty() {
		return valueList;
	}
	
	public List<Double> getValueList() {
		return valueList.get();
	}
	
	public void setValueList(List<Double> valueList) {
		this.valueList.set(valueList);
	}
    
}
