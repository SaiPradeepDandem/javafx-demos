package com.ezest.javafx.demogallery.webview;

import javafx.application.Application;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.scene.Scene;
import javafx.scene.web.WebEngine;
import javafx.scene.web.WebView;
import javafx.stage.Stage;
import org.w3c.dom.Document;

/**
* Embeds jQuery in a document loaded into a WebView.
* Uses jQuery to run an animation to hide each link in the document as it is clicked.
*/
public class JQueryWebView extends Application {
  public static final String DEFAULT_JQUERY_MIN_VERSION = "1.7.2";
  public static final String JQUERY_LOCATION = "http://code.jquery.com/jquery-1.7.2.min.js";
  public static void main(String[] args) { launch(args); }
  @Override public void start(Stage primaryStage) {
    final WebView webView = new WebView();
    final WebEngine engine = webView.getEngine();
    engine.load("http://docs.oracle.com/javafx/2/get_started/animation.htm");
    engine.documentProperty().addListener(new ChangeListener<Document>() {
      @Override public void changed(ObservableValue<? extends Document> prop, Document oldDoc, Document newDoc) {
        executejQuery(
          engine,
          "$(\"a\").click(function(event){" +
          " event.preventDefault();" +
          " $(this).hide(\"slow\");" +
          "});"
        );
      }
    });
    primaryStage.setScene(new Scene(webView));
    primaryStage.show();
  }

  /**
* Enables Firebug Lite for debugging a webEngine.
* @param engine the webEngine for which debugging is to be enabled.
*/
  private static void enableFirebug(final WebEngine engine) {
    engine.executeScript("if (!document.getElementById('FirebugLite')){E = document['createElement' + 'NS'] && document.documentElement.namespaceURI;E = E ? document['createElement' + 'NS'](E, 'script') : document['createElement']('script');E['setAttribute']('id', 'FirebugLite');E['setAttribute']('src', 'https://getfirebug.com/' + 'firebug-lite.js' + '#startOpened');E['setAttribute']('FirebugLite', '4');(document['getElementsByTagName']('head')[0] || document['getElementsByTagName']('body')[0]).appendChild(E);E = new Image;E['setAttribute']('src', 'https://getfirebug.com/' + '#startOpened');}");
  }
  
  /**
* Executes a script which may reference jQuery function on a document.
* Checks if the document loaded in a webEngine has a version of jQuery corresponding to
* the minimum required version loaded, and, if not, then loads jQuery into the document
* from the default JQUERY_LOCATION.
* @param engine the webView engine to be used.
* @Param jQueryLocation the location of the jQuery script to be executed.
* @param minVersion the minimum version of jQuery which needs to be included in the document.
* @param script provided javascript script string (which may include use of jQuery functions on the document).
* @return the result of the script execution.
*/
  private static Object executejQuery(final WebEngine engine, String minVersion, String jQueryLocation, String script) {
    return engine.executeScript(
      "(function(window, document, version, callback) { "
        + "var j, d;"
        + "var loaded = false;"
        + "if (!(j = window.jQuery) || version > j.fn.jquery || callback(j, loaded)) {"
        + " var script = document.createElement(\"script\");"
        + " script.type = \"text/javascript\";"
        + " script.src = \"" + jQueryLocation + "\";"
        + " script.onload = script.onreadystatechange = function() {"
        + " if (!loaded && (!(d = this.readyState) || d == \"loaded\" || d == \"complete\")) {"
        + " callback((j = window.jQuery).noConflict(1), loaded = true);"
        + " j(script).remove();"
        + " }"
        + " };"
        + " document.documentElement.childNodes[0].appendChild(script) "
        + "} "
      + "})(window, document, \"" + minVersion + "\", function($, jquery_loaded) {" + script + "});"
    );
  }
  
  private static Object executejQuery(final WebEngine engine, String minVersion, String script) {
    return executejQuery(engine, DEFAULT_JQUERY_MIN_VERSION, JQUERY_LOCATION, script);
  }
  
  private Object executejQuery(final WebEngine engine, String script) {
    return executejQuery(engine, DEFAULT_JQUERY_MIN_VERSION, script);
  }
}