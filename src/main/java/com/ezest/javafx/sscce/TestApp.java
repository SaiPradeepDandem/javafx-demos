package com.ezest.javafx.sscce;

import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.control.TitledPane;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.StackPaneBuilder;
import javafx.stage.Stage;
import javafx.util.Callback;

public class TestApp extends Application
{
    public static void main(String[] args)
    {
        Application.launch(args);
    }
 
    @Override
    public void start(Stage stage) throws Exception
    {                                                                           
        FlowPane root = new FlowPane();
 
        // smiley face is just the first one google showed me - not mine!
        ObservableList<Buddy> buddies = FXCollections.observableArrayList(
                new Buddy("http://www.pavendors.com/images/smileyface.gif", "Don Haig", true),
                new Buddy("http://www.pavendors.com/images/smileyface.gif", "David Sloan", false)
        );
 
        ListView<Buddy> buddiesList = new ListView<Buddy>(buddies);
        buddiesList.setCellFactory(new Callback<ListView<Buddy>, ListCell<Buddy>>()
        {
            public ListCell<Buddy> call(ListView<Buddy> buddyListView)
            {
                final BuddyCell buddyCell = new BuddyCell();
                ListCell<Buddy> cell = new ListCell<Buddy>()
                {
                    protected void updateItem(Buddy buddy, boolean empty)
                    {
                        super.updateItem(buddy, empty);
                        buddyCell.setVisible(!empty);
                        buddyCell.setBuddy(buddy);
                    }
                };
                cell.setGraphic(buddyCell);
                return cell;
            }
        });
 
        TitledPane buddiesArea = new TitledPane("Buddies", buddiesList);
        
        root.getChildren().addAll(buddiesArea);
 
        Scene scene = new Scene(root, 800, 600);
        scene.getStylesheets().add("styles.css");
        stage.setScene(scene);
        stage.show();
    }
 
    //-------------------------------------------------------------------------
 
    private class BuddyCell extends HBox
    {
        private ImageView iconLabel;
        private Label nameLabel;
        private CheckBox onlineCheckBox; // use an icon for real
 
        private BuddyCell()
        {
            iconLabel = new ImageView();
            iconLabel.setFitHeight(20);
            iconLabel.setFitWidth(20);
            getChildren().add(iconLabel);
 
            nameLabel = new Label();
            nameLabel.setMaxWidth(Double.MAX_VALUE);
            HBox.setHgrow(nameLabel, Priority.ALWAYS);
            getChildren().add(nameLabel);
 
            onlineCheckBox = new CheckBox();
            getChildren().add(onlineCheckBox);
        }
 
        public void setBuddy(Buddy buddy)
        {
            if (buddy != null)
            {
                iconLabel.setImage(new Image(buddy.getIcon()));
                nameLabel.setText(buddy.getName());
                onlineCheckBox.setSelected(buddy.isOnline());
            }
            else
            {
                iconLabel.setImage(null);
                nameLabel.setText(null);
                onlineCheckBox.setSelected(false);
            }
        }
    }
 
    //-------------------------------------------------------------------------
 
    private class Buddy
    {
        private String icon;
        private String name;
        private boolean online; // use an enum for real
 
        private Buddy(String icon, String name, boolean online)
        {
            this.icon = icon;
            this.name = name;
            this.online = online;
        }
 
        public String getIcon()
        {
            return icon;
        }
 
        public String getName()
        {
            return name;
        }
 
        public boolean isOnline()
        {
            return online;
        }
    }
}
