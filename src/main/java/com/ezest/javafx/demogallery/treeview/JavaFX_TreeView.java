package com.ezest.javafx.demogallery.treeview;

import java.util.HashMap;
import java.util.Map;

import javafx.application.Application;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TreeItem;
import javafx.scene.control.TreeView;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
 
public class JavaFX_TreeView extends Application {
 
    public static void main(String[] args) {
        Application.launch(args);
    }
 
    public void start(final Stage stage) throws Exception {
 
        VBox root = new VBox();
 
        MyTreeData group1 = new MyTreeData("Group1");
        final MyTreeData group2 = new MyTreeData("Group2");
        MyTreeData subGroup1 = new MyTreeData("SubItem1");
        MyTreeData subGroup2 = new MyTreeData("SubItem3");
 
        group2.getChildren().addAll(subGroup1, subGroup2);
 
        ObservableList<MyTreeData> treeData = FXCollections.observableArrayList(group1, group2);
 
        TreeViewWithItems<MyTreeData> treeView = new TreeViewWithItems<MyTreeData>(new TreeItem<MyTreeData>());
        treeView.setShowRoot(false);
        treeView.setItems(treeData);
        treeView.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<TreeItem>() {
			@Override
			public void changed( ObservableValue<? extends TreeItem> paramObservableValue, TreeItem paramT1, TreeItem selectedItem) {
				
				System.out.println(selectedItem);
				
			}
		});
 
        Button button1 = new Button("Add");
        button1.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                group2.getChildren().add(new MyTreeData("SubItem2"));
 
            }
        });
        Button button2 = new Button("Sort");
        button2.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                FXCollections.sort(group2.getChildren());
            }
        });
        root.getChildren().addAll(treeView, button1, button2);
 
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }
 
    private class MyTreeData implements HierarchyData<MyTreeData>, Comparable<MyTreeData> {
        private String data;
 
        public MyTreeData(String data) {
            this.data = data;
        }
 
        private ObservableList<MyTreeData> children = FXCollections.observableArrayList();
 
        @Override
        public ObservableList<MyTreeData> getChildren() {
            return children;
        }
 
        @Override
        public String toString() {
            return data;
        }
 
        @Override
        public int compareTo(MyTreeData o) {
            return data.compareTo(o.data);
        }
    }
}

/**
 * Used to mark an object as hierarchical data.
 * This object can then be used as data source for an hierarchical control, like the {@link javafx.scene.control.TreeView}.
 *
 * @author Christian Schudt
 */
interface HierarchyData<T extends HierarchyData> {
    /**
     * The children collection, which represents the recursive nature of the hierarchy.
     * Each child is again a {@link HierarchyData}.
     *
     * @return A list of children.
     */
    ObservableList<T> getChildren();
}


/**
 * @author Christian Schudt
 */
class TreeViewWithItems<T extends HierarchyData<T>> extends TreeView<T> {
 
    public TreeViewWithItems(TreeItem<T> root) {
        super(root);
    }
 
    /**
     * Sets items for the tree.
     *
     * @param list The list.
     */
    public void setItems(ObservableList<? extends T> list) {
 
        for (T value : list) {
            getRoot().getChildren().add(addRecursively(value));
        }
 
        list.addListener(getListChangeListener(getRoot()));
    }
 
 
    private Map<T, ListChangeListener<T>> map = new HashMap<T, ListChangeListener<T>>();
 
    /**
     * Gets a {@link javafx.collections.ListChangeListener} for a list of RosterItems. It listens to changes on the underlying list and updates the UI accordingly.
     *
     * @param treeItem The tree item which holds the list.
     * @return The listener.
     */
    private ListChangeListener<T> getListChangeListener(final TreeItem<T> treeItem) {
        return new ListChangeListener<T>() {
            @Override
            public void onChanged(final Change<? extends T> change) {
                while (change.next()) {
                    if (change.wasRemoved()) {
                        for (T removed : change.getRemoved()) {
 
                            for (TreeItem<T> item : treeItem.getChildren()) {
                                if (item.getValue().equals(removed)) {
                                    treeItem.getChildren().remove(item);
                                    break;
                                }
                            }
                            removeRecursively(removed);
                        }
                    }
                    // If items have been added
                    if (change.wasAdded()) {
                        ObservableList<? extends T> list = change.getList();
 
                        // Get the new items
                        for (int i = change.getFrom(); i < change.getTo(); i++) {
                            treeItem.getChildren().add(i, addRecursively(list.get(i)));
                        }
                    }
                    // If the list was sorted.
                    if (change.wasPermutated()) {
                        // Store the new order.
                        Map<Integer, TreeItem<T>> tempMap = new HashMap<Integer, TreeItem<T>>();
 
                        for (int i = change.getFrom(); i < change.getTo(); i++) {
                            int a = change.getPermutation(i);
                            tempMap.put(a, treeItem.getChildren().get(i));
                        }
                        getSelectionModel().clearSelection();
                        treeItem.getChildren().clear();
 
                        // Add the items in the new order.
                        for (int i = change.getFrom(); i < change.getTo(); i++) {
                            treeItem.getChildren().add(tempMap.get(i));
                        }
                    }
                }
            }
        };
    }
 
    /**
     * Removes the listener recursively.
     *
     * @param value The tree item.
     */
    private void removeRecursively(T value) {
        if (value != null && value.getChildren() != null) {
            value.getChildren().removeListener(map.get(value));
 
            for (T child : value.getChildren()) {
                removeRecursively(child);
            }
        }
    }
 
 
    /**
     * Adds the children to the tree recursively.
     *
     * @param value The initial value.
     * @return The tree item.
     */
    private TreeItem<T> addRecursively(T value) {
 
        TreeItem<T> treeItem = new TreeItem<T>();
        treeItem.setValue(value);
        treeItem.setExpanded(true);
 
        if (value != null && value.getChildren() != null) {
            ListChangeListener<T> listChangeListener = getListChangeListener(treeItem);
            value.getChildren().addListener(listChangeListener);
            map.put(value, listChangeListener);
 
            for (T child : value.getChildren()) {
                treeItem.getChildren().add(addRecursively(child));
            }
        }
        return treeItem;
    }
}