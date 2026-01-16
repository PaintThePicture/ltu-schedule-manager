package com.github.models.services;

import com.github.models.entities.TimeEditReservation;

import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.CheckBoxTableCell;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.cell.TextFieldTableCell;
/**
 * Builder for configuring a TableView to display TimeEdit reservations.
 */
public class ScheduleTableBuilder {
    private final TableView<TimeEditReservation> table; 

    /**
     * Constructs a ScheduleTableBuilder with the specified builder.
     * @param builder
     */
    private ScheduleTableBuilder(Builder builder) {
        this.table = builder.table;

        this.table.setEditable(true);
        this.table.setItems(ScheduleStore.getInstance().getReservations());
    }

    /**
     * Builder class for ScheduleTableBuilder.
     */
    public static class Builder {
        private final TableView<TimeEditReservation> table;

        /**
         * Constructor for Builder.
         * @param table
         * @param colSelect
         * @param dateCol
         * @param timeCol
         */
        public Builder(TableView<TimeEditReservation> table,
                       TableColumn<TimeEditReservation, Boolean> colSelect,
                       TableColumn<TimeEditReservation, String> dateCol, 
                       TableColumn<TimeEditReservation, String> timeCol) {
            
            this.table = table;
            
            colSelect.setCellValueFactory(new PropFactory<>("selected"));
            
            colSelect.setCellFactory(CheckBoxTableCell.forTableColumn(colSelect));
            colSelect.setEditable(true);

            dateCol.setCellValueFactory(new PropFactory<>("displayDate"));
            timeCol.setCellValueFactory(new PropFactory<>("displayTimeRange"));
        }  
        /**
         * Adds activity column configuration.
         * @param col
         * @return
         */
        public Builder withActivity(TableColumn<TimeEditReservation, String> col) {
            col.setCellValueFactory(new PropFactory<>("activity"));
            return this;
        }
        /**
         * Adds location column configuration.
         * @param col
         * @return
         */
        public Builder withLocation(TableColumn<TimeEditReservation, String> col) {
            col.setCellValueFactory(new PropFactory<>("location"));
            return this;
        }  
        /**
         * Adds course code column configuration.
         * @param col
         * @return
         */
        public Builder withCourseCode(TableColumn<TimeEditReservation, String> col) {
            col.setCellValueFactory(new PropFactory<>("courseCode"));
            return this;
        }
        /**
         * Adds comment column configuration.
         * @param col
         * @return
         */
        public Builder withComment(TableColumn<TimeEditReservation, String> col) {
            col.setCellValueFactory(new PropFactory<>("userComment"));
            
            col.setCellFactory(TextFieldTableCell.forTableColumn());
            col.setEditable(true);
            return this;
        }
        /**
         * Builds the ScheduleTableBuilder.
         * @return
         */
        public ScheduleTableBuilder build() {
            return new ScheduleTableBuilder(this);
        }
    }
    // Helper class for property value factories.
    private static class PropFactory<T> extends PropertyValueFactory<TimeEditReservation, T> {
        public PropFactory(String property) {
            super(property);
        }
    }
}
