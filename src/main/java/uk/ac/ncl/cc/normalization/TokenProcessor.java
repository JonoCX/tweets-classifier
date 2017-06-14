package uk.ac.ncl.cc.normalization;

import java.util.ArrayList;
import java.util.List;

/**
 * Used to process tokens with any of the {@link uk.ac.ncl.cc.normalization.Normalizer} objects.
 *
 * Created by Michael Daniilakis
 */
public class TokenProcessor {

    /**
     * A List of Normalizers.
     */
    private List<Normalizer> normalizers = new ArrayList<Normalizer>();

    /**
     * Instance.
     */
    private static TokenProcessor instance = null;

    /**
     * Protected constructor, use getInstance()
     */
    protected TokenProcessor() {
        // protected
    }

    /**
     * Creates a instance.
     *
     * @return a TokenProcessor instance
     */
    public static TokenProcessor getInstance() {
        if (instance == null) {
            instance = new TokenProcessor();
        }
        return instance;
    }

    /**
     * Adds a Normalizer to the list.
     *
     * @param normalizer the Normalizer
     * @return this
     */
    public TokenProcessor addNormalizer(Normalizer normalizer) {
        normalizers.add(normalizer);
        return this;
    }

    /**
     * Process a token with all the provided normalizers.
     *
     * @param token the raw token
     * @return the transformed token
     */
    public String process(String token) {
        for (Normalizer normalizer : normalizers) {
            token = normalizer.normalize(token);
        }
        return token;
    }
}
