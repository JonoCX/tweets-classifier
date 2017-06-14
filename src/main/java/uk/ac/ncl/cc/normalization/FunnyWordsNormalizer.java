package uk.ac.ncl.cc.normalization;

import org.apache.commons.lang3.StringUtils;

/**
 * Created by b4060825
 */
public class FunnyWordsNormalizer implements Normalizer {

    /**
     * Replaces all the links within the token
     * with the Keyword 'url'.
     *
     * @param token the initial token
     * @return token transformed token
     */
    @Override
    public String normalize(String token) {

        if (StringUtils.equals(token.toLowerCase(), "k")) {
            return "funny";
        }

        if (StringUtils.equals(token.toLowerCase(), "kk")) {
            return "funny";
        }

        if (StringUtils.equals(token.toLowerCase(), "lol")) {
            return "funny";
        }

        if (StringUtils.equals(token.toLowerCase(), "rs")) {
            return "funny";
        }

        token = token.toLowerCase().replaceAll(".*(h+e+h+).*", "funny");
        token = token.toLowerCase().replaceAll(".*(h+a+h+).*", "funny");
        token = token.toLowerCase().replaceAll("(r+s+r+s+).*", "funny");
        token = token.toLowerCase().replaceAll(".*(h+u+a+h+u+).*", "funny");
        token = token.toLowerCase().replaceAll(".*(h+o+h+o+).*", "funny");
        token = token.toLowerCase().replaceAll(".*(h+a+.*h+a+).*", "funny");
        token = token.toLowerCase().replaceAll(".*(h+e+u+.*h+e+u+).*", "funny");
        token = token.toLowerCase().replaceAll(".*(h+u+a+.*h+u+a+).*", "funny");

        return token;

    }


}
