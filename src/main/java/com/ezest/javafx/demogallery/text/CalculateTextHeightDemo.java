package com.ezest.javafx.demogallery.text;

import com.sun.javafx.tk.Toolkit;

import javafx.scene.text.Font;
import javafx.scene.text.Text;

public class CalculateTextHeightDemo {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}

	/**
	 * Wrapper methods to provide a Font.getDefault
	 * @param content
	 * @param wrappingWidth
	 * @return
	 */
	public static double calculateHeight(String content, int wrappingWidth) {
		return calculateHeight(content, wrappingWidth, Font.getDefault());
	}

	/**
	 * 
	 * Calculates the exact height of the text.<br>
	 * For use in eg. a textarea without scrollbars.<br>
	 * <br>
	 * Shows at least 2 lines.
	 * @param content
	 * @param wrappingWidth
	 * @param font
	 * @return
	 */
	public static double calculateHeight(String content, int wrappingWidth, Font font) {
		Text helper = new Text();
		helper.setText(content);
		helper.setWrappingWidth(wrappingWidth);
		helper.setFont(font);
		return Toolkit.getToolkit().getFontLoader().getFontMetrics(font).getLineHeight() * 2 + helper.getLayoutBounds().getHeight();
	}

}
