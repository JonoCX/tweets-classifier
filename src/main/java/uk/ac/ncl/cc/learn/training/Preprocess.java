package uk.ac.ncl.cc.learn.training;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.apache.commons.lang3.StringUtils;

import opennlp.tools.tokenize.Tokenizer;
import opennlp.tools.tokenize.WhitespaceTokenizer;
import uk.ac.ncl.cc.normalization.AbbreviationsNormalizer;
import uk.ac.ncl.cc.normalization.EmojiNormalizer;
import uk.ac.ncl.cc.normalization.FunnyWordsNormalizer;
import uk.ac.ncl.cc.normalization.LemmaNormalizer;
import uk.ac.ncl.cc.normalization.LinkToImageNormalizer;
import uk.ac.ncl.cc.normalization.LinkToWebpageNormalizer;
import uk.ac.ncl.cc.normalization.LowerCaseNormalizer;
import uk.ac.ncl.cc.normalization.MentionsNormalizer;
import uk.ac.ncl.cc.normalization.NumericDataNormalizer;
import uk.ac.ncl.cc.normalization.PictographEmojiNormalizer;
import uk.ac.ncl.cc.normalization.RepeatedLetterNormalizer;
import uk.ac.ncl.cc.normalization.SpecialCharacterNormalizer;
import uk.ac.ncl.cc.normalization.StopwordsNormalizer;
import uk.ac.ncl.cc.normalization.TokenProcessor;

public class Preprocess {
    private static final Logger log = Logger.getLogger(Train.class.getName());

    private static String[] ofThese = new String[]{
            "@", "rn", "via", "tbr"
    };

    public static void main(String[] args) {
        String rawDataFilaPath = "data/testSet.txt";
//    	String rawDataFilaPath = "data/test.txt";
        String outputFilePath = "data/tweets.train";
        Tokenizer tokenizer = WhitespaceTokenizer.INSTANCE;
        TokenProcessor tokenProcessor = TokenProcessor
                .getInstance()
                .addNormalizer(new LowerCaseNormalizer())
                .addNormalizer(new MentionsNormalizer())
                .addNormalizer(new LinkToWebpageNormalizer())
                .addNormalizer(new LinkToImageNormalizer())
                .addNormalizer(new SpecialCharacterNormalizer())
                .addNormalizer(new EmojiNormalizer())
                .addNormalizer(new PictographEmojiNormalizer())
                .addNormalizer(new AbbreviationsNormalizer())
                .addNormalizer(new RepeatedLetterNormalizer())
                .addNormalizer(new NumericDataNormalizer())
                .addNormalizer(new FunnyWordsNormalizer())
                .addNormalizer(new StopwordsNormalizer("data/stopwords.txt"));

        LemmaNormalizer lemmaNormalizer = new LemmaNormalizer("pt-pos-maxent.bin");
        lemmaNormalizer.ignoreToken("dengue");
        lemmaNormalizer.ignoreToken("zika");
        tokenProcessor.addNormalizer(lemmaNormalizer);

        try {
            FileOutputStream fos = new FileOutputStream(new File(outputFilePath));
            FileInputStream fis = new FileInputStream(new File(rawDataFilaPath));
            BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(fos, "UTF-8"));
            BufferedReader br = new BufferedReader(new InputStreamReader(fis, "UTF-8"));
            String line = null;
            while ((line = br.readLine()) != null) {

                String[] tokens = tokenizer.tokenize(line);
                List<String> rawTokens = Arrays.asList(tokens);
                List<String> featureSet = new ArrayList<String>();

                for (String token : rawTokens.subList(1, rawTokens.size())) {

                    if (StringUtils.startsWithAny(token.toLowerCase(), ofThese)) {
                        continue;
                    }

                    String tok = tokenProcessor.process(token);
                    if (tok.trim().length() == 0) {
                        continue;
                    }

                    featureSet.add(tok);
                }

                StringBuilder builder = new StringBuilder();
                builder.append(tokens[0]).append(" ");

                for (String token1 : featureSet) {
                    builder.append(token1).append(" ");
                }
                bw.write(builder.toString().trim());
                bw.newLine();
            }
            bw.close();
            br.close();
        } catch (IOException e) {
            log.log(Level.INFO, e.getMessage());
        }
    }
}
