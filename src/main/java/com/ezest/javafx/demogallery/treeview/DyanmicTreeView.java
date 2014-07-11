package com.ezest.javafx.demogallery.treeview;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.TreeItem;
import javafx.scene.control.TreeItem.TreeModificationEvent;
import javafx.scene.control.TreeView;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
 
public class DyanmicTreeView extends Application {
 
    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        Application.launch(DyanmicTreeView.class, args);
    }
 
    @Override
    public void start(Stage primaryStage) {
        primaryStage.setTitle("Example Dynamic Tree");
        primaryStage.setResizable(true);
        final VBox box = new VBox();
        box.setFillWidth(false);
        Scene scene = new Scene(box);
        primaryStage.setScene(scene);
        box.getChildren().add(this.getExampleTree());
        primaryStage.show();
    }
 
    private TreeView<DynamicTreeNodeModel> getExampleTree() {
        DynamicTreeNodeModel rootNode = new RandomDynamicTreeNodeModel(null, "Root Node");
        TreeView<DynamicTreeNodeModel> treeView = new TreeView<DynamicTreeNodeModel>();
        treeView.setPrefSize(1000, 750);
        TreeItem rootItem = new TreeItem(rootNode);
        rootItem.addEventHandler(TreeItem.branchExpandedEvent(), new EventHandler<TreeItem.TreeModificationEvent<DynamicTreeNodeModel>>() {
 
            public void handle(TreeModificationEvent<DynamicTreeNodeModel> event) {
//                System.out.println("handling event " + event);
                TreeItem<DynamicTreeNodeModel> item = event.getTreeItem();
                DynamicTreeNodeModel node = item.getValue();
                boolean isPopulated = node.isPopulated();
                boolean areGrandChildrenPopulated = node.areChildenPopulated();
                node.populateToDepth(2);
                if (!isPopulated) {
                    for (DynamicTreeNodeModel childNode : node.getChildren()) {
                        TreeItem childItem = new TreeItem(childNode);
                        childItem.addEventHandler(TreeItem.branchExpandedEvent(), this);
                        item.getChildren().add(childItem);
                    }
                }
                if (!areGrandChildrenPopulated) {
                    int i = 0;
                    for (TreeItem childItem : item.getChildren()) {
                        // get cooresponding node in the model
                        DynamicTreeNodeModel childNode = node.getChildren().get(i);
                        i++;
                        for (DynamicTreeNodeModel grandChildNode : childNode.getChildren()) {
                            TreeItem grandChildItem = new TreeItem(grandChildNode);
                            grandChildItem.addEventHandler(TreeItem.branchExpandedEvent(), this);
                            childItem.getChildren().add(grandChildItem);
                        }
                    }
                }
            }
        });
        treeView.setShowRoot(true);
        treeView.setRoot(rootItem);
        rootItem.setExpanded(true);
//        treeView.setCellFactory(new LearningTreeCellFactory());
        return treeView;
    }
 
    private static interface DynamicTreeNodeModel {
 
        public String getName();
 
        public void setName(String name);
 
        public boolean isPopulated();
 
        public boolean areChildenPopulated();
 
        public List<DynamicTreeNodeModel> getChildren();
 
        public void setChildren(List<DynamicTreeNodeModel> children);
 
        public DynamicTreeNodeModel getParent();
 
        public void setParent(DynamicTreeNodeModel parent);
 
        public void populateToDepth(int depth);
 
        @Override
        public String toString();
    }
 
    private static class RandomDynamicTreeNodeModel implements DynamicTreeNodeModel {
 
        private DynamicTreeNodeModel parent;
        private String name;
        private List<DynamicTreeNodeModel> children = null;
 
        public RandomDynamicTreeNodeModel(DynamicTreeNodeModel parent, String name) {
            this.parent = parent;
            this.name = name;
        }
 
        @Override
        public String getName() {
            return name;
        }
 
        @Override
        public void setName(String name) {
            this.name = name;
        }
 
        @Override
        public boolean isPopulated() {
            if (children == null) {
                return false;
            }
            return true;
        }
 
        @Override
        public boolean areChildenPopulated() {
            if (!this.isPopulated()) {
                return false;
            }
            for (DynamicTreeNodeModel child : this.children) {
                if (!child.isPopulated()) {
                    return false;
                }
            }
            return true;
        }
 
        @Override
        public List<DynamicTreeNodeModel> getChildren() {
            return children;
        }
 
        @Override
        public void setChildren(List<DynamicTreeNodeModel> children) {
            this.children = children;
        }
 
        @Override
        public DynamicTreeNodeModel getParent() {
            return parent;
        }
 
        @Override
        public void setParent(DynamicTreeNodeModel parent) {
            this.parent = parent;
        }
        private static Random random = new Random();
 
        @Override
        public void populateToDepth(int depth) {
            if (depth <= 0) {
                return;
            }
            if (children == null) {
                int num = random.nextInt(5);
                System.out.println("got a random number " + num);
                children = new ArrayList(num);
                for (int i = 0; i < num; i++) {
                    children.add(new RandomDynamicTreeNodeModel(this, "child " + i));
                }
            }
            int childDepth = depth - 1;
            for (DynamicTreeNodeModel child : children) {
                child.populateToDepth(childDepth);
            }
        }
 
        @Override
        public String toString() {
            return this.name;
        }
    }
}
