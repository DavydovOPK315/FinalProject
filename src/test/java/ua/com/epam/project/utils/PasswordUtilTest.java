package ua.com.epam.project.utils;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.sql.SQLException;

class PasswordUtilTest {

    @Test
    void encrypt() throws SQLException {
        String strToEncrypt = "pass";
        String salt = "roma";
        String expected = "jolXoIdxearRI7tegiq1Dw==";
        String result = PasswordUtil.encrypt(strToEncrypt, salt);

        Assertions.assertEquals(expected, result);
        Assertions.assertThrows(SQLException.class, () -> PasswordUtil.encrypt(strToEncrypt, null));
    }
}