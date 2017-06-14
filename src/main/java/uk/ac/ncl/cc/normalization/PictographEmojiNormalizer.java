package uk.ac.ncl.cc.normalization;

/**
 * @author Michael Daniilakis
 */
public class PictographEmojiNormalizer implements Normalizer {

    public PictographEmojiNormalizer() {
        // default constructor
    }

    /**
     *  Removes all miscellaneous symbols and pictographs including
     *  emoji characters within the token.
     *
     * @param token
     * @return
     */
    @Override
    public String normalize(String token) {
        return token.replaceAll("\\p{So}+", "");
    }
}
