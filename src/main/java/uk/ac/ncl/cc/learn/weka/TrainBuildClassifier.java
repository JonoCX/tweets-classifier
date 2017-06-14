package uk.ac.ncl.cc.learn.weka;


import weka.attributeSelection.InfoGainAttributeEval;
import weka.attributeSelection.Ranker;
import weka.classifiers.Evaluation;
import weka.classifiers.bayes.NaiveBayesMultinomial;
import weka.classifiers.meta.FilteredClassifier;
import weka.classifiers.trees.RandomForest;
import weka.core.Instances;
import weka.core.SelectedTag;
import weka.core.converters.ConverterUtils.DataSource;
import weka.core.tokenizers.NGramTokenizer;
import weka.filters.Filter;
import weka.filters.MultiFilter;
import weka.filters.supervised.attribute.AttributeSelection;
import weka.filters.supervised.instance.SMOTE;
import weka.filters.unsupervised.attribute.StringToWordVector;

import java.io.FileOutputStream;
import java.io.ObjectOutputStream;
import java.util.Random;


/*
 * Author: Callum McClean
 */
public class TrainBuildClassifier {

	public static void main(String args[]) throws Exception {
		
		//load training dataset
		DataSource source = new DataSource("data/tweets.arff");
		Instances trainDataset = source.getDataSet();	
		
		//set class index to the last attribute
		trainDataset.setClassIndex(trainDataset.numAttributes()-1);
		
		//create STWV
		StringToWordVector stwv = new StringToWordVector();
		String[] options = weka.core.Utils.splitOptions("-R first-last -W 1000 -prune-rate -1.0 -N 0 -stemmer weka.core.stemmers.NullStemmer -stopwords-handler weka.core.stopwords.Null -M 1 -tokenizer weka.core.tokenizers.NGramTokenizer -max 3 -min 1");
		stwv.setOptions(options);
		
		//create SMOTE 
		SMOTE smote = new SMOTE();
		String[] options1 = weka.core.Utils.splitOptions("-C 0 -K 5 -P 100.0 -S 1");
		smote.setOptions(options1);
		
		//create attribute selection
		AttributeSelection as = new AttributeSelection();
		String[] options2 = weka.core.Utils.splitOptions("-T 0 -N -1");
        Ranker search = new Ranker();
        search.setOptions(options2);
        InfoGainAttributeEval evaluator = new InfoGainAttributeEval();
        as.setEvaluator(evaluator);
        as.setSearch(search);
		
		//add filters to a single multifilter
		MultiFilter multi = new MultiFilter();
        multi.setFilters(new Filter[] {stwv, as, smote});
		
		//create filtered classifier
		FilteredClassifier fc = new FilteredClassifier();
		
		//define classification method
		RandomForest rf = new RandomForest();
		String[] options3 = weka.core.Utils.splitOptions("-I 100 -K 0 -S 1");
		rf.setOptions(options3);
		
		//add filters to model
		fc.setFilter(multi);
		
		//add method to model
		fc.setClassifier(rf);
		
		//train model on training set
		fc.buildClassifier(trainDataset);
		
		//output trained model to file
        ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream("data/current.model"));
        out.writeObject(fc);
        out.flush();
        out.close();
    }
}
