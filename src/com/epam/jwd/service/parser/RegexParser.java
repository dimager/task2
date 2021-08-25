package com.epam.jwd.service.parser;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

class RegexParser {
    private static final String SENTENCE_REGEX = "(?<=[\\Q.!?;\\E])\\s+";
    private static final String WORD_REGEX = "(\\w[’']\\w)|\\s|\\w+|\\W";
    private static final Logger logger = LogManager.getLogger(RegexParser.class);
    private static final String START_SPLIT_PARAGRAPH_MESSAGE = "Parsing paragraph to sentences";


    public static List<String> splitParagraphToSentences(String paragraph) {
        logger.debug(START_SPLIT_PARAGRAPH_MESSAGE);
        return Arrays.asList(paragraph.split(SENTENCE_REGEX));
    }

    public static List<String> splitSentenceToWords(String sentence) {
        Pattern pattern = Pattern.compile(WORD_REGEX);
        Matcher matcher = pattern.matcher(sentence);
        List<String> words = new LinkedList<>();
        while (matcher.find()) {
            words.add(sentence.substring(matcher.start(), matcher.end()));
        }
        return words;
    }
}
