package com.epam.jwd.service;

import com.epam.jwd.domain.Sentence;
import com.epam.jwd.domain.Text;

import java.text.BreakIterator;
import java.util.LinkedList;
import java.util.List;

public class SentenceParser extends Parser {
    List<Text> list = new LinkedList<>();

    @Override
    public List<Text> split(String text) {
        BreakIterator sentenceIterator = BreakIterator.getSentenceInstance();
        sentenceIterator.setText(text);
        int start = sentenceIterator.first();
        for (int end = sentenceIterator.next();
             end != BreakIterator.DONE;
             start = end, end = sentenceIterator.next()) {
            linkWith(new WordAndPunctuationMarkParser());
            Sentence sentence = new Sentence();
            splitNext(text.substring(start, end)).forEach(text1 -> sentence.add(text1));
            list.add(sentence);
        }
        return list;
    }
}
