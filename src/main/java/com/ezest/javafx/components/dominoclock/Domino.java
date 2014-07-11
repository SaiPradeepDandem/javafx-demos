package com.ezest.javafx.components.dominoclock;

import javafx.beans.property.BooleanProperty;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.scene.control.Control;
import javafx.scene.paint.Color;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class Domino extends Control {
    private static final String     DEFAULT_STYLE_CLASS = "domino";
    private IntegerProperty         number;
    private ObjectProperty<Color>   color;
    private BooleanProperty         animated;
    private Map<Integer, List<Dot>> mapping;
    public static enum Dot {
        D00, D10, D20,
        D01, D11, D21,
        D02, D12, D22,
        //*********//
        D03, D13, D23,
        D04, D14, D24,
        D05, D15, D25
    };


    // ******************** Constructors **************************************
    public Domino() {
        number     = new SimpleIntegerProperty(0);
        color      = new SimpleObjectProperty<Color>(Color.WHITE);
        animated   = new SimpleBooleanProperty(true);
        mapping    = new HashMap<Integer, List<Dot>>(12);
        initMapping();
        init();
    }

    private void init() {
        getStyleClass().add(DEFAULT_STYLE_CLASS);
    }

    private void initMapping() {
        // 0 - 12
        mapping.clear();
        mapping.put(0, Arrays.asList(new Dot[]{}));
        mapping.put(1, Arrays.asList(new Dot[]{Dot.D14}));
        mapping.put(2, Arrays.asList(new Dot[]{Dot.D14, Dot.D11}));
        mapping.put(3, Arrays.asList(new Dot[]{Dot.D03, Dot.D25, Dot.D11}));
        mapping.put(4, Arrays.asList(new Dot[]{Dot.D03, Dot.D25, Dot.D00, Dot.D22}));
        mapping.put(5, Arrays.asList(new Dot[]{Dot.D03, Dot.D14, Dot.D25, Dot.D00, Dot.D22}));
        mapping.put(6, Arrays.asList(new Dot[]{Dot.D03, Dot.D14, Dot.D25, Dot.D00, Dot.D11, Dot.D22}));
        mapping.put(7, Arrays.asList(new Dot[]{Dot.D03, Dot.D23, Dot.D05, Dot.D25, Dot.D00, Dot.D11, Dot.D22}));
        mapping.put(8, Arrays.asList(new Dot[]{Dot.D03, Dot.D23, Dot.D05, Dot.D25, Dot.D00, Dot.D02, Dot.D20, Dot.D22}));
        mapping.put(9, Arrays.asList(new Dot[]{Dot.D03, Dot.D23, Dot.D14, Dot.D05, Dot.D25, Dot.D00, Dot.D20, Dot.D02, Dot.D22}));
        mapping.put(10, Arrays.asList(new Dot[]{Dot.D03, Dot.D23, Dot.D14, Dot.D05, Dot.D25, Dot.D00, Dot.D20, Dot.D11, Dot.D02, Dot.D22}));
        mapping.put(11, Arrays.asList(new Dot[]{Dot.D03, Dot.D23, Dot.D04, Dot.D24, Dot.D05, Dot.D25, Dot.D00, Dot.D20, Dot.D11, Dot.D02, Dot.D22}));
        mapping.put(12, Arrays.asList(new Dot[]{Dot.D03, Dot.D23, Dot.D04, Dot.D24, Dot.D05, Dot.D25, Dot.D00, Dot.D20, Dot.D01, Dot.D21, Dot.D02, Dot.D22}));
    }


    // ******************** Methods *******************************************
    public final int getNumber() {
        return number.get();
    }

    public final void setNumber(final int NUMBER) {
        number.set(NUMBER);
    }

    public final IntegerProperty numberProperty() {
        return number;
    }

    public final Color getColor() {
        return color.get();
    }

    public final void setColor(final Color COLOR) {
        color.set(COLOR);
    }

    public final ObjectProperty<Color> colorProperty() {
        return color;
    }

    public final boolean isAnimated() {
        return animated.get();
    }

    public final void setAnimated(final boolean ANIMATED) {
        animated.set(ANIMATED);
    }

    public final BooleanProperty animatedProperty() {
        return animated;
    }

    public final Map<Integer, List<Dot>> getDotMapping() {
        HashMap<Integer, List<Dot>> dotMapping = new HashMap<Integer, List<Dot>>(12);
        dotMapping.putAll(mapping);
        return dotMapping;
    }

    @Override public void setPrefSize(final double WIDTH, final double HEIGHT) {
        double prefHeight = WIDTH < (HEIGHT * 0.5) ? (WIDTH * 2.0) : HEIGHT;
        double prefWidth = prefHeight * 0.5;
        super.setPrefSize(prefWidth, prefHeight);
    }


    // ******************** Style related *************************************
    @Override protected String getUserAgentStylesheet() {
        return getClass().getResource(getClass().getSimpleName().toLowerCase() + ".css").toExternalForm();
    }
}

