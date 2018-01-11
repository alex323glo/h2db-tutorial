package com.alex323glo.tutor.h2db.part_1;

import java.sql.*;

/**
 * Created by alex323glo on 11.01.18.
 */
public class ConnectH2 {

    public static void main(String[] args) throws Exception {
        Connection connection = createConnection();

        createTable(connection);
        insertData(connection);
        printAllData(connection);
    }

    private static Connection createConnection() throws SQLException {
        return DriverManager.getConnection(
                "jdbc:h2:/home/alex323glo/Java/Idea/h2db-tutorial/embedded_h2db/test",
                "sa",
                "");
    }

    private static void createTable(Connection connection) throws SQLException {
        Statement statement = connection.createStatement();

        statement.execute("CREATE TABLE test(" +
                "id INT AUTO_INCREMENT NOT NULL, " +
                "name VARCHAR(45)" + ")");
    }

    private static void insertData(Connection connection) throws SQLException {
        Statement statement = connection.createStatement();

        statement.execute(
                "INSERT INTO TEST VALUES(default,'HELLO')");
        statement.execute(
                "INSERT INTO TEST(NAME) VALUES('John')");

        PreparedStatement preparedStatement = connection.prepareStatement(
                "INSERT INTO TEST(NAME) VALUES(?)");
        preparedStatement.setString(1, "Jack");
        preparedStatement.execute();
    }

    private static void printAllData(Connection connection) throws SQLException {
        ResultSet resultSet = connection.createStatement().executeQuery(
                "SELECT * FROM TEST");

        while (resultSet.next()) {
            System.out.printf("ID: %s, NAME: %s\n",
                    resultSet.getString("ID"),
                    resultSet.getString("NAME"));
        }
    }

}
