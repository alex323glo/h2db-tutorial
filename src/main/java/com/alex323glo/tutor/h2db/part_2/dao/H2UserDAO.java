package com.alex323glo.tutor.h2db.part_2.dao;

import com.alex323glo.tutor.h2db.part_2.exception.DAOException;
import com.alex323glo.tutor.h2db.part_2.exception.ValidationException;
import com.alex323glo.tutor.h2db.part_2.model.response.Response;
import com.alex323glo.tutor.h2db.part_2.model.response.ResponseStatus;
import com.alex323glo.tutor.h2db.part_2.model.user.User;
import com.alex323glo.tutor.h2db.part_2.model.user.UserType;
import com.alex323glo.tutor.h2db.part_2.util.Validator;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Map;
import java.util.Set;

/**
 * User DAO implementation for H2 Data Base.
 *
 * @author alex323glo
 * @version 1.0
 *
 * @see DAO
 */
public class H2UserDAO implements DAO<String, User> {

    private Connection connection;
    private String tableName;

    private static final String SELECT_QUERY_TEMPLATE = "SELECT %s FROM %s";            // (matcher, table)
    private static final String INSERT_QUERY_TEMPLATE = "INSERT INTO %s VALUES(%s,%s)"; // (table, key, value)

    public H2UserDAO(Connection connection, String tableName) {
        this.setConnection(connection);
        this.setTableName(tableName);
    }

    public Connection getConnection() {
        return connection;
    }

    public void setConnection(Connection connection) throws DAOException {
        checkConnection(connection);
        this.connection = connection;
    }

    public String getTableName() {
        return tableName;
    }

    public void setTableName(String tableName) throws DAOException {
        if (tableName == null || tableName.length() < 1) {
            throw new DAOException("wrong tableName: " + tableName);
        }
        this.tableName = tableName;
    }

    /**
     * Creates new record.
     *
     * @param key   unique identifier of new record.
     * @param value data, which will be stored inside new record.
     * @return Response object with OK status (if operation was successful) and
     * Null body.
     * @throws DAOException if params don't pass validation or DAO has some problems
     *                      with execution of this part of logic (check cause of thrown DAOException).
     * @see Response
     * @see DAOException
     */
    @Override
    public Response create(String key, User value) throws DAOException {
        checkConnection(connection);

        try {
            Statement statement = connection.createStatement();

            statement.execute("SELECT * FROM " + tableName + " WHERE username=" + key + ";");
            if (statement.getResultSet().getString("username") != null) {
                return new Response(ResponseStatus.KO);
            }
            statement.close();

            statement.execute("INSERT INTO " + tableName + " VALUES(" +
                    key + ", " + value.getPassword() + ", " + value.getUserType().toString() + ");");

            return new Response(ResponseStatus.OK);
        } catch (SQLException e) {
            e.printStackTrace();
            throw new DAOException("can't execute SQL", e);
        }
    }

    /**
     * Reads existent record.
     *
     * @param key unique identifier of existent record.
     * @return Response object with OK status and not Null body (data, stored inside this
     * record), if operation was successful, or Response object with KO status and Null body,
     * if record with such key doesn't exist.
     * @throws DAOException if params don't pass validation or DAO has some problems
     *                      with execution of this part of logic (check cause of thrown DAOException).
     * @see Response
     * @see DAOException
     */
    @Override
    public Response<User> read(String key) throws DAOException {
        checkConnection(connection);

        try {
            Statement statement = connection.createStatement();

            statement.execute("SELECT * FROM " + tableName + " WHERE username=" + key + ";");
            ResultSet resultSet = statement.getResultSet();

            if (resultSet.getString("username") == null) {
                return new Response<User>(ResponseStatus.KO);
            }

            return new Response<User>(ResponseStatus.OK, new User(
                    resultSet.getString("username"),
                    resultSet.getString("password"),
                    resultSet.getString("type").toLowerCase().equals("root")
                            ? UserType.ROOT : UserType.USER)
            );

        } catch (SQLException e) {
            e.printStackTrace();
            throw new DAOException("can't carry out SQL", e);
        }
    }

    /**
     * Updates data of existent record.
     *
     * @param key   unique identifier of existent record.
     * @param value new data, which will be stored inside existent record.
     * @return Response object with OK status and not Null body (old data,
     * stored inside existent record before this update() operation was carried out),
     * if operation was successful, or Response object with KO status and Null body,
     * if record with such key doesn't exist.
     * @throws DAOException if params don't pass validation or DAO has some problems
     *                      with execution of this part of logic (check cause of thrown DAOException).
     * @see Response
     * @see DAOException
     */
    @Override
    public Response<User> update(String key, User value) throws DAOException {
        throw new UnsupportedOperationException();
    }

    /**
     * Deletes existent record.
     *
     * @param key unique identifier of existent record.
     * @return Response object with OK status and not Null body (old data,
     * stored inside existent record before this delete() operation was carried out),
     * if operation was successful, or Response object with KO status and Null body,
     * if record with such key doesn't exist.
     * @throws DAOException if params don't pass validation or DAO has some problems
     *                      with execution of this part of logic (check cause of thrown DAOException).
     * @see Response
     * @see DAOException
     */
    @Override
    public Response<User> delete(String key) throws DAOException {
        throw new UnsupportedOperationException();
    }

    /**
     * Gets Set of keys of all existent records.
     *
     * @return not Null Set of unique records' identifiers.
     * @throws DAOException if params don't pass validation or DAO has some problems
     *                      with execution of this part of logic (check cause of thrown DAOException).
     * @see DAOException
     */
    @Override
    public Set<String> getKeyset() throws DAOException {
        throw new UnsupportedOperationException();
    }

    /**
     * Get all existent records as Map of K-V records.
     *
     * @return not Null Map, made of key-value records.
     * @throws DAOException if params don't pass validation or DAO has some problems
     *                      with execution of this part of logic (check cause of thrown DAOException).
     * @see DAOException
     */
    @Override
    public Map<String, User> getAll() throws DAOException {
        throw new UnsupportedOperationException();
    }

    private static void checkConnection(Connection connection) throws DAOException {
        try {
            Validator.validate(connection);
        } catch (ValidationException e) {
            e.printStackTrace();
            throw new DAOException("connection is not valid", e);
        }
    }
}
