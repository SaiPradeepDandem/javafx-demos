package com.ezest.javafx.components.dominoclock;

import javafx.beans.property.BooleanProperty;
import javafx.beans.property.DoubleProperty;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.Property;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.scene.control.ControlBuilder;
import javafx.scene.paint.Color;
import javafx.util.Builder;

import java.util.HashMap;


public class DominoBuilder<B extends DominoBuilder<B>> extends ControlBuilder<B> implements Builder<Domino> {
    private HashMap<String, Property> properties = new HashMap<String, Property>();


    // ******************** Constructors **************************************
    protected DominoBuilder() {}


    // ******************** Methods *******************************************
    public static final DominoBuilder create() {
        return new DominoBuilder();
    }

    public final DominoBuilder number(final int NUMBER) {
        properties.put("number", new SimpleIntegerProperty(NUMBER));
        return this;
    }

    public final DominoBuilder color(final Color COLOR) {
        properties.put("color", new SimpleObjectProperty<Color>(COLOR));
        return this;
    }

    public final DominoBuilder animated(final boolean ANIMATED) {
        properties.put("animated", new SimpleBooleanProperty(ANIMATED));
        return this;
    }

    @Override public final B prefWidth(final double PREF_WIDTH) {
        properties.put("prefWidth", new SimpleDoubleProperty(PREF_WIDTH));
        return (B)this;
    }

    @Override public final B prefHeight(final double PREF_HEIGHT) {
        properties.put("prefHeight", new SimpleDoubleProperty(PREF_HEIGHT));
        return (B)this;
    }

    @Override public final Domino build() {
        final Domino CONTROL = new Domino();
        for (String key : properties.keySet()) {
            if ("number".equals(key)) {
                CONTROL.setNumber(((IntegerProperty) properties.get(key)).get());
            } else if ("color".equals(key)) {
                CONTROL.setColor(((ObjectProperty<Color>) properties.get(key)).get());
            } else if ("animated".equals(key)) {
                CONTROL.setAnimated(((BooleanProperty) properties.get(key)).get());
            } else if("prefWidth".equals(key)) {
                CONTROL.setPrefWidth(((DoubleProperty) properties.get(key)).get());
            } else if("prefHeight".equals(key)) {
                CONTROL.setPrefHeight(((DoubleProperty) properties.get(key)).get());
            }
        }

        return CONTROL;
    }
}

