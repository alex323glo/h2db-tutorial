package com.alex323glo.tutor.h2db.part_2.controller;

import com.alex323glo.tutor.h2db.part_2.exception.AppException;

public interface MainController {

    /**
     * Registers new user in system.
     *
     * @param username unique identifier for new user.
     * @param password password for new user.
     * @return String access token of registered (and automatically authorised) user.
     * @throws AppException if params don't pass validation or service has some problems
     * with execution of this part of logic (check cause of thrown AppException).
     *
     * @see AppException
     */
    String register(String username, String password) throws AppException;

    /**
     * Authorises registered user in system.
     *
     * @param username unique identifier of existent user.
     * @param password password of this existent user.
     * @return String access token of authorised user.
     * @throws AppException if params don't pass validation or service has some problems
     * with execution of this part of logic (check cause of thrown AppException).
     *
     * @see AppException
     */
    String login(String username, String password) throws AppException;

    /**
     * Removes access token from list (in system) of authorised users.
     *
     * @param accessToken token, which will be removed from system.
     * @throws AppException if params don't pass validation or service has some problems
     * with execution of this part of logic (check cause of thrown AppException).
     */
    void logout(String accessToken) throws AppException;

    /**
     * Returns JSON object of personal single user data record (accessed by unique USER access token)
     * converted to String.
     *
     * @param accessToken unique access token, which gives access to concrete (single)
     *                    record of user data, linked to this access token after authorisation..
     * @return JSON object, represented as String.
     * @throws AppException if params don't pass validation or service has some problems
     * with execution of this part of logic (check cause of thrown AppException).
     */
    String getUserInfoAsJSON(String accessToken) throws AppException;

    /**
     * Returns JSON object of personal all user data records (accessed by unique ROOT access token)
     * converted to String.
     *
     * @param accessToken unique access token, which gives access to all records
     *                    of user data (gives root access).
     * @return JSON object, represented as String.
     * @throws AppException if params don't pass validation or service has some problems
     * with execution of this part of logic (check cause of thrown AppException).
     */
    String getAllUsersAsJSON(String accessToken) throws AppException;

}
