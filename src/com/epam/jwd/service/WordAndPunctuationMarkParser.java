package com.epam.jwd.service;

import com.epam.jwd.domain.Number;
import com.epam.jwd.domain.PunctuationMark;
import com.epam.jwd.domain.Space;
import com.epam.jwd.domain.Text;
import com.epam.jwd.domain.Word;

import java.text.BreakIterator;
import java.util.LinkedList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class WordAndPunctuationMarkParser extends Parser {
    private final Pattern punctuationPattern = Pattern.compile("[,.;:’“”!?#\\-_–$%^&*=@(){}\\\\\\/]");
    private final Pattern numberPattern = Pattern.compile("[0-9]");
    private final Pattern spacePattern = Pattern.compile("\\s");
    //private final Pattern specialWords = Pattern.compile("dont");
    private List<Text> list = new LinkedList<>();

    @Override
    public List<Text> split(String text) {
        BreakIterator wordIterator = BreakIterator.getWordInstance();
        wordIterator.setText(text);
        int start = wordIterator.first();
        for (int end = wordIterator.next();
             end != BreakIterator.DONE;
             start = end, end = wordIterator.next()) {
            String word = text.substring(start, end);
            Matcher punctuatuinMatcher = punctuationPattern.matcher(word);
            Matcher numberMatcher = numberPattern.matcher(word);
            Matcher spaceMatcher = spacePattern.matcher(word);
            if (punctuatuinMatcher.find()) {
                list.add(new PunctuationMark(word));
            } else if (numberMatcher.find()) {
                list.add(new Number(word));
            }else if (spaceMatcher.find()) {
                list.add(new Space(word));
            } else {
                list.add(new Word(word));
            }
        }
        return list;
    }
}
