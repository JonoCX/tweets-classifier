package uk.ac.ncl.cc.normalization;

import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.StringUtils;

/**
 * Created by B4046044 on 27/05/2015.
 */
public class SpecialCharacterNormalizer implements Normalizer {

    private char[] characters = new char[] {
        ',', '.', ':', ';', '!', '?', '#', '%',
        '(', ')', '{', '}', '[', ']', '<', '>',
        '|', '~', '^', '&', '*', '$', '£', '`',
        '-', '–', '—', '_', '+',
        '\\', '\'', '\"', '@',
        '/', '‘', '’', '…'
    };

    public SpecialCharacterNormalizer() {
        // default constructor
    }

    public SpecialCharacterNormalizer(char[] more) {
        characters = ArrayUtils.addAll(characters, more);
    }

    @Override
    public String normalize(String token) {

        //String str = StringUtils.strip(token, new String(characters));

        if (StringUtils.containsAny(token.toLowerCase(),characters)) {

            token=token.replace(',',' ');
            token= token.replace('.',' ');
            token=token.replace(':',' ');
            token= token.replace(';',' ');
            token=token.replace('!',' ');
            token= token.replace('?',' ');
            token=token.replace('#',' ');
            token= token.replace('%',' ');

            token=token.replace('(',' ');
            token= token.replace(')',' ');
            token=token.replace('{',' ');
            token= token.replace('}',' ');

            token=token.replace('[',' ');
            token= token.replace(']',' ');
            token=token.replace('<',' ');
            token= token.replace('>',' ');

            token=token.replace('|',' ');
            token= token.replace('~',' ');
            token=token.replace("^","");
            token= token.replace('&',' ');

            token=token.replace('*',' ');
            token= token.replace('$',' ');
            token=token.replace('£',' ');
            token= token.replace("`","");

            token=token.replace('-',' ');
            token= token.replace('–',' ');
            token=token.replace('—',' ');
            token= token.replace('_',' ');

            token=token.replace('+',' ');
            token=token.replace('+',' ');
            token= token.replace('\\',' ');
            token=token.replace('\'',' ');
            token= token.replace('\"',' ');
            token=token.replace('/',' ');
            token= token.replace("‘","");
            token=token.replace("’","");
            token=token.replace('=',' ');
            token=token.replace('…',' ');
        }

        return  token.replaceAll("\\s+"," ").trim();
    }
}
