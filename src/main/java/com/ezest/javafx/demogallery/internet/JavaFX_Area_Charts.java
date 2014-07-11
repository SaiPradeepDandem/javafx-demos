package com.ezest.javafx.demogallery.internet;

import javafx.application.Application;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.chart.AreaChart;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.stage.Stage;

/**
 *
 * @web http://java-buddy.blogspot.com/
 */
public class JavaFX_Area_Charts extends Application {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        launch(args);
    }
    
    @Override
    public void start(Stage primaryStage) {
        primaryStage.setTitle("java-buddy.blogspot.com");
        Group root = new Group();
        
        final CategoryAxis xAxis = new CategoryAxis();
        final NumberAxis yAxis = new NumberAxis();
        
        xAxis.setLabel("Month");
        yAxis.setLabel("Value");
        
        final AreaChart<String,Number> areaChart = new AreaChart<String,Number>(xAxis,yAxis);

        areaChart.setTitle("AreaChart");
        XYChart.Series series = new XYChart.Series();
        series.setName("XYChart.Series");
        
        series.getData().add(new XYChart.Data("January", 100));
        series.getData().add(new XYChart.Data("February", 200));
        series.getData().add(new XYChart.Data("March", 50));
        series.getData().add(new XYChart.Data("April", 75));
        series.getData().add(new XYChart.Data("May", 110));
        series.getData().add(new XYChart.Data("June", 300));
        series.getData().add(new XYChart.Data("July", 111));
        series.getData().add(new XYChart.Data("August", 30));
        series.getData().add(new XYChart.Data("September", 75));
        series.getData().add(new XYChart.Data("October", 55));
        series.getData().add(new XYChart.Data("November", 225));
        series.getData().add(new XYChart.Data("December", 99));
        
        areaChart.getData().add(series);
            
        root.getChildren().add(areaChart);

        primaryStage.setScene(new Scene(root, 500, 400));
        primaryStage.show();
    }
    
}
