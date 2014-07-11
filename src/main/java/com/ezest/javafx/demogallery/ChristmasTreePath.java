package com.ezest.javafx.demogallery;

import javafx.application.Application;  
import javafx.event.EventHandler;  
import javafx.scene.Group;  
import javafx.scene.Scene;  
import javafx.scene.effect.Glow;  
import javafx.scene.input.MouseEvent;  
import javafx.scene.paint.Color;  
import javafx.scene.paint.Paint;  
import javafx.scene.shape.*;  
import javafx.scene.text.Font;  
import javafx.scene.text.Text;  
import javafx.stage.Stage;  
  
/** 
 * Simple example of using JavaFX 2.0's Path to create a simple Christmas tree. 
 *  
 * @author Dustin 
 */  
public class ChristmasTreePath extends Application  
{  
   /** Number of branches on Christmas tree. */  
   private final static int NUMBER_BRANCHES = 4;  
   /** X-coordinate of very top of Christmas tree. */  
   private final static int TOP_CENTER_X = 400;  
   /** Y-coordinate of very top of Christmas tree. */  
   private final static int TOP_CENTER_Y = 25;  
   /** Horizontal distance to end of each branch. */  
   private final static int DELTA_X = 125;  
   /** Vertical distance to end of each branch. */  
   private final static int DELTA_Y = 100;  
   /** Length of each branch as measured on bottom of branch. */  
   private final static int BRANCH_LENGTH = 75;  
   /** Width of tree stump. */  
   private final static int STUMP_WIDTH = 100;  
   /** Height of tree stump. */  
   private final static int STUMP_HEIGHT = 150;  
   /** X-coordinate of top left corner of tree stump. */  
   private final static int LEFT_STUMP_X = TOP_CENTER_X - STUMP_WIDTH/2;  
   /** Y-coordinate of top left corner of tree stump. */  
   private final static int LEFT_STUMP_Y = TOP_CENTER_Y + DELTA_Y * NUMBER_BRANCHES;  
   /** Width of Christmas tree bottom. */  
   private final static int TREE_BOTTOM_WIDTH = (DELTA_X-BRANCH_LENGTH) * NUMBER_BRANCHES * 2;  
  
   /** 
    * Simple representation of (x, y) coordinate. 
    */  
   private static class Coordinate  
   {  
      /** Horizontal portion of coordinate. */  
      final private int x;  
  
      /** Vertical portion of coordinate. */  
      final private int y;  
  
      /** 
       * Create instance of me with 'x' and 'y' components. 
       *  
       * @param newX The horizontal portion of the coordinate. 
       * @param newY The vertical portion of the coordinate. 
       */  
      public Coordinate(final int newX, final int newY)  
      {  
         this.x = newX;  
         this.y = newY;   
      }  
  
      /** 
       * Provide String representation of this coordinate. 
       *  
       * @return String representation of this coordinate in form "(x, y)". 
       */  
      @Override  
      public String toString()  
      {  
         return "(" + this.x + ", " + this.y + ")";  
      }  
   }  
  
   /** 
    * Draw left side of the Christmas tree from top to bottom. 
    *  
    * @param path Path for left side of Christmas tree to be added to. 
    * @param startingX X portion of the starting coordinate. 
    * @param startingY Y portion of the starting coordinate. 
    * @return Coordinate with x and y values. 
    */  
   private Coordinate drawLeftSide(  
      final Path path, final int startingX, final int startingY)  
   {  
      int coordX = startingX - DELTA_X;  
      int coordY = startingY + DELTA_Y;  
      final LineTo topLeft = new LineTo(coordX, coordY);  
      path.getElements().add(topLeft);  
  
      coordX += BRANCH_LENGTH;  
      final LineTo topLeftHorizontal = new LineTo(coordX, coordY);  
      path.getElements().add(topLeftHorizontal);  
  
      coordX -= DELTA_X;  
      coordY += DELTA_Y;  
      final LineTo secondLeft = new LineTo(coordX, coordY);  
      path.getElements().add(secondLeft);  
  
      coordX += BRANCH_LENGTH;  
      final LineTo secondLeftHorizontal = new LineTo(coordX, coordY);  
      path.getElements().add(secondLeftHorizontal);  
  
      coordX -= DELTA_X;  
      coordY += DELTA_Y;  
      final LineTo thirdLeft = new LineTo(coordX, coordY);  
      path.getElements().add(thirdLeft);  
  
      coordX += BRANCH_LENGTH;  
      final LineTo thirdLeftHorizontal = new LineTo(coordX, coordY);  
      path.getElements().add(thirdLeftHorizontal);  
  
      coordX -= DELTA_X;  
      coordY += DELTA_Y;  
      final LineTo fourthLeft = new LineTo(coordX, coordY);  
      path.getElements().add(fourthLeft);  
  
      coordX += BRANCH_LENGTH;  
      final LineTo fourthLeftHorizontal = new LineTo(coordX, coordY);  
      path.getElements().add(fourthLeftHorizontal);  
  
      return new Coordinate(coordX, coordY);  
   }  
  
