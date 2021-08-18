package com.epam.jwd.service;

import com.epam.jwd.Main;
import com.epam.jwd.domain.Sentence;
import com.epam.jwd.domain.Text;

import java.text.BreakIterator;
import java.util.LinkedList;
import java.util.List;

public class SentenceParser extends Parser {
    private List<Text> sentenses = new LinkedList<>();

    @Override
    public List<Text> split(String paragraph) {
        BreakIterator sentenceIterator = BreakIterator.getSentenceInstance();
        sentenceIterator.setText(paragraph);
        Main.logger.debug("Parsing paragraph on sentences");
        int start = sentenceIterator.first();
        for (int end = sentenceIterator.next();
             end != BreakIterator.DONE;
             start = end, end = sentenceIterator.next()) {
            linkWith(new WordParser());
            Sentence sentence = new Sentence();
            splitNext(paragraph.substring(start, end)).forEach(sentence::add);
            Main.logger.debug("Sentence is parsed -> " + sentence);
            sentenses.add(sentence);
        }
        return sentenses;
    }
}
