package com.epam.jwd.service.parser;

import com.epam.jwd.domain.FunctionWord;
import com.epam.jwd.domain.Number;
import com.epam.jwd.domain.PunctuationMark;
import com.epam.jwd.domain.Space;
import com.epam.jwd.domain.Text;
import com.epam.jwd.domain.ContentWord;
import com.epam.jwd.service.reader.FunctionWordsReader;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.LinkedList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class WordParser extends BaseParser {
    private final static Logger logger = LogManager.getLogger(WordParser.class);
    private final static String START_PARSING_TO_WORD_MESSAGE = "Parsing sentence on words  -> ";
    private final static String PUNCTUATION_PATTERN_REGEX ="[,.;:’“”!?#\\-_–$%^&*=@(){}\\\\\\/]";
    private final static String NUMBER_PATTERN_REGEX = "[0-9]";
    private final static String SPACE_PATTERN_REGEX = "\\s";

    private final static Pattern PUNCTUATION_PATTERN = Pattern.compile(PUNCTUATION_PATTERN_REGEX);
    private final static Pattern NUMBER_PATTERN = Pattern.compile(NUMBER_PATTERN_REGEX);
    private final static Pattern SPACE_PATTERN = Pattern.compile(SPACE_PATTERN_REGEX);
    private List<Text> sentenceElements = new LinkedList<>();

    @Override
    public List<Text> split(String sentence) {
        logger.debug(START_PARSING_TO_WORD_MESSAGE + sentence);
        RegexParser.splitSentenceToWords(sentence)
                .forEach(element -> sentenceElements
                .add(getElementByType(element)));
        sentenceElements.add(new Space(" "));
        return sentenceElements;
    }

    private Text getElementByType(String sentenceElement) {
        Matcher punctuationMatcher = PUNCTUATION_PATTERN.matcher(sentenceElement);
        Matcher numberMatcher = NUMBER_PATTERN.matcher(sentenceElement);
        Matcher spaceMatcher = SPACE_PATTERN.matcher(sentenceElement);
        if (punctuationMatcher.find()) {
            return new PunctuationMark(sentenceElement);
        } else if (numberMatcher.find()) {
            return new Number(sentenceElement);
        } else if (spaceMatcher.find()) {
            return new Space(sentenceElement);
        } else if (isFunctionWord(sentenceElement)) {
            return new FunctionWord(sentenceElement);
        } else {
            return new ContentWord(sentenceElement);
        }
    }

    private static boolean isFunctionWord (String word){
        for (String s : FunctionWordsReader.getFunctionWordsList() ) {
            if (s.trim().equalsIgnoreCase(word.toLowerCase())){
                return true;
            }
        }
        return false;
    }
}

