package com.alex323glo.tutor.h2db.part_2.dao;

import com.alex323glo.tutor.h2db.part_2.model.response.Response;
import com.alex323glo.tutor.h2db.part_2.model.response.ResponseStatus;
import com.alex323glo.tutor.h2db.part_2.model.token.AccessToken;
import com.alex323glo.tutor.h2db.part_2.model.user.UserType;
import org.junit.*;

import java.sql.Connection;
import java.sql.DriverManager;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

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
    private static final UserType TEST_ACCESS_TOKEN_TYPE = UserType.USER;

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
    public void readWithOK() throws Exception {
        AccessToken accessToken = new AccessToken(TEST_ACCESS_TOKEN, TEST_USERNAME, TEST_ACCESS_TOKEN_TYPE);
        accessTokenDAO.create(accessToken.getToken(), accessToken);

        Response<AccessToken> response = accessTokenDAO.read(TEST_ACCESS_TOKEN);
        assertEquals(ResponseStatus.OK, response.getStatus());
        assertEquals(accessToken, response.getBody());
    }


    @Test
    public void readWithKO() throws Exception {
        Response<AccessToken> response = accessTokenDAO.read(TEST_ACCESS_TOKEN);
        assertEquals(ResponseStatus.KO, response.getStatus());
    }

    @Test
    public void updateWithOK() throws Exception {
        AccessToken accessToken = new AccessToken(TEST_ACCESS_TOKEN, TEST_USERNAME, TEST_ACCESS_TOKEN_TYPE);
        accessTokenDAO.create(accessToken.getToken(), accessToken);

        AccessToken newAccessToken = new AccessToken(
                TEST_ACCESS_TOKEN + "1",
                TEST_USERNAME + "1",
                TEST_ACCESS_TOKEN_TYPE.equals(UserType.USER) ? UserType.ROOT : UserType.USER
        );
        Response<AccessToken> updateResponse = accessTokenDAO.update(TEST_ACCESS_TOKEN, newAccessToken);

        assertEquals(ResponseStatus.OK, updateResponse.getStatus());
        assertEquals(accessToken, updateResponse.getBody());

        Response<AccessToken> readResponse = accessTokenDAO.read(newAccessToken.getToken());

        assertEquals(newAccessToken, readResponse.getBody());
    }

    @Test
    public void updateWithKO() throws Exception {
        AccessToken accessToken = new AccessToken(TEST_ACCESS_TOKEN, TEST_USERNAME, TEST_ACCESS_TOKEN_TYPE);
        Response<AccessToken> updateResponse = accessTokenDAO.update(TEST_ACCESS_TOKEN, accessToken);
        assertEquals(ResponseStatus.KO, updateResponse.getStatus());
    }

    @Test
    public void deleteWithOK() throws Exception {
        AccessToken accessToken = new AccessToken(TEST_ACCESS_TOKEN, TEST_USERNAME, TEST_ACCESS_TOKEN_TYPE);
        accessTokenDAO.create(accessToken.getToken(), accessToken);

        Response<AccessToken> response = accessTokenDAO.update(TEST_ACCESS_TOKEN, accessToken);

        assertEquals(ResponseStatus.OK, response.getStatus());
        assertEquals(accessToken, response.getBody());
    }

    @Test
    public void deleteWithKO() throws Exception {
        Response<AccessToken> response = accessTokenDAO.delete(TEST_ACCESS_TOKEN);
        assertEquals(ResponseStatus.KO, response.getStatus());
    }

    @Test
    public void getKeysetFromNotEmptyTable() throws Exception {
        AccessToken accessToken1 = new AccessToken(TEST_ACCESS_TOKEN + "1", TEST_USERNAME,
                TEST_ACCESS_TOKEN_TYPE);
        AccessToken accessToken2 = new AccessToken(TEST_ACCESS_TOKEN + "2", TEST_USERNAME,
                TEST_ACCESS_TOKEN_TYPE);
        accessTokenDAO.create(accessToken1.getToken(), accessToken1);
        accessTokenDAO.create(accessToken2.getToken(), accessToken2);

        Set<String> expectedKeySet = new HashSet<>();
        expectedKeySet.add(accessToken1.getToken());
        expectedKeySet.add(accessToken2.getToken());

        Set<String> actualKeySet = accessTokenDAO.getKeyset();

        assertEquals(expectedKeySet, actualKeySet);
    }

    @Test
    public void getKeysetFromEmptyTable() throws Exception {
        Set<String> expectedEmptyKeySet = new HashSet<>();
        Set<String> actualKeySet = accessTokenDAO.getKeyset();
        assertEquals(expectedEmptyKeySet, actualKeySet);
    }

    @Test
    public void getAllFromNotEmptyTable() throws Exception {
        AccessToken accessToken1 = new AccessToken(TEST_ACCESS_TOKEN + "1", TEST_USERNAME,
                TEST_ACCESS_TOKEN_TYPE);
        AccessToken accessToken2 = new AccessToken(TEST_ACCESS_TOKEN + "2", TEST_USERNAME,
                TEST_ACCESS_TOKEN_TYPE);
        accessTokenDAO.create(accessToken1.getToken(), accessToken1);
        accessTokenDAO.create(accessToken2.getToken(), accessToken2);

        Map<String, AccessToken> expectedMap = new HashMap<>();
        expectedMap.put(accessToken1.getToken(), accessToken1);
        expectedMap.put(accessToken2.getToken(), accessToken2);

        Map<String, AccessToken> actualMap = accessTokenDAO.getAll();

        assertEquals(expectedMap, actualMap);
    }

    @Test
    public void getAllFromEmptyTable() throws Exception {
        Map<String, AccessToken> expectedEmptyMap = new HashMap<>();
        Map<String, AccessToken> actualMap = accessTokenDAO.getAll();
        assertEquals(expectedEmptyMap, actualMap);
    }
}