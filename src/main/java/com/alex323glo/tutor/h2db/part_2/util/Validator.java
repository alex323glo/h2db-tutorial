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
     *
     * @see Connection
     * @see ValidationException
     */
    public static void validateSQLConnection(Connection connection) throws ValidationException {
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

    /**
     * Checks (validates) DAO connection to SQL DB.
     *
     * @param connection SQL connection to DB.
     * @throws DAOException if connection is null or is not valid to use.
     *
     * @see Connection
     * @see DAOException
     */
    public static void checkDAOConnection(Connection connection) throws DAOException {
        try {
            Validator.validateSQLConnection(connection);
        } catch (ValidationException e) {
            e.printStackTrace();
            throw new DAOException("connection is not valid", e);
        }
    }

    /**
     * Validates String identifier.
     *
     * @param id identifier value.
     * @throws ValidationException if id is Null.
     *
     * @see ValidationException
     */
    public static void validateId(String id) throws ValidationException {
        if (id == null) {
            throw new ValidationException("id is null");
        }
    }

    /**
     * Validates String identifier. Uses validateId(String) method
     * to check if id is Null.
     *
     * @param id identifier value.
     * @param requiredLength identifier length matcher.
     * @throws ValidationException if id is Null or its length is not equal to required.
     *
     * @see ValidationException
     */
    public static void validateId(String id, int requiredLength) throws ValidationException {
        validateId(id);
        if (id.length() != requiredLength) {
            throw new ValidationException("id length doesn't match requirements");
        }
    }

    /**
     * Validates not-Null value.
     *
     * @param value value, needed to check.
     * @throws ValidationException if value is Null.
     *
     * @see ValidationException
     */
    public static void validateNotNull(Object value) throws ValidationException {
        if (value == null) {
            throw new ValidationException("value is null");
        }
    }
    
}