   /** 
    * Draw right side of the Christmas tree from bottom to top. 
    *  
    * @param path Path for right side of Christmas tree to be added to. 
    * @param startingX X portion of the starting coordinate. 
    * @param startingY Y portion of the starting coordinate. 
    * @return Coordinate with x and y values. 
    */  
   private Coordinate drawRightSide(  
      final Path path, final int startingX, final int startingY)  
   {  
      int coordX = startingX + BRANCH_LENGTH;  
      int coordY = startingY;  
      final LineTo bottomHorizontal = new LineTo(coordX, coordY);  
      path.getElements().add(bottomHorizontal);  
  
      coordX -= DELTA_X;  
      coordY -= DELTA_Y;  
      final LineTo bottomBranch = new LineTo(coordX, coordY);  
      path.getElements().add(bottomBranch);  
  
      coordX += BRANCH_LENGTH;  
      final LineTo secondHorizontal = new LineTo(coordX, coordY);  
      path.getElements().add(secondHorizontal);  
  
      coordX -= DELTA_X;  
      coordY -= DELTA_Y;  
      final LineTo secondBottomBranch = new LineTo(coordX, coordY);  
      path.getElements().add(secondBottomBranch);  
  
      coordX += BRANCH_LENGTH;  
      final LineTo thirdHorizontal = new LineTo(coordX, coordY);  
      path.getElements().add(thirdHorizontal);  
  
      coordX -= DELTA_X;  
      coordY -= DELTA_Y;  
      final LineTo thirdBottomBranch = new LineTo(coordX, coordY);  
      path.getElements().add(thirdBottomBranch);  
  
      coordX += BRANCH_LENGTH;  
      final LineTo fourthHorizontal = new LineTo(coordX, coordY);  
      path.getElements().add(fourthHorizontal);  
  
      coordX -= DELTA_X;  
      coordY -= DELTA_Y;  
      final LineTo fourthBottomBranch = new LineTo(coordX, coordY);  
      path.getElements().add(fourthBottomBranch);  
  
      return new Coordinate(coordX, coordY);  
   }  
  
   /** 
    * Draw stump of tree. 
    *  
    * @return Path representing Christmas tree stump. 
    */  
   private Path buildStumpPath()  
   {  
      final Path path = new Path();  
  
      int coordX = LEFT_STUMP_X;  
      int coordY = LEFT_STUMP_Y;  
      final MoveTo startingPoint = new MoveTo(coordX, coordY);  
      path.getElements().add(startingPoint);  
  
      coordY += STUMP_HEIGHT;  
      final LineTo leftStumpSide = new LineTo(coordX, coordY);  
      path.getElements().add(leftStumpSide);  
  
      coordX += STUMP_WIDTH;  
      final LineTo stumpBottom = new LineTo(coordX, coordY);  
      path.getElements().add(stumpBottom);  
  
      coordY -= STUMP_HEIGHT;  
      final LineTo rightStumpSide = new LineTo(coordX, coordY);  
      path.getElements().add(rightStumpSide);  
  
      coordX -= STUMP_WIDTH;  
      final LineTo topStump = new LineTo(coordX, coordY);  
      path.getElements().add(topStump);  
  
      path.setFill(Color.BROWN);  
  
      return path;  
   }  
  
   /** 
    * Build the exterior path of a Christmas Tree. 
    *  
    * @return Path representing the exterior of a simple Christmas tree drawing. 
    */  
   private Path buildChristmasTreePath()  
   {  
      int coordX = TOP_CENTER_X;  
      int coordY = TOP_CENTER_Y;  
      final Path path = new Path();  
      final MoveTo startingPoint = new MoveTo(coordX, coordY);  
      path.getElements().add(startingPoint);  
  
      final Coordinate bottomLeft = drawLeftSide(path, coordX, coordY);  
      coordX = bottomLeft.x + TREE_BOTTOM_WIDTH;  
      coordY = bottomLeft.y;  
  
      final LineTo treeBottom = new LineTo(coordX, coordY);  
      path.getElements().add(treeBottom);  
  
      drawRightSide(path, coordX, coordY);  
  
      path.setFill(Color.GREEN);  
  
      return path;  
   }  
  
