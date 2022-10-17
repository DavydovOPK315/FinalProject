package ua.com.epam.project.utils;

import org.apache.log4j.Logger;

import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.PBEKeySpec;
import javax.crypto.spec.SecretKeySpec;
import java.nio.charset.StandardCharsets;
import java.security.spec.KeySpec;
import java.sql.SQLException;
import java.util.Base64;

/**
 * Util class to encrypt password
 *
 * @author Denis Davydov
 * @version 2.0
 */
public final class PasswordUtil {
    private static final String SECRET_KEY = "final_project_secret_key";
    private static final Logger LOG = Logger.getLogger(PasswordUtil.class);

    private PasswordUtil() {
    }

    /**
     * Function to encrypt password
     *
     * @param strToEncrypt password to encrypt
     * @param salt         salt
     * @return return encrypted password
     * @throws SQLException exception can be thrown
     */
    public static String encrypt(String strToEncrypt, String salt) throws SQLException {
        try {
            byte[] iv = {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0};
            IvParameterSpec ivspec = new IvParameterSpec(iv);
            SecretKeyFactory factory = SecretKeyFactory.getInstance("PBKDF2WithHmacSHA256");
            KeySpec spec = new PBEKeySpec(SECRET_KEY.toCharArray(), salt.getBytes(), 65536, 256);
            SecretKey tmp = factory.generateSecret(spec);
            SecretKeySpec secretKey = new SecretKeySpec(tmp.getEncoded(), "AES");

            Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
            cipher.init(Cipher.ENCRYPT_MODE, secretKey, ivspec);
            String encryptedPassword = Base64.getEncoder()
                    .encodeToString(cipher.doFinal(strToEncrypt.getBytes(StandardCharsets.UTF_8)));
            LOG.info("Password successfully encrypted");
            return encryptedPassword;
        } catch (Exception e) {
            LOG.error("Error while encrypting the password");
            throw new SQLException("");
        }
    }
}