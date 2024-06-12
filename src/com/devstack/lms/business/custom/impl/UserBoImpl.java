package com.devstack.lms.business.custom.impl;

import com.devstack.lms.business.custom.UserBo;
import com.devstack.lms.dto.UserDto;

import java.sql.SQLException;

public class UserBoImpl implements UserBo {

    @Override
    public boolean create(UserDto dto) throws SQLException, ClassNotFoundException {
        return false;
    }
}
