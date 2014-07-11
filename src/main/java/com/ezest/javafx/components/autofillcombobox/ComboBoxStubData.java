package com.ezest.javafx.components.autofillcombobox;

import java.util.ArrayList;
import java.util.List;

public class ComboBoxStubData {
	public static List<PostalCode> getCodes(){
		String[] names = {"Bruce Rock","Cambridge","Coolgardie","Cunderdin","Dalwallinu","Dowerin","Dundas","Esperance","Gingin","Goomalling","Joondalup"};
		List<PostalCode> list = new ArrayList<>();
		for (int i = 6000,k=0; i < 6011; i++,k++) {
			list.add(new PostalCode(names[k], ""+i));
		}
		return list;
	}
}
