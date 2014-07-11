package com.ezest.javafx.uicontrols;
import com.ezest.javafx.components.MemoComponent;
import com.ezest.javafx.domain.Memo;

import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Region;
import javafx.scene.layout.RowConstraints;
import javafx.scene.layout.TilePane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;


public class UIControlsDemo3 extends Application {
	public static void main(String[] args) {
		Application.launch(args);
	}
	
	@Override
	public void start(Stage stage) throws Exception {
		ScrollPane root  = new ScrollPane();
		Scene scene = new Scene(root, 1050, 600, Color.BISQUE);
		scene.getStylesheets().add("styles/sample.css");
		
		VBox vb = new VBox();
		
		MemoComponent<Memo> memo1 = new MemoComponent<Memo>("Personal Note:");
		memo1.setData(getMemoData1());
		
		MemoComponent<Memo> memo2 = new MemoComponent<Memo>("Secrets:");
		vb.getChildren().add(memo1);
		vb.getChildren().add(memo2);
		
		TextField tf = new TextField();
		tf.getStyleClass().add("my-field");
		vb.getChildren().add(tf);
		vb.getChildren().add(getEquiCellGrid());
		
		root.setContent(vb);
		
		stage.setTitle("JavaFx UI Controls Demo");
		stage.setScene(scene);
		stage.show();
	}
	
	public ObservableList<Memo> getMemoData1(){
		ObservableList<Memo> list = FXCollections.observableArrayList();
		list.add(new Memo("This is for chekc of the fist line.", "RSS", "Other"));
		list.add(new Memo("In Example 13-9, the createTextBox method uses a private textBox", "OST", "Other"));
		list.add(new Memo("The setCellFactory method of the TableColumn class can be used to install a custom cell factory.", "OST", "Physician In Surgery"));
		list.add(new Memo("Use the setOnEditCommit method of the TableColumn class, as shown in Example 13-11, so that the table can process any changes to its items.", "DSP", "Physician In Surgery"));
		list.add(new Memo("Replaces the corresponding element in the data observable list.", "DSP", "Other"));
		list.add(new Memo("You can find the complete source code of the address book application in the list of the Application Files.", "DSP", "Physician In Surgery"));
		return list;
	}
	
	public Node getEquiCellGrid(){
		GridPane grid = new GridPane();
        grid.setGridLinesVisible(true); //debug
        final int numRows = 10;
        final int numColumns = 6;
        for(int i = 0; i < numRows; i++) {
            RowConstraints row = new RowConstraints();
            row.setPercentHeight(100/numRows);
            grid.getRowConstraints().add(row);
        }
        for(int i = 0; i < numRows; i++) {
            ColumnConstraints column = new ColumnConstraints();
            column.setPercentWidth(100/numColumns);
            grid.getColumnConstraints().add(column);
        }

        // prevent grid from being resized larger so cells don't grow proportionally
        grid.setMaxSize(Region.USE_PREF_SIZE, Region.USE_PREF_SIZE);
        grid.addColumn(0, new Rectangle(75,75,Color.BLUE), new Rectangle(100,100,Color.RED));
        grid.addRow(0, new Button("Blue"), new Button("Magenta"));
        return grid;
	}
}

	

