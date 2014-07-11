package com.ezest.javafx.demogallery.swingawtintegration;

import java.awt.event.*;
import javax.swing.*;
 
public class SwingTrayTest {
 
	public static void main(String[] args) {
		Icon icon = new ImageIcon(SwingTrayTest.class.getResource("alert_obj.gif"));
		
		JPopupMenu menu = new JPopupMenu();
		menu.add(new JMenuItem("Test 1"));
		
		menu.addSeparator();
		
		JMenu subMenu = new JMenu("Test 2");
		subMenu.add(new JMenuItem("Test 3"));
		
		menu.add(subMenu);
		
		menu.addSeparator();
		
		JMenuItem exit = new JMenuItem("Exit");
		
		exit.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				System.exit(0);
			}
		});
		
		menu.add(exit);
		
		}
}
