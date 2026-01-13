package com.github.models.services;

import com.github.models.entities.TimeEditReservation;

import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.CheckBoxTableCell;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.cell.TextFieldTableCell;

public class ScheduleTableBuilder {
    private final TableView<TimeEditReservation> table; 

    private ScheduleTableBuilder(Builder builder) {
        this.table = builder.table;

        this.table.setEditable(true);
        this.table.setItems(ScheduleStore.getInstance().getReservations());
    }

    public static class Builder {
        private final TableView<TimeEditReservation> table;

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
        public Builder withActivity(TableColumn<TimeEditReservation, String> col) {
            col.setCellValueFactory(new PropFactory<>("activity"));
            return this;
        }
        public Builder withLocation(TableColumn<TimeEditReservation, String> col) {
            col.setCellValueFactory(new PropFactory<>("location"));
            return this;
        }
        public Builder withCourseCode(TableColumn<TimeEditReservation, String> col) {
            col.setCellValueFactory(new PropFactory<>("courseCode"));
            return this;
        }
        public Builder withComment(TableColumn<TimeEditReservation, String> col) {
            col.setCellValueFactory(new PropFactory<>("userComment"));
            
            col.setCellFactory(TextFieldTableCell.forTableColumn());
            col.setEditable(true);
            return this;
        }

        public ScheduleTableBuilder build() {
            return new ScheduleTableBuilder(this);
        }
    }

    private static class PropFactory<T> extends PropertyValueFactory<TimeEditReservation, T> {
        public PropFactory(String property) {
            super(property);
        }
    }
}
