package uk.ac.ncl.cc.normalization;

public class LowerCaseNormalizer implements Normalizer {

    @Override
    public String normalize(String token) {
        return token.toLowerCase();
    }
}
