package uk.ac.ncl.cc.normalization;

import emoji4j.EmojiUtils;

public class EmojiNormalizer implements Normalizer {

	@Override
	public String normalize(String token) {
		String translated = EmojiUtils.shortCodify(token);
		if (EmojiUtils.isEmoji(translated)) {
			return "";
		}
		return translated.replace("::", ": :");
	}

}
