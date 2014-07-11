package com.ezest.javafx.sscce;

import javafx.application.Application;
import javafx.beans.binding.NumberBinding;
import javafx.beans.binding.When;
import javafx.beans.property.DoubleProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Group;
import javafx.scene.GroupBuilder;
import javafx.scene.Scene;
import javafx.scene.SceneBuilder;
import javafx.scene.control.Label;
import javafx.scene.control.LabelBuilder;
import javafx.scene.control.TextField;
import javafx.scene.control.TextFieldBuilder;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBoxBuilder;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.CircleBuilder;
import javafx.scene.shape.Line;
import javafx.scene.shape.LineBuilder;
import javafx.scene.shape.RectangleBuilder;
import javafx.stage.Stage;

public class TriangleArea extends Application {
  private int clickcount = 0;
  private Circle c[] = new Circle[3];
  private Line l[] = new Line[3];
  private Line a_x_l[] = new Line[12];
  private Line a_y_l[] = new Line[12];
  private TextField tx[] = new TextField[3];
  private TextField ty[] = new TextField[3];
  private Label area_Label = new Label();

  private DoubleProperty org_x[] = new SimpleDoubleProperty[3];
  private DoubleProperty org_y[]= new SimpleDoubleProperty[3];
  private NumberBinding map_x[] = new NumberBinding[3];
  private NumberBinding map_y[] = new NumberBinding[3];
  private NumberBinding area;

  {
    for(int i = 0; i < 3; i++ ) {
      // create Circles represented the vertices of triangle
      // and Lines represented the sides of triangle
      c[i] = CircleBuilder.create()
                          .radius(3.0)
                          .fill(Color.RED)
                          .build();
      l[i] = LineBuilder.create()
                          .strokeWidth(2.0)
                          .stroke(Color.RED)
                          .build();

      // create TextFields for print out the x-coordinates
      // and y-coordinates of the vertices
      tx[i] = TextFieldBuilder.create()
                              .prefColumnCount(2)
                              .build();
      ty[i] = TextFieldBuilder.create()
                              .prefColumnCount(2)
                              .build();

      // Properties for the mouse clicked position
      // Origin position by pixel from top-left of the scene
      org_x[i] = new SimpleDoubleProperty(10);
      org_y[i] = new SimpleDoubleProperty(490);

      // the coordinates of its own Cartesian plane
      // bound by the Properties of mouse clicked position
      map_x[i] = org_x[i].subtract(10)
                         .multiply(2.54)
                         .divide(96.0);
      map_y[i] = org_y[i].negate()
                         .add(490)
                         .multiply(2.54)
                         .divide(96.0);
    }

    // lines for the grid
    for (int i = 0; i < 12; i++) {
      double delta_y = 490.0 - (i+1) * (96.0 / 2.54);
      double delta_x = 10.0 + (i+1) * (96.0 / 2.54);
      a_x_l[i] = new Line(5, delta_y, 495, delta_y);
      a_x_l[i].setStrokeWidth((i+1)%5==0 ? 0.3 : 0.1);
      a_y_l[i] = new Line(delta_x, 5, delta_x, 495);
      a_y_l[i].setStrokeWidth((i+1)%5==0 ? 0.3 : 0.1);
    }
  }

  public TriangleArea() {
    initialize();

    for(int i = 0; i < 3; i++) {
      int j = (i == 2) ? 0 : i+1;

      // Start and end positions of Line is bound by
      // the vertices(the center position of Circles)
      l[i].startXProperty().bind(c[i].centerXProperty());
      l[i].startYProperty().bind(c[i].centerYProperty());
      l[i].endXProperty().bind(c[j].centerXProperty());
      l[i].endYProperty().bind(c[j].centerYProperty());

      // center position of Circle is bound by
      // the mouse clicked position
      c[i].centerXProperty().bind(org_x[i]);
      c[i].centerYProperty().bind(org_y[i]);

      // Text of TextField is bound by the string form
      // presenting the vertices(the center of Circles)
      tx[i].textProperty().bind(map_x[i].asString("%2.1f"));
      ty[i].textProperty().bind(map_y[i].asString("%2.1f"));
    }

    // the area of triangle is computed by the bound values
    // by Circle positions
    NumberBinding tmp_area =
             map_x[0].multiply(map_y[1])
        .add(map_x[1].multiply(map_y[2]))
        .add(map_x[2].multiply(map_y[0]))
        .subtract(map_x[0].multiply(map_y[2]))
        .subtract(map_x[1].multiply(map_y[0]))
        .subtract(map_x[2].multiply(map_y[1]))
        .divide(2.0);

    // the code for computing the absolute value using
    // Class When and then() and otherwise() methods.
    area = new When(tmp_area.lessThan(0))
              .then(tmp_area.negate())
              .otherwise(tmp_area);

    // Text of Label presenting the area of triangle is
    // bound by the string form the computing the area
    area_Label.textProperty().bind(
      new SimpleStringProperty("Area = ")
              .concat(area.asString("%2.1f")));
  }

  private void initialize() {
    clickcount = 0;
  }

  @Override public void start(Stage stage) {
    final Group root;
    Scene scene = SceneBuilder.create()
      .width(500).height(500)
      .fill(Color.WHITE)
      .root(root = GroupBuilder.create()
        .children(RectangleBuilder.create()
          .layoutX(5).layoutY(5)
          .width(490).height(490)
          .fill(Color.LIGHTSKYBLUE)
          .onMouseClicked(new EventHandler<MouseEvent>() {
            // Eventhandler for mouse click event
            @Override public void handle(MouseEvent e) {
              if (clickcount > 2) { initialize(); }
              for (int j = clickcount; j < 3; j++) {
                org_x[j].set(e.getSceneX());
                org_y[j].set(e.getSceneY());
              }
              clickcount++;
            }
          }).build(),
        // line for x-axis through the Origin
        LineBuilder.create()
          .startX(5).startY(490)
          .endX(495).endY(490)
          .build(),
        // Line for y-axis through the Origin
        LineBuilder.create()
          .startX(10).startY(5)
          .endX(10).endY(495)
          .build(),
        // grid horizontal lines which interval is of 1cm
        a_x_l[0], a_x_l[1], a_x_l[2], a_x_l[3],
        a_x_l[4], a_x_l[5], a_x_l[6], a_x_l[7],
        a_x_l[8], a_x_l[9], a_x_l[10], a_x_l[11],
        // grid vertical lines which interval is of 1cm
        a_y_l[0], a_y_l[1], a_y_l[2], a_y_l[3],
        a_y_l[4], a_y_l[5], a_y_l[6], a_y_l[7],
        a_y_l[8], a_y_l[9], a_y_l[10], a_y_l[11],
        // Sides
        l[0], l[1], l[2],
        // Vertices
        c[0], c[1], c[2],
        // the printing space of the coordinates and
        // the area of triangle
        HBoxBuilder.create()
          .layoutX(10)
          .padding(new Insets(10, 10, 10, 10)).spacing(3)
          .alignment(Pos.BOTTOM_CENTER)
          .children(
            LabelBuilder.create().text("A(").build(),
            tx[0],
            LabelBuilder.create().text(",").build(),
            ty[0],
            LabelBuilder.create().text("),").build(),
            LabelBuilder.create().text("B(").build(),
            tx[1],
             LabelBuilder.create().text(",").build(),
            ty[1],
            LabelBuilder.create().text("),").build(),
            LabelBuilder.create().text("C(").build(),
            tx[2],
            LabelBuilder.create().text(",").build(),
            ty[2],
            LabelBuilder.create().text(") ⇒ ").build(),
            area_Label
          ).build()
      ).build()
    ).build();

    stage.setTitle("Triangle Area");
    stage.setScene(scene);
    stage.show();
  }

  public static void main(String[] args) {
    launch(args);
  }
}
