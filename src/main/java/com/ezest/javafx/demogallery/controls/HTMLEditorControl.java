package com.ezest.javafx.demogallery.controls;

import javafx.beans.property.SimpleObjectProperty;
import javafx.event.EventHandler;
import javafx.scene.Node;
import javafx.scene.control.ToggleButton;
import javafx.scene.control.ToolBar;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyCodeCombination;
import javafx.scene.input.KeyCombination;
import javafx.scene.input.KeyEvent;
import javafx.scene.web.HTMLEditor;

/**
 * Custom editor control to override the styles for <p> tag to avoid the extra spacing.
 * @author Sai.Dandem
 */
public class HTMLEditorControl  extends HTMLEditor{
	
	public final static String P_CLASS = "p {margin:0 0 0 0}";
	public final String INITIAL_TEXT = "<html><head><style type=\"text/css\">p {margin:0 0 0 0}</style></head><body contenteditable=\"true\"></body></html>";
	public final static String DEFAULT_STYLE_TEXT = "<style type=\"text/css\">"+P_CLASS+"</style>";
	
	public HTMLEditorControl(){
		super();
		setHtmlText(INITIAL_TEXT);
		addKeyListeners();
	}

	/**
	 * Add the key combination listeners for firing the "Italic" and "Underline" button.
	 * (Work around for the issue)
	 */
	private void addKeyListeners() {
		final KeyCombination icombination=new KeyCodeCombination(KeyCode.I,KeyCombination.CONTROL_DOWN);
		final KeyCombination ucombination=new KeyCodeCombination(KeyCode.U,KeyCombination.CONTROL_DOWN);
		
		final SimpleObjectProperty<ToggleButton> italicBtn = new SimpleObjectProperty<ToggleButton>();
		final SimpleObjectProperty<ToggleButton> underlineBtn = new SimpleObjectProperty<ToggleButton>();
		
		ToolBar bar = (ToolBar)this.lookup(".bottom-toolbar");
		for (Node node : bar.getItems()) {
			if(node instanceof ToggleButton && node.getUserData().equals("italic")){
				italicBtn.set ( (ToggleButton)node);
			}else if(node instanceof ToggleButton && node.getUserData().equals("underline")){
				underlineBtn.set ( (ToggleButton)node);
			}
		}
		
		this.addEventHandler(KeyEvent.KEY_RELEASED, new EventHandler<KeyEvent>() {
			@Override
			public void handle(KeyEvent event) {
				if(icombination.match(event)){
					italicBtn.get().fire();
				}else if(ucombination.match(event)){
					underlineBtn.get().fire();
				}
			}
		});
	}

	@Override
	public void setHtmlText(String paramString) {
		if(!hasLength(paramString)){
			super.setHtmlText(INITIAL_TEXT);
		}else{
			paramString = HTMLEditorControl.addParagraphStyles(paramString);
			super.setHtmlText(paramString);
		}
	}
	
	public boolean hasLength(CharSequence str) {
		return (str != null && str.length() > 0);
	}
	
	public static String addParagraphStyles(String htmlString){
		if(htmlString.indexOf(P_CLASS)<0){
			if(htmlString.indexOf("</style>")>-1){
				htmlString = htmlString.replaceAll("</style>", P_CLASS+"</style>");
				
			}else if(htmlString.indexOf("</head>")>-1){
				htmlString = htmlString.replaceAll("</head>", DEFAULT_STYLE_TEXT+"</head>");
			
			}else if (htmlString.indexOf("<html>")>-1){
				htmlString = htmlString.replaceAll("<html>", "<html><head>"+DEFAULT_STYLE_TEXT+"</head>");
			}
		}
		return htmlString;
	}
}

