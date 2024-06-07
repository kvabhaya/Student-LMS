package com.devstack.lms.controller;

import com.devstack.lms.db.DatabaseAccessCode;
import com.devstack.lms.model.AllRegistrations;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.stream.Collectors;

public class AllRegistrationFormController {
    public AnchorPane context;
    public TableView<AllRegistrations> tblCourses;
    public TableColumn<AllRegistrations, String> colDate;
    public TableColumn<AllRegistrations, String> colStudentName;
    public TableColumn<AllRegistrations, String> colPayment;
    public Button btnSave;
    public ComboBox<String> cmbSelectCourse;

    public void initialize() {
        loadAllCourses();

        colDate.setCellValueFactory(data -> data.getValue().registeredDateProperty());
        colStudentName.setCellValueFactory(data -> data.getValue().studentNameProperty());
        colPayment.setCellValueFactory(data -> data.getValue().paymentTypeProperty());
    }

    private void setCourseDetails(Object newValue) {
    }

    ObservableList<String> courseObList = null;

    private void loadAllCourses() {
        try {
            DatabaseAccessCode databaseAccessCode = new DatabaseAccessCode();
            courseObList = FXCollections.observableArrayList(databaseAccessCode.findAllCourses("")
                    .stream().map(e -> e.getCourseId() + "|" + e.getCourseName()).collect(Collectors.toList()));
            cmbSelectCourse.setItems(courseObList);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void btnBachToHomeOnAction(ActionEvent actionEvent) throws IOException {
        URL resource = getClass().getResource("../view/DashboardForm.fxml");
        Stage stage = (Stage) context.getScene().getWindow();
        stage.centerOnScreen();
        stage.setScene(new Scene(FXMLLoader.load(resource)));
        stage.setTitle("Dashboard Form");
    }

    public void searchOnAction(ActionEvent actionEvent) {
        String selectedCourse = cmbSelectCourse.getSelectionModel().getSelectedItem();
        if (selectedCourse != null) {
            String courseId = selectedCourse.split("\\|")[0];
            try {
                DatabaseAccessCode databaseAccessCode = new DatabaseAccessCode();
                List<AllRegistrations> registrationList = databaseAccessCode.loadAllDetails(courseId);
                ObservableList<AllRegistrations> registrations = FXCollections.observableArrayList(registrationList);
                tblCourses.setItems(registrations);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}
