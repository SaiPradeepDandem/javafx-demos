package com.ezest.javafx.common;

import javafx.application.Preloader;
import javafx.stage.Stage;

public class PreloaderCheck extends Preloader{

	@Override
	public void start(Stage arg0) throws Exception {
		System.out.println("hello");
	}

	public static void main(String[] args) {
		Preloader.launch(args);
	}
}
