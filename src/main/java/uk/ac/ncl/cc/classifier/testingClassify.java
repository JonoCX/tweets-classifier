package uk.ac.ncl.cc.classifier;

import weka.core.Instance;

public class testingClassify {

	public static void main(String[] args) {
		try {
			Classifier classifier = Classifier.getInstance();
			Category category = classifier.classify("aturo miga acha beijar alguem dengue tbm pego doença");
			System.out.println(category.getKey());
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		

	}

}
