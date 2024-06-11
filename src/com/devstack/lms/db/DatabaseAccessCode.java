package com.devstack.lms.db;

import com.devstack.lms.entity.*;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class DatabaseAccessCode {
    //Student

    //user
    public boolean signUp(User user) throws ClassNotFoundException, SQLException {
        String sql = "INSERT INTO user VALUES (?,?,?)";
        PreparedStatement preparedStatement = DbConnection.getInstance().getConnection().prepareStatement(sql);
        preparedStatement.setString(1, user.getUserId());
        preparedStatement.setString(2, user.getUsername());
        preparedStatement.setString(3, user.getPassword());

        return preparedStatement.executeUpdate()>0;
    }

    public boolean login(String username, String password) throws ClassNotFoundException, SQLException {
        String sql = "SELECT * FROM user WHERE username = '" + username +"' AND password = '" + password +"'";
        Statement statement = DbConnection.getInstance().getConnection().createStatement();
        ResultSet resultSet = statement.executeQuery(sql);

        if(resultSet.next()){
            return true;
        }
        return false;
    }

    //course
    public boolean saveCourse(Course course) throws ClassNotFoundException, SQLException {
        String sql = "INSERT INTO course VALUES (?,?,?)";
        PreparedStatement preparedStatement = DbConnection.getInstance().getConnection().prepareStatement(sql);
        preparedStatement.setString(1, course.getCourseId());
        preparedStatement.setString(2, course.getCourseName());
        preparedStatement.setDouble(3, course.getFee());


        return preparedStatement.executeUpdate()>0;
    }

    public List<Course> findAllCourses(String searchText) throws ClassNotFoundException, SQLException {
        String sql = "SELECT * FROM course";
        PreparedStatement preparedStatement = DbConnection.getInstance().getConnection().prepareStatement(sql);

        ResultSet resultSet = preparedStatement.executeQuery();
        List<Course> courseList = new ArrayList<>();
        while(resultSet.next()){
            courseList.add(new Course(
                    resultSet.getString(1),
                    resultSet.getString(2),
                    resultSet.getDouble(3)
            ));
        }
        return courseList;
    }

    public Course findCourse(String course_id) throws ClassNotFoundException, SQLException {
        String sql = "SELECT * FROM course WHERE course_id=?";
        PreparedStatement preparedStatement = DbConnection.getInstance().getConnection().prepareStatement(sql);
        preparedStatement.setString(1,course_id);

        ResultSet resultSet = preparedStatement.executeQuery();

        if(resultSet.next()){
            return new Course(
                    resultSet.getString(1),
                    resultSet.getString(2),
                    resultSet.getDouble(3)
            );
        }
        return null;
    }

    public boolean deleteCourse(String courseId) throws SQLException, ClassNotFoundException {
        String sql = "DELETE FROM course WHERE course_id=?";
        PreparedStatement preparedStatement = DbConnection.getInstance().getConnection().prepareStatement(sql);
        preparedStatement.setString(1, courseId);

        return preparedStatement.executeUpdate()>0;
    }

    public boolean updateCourse(Course course) throws ClassNotFoundException, SQLException {
        String sql = "UPDATE course SET course_name=?, fee=? WHERE course_id=?";
        PreparedStatement preparedStatement = DbConnection.getInstance().getConnection().prepareStatement(sql);

        preparedStatement.setString(1, course.getCourseName());
        preparedStatement.setDouble(2, course.getFee());
        preparedStatement.setString(3, course.getCourseId());

        return preparedStatement.executeUpdate()>0;
    }

    //Registration
    public boolean register(Registration registration) throws ClassNotFoundException, SQLException {
        String sql = "INSERT INTO registration VALUES (?,?,?,?,?,?)";
        PreparedStatement preparedStatement = DbConnection.getInstance().getConnection().prepareStatement(sql);
        preparedStatement.setString(1, registration.getRegisterId());
        preparedStatement.setObject(2, registration.getDate());
        preparedStatement.setObject(3, registration.getNic());
        preparedStatement.setString(4, registration.getPaymentType().name());
        preparedStatement.setString(5, registration.getStudent());
        preparedStatement.setString(6, registration.getCourse());


        return preparedStatement.executeUpdate()>0;
    }

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
