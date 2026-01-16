package com.github.utilities;

import java.io.PrintWriter;
import java.io.StringWriter;

import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.TextArea;
import javafx.scene.layout.Region;
import javafx.stage.Window;

/**
 * Builder for creating alert windows.
 */
public class AltertWindowBuilder {
    private final Alert alert;

    /**
     * Constructs an AltertWindowBuilder with the specified builder.
     * @param builder
     */
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
    /**
     * Builder class for AltertWindowBuilder.
     */
    public static class Builder {
        private final AlertType type;
        private final String title;
        private final String content;

        private String header;
        private Throwable exception;
        private Window owner;
        /**
         * Constructor for Builder.
         * @param type
         * @param title
         * @param content
         */
        public Builder(AlertType type, String title, String content) {
            this.type = type;
            this.title = title;
            this.content = content;
        }
        /**
         * Sets the header text.
         * @param header
         * @return
         */
        public Builder withHeader(String header) {
            this.header = header;
            return this;
        }
        /**
         * Sets the exception to display stack trace.
         * @param e
         * @return
         */
        public Builder withException(Throwable e) {
            this.exception = e;
            return this;
        }
        /**
         * Sets the owner window.
         * @param owner
         * @return
         */
        public Builder withOwner(Window owner) {
            this.owner = owner;
            return this;
        }
        /**
         * Builds the AltertWindowBuilder.
         * @return
         */
        public AltertWindowBuilder build() {
            return new AltertWindowBuilder(this);
        }
    }
    // Creates a TextArea node containing the stack trace of the exception.
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
    /**
     * Displays the alert window.
     */
    public void show() {
        this.alert.showAndWait();
    }
}
