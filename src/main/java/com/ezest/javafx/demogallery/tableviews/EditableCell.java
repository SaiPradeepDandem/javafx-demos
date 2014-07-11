package com.ezest.javafx.demogallery.tableviews;

import java.util.logging.Logger;

import javafx.collections.ObservableList;
import javafx.event.EventHandler;
import javafx.scene.Node;
import javafx.scene.control.TableCell;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.util.Callback;

import com.sun.javafx.scene.control.skin.TableRowSkin;

/**
 * An editable JavaFX {@link TableCell} with support for tabbing during editing.
 * Most of the built-in JavaFX event handling breaks when modifying the cell behaviour,
 * so this class defines its own events.
 * @see <a href="http://docs.oracle.com/javafx/2.0/ui_controls/table-view.htm#CJAGDAHE"
 * >http://docs.oracle.com/javafx/2.0/ui_controls/table-view.htm#CJAGDAHE</a>
 * @author <a href="mailto:dennis.wagelaar@healthconnect.be">Dennis Wagelaar</a>
 *
 * @param <S> The type of the row objects contained within the TableView items list.
 * @param <T> The type of the cell items.
 */
public abstract class EditableCell<S, T> extends TableCell<S, T> implements IEditableCell {

	protected static final Logger LOG = Logger.getLogger(EditableCell.class.getName());

	protected final IEditableCellView<S> view;

	private int nextEditTabIndex = -1;
	private int prevEditTabIndex = -1;
	private Callback<T, String> validateCallback;
	private boolean enterCommit;
	private EventHandler<MouseEvent> mouseClickHandler;

