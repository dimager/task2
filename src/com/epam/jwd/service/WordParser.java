package com.epam.jwd.service;

import com.epam.jwd.Main;
import com.epam.jwd.domain.FunctionWord;
import com.epam.jwd.domain.Number;
import com.epam.jwd.domain.PunctuationMark;
import com.epam.jwd.domain.Space;
import com.epam.jwd.domain.Text;
import com.epam.jwd.domain.ContentWord;

import java.text.BreakIterator;
import java.util.LinkedList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class WordParser extends Parser {
    private final Pattern punctuationPattern = Pattern.compile("[,.;:’“”!?#\\-_–$%^&*=@(){}\\\\\\/]");
    private final Pattern numberPattern = Pattern.compile("[0-9]");
    private final Pattern spacePattern = Pattern.compile("\\s");
    private List<Text> sentenceElements = new LinkedList<>();

    @Override
    public List<Text> split(String sentence) {
        Main.logger.debug("Parsing sentence on words" + sentence);
        BreakIterator sentenceElementIterator = BreakIterator.getWordInstance();
        sentenceElementIterator.setText(sentence);
        int start = sentenceElementIterator.first();
        for (int end = sentenceElementIterator.next();
             end != BreakIterator.DONE;
             start = end, end = sentenceElementIterator.next()) {
            sentenceElements.add(getElementByType(sentence.substring(start, end)));
        }
        return sentenceElements;
    }

    private Text getElementByType(String sentenceElement) {
        Matcher punctuationMatcher = punctuationPattern.matcher(sentenceElement);
        Matcher numberMatcher = numberPattern.matcher(sentenceElement);
        Matcher spaceMatcher = spacePattern.matcher(sentenceElement);
        if (punctuationMatcher.find()) {
            return new PunctuationMark(sentenceElement);
        } else if (numberMatcher.find()) {
            return new Number(sentenceElement);
        } else if (spaceMatcher.find()) {
            return new Space(sentenceElement);
        } else if (FunctionWordsReader.isFunctionWord(sentenceElement)) {
            return new FunctionWord(sentenceElement);
        } else {
            return new ContentWord(sentenceElement);
        }
    }
}

