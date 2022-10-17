package ua.com.epam.project.utils;

import org.apache.log4j.Logger;

import javax.json.Json;
import javax.json.JsonObject;
import javax.json.JsonReader;
import javax.net.ssl.HttpsURLConnection;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URL;

/**
 * Class for checking captcha
 *
 * @author Denis Davydov
 * @version 2.0
 */
public final class VerifyUtils {
    private static final String SECRET_KEY = "6Ld50h0iAAAAAGgaEnPAQ6Cc_k4i5Mz5aFOCYOY9";
    private static final String SITE_VERIFY_URL = "https://www.google.com/recaptcha/api/siteverify";
    private static final Logger LOG = Logger.getLogger(VerifyUtils.class);

    private VerifyUtils() {
    }

    /**
     * Function to verify captcha
     *
     * @param gRecaptchaResponse recaptcha response
     * @return return query result can be true or false
     */
    public static boolean verify(String gRecaptchaResponse) {
        if (gRecaptchaResponse == null || gRecaptchaResponse.length() == 0)
            return false;

        try {
            URL verifyUrl = new URL(SITE_VERIFY_URL);
            HttpsURLConnection conn = (HttpsURLConnection) verifyUrl.openConnection();
            conn.setRequestMethod("POST");
            conn.setRequestProperty("User-Agent", "Mozilla/5.0");
            conn.setRequestProperty("Accept-Language", "en-US,en;q=0.5");
            String postParams = "secret=" + SECRET_KEY + "&response=" + gRecaptchaResponse;
            conn.setDoOutput(true);

            OutputStream outStream = conn.getOutputStream();
            outStream.write(postParams.getBytes());
            outStream.flush();
            outStream.close();

            InputStream is = conn.getInputStream();
            JsonReader jsonReader = Json.createReader(is);
            JsonObject jsonObject = jsonReader.readObject();
            jsonReader.close();

            return jsonObject.getBoolean("success");
        } catch (Exception e) {
            LOG.error("Problem verify captcha");
            return false;
        }
    }
}