   /** 
    * Create a bulb based on provided parameters and associate a MouseEvent to 
    * it such that clicking on a bulb will increase its size and enable the glow 
    * effect. 
    *  
    * @param centerX X-coordinate of center of bulb. 
    * @param centerY Y-coordinate of center of bulb. 
    * @param radius Radius of bulb. 
    * @param paint Paint/color instance to be used for bulb. 
    * @return Christmas tree bulb with interactive support. 
    */  
   private Circle createInteractiveBulb(  
      final int centerX, final int centerY, final int radius, final Paint paint)  
   {  
      final Circle bulb = new Circle(centerX, centerY, radius, paint);  
      bulb.setOnMouseClicked(  
         new EventHandler<MouseEvent>()    
         {  
            @Override  
            public void handle(MouseEvent mouseEvent)  
            {  
               bulb.setEffect(new Glow(1.0));  
               bulb.setRadius(bulb.getRadius() + 5);  
            }  
         });  
      return bulb;  
   }  
  
   /** 
    * Add colored circles (bulbs) to the provided Group. 
    *  
    * @param group Group to which 'bulbs' are to be added. 
    */  
   private void addBulbs(final Group group)  
   {  
      final Circle bulbOne = createInteractiveBulb(350,100,10, Color.RED);  
      group.getChildren().add(bulbOne);  
      final Circle bulbTwo = createInteractiveBulb(285,210,10, Color.YELLOW);  
      group.getChildren().add(bulbTwo);  
      final Circle bulbThree = createInteractiveBulb(325,300,10, Color.WHITE);  
      group.getChildren().add(bulbThree);  
      final Circle bulbFour = createInteractiveBulb(475,290,10, Color.BLUE);  
      group.getChildren().add(bulbFour);  
      final Circle bulbFive = createInteractiveBulb(380,150,10, Color.CADETBLUE);  
      group.getChildren().add(bulbFive);  
      final Circle bulbSix = createInteractiveBulb(550,390,10, Color.VIOLET);  
      group.getChildren().add(bulbSix);  
      final Circle bulbSeven = createInteractiveBulb(375,400,10, Color.GOLD);  
      group.getChildren().add(bulbSeven);  
      final Circle bulbEight = createInteractiveBulb(445,195,10, Color.SILVER);  
      group.getChildren().add(bulbEight);  
      final Circle bulbNine = createInteractiveBulb(220,385,10, Color.DARKSALMON);  
      group.getChildren().add(bulbNine);  
   }  
  
   /** 
    * Add text portions to Christmas Tree group. 
    *  
    * @param group Group for text to be added to. 
    */  
   private void addText(final Group group)  
   {  
      final Text text1 = new Text(25, 125, "Merry\nChristmas!");    
      text1.setFill(Color.RED);    
      text1.setFont(Font.font(java.awt.Font.SERIF, 50));    
      group.getChildren().add(text1);  
   
      final Text text2 = new Text(600, 150, "2011");  
      text2.setFill(Color.DARKGREEN);  
      text2.setFont(Font.font(java.awt.Font.SERIF, 75));  
      group.getChildren().add(text2);  
   }  
  
   /** 
    * Starting method of JavaFX application. 
    *  
    * @param stage Primary stage. 
    * @throws Exception Thrown for exceptional circumstances. 
    */  
   @Override  
   public void start(final Stage stage) throws Exception  
   {  
      stage.setTitle("JavaFX 2.0: Christmas Tree 2011");  
      final Group rootGroup = new Group();  
      final Scene scene = new Scene(rootGroup, 800, 600, Color.WHITE);  
      stage.setScene(scene);  
      rootGroup.getChildren().add(buildChristmasTreePath());  
      rootGroup.getChildren().add(buildStumpPath());  
      addBulbs(rootGroup);  
      addText(rootGroup);  
      stage.show();  
   }  
  
   /** 
    * Main function that kicks off this JavaFX demonstrative application. 
    *  
    * @param arguments Command-line arguments; none expected. 
    */  
   public static void main(final String[] arguments)  
   {  
      Application.launch(arguments);  
   }  
}  
