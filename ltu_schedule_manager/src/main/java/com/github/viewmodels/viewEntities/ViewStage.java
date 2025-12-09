package com.github.viewmodels.viewEntities;

import com.github.viewmodels.viewConfiguration.ViewProperties;
import com.github.viewmodels.viewNavigation.ViewController;

import javafx.beans.value.ChangeListener;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class ViewStage {
    private ViewController controller;
    private static final ViewStage instance = new ViewStage();
    private final ViewProperties properties;
    private Scene scene;
    private Stage stage;
    private ChangeListener<Number> heightListener;
    private ChangeListener<Number> widthListener;
    private ChangeListener<Number> xListener;
    private ChangeListener<Number> yListener;

    private ViewStage() {
        this.properties = new ViewProperties();
    }

    public void setPrimStage(Stage stage) {
        if (this.stage != null) {
            throw new IllegalStateException("\n\nError: primary stage has already been set and cannot be changed. \n\n");
        }

        this.stage = stage;
        this.properties.applyToStage(this.stage);

        createListeners();
        this.stage.xProperty().addListener(this.xListener);
        this.stage.yProperty().addListener(this.yListener);
        this.stage.widthProperty().addListener(this.widthListener);
        this.stage.heightProperty().addListener(this.heightListener);
    }

    public void show(String title) {
        if (this.stage == null) {
            throw new IllegalStateException("\n\nError: stage is not set. Call setPrimStage, at startup");
        }

        this.stage.setScene(this.scene);
        this.stage.setTitle(title);
        this.stage.show();
    }

    public void removeListeners() {
        this.stage.xProperty().removeListener(this.xListener);
        this.stage.yProperty().removeListener(this.yListener);
        this.stage.widthProperty().removeListener(this.widthListener);
        this.stage.heightProperty().removeListener(this.heightListener);
    }

    private void createListeners() {
        this.xListener = (o, oV, nV) -> this.properties.setXPosition(nV.doubleValue());
        this.yListener = (o, oV, nV) -> this.properties.setYPosition(nV.doubleValue());
        this.widthListener = (o, oV, nV) -> this.properties.setWidth(nV.doubleValue());
        this.heightListener = (o, oV, nV) -> this.properties.setHeight(nV.doubleValue());
    }

    public ViewController getController() {
        return controller;
    }

    public void setController(ViewController controller) {
        this.controller = controller;
    }

    public ViewProperties getProperties() {
        return properties;
    }

    public void setScene(Scene scene) {
        this.scene = scene;
    }

    public static ViewStage getInstance() {
        return instance;
    }
}
