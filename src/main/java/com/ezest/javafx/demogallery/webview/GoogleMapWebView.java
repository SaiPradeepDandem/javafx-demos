package com.ezest.javafx.demogallery.webview;

import java.net.URL;

import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.HPos;
import javafx.geometry.Insets;
import javafx.geometry.VPos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Region;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.StackPaneBuilder;
import javafx.scene.web.WebEngine;
import javafx.scene.web.WebView;
import javafx.stage.Stage;

public class GoogleMapWebView extends Application {

	private Scene scene;
	MyBrowser myBrowser;

	/**
	 * @param args
	 *            the command line arguments
	 */
	public static void main(String[] args) {
		launch(args);
	}

	@Override
	public void start(Stage primaryStage) {
		primaryStage.setTitle("java-buddy.blogspot.com");

		myBrowser = new MyBrowser();
		scene = new Scene(myBrowser, 640, 480);

		primaryStage.setScene(scene);
		primaryStage.show();
	}

	class MyBrowser extends Region {

		HBox toolbar;

		WebView webView = new WebView();
		WebEngine webEngine = webView.getEngine();

		public MyBrowser() {

			final URL urlHello = getClass().getResource("Googlemap_overlay.html");
			webEngine.load(urlHello.toExternalForm());
			final StackPane sp = StackPaneBuilder.create().visible(false).prefHeight(15).prefWidth(100).style("-fx-border-color:red;-fx-border-width:1px;-fx-background-color:yellow;").build();

			Button show = new Button("Show Andhra");
			show.setOnAction(new EventHandler<ActionEvent>() {
				@Override
				public void handle(ActionEvent arg0) {
					webEngine.executeScript(" showOverlay('andhra', '"+getAndhraCoOrds()+"') ");
					sp.setVisible(true);
				}
			});

			Button clear = new Button("Clear");
			clear.setOnAction(new EventHandler<ActionEvent>() {
				@Override
				public void handle(ActionEvent arg0) {
					webEngine.executeScript("clearOverlay(\"andhra\")");
					sp.setVisible(false);
				}
			});
			
			sp.setOnMouseEntered(new EventHandler<MouseEvent>() {
				@Override
				public void handle(MouseEvent arg0) {
					webEngine.executeScript("highlightOverlay(\"andhra\")");
				}
			});
			
			sp.setOnMouseExited(new EventHandler<MouseEvent>() {
				@Override
				public void handle(MouseEvent arg0) {
					webEngine.executeScript("dehighlightOverlay(\"andhra\")");
				}
			});
			
			toolbar = new HBox();
			toolbar.setPadding(new Insets(10, 10, 10, 10));
			toolbar.setSpacing(10);
			toolbar.setStyle("-fx-background-color: #336699");
			toolbar.getChildren().addAll(show, clear,sp);

			getChildren().add(toolbar);
			getChildren().add(webView);
		}

		@Override
		protected void layoutChildren() {
			double w = getWidth();
			double h = getHeight();
			double toolbarHeight = toolbar.prefHeight(w);
			layoutInArea(webView, 0, 0, w, h - toolbarHeight, 0, HPos.CENTER, VPos.CENTER);
			layoutInArea(toolbar, 0, h - toolbarHeight, w, toolbarHeight, 0, HPos.CENTER, VPos.CENTER);
		}

	}

	private String getAndhraCoOrds() {
		return "{\"19.108839\":\"84.795227\", \"19.010190\":\"84.537048\", \"18.823117\":\"84.377747\", \"18.750310\":\"84.190979\", \"18.812718\":\"83.894348\", \"19.098458\":\"83.641663\", \"18.953051\":\"83.345032\", \"18.765914\":\"83.312073\", \"18.724300\":\"83.108826\", \"18.557740\":\"83.048401\", \"18.359739\":\"83.037415\", \"18.250220\":\"82.669373\", \"17.874203\":\"82.056885\", \"17.832374\":\"81.397705\", \"18.312811\":\"80.738525\", \"18.646245\":\"80.343018\", \"18.958246\":\"79.859619\", \"19.414792\":\"79.94751\", \"19.746024\":\"78.431396\", \"19.186678\":\"77.947998\", \"18.375379\":\"77.618408\", \"17.392579\":\"77.596436\", \"16.678293\":\"77.486572\", \"16.045813\":\"77.530518\", \"15.728814\":\"77.069092\", \"15.029686\":\"77.091064\", \"14.838612\":\"76.761475\", \"14.349548\":\"76.981201\", \"13.774066\":\"77.200928\", \"13.816744\":\"77.926025\", \"13.218556\":\"78.607178\", \"12.640338\":\"78.233643\", \"12.576010\":\"78.607178\", \"12.983148\":\"78.760986\", \"12.876070\":\"79.244385\", \"13.239945\":\"79.859619\", \"13.624633\":\"80.101318\", \"14.338904\":\"80.180969\", \"14.891705\":\"80.082092\", \"15.220589\":\"80.087585\", \"15.506619\":\"80.25238\", \"15.786968\":\"80.400696\", \"15.850389\":\"80.653381\", \"15.712951\":\"80.829163\", \"15.749963\":\"81.01593\", \"15.977173\":\"81.169739\", \"16.251594\":\"81.274109\", \"16.341226\":\"81.526794\", \"16.309596\":\"81.77948\", \"16.504566\":\"82.229919\", \"16.772987\":\"82.411194\", \"16.846605\":\"82.235413\", \"17.104043\":\"82.372742\", \"17.334908\":\"82.746277\", \"17.649257\":\"83.301086\", \"17.926476\":\"83.531799\", \"18.166730\":\"83.866882\", \"18.417079\":\"84.267883\", \"18.677471\":\"84.509583\", \"18.854310\":\"84.624939\", \"19.046541\":\"84.762268\"}";
	}
}
