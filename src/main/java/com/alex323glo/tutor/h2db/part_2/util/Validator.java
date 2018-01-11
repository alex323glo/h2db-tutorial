package com.alex323glo.tutor.h2db.part_2.util;

import com.alex323glo.tutor.h2db.part_2.exception.DAOException;
import com.alex323glo.tutor.h2db.part_2.exception.ValidationException;

import java.sql.Connection;
import java.sql.SQLException;

/**
 * Container for general static validation methods.
 * 
 * @author alex323glo 
 * @version 1.0
 */
public class Validator {

    /**
     * Validates SQL Connection. Checks if it is Null, closed or can't 
     * be accessed because of SQLException.
     * 
     * @param connection checked connection instance.
     * @throws ValidationException if connection is not valid.
     */
    public static void validate(Connection connection) throws ValidationException {
        if (connection == null) {
            throw new NullPointerException("SQL connection is null");
        }

        try {
            if (connection.isClosed()) {
                throw new DAOException("SQL connection was already closed");
            }
        } catch (SQLException e) {
            e.printStackTrace();
            throw new DAOException("can't use connection instance (SQLException)", e);
        }
    }
    
}
