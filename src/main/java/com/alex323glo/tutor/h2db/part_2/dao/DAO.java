package com.alex323glo.tutor.h2db.part_2.dao;

import com.alex323glo.tutor.h2db.part_2.exception.DAOException;
import com.alex323glo.tutor.h2db.part_2.model.response.Response;

import java.util.Map;
import java.util.Set;

public interface DAO<K, V> {

    /**
     * Creates new record.
     *
     * @param key unique identifier of new record.
     * @param value data, which will be stored inside new record.
     * @return Response object with OK status (if operation was successful) and
     * Null body.
     * @throws DAOException if params don't pass validation or DAO has some problems
     * with execution of this part of logic (check cause of thrown DAOException).
     *
     * @see Response
     * @see DAOException
     */
    Response create(K key, V value) throws DAOException;

    /**
     * Reads existent record.
     *
     * @param key unique identifier of existent record.
     * @return Response object with OK status and not Null body (data, stored inside this
     * record), if operation was successful, or Response object with KO status and Null body,
     * if record with such key doesn't exist.
     * @throws DAOException if params don't pass validation or DAO has some problems
     * with execution of this part of logic (check cause of thrown DAOException).
     *
     * @see Response
     * @see DAOException
     */
    Response<V> read(K key) throws DAOException;

    /**
     * Updates data of existent record.
     *
     * @param key unique identifier of existent record.
     * @param value new data, which will be stored inside existent record.
     * @return Response object with OK status and not Null body (old data,
     * stored inside existent record before this update() operation was carried out),
     * if operation was successful, or Response object with KO status and Null body,
     * if record with such key doesn't exist.
     *
     * @throws DAOException if params don't pass validation or DAO has some problems
     * with execution of this part of logic (check cause of thrown DAOException).
     *
     * @see Response
     * @see DAOException
     */
    Response<V> update(K key, V value) throws DAOException;

    /**
     * Deletes existent record.
     *
     * @param key unique identifier of existent record.
     * @return Response object with OK status and not Null body (old data,
     * stored inside existent record before this delete() operation was carried out),
     * if operation was successful, or Response object with KO status and Null body,
     * if record with such key doesn't exist.
     * @throws DAOException if params don't pass validation or DAO has some problems
     * with execution of this part of logic (check cause of thrown DAOException).
     *
     * @see Response
     * @see DAOException
     */
    Response<V> delete(K key) throws DAOException;

    /**
     * Gets Set of keys of all existent records.
     *
     * @return not Null Set of unique records' identifiers.
     * @throws DAOException if params don't pass validation or DAO has some problems
     * with execution of this part of logic (check cause of thrown DAOException).
     *
     * @see DAOException
     */
    Set<K> getKeyset() throws DAOException;

    /**
     * Get all existent records as Map of K-V records.
     *
     * @return not Null Map, made of key-value records.
     * @throws DAOException if params don't pass validation or DAO has some problems
     * with execution of this part of logic (check cause of thrown DAOException).
     *
     * @see DAOException
     */
    Map<K, V> getAll() throws DAOException;

}
