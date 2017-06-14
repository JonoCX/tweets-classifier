package uk.ac.ncl.cc.normalization;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.HashSet;
import java.util.Set;
import java.util.StringTokenizer;

public class StopwordsNormalizer implements Normalizer {

    private Set<String> stopwords = new HashSet<String>();

    private String fromFile;

    public StopwordsNormalizer(String fromFile) {
        this.fromFile = fromFile;
        try {
            FileInputStream fis = new FileInputStream(new File(this.fromFile));
            BufferedReader br = new BufferedReader(new InputStreamReader(fis, "UTF-8"));
            String line = null;
            while ((line = br.readLine()) != null) {
                stopwords.add(line.trim());
            }
            br.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public String getFileName() {
        return fromFile;
    }

    public void setFileName(String fromFile) {
        this.fromFile = fromFile;
    }

    @Override
    public String normalize(String token) {
    	StringTokenizer tokenizer = new StringTokenizer(token);
    	StringBuilder builder = new StringBuilder();
    	boolean added = false;
    	while (tokenizer.hasMoreTokens()) {
    		String subToken = tokenizer.nextToken();
    		if (!stopwords.contains(subToken)) {
    			if (added) {
    				builder.append(" ");
    			}
    			builder.append(subToken);
    			added = true;
            }
    	}
        
        return builder.toString();
    }
}
