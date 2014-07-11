package com.ezest.javafx.demogallery.panes;

import javafx.application.Application;
import javafx.geometry.HPos;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import javafx.scene.paint.RadialGradientBuilder;
import javafx.scene.paint.Stop;
import javafx.stage.Stage;
 
public class GridPaneExample extends Application
{
    private final Paint background = RadialGradientBuilder.create()
            .stops(new Stop(0d, Color.TURQUOISE), new Stop(1, Color.web("3A5998")))
            .centerX(0.5d).centerY(0.5d).build();
    private final String LABEL_STYLE = "-fx-text-fill: white; -fx-font-size: 14;"
            + "-fx-effect: dropshadow(one-pass-box, black, 5, 0, 1, 1);";
     
    @Override
    public void start(Stage primaryStage) throws Exception
    {
        Scene scene = new Scene(createGridPane(), 370, 250, background);
        primaryStage.setTitle("GridPaneExample 2 - User form");
        primaryStage.setScene(scene);
        primaryStage.show();
    }
     
    private GridPane createGridPane()
    {
        GridPane gridPane = new GridPane();
        gridPane.setPadding(new Insets(20, 0, 20, 20));
        gridPane.setHgap(7); gridPane.setVgap(7);
         
        Label lbFirstName = new Label("First Name:");
        lbFirstName.setStyle(LABEL_STYLE);
        GridPane.setHalignment(lbFirstName, HPos.RIGHT);
        TextField tfFirstName = new TextField();
         
        Label lbLastName = new Label("Last Name:");
        lbLastName.setStyle(LABEL_STYLE);
        GridPane.setHalignment(lbLastName, HPos.RIGHT);
        TextField tfLastName = new TextField();
         
        Label lbCity = new Label("City:");
        lbCity.setStyle(LABEL_STYLE);
        GridPane.setHalignment(lbCity, HPos.RIGHT);
        TextField tfCity = new TextField();
         
        Label lbStreetNr = new Label("Street/Nr.:");
        lbStreetNr.setStyle(LABEL_STYLE);
        GridPane.setHalignment(lbStreetNr, HPos.RIGHT);
        TextField tfStreet = new TextField();
        tfStreet.setPrefColumnCount(14);
        GridPane.setColumnSpan(tfStreet, 2);
        TextField tfNumber = new TextField();
        tfNumber.setPrefColumnCount(3);
         
        Label lbNotes = new Label("Notes:");
        lbNotes.setStyle(LABEL_STYLE);
        GridPane.setHalignment(lbNotes, HPos.RIGHT);
        TextArea taNotes = new TextArea();
        taNotes.setPrefColumnCount(5);
        taNotes.setPrefRowCount(5);
        GridPane.setColumnSpan(taNotes, 3);
        GridPane.setRowSpan(taNotes, 2);   
         
        ImageView imageView = new ImageView(new Image(getClass()
                .getResourceAsStream("person.png"), 0, 65, true, true));
        GridPane.setHalignment(imageView, HPos.LEFT);
        GridPane.setColumnSpan(imageView, 2);
        GridPane.setRowSpan(imageView, 3);
         
//        gridPane.setGridLinesVisible(true);
         
        gridPane.add(lbFirstName, 0, 0); gridPane.add(tfFirstName, 1, 0);
        gridPane.add(imageView, 2, 0); gridPane.add(lbLastName, 0, 1);
        gridPane.add(tfLastName, 1, 1); gridPane.add(lbCity, 0, 2);
        gridPane.add(tfCity, 1, 2); gridPane.add(lbStreetNr, 0, 3);
        gridPane.add(tfStreet, 1, 3); gridPane.add(tfNumber, 3, 3);
        gridPane.add(lbNotes, 0, 4); gridPane.add(taNotes, 1, 4);
         
        return gridPane;
    }
     
    public static void main(String[] args)
    {   Application.launch(args);   }
}
