package com.epam.jwd.service.parser;

import com.epam.jwd.domain.Sentence;
import com.epam.jwd.domain.Text;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

public class SentenceParser extends BaseParser {
    private List<Text> sentenses = new LinkedList<>();
    private final static Logger logger = LogManager.getLogger(SentenceParser.class);

    @Override
    public List<Text> split(String paragraph) {
        Iterator<String> listIterator = RegexParser.splitParagraphToSentences(paragraph).listIterator();
        while (listIterator.hasNext()) {
            linkWith(new WordParser());
            Sentence sentence = new Sentence();
            splitNext(listIterator.next()).forEach(sentence::add);
            logger.debug("Sentence is parsed");
            sentenses.add(sentence);
        }
        return sentenses;
    }
}



