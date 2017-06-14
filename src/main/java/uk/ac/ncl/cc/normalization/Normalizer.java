package uk.ac.ncl.cc.normalization;

/**
 * Interface Normalizer
 *
 * Created by Michael Daniilakis
 */
public interface Normalizer {

    /**
     * Implement this method to transform a token.
     *
     * @param token raw token
     * @return transformed token
     */
    String normalize(String token);
}
