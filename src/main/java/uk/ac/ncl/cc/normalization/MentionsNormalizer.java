package uk.ac.ncl.cc.normalization;

public class MentionsNormalizer implements Normalizer {

    @Override
    public String normalize(String token) {
    	if (token.contains("@")) {
    		return " ";
    	}
    	return token;
    }
}
