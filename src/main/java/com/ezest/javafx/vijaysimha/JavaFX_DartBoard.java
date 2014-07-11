package com.ezest.javafx.vijaysimha;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.scene.shape.ArcBuilder;
import javafx.scene.shape.ArcToBuilder;
import javafx.scene.shape.ArcType;
import javafx.scene.shape.CircleBuilder;
import javafx.scene.shape.LineTo;
import javafx.scene.shape.MoveTo;
import javafx.scene.shape.Path;
import javafx.scene.shape.PathBuilder;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.TextBuilder;
import javafx.stage.Stage;
 
/**
 * http://www.hameister.org/JavaFX_Dartboard.html
 * @author hameister
 */
public class JavaFX_DartBoard extends Application {
 
    // Base for the board size is 400px
    // The FACTOR_SIZE can be used to change the size of the board
    private final static double FACTOR_SIZE = 1.0d;
    private final static double BORDER_SIZE = 50;
    private final static double WIDTH = (400 + BORDER_SIZE) * FACTOR_SIZE;
    private final static double HEIGHT = (400 + BORDER_SIZE) * FACTOR_SIZE;
    private static double OUTER_DOUBLE_RING = 170.0d * FACTOR_SIZE;
    private static double INNER_DOUBLE_RING = 162.0d * FACTOR_SIZE;
    private static double OUTER_TRIPLE_RING = 107.0d * FACTOR_SIZE;
    private static double INNER_TRIPLE_RING = 99.0d * FACTOR_SIZE;
    private static double BULL = (31.8d / 2) * FACTOR_SIZE;
    private static double BULLS_EYE = (12.7d / 2) * FACTOR_SIZE;
    private static double CENTER_X = WIDTH / 2;
    private static double CENTER_Y = HEIGHT / 2;
    private static double POSITION_NUMBERS = 200 * FACTOR_SIZE;
    private final static int ROTATE_ANGLE_DEGREES = 18; // 18 Degrees per field
    private static int ROTATE_ANGLE_DEGREES_OFFSET = ROTATE_ANGLE_DEGREES / 2; // 9 Degrees per field
    private static final String[] DARTBOARD_NUMBERS = {
                    "20", "1", "18", "4", "13",
                    "6", "10", "15", "2", "17",
                    "3", "19", "7", "16", "8",
                    "11", "14", "9", "12", "5"
                   };
    private static final int FONT_SIZE = 20;
     
    @Override
    public void start(Stage primaryStage) {
        Pane root = new Pane();
         
        String doubleTripleStyle;
        String fieldStyle;
         
        for (int i = 0; i < DARTBOARD_NUMBERS.length; i++) {
            if (i % 2 == 0) {
                doubleTripleStyle = "double-triple-style-green";
                fieldStyle = "field-style-light";
            } else {
                doubleTripleStyle = "double-triple-style-red";
                fieldStyle = "field-style-dark";
            }
             
            // Create double rings
            root.getChildren().add(
                    createDartboardField(
                    i * ROTATE_ANGLE_DEGREES + ROTATE_ANGLE_DEGREES_OFFSET,
                    (i + 1) * ROTATE_ANGLE_DEGREES + ROTATE_ANGLE_DEGREES_OFFSET,
                    INNER_DOUBLE_RING,
                    OUTER_DOUBLE_RING,
                    doubleTripleStyle));
             
            // Create triple rings
            root.getChildren().add(
                    createDartboardField(
                    i * ROTATE_ANGLE_DEGREES + ROTATE_ANGLE_DEGREES_OFFSET,
                    (i + 1) * ROTATE_ANGLE_DEGREES + ROTATE_ANGLE_DEGREES_OFFSET,
                    INNER_TRIPLE_RING,
                    OUTER_TRIPLE_RING,
                    doubleTripleStyle));
             
            // Create outer fields
            root.getChildren().add(
                    createDartboardField(
                    i * ROTATE_ANGLE_DEGREES + ROTATE_ANGLE_DEGREES_OFFSET,
                    (i + 1) * ROTATE_ANGLE_DEGREES + ROTATE_ANGLE_DEGREES_OFFSET,
                    OUTER_TRIPLE_RING,
                    INNER_DOUBLE_RING,
                    fieldStyle));
             
            // Create inner fields
            root.getChildren().add(
                    createDartboardField(
                    i * ROTATE_ANGLE_DEGREES + ROTATE_ANGLE_DEGREES_OFFSET,
                    (i + 1) * ROTATE_ANGLE_DEGREES + ROTATE_ANGLE_DEGREES_OFFSET,
                    BULL,
                    INNER_TRIPLE_RING,
                    fieldStyle));
        }
          
        // Create Bull
        root.getChildren().add(ArcBuilder.create()
                .centerX(CENTER_X)
                .centerY(CENTER_Y)
                .type(ArcType.OPEN)
                .radiusX(BULL)
                .radiusY(BULL)
                .strokeWidth(BULL - BULLS_EYE)
                .styleClass("bull-style")
                .length(360)
                .build());
 
        // Create Bulls Eye
        root.getChildren().add(ArcBuilder
                .create()
                .centerX(CENTER_X)
                .centerY(CENTER_Y)
                .type(ArcType.OPEN)
                .radiusX(BULLS_EYE)
                .radiusY(BULLS_EYE)
                .strokeWidth(BULLS_EYE)
                .styleClass("bulls-eye-style")
                .length(360)
                .build());
 
        //Yellow circles around Bull
        root.getChildren().add(CircleBuilder
                .create()
                .centerX(CENTER_X)
                .centerY(CENTER_Y)
                .radius(BULL)
                .styleClass("border-line")
                .build());
         
        //Yellow circles around Bulls eye
        root.getChildren().add(CircleBuilder
                .create()
                .centerX(CENTER_X)
                .centerY(CENTER_Y)
                .radius(BULLS_EYE)
                .styleClass("border-line")
                .build());
 
  
        paintNumbers(root);
         
        Scene scene = new Scene(root, WIDTH, HEIGHT);
         
        primaryStage.setTitle("Dartboard");
        primaryStage.setScene(scene);
        scene.getStylesheets().add("styles/dartboard/theme2.css");
        primaryStage.show();
    }
 
