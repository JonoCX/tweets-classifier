package uk.ac.ncl.cc.learn.weka;


import weka.classifiers.meta.FilteredClassifier;
import weka.classifiers.trees.RandomForest;
import weka.classifiers.bayes.NaiveBayesMultinomial;
import weka.core.Instances;
import weka.core.SelectedTag;
import weka.core.converters.ConverterUtils.DataSource;
import weka.core.tokenizers.NGramTokenizer;
import weka.filters.unsupervised.attribute.StringToWordVector;

import java.io.FileOutputStream;
import java.io.ObjectOutputStream;

public class TrainBuildClassifier2 {

    public static void main(String[] args) throws Exception {


        DataSource dataSource = new DataSource("data/tweets.arff");
        Instances instances = dataSource.getDataSet();
        instances.setClassIndex(1);

        StringToWordVector filter = new StringToWordVector();
        filter.setWordsToKeep(100000000);
        filter.setOutputWordCounts(true);
        filter.setNormalizeDocLength(new SelectedTag(StringToWordVector.FILTER_NORMALIZE_ALL, StringToWordVector.TAGS_FILTER));

        NGramTokenizer tokenizer = new NGramTokenizer();
        tokenizer.setNGramMinSize(1);
        tokenizer.setNGramMaxSize(4);
        filter.setTokenizer(tokenizer);

        FilteredClassifier fc = new FilteredClassifier();
        fc.setClassifier(new NaiveBayesMultinomial());
        fc.setFilter(filter);

        // Train and build the classifier
        System.out.println("Building Classifier.....");
        fc.buildClassifier(instances);
        
        System.out.println("Outputting model.....");
        ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream("data/bayes.model"));
        System.out.println("Done.");
        out.writeObject(fc);
        out.flush();
        out.close();
    }
}