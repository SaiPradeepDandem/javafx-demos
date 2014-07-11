package com.ezest.javafx.demogallery.treeview;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

public class EmployeeDataBase {
	static List<Employee> employees = Arrays.<Employee>asList(
			new Employee(1, "Employee1"),
			new Employee(2, "Employee2"),
			new Employee(3, "Employee3"),
			new Employee(4, "Employee4"),
			new Employee(5, "Employee5"),
			new Employee(6, "Employee6"),
			new Employee(7, "Employee7"),
			new Employee(8, "Employee8"),
			new Employee(9, "Employee9"),
			new Employee(10, "Employee10"),
			new Employee(11, "Employee11"),
			new Employee(12, "Employee12"),
			new Employee(13, "Employee13"),
			new Employee(14, "Employee14"),
			new Employee(15, "Employee15"),
			new Employee(16, "Employee16"));
	
	@SuppressWarnings("serial")
	static Map<Long,Long> linkMap = new HashMap<Long,Long>() {
        {
        	put(1L,0L);
        	put(2L,0L);
        	put(3L,0L);
        	put(4L,1L);
        	put(5L,1L);
        	put(6L,4L);
        	put(7L,4L);
        	put(8L,2L);
        	put(9L,2L);
        	put(10L,3L);
        	put(11L,3L);
        	put(12L,8L);
        	put(13L,9L);
        	put(14L,10L);
        	put(15L,11L);
        	put(16L,5L);
        };
    };
	
    public static List<Employee> getChidren(Employee emp){
    	List<Employee> list = new ArrayList<Employee>();
    	long parentId = emp==null ? 0 : emp.getId();
    	List<Long> filIdList = new ArrayList<Long>();
    	for (Entry<Long, Long> entry : linkMap.entrySet()) {
			if(entry.getValue()==parentId){
				filIdList.add(entry.getKey());
			}
		}
    	
    	for (Employee employee : employees) {
			if(filIdList.contains(employee.getId())){
				list.add(employee);
			}
		}
    	return list;
    }

}
