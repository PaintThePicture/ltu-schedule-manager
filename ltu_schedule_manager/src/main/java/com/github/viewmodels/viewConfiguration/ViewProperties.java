package com.github.viewmodels.viewConfiguration;

import javafx.stage.Stage;

public class ViewProperties {
    private boolean centerOnScreen;
    private double height;
    private double heightMin;
    private boolean resizable;
    private double width;
    private double widthMin;
    private double xPosition;
    private double yPosition;

    public ViewProperties() {
        this.xPosition = -1;
        this.yPosition = -1;
        this.width = 1100;
        this.height = 600;
        this.widthMin = 600;
        this.heightMin = 400;
        this.resizable = true;
        this.centerOnScreen = true;
    }

    public ViewProperties(double xposition, double yposition, 
                           double width, double height, 
                           double minWidth, double minHeight, 
                           boolean resizable, boolean centerOnScreen) {
        this.xPosition = xposition;
        this.yPosition = yposition;
        this.width = width;
        this.height = height;
        this.widthMin = minWidth;
        this.heightMin = minHeight;
        this.resizable = resizable;
        this.centerOnScreen = centerOnScreen;
    }

    public void applyToStage(Stage stage) {
        if(stage != null) {
            stage.setX(this.xPosition <= 0 ? -1 : this.xPosition);
            stage.setY(this.yPosition <= 0 ? -1 : this.yPosition);
            stage.setWidth(this.width);
            stage.setHeight(this.height);
            stage.setMinWidth(this.widthMin);
            stage.setMinHeight(this.heightMin);
            stage.setResizable(this.resizable);

            if(this.centerOnScreen) {
                stage.centerOnScreen();
            }
        }
    }

    public boolean isCenterOnScreen() {
        return centerOnScreen;
    }

    public void setCenterOnScreen(boolean centerOnScreen) {
        this.centerOnScreen = centerOnScreen;
    }

    public double getHeight() {
        return height;
    }

    public void setHeight(double height) {
        this.height = height;
    }

    public double getHeightMin() {
        return heightMin;
    }

    public void setHeightMin(double heightMin) {
        this.heightMin = heightMin;
    }

    public boolean isResizable() {
        return resizable;
    }

    public void setResizable(boolean resizable) {
        this.resizable = resizable;
    }

    public double getWidth() {
        return width;
    }

    public void setWidth(double width) {
        this.width = width;
    }

    public double getWidthMin() {
        return widthMin;
    }

    public void setWidthMin(double widthMin) {
        this.widthMin = widthMin;
    }

    public double getXPosition() {
        return xPosition;
    }

    public void setXPosition(double xPosition) {
        this.xPosition = xPosition;
    }

    public double getYPosition() {
        return yPosition;
    }

    public void setYPosition(double yPosition) {
        this.yPosition = yPosition;
    }
}
