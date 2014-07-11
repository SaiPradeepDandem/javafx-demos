package com.ezest.javafx.uicontrols;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.geometry.Orientation;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.ScrollBar;
import javafx.scene.control.ScrollPane;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.TilePane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;

public class ScrollBarElement {

	public static Node getSimpleBar(){
		ScrollBar sb = new ScrollBar();
		sb.setMin(0);
		sb.setMax(100);
		sb.setValue(20);
		sb.setOrientation(Orientation.VERTICAL);
		return sb;
	}
	
	public static Node getAppScrollBar(Scene scene,final TilePane tp){
		ScrollBar sc = new ScrollBar();
		sc.setLayoutX(scene.getWidth()-sc.getWidth());
        sc.setMin(0);
        sc.setOrientation(Orientation.VERTICAL);
        sc.setPrefHeight(180);
        sc.setMax(550);
        
        sc.valueProperty().addListener(new ChangeListener<Number>() {
            public void changed(ObservableValue<? extends Number> ov,
                Number old_val, Number new_val) {
            		tp.setLayoutY(-new_val.doubleValue());
            }
        });
        
        sc.setStyle(
                "-fx-base: lemonchiffon;"
                + "-fx-border-color: darkgreen; "
                + "-fx-border-insets: -5; "
                + "-fx-border-radius: 10;"
                + "-fx-border-style: dotted;"
                + "-fx-border-width: 2;"
                + "-fx-background-color: #b6e7c9;"
                + "-fx-background-radius: 10;"
           );
        return sc;
	}
	
	public static Node getScrollPane(){
		VBox vb = new VBox();
		vb.setSpacing(10);
		
		ScrollPane sp = new ScrollPane(){
			/*@Override
			protected void layoutChildren() {
				// TODO Auto-generated method stub
				super.layoutChildren();
				((ImageView)getContent()).get
				System.out.println();
			}*/
		};
		Image image = new Image(ScrollBarElement.class.getResourceAsStream("/images/panda.jpg"));
		ImageView view = new ImageView(image);
		//view.setScaleX(-2.5);
		//view.setScaleY(-2.5);
		
		sp.setContent(view);
		sp.setPrefSize(150, 150);
		sp.setVvalue(30);
		
		final Text x = new Text("0");
		final Text y = new Text("0");
		HBox hb = new HBox();
		hb.getChildren().addAll(x,new Text(" , "),y);
		
		vb.getChildren().addAll(sp,hb);
		
		sp.vvalueProperty().addListener(new ChangeListener<Number>() {
			@Override
			public void changed(ObservableValue arg0, Number arg1, Number arg2) {
				y.setText(arg2.doubleValue()+"");
			}
		});
		
		sp.hvalueProperty().addListener(new ChangeListener<Number>() {
			@Override
			public void changed(ObservableValue arg0, Number arg1, Number arg2) {
				x.setText(arg2.doubleValue()+"");
			}
		});
		
		return vb;
	}
}
