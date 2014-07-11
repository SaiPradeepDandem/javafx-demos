package com.ezest.javafx.demogallery.controls;

import javafx.scene.control.ControlBuilder;
import javafx.scene.control.TextField;
import javafx.util.Builder;

public class CustomTextFieldBuilder<B extends CustomTextFieldBuilder<B>> extends ControlBuilder<B>
													implements Builder<TextField> {

	private int __set;
	private int maxCharLength ;
	private CustomTextField.TextFieldType type;
	
	public static CustomTextFieldBuilder<?> create(){
		return new CustomTextFieldBuilder();
	}
	
	public void applyTo(CustomTextField paramTextField){
	    super.applyTo(paramTextField);
	    int i = this.__set;
	    if ((i & 0x1) != 0)
	      paramTextField.setMaxCharLength(this.maxCharLength);
	    if ((i & 0x2) != 0)
	      paramTextField.setType(this.type);
	   }
	
	public B maxCharLength(int length){
		this.maxCharLength = length;
	    this.__set |= 1;
		return (B) this;
	}
	
	public B type(CustomTextField.TextFieldType type){
		this.type = type;
	    this.__set |= 2;
		return (B) this;
	}
	
	@Override
	public TextField build() {
		CustomTextField localTextField = new CustomTextField();
		applyTo(localTextField);
		return localTextField;
	}

}
