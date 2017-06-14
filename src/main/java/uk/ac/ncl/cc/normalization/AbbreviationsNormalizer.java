package uk.ac.ncl.cc.normalization;

import java.util.HashMap;
import java.util.Map;

/**
 * @author Michael Daniilakis
 */
public class AbbreviationsNormalizer implements Normalizer {

    Map<String, String> tokens = new HashMap<String, String>();

    public AbbreviationsNormalizer() {
        tokens.put( "abs",      "abraço"            );
        tokens.put( "add",      "adicionar"         );
        tokens.put( "amg",      "amigo"             );
        tokens.put( "bbq",      "babaca"            );
        tokens.put( "bbk",      "babaca"            );
        tokens.put( "bff",      "amigo"             );
        tokens.put( "bjs",      "beijo"             );
        tokens.put( "blz",      "beleza"            );
        tokens.put( "brinks",   "brincadeira"       );
        tokens.put( "cmg",      "comigo"            );
        tokens.put( "ctz",      "certeza"           );
        tokens.put( "fb",       "facebook"          );
        tokens.put( "flw",      "até mais"          );
        tokens.put( "fmz",      "firmeza"           );
        tokens.put( "glr",      "galera"            );
        tokens.put( "kd",       "cadê"              );
        tokens.put( "msg",      "mensagem"          );
        tokens.put( "mto",      "muito"             );
        tokens.put( "n",        "não"               );
        tokens.put( "noob",     "novo"              );
        tokens.put( "pfv",      "por favor"         );
        tokens.put( "plmdds",   "pelo amor de deus" );
        tokens.put( "pq",       "porque"            );
        tokens.put( "prq",      "porque"            );
        tokens.put( "pra",      "para"              );
        tokens.put( "q",        "que"               );
        tokens.put( "qdo",      "quando"            );
        tokens.put( "ql",       "qual"              );
        tokens.put( "qm",       "quem"              );
        tokens.put( "qnd",      "quando"            );
        tokens.put( "qnt",      "quantidade"        );
        tokens.put( "qq",       "qualquer"          );
        tokens.put( "qrdo",     "querido"           );
        tokens.put( "qrda",     "querida"           );
        tokens.put( "s",        "sim"               );
        tokens.put( "sqn",      "só que não"        );
        tokens.put( "tc",       "teclar"            );
        tokens.put( "tmb",      "também"            );
        tokens.put( "to",       "estou"             );
        tokens.put( "vc",       "você"              );
        tokens.put( "vdd",      "verdade"           );
        tokens.put( "vlw",      "valeu"             );
        tokens.put( "rt",       "retweet"           );
    }

    @Override
    public String normalize(String token) {
        for (Map.Entry<String, String> entry : tokens.entrySet()) {
            if (token.equalsIgnoreCase(entry.getKey())) {
                return entry.getValue();
            }
        }
        return token;
    }
}
