# Twitter Rank Pipeline #

## Requirements ##

See dependencies in the the pom.xml file.

## Produce bayes.model ##

The package uk.ac.ncl.cc contains all the code needed to produce the bayes.model. 
The steps to do so are the following:

* Create a .txt file named 'tweetsForTraining.txt' whereby they are in the following format:
* noise "RT @frasesdebebadas: Os gringos achando q o maior problema do Brasil é zika virus e violência, só vão descobrir a vdd qnd chegarem aqui http..."
* Once you've done this, put the text file within the project workspace and run the Preprocess.java class. This class produces the 'tweets.train' file.
* Now to convert the 'tweets.train' file into the 'tweets.arff' file; to do this, run the ArffConverter.java class. The result should be a 'tweets.arff' file.
* To produce the 'bayes.model' run the TrainBuildClassifier.java class.
* Done.

## Run the Classification ##

To be able to run the classification you need both the bayes.model file and the pt-pos-maxent.bin file.

In order to perform the classification on a string do the following:

* `Classifier classifier = Classifier.getInstance();`

* `Category category = classifier.classify(<string to be classified>);`

* And from the category you're able to call methods such as `getKey();` to get the label of the cateory and `getPrediction();` to get the integer value of the classification label.