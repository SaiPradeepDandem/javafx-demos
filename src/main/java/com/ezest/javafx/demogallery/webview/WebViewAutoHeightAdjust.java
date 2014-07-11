package com.ezest.javafx.demogallery.webview;

import java.net.URL;

import javafx.application.Application;
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
import javafx.scene.text.Text;
import javafx.scene.web.WebEngine;
import javafx.scene.web.WebView;
import javafx.stage.Screen;
import javafx.stage.Stage;

import com.javafx.experiments.scenicview.ScenicView;

public class WebViewAutoHeightAdjust extends Application {

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
		
		WebView webView = new WebView();
		webView.setMaxWidth(730);
		WebEngine webEngine = webView.getEngine();
		final URL urlHello = getClass().getResource("AutoHeightCheck.html");
		webEngine.load(urlHello.toExternalForm());
		//webEngine.load("www.makemytrip.com");
		//webEngine.loadContent(getReplacedContent("FX"));
		
		
		Text txt = new Text(getMainContent());
		txt.setWrappingWidth(700);
		txt.setStyle("-fx-font-size:13px;-fx-font-family:verdana;");
		
		StackPane txtP = new StackPane();
		txtP.setPadding(new Insets(15));
		txtP.getChildren().add(txt);
		
		StackPane container  = new StackPane();
		container.setAlignment(Pos.TOP_LEFT);
		container.getChildren().addAll(txtP,webView);
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
		String r ="<html><head><style>body{ color:#635564; font-size:13px; font-family:verdana; background:#FFF3A4;}</style></head><body>";
		while(s.indexOf(text)>-1){
			String a = s.substring(0,(s.indexOf(text)+text.length()));
			r = r + a.replace(text, "<span style='background:#74A726;color:#FFFFFF;'>"+text+"</span>");
			s = s.substring((s.indexOf(text)+text.length()));
		}
		if(s.length()>0){
			r = r + s;
		}
		r = r + "</body></html>";
		return r;
	}
	
	private String getMainContent(){
		return "1 The JavaFX Advantage for Swing DevelopersFor more than 10 years, application developers have found Swing to be a highly effective toolkit for building graphical user interfaces (GUIs) and adding interactivity to Java applications. However, some of today's most popular GUI features cannot be easily implemented by using Swing.JavaFX is designed to provide applications with such sophisticated GUI features as smooth animation, web views, audio and video playback, and styles based on Cascading Style Sheets (CSS). These features and others described in the following sections can help application developers to meet the full range of modern requirements. Later chapters in this document explain how to use Swing and JavaFX together.Using FXML FXML is an XML-based markup language that enables developers to create a user interface (UI) in a JavaFX application separately from implementing the application logic. Swing has never offered a declarative approach to building a user interface. The declarative method for creating a UI is particularly suitable for the scene graph, because the scene graph is more transparent in FXML. Using FXML enables developers to more easily maintain complex user interfaces.To learn more about the benefits of using FXML, see the Getting Started with FXML document.JavaFX Scene BuilderTo help developers build the layout of their applications, JavaFX provides a design tool called the JavaFX Scene Builder. You drag and drop UI components to a JavaFX Content pane, and the tool generates the FXML code that can be used in an IDE such as NetBeans or Eclipse.For more information, see Getting Started with JavaFX Scene Builder and the Scene Builder User Guide.CSS SupportCascading style sheets contain style definitions that control the look of UI elements. The usage of CSS in JavaFX applications is similar to the usage of CSS in HTML. With CSS, you can easily customize and develop themes for JavaFX controls and scene graph objects.Using CSS as opposed to setting inline styles enables you to separate the logic of the application from setting its visual appearance. Using CSS also simplifies further maintenance of how your application looks and provides some performance benefits.For more information about CSS, see Skinning JavaFX Applications with CSS and JavaFX CSS Reference Guide.JavaFX Media SupportWith the media support provided by the JavaFX platform, you can leverage your desktop application by adding media functionality such as playback of audio and video files. Media functionality is available on all platforms where JavaFX is supported. For the list of supported media codecs, see Introduction to JavaFX Media.For more details, see Leveraging Applications with Media Features chapter.AnimationAnimation brings dynamics and a modern look to the interface of your applications. Animating objects in a Swing application is possible but is not straightforward. In the Swing rendering model, painting happens on a double buffer. All alterations of object properties and positions with time are rendered on a double buffer. Only when the painting is completed, is the final result actually painted onto the screen. To show time-based alterations of objects requires significant efforts from a developer using Swing. In contrast, JavaFX enables developers to animate graphical objects in their applications more easily because of the scene graph underlying the platform and the particular APIs that are specifically created for that purpose.For more details about animation in JavaFX, see Creating Transitions and Timeline Animation in JavaFX. Be sure to check the Tree animation example.HTML ContentFor a long time, Swing developers have wanted the ability to render HTML content in Java applications. JavaFX brought this feature to life by providing a user interface component that has web view and full browsing functionality.For more details, see Adding HTML Content to JavaFX Applications.1 The JavaFX Advantage for Swing Developers"
				+"1 The JavaFX Advantage for Swing DevelopersFor more than 10 years, application developers have found Swing to be a highly effective toolkit for building graphical user interfaces (GUIs) and adding interactivity to Java applications. However, some of today's most popular GUI features cannot be easily implemented by using Swing.JavaFX is designed to provide applications with such sophisticated GUI features as smooth animation, web views, audio and video playback, and styles based on Cascading Style Sheets (CSS). These features and others described in the following sections can help application developers to meet the full range of modern requirements. Later chapters in this document explain how to use Swing and JavaFX together.Using FXML FXML is an XML-based markup language that enables developers to create a user interface (UI) in a JavaFX application separately from implementing the application logic. Swing has never offered a declarative approach to building a user interface. The declarative method for creating a UI is particularly suitable for the scene graph, because the scene graph is more transparent in FXML. Using FXML enables developers to more easily maintain complex user interfaces.To learn more about the benefits of using FXML, see the Getting Started with FXML document.JavaFX Scene BuilderTo help developers build the layout of their applications, JavaFX provides a design tool called the JavaFX Scene Builder. You drag and drop UI components to a JavaFX Content pane, and the tool generates the FXML code that can be used in an IDE such as NetBeans or Eclipse.For more information, see Getting Started with JavaFX Scene Builder and the Scene Builder User Guide.CSS SupportCascading style sheets contain style definitions that control the look of UI elements. The usage of CSS in JavaFX applications is similar to the usage of CSS in HTML. With CSS, you can easily customize and develop themes for JavaFX controls and scene graph objects.Using CSS as opposed to setting inline styles enables you to separate the logic of the application from setting its visual appearance. Using CSS also simplifies further maintenance of how your application looks and provides some performance benefits.For more information about CSS, see Skinning JavaFX Applications with CSS and JavaFX CSS Reference Guide.JavaFX Media SupportWith the media support provided by the JavaFX platform, you can leverage your desktop application by adding media functionality such as playback of audio and video files. Media functionality is available on all platforms where JavaFX is supported. For the list of supported media codecs, see Introduction to JavaFX Media.For more details, see Leveraging Applications with Media Features chapter.AnimationAnimation brings dynamics and a modern look to the interface of your applications. Animating objects in a Swing application is possible but is not straightforward. In the Swing rendering model, painting happens on a double buffer. All alterations of object properties and positions with time are rendered on a double buffer. Only when the painting is completed, is the final result actually painted onto the screen. To show time-based alterations of objects requires significant efforts from a developer using Swing. In contrast, JavaFX enables developers to animate graphical objects in their applications more easily because of the scene graph underlying the platform and the particular APIs that are specifically created for that purpose.For more details about animation in JavaFX, see Creating Transitions and Timeline Animation in JavaFX. Be sure to check the Tree animation example.HTML ContentFor a long time, Swing developers have wanted the ability to render HTML content in Java applications. JavaFX brought this feature to life by providing a user interface component that has web view and full browsing functionality.For more details, see Adding HTML Content to JavaFX Applications.1 The JavaFX Advantage for Swing Developers";
	}
	
	class WebContainer extends StackPane{
		public WebContainer(){
			super();
		}
	}
}


