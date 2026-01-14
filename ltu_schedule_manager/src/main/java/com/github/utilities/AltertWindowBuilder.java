package com.github.utilities;

import java.io.PrintWriter;
import java.io.StringWriter;

import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.TextArea;
import javafx.scene.layout.Region;
import javafx.stage.Window;

public class AltertWindowBuilder {
    private final Alert alert;

    private AltertWindowBuilder(Builder builder) {
        this.alert = new Alert(builder.type);
        this.alert.setTitle(builder.title);
        this.alert.setHeaderText(builder.header);
        this.alert.setContentText(builder.content);

        if (builder.header != null) {
            this.alert.setHeaderText(builder.header);
        }

        if (builder.owner != null) {
            this.alert.initOwner(builder.owner);
        }

        if (builder.exception != null) {
            this.alert.getDialogPane().setExpandableContent(createStackTraceNode(builder.exception));
        }

        this.alert.getDialogPane().setMinHeight(Region.USE_PREF_SIZE);
    }

    public static class Builder {
        private final AlertType type;
        private final String title;
        private final String content;

        private String header;
        private Throwable exception;
        private Window owner;

        public Builder(AlertType type, String title, String content) {
            this.type = type;
            this.title = title;
            this.content = content;
        }
        public Builder withHeader(String header) {
            this.header = header;
            return this;
        }
        public Builder withException(Throwable e) {
            this.exception = e;
            return this;
        }
        public Builder withOwner(Window owner) {
            this.owner = owner;
            return this;
        }
        public AltertWindowBuilder build() {
            return new AltertWindowBuilder(this);
        }
    }

    private TextArea createStackTraceNode(Throwable e) {
            StringWriter sw = new StringWriter();
            PrintWriter pw = new PrintWriter(sw);
            e.printStackTrace(pw);
            
            TextArea textArea = new TextArea(sw.toString());
            textArea.setEditable(false);
            textArea.setWrapText(true);
            textArea.setMaxWidth(Double.MAX_VALUE);
            textArea.setMaxHeight(Double.MAX_VALUE);
            return textArea;
    }

    public void show() {
        this.alert.showAndWait();
    }
}
