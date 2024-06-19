package com.devstack.lms.controller;

import com.devstack.lms.db.DatabaseAccessCode;
import com.devstack.lms.entity.AllRegistrations;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
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

        colDate.setCellValueFactory((new PropertyValueFactory<>("registered_date")));
        colStudentName.setCellValueFactory((new PropertyValueFactory<>("studentName")));
        colPayment.setCellValueFactory((new PropertyValueFactory<>("paymentType")));
//        loadAllCourses();
        tblCourses.getSelectionModel().selectedItemProperty().addListener((obs, oldSelection, newSelection) -> {
            if (newSelection != null) {
                showRegistrationDetailForm(newSelection);
            }
        });
    }

    private void showRegistrationDetailForm(AllRegistrations newSelection) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("../view/RegistrationDetailForm.fxml"));
            Parent root = loader.load();

//            RegistrationDetailFormController controller = loader.getController();
//            controller.initData(registration);

            Stage stage = new Stage();
            stage.setScene(new Scene(root));
            stage.setTitle("Registration Detail");
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    ObservableList<String> courseObList = null;

//    private void loadAllCourses() {
//        try {
//            DatabaseAccessCode databaseAccessCode = new DatabaseAccessCode();
//            courseObList = FXCollections.observableArrayList(databaseAccessCode.findAllCourses("")
//                    .stream().map(e -> e.getCourseId() + "|" + e.getCourseName()).collect(Collectors.toList()));
//            cmbSelectCourse.setItems(courseObList);
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//    }

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
