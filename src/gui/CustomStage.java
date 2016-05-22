package gui;

import java.util.Optional;

import img.SafeImage;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.control.ToolBar;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

/**
 * Stage with a custom title bar
 * 
 * @author Orion
 *
 */
public class CustomStage extends Stage {
    
    private final int titleHeight = 25;
    private final int titleButtonHeight = 20;
    
    private Scene baseScene;
    private BorderPane basePane;
    private ToolBar titleBar;
    private HBox titleBuffer;
    private Label titleLabel;
    private Button minimizeButton, closeButton;
    
    private double xInitial, yInitial;
    
    private boolean warnBeforeQuitting;
    
    protected CustomStage() {
        this.initStyle(StageStyle.UNDECORATED);
        
        basePane = new BorderPane();
        
        titleLabel = new Label();
        
        titleBuffer = new HBox();
        HBox.setHgrow(titleBuffer, Priority.ALWAYS);
        
        minimizeButton = new Button();
        minimizeButton.setGraphic(new ImageView(new SafeImage("/img/minimizeIcon.png").getImage()));
        minimizeButton.setPrefHeight(titleButtonHeight);
        minimizeButton.setMaxHeight(titleButtonHeight);
        minimizeButton.setMinHeight(titleButtonHeight);
        minimizeButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                toggleMinimized();
            }
        });
        
        closeButton = new Button();
        closeButton.setGraphic(new ImageView(new SafeImage("/img/closeIcon.png").getImage()));
        closeButton.setPrefHeight(titleButtonHeight);
        closeButton.setMaxHeight(titleButtonHeight);
        closeButton.setMinHeight(titleButtonHeight);
        closeButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                closeWindow();
            }
        });
        
        titleBar = new ToolBar(
                titleLabel,
                titleBuffer,
                minimizeButton,
                closeButton);
        titleBar.setPrefHeight(titleHeight);
        titleBar.setMaxHeight(titleHeight);
        titleBar.setMinHeight(titleHeight);
        
        titleBar.addEventFilter(MouseEvent.MOUSE_PRESSED, new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                xInitial = event.getScreenX();
                yInitial = event.getScreenY();
            }
        });
        
        titleBar.addEventFilter(MouseEvent.MOUSE_DRAGGED, new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                offsetPosition(event.getScreenX() - xInitial, event.getScreenY() - yInitial);
                xInitial = event.getScreenX();
                yInitial = event.getScreenY();
            }
        });
        
        basePane.setTop(titleBar);
        
        baseScene = new Scene(basePane);
        
        this.setScene(baseScene);
    }
    
    /**
     * Sets the node to encompass the entirety of 
     * the stage that does not include the title bar
     * 
     * @param majorNode The {@link javafx.scene.Node Node} to put in the stage
     */
    public void setMajor(Node majorNode) {
        basePane.setCenter(majorNode);
    }
    
    /**
     * <u><b>! Use this instead of {@link javafx.stage.Stage#setTitle(String) setTitle} !</b></u><br>
     * Sets the value of the property title and its visual representation on the custom title bar.
     * 
     * @param newTitle The {@link java.lang.String String} title replace the current title with
     */
    public void setCustomTitle(String newTitle) {
        this.setTitle(newTitle);
        titleLabel.setText(newTitle);
    }
    
    public void setTitleIcon(Image titleIcon) {
        titleLabel.setGraphic(new ImageView(titleIcon));
    }
    
    public void setTitleBarColor(String color) {
        titleBar.setStyle("-fx-background-color: " + color);
    }
    
    public void setTitleTextColor(String color) {
        titleLabel.setTextFill(Color.web(color));
    }
    
    public void toggleMinimized() {
        this.setIconified(!this.isIconified());
    }
    
    public void setMinimized(boolean bool) {
        this.setIconified(bool);
    }
    
    public void closeWindow() {
        if (warnBeforeQuitting) {
        	Alert closeAlert = new Alert(AlertType.CONFIRMATION);
        	closeAlert.setTitle("Confirm Close");
        	closeAlert.setHeaderText("Confirm Close");
        	closeAlert.setContentText("Are you sure that you would like to close Molecula?");
        	Optional<ButtonType> result = closeAlert.showAndWait();
        	if (result.get() == ButtonType.OK){
        		this.close();
        	} else {
        	    //Do nothing
        	}
        } else {
            this.close();
        }
    }
    
    public void setPosition(double x, double y) {
        this.setX(x);
        this.setY(y);
    }
    
    public void offsetPosition(double xOffset, double yOffset) {
        this.setX(this.getX() + xOffset);
        this.setY(this.getY() + yOffset);
    }
    
    public void warnBeforeQuitting(boolean bool) {
        warnBeforeQuitting = bool;
    }
}
