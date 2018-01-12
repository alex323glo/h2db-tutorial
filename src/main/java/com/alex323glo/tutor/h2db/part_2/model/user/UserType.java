package com.alex323glo.tutor.h2db.part_2.model.user;

/**
 * Created by alex323glo on 11.01.18.
 */
public enum UserType {
    USER, ROOT;

    /**
     * Converts String to UserType enum instance.
     *
     * @param src source String, needed to convert to UserType instance.
     * @return generated UserType instance, or Null, if String
     * param doesn't represent any variant of UserType enum.
     */
    public static UserType fromString(String src) {
        if (src == null) {
            throw new NullPointerException("src is null");
        }

        switch (src.toUpperCase()) {
            case "USER":
                return UserType.USER;
            case "ROOT":
                return UserType.ROOT;
        }

        return null;
    }
}
