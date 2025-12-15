package com.github.controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;

import java.io.IOException;

import com.github.App;
import com.github.viewmodels.viewNavigation.ViewController;

import javafx.fxml.FXML;

public class PrimaryController implements ViewController {
    
    @FXML
    private TableColumn<?, ?> colActivity;

    @FXML
    private TableColumn<?, ?> colComment;

    @FXML
    private TableColumn<?, ?> colCourseCode;

    @FXML
    private TableColumn<?, ?> colDate;

    @FXML
    private TableColumn<?, ?> colLocation;

    @FXML
    private TableColumn<?, ?> colSelect;

    @FXML
    private TableColumn<?, ?> colTime;

    @FXML
    private TableView<?> resultsTable;

    @FXML
    private TextField tfComment;

    @FXML
    private TextField tfLinkTimeEdit;

    @FXML
    void clickImport(ActionEvent event) {

    }

    @FXML
    void clickSelectAll(ActionEvent event) {

    }

    @FXML
    void clickSelectNone(ActionEvent event) {

    }

    @FXML
    void clickSetComment(ActionEvent event) {

    }

    @FXML
    void clickTransfer(ActionEvent event) {

    }

    @Override
    public void initialize() {
        // TODO Auto-generated method stub

    }

    @Override
    public void onUnload() {
        // TODO Auto-generated method stub

    }

}