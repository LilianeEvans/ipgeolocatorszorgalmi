package geolocator;

import java.net.URL;

import java.io.IOException;

import com.google.gson.Gson;

import com.google.common.net.UrlEscapers;

import com.sun.tools.javac.Main;
import org.apache.commons.io.IOUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Class for obtaining geolocation information about an IP address or host
 * name. The class uses the <a href="http://ip-api.com/">IP-API.com</a>
 * service.
 */

public class GeoLocator {
    private static Logger logger = LoggerFactory.getLogger(GeoLocator.class);
    /**
     * URI of the geolocation service.
     */
    public static final String GEOLOCATOR_SERVICE_URI = "http://ip-api.com/json/";

    private static Gson GSON = new Gson();

    /**
     * Creates a <code>GeoLocator</code> object.
     */
    public GeoLocator() {}

    /**
     * Returns geolocation information about the JVM running the application.
     *
     * @return an object wrapping the geolocation information returned
     * @throws IOException if any I/O error occurs
     */
    public GeoLocation getGeoLocation() throws IOException {
        return getGeoLocation(null);
    }

    /**
     * Returns geolocation information about the IP address or host name
     * specified. If the argument is <code>null</code>, the method returns
     * geolocation information about the JVM running the application.
     *
     * @param ipAddrOrHost the IP address or host name, may be {@code null}
     * @return an object wrapping the geolocation information returned
     * @throws IOException if any I/O error occurs
     */
    public GeoLocation getGeoLocation(String ipAddrOrHost) throws IOException {
        URL url;
        if (ipAddrOrHost != null) {
            ipAddrOrHost = UrlEscapers.urlPathSegmentEscaper().escape(ipAddrOrHost);
            url = new URL(GEOLOCATOR_SERVICE_URI + ipAddrOrHost);
            logger.info("URL set to {}", url);
        } else {
            url = new URL(GEOLOCATOR_SERVICE_URI);
            logger.info("URL set to {}", url);
        }
        String s = IOUtils.toString(url, "UTF-8");
        logger.debug("Json: {}", s);
        return GSON.fromJson(s, GeoLocation.class);
    }

    public static void main(String[] args) throws IOException {
        //Logger logger = LoggerFactory.getLogger(GeoLocator.class);


        try {
            String arg = args.length > 0 ? args[0] : null;
            logger.info("Kiiratas", new GeoLocator().getGeoLocation(arg));
            if(args.length < 0) {logger.error("Something went wrong.");}
        } catch (IOException e) {
            logger.error(e.getMessage());
        }

    }

}
