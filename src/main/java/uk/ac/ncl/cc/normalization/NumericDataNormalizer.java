package uk.ac.ncl.cc.normalization;

/**
 * Created by b4060825
 */
public class NumericDataNormalizer implements Normalizer {

    /**
     * Replaces all the numbers or numeric data within the token
     * with the Keyword 'numeral'
     *
     * @param token the initial token
     * @return the transformed token
     */
    @Override
    public String normalize(String token) {
        return token.replaceAll("\\d+", " numeral ").replaceAll("\\s+", " ").trim();

    }
}
