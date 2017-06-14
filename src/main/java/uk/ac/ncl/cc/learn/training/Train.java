package uk.ac.ncl.cc.learn.training;

import opennlp.tools.doccat.DoccatModel;
import opennlp.tools.doccat.DocumentCategorizerME;
import opennlp.tools.doccat.DocumentSample;
import opennlp.tools.doccat.DocumentSampleStream;
import opennlp.tools.util.ObjectStream;
import opennlp.tools.util.PlainTextByLineStream;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Train {

    private static final Logger log = Logger.getLogger(Train.class.getName());

    public static void main(String[] args) {
        String modelPath = "pt-doccat.bin";
        String trainingDataFilePath = "tweets-processed.train";
        InputStream dataInputStream = null;
        DoccatModel model = null;
        try {
            dataInputStream = new FileInputStream(trainingDataFilePath);
            ObjectStream<String> lineStream = new PlainTextByLineStream(dataInputStream, "UTF-8");
            ObjectStream<DocumentSample> sampleStream = new DocumentSampleStream(lineStream);
            model = DocumentCategorizerME.train("pt", sampleStream);
        } catch (IOException e) {
            log.log(Level.INFO, e.getMessage());
        } finally {
            if (dataInputStream != null) {
                try {
                    dataInputStream.close();
                } catch (IOException e) {
                    log.log(Level.INFO, e.getMessage());
                }
            }
        }
        try {
            if (model != null) {
                model.serialize(new FileOutputStream(modelPath));
            }
        } catch (IOException e) {
            log.log(Level.INFO, e.getMessage());
        }
    }
}
