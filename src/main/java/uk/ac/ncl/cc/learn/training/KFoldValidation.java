package uk.ac.ncl.cc.learn.training;

import java.text.DecimalFormat;
import java.util.Date;

import uk.ac.ncl.cc.classifier.Category;
import weka.classifiers.Evaluation;
import weka.classifiers.bayes.NaiveBayesMultinomial;
import weka.classifiers.meta.FilteredClassifier;
import weka.core.Debug.Random;
import weka.core.Instances;
import weka.core.SelectedTag;
import weka.core.converters.ConverterUtils.DataSource;
import weka.core.tokenizers.NGramTokenizer;
import weka.filters.unsupervised.attribute.StringToWordVector;

public class KFoldValidation {

	public static void main(String[] args) throws Exception {
		DataSource dataSource = new DataSource("data/tweets.arff");
		Instances data = dataSource.getDataSet();
		data.setClassIndex(0);

		long seed = new Date().getTime();
		int folds = 10;

		Random rand = new Random(seed);
		Instances randData = new Instances(data); 
		randData.randomize(rand); 
		randData.stratify(folds);

		Evaluation eval = new Evaluation(randData);
		for (int n = 0; n < folds; n++) {
			Instances train = randData.trainCV(folds, n);
			Instances test = randData.testCV(folds, n);

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
			
//			System.out.println("Classifier: " + fc.getClass().getName() + " " + Utils.joinOptions(fc.getOptions()));

			// Train and build the classifier
			fc.buildClassifier(train);
			eval.evaluateModel(fc, test);
		}

		System.out.println();
		System.out.println("=== Setup ===");
		
		System.out.println("Dataset: " + data.relationName());
		System.out.println("Folds: " + folds);
		System.out.println("Seed: " + seed);
		System.out.println();
		System.out.println(eval.toSummaryString("=== " + folds + "-fold Cross-validation ===", false));
		DecimalFormat df = new DecimalFormat("0.00"); 
		for (int i = 0; i <= 3; i++) {
			System.out.println("True Positive rate for " + Category.fromPrediction(i).getKey() + ":" +  df.format(eval.truePositiveRate(i)));
			System.out.println("True Negative rate for " + Category.fromPrediction(i).getKey() + ":" +  df.format(eval.trueNegativeRate(i)));
		}
		System.out.println(eval.toMatrixString());
	}
}
