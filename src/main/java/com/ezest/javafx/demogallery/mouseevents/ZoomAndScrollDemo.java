package com.ezest.javafx.demogallery.mouseevents;

import javafx.animation.Animation;
import javafx.animation.ParallelTransition;
import javafx.animation.ParallelTransitionBuilder;
import javafx.animation.RotateTransition;
import javafx.animation.RotateTransitionBuilder;
import javafx.animation.ScaleTransition;
import javafx.animation.ScaleTransitionBuilder;
import javafx.animation.Timeline;
import javafx.animation.TranslateTransition;
import javafx.animation.TranslateTransitionBuilder;
import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.geometry.Point2D;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.input.ScrollEvent;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;
import javafx.util.Duration;
 
public class ZoomAndScrollDemo extends Application
{
    Stage primStage;
    Scene mainScene;
    Group root;
    Pane masterPane;
    Point2D dragAnchor;
    double initX;
    double initY;
   
    public static void main(String[] args)
    {
        launch(args);
    }
   
    @Override
    public void init()
    {
        root = new Group();
       
        final Pane pane = new Pane();
        pane.setStyle("-fx-background-color: #CCFF99");
       
        pane.setOnScroll(new EventHandler<ScrollEvent>()
        {
            @Override
            public void handle(ScrollEvent se)
            {
                if(se.getDeltaY() > 0)
                {
                    pane.setScaleX(pane.getScaleX() + pane.getScaleX()/15);
                    pane.setScaleY(pane.getScaleY() + pane.getScaleY()/15);
                }
                else
                {
                    pane.setScaleX(pane.getScaleX() - pane.getScaleX()/15);
                    pane.setScaleY(pane.getScaleY() - pane.getScaleY()/15);
                }
            }
        });
       
        pane.setOnMousePressed(new EventHandler<MouseEvent>()
        {
            public void handle(MouseEvent me)
            {
                initX = pane.getTranslateX();
                initY = pane.getTranslateY();
                dragAnchor = new Point2D(me.getSceneX(), me.getSceneY());
            }
        });
       
        pane.setOnMouseDragged(new EventHandler<MouseEvent>()
        {
            public void handle(MouseEvent me) {
                double dragX = me.getSceneX() - dragAnchor.getX();
                double dragY = me.getSceneY() - dragAnchor.getY();
                //calculate new position of the pane
                double newXPosition = initX + dragX;
                double newYPosition = initY + dragY;
                //if new position do not exceeds borders of the rectangle, translate to this position
                pane.setTranslateX(newXPosition);
                pane.setTranslateY(newYPosition);
 
            }
        });
       
        
        
        int x = 0;
        int y = -40;
        for(int i = 0; i < 5; i++)
        {
            y = y + 40;
            for (int j = 0; j < 5; j++)
            {
                final Rectangle rect = new Rectangle(x, y, 30 , 30);
                final RotateTransition rotateTransition = RotateTransitionBuilder.create()
                        .node(rect)
                        .duration(Duration.seconds(4))
                        .fromAngle(0)
                        .toAngle(720)
                        .cycleCount(Timeline.INDEFINITE)
                        .autoReverse(true)
                        .build();
                rect.setOnMouseClicked(new EventHandler<MouseEvent>()
                {
                    public void handle(MouseEvent me)
                    {
                        if(rotateTransition.getStatus().equals(Animation.Status.RUNNING))
                        {
                            rotateTransition.setToAngle(0);
                            rotateTransition.stop();
                            rect.setFill(Color.BLACK);
                            rect.setScaleX(1.0);
                            rect.setScaleY(1.0);
                        }
                        else
                        {
                            rect.setFill(Color.AQUAMARINE);
                            rect.setScaleX(2.0);
                            rect.setScaleY(2.0);
                            rotateTransition.play();
                        }
                       
                        
                        
                        
                    }
                });
               
                pane.getChildren().add(rect);
                x = x + 40;
                
            }
            x = 0;
            
            
        }
       
        pane.autosize();
        pane.setPrefSize(pane.getWidth(), pane.getHeight());
        pane.setMaxSize(pane.getWidth(), pane.getHeight());
        root.getChildren().add(pane);
       
        masterPane = new Pane();
        masterPane.getChildren().add(root);
        masterPane.setStyle("-fx-background-color: #FF0000");
        masterPane.setOnMousePressed(new EventHandler<MouseEvent>()
        {
           public void handle(MouseEvent me)
           {
               System.out.println(me.getButton());
               if((MouseButton.MIDDLE).equals(me.getButton()))
               {
                   double screenWidth  = masterPane.getWidth();
                   double screenHeight = masterPane.getHeight();
                   double scaleXIs     = pane.getScaleX();
                   double scaleYIs     = pane.getScaleY();
                   double paneWidth    = pane.getWidth()  * scaleXIs;
                   double paneHeight   = pane.getHeight() * scaleYIs;
                  
                   double screenScale  = (screenWidth < screenHeight) ? screenWidth : screenHeight;
                   int    screenSide   = (screenWidth < screenHeight) ? 0 : 1;                 
                   
                   double scaleFactor = 0.0;
                   if(screenSide == 1)
                   {
                       scaleFactor = screenScale / paneWidth;
                   }
                   else
                   {
                       scaleFactor = screenScale / paneHeight;   
                   }
                  
                   double scaleXTo = scaleXIs * scaleFactor;
                   double scaleYTo = scaleYIs * scaleFactor;
                  
 
                   double moveToX  = (screenWidth /2) - (pane.getWidth()  / 2);
                   double moveToY  = (screenHeight/2) - (pane.getHeight() / 2);
                   TranslateTransition transTrans = TranslateTransitionBuilder.create()
                       .duration(Duration.seconds(2))
                       .toX(moveToX)
                       .toY(moveToY)
                       .build();
                   ScaleTransition scaleTrans = ScaleTransitionBuilder.create()
                       .duration(Duration.seconds(2))
                       .toX(scaleXTo)
                       .toY(scaleYTo)
                       .build();
                   ParallelTransition parallelTransition = ParallelTransitionBuilder.create()
                      .node(pane)
                      .children(transTrans,scaleTrans)
                      .build();
                   parallelTransition.play();
               }
           }
        });
       
    }
    public void start(Stage primaryStage)
    {
        primStage = primaryStage;
        mainScene = new Scene(masterPane, 430, 430);
        primaryStage.setScene(mainScene);
        primaryStage.show();
        primaryStage.setIconified(true);
        primaryStage.setIconified(false);
    }
}

