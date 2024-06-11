package com.devstack.lms.db;

import com.devstack.lms.entity.*;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class DatabaseAccessCode {
    //Student
    //user
    //course
    //Registration
    //All Registration
    public List<AllRegistrations> loadAllDetails(String courseId) throws ClassNotFoundException, SQLException {
        String sql = "SELECT r.registered_date, s.student_name, r.paymentType " +
                "FROM registration r " +
                "JOIN student s ON r.student = s.student_id " +
                "WHERE r.course = ?";
        PreparedStatement preparedStatement = DbConnection.getInstance().getConnection().prepareStatement(sql);
        preparedStatement.setString(1, courseId);

        ResultSet resultSet = preparedStatement.executeQuery();
        List<AllRegistrations> registrationList = new ArrayList<>();
        while (resultSet.next()) {
            registrationList.add(new AllRegistrations(
                    resultSet.getString(1),
                    resultSet.getString(2),
                    resultSet.getString(3)
            ));
        }
        return registrationList;
    }
    //Registration Detail


}
