package com.ezest.javafx.demogallery.tableviews;

/**
 * Interface for views on {@link IEditableCell}s.
 * @author <a href="mailto:dennis.wagelaar@healthconnect.be">Dennis Wagelaar</a>
 *
 * @param <S> The {@link EditableCell} row object type.
 */
public interface IEditableCellView<S> {

	/**
	 * Sets <code>cell</code> as the currently active cell.
	 * @param cell the cell to set as active
	 */
	public void setActiveCell(EditableCell<S, ?> cell);
	
}
