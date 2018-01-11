package com.alex323glo.tutor.h2db.part_2.dao;

import com.alex323glo.tutor.h2db.part_2.model.response.Response;
import com.alex323glo.tutor.h2db.part_2.model.response.ResponseStatus;
import com.alex323glo.tutor.h2db.part_2.model.token.AccessToken;
import com.alex323glo.tutor.h2db.part_2.model.token.AccessTokenType;
import org.junit.*;

import java.sql.Connection;
import java.sql.DriverManager;

import static org.junit.Assert.*;

public class H2AccessTokenDAOTest {

    private DAO<String, AccessToken> accessTokenDAO;
    private Connection dbConnection;

    private static final String DB_URL =
            "jdbc:h2:/home/alex323glo/Java/Idea/h2db-tutorial/src/test/resources/test_access_token_db/test";
    private static final String DB_TABLE_NAME = "access_tokens";
    private static final String DB_USER = "user";
    private static final String DB_PASS = "pass";

    private static final String TEST_ACCESS_TOKEN = "test_access_token";
    private static final String TEST_USERNAME = "test_username";
    private static final AccessTokenType TEST_ACCESS_TOKEN_TYPE = AccessTokenType.USER;

    @BeforeClass
    public static void createTestTable() throws Exception {
        Connection tempConnection = DriverManager.getConnection(DB_URL, DB_USER, DB_PASS);
        tempConnection.createStatement().execute(
                "CREATE TABLE access_tokens(token VARCHAR(45), username VARCHAR(45), type VARCHAR (45));"
        );
    }

    @AfterClass
    public static void dropTestTable() throws Exception {
        Connection tempConnection = DriverManager.getConnection(DB_URL, DB_USER, DB_PASS);
        tempConnection.createStatement().execute("DROP TABLE access_tokens;");
    }

    @Before
    public void setUp() throws Exception {
        dbConnection = DriverManager.getConnection(DB_URL, DB_USER, DB_PASS);
        dbConnection.createStatement().execute("DELETE FROM " + DB_TABLE_NAME + ";");
        accessTokenDAO = new H2AccessTokenDAO(dbConnection, DB_TABLE_NAME);
    }

    @After
    public void tearDown() throws Exception {
        dbConnection.close();
    }

    @Test
    public void createWithOK() throws Exception {
        AccessToken accessToken = new AccessToken(TEST_ACCESS_TOKEN, TEST_USERNAME, TEST_ACCESS_TOKEN_TYPE);
        Response response = accessTokenDAO.create(accessToken.getToken(), accessToken);
        assertEquals(ResponseStatus.OK, response.getStatus());
    }

    @Test
    public void createWithKO() throws Exception {
        AccessToken accessToken = new AccessToken(TEST_ACCESS_TOKEN, TEST_USERNAME, TEST_ACCESS_TOKEN_TYPE);
        accessTokenDAO.create(accessToken.getToken(), accessToken);

        Response response = accessTokenDAO.create(accessToken.getToken(), accessToken);
        assertEquals(ResponseStatus.KO, response.getStatus());
    }

    @Test
    public void createWithException() throws Exception {
        // TODO finish Unit Test
    }

    @Test
    public void read() throws Exception {
        // TODO finish Unit Test
    }

    @Test
    public void update() throws Exception {
        // TODO finish Unit Test
    }

    @Test
    public void delete() throws Exception {
        // TODO finish Unit Test
    }

    @Test
    public void getKeyset() throws Exception {
        // TODO finish Unit Test
    }

    @Test
    public void getAll() throws Exception {
        // TODO finish Unit Test
    }

}