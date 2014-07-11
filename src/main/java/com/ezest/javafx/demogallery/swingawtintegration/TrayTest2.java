package com.ezest.javafx.demogallery.swingawtintegration;

import java.awt.Frame;
import java.awt.Image;
import java.awt.MenuItem;
import java.awt.PopupMenu;
import java.awt.SystemTray;
import java.awt.Toolkit;
import java.awt.TrayIcon;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.JDialog;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;

public class TrayTest2 extends Application {
	public static void main(String[] args) {
		launch(args);
	}

	@Override
	public void start(final Stage primaryStage) {
		primaryStage.setTitle("Hello World!");
		Button btn = new Button();
		btn.setText("Say 'Hello World'");
		btn.setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent event) {
				System.out.println("Hello World!");
			}
		});

		StackPane root = new StackPane();
		root.getChildren().add(btn);
		primaryStage.setScene(new Scene(root, 300, 250));
		primaryStage.show();

		if (SystemTray.isSupported()) {
			final JDialog  frame = new JDialog();
			frame.setUndecorated(true);
			SystemTray tray = SystemTray.getSystemTray();
			Image image = Toolkit.getDefaultToolkit().getImage(TrayTest2.class.getResource("/images/mglass.gif"));
			final PopupMenu popup = new PopupMenu();
			MenuItem item = new MenuItem("Exit");

			popup.add(item);

			TrayIcon trayIcon = new TrayIcon(image, "Amr_Trial", popup);

			ActionListener listener = new ActionListener() {
				@Override
				public void actionPerformed(java.awt.event.ActionEvent arg0) {
					// TODO Auto-generated method stub
					System.exit(0);
				}
			};

			ActionListener listenerTray = new ActionListener() {
				@Override
				public void actionPerformed(java.awt.event.ActionEvent event) {
					Platform.runLater(new Runnable() {
						@Override
						public void run() {
							//primaryStage.hide();
						}
					});
				}
			};

			trayIcon.addActionListener(listenerTray);
			trayIcon.addMouseListener(new MouseAdapter() {
				@Override
				public void mouseClicked(MouseEvent e) {
					System.out.println(e.getButton());
					Platform.runLater(new Runnable() {
						@Override
						public void run() {
							System.out.println(23);
							primaryStage.show();
						}
					});
					if (e.getButton() == MouseEvent.BUTTON1) {
						frame.add(popup);
						popup.show(frame, e.getXOnScreen(), e.getYOnScreen());
					}
				}
			});
			item.addActionListener(listener);

			try {
				frame.setResizable(false);
				frame.setVisible(true);
				tray.add(trayIcon);
			} catch (Exception e) {
				System.err.println("Can't add to tray");
			}
		} else {
			System.err.println("Tray unavailable");
		}
		//
	}
}
