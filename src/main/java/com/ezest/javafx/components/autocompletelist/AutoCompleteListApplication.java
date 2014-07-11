package com.ezest.javafx.components.autocompletelist;

import java.util.Arrays;
import java.util.List;

import javafx.application.Application;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.ListChangeListener;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.stage.Stage;

public class AutoCompleteListApplication extends Application
{
    public static void main(String[] args)
    {
        Application.launch(AutoCompleteListApplication.class, args);
    }

    public void start(Stage stage) throws Exception
    {
        HBox root = new HBox();
        root.setStyle("-fx-padding: 30");

        root.getChildren().add(new Label("Choose your destiny:"));

        final AutoCompleteList<String> list = new AutoCompleteList<String>();
        final List<String> allItems = Arrays.asList("Make a difference", "Do no harm", "Start a Krispy Kreme franchise");

        // filtering of list
        list.inputText().addListener(new ChangeListener<String>()
        {
            public void changed(ObservableValue<? extends String> source, String oldValue, String newValue)
            {
                list.getAvailableItems().clear();
                for (String item : allItems)
                {
                    if (item.toLowerCase().contains(newValue))
                    {
                        list.getAvailableItems().add(item);
                    }
                }
                list.getAvailableItemsSelectionModel().selectFirst();
            }
        });

        list.getChosenItems().addListener(new ListChangeListener<String>()
        {
            public void onChanged(Change<? extends String> change)
            {
                System.out.println("Your destiny consists of: ");
                for (String chosen : list.getChosenItems())
                {
                    System.out.println(" => " + chosen);
                }
            }
        });
        HBox.setHgrow(list, Priority.ALWAYS);
        root.getChildren().add(list);

        Scene scene = new Scene(root, 800, 600);
        stage.setScene(scene);
        stage.show();
    }
}
