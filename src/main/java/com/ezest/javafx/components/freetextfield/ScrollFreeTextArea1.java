package com.ezest.javafx.components.freetextfield;

import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Group;
import javafx.scene.GroupBuilder;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.layout.Region;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.StackPaneBuilder;
import javafx.scene.text.Text;

/**
 * TextArea component which does not have a scroll enabled.
 * @author Sai.Dandem
 */
public class ScrollFreeTextArea1 extends StackPane{

	private Text text ;
	private Label label;
	//private TextArea textAreaBkp;
	private TextArea textArea;
	private Character enterChar = new Character((char)10);
	private Region content;
	private SimpleDoubleProperty contentHeight = new SimpleDoubleProperty();
	StackPane lblContainer ;
	
	private final double NEW_LINE_HEIGHT = 18D;
	private final double TOP_PADDING = 3D;
	private final double BOTTOM_PADDING = 6D;
	
	public ScrollFreeTextArea1(){
		super();
		configure();
	}
	
	private void configure(){
		setAlignment(Pos.TOP_LEFT);
		//this.textAreaBkp =new TextArea();
		//this.textAreaBkp.setText("\n");
		
		this.textArea =new TextArea(){
			protected void layoutChildren() {
				super.layoutChildren();
				if(content==null){
					content = (Region)lookup(".content");
					contentHeight.bind(content.heightProperty());
					content.heightProperty().addListener(new ChangeListener<Number>() {
						@Override
						public void changed(ObservableValue<? extends Number> paramObservableValue,	Number paramT1, Number paramT2) {
							//System.out.println("Content View Height :"+paramT2.doubleValue());
						}
					});
				}
			};
		};
		this.textArea.setWrapText(true);
		
		
		this.label =new Label();
		this.label.setWrapText(true);
		this.label.setPickOnBounds(true);
		
		this.label.prefWidthProperty().bind(this.textArea.widthProperty());
		
		this.text =new Text();
		this.text.wrappingWidthProperty().bind(this.label.prefWidthProperty());
		
		this.label.textProperty().bind(this.textArea.textProperty());
		/*label.textProperty().bind(new StringBinding() {
			{
				bind(textArea.textProperty());
			}
			@Override
			protected String computeValue() {
				if(textArea.getText()!=null && textArea.getText().length()>0){
					if(!((Character)textArea.getText().charAt(textArea.getText().length()-1)).equals(enterChar)){ 
						System.out.println("enter added.."+textAreaBkp.getText().length());
						return textArea.getText()+textAreaBkp.getText();
					}
				}
				return textArea.getText();
			}
		});*/
		
		/*textAreaBkp.textProperty().bind(new StringBinding() {
			{
				bind(textArea.textProperty());
			}
			@Override
			protected String computeValue() {
				if(textArea.getText()!=null && textArea.getText().length()>0){
					if(!((Character)textArea.getText().charAt(textArea.getText().length()-1)).equals(enterChar)){ 
						//System.out.println("enter added.."+textAreaBkp.getText().length());
						return textArea.getText()+"\n";
					}
				}
				return textArea.getText();
			}
		});
		this.label.textProperty().bind(this.textAreaBkp.textProperty());
		*/
		 lblContainer = StackPaneBuilder.create()
										  .alignment(Pos.TOP_LEFT)
										  .padding(new Insets(4,7,7,7))
										  .children(label)
										  .build();
		// Binding the container width to the TextArea width.
		lblContainer.maxWidthProperty().bind(textArea.widthProperty());
		
		textArea.textProperty().addListener(new ChangeListener<String>() {
			@Override
			public void changed(ObservableValue<? extends String> paramObservableValue,	String paramT1, String value) {
				
				//text.setText(value);
				//label.setText(value);
				layoutForNewLine(textArea.getText());
			}
		});
		
		label.heightProperty().addListener(new ChangeListener<Number>() {
			@Override
			public void changed(ObservableValue<? extends Number> paramObservableValue,	Number paramT1, Number paramT2) {
				layoutForNewLine(textArea.getText());
			}
		});
		
		requestToLayout();
	}
	
	private void layoutForNewLine(String text){
		if(text!=null && text.length()>0 && 
					((Character)text.charAt(text.length()-1)).equals(enterChar)){ 
			textArea.setPrefHeight(label.getHeight() + NEW_LINE_HEIGHT + TOP_PADDING + BOTTOM_PADDING);
			textArea.setMinHeight(label.getHeight() + NEW_LINE_HEIGHT + TOP_PADDING + BOTTOM_PADDING);
			
			//textAreaBkp.setPrefHeight(label.getHeight() + NEW_LINE_HEIGHT + TOP_PADDING + BOTTOM_PADDING);
			//textAreaBkp.setMinHeight(label.getHeight() + NEW_LINE_HEIGHT + TOP_PADDING + BOTTOM_PADDING);
			//setMinHeight(label.getHeight() + NEW_LINE_HEIGHT + TOP_PADDING + BOTTOM_PADDING);
		}else{
			textArea.setPrefHeight(label.getHeight() + TOP_PADDING + BOTTOM_PADDING);
			textArea.setMinHeight(label.getHeight() + TOP_PADDING + BOTTOM_PADDING);
			
			//textAreaBkp.setPrefHeight(label.getHeight() + TOP_PADDING + BOTTOM_PADDING);
			//textAreaBkp.setMinHeight(label.getHeight() + TOP_PADDING + BOTTOM_PADDING);
			
			/*textArea.setPrefHeight(contentHeight.get() + TOP_PADDING + BOTTOM_PADDING);
			textArea.setMinHeight(contentHeight.get() + TOP_PADDING + BOTTOM_PADDING);
			*///setMinHeight(label.getHeight() + TOP_PADDING + BOTTOM_PADDING);
			
			
		}
	}
	
	Group grp ;
	public void requestToLayout(){
		System.out.println("in request layout");
		getChildren().clear();
		grp = GroupBuilder.create().children(lblContainer).build();
		getChildren().addAll(grp,textArea);
	}
	
	public void requestGroup(){
		System.out.println("in request layout");
		grp.requestLayout();
	}
	
	
}
