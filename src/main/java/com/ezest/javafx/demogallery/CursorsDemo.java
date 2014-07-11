package com.ezest.javafx.demogallery;

import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.scene.Cursor;
import javafx.scene.Group;
import javafx.scene.ImageCursor;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.effect.Reflection;
import javafx.scene.image.Image;
import javafx.scene.layout.TilePane;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.stage.Stage;

public class CursorsDemo extends Application{

	public static void main(String[] args) {
		Application.launch(args);
	}

	@Override
	public void start(Stage stage) throws Exception {
		stage.setResizable(false);
		Group root  = new Group();
		root.getStyleClass().add("mainStage");
		Scene scene = new Scene(root, 832, 290, Color.BISQUE);
		scene.getStylesheets().add("styles/sample.css");
		
		
	    TilePane tp = new TilePane();
		tp.setPrefColumns(5);
		tp.setVgap(30);
		tp.setHgap(15);
		tp.setPadding(new Insets(10));
		
		tp.getChildren().add( new MyButton("CLOSED_HAND", Cursor.CLOSED_HAND));
		tp.getChildren().add( new MyButton("CROSSHAIR", Cursor.CROSSHAIR));
		tp.getChildren().add( new MyButton("DEFAULT", Cursor.DEFAULT));
		tp.getChildren().add( new MyButton("DISAPPEAR", Cursor.DISAPPEAR));
		tp.getChildren().add( new MyButton("E_RESIZE", Cursor.E_RESIZE));
		tp.getChildren().add( new MyButton("H_RESIZE", Cursor.H_RESIZE));
		tp.getChildren().add( new MyButton("HAND", Cursor.HAND));
		tp.getChildren().add( new MyButton("MOVE", Cursor.MOVE));
		tp.getChildren().add( new MyButton("N_RESIZE", Cursor.N_RESIZE));
		tp.getChildren().add( new MyButton("NE_RESIZE", Cursor.NE_RESIZE));
		tp.getChildren().add( new MyButton("NONE", Cursor.NONE));
		tp.getChildren().add( new MyButton("NW_RESIZE", Cursor.NW_RESIZE));
		tp.getChildren().add( new MyButton("OPEN_HAND", Cursor.OPEN_HAND));
		tp.getChildren().add( new MyButton("S_RESIZE", Cursor.S_RESIZE));
		tp.getChildren().add( new MyButton("SE_RESIZE", Cursor.SE_RESIZE));
		tp.getChildren().add( new MyButton("SW_RESIZE", Cursor.SW_RESIZE));
		tp.getChildren().add( new MyButton("TEXT", Cursor.TEXT));
		tp.getChildren().add( new MyButton("V_RESIZE", Cursor.V_RESIZE));
		tp.getChildren().add( new MyButton("W_RESIZE", Cursor.W_RESIZE));
		tp.getChildren().add( new MyButton("WAIT", Cursor.WAIT));
		
		Image img = new Image(getClass().getResourceAsStream("/images/close.png"));
		ImageCursor CURSOR_SEEK = new ImageCursor(img);
		tp.getChildren().add( new MyButton("CUSTOM CURSOR", CURSOR_SEEK));
		
		
	    root.getChildren().addAll(tp);
	    stage.setTitle("JavaFx Cursors Demo");
		stage.setScene(scene);
		stage.show();
	}
	
	/**
	 * Custom Button
	 * @author Sai.Dandem
	 *
	 */
	class MyButton extends Button{
		public MyButton(String text, Cursor cursor){
			setText(text);
			setFont(new Font("Tahoma", 18));
			setEffect(new Reflection());
		    setCursor(cursor);
		    setPrefWidth(150);
		}
	}

}
