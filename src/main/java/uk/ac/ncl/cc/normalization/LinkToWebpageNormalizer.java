package uk.ac.ncl.cc.normalization;

import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.StringUtils;

/**
 * Created by b4060825
 */
public class LinkToWebpageNormalizer implements Normalizer {


    private String[] link = new String[]{
            "http",
            "https",
            "ftp",
            "gopher",
            "telnet",
            "file:"
    };

    public LinkToWebpageNormalizer() {
        // default constructor
    }

    public LinkToWebpageNormalizer(String[] more) {
        link = ArrayUtils.addAll(link, more);
    }

    /**
     * Replaces all the links within the token
     * with the Keyword 'url'.
     *
     * @param token the initial token
     * @return token transformed token
     */
    @Override
    public String normalize(String token) {

        if (StringUtils.startsWithAny(token.toLowerCase(), link)) {
            token = "url";
        }
        return token;

    }


}
