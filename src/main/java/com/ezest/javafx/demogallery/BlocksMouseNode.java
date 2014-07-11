package com.ezest.javafx.demogallery;
import javafx.beans.property.ObjectProperty;
import javafx.collections.ObservableList;
import javafx.event.EventHandler;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.input.MouseEvent;

/**
 /**
 *
 * @author Narayan
 */
public class BlocksMouseNode extends Parent{
    private boolean blocksMouse;
    private static final int CLICKED=1,
            PRESSED=2,RELEASED=3,MOVED=4,
            DRAGGED=5,ENTERED=6,EXITED=7;

        @Override
        public ObservableList getChildren(){
            return super.getChildren();
        }

    // The method for registering the MouseEvent to the Node which
    // is just below this node
    public void register(ObjectProperty<EventHandler<? super MouseEvent>> property,
            final int i, final boolean blocks){
        property.set(new EventHandler<MouseEvent>(){
            @Override
            public void handle(MouseEvent event) {
                if(blocks){
                    setMouseTransparent(true);
                    Node n = getScene().getRoot().impl_pickNode(event.getSceneX(),event.getSceneY());
                    if(n !=null){
                        switch(i){
                                case CLICKED: if(n.getOnMouseClicked()!=null)
                                                n.getOnMouseClicked().handle(event);
                                              break;
                                case PRESSED: if(n.getOnMousePressed()!=null)
                                                n.getOnMousePressed().handle(event);
                                              break;
                                case RELEASED: if(n.getOnMouseReleased()!=null)
                                                n.getOnMouseReleased().handle(event);
                                               break;
                                case MOVED: if(n.getOnMouseMoved() !=null)
                                             n.getOnMouseMoved().handle(event);
                                            break;
                                case DRAGGED: if(n.getOnMouseDragged()!=null)
                                                n.getOnMouseDragged().handle(event);
                                              break;
                                case ENTERED: if(n.getOnMouseEntered()!=null)
                                                n.getOnMouseEntered().handle(event);
                                              break;
                                case EXITED:if(n.getOnMouseExited()!=null)
                                                n.getOnMouseExited().handle(event);
                                             break;
                        }

                        if(n.getOnMouseEntered()!=null)
                            n.getOnMouseEntered().handle(event);
                    }
                    setMouseTransparent(false);
                }
            }
        });
    }

    //Registering All the MouseEvents
    private void registerMouseEvents(boolean block){
          register(onMouseClickedProperty(),CLICKED,block);
          register(onMouseMovedProperty(),MOVED,block);
          register(onMouseReleasedProperty(),RELEASED,block);
          register(onMousePressedProperty(),PRESSED,block);
          register(onMouseDraggedProperty(),DRAGGED,block);
          register(onMouseEnteredProperty(),ENTERED,block);
          register(onMouseExitedProperty(),EXITED,block);
    }
    //-----------------
    //ACCESSOR METHODS
    //-----------------
    public void setBlocksMouse(boolean block){
        blocksMouse = block;
        registerMouseEvents(blocksMouse);
    }   

    public boolean getBlocksMouse(){
        return blocksMouse;
    }
}