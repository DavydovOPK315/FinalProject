package ua.com.epam.project.utils;

import javax.servlet.http.HttpServletRequest;

/**
 * Util class to get site URL
 *
 * @author Denis Davydov
 * @version 2.0
 */
public final class GetSiteUrlUtil {

    private GetSiteUrlUtil() {
    }

    /**
     * Function to get site URL
     *
     * @param request reques
     * @return return site URL
     */
    public static String getSiteURL(HttpServletRequest request) {
        String siteURL = request.getRequestURL().toString();
        return siteURL.replace(request.getServletPath(), "");
    }
}