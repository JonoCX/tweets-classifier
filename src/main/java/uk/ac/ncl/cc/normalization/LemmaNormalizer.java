package uk.ac.ncl.cc.normalization;

import lemma.LemmatizeException;
import lemma.Lemmatizer;
import opennlp.tools.postag.POSModel;
import opennlp.tools.postag.POSTaggerME;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Michael Daniilakis
 */
public class LemmaNormalizer implements Normalizer {

    private POSTaggerME tagger;

    private Lemmatizer lemmatizer;

    private List<String> ignores = new ArrayList<String>();

    public LemmaNormalizer(String modelFilename) {
        InputStream modelIn = null;
        POSModel model = null;
        try {
            modelIn = getClass().getClassLoader().getResourceAsStream(modelFilename);
            model = new POSModel(modelIn);
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (modelIn != null) try {
                modelIn.close();
            } catch (IOException e) {}
        }
        tagger = new POSTaggerME(model);
        try {
            lemmatizer = new Lemmatizer();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void ignoreToken(String token) {
        ignores.add(token);
    }

    private String tag(String token) {
        String[] tokens = new String[] {
            token
        };
        return tagger.tag(tokens)[0];
    }

    //@Override
    public String normalize(String token) {
        if (ignores.contains(token)) {
            return token.toLowerCase();
        }
        return lemmatizer.lemmatize(token, tag(token));
    }

    public String[] normalize(String[] tokens) {
        try {
            return lemmatizer.lemmatize(tokens, tagger.tag(tokens));
        } catch (Exception e) {
            e.printStackTrace();
        }
        return tokens;
    }
}
