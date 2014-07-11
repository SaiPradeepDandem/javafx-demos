package com.ezest.javafx.components.dominoclock;

import com.sun.javafx.scene.control.skin.SkinBase;
import javafx.animation.RotateTransition;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Group;
import javafx.scene.effect.BlurType;
import javafx.scene.effect.InnerShadow;
import javafx.scene.effect.Reflection;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Rectangle;
import javafx.scene.shape.Shape;
import javafx.scene.transform.Rotate;
import javafx.util.Duration;

import java.util.HashMap;
import java.util.Map;


public class DominoSkin extends SkinBase<Domino, DominoBehavior> {
    private static final Duration  FLIP_TIME = Duration.millis(250);
    private Domino                 control;
    private Group                  domino;
    private Group                  dots;
    private Map<Domino.Dot, Shape> dotMap;
    private Reflection             reflection;
    private boolean                isDirty;
    private boolean                initialized;


    // ******************** Constructors **************************************
    public DominoSkin(final Domino CONTROL) {
        super(CONTROL, new DominoBehavior(CONTROL));
        control     = CONTROL;
        domino      = new Group();
        dots        = new Group();
        dotMap      = new HashMap<Domino.Dot, Shape>(12);
        reflection  = new Reflection();
        initialized = false;
        isDirty     = false;
        init();
    }

    private void init() {
        if (control.getPrefWidth() < 0 | control.getPrefHeight() < 0) {
            control.setPrefSize(136, 272);
        }

        control.prefWidthProperty().addListener(new ChangeListener<Number>() {
            @Override
            public void changed(ObservableValue<? extends Number> ov, Number oldValue, Number newValue) {
                dotMap = createDotMap("domino-dot-off");
                updateCharacterAnimated();
                isDirty = true;
            }
        });

        control.prefHeightProperty().addListener(new ChangeListener<Number>() {
            @Override
            public void changed(ObservableValue<? extends Number> ov, Number oldValue, Number newValue) {
                dotMap = createDotMap("domino-dot-off");
                updateCharacterAnimated();
                isDirty = true;
            }
        });

        reflection.setFraction(0.3);
        dots.setCache(true);
        dotMap = createDotMap("domino-dot-off");
        updateColor();

        updateCharacterAnimated();

        // Register listeners
        registerChangeListener(control.numberProperty(), "NUMBER");
        registerChangeListener(control.colorProperty(), "COLOR");

        initialized = true;
        paint();
    }


    // ******************** Methods *******************************************
    public final void paint() {
        if (!initialized) {
            init();
        }
        drawMemory();
        updateCharacterAnimated();
        drawDots();
        getChildren().clear();
        getChildren().addAll(domino, dots);
        setEffect(reflection);
    }

    @Override protected void handleControlPropertyChanged(final String PROPERTY) {
        super.handleControlPropertyChanged(PROPERTY);
        if ("NUMBER".equals(PROPERTY)) {
            if (control.isAnimated()) {
                updateCharacterAnimated();
            } else {
                updateCharacter();
            }
        } else if ("COLOR".equals(PROPERTY)) {
            updateColor();
            if (control.isAnimated()) {
                updateCharacterAnimated();
            } else {
                updateCharacter();
            }
        }
    }

    @Override public void layoutChildren() {
        if (isDirty) {
            paint();
            isDirty = false;
        }
        super.layoutChildren();
    }

    @Override public final Domino getSkinnable() {
        return control;
    }

    @Override public final void dispose() {
        control = null;
    }

    @Override protected double computePrefWidth(final double PREF_WIDTH) {
        double prefWidth = 136;
        if (PREF_WIDTH != -1) {
            prefWidth = Math.max(0, PREF_WIDTH - getInsets().getLeft() - getInsets().getRight());
        }
        return super.computePrefWidth(prefWidth);
    }

    @Override protected double computePrefHeight(final double PREF_HEIGHT) {
        double prefHeight = 272;
        if (PREF_HEIGHT != -1) {
            prefHeight = Math.max(0, PREF_HEIGHT - getInsets().getTop() - getInsets().getBottom());
        }
        return super.computePrefWidth(prefHeight);
    }

    private final String createCssColor(final Color COLOR) {
        final StringBuilder CSS_COLOR = new StringBuilder(19);
        CSS_COLOR.append("rgba(");
        CSS_COLOR.append((int) (COLOR.getRed() * 255));
        CSS_COLOR.append(", ");
        CSS_COLOR.append((int) (COLOR.getGreen() * 255));
        CSS_COLOR.append(", ");
        CSS_COLOR.append((int) (COLOR.getBlue() * 255));
        CSS_COLOR.append(", ");
        CSS_COLOR.append(COLOR.getOpacity());
        CSS_COLOR.append(");");
        return CSS_COLOR.toString();
    }


