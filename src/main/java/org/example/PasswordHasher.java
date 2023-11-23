package org.example;

import org.mindrot.jbcrypt.BCrypt;

public class PasswordHasher {

    public static String hashPassword(String password) {
        String salt = BCrypt.gensalt(13);
        return BCrypt.hashpw(password, salt);
    }

    //password - not hashed passord; hashedPassword - from db
    public static boolean checkPassword(String password, String hashedPassword) {
        return BCrypt.checkpw(password, hashedPassword);
    }

}
