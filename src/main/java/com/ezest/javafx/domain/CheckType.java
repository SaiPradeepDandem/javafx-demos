package com.ezest.javafx.domain;

public class CheckType {
public static void main(String[] args) {
	Check<Integer> obj = new Check<Integer>();
}
}
class Check<T>{
	public Check(){
		System.out.println("Len : "+getClass().getGenericInterfaces().length);
		if(getClass().getGenericInterfaces().length>0){
			for(int i=0;i<getClass().getGenericInterfaces().length;i++){
				System.out.println(getClass().getGenericInterfaces()[i]);
			}
		}
		System.out.println("----"+getClass().getGenericSuperclass());
	}
}