    // ******************** Drawing related ***********************************
    private final void drawMemory() {
        final double SIZE = control.getPrefWidth() < control.getPrefHeight() ? control.getPrefWidth() : control.getPrefHeight();
        final double WIDTH = control.getPrefWidth();
        final double HEIGHT = control.getPrefHeight();

        final Shape IBOUNDS = new Rectangle(0, 0, WIDTH, HEIGHT);
        IBOUNDS.setOpacity(0.0);

        domino.getChildren().clear();
        domino.getChildren().add(IBOUNDS);

        final Rectangle FRAME = new Rectangle(0.0, 0.0,
                                              WIDTH, HEIGHT);
        FRAME.setArcWidth(0.2647058823529412 * WIDTH);
        FRAME.setArcHeight(0.1323529411764706 * HEIGHT);
        FRAME.getStyleClass().add("domino-frame");

        final InnerShadow FRAME_INNER_SHADOW = new InnerShadow();
        FRAME_INNER_SHADOW.setWidth(0.1 * FRAME.getLayoutBounds().getWidth());
        FRAME_INNER_SHADOW.setHeight(0.1 * FRAME.getLayoutBounds().getHeight());
        FRAME_INNER_SHADOW.setOffsetX(0.0);
        FRAME_INNER_SHADOW.setOffsetY(-0.01 * SIZE);
        FRAME_INNER_SHADOW.setRadius(0.1 * FRAME.getLayoutBounds().getWidth());
        FRAME_INNER_SHADOW.setColor(Color.color(0.8, 0.8, 0.8, 0.2));
        FRAME_INNER_SHADOW.setBlurType(BlurType.GAUSSIAN);
        FRAME_INNER_SHADOW.inputProperty().set(null);
        FRAME.setEffect(FRAME_INNER_SHADOW);

        final Rectangle MAIN = new Rectangle(0.03676470588235294 * WIDTH, 0.01838235294117647 * HEIGHT,
                                             0.9264705882352942 * WIDTH, 0.9632352941176471 * HEIGHT);
        MAIN.setArcWidth(0.23529411764705882 * WIDTH);
        MAIN.setArcHeight(0.11764705882352941 * HEIGHT);
        MAIN.getStyleClass().add("domino-main");

        final InnerShadow MAIN_INNER_SHADOW = new InnerShadow();
        MAIN_INNER_SHADOW.setWidth(0.05 * MAIN.getLayoutBounds().getWidth());
        MAIN_INNER_SHADOW.setHeight(0.05 * MAIN.getLayoutBounds().getHeight());
        MAIN_INNER_SHADOW.setOffsetX(0.0);
        MAIN_INNER_SHADOW.setOffsetY(0.008 * SIZE);
        MAIN_INNER_SHADOW.setRadius(0.05 * MAIN.getLayoutBounds().getWidth());
        MAIN_INNER_SHADOW.setColor(Color.BLACK);
        MAIN_INNER_SHADOW.setBlurType(BlurType.GAUSSIAN);

        final InnerShadow MAIN_INNER_GLOW = new InnerShadow();
        MAIN_INNER_GLOW.setWidth(0.025 * MAIN.getLayoutBounds().getWidth());
        MAIN_INNER_GLOW.setHeight(0.025 * MAIN.getLayoutBounds().getHeight());
        MAIN_INNER_GLOW.setOffsetX(0.0);
        MAIN_INNER_GLOW.setOffsetY(-0.008 * SIZE);
        MAIN_INNER_GLOW.setRadius(0.025 * MAIN.getLayoutBounds().getWidth());
        MAIN_INNER_GLOW.setColor(Color.color(1, 1, 1, 0.5));
        MAIN_INNER_GLOW.setBlurType(BlurType.GAUSSIAN);
        MAIN_INNER_GLOW.inputProperty().set(MAIN_INNER_SHADOW);
        MAIN.setEffect(MAIN_INNER_GLOW);

        final Rectangle SEPARATOR = new Rectangle(0.1323529411764706 * WIDTH, 0.4742647058823529 * HEIGHT,
                                                  0.7647058823529411 * WIDTH, 0.025735294117647058 * HEIGHT);
        SEPARATOR.setArcWidth(0.051470588235294115 * WIDTH);
        SEPARATOR.setArcHeight(0.025735294117647058 * HEIGHT);
        SEPARATOR.getStyleClass().add("domino-separator");

        domino.getChildren().addAll(FRAME, MAIN, SEPARATOR);
        domino.getChildren().addAll(createDotMap("domino-dot-off").values());
    }

    private final void drawDots() {
        final double WIDTH = control.getPrefWidth();
        final double HEIGHT = control.getPrefHeight();

        final Shape IBOUNDS = new Rectangle(0, 0, WIDTH, HEIGHT);
        IBOUNDS.setOpacity(0.0);

        dots.getChildren().clear();
        dots.getChildren().add(IBOUNDS);
        dots.getChildren().addAll(dotMap.values());
    }

    private final void updateColor() {
        for (Shape dot : dotMap.values()) {
            dot.setStyle("-fx-on-color: " + createCssColor(control.getColor()));
        }
    }

    private void updateCharacter() {
        final int NUMBER = control.getNumber();
        for (final Domino.Dot dot : dotMap.keySet()) {
            if (control.getDotMapping().containsKey(NUMBER)) {
                if (control.getDotMapping().get(NUMBER).contains(dot)) {
                    dotMap.get(dot).getStyleClass().clear();
                    dotMap.get(dot).getStyleClass().add("domino-dot-on");
                } else {
                    dotMap.get(dot).getStyleClass().clear();
                    dotMap.get(dot).getStyleClass().add("domino-dot-off");
                }
            } else {
                dotMap.get(dot).getStyleClass().clear();
                dotMap.get(dot).getStyleClass().add("domino-dot-off");
            }
        }
    }

    private void updateCharacterAnimated() {
        final int NUMBER = control.getNumber();
        for (final Domino.Dot dot : dotMap.keySet()) {
            if (control.getDotMapping().containsKey(NUMBER)) {
                if (control.getDotMapping().get(NUMBER).contains(dot)) {
                    if (dotMap.get(dot).getStyleClass().contains("domino-dot-off")) {
                        flipTo180(dot, "domino-dot-on");
                    }
                } else {
                    if (dotMap.get(dot).getStyleClass().contains("domino-dot-on")) {
                        flipTo0(dot, "domino-dot-off");
                    }
                }
            } else {
                if (dotMap.get(dot).getStyleClass().contains("domino-dot-on")) {
                    flipTo0(dot, "domino-dot-off");
                }
            }
        }
    }

    private void flipTo180(final Domino.Dot DOT, final String STYLE) {
        final RotateTransition ROT_0_90 = new RotateTransition(FLIP_TIME.divide(2), dotMap.get(DOT));
        ROT_0_90.setAxis(Rotate.Y_AXIS);
        ROT_0_90.setFromAngle(0);
        ROT_0_90.setToAngle(90);
        ROT_0_90.play();
        ROT_0_90.setOnFinished(new EventHandler<ActionEvent>() {
            @Override public void handle(final ActionEvent EVENT) {
                dotMap.get(DOT).getStyleClass().clear();
                dotMap.get(DOT).getStyleClass().add(STYLE);
                final RotateTransition ROT_90_180 = new RotateTransition(FLIP_TIME.divide(2), dotMap.get(DOT));
                ROT_90_180.setAxis(Rotate.Y_AXIS);
                ROT_90_180.setFromAngle(90);
                ROT_90_180.setToAngle(180);
                ROT_90_180.play();
            }
        });
    }

    private void flipTo0(final Domino.Dot DOT, final String STYLE) {
        final RotateTransition ROT_180_90 = new RotateTransition(FLIP_TIME.divide(2), dotMap.get(DOT));
        ROT_180_90.setAxis(Rotate.Y_AXIS);
        ROT_180_90.setFromAngle(180);
        ROT_180_90.setToAngle(90);
        ROT_180_90.play();
        ROT_180_90.setOnFinished(new EventHandler<ActionEvent>() {
            @Override public void handle(final ActionEvent EVENT) {
                dotMap.get(DOT).getStyleClass().clear();
                dotMap.get(DOT).getStyleClass().add(STYLE);
                final RotateTransition ROT_90_0 = new RotateTransition(FLIP_TIME.divide(2), dotMap.get(DOT));
                ROT_90_0.setAxis(Rotate.Y_AXIS);
                ROT_90_0.setFromAngle(90);
                ROT_90_0.setToAngle(0);
                ROT_90_0.play();
            }
        });
    }

    public final Map<Domino.Dot, Shape> createDotMap(final String STYLE) {
        final Map<Domino.Dot, Shape> DOT_MAP = new HashMap<Domino.Dot, Shape>(12);

        final double WIDTH = control.getPrefWidth();
        final double HEIGHT = control.getPrefHeight();

        Circle D00 = new Circle(0.22058823529411764 * WIDTH, 0.09558823529411764 * HEIGHT, 0.10294117647058823 * WIDTH);
        D00.getStyleClass().add(STYLE);
        DOT_MAP.put(Domino.Dot.D00, D00);

        Circle D01 = new Circle(0.21323529411764705 * WIDTH, 0.23529411764705882 * HEIGHT, 0.10294117647058823 * WIDTH);
        D01.getStyleClass().add(STYLE);
        DOT_MAP.put(Domino.Dot.D01, D01);

        Circle D02 = new Circle(0.21323529411764705 * WIDTH, 0.3786764705882353 * HEIGHT, 0.10294117647058823 * WIDTH);
        D02.getStyleClass().add(STYLE);
        DOT_MAP.put(Domino.Dot.D02, D02);

        Circle D03 = new Circle(0.20588235294117646 * WIDTH, 0.6029411764705882 * HEIGHT, 0.10294117647058823 * WIDTH);
        D03.getStyleClass().add(STYLE);
        DOT_MAP.put(Domino.Dot.D03, D03);

        Circle D04 = new Circle(0.19852941176470587 * WIDTH, 0.7426470588235294 * HEIGHT, 0.10294117647058823 * WIDTH);
        D04.getStyleClass().add(STYLE);
        DOT_MAP.put(Domino.Dot.D04, D04);

        Circle D05 = new Circle(0.19852941176470587 * WIDTH, 0.8860294117647058 * HEIGHT, 0.10294117647058823 * WIDTH);
        D05.getStyleClass().add(STYLE);
        DOT_MAP.put(Domino.Dot.D05, D05);

        Circle D10 = new Circle(0.5147058823529411 * WIDTH, 0.09558823529411764 * HEIGHT, 0.10294117647058823 * WIDTH);
        D10.getStyleClass().add(STYLE);
        DOT_MAP.put(Domino.Dot.D10, D10);

        Circle D11 = new Circle(0.5073529411764706 * WIDTH, 0.23529411764705882 * HEIGHT, 0.10294117647058823 * WIDTH);
        D11.getStyleClass().add(STYLE);
        DOT_MAP.put(Domino.Dot.D11, D11);

        Circle D12 = new Circle(0.5073529411764706 * WIDTH, 0.3786764705882353 * HEIGHT, 0.10294117647058823 * WIDTH);
        D12.getStyleClass().add(STYLE);
        DOT_MAP.put(Domino.Dot.D12, D12);

        Circle D13 = new Circle(0.5 * WIDTH, 0.6029411764705882 * HEIGHT, 0.10294117647058823 * WIDTH);
        D13.getStyleClass().add(STYLE);
        DOT_MAP.put(Domino.Dot.D13, D13);

        Circle D14 = new Circle(0.49264705882352944 * WIDTH, 0.7426470588235294 * HEIGHT, 0.10294117647058823 * WIDTH);
        D14.getStyleClass().add(STYLE);
        DOT_MAP.put(Domino.Dot.D14, D14);

        Circle D15 = new Circle(0.49264705882352944 * WIDTH, 0.8860294117647058 * HEIGHT, 0.10294117647058823 * WIDTH);
        D15.getStyleClass().add(STYLE);
        DOT_MAP.put(Domino.Dot.D15, D15);

        Circle D20 = new Circle(0.8088235294117647 * WIDTH, 0.09558823529411764 * HEIGHT, 0.10294117647058823 * WIDTH);
        D20.getStyleClass().add(STYLE);
        DOT_MAP.put(Domino.Dot.D20, D20);

        Circle D21 = new Circle(0.8014705882352942 * WIDTH, 0.23529411764705882 * HEIGHT, 0.10294117647058823 * WIDTH);
        D21.getStyleClass().add(STYLE);
        DOT_MAP.put(Domino.Dot.D21, D21);

        Circle D22 = new Circle(0.8014705882352942 * WIDTH, 0.3786764705882353 * HEIGHT, 0.10294117647058823 * WIDTH);
        D22.getStyleClass().add(STYLE);
        DOT_MAP.put(Domino.Dot.D22, D22);

        Circle D23 = new Circle(0.7941176470588235 * WIDTH, 0.6029411764705882 * HEIGHT, 0.10294117647058823 * WIDTH);
        D23.getStyleClass().add(STYLE);
        DOT_MAP.put(Domino.Dot.D23, D23);

        Circle D24 = new Circle(0.7867647058823529 * WIDTH, 0.7426470588235294 * HEIGHT, 0.10294117647058823 * WIDTH);
        D24.getStyleClass().add(STYLE);
        DOT_MAP.put(Domino.Dot.D24, D24);

        Circle D25 = new Circle(0.7867647058823529 * WIDTH, 0.8860294117647058 * HEIGHT, 0.10294117647058823 * WIDTH);
        D25.getStyleClass().add(STYLE);
        DOT_MAP.put(Domino.Dot.D25, D25);

        return DOT_MAP;
    }
}

