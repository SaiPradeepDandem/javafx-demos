package com.ezest.javafx.uicontrols;

import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.Timeline;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ContextMenu;
import javafx.scene.control.Label;
import javafx.scene.control.MenuItem;
import javafx.scene.control.RadioButton;
import javafx.scene.control.ToggleButton;
import javafx.scene.effect.DropShadow;
import javafx.scene.effect.Reflection;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCharacterCombination;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyCodeCombination;
import javafx.scene.input.KeyCombination;
import javafx.scene.input.KeyCombination.Modifier;
import javafx.scene.input.KeyCombination.ModifierValue;
import javafx.scene.input.Mnemonic;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.TilePane;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.util.Duration;

public class UIControlsDemo extends Application{

	public static void main(String[] args) {
		Application.launch(args);
	}
	
	@Override
	public void start(Stage stage) throws Exception {
		Group root  = new Group();
		
		Scene scene = new Scene(root, 1050, 500, Color.BISQUE);
		scene.getStylesheets().add("styles/sample.css");
		
		final Button button = new Button();
	    button.setText("Hello Check");
	    button.setFont(new Font("Tahoma", 24));
	    button.setEffect(new Reflection());
	    
	    ContextMenu menu = new ContextMenu();
	    MenuItem item1 = new MenuItem("Item 1");
	    MenuItem item2 = new MenuItem("Item 2");
	    MenuItem item3 = new MenuItem("Item 3");
	    menu.getItems().addAll(item1,item2,item3);
	    button.setContextMenu(menu);
	    
	    
	    
	    
	    final Timeline timeline = new Timeline();
	    timeline.setCycleCount(Timeline.INDEFINITE);
	    timeline.setAutoReverse(true);
	    final KeyValue kv = new KeyValue(button.opacityProperty(), 0);
	    final KeyFrame kf = new KeyFrame(Duration.valueOf("600s"), kv);
	    timeline.getKeyFrames().add(kf);
	    //timeline.play();
	   
	    
	    button.setOnAction(new EventHandler<ActionEvent>() {
	    	@Override
			public void handle(ActionEvent arg0) {
	    		timeline.play();
			}
		});
	    
	   //Adding the shadow when the mouse cursor is on
	    button.addEventHandler(MouseEvent.MOUSE_ENTERED, 
			      new EventHandler<MouseEvent>() {
			          @Override public void handle(MouseEvent e) {
			              button.setEffect(new DropShadow());
			          }
			  });
		   
	    button.addEventHandler(MouseEvent.MOUSE_EXITED, 
			      new EventHandler<MouseEvent>() {
			          @Override public void handle(MouseEvent e) {
			              button.setEffect(new Reflection());
			          }
			  });
		   
		    
	    
	    
	    ToggleButton tb3 = new ToggleButton ("I don't know");
	    tb3.setStyle("-fx-font: 22 arial; -fx-base: yellow");
	    tb3.setGraphic( new ImageView( new Image(getClass().getResourceAsStream("/images/close.png"))));
	    
	    
	    Label label2 = new Label("Search");
	    label2.setFont(Font.font("Cambria", 32));
	    label2.setRotate(270);
	    label2.setMnemonicParsing(true);
	    
	    //KeyCombination kc = new KeyCodeCombination(KeyCode.B, ModifierValue.ANY,ModifierValue.DOWN,ModifierValue.UP, ModifierValue.DOWN, ModifierValue.DOWN);
	    KeyCombination kc = new KeyCharacterCombination("A", KeyCombination.SHIFT_DOWN);
	    Mnemonic aa = new Mnemonic(label2, kc);
	    aa.fire();
	    
	    final Timeline timeline2 = new Timeline();
	    timeline2.setCycleCount(Timeline.INDEFINITE);
	    timeline2.setAutoReverse(true);
	    final KeyValue kv2 = new KeyValue(button.rotateProperty(), 0);
	    final KeyFrame kf2 = new KeyFrame(Duration.valueOf("600s"), kv2);
	    timeline2.getKeyFrames().add(kf2);
	    timeline2.play();
	    
	    Image image = new Image(getClass().getResourceAsStream("/images/search.png"));
	    final Label label3 = new Label("Search", new ImageView(image));
	    label3.getStyleClass().add("emrHeaderLbl");
	    
	    label3.setOnMouseEntered(new EventHandler<MouseEvent>() {
			@Override
			public void handle(MouseEvent arg0) {
				label3.setScaleX(1.5);
				label3.setScaleY(1.5);
			}
		});
	    label3.setOnMouseExited(new EventHandler<MouseEvent>() {
			@Override
			public void handle(MouseEvent arg0) {
				label3.setScaleX(1);
				label3.setScaleY(1);
			}
		});
	    
	    HBox hb = new HBox();
	    hb.setPadding(new Insets(15, 12, 15, 12));
		hb.setSpacing(10);
		
		hb.getChildren().add(button);
		hb.getChildren().add(tb3);
		hb.getChildren().add(label2);
		hb.getChildren().add(label3);
		
		FlowPane fp = new FlowPane();
		fp.setPrefWrapLength(scene.getWidth()-100);
		fp.getChildren().add(hb);
		//fp.getChildren().add(tb3);
		//fp.getChildren().add(label2);
		//fp.getChildren().add(label3);
		
		TilePane tp = new TilePane();
		tp.setPrefColumns(4);
		tp.getChildren().add(button);
		tp.getChildren().add(tb3);
		tp.getChildren().add(label2);
		tp.getChildren().add(label3);
		tp.getChildren().add(getRadioButton(scene));
		tp.getChildren().add(getGraphicRadioButton());
		tp.getChildren().add( RadioButtonGroup.getNode());
		tp.getChildren().add( ToggleButtonGroup.getNode());
		tp.getChildren().add( ToggleButtonGroup.getNode2());
		tp.getChildren().add( CheckBoxGroup.getCheckBox());
		tp.getChildren().add( getCustomFont());
		tp.getChildren().add( ChoiceBoxElement.getNode());
		tp.getChildren().add( ScrollBarElement.getSimpleBar());
		
		Text text = new Text("Sai Prad");
		text.getStyleClass().add("emrHeaderTxt");
		tp.getChildren().add( text);
		
		Image imageSearch = new Image(getClass().getResourceAsStream("/images/search.png"));
		System.out.println(imageSearch.isError());
		if(imageSearch.isError()){
			tp.getChildren().add( new Label("Unable to load image"));
		}else{
			tp.getChildren().add( new ImageView(imageSearch));
		}
		
	   
		
		
	    root.getChildren().addAll(tp,ScrollBarElement.getAppScrollBar(scene,tp));
	    
	    
		stage.setTitle("JavaFx UI Controls Demo");
		stage.setScene(scene);
		stage.show();
	}
	
	private Node getCustomFont() {
		HBox hb = new HBox();
		
		Text text1 = new Text("Sai");		
		text1.getStyleClass().add("myCustomFontLabel");
		
		Text text2 = new Text("Pradeep");		
		text2.setFont(Font.loadFont(UIControlsDemo.class.getResourceAsStream("/fonts/BleedingCowboys.ttf"), 22));
		
		Text text3 = new Text("Dandem");		
		//text3.getStyleClass().add("myCustomFontLabel");
		
		hb.getChildren().addAll(text1,text2,text3);
		
		return hb;
	}

	private Node getRadioButton(Scene scene){
		RadioButton rb1 = new RadioButton();
		rb1.setText("Home");
		
		RadioButton rb2 = new RadioButton("Calendar");
		
		return rb2;
	}
	
	private Node getGraphicRadioButton(){
		Image image = new Image(getClass().getResourceAsStream("/images/close.png"));
		RadioButton rb = new RadioButton("Agree");
		rb.setGraphic(new ImageView(image));
		
		return rb;
	}
	
	
	
	

}
