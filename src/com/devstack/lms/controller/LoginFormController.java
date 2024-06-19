package com.devstack.lms.controller;

import com.devstack.lms.business.BoFactory;
import com.devstack.lms.business.custom.UserBo;
import com.devstack.lms.db.DatabaseAccessCode;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import javafx.stage.Window;

import java.io.Console;
import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;

public class LoginFormController {
    public AnchorPane context;
    public TextField txtUsername;
    public PasswordField txtPassword;

    private final UserBo userBo = BoFactory.getBo(BoFactory.BoType.USER);

    public void openDashboardOnAction(ActionEvent actionEvent) throws IOException {
//        DatabaseAccessCode databaseAccessCode = new DatabaseAccessCode();
        try {
            boolean islogedIn = userBo.login(
                    txtUsername.getText(),
                    txtPassword.getText()
            );
            if(islogedIn){
                System.out.println("Logged");
                setUi("DashboardForm");
            }else{
                System.out.println("Try again..");
            }
        } catch (ClassNotFoundException | SQLException e) {
            throw new RuntimeException(e);
        }

    }

    public void navigateToRegisterFormOnAction(ActionEvent actionEvent) throws IOException {
        setUi("SignupForm");
    }

    private void setUi(String location) throws IOException {
        URL resource = getClass().getResource("../view/"+location+".fxml");
        Stage stage = (Stage) context.getScene().getWindow();
        stage.centerOnScreen();
        stage.setScene(new Scene(FXMLLoader.load(resource)));
        stage.setTitle(location);
    }
}
