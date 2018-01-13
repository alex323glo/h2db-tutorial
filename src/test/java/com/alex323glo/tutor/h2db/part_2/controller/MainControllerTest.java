package com.alex323glo.tutor.h2db.part_2.controller;

import com.alex323glo.tutor.h2db.part_2.dao.DAO;
import com.alex323glo.tutor.h2db.part_2.dao.H2AccessTokenDAO;
import com.alex323glo.tutor.h2db.part_2.dao.H2UserDAO;
import com.alex323glo.tutor.h2db.part_2.model.response.ResponseStatus;
import com.alex323glo.tutor.h2db.part_2.model.token.AccessToken;
import com.alex323glo.tutor.h2db.part_2.model.user.User;
import com.alex323glo.tutor.h2db.part_2.model.user.UserType;
import com.google.gson.Gson;
import org.junit.*;

import java.sql.Connection;
import java.sql.DriverManager;
import java.util.HashMap;
import java.util.Map;

import static org.junit.Assert.*;

public class MainControllerTest {

    private MainController mainController;
    private DAO<String, User> userDAO;
    private DAO<String, AccessToken> accessTokenDAO;
    private Connection userDBConnection;
    private Connection accessTokenDBConnection;


    private static final String USER_DB_URL =
            "jdbc:h2:/home/alex323glo/Java/Idea/h2db-tutorial/src/test/resources/test_user_db/test";
    private static final String USER_DB_TABLE_NAME = "users";
    private static final String ACCESS_TOKEN_DB_URL =
            "jdbc:h2:/home/alex323glo/Java/Idea/h2db-tutorial/src/test/resources/test_access_token_db/test";
    private static final String ACCESS_TOKEN_DB_TABLE_NAME = "access_tokens";
    private static final String DB_USER = "user";
    private static final String DB_PASS = "pass";

    private static final String TEST_USERNAME = "test_username";
    private static final String TEST_PASSWORD = "test_password";
    private static final UserType TEST_USERTYPE = UserType.ROOT;
    private static final String TEST_ACCESS_TOKEN = "test_access_token";

    @BeforeClass
    public static void createTestTable() throws Exception {
        Connection tempUserDBConnection = DriverManager.getConnection(USER_DB_URL, DB_USER, DB_PASS);
        tempUserDBConnection.createStatement().execute(
                "CREATE TABLE users(username VARCHAR(45), password VARCHAR(45), TYPE VARCHAR(45));"
        );

        Connection tempAccessTokenDBConnection = DriverManager.getConnection(ACCESS_TOKEN_DB_URL, DB_USER, DB_PASS);
        tempAccessTokenDBConnection.createStatement().execute(
                "CREATE TABLE access_tokens(token VARCHAR(45), username VARCHAR(45), type VARCHAR (45));"
        );
    }

    @AfterClass
    public static void dropTestTable() throws Exception {
        Connection tempUserDBConnection = DriverManager.getConnection(USER_DB_URL, DB_USER, DB_PASS);
        tempUserDBConnection.createStatement().execute("DROP TABLE users;");

        Connection tempAccessTokenDBConnection = DriverManager.getConnection(ACCESS_TOKEN_DB_URL, DB_USER, DB_PASS);
        tempAccessTokenDBConnection.createStatement().execute("DROP TABLE access_tokens;");
    }

    @Before
    public void setUp() throws Exception {
        userDBConnection = DriverManager.getConnection(USER_DB_URL, DB_USER, DB_PASS);
        userDBConnection.createStatement().execute("DELETE FROM " + USER_DB_TABLE_NAME + ";");
        userDAO = new H2UserDAO(userDBConnection, USER_DB_TABLE_NAME);

        accessTokenDBConnection = DriverManager.getConnection(ACCESS_TOKEN_DB_URL, DB_USER, DB_PASS);
        accessTokenDBConnection.createStatement().execute("DELETE FROM " + ACCESS_TOKEN_DB_TABLE_NAME + ";");
        accessTokenDAO = new H2AccessTokenDAO(accessTokenDBConnection, ACCESS_TOKEN_DB_TABLE_NAME);

        mainController = new MainControllerImpl(userDAO, accessTokenDAO);
    }