	/**
	 * Creates a new {@link EditableCell}.
	 * @param view the view to notify on cell changes
	 */
	public EditableCell(final IEditableCellView<S> view) {
		if (view == null) {
			throw new IllegalArgumentException("view may not be null");
		}
		this.view = view;
		setEditable(true);
		/* Enabling to get the edit cell on single mouse click over the cell. */
		setOnMouseClicked(getMouseClickHandler());
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void startEdit() {
		super.startEdit();
		LOG.fine("startEdit(): setting active cell to " + this);
		view.setActiveCell(this);
	}

	/**
	 * {@inheritDoc}
	 * 
	 * Disabled for {@link EditableCell} and its subclasses.
	 */
	@Override
	public final void cancelEdit() {
		// disabled
	}

	/**
	 * Transitions from an editing state into a non-editing state, without saving any user input.
	 */
	public void customCancelEdit() {
		super.cancelEdit();
		LOG.fine("customCancelEdit(): setting active cell to null");
		view.setActiveCell(null);
		getTableView().requestFocus();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void commitEdit(final T newValue) {
		if (!getItem().equals(newValue) || isEnterCommit()) { // value changed or ENTER pressed
			final Callback<T, String> validate = getValidateCallback();
			final String validateResult = validate == null ? null : validate.call(newValue);
			setInvalidStyle(validateResult);
			super.commitEdit(newValue);
		}
	}

	/**
	 * Call this method to transition from an editing state into a non-editing
	 * state, and in the process saving any pending user input.
	 * The cell value is retrieved from the JavaFX control that is responsible
	 * for editing this cell's value.
	 */
	public abstract void commitEdit();

	/**
	 * Returns the next editable cell index for when TAB is pressed.
	 * @return the nextEditTabIndex
	 */
	public int getNextEditTabIndex() {
		return nextEditTabIndex;
	}

	/**
	 * Sets the next editable cell index for when TAB is pressed.
	 * @param nextEditTabIndex the nextEditTabIndex to set
	 */
	public void setNextEditTabIndex(int nextEditTabIndex) {
		this.nextEditTabIndex = nextEditTabIndex;
	}

	/**
	 * Returns the previous editable cell index for when TAB is pressed.
	 * @return the prevEditTabIndex
	 */
	public int getPrevEditTabIndex() {
		return prevEditTabIndex;
	}

	/**
	 * Sets the previous editable cell index for when TAB is pressed.
	 * @param prevEditTabIndex the prevEditTabIndex to set
	 */
	public void setPrevEditTabIndex(int prevEditTabIndex) {
		this.prevEditTabIndex = prevEditTabIndex;
	}

	/**
	 * Returns the validation callback function. This function takes a value as an
	 * argument, and returns either an error message or <code>null</code>.
	 * @return the validateCallback
	 */
	public Callback<T, String> getValidateCallback() {
		return validateCallback;
	}

	/**
	 * Sets the validation callback function. This function takes a value as an
	 * argument, and returns either an error message or <code>null</code>.
	 * @param validateCallback the validateCallback to set
	 */
	public void setValidateCallback(Callback<T, String> validateCallback) {
		this.validateCallback = validateCallback;
	}

	/**
	 * Returns <code>true</code> when the last {@link #commitEdit(Object)} was
	 * done via the ENTER key.
	 * @return <code>true</code> when the last {@link #commitEdit(Object)} was
	 * done via the ENTER key.
	 */
	public boolean isEnterCommit() {
		return enterCommit;
	}

	/**
	 * Sets whether the last {@link #commitEdit(Object)} was
	 * done via the ENTER key.
	 * @param enterCommit the enterCommit to set
	 */
	protected void setEnterCommit(boolean enterCommit) {
		this.enterCommit = enterCommit;
	}

	/**
	 * Method to retrieve all the cells of the row for the given row cell.
	 * 
	 * @param cell
	 *            - Table cell
	 * @return List of cells of the row.
	 */
	protected ObservableList<Node> getCurrentRow() {
		final TableRowSkin<?> rowSkin = (TableRowSkin<?>) getParent();
		return rowSkin.getChildren();
	}

	/**
	 * Handles ENTER keypresses.
	 * @param event the key event
	 */
	protected void onEnterPressed(final KeyEvent event) {
		setEnterCommit(true);
		commitEdit();
		setEnterCommit(false);
	}

	/**
	 * Handles ESC keypresses.
	 * @param event the key event
	 */
	protected void onEscPressed(final KeyEvent event) {
		customCancelEdit();
	}

	/**
	 * Handles TAB keypresses.
	 * @param event the key event
	 */
	protected void onTabPressed(final KeyEvent event) {
		final ObservableList<Node> row = getCurrentRow();
		Node newNode = null;
		if (event.isShiftDown()) {
			final int prevIndex = getPrevEditTabIndex();
			if (prevIndex > -1) {
				newNode = row.get(prevIndex);
			}
		} else {
			final int nextIndex = getNextEditTabIndex();
			if (nextIndex > -1) {
				newNode = row.get(nextIndex);
			}
		}
		if (newNode instanceof EditableCell<?, ?>) {
			@SuppressWarnings("unchecked")
			final EditableCell<S, ?> newCell = (EditableCell<S, ?>)newNode;
			commitEdit();
			newCell.startEdit();
		}
	}

	/**
	 * Returns the standard mouse click handler for editable cells.
	 * @return the mouseClickHandler
	 */
	protected EventHandler<MouseEvent> getMouseClickHandler() {
		if (mouseClickHandler == null) {
			mouseClickHandler = new EventHandler<MouseEvent>() {
				@Override
				public void handle(MouseEvent event) {
					LOG.fine("mouse clicked on editable cell");
					try {
						startEdit();
					} catch (NullPointerException e) {
						// errors on null TablePos can be ignored - we don't use them
					}
				}
			};
		}
		return mouseClickHandler;
	}

	/**
	 * Returns the string value for {@link #getItem()}.
	 * @return the string value
	 */
	protected String getString() {
	    return getItem() == null ? "" : getItem().toString();
	}

	/**
	 * Sets the style of this cell to invalid if <code>message</code> is not <code>null</code>.
	 * @param message the error message, or null
	 */
	protected abstract void setInvalidStyle(String message);

	/**
	 * {@inheritDoc}
	 */
	@Override
	protected String getUserAgentStylesheet() {
		return "be/healthconnect/javafx/controls/controls.css";
	}

}