    /**
     * Find the center on the X-Axis of the text. Is used to position the text
     * at the correct x position on the board.
     */
    private double calculateTextOffsetX(String text, double fontSize) {
        return (text.length() / 2) + fontSize * 0.5;
    }
 
    /**
     * Find the center on the Y-Axis of the text. Is used to position the text
     * at the correct y position on the board.
     */
    private double calculateTextOffsetY(double fontSize) {
        return (fontSize / 2) - fontSize * 0.4;
    }
     
    private void paintNumbers(Pane root) {
        TextBuilder textBuilder = TextBuilder.create()
                .font(Font.font("Verdana", FontWeight.BOLD, FONT_SIZE * FACTOR_SIZE))
                .styleClass("text-surround");
         
        for (int i = 0; i < DARTBOARD_NUMBERS.length; i++) {
            double degreeStart = i * ROTATE_ANGLE_DEGREES;
            double angleAlpha = degreeStart * (Math.PI / 180);
             
            double pointX1 = CENTER_X + POSITION_NUMBERS * Math.sin(angleAlpha)
                    - calculateTextOffsetX(DARTBOARD_NUMBERS[i], FONT_SIZE);
            double pointY1 = CENTER_Y - POSITION_NUMBERS * Math.cos(angleAlpha)
                    + calculateTextOffsetY(FONT_SIZE);
             
            root.getChildren().add(
                textBuilder
                     .x(pointX1)
                     .y(pointY1)
                     .text(DARTBOARD_NUMBERS[i])
                     .build()
                    );
        }
    }
     
    private Path createDartboardField(double degreeStart, double degreeEnd, double innerRadius, double outerRadius, String style) {
        double angleAlpha = degreeStart * (Math.PI / 180);
        double angleAlphaNext = degreeEnd * (Math.PI / 180);
 
        //Point 1
        double pointX1 = CENTER_X + innerRadius * Math.sin(angleAlpha);
        double pointY1 = CENTER_Y - innerRadius * Math.cos(angleAlpha);
 
        //Point 2
        double pointX2 = CENTER_X + outerRadius * Math.sin(angleAlpha);
        double pointY2 = CENTER_Y - outerRadius * Math.cos(angleAlpha);
 
        //Point 3
        double pointX3 = CENTER_X + outerRadius * Math.sin(angleAlphaNext);
        double pointY3 = CENTER_Y - outerRadius * Math.cos(angleAlphaNext);
 
        //Point 4
        double pointX4 = CENTER_X + innerRadius * Math.sin(angleAlphaNext);
        double pointY4 = CENTER_Y - innerRadius * Math.cos(angleAlphaNext);
         
        Path path = PathBuilder.create()
                .styleClass(style)
                .elements(
                new MoveTo(pointX1, pointY1),
                new LineTo(pointX2, pointY2),
                ArcToBuilder
                         .create()
                         .radiusX(outerRadius)
                         .radiusY(outerRadius)
                         .x(pointX3)
                         .y(pointY3)
                         .sweepFlag(true)
                         .build(),
                new LineTo(pointX4, pointY4),
                ArcToBuilder
                         .create()
                         .radiusX(innerRadius)
                         .radiusY(innerRadius)
                         .x(pointX1)
                         .y(pointY1)
                         .sweepFlag(false)
                         .build())
                .build();
        return path;
    }
     
    public static void main(String[] args) {
        launch(args);
    }
}
