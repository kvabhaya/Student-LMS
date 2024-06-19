package com.devstack.lms.dao.custom;

import com.devstack.lms.dao.CrudDao;
import com.devstack.lms.dao.SuperDao;
import com.devstack.lms.entity.User;

import java.sql.SQLException;

public interface UserDao extends CrudDao<User, String>, SuperDao {
    public boolean login(String username, String password) throws ClassNotFoundException, SQLException;
}
