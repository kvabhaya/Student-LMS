package com.devstack.lms.controller;

import com.devstack.lms.db.DatabaseAccessCode;
import com.devstack.lms.model.Course;
import com.devstack.lms.model.Student;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.UUID;

public class CourseFormController {
    public AnchorPane context;
    public TextField txtCourseName;
    public TextField txtCourseFee;
    public TextField txtSearch;
    public TableView tblCourses;
    public TableColumn colCourseName;
    public TableColumn colCourseFee;
    public TableColumn colOption;
    public Button btnSave;

    public void btnNewCourseOnAction(ActionEvent actionEvent){

    }
    public void btnBachToHomeOnAction(ActionEvent actionEvent) throws IOException {
        setUi("DashboardForm");
    }
    public void saveCourseOnAction(ActionEvent actionEvent){
        try{
            Course course = new Course(
                    UUID.randomUUID().toString(),
                    txtCourseName.getText().trim(),
                    Double.parseDouble(txtCourseFee.getText())
            );
            DatabaseAccessCode databaseAccessCode = new DatabaseAccessCode();
            boolean isSaved = databaseAccessCode.saveCourse(course);
            if(isSaved){
                new Alert(Alert.AlertType.INFORMATION,"Course has been saved...", ButtonType.CLOSE).show();
                clearFields();
            }else{
                new Alert(Alert.AlertType.WARNING,"Try again...", ButtonType.CLOSE).show();
            }

        }catch(SQLException | ClassNotFoundException e){
            new Alert(Alert.AlertType.ERROR,e.getMessage(), ButtonType.CLOSE).show();
        }
    }

    private void clearFields() {
        txtCourseName.clear();
        txtCourseFee.clear();
    }
    private void setUi(String location) throws IOException {
        URL resource = getClass().getResource("../view/"+location+".fxml");
        Stage stage = (Stage) context.getScene().getWindow();
        stage.centerOnScreen();
        stage.setScene(new Scene(FXMLLoader.load(resource)));
        stage.setTitle(location);
    }
}
