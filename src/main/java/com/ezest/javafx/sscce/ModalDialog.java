package com.ezest.javafx.sscce;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
 
public class ModalDialog extends Stage {
	Stage owner;
	Stage stage;
	BorderPane root;
  //-----------------------------------------------------
  public ModalDialog( Stage owner, String title){
	root = new BorderPane();
	stage = this;
    this.owner = owner;
    initModality( Modality.APPLICATION_MODAL );
    initOwner( owner );
    initStyle( StageStyle.UTILITY ); 
    setTitle( title );
    setContents();
  }
  //------------------------------------------------
  public void setContents(){
    
    Scene scene = new Scene(root,150,150);
    setScene(scene);
    
    Group groupInDialog = new Group();
    groupInDialog.getChildren().add( new Label("Really Exit ?") );
    root.setCenter( groupInDialog );
   
    
    Button yes = new Button( "Yes" );
    yes.setOnAction(new EventHandler<ActionEvent>() {
		@Override
		public void handle(ActionEvent paramT) {
			stage.close();
			owner.close();
		}
	});
    
    Button no  = new Button( "No" );
    no.setOnAction(new EventHandler<ActionEvent>() {
		@Override
		public void handle(ActionEvent paramT) {
			stage.close();
		}
	});
    
    HBox buttonPane = new HBox();
    buttonPane.setSpacing(10);
    buttonPane.getChildren().addAll(yes,no);
    root.setBottom(buttonPane);
    
    stage.show();
  }
}

