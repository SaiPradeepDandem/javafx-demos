package com.ezest.javafx.components.freetextfield;

import javafx.beans.binding.StringBinding;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollBar;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextArea;
import javafx.scene.layout.Region;
import javafx.scene.layout.StackPane;
import javafx.scene.text.Text;

public class FreeTextField_Bkp extends StackPane{

	private Text text;
	private Label label;
	private TextArea textArea;
	private ScrollPane taScrollPane;
	
	
	private Character enterChar = new Character((char)10);
	private Region content;
	private SimpleDoubleProperty contentHeight = new SimpleDoubleProperty();
	
	public FreeTextField_Bkp(){
		super();
		setAlignment(Pos.TOP_LEFT);
		//setPadding(new Insets(4,7,7,7));
		setStyle("-fx-background-color:#EAEAEA;");
		
		text = new Text();
		textArea = new TextArea(){
			protected void layoutChildren() {
				/*if(taScrollPane==null){
					ScrollBar sb = (ScrollBar)lookup(".scroll-bar");
					sb.setVisible(false);
					System.out.println(sb.getOrientation());
					taScrollPane = (ScrollPane)lookup(".scroll-pane");
					taScrollPane.setHbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);
					taScrollPane.setVbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);
				}*/
				super.layoutChildren();
				if(content==null){
					content = (Region)lookup(".content");
					contentHeight.bind(content.heightProperty());
					content.heightProperty().addListener(new ChangeListener<Number>() {

						@Override
						public void changed(
								ObservableValue<? extends Number> paramObservableValue,
								Number paramT1, Number paramT2) {
							System.out.println("Content View Height :"+paramT2.doubleValue());
						}
						
					});
				}
			
			};
		};
		textArea.setWrapText(true);
		
		label =new Label();
		label.setWrapText(true);
	
		withLabel();
		
		/*final StackPane sp = new StackPane();
		sp.setStyle("-fx-border-color:green;-fx-border-width:1px;");
		sp.setPadding(new Insets(5));
		sp.setAlignment(Pos.TOP_LEFT);
		//sp.maxWidthProperty().bind(widthProperty());
		
		Group gp = new Group();
		gp.getChildren().add(text);
		
		heightProperty().addListener(new ChangeListener<Number>() {
			@Override
			public void changed(ObservableValue<? extends Number> paramObservableValue,	Number paramT1, Number paramT2) {
				System.out.println("Sp Height : "+paramT2.doubleValue());
				//textArea.setPrefHeight(paramT2.doubleValue());
				//textArea.setMinHeight(paramT2.doubleValue());
			}
		});
		sp.getChildren().add(text);
		
		text.wrappingWidthProperty().bind(textArea.widthProperty());
		text.textProperty().bind(textArea.textProperty());
		
		//textArea.prefWidthProperty().bind(widthProperty());
		//textArea.prefHeightProperty().bind(heightProperty());
		textArea.textProperty().addListener(new ChangeListener<String>() {
			@Override
			public void changed(ObservableValue<? extends String> paramObservableValue,	String paramT1, String paramT2) {
				sp.getChildren().clear();
				text.setText(paramT2);
				sp.getChildren().add(text);
			}
		});
		label.prefWidthProperty().bind(textArea.widthProperty());
		label.textProperty().bind(textArea.textProperty());
		
		//textArea.prefHeightProperty().bind(label.heightProperty());
		textArea.setTranslateX(-7);
		textArea.setTranslateY(-5);
		
		
		getChildren().addAll(text,textArea);*/
	}
	
