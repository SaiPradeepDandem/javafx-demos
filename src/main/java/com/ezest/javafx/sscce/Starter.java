package com.ezest.javafx.sscce;

import javafx.animation.FadeTransition;
import javafx.animation.Interpolator;
import javafx.animation.ParallelTransition;
import javafx.animation.ScaleTransition;
import javafx.animation.SequentialTransition;
import javafx.beans.property.DoubleProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.event.EventHandler;
import javafx.scene.effect.DropShadow;
import javafx.scene.effect.DropShadowBuilder;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.util.Duration;

public class Starter extends StackPane
{
 
    private ParallelTransition startTransition;
    private ScaleTransition scaleTransition;
    private ScaleTransition initTransition;
    private FadeTransition fadeTransition;
    private SequentialTransition startsSequentialTransition;
    private DoubleProperty expandToMaxProperty;
    private ImageView baseImage;
 
    public Starter(String base, double fitWidth, double fitHeight)
    {
        baseImage = new ImageView(base);
        init(fitWidth, fitHeight);
    }
 
    public Starter(Image base, double fitWidth, double fitHeight)
    {
        baseImage = new ImageView(base);
        init(fitWidth, fitHeight);
    }
 
    private void init(double fitWidth, double fitHeight)
    {
        expandToMaxProperty = new SimpleDoubleProperty(1.2);
 
        baseImage.setFitWidth(fitWidth);
        baseImage.setFitHeight(fitHeight);
 
        Color selectedColor = Color.rgb(0, 0, 0, 0.5);
        DropShadow dropShadow = DropShadowBuilder.create()
                .color(selectedColor)
                .build();
        setEffect(dropShadow);
 
        dropShadow.radiusProperty()
                .bind(scaleXProperty()
                .multiply(8));
        dropShadow.offsetXProperty()
                .bind(scaleXProperty()
                .multiply(8));
        dropShadow.offsetYProperty()
                .bind(scaleYProperty()
                .multiply(8));
 
        scaleTransition =
                new ScaleTransition(Duration.millis(200), this);
        scaleTransition.setCycleCount(1);
        scaleTransition
                .setInterpolator(Interpolator.EASE_BOTH);
 
 
 
        fadeTransition = new FadeTransition(Duration.millis(200), this);
        fadeTransition.setCycleCount(1);
        fadeTransition
                .setInterpolator(Interpolator.EASE_BOTH);
 
        startTransition = new ParallelTransition();
        startTransition.setCycleCount(2);
        startTransition.setAutoReverse(true);
        startTransition.getChildren()
                .addAll(scaleTransition,
                fadeTransition);
 
        initTransition =
                new ScaleTransition(Duration.millis(200), this);
        initTransition.setToX(1);
        initTransition.setToY(1);
        initTransition.setCycleCount(1);
        initTransition
                .setInterpolator(Interpolator.EASE_BOTH);
 
        startsSequentialTransition = new SequentialTransition();
        startsSequentialTransition.getChildren()
                .addAll(
                startTransition,
                initTransition);
 
        getChildren()
                .addAll(baseImage);
 
        setOnMouseEntered(new EventHandler<MouseEvent>()
        {
            @Override
            public void handle(MouseEvent t)
            {
                scaleTransition.setFromX(getScaleX());
                scaleTransition.setFromY(getScaleY());
                scaleTransition.setToX(expandToMaxProperty.get());
                scaleTransition.setToY(expandToMaxProperty.get());
                scaleTransition.playFromStart();
            }
        });
 
        setOnMouseExited(new EventHandler<MouseEvent>()
        {
            @Override
            public void handle(MouseEvent t)
            {
                scaleTransition.setFromX(getScaleX());
                scaleTransition.setFromY(getScaleY());
                scaleTransition.setToX(1);
                scaleTransition.setToY(1);
                scaleTransition.playFromStart();
            }
        });
 
        setOnMouseClicked(new EventHandler<MouseEvent>()
        {
            @Override
            public void handle(MouseEvent t)
            {
                scaleTransition.setFromX(getScaleX());
                scaleTransition.setFromY(getScaleY());
                scaleTransition.setToX(2);
                scaleTransition.setToY(2);
                fadeTransition.setFromValue(1.0f);
                fadeTransition.setToValue(0.5f);
                startsSequentialTransition.playFromStart();
            }
        });
    }
 
    public DoubleProperty expandToMaxProperty()
    {
        return expandToMaxProperty;
    }
}