    @After
    public void tearDown() throws Exception {
        userDBConnection.close();
        accessTokenDBConnection.close();
    }

    @Test
    public void registerSuccessfully() throws Exception {
        User user = new User(TEST_USERNAME, TEST_PASSWORD);

        String accessTokenStr = mainController.register(user.getUsername(), user.getPassword());

        AccessToken accessToken = new AccessToken(accessTokenStr, TEST_USERNAME);

        assertNotNull(accessTokenStr);
        assertEquals(user, userDAO.read(TEST_USERNAME).getBody());
        assertEquals(accessToken, accessTokenDAO.read(accessTokenStr).getBody());
    }

    // TODO add test for unsuccessful registration

    @Test
    public void login() throws Exception {
        mainController.register(TEST_USERNAME, TEST_PASSWORD);

        String accessTokenStr = mainController.login(TEST_USERNAME, TEST_PASSWORD);

        AccessToken accessToken = new AccessToken(accessTokenStr, TEST_USERNAME);

        assertNotNull(accessTokenStr);
        assertEquals(accessToken, accessTokenDAO.read(accessTokenStr).getBody());
    }

    // TODO add test for unsuccessful login

    @Test
    public void logout() throws Exception {
        String accessTokenStr = mainController.register(TEST_USERNAME, TEST_PASSWORD);
        assertNotNull(accessTokenStr);

        boolean logoutResult = mainController.logout(accessTokenStr);

        assertTrue(logoutResult);
        assertEquals(ResponseStatus.KO, accessTokenDAO.read(accessTokenStr).getStatus());
    }

    // TODO add test for unsuccessful logout

    @Test
    public void getUserInfoAsJSON() throws Exception {
        User user = new User(TEST_USERNAME, TEST_PASSWORD, TEST_USERTYPE);
        AccessToken accessToken = new AccessToken(TEST_ACCESS_TOKEN, TEST_USERNAME, TEST_USERTYPE);
        userDAO.create(user.getUsername(), user);
        accessTokenDAO.create(accessToken.getToken(), accessToken);

        String expectedUserInfoJSON = new Gson().toJson(user, User.class);

        String actualUserInfoJSON = mainController.getUserInfoAsJSON(accessToken.getToken());

        assertEquals(expectedUserInfoJSON, actualUserInfoJSON);
    }

    // TODO add test for unsuccessful getUserInfoAsJSON

    @Test
    public void getAllUsersAsJSON() throws Exception {
        User rootUser = new User(TEST_USERNAME + "0", TEST_PASSWORD, TEST_USERTYPE);
        User user1 = new User(TEST_USERNAME + "1", TEST_PASSWORD, UserType.USER);
        User user2 = new User(TEST_USERNAME + "2", TEST_PASSWORD, UserType.USER);
        AccessToken accessToken = new AccessToken(TEST_ACCESS_TOKEN, rootUser.getUsername(), UserType.ROOT);

        userDAO.create(rootUser.getUsername(), rootUser);
        userDAO.create(user1.getUsername(), user1);
        userDAO.create(user2.getUsername(), user2);
        accessTokenDAO.create(accessToken.getToken(), accessToken);

        Map<String, User> userMap = new HashMap<>();
        userMap.put(rootUser.getUsername(), rootUser);
        userMap.put(user1.getUsername(), user1);
        userMap.put(user2.getUsername(), user2);

        String expectedAllUsersInfoJSON = new Gson().toJson(userMap, Map.class);

        String actualAllUsersInfoJSON = mainController.getAllUsersAsJSON(accessToken.getToken());

        assertEquals(expectedAllUsersInfoJSON, actualAllUsersInfoJSON);
    }

    // TODO add test for unsuccessful getAllUsersAsJSON

}