package com.devstack.lms.dao.custom.impl;

import com.devstack.lms.dao.CrudUtil;
import com.devstack.lms.dao.custom.CourseDao;
import com.devstack.lms.db.DbConnection;
import com.devstack.lms.entity.Course;
import com.devstack.lms.entity.Student;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class CourseDaoImpl implements CourseDao {

    @Override
    public boolean create(Course course) throws SQLException, ClassNotFoundException {
        return CrudUtil.execute("INSERT INTO course VALUES (?,?,?)",
                course.getCourseId(),course.getCourseName(),course.getFee());
    }

    @Override
    public Course find(String c) throws SQLException, ClassNotFoundException {

        ResultSet resultSet = CrudUtil.execute("SELECT * FROM course WHERE course_id=?",c);

        if(resultSet.next()){
            return new Course(
                    resultSet.getString(1),
                    resultSet.getString(2),
                    resultSet.getDouble(3)
            );
        }
        return null;
    }

    @Override
    public boolean update(Course course) throws SQLException, ClassNotFoundException {
        return CrudUtil.execute("UPDATE course SET course_name=?, fee=? WHERE course_id=?",
                course.getCourseName(),course.getFee(),course.getCourseId());
    }

    @Override
    public boolean delete(String c) throws SQLException, ClassNotFoundException {
        return CrudUtil.execute("DELETE FROM course WHERE course_id=?",c);
    }

    @Override
    public List<Course> findAll() {
        return Collections.emptyList();
    }
//    public List<Course> findAll() throws SQLException, ClassNotFoundException {
//        ResultSet resultSet = CrudUtil.execute("SELECT * FROM course");
//        List<Course> courseList = new ArrayList<>();
//        while(resultSet.next()){
//            courseList.add(new Course(
//                    resultSet.getString(1),
//                    resultSet.getString(2),
//                    resultSet.getDouble(3)
//            ));
//        }
//        return courseList;
//    }

    @Override
    public List<Course> search(String searchText) throws SQLException, ClassNotFoundException {
        searchText="%"+searchText+"%";
        ResultSet resultSet = CrudUtil.execute("SELECT * FROM course WHERE course_name LIKE ?",
                searchText,searchText);

        List<Course> courseList=new ArrayList<>();
        while(resultSet.next()){
            courseList.add(new Course(
                    resultSet.getString(1),
                    resultSet.getString(2),
                    resultSet.getDouble(3)
            ));
        }
        return courseList;
    }
}
