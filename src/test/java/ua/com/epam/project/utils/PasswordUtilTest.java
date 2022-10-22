package ua.com.epam.project.utils;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.sql.SQLException;

class PasswordUtilTest {

    private final String strToEncrypt = "pass";

    @Test
    void encryptSuccess() throws SQLException {
        String salt = "roma";
        String expected = "jolXoIdxearRI7tegiq1Dw==";
        String result = PasswordUtil.encrypt(strToEncrypt, salt);

        Assertions.assertEquals(expected, result);
    }

    @Test
    void encryptError() {
        Assertions.assertThrows(SQLException.class, () -> PasswordUtil.encrypt(strToEncrypt, null));
    }
}