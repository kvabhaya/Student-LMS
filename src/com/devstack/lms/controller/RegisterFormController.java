package com.devstack.lms.controller;

import com.devstack.lms.business.BoFactory;
import com.devstack.lms.business.custom.CourseBo;
import com.devstack.lms.business.custom.RegistrationBo;
import com.devstack.lms.business.custom.StudentBo;
import com.devstack.lms.db.DatabaseAccessCode;
import com.devstack.lms.dto.CourseDto;
import com.devstack.lms.dto.RegistrationDto;
import com.devstack.lms.dto.StudentDto;
import com.devstack.lms.entity.Course;
import com.devstack.lms.entity.Registration;
import com.devstack.lms.entity.Student;
import com.devstack.lms.util.PaymentType;
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
import java.sql.SQLException;
import java.util.Date;
import java.util.UUID;
import java.util.stream.Collectors;

public class RegisterFormController {
    public TextField txtName;
    public TextField txtEmail;
    public Button btnSave;
    public TextField txtCourseName;
    public TextField txtCourseFee;
    public ComboBox<String> cmbCourse;
    public ComboBox<String> cmbStudent;
    public AnchorPane context;
    public RadioButton rbtnCash;

    private StudentDto selectedStudent;
    private CourseDto selectedCourse;

    private final RegistrationBo registrationBo = BoFactory.getBo(BoFactory.BoType.REGISTRATION);
    private final StudentBo studentBo = BoFactory.getBo(BoFactory.BoType.STUDENT);
    private final CourseBo courseBo = BoFactory.getBo(BoFactory.BoType.COURSE);


    public void initialize(){
        loadAllCourses();
        loadAllStudents();
        cmbCourse.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) ->{
            if(newValue!=null){
                setCourseDetails(newValue);
            }
                });
        cmbStudent.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) ->{
            if(newValue!=null){
                setStudentDetails(newValue);
            }
        });
    }

    private void setStudentDetails(String newValue) {
        String[] splitData = newValue.split("\\|");
        String studentId = splitData[0].trim();
        try{
            selectedStudent = StudentBo.find(studentId);
            if(selectedStudent==null){
                new Alert(Alert.AlertType.WARNING,"Student not found...");
                return;
            }
            txtName.setText(selectedStudent.getStudentName());
            txtEmail.setText(selectedStudent.getEmail());
        } catch (Exception e){
            e.printStackTrace();
        }
    }

    ObservableList<String> studentObList = null;
    private void loadAllStudents() {
        try{
            studentObList = FXCollections.observableArrayList(StudentBo.findAll()
                    .stream().map(e->e.getStudentId()+"|"+e.getStudentName()).collect(Collectors.toList()));
            cmbStudent.setItems(studentObList);
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    private void setCourseDetails(String newValue) {
        String[] splitData = newValue.split("\\|");
        String courseId = splitData[0].trim();
        try{
            selectedCourse = courseBo.find(courseId);
            if(selectedCourse==null){
                new Alert(Alert.AlertType.WARNING,"Student not found...");
                return;
            }
            txtCourseName.setText(selectedCourse.getCourseName());
            txtCourseFee.setText(String.valueOf(selectedCourse.getFee()));
        } catch (Exception e){
            e.printStackTrace();
        }
    }

    ObservableList<String> courseObList = null;
    private void loadAllCourses() {

        try{
            courseObList = FXCollections.observableArrayList(CourseBo.findAll()
                    .stream().map(e->e.getCourseId()+"|"+e.getCourseName()).collect(Collectors.toList()));
            cmbCourse.setItems(courseObList);
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    public void btnBachToHomeOnAction(ActionEvent actionEvent) throws IOException {
        setUi("DashboardForm");
    }

    public void registerNowOnAction(ActionEvent actionEvent) {
        if(selectedCourse==null || selectedStudent==null){
            new Alert(Alert.AlertType.WARNING, "Please return to home and come back...").show();
            return;
        }
        try{
            RegistrationDto registration = new RegistrationDto(
                    UUID.randomUUID().toString(),
                    new Date(),
                    null,
                    rbtnCash.isSelected()?PaymentType.CASH:PaymentType.CARD,
                    selectedStudent.getStudentId(),
                    selectedCourse.getCourseId());
            boolean isSaved = registrationBo.create(registration);
            if(isSaved){
                new Alert(Alert.AlertType.INFORMATION,"Registration success...", ButtonType.CLOSE).show();
                clearFields();
            }else{
                new Alert(Alert.AlertType.WARNING,"Try again...", ButtonType.CLOSE).show();
            }

        }catch(SQLException | ClassNotFoundException e){
            new Alert(Alert.AlertType.ERROR,e.getMessage(), ButtonType.CLOSE).show();
        }
    }

    private void clearFields() {
        txtName.clear();
        txtEmail.clear();
        txtCourseFee.clear();
        txtCourseName.clear();

        cmbStudent.setValue(null);
        cmbCourse.setValue(null);

        selectedStudent=null;
        selectedCourse=null;
    }

    private void setUi(String location) throws IOException {
        URL resource = getClass().getResource("../view/"+location+".fxml");
        Stage stage = (Stage) context.getScene().getWindow();
        stage.centerOnScreen();
        stage.setScene(new Scene(FXMLLoader.load(resource)));
        stage.setTitle(location);
    }
}
