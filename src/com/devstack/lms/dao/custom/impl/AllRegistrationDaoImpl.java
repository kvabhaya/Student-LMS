package com.devstack.lms.dao.custom.impl;

import com.devstack.lms.dao.custom.CourseDao;
import com.devstack.lms.entity.Course;

import java.sql.SQLException;
import java.util.Collections;
import java.util.List;

public class AllRegistrationDaoImpl implements CourseDao {
    @Override
    public boolean create(Course course) throws SQLException, ClassNotFoundException {
        return false;
    }

    @Override
    public Course find(String s) throws SQLException, ClassNotFoundException {
        return null;
    }

    @Override
    public boolean update(Course course) throws SQLException, ClassNotFoundException {
        return false;
    }

    @Override
    public boolean delete(String s) throws SQLException, ClassNotFoundException {
        return false;
    }

    @Override
    public List<Course> findAll() throws SQLException, ClassNotFoundException {
        return Collections.emptyList();
    }

    @Override
    public List<Course> search(String searchText) throws SQLException, ClassNotFoundException {
        return Collections.emptyList();
    }
}
