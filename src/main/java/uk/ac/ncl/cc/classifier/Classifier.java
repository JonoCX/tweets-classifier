package uk.ac.ncl.cc.classifier;

import opennlp.tools.tokenize.Tokenizer;
import opennlp.tools.tokenize.WhitespaceTokenizer;
import org.apache.commons.lang3.StringUtils;
import uk.ac.ncl.cc.normalization.*;
import weka.attributeSelection.InfoGainAttributeEval;
import weka.attributeSelection.Ranker;
import weka.classifiers.bayes.NaiveBayesMultinomial;
import weka.classifiers.meta.FilteredClassifier;
import weka.classifiers.trees.RandomForest;
import weka.core.Attribute;
import weka.core.FastVector;
import weka.core.Instance;
import weka.core.Instances;
import weka.core.SelectedTag;
import weka.core.tokenizers.NGramTokenizer;
import weka.filters.Filter;
import weka.filters.MultiFilter;
import weka.filters.supervised.attribute.AttributeSelection;
import weka.filters.supervised.instance.SMOTE;
import weka.filters.unsupervised.attribute.StringToWordVector;

import java.io.InputStream;
import java.io.ObjectInputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * @author Michael Daniilakis
 * @author Callum McClean (Adatpted)
 */
public class Classifier {

    private static Classifier instance = null;
    private FastVector categories;
    private FilteredClassifier fc;
    private Tokenizer tokenizer = WhitespaceTokenizer.INSTANCE;
    private TokenProcessor processor = TokenProcessor.getInstance();

    protected Classifier() throws Exception {

        categories = new FastVector(Category.values().length);
        //System.out.println("category values length" + Category.values().length);
        for (Category category : Category.values()) {
            categories.addElement(category.getKey());
            //System.out.println("category keys" + category.getKey());
        }

        processor.addNormalizer(new LinkToWebpageNormalizer());
        processor.addNormalizer(new LinkToImageNormalizer());
        processor.addNormalizer(new SpecialCharacterNormalizer());
        processor.addNormalizer(new PictographEmojiNormalizer());
        processor.addNormalizer(new AbbreviationsNormalizer());
        processor.addNormalizer(new RepeatedLetterNormalizer());
        processor.addNormalizer(new NumericDataNormalizer());
        processor.addNormalizer(new FunnyWordsNormalizer());

        LemmaNormalizer lemmaNormalizer = new LemmaNormalizer("pt-pos-maxent.bin");
        lemmaNormalizer.ignoreToken("dengue");
        processor.addNormalizer(lemmaNormalizer);
        loadModel();

        //System.out.println(Arrays.toString(((StringToWordVector) fc.getFilter()).getOptions()));
    }

    /**
     * Creates an instance if not already instantiated, loads the model
     * and returns that instance.
     *
     * If an instance is already instantiated it is returned.
     *
     * @return a Classifier instance
     * @throws Exception
     */
    public static Classifier getInstance() throws Exception {
        if (instance == null) {
            instance = new Classifier();
        }
        return instance;
    }

    private String[] normalize(String document) {

        String[] tokens = tokenizer.tokenize(document);
        List<String> features = new ArrayList<String>();
        String[] removals = new String[] {
                "@",
                "rn",
                "via",
                "tbr",
        };

        for (String token : tokens) {

            if (StringUtils.startsWithAny(token.toLowerCase(), removals)) {
                continue;
            }
            String tmp = processor.process(token);
            if (tmp.trim().length() == 0) {
                continue;
            }
            features.add(tmp);
        }

        return features.toArray(new String[features.size()]);
    }

    /**
     * Accepts a document (String) and returns the predicted category based on the
     * highest probability being in that specific category.
     *
     * @param document the document
     * @return the predicted Category
     * @throws Exception
     */
    public Category classify(String document) throws Exception {
        String[] features = normalize(document);
        Instances instance = featuresToInstance(features);

        return Category.fromPrediction((int)fc.classifyInstance(instance.instance(0)));
        
    }

    private Instances featuresToInstance(String[] tokens) {
        String doc = StringUtils.join(tokens, " ");
        Attribute classAttr = new Attribute("class", categories);
        Attribute tweetAttr = new Attribute("tweet", (FastVector) null);
        FastVector attributes = new FastVector(2);
        attributes.addElement(tweetAttr);
        attributes.addElement(classAttr);
        Instances instances = new Instances("Sample Relation", attributes, 1);
        instances.setClassIndex(instances.numAttributes()-1);
        Instance instance = new Instance(2);
        instance.setValue(tweetAttr, doc);
        instances.add(instance);
        //System.out.println(instances);
        return instances;
    }

    private void loadModel() throws Exception {
        InputStream is = getClass().getClassLoader().getResourceAsStream("current.model");
        ObjectInputStream in = new ObjectInputStream(is);
        Object tmp = in.readObject();
        fc = (FilteredClassifier) tmp;
        in.close();
    }
}
