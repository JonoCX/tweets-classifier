package classifier;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;

import uk.ac.ncl.cc.classifier.Category;
import uk.ac.ncl.cc.classifier.Classifier;

public class Test {
	public static void main(String[] args) throws Exception {
		Classifier classifier = Classifier.getInstance();
		
		BufferedReader reader = new BufferedReader(new FileReader(new File("data/providers_receivers.txt")));
		String line = reader.readLine();
		System.out.println("correct;predicted;text");
		int correct = 0;
		int total = 0;
		while(line != null) {
			int firstSpace = line.indexOf(" ");
			String correctCategory = line.substring(0, firstSpace);
			String text = line.substring(firstSpace + 1, line.length());
			
			if(correctCategory.equals("provider") || correctCategory.equals("receiver")) {
				correctCategory = "relevant";
			}
			total++;
			Category category = classifier.classify(text);
			if (!correctCategory.equals(category.getKey())) {
				System.out.println(correctCategory + ";" + category.getKey() + ";" + text);
			}
			else{
				correct++;
			}
			line = reader.readLine();
		}
		System.out.println("ACCURACY ======== " +((double)correct/(double)total)*100 + "%");
		reader.close();
	}
}