	/*private void withLabel1(){
		
		final SimpleBooleanProperty isTempSpace = new SimpleBooleanProperty();
		final KeyCombination icombination=new KeyCodeCombination(KeyCode.I,KeyCombination.CONTROL_DOWN);
		
		StackPane lblSp = new StackPane();
		lblSp.setAlignment(Pos.TOP_LEFT);
		lblSp.setPadding(new Insets(4,7,7,7));
		lblSp.setStyle("-fx-background-color:yellow;");
		lblSp.getChildren().add(label);
		
		lblSp.maxWidthProperty().bind(textArea.widthProperty());
		
		label.prefWidthProperty().bind(textArea.widthProperty());
		label.textProperty().bind(textArea.textProperty());
		
		textArea.textProperty().addListener(new ChangeListener<String>() {
			@Override
			public void changed(ObservableValue<? extends String> paramObservableValue,	String paramT1, String value) {
				if(isNL(paramT2));
				Character c = paramT2.charAt(paramT2.length()-1);
				System.out.println("Character : "+c);
				if(isTempSpace.get()){
					isTempSpace.set(false);
					System.out.println("Entered is temp space to remove space");
					String newStr = value.substring(0,value.length()-2)+ value.charAt(value.length()-1);
					System.out.println("new str : "+newStr);
					textArea.setText(newStr);
				
				}
				
			}
		});
		
		this.scrollPane.setHbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);
    this.scrollPane.setVbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);
		
		textArea.setOnKeyReleased(new EventHandler<KeyEvent>() {
			@Override
			public void handle(KeyEvent param) {
				if(param.getCode().equals(KeyCode.ENTER)){
					System.out.println("Setting extra space..");
					textArea.setText(textArea.getText()+" ");
					//textArea.end();//selectPositionCaret(textArea.getText().length()-1);
					isTempSpace.set(true);
				}else{
					isTempSpace.set(false);
				}
			}
		});
		
		label.heightProperty().addListener(new ChangeListener<Number>() {
			@Override
			public void changed(
					ObservableValue<? extends Number> paramObservableValue,
					Number paramT1, Number paramT2) {
				System.out.println("Label height :"+paramT2.doubleValue());
				textArea.setPrefHeight(paramT2.doubleValue()+6+3);
				textArea.setMinHeight(paramT2.doubleValue()+6+3);
			}
		});
		//textArea.prefHeightProperty().bind(label.heightProperty());
		//textArea.setTranslateX(-7);
		//textArea.setTranslateY(-5);
		
		
		getChildren().addAll(lblSp,textArea);
	}*/
	
	private void withLabel(){
		
		StackPane lblSp = new StackPane();
		lblSp.setAlignment(Pos.TOP_LEFT);
		lblSp.setPadding(new Insets(4,7,7,7));
		lblSp.setStyle("-fx-background-color:yellow;");
		lblSp.getChildren().add(label);
		
		lblSp.maxWidthProperty().bind(textArea.widthProperty());
		
		label.prefWidthProperty().bind(textArea.widthProperty());
		//label.textProperty().bind(textArea.textProperty());
				label.textProperty().bind(new StringBinding() {
					{
						bind(textArea.textProperty());
					}
					@Override
					protected String computeValue() {
						return textArea.getText()+enterChar;
					}
				});
		textArea.textProperty().addListener(new ChangeListener<String>() {
			@Override
			public void changed(ObservableValue<? extends String> paramObservableValue,	String paramT1, String value) {
				if(!checkForNewLine(textArea.getText())){
					textArea.setPrefHeight(label.getHeight()+6+3);
					textArea.setMinHeight(label.getHeight()+6+3);
				}
				System.out.println("contentHeight : "+contentHeight.get());
				/*textArea.setPrefHeight(contentHeight.get()+6+3);
				textArea.setMinHeight(contentHeight.get()+6+3);*/
			}
		});
		
		/*this.scrollPane.setHbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);
    this.scrollPane.setVbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);*/
		
		label.heightProperty().addListener(new ChangeListener<Number>() {
			@Override
			public void changed(
					ObservableValue<? extends Number> paramObservableValue,
					Number paramT1, Number paramT2) {
				System.out.println("Label height :"+paramT2.doubleValue());
				//checkForNewLine(textArea.getText());
				if(!checkForNewLine(textArea.getText())){
					textArea.setPrefHeight(paramT2.doubleValue()+6+3);
					textArea.setMinHeight(paramT2.doubleValue()+6+3);
				}
			}
		});
		
		//Group grp =GroupBuilder.create().children(lblSp).build();
		
		getChildren().addAll(lblSp,textArea);
	}
	
	private boolean checkForNewLine(String text){
		if(text!=null && text.length()>0){ 
			Character lastChar = text.charAt(text.length()-1);
			System.out.println("Last Char : "+lastChar);
			if(lastChar.equals(enterChar)){
				System.out.println("Setting modified height");
				//textArea.setPrefHeight(label.getHeight()+18+6+3);
				//textArea.setMinHeight(label.getHeight()+18+6+3);
				
				textArea.setPrefHeight(label.getHeight()+6+3);
				textArea.setMinHeight(label.getHeight()+6+3);
				return true;
			}else{
				/*System.out.println("Setting label height");
				textArea.setPrefHeight(label.getHeight()+6+3);
				textArea.setMinHeight(label.getHeight()+6+3);
				return false;*/
			}
		}
		return false;
	}
	
	private boolean isNL( String string)
	  {
	      if ( ( string == null ) || ( string.length() == 0 ))
	      {
	          return false;
	      }
	      else
	      {
	    	  char[] charArray = string.toCharArray();
	    	  int index = charArray.length-1; // last index
	          return ( ( ( charArray[ index ] == '\n' ) || 
	                   ( charArray[ index ] == '\r' ) )  ? true : false );
	      }
	  }
}
