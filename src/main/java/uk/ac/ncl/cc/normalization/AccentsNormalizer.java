package uk.ac.ncl.cc.normalization;

/**
 * @author Michael Daniilakis
 */
public class AccentsNormalizer implements Normalizer {

    @Override
    public String normalize(String token) {
        StringBuilder sb = new StringBuilder(token.length());
        token = java.text.Normalizer.normalize(token, java.text.Normalizer.Form.NFD);
        for (char c : token.toCharArray()) {
            if (c <= '\u007F') sb.append(c);
        }
        return sb.toString();
    }
}
