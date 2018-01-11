package com.alex323glo.tutor.h2db.part_2.controller;

import com.alex323glo.tutor.h2db.part_2.dao.DAO;
import com.alex323glo.tutor.h2db.part_2.exception.AppException;
import com.alex323glo.tutor.h2db.part_2.exception.DAOException;
import com.alex323glo.tutor.h2db.part_2.exception.ValidationException;
import com.alex323glo.tutor.h2db.part_2.model.response.Response;
import com.alex323glo.tutor.h2db.part_2.model.response.ResponseStatus;
import com.alex323glo.tutor.h2db.part_2.model.token.AccessToken;
import com.alex323glo.tutor.h2db.part_2.model.user.User;

import static com.alex323glo.tutor.h2db.part_2.util.Validator.*;
import static com.alex323glo.tutor.h2db.part_2.util.Generator.*;

/**
 * Main Application controller implementation.
 *
 * @author alex323glo
 * @version 1.0
 *
 * @see MainController
 */
public class MainControllerImpl implements MainController {

    private DAO<String, User> userDAO;
    private DAO<String, AccessToken> accessTokenDAO;

    public MainControllerImpl(DAO<String, User> userDAO, DAO<String, AccessToken> accessTokenDAO) {
        setUserDAO(userDAO);
        setAccessTokenDAO(accessTokenDAO);
    }

    public DAO<String, User> getUserDAO() {
        return userDAO;
    }

    public void setUserDAO(DAO<String, User> userDAO) {
        if (userDAO == null) {
            throw new NullPointerException("userDAO is null");
        }
        this.userDAO = userDAO;
    }

    public DAO<String, AccessToken> getAccessTokenDAO() {
        return accessTokenDAO;
    }

    public void setAccessTokenDAO(DAO<String, AccessToken> accessTokenDAO) {
        if (accessTokenDAO == null) {
            throw new NullPointerException("accessTokenDAO is null");
        }
        this.accessTokenDAO = accessTokenDAO;
    }

    /**
     * Registers new user in system.
     *
     * @param username unique identifier for new user.
     * @param password password for new user.
     * @return String access token of registered (and automatically authorised) user.
     * @throws AppException if params don't pass validation or service has some problems
     *                      with execution of this part of logic (check cause of thrown AppException).
     * @see AppException
     */
    @Override
    public String register(String username, String password) throws AppException {
        try {
            validateId(username);
            validateNotNull(password);
        } catch (ValidationException e) {
            e.printStackTrace();
            throw new AppException("arguments didn't pass validation", e);
        }

        User newUser = new User(username, password);

        try {
            Response response = userDAO.create(newUser.getUsername(), newUser);
            if (response.getStatus().equals(ResponseStatus.KO)) {
                // TODO row below could be replaced with logger
                System.out.printf("MainController: User \"" + username + "\" already exists!");
                return null;
            }

            String newToken = generateUniqueToken(accessTokenDAO.getKeyset());
            AccessToken newAccessToken = new AccessToken(newToken, newUser.getUsername());
            accessTokenDAO.create(newAccessToken.getToken(), newAccessToken);

            return newToken;
        } catch (DAOException e) {
            e.printStackTrace();
            throw new AppException("can't ork with DAO", e);
        }
    }

    /**
     * Authorises registered user in system.
     *
     * @param username unique identifier of existent user.
     * @param password password of this existent user.
     * @return String access token of authorised user.
     * @throws AppException if params don't pass validation or service has some problems
     *                      with execution of this part of logic (check cause of thrown AppException).
     * @see AppException
     */
    @Override
    public String login(String username, String password) throws AppException {
        // TODO finish implementation
        return null;
    }

    /**
     * Removes access token from list (in system) of authorised users.
     *
     * @param accessToken token, which will be removed from system.
     * @throws AppException if params don't pass validation or service has some problems
     *                      with execution of this part of logic (check cause of thrown AppException).
     */
    @Override
    public void logout(String accessToken) throws AppException {
        // TODO finish implementation
    }

    /**
     * Returns JSON object of personal single user data record (accessed by unique USER access token)
     * converted to String.
     *
     * @param accessToken unique access token, which gives access to concrete (single)
     *                    record of user data, linked to this access token after authorisation..
     * @return JSON object, represented as String.
     * @throws AppException if params don't pass validation or service has some problems
     *                      with execution of this part of logic (check cause of thrown AppException).
     */
    @Override
    public String getUserInfoAsJSON(String accessToken) throws AppException {
        // TODO finish implementation
        return null;
    }

    /**
     * Returns JSON object of personal all user data records (accessed by unique ROOT access token)
     * converted to String.
     *
     * @param accessToken unique access token, which gives access to all records
     *                    of user data (gives root access).
     * @return JSON object, represented as String.
     * @throws AppException if params don't pass validation or service has some problems
     *                      with execution of this part of logic (check cause of thrown AppException).
     */
    @Override
    public String getAllUsersAsJSON(String accessToken) throws AppException {
        // TODO finish implementation
        return null;
    }
}
