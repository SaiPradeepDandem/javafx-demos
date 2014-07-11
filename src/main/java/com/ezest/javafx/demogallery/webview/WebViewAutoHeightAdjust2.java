package com.ezest.javafx.demogallery.webview;

import javafx.application.Application;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.concurrent.Worker.State;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Cursor;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.Separator;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.StackPaneBuilder;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.web.WebEngine;
import javafx.scene.web.WebEvent;
import javafx.scene.web.WebView;
import javafx.stage.Screen;
import javafx.stage.Stage;
import netscape.javascript.JSObject;

import com.javafx.experiments.scenicview.ScenicView;

public class WebViewAutoHeightAdjust2 extends Application {
	public static final String JQUERY_URL = WebViewAutoHeightAdjust2.class.getResource("/scripts/jquery-1.7.2.js").toExternalForm();
	public static final String MYJS_URL = WebViewAutoHeightAdjust2.class.getResource("/scripts/myscript.js").toExternalForm();
	Stage stage;
	Scene scene;
	StackPane root;
	public static void main(String[] args) {
		Application.launch(args);
	}
	
	@Override
	public void start(Stage stage) throws Exception {
		this.stage = stage;
		configureScene();
		configureStage();
		
		VBox vb = new VBox();
		vb.setMaxWidth(700);
		StackPane header = StackPaneBuilder.create().style("-fx-background-color:orange;").prefHeight(50).build();
		StackPane footer = StackPaneBuilder.create().style("-fx-background-color:orange;").children(new Label("Footer")).prefHeight(50).build();
		
		final WebView webView = new WebView();
		webView.setMaxWidth(730);
		final WebEngine webEngine = webView.getEngine();
		webEngine.loadContent(getReplacedContent("FX"));
		webEngine.setOnAlert(new EventHandler<WebEvent<String>>() {
			@Override
			public void handle(WebEvent<String> event) {
				double height = Double.parseDouble(event.getData());
				webView.setPrefHeight(height+2);
				webView.setMinHeight(height+2);
				webView.setMaxHeight(height+2);
			}
		});
		webEngine.getLoadWorker().stateProperty().addListener(
                new ChangeListener<State>(){
                     
                    @Override
                    public void changed(ObservableValue<? extends State> ov, State oldState, State newState) {
                        if(newState == State.SUCCEEDED){
                            JSObject window = (JSObject)webEngine.executeScript("window");
                            window.setMember("app", new JavaApplication());
                        }
                    }
                });
         
         
        JSObject window = (JSObject)webEngine.executeScript("window");
        window.setMember("app", new JavaApplication());
       
		StackPane container  = new StackPane();
		container.setAlignment(Pos.TOP_LEFT);
		container.getChildren().addAll(webView);
		vb.getChildren().addAll(header,container, footer);
		
		ScrollPane sp = new ScrollPane();
		sp.setContent(vb);
		root.getChildren().add(sp);
	}

	private void configureStage(){
		stage.setTitle(this.getClass().getSimpleName());
		stage.setX(0);
	    stage.setY(0);
	    stage.setWidth(Screen.getPrimary().getVisualBounds().getWidth());
	    stage.setHeight(Screen.getPrimary().getVisualBounds().getHeight());
	    stage.setScene(this.scene);
	    stage.show();
	}
	
	private void configureScene(){
		root = new StackPane();
		root.setAlignment(Pos.TOP_LEFT);
		root.setPadding(new Insets(15));
		BorderPane bp = new BorderPane();
		bp.setBottom(getBottom());
		bp.setCenter(root);
		bp.autosize();
		this.scene = new Scene(bp, Color.LINEN);
		scene.getStylesheets().add("styles/template.css");
	}

	private Node getBottom() {
		StackPane sp = new StackPane();
		sp.setMinHeight(25);
		sp.setAlignment(Pos.TOP_RIGHT);
		ImageView image = new ImageView(new Image(getClass().getResourceAsStream("/images/mglass.gif")));
		image.setCursor(Cursor.HAND);
		image.setTranslateX(-5);
		image.setTranslateY(3);
		image.setOnMouseClicked(new EventHandler<MouseEvent>() {
			@Override
			public void handle(MouseEvent paramT) {
				ScenicView.show(scene);
			}
		});
		sp.getChildren().addAll(new Separator(),image);
		return sp;
	}

	private String getReplacedContent(String text){
		String s = getMainContent();
		String r ="<html><head><style>body{ color:#635564; font-size:13px; font-family:verdana; background:#FFF3A4; margin:0px;padding:0px;}</style>";
		r = r+"<script src=\""+JQUERY_URL+"\"></script>" ;
		r = r+"<script>"+
						 "$(window).ready(function() {" +
							"reAdjustHeight();"+
						 "}); " +
						 " function reAdjustHeight(){"+
					 	  "alert($(\"#pageBody\").height());" +
						 " }"+
						 " function addMore(){"+
						        "document.getElementById('pageBody').innerHTML = 'AAA<br> '+document.getElementById('pageBody').innerHTML;"+
						        "alert($(\"#pageBody\").height()); "+
						 " }";
	 
		r = r+"</script></head><body><div id='pageBody'>";
		while(s.indexOf(text)>-1){
			String a = s.substring(0,(s.indexOf(text)+text.length()));
			r = r + a.replace(text, "<span style='background:#74A726;color:#FFFFFF;'>"+text+"</span>");
			s = s.substring((s.indexOf(text)+text.length()));
		}
		if(s.length()>0){
			r = r + s;
		}
		r = r + "</div></body></html>";
		return r;
	}
	
	private String getMainContent(){
		String str="";
		for (int i = 0; i < 1; i++) {
			str = str +"<h1>The JavaFX Advantage for Swing Developers</h1><br><a onclick='addMore();'>For</a> more than 10 years, application developers have found Swing to be a highly effective toolkit for building graphical user interfaces (GUIs) and adding interactivity to Java applications. However, some of today's most popular GUI features cannot be easily implemented by using Swing.JavaFX is designed to provide applications with such sophisticated GUI";
		}
		return str;
	}
	
	class WebContainer extends StackPane{
		public WebContainer(){
			super();
		}
	}
	 public class JavaApplication {
	        public void adjustWebViewHgt() {
	            System.out.println("Hi");
	        }
	    }
}
