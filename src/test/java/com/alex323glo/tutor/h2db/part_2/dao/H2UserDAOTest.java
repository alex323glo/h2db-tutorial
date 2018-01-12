package com.alex323glo.tutor.h2db.part_2.dao;

import com.alex323glo.tutor.h2db.part_2.model.response.Response;
import com.alex323glo.tutor.h2db.part_2.model.response.ResponseStatus;
import com.alex323glo.tutor.h2db.part_2.model.user.User;
import com.alex323glo.tutor.h2db.part_2.model.user.UserType;
import org.junit.*;

import java.sql.Connection;
import java.sql.DriverManager;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import static org.junit.Assert.*;

public class H2UserDAOTest {

    private DAO<String, User> userDAO;
    private Connection dbConnection;

    private static final String DB_URL =
            "jdbc:h2:/home/alex323glo/Java/Idea/h2db-tutorial/src/test/resources/test_user_db/test";
    private static final String DB_TABLE_NAME = "users";
    private static final String DB_USER = "user";
    private static final String DB_PASS = "pass";

    private static final String TEST_USERNAME = "test_username";
    private static final String TEST_PASSWORD = "test_password";
    private static final UserType TEST_USERTYPE = UserType.USER;

    @BeforeClass
    public static void createTestTable() throws Exception {
        Connection tempConnection = DriverManager.getConnection(DB_URL, DB_USER, DB_PASS);
        tempConnection.createStatement().execute(
                "CREATE TABLE users(username VARCHAR(45), password VARCHAR(45), TYPE VARCHAR(45));"
        );
    }

    @AfterClass
    public static void dropTestTable() throws Exception {
        Connection tempConnection = DriverManager.getConnection(DB_URL, DB_USER, DB_PASS);
        tempConnection.createStatement().execute("DROP TABLE users;");
    }

    @Before
    public void setUp() throws Exception {
        dbConnection = DriverManager.getConnection(DB_URL, DB_USER, DB_PASS);
        dbConnection.createStatement().execute("DELETE FROM " + DB_TABLE_NAME + ";");
        userDAO = new H2UserDAO(dbConnection, DB_TABLE_NAME);
    }

    @After
    public void tearDown() throws Exception {
        dbConnection.close();
    }

    @Test
    public void createWithOK() throws Exception {
        User user = new User(TEST_USERNAME, TEST_PASSWORD, TEST_USERTYPE);
        Response response = userDAO.create(user.getUsername(), user);
        assertEquals(ResponseStatus.OK, response.getStatus());
    }

    @Test
    public void createWithKO() throws Exception {
        User user = new User(TEST_USERNAME, TEST_PASSWORD, TEST_USERTYPE);
        userDAO.create(user.getUsername(), user);

        Response response = userDAO.create(user.getUsername(), user);
        assertEquals(ResponseStatus.KO, response.getStatus());
    }

    @Test
    public void readWithOK() throws Exception {
        User user = new User(TEST_USERNAME, TEST_PASSWORD, TEST_USERTYPE);
        userDAO.create(user.getUsername(), user);

        Response<User> response = userDAO.read(TEST_USERNAME);
        assertEquals(ResponseStatus.OK, response.getStatus());
        assertEquals(user, response.getBody());
    }


    @Test
    public void readWithKO() throws Exception {
        Response<User> response = userDAO.read(TEST_USERNAME);
        assertEquals(ResponseStatus.KO, response.getStatus());
    }

    @Test
    public void updateWithOK() throws Exception {
        User user = new User(TEST_USERNAME, TEST_PASSWORD, TEST_USERTYPE);
        userDAO.create(user.getUsername(), user);

        User newUser = new User(
                TEST_USERNAME + "1",
                TEST_PASSWORD + "1",
                TEST_USERTYPE.equals(UserType.USER) ? UserType.ROOT : UserType.USER
        );
        Response<User> updateResponse = userDAO.update(TEST_USERNAME, newUser);

        assertEquals(ResponseStatus.OK, updateResponse.getStatus());
        assertEquals(user, updateResponse.getBody());

        Response<User> readResponse = userDAO.read(newUser.getUsername());

        assertEquals(newUser, readResponse.getBody());
    }

    @Test
    public void updateWithKO() throws Exception {
        User user = new User(TEST_USERNAME, TEST_PASSWORD, TEST_USERTYPE);
        Response<User> updateResponse = userDAO.update(TEST_USERNAME, user);
        assertEquals(ResponseStatus.KO, updateResponse.getStatus());
    }

    @Test
    public void deleteWithOK() throws Exception {
        User user = new User(TEST_USERNAME, TEST_PASSWORD, TEST_USERTYPE);
        userDAO.create(user.getUsername(), user);

        Response<User> updateResponse = userDAO.delete(TEST_USERNAME);

        assertEquals(ResponseStatus.OK, updateResponse.getStatus());
        assertEquals(user, updateResponse.getBody());
    }

    @Test
    public void deleteWithKO() throws Exception {
        Response<User> updateResponse = userDAO.delete(TEST_USERNAME);
        assertEquals(ResponseStatus.KO, updateResponse.getStatus());
    }

    @Test
    public void getKeysetFromNotEmptyTable() throws Exception {
        User user1 = new User(TEST_USERNAME + "1", TEST_PASSWORD, TEST_USERTYPE);
        User user2 = new User(TEST_USERNAME + "2", TEST_PASSWORD, TEST_USERTYPE);
        userDAO.create(user1.getUsername(), user1);
        userDAO.create(user2.getUsername(), user2);

        Set<String> expectedKeySet = new HashSet<>();
        expectedKeySet.add(user1.getUsername());
        expectedKeySet.add(user2.getUsername());

        Set<String> actualKeySet = userDAO.getKeyset();

        assertEquals(expectedKeySet, actualKeySet);
    }

    @Test
    public void getKeysetFromEmptyTable() throws Exception {
        Set<String> expectedEmptyKeySet = new HashSet<>();
        Set<String> actualKeySet = userDAO.getKeyset();
        assertEquals(expectedEmptyKeySet, actualKeySet);
    }

    @Test
    public void getAllFromNotEmptyTable() throws Exception {
        User user1 = new User(TEST_USERNAME + "1", TEST_PASSWORD, TEST_USERTYPE);
        User user2 = new User(TEST_USERNAME + "2", TEST_PASSWORD, TEST_USERTYPE);
        userDAO.create(user1.getUsername(), user1);
        userDAO.create(user2.getUsername(), user2);

        Map<String, User> expectedMap = new HashMap<>();
        expectedMap.put(user1.getUsername(), user1);
        expectedMap.put(user2.getUsername(), user2);

        Map<String, User> actualMap = userDAO.getAll();

        assertEquals(expectedMap, actualMap);
    }

    @Test
    public void getAllFromEmptyTable() throws Exception {
        Map<String, User> expectedEmptyMap = new HashMap<>();
        Map<String, User> actualMap = userDAO.getAll();
        assertEquals(expectedEmptyMap, actualMap);
    }

}