package com.alex323glo.tutor.h2db.part_2.util;

import java.util.Set;
import java.util.UUID;

/**
 * Container for general static generating methods.
 *
 * @author alex323glo
 * @version 1.0
 */
public class Generator {

    /**
     * Generates unique (regarding the tokens from tokenSet) token.
     * Uses UUID to generate token.
     *
     * @param tokenSet Set of unique String tokens.
     * @return unique String token.
     *
     * @see Set
     * @see UUID
     */
    public static String generateUniqueToken(Set<String> tokenSet) {
        if (tokenSet == null) {
            throw new NullPointerException("tokenSet is null");
        }

        String token = null;
        do {
            token = UUID.randomUUID().toString();
        } while (tokenSet.contains(token));

        return token;
    }

}
