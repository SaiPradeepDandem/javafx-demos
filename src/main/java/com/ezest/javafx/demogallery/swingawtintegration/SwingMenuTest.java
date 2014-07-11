package com.ezest.javafx.demogallery.swingawtintegration;

import javafx.application.Platform;
import javafx.embed.swing.JFXPanel;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.Text;

import javax.swing.JFrame;
import javax.swing.SwingUtilities;
import com.sun.awt.*;

public class SwingMenuTest {

    private static void initAndShowGUI() {
        // This method is invoked on the EDT thread
        JFrame frame = new JFrame("Swing and JavaFX");
        final JFXPanel fxPanel = new JFXPanel();
        frame.add(fxPanel);
        frame.setSize(300, 200);
        frame.setUndecorated(true);
       // frame.setOpacity(0.1f);
        frame.setVisible(true);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        Platform.runLater(new Runnable() {
            @Override
            public void run() {
            initFX(fxPanel);
            }
       });
    }

    private static void initFX(JFXPanel fxPanel) {
        // This method is invoked on the JavaFX thread
        Scene scene = createScene();
        fxPanel.setScene(scene);
    }

    private static Scene createScene() {
        Group  root  =  new  Group();
        Scene  scene  =  new  Scene(root, Color.TRANSPARENT);
        Text  text  =  new  Text();
        text.setX(40);
        text.setY(100);
        text.setFont(new Font(25));
        text.setText("Welcome JavaFX!");
        root.getChildren().add(text);
        return (scene);
    }
    
    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
            initAndShowGUI();
            //configure();
            }
        });
    }
    
    /*public static void configure(){
    	com.apple.eawt.Application application = com.apple.eawt.Application.getApplication();
		application.setEnabledPreferencesMenu(true);
		application.setEnabledAboutMenu(true);
		application.addApplicationListener(new com.apple.eawt.ApplicationAdapter() {
		   @Override
		   public void handleAbout(com.apple.eawt.ApplicationEvent e) {
		       // code goes here to show your About Dialog
		       e.setHandled(true);
		   }

		   @Override
		   public void handleOpenApplication(com.apple.eawt.ApplicationEvent e) {
		       // your code here
		   }

		   @Override
		   public void handleOpenFile(com.apple.eawt.ApplicationEvent e) {
		   }

		   @Override
		   public void handlePreferences(com.apple.eawt.ApplicationEvent e) {
		   }

		   @Override
		   public void handlePrintFile(com.apple.eawt.ApplicationEvent e) {
		   }

		   @Override
		   public void handleQuit(com.apple.eawt.ApplicationEvent e) {
		       // you code to quit your app
		   }
		});
		
				ImageIcon icon=new ImageIcon();
				application.setDockIconImage(icon.getImage());
				application.setDockMenu(new PopupMenu());
				MenuItem item = new MenuItem("Post it icon");
				item.addActionListener(new ActionListener() {
					
					@Override
					public void actionPerformed(ActionEvent e) {
						System.out.println("Hi.li");
					}
				});
				application.getDockMenu().add(item);
    }*/
}

