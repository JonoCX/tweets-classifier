package uk.ac.ncl.cc.learn.weka;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.util.Arrays;
import java.util.List;

import opennlp.tools.tokenize.Tokenizer;
import opennlp.tools.tokenize.WhitespaceTokenizer;

/**
 * Created by B4046044 on 20/06/2015.
 */
public class ArffConverter {

    public static void main(String[] args) {
        String tweetsFilePath = "data/tweets.train";
        String arffFilePath = "data/testSet.arff";
        try {
            FileOutputStream fos = new FileOutputStream(new File(arffFilePath));
            FileInputStream fis = new FileInputStream(new File(tweetsFilePath));
            BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(fos, "UTF-8"));
            BufferedReader br = new BufferedReader(new InputStreamReader(fis, "UTF-8"));
            Tokenizer tokenizer = WhitespaceTokenizer.INSTANCE;

            bw.write("@RELATION tweets");
            bw.newLine();
            bw.newLine();
            //bw.write("@ATTRIBUTE class {informative,joke,mosquito_focus,sickness}");
            bw.write("@ATTRIBUTE tweet string");
            bw.newLine();
            bw.write("@ATTRIBUTE class {news,noise,relevant}");
            bw.newLine();
            bw.newLine();
            bw.write("@DATA");
            bw.newLine();

            String line = null;
            while ((line = br.readLine()) != null) {
                String[] tokens = tokenizer.tokenize(line);
                List<String> tokensList = Arrays.asList(tokens);

                StringBuilder builder = new StringBuilder();
                for (String token : tokensList.subList(1, tokensList.size())) {
                    builder.append(token).append(" ");
                }

                bw.write("'" + builder.toString().trim() + "'," + tokens[0]);
                bw.newLine();
            }
            bw.flush();
            bw.close();
            br.close();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
