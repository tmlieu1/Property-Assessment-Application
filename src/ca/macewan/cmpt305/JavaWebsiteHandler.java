package ca.macewan.cmpt305;

import javafx.scene.web.WebEngine;
import javafx.scene.web.WebView;

public class JavaWebsiteHandler {
	//webview
	WebView map = new WebView();
	WebEngine engine = map.getEngine();
	String url = this.getClass().getResource("/ca/macewan/cmpt305/website.html").toExternalForm();
	engine.load(url);
}
