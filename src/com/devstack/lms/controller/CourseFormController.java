package com.devstack.lms.controller;

import com.devstack.lms.db.DatabaseAccessCode;
import com.devstack.lms.model.Course;
import com.devstack.lms.model.Student;
import com.devstack.lms.view.TM.CourseTM;
import com.devstack.lms.view.TM.StudentTM;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

public class CourseFormController {
    public AnchorPane context;
    public TextField txtCourseName;
    public TextField txtCourseFee;
    public TextField txtSearch;
    public TableView<CourseTM> tblCourses;
    public TableColumn<CourseTM, String> colCourseName;
    public TableColumn<CourseTM, Double> colCourseFee;
    public TableColumn<CourseTM, ButtonBar> colOption;
    public Button btnSave;

    private String searchText="";
    private Course selectedCourse=null;

    public void initialize(){
        colCourseName.setCellValueFactory(new PropertyValueFactory<>("course_name"));
        colCourseFee.setCellValueFactory(new PropertyValueFactory<>("fee"));
        colOption.setCellValueFactory(new PropertyValueFactory<>("bar"));
        loadAllCourses();

        txtSearch.textProperty().addListener((observable, oldValue, newValue) -> {
            searchText = newValue;
            loadAllCourses();
        });
    }

    private void loadAllCourses() {
        ObservableList<CourseTM> tmObservableList = FXCollections.observableArrayList();
        try{
            DatabaseAccessCode databaseAccessCode = new DatabaseAccessCode();
            List<Course> allCourses = databaseAccessCode.findAllCourses(searchText);

            for(Course c:allCourses){

                ButtonBar bar = new ButtonBar();
                Button deleteButton = new Button("Delete");
                Button updateButton = new Button("Update");
                bar.getButtons().addAll(deleteButton,updateButton);

                CourseTM tm = new CourseTM(
                        c.getCourseId(),
                        c.getCourseName(),
                        c.getFee(),
                        bar
                );

                deleteButton.setOnAction(event -> {

                    Alert alert = new Alert(Alert.AlertType.CONFIRMATION,"Are you sure?", ButtonType.NO, ButtonType.YES);
                    Optional<ButtonType> buttonType = alert.showAndWait();

                    if(buttonType.get()==ButtonType.YES){
                        try{
                            DatabaseAccessCode dbAccessCode = new DatabaseAccessCode();
                            boolean isDeleted = dbAccessCode.deleteCourse(tm.getCourse_id());
                            if(isDeleted){
                                new Alert(Alert.AlertType.INFORMATION,"Course has been deleted...", ButtonType.CLOSE).show();
                                loadAllCourses();
                            }else{
                                new Alert(Alert.AlertType.WARNING,"Something went wrong. Try again...", ButtonType.CLOSE).show();
                            }
                        }catch(SQLException | ClassNotFoundException e){
                            new Alert(Alert.AlertType.ERROR, e.getMessage(),ButtonType.CLOSE).show();
                        }
                    }
                });

                updateButton.setOnAction(event -> {
                    btnSave.setText("Update Course");
                    selectedCourse = c;

                    txtCourseName.setText(c.getCourseName());
                    txtCourseFee.setText(String.valueOf(c.getFee()));
                });

                tmObservableList.add(tm);
            }
            tblCourses.setItems(tmObservableList);
        }catch(SQLException | ClassNotFoundException e){
            new Alert(Alert.AlertType.ERROR, e.getMessage(),ButtonType.CLOSE).show();
        }
    }

    public void btnNewCourseOnAction(ActionEvent actionEvent){
        btnSave.setText("Save Course");
        clearFields();
        selectedCourse=null;
    }

    public void btnBachToHomeOnAction(ActionEvent actionEvent) throws IOException {
        setUi("DashboardForm");
    }

    public void saveCourseOnAction(ActionEvent actionEvent){
        if(btnSave.getText().equalsIgnoreCase("Save Course")){
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
        }else {
            if(selectedCourse != null){
                try{
                    Course course = new Course(
                            UUID.randomUUID().toString(),
                            txtCourseName.getText().trim(),
                            Double.parseDouble(txtCourseFee.getText())
                    );
                    DatabaseAccessCode databaseAccessCode = new DatabaseAccessCode();
                    boolean isSaved = databaseAccessCode.updateCourse(course);
                    if(isSaved){
                        new Alert(Alert.AlertType.INFORMATION,"Course has been updated...", ButtonType.CLOSE).show();
                        clearFields();
                        loadAllCourses();
                        btnSave.setText("Save course");
                    }else{
                        new Alert(Alert.AlertType.WARNING,"Try again...", ButtonType.CLOSE).show();
                    }

                }catch(SQLException | ClassNotFoundException e){
                    new Alert(Alert.AlertType.ERROR,e.getMessage(), ButtonType.CLOSE).show();
                }
            }else{
                new Alert(Alert.AlertType.ERROR,"Select a student", ButtonType.CLOSE).show();
            }
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
