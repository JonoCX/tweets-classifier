package uk.ac.ncl.cc.normalization;

import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.utils.HttpClientUtils;
import org.apache.http.client.utils.URIBuilder;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.json.JSONObject;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URLEncoder;

/**
 * @author Michael Daniilakis
 */
public class RepeatedLetterNormalizer implements Normalizer {

    private CloseableHttpClient httpClient = HttpClients.createDefault();

    public RepeatedLetterNormalizer() {
        // default constructor
    }

    /**
     * Checks if the given token contains repeated same letters and replaces them
     * with one character. The new token is only returned if the it is a valid
     * word in Brazilian Portuguese. This is done by checking an online API.
     *
     * @param token the token to normalize
     * @return the original or transformed token
     */
    @Override
    public String normalize(String token) {
        if (hasRepeatedCharacter(token)) {
            String transformed = token.replaceAll("(?i)(\\p{L})\\1{2,}", "$1");
            try {
                if (isValidToken(transformed)) {
                    System.out.println("Transformation valid " + token + " to " + transformed);
                    return transformed;
                } else {
                    String token1 = token.replaceAll("([a-z])\\1+", "$1");
                    System.out.println("Transformation invalid " + token + " to " + token1);
                    return token1;
                }
            } catch (IOException e) {
                e.printStackTrace();
                return token;
            } catch (URISyntaxException e) {
                e.printStackTrace();
                return token;
            }
        }
        return token;
    }


    /**
     * Return whether a token contains three or more repeated
     * same characters.
     *
     * @param token the token to check
     * @return whether there are three or more repeated
     * characters in the given token
     */
    private boolean hasRepeatedCharacter(String token) {
        for (int i = 0; i < token.length(); i++) {
            if (i > 1) {
                if (Character.isDigit(token.charAt(i))) {
                    continue;
                }
                if (Character.toLowerCase(token.charAt(i)) == Character.toLowerCase(token.charAt(i - 1)) &&
                    Character.toLowerCase(token.charAt(i)) == Character.toLowerCase(token.charAt(i - 2))) {
                    return true;
                }
            }
        }
        return false;
    }


    /**
     * Returns whether the token is valid or not by checking the
     * online dictionary service (API).
     *
     * @param token the token to check
     * @return whether the token is valid or not
     * @throws IOException if there is a problem with the connection
     */
    private boolean isValidToken(String token) throws IOException, URISyntaxException {
        return streamToJson(findToken(token)).getInt("count") > 0;
    }

    /**
     * Returns the JSON response from the stream.
     *
     * @param stream the response stream
     * @return the JSON response from the stream
     */
    private JSONObject streamToJson(String stream) {
        return new JSONObject(stream);
    }

    /**
     * Performs a request to find the token in the service
     * and returns the response.
     *
     * @param token the token to search for
     * @return the response of the request
     * @throws IOException
     */
    private String findToken(String token) throws IOException, URISyntaxException {
        URI uri = new URIBuilder()
                .setScheme("http")
                .setHost("api.pearson.com")
                .setPath("/v2/dictionaries/brpe/entries")
                .setParameter("headword", URLEncoder.encode(token, "UTF-8"))
                .build();
        HttpGet req = new HttpGet(uri);
        CloseableHttpResponse response = httpClient.execute(req);
        String responseBody = EntityUtils.toString(response.getEntity());
        HttpClientUtils.closeQuietly(response);
        return responseBody;
    }
}
