package com.epam.jwd.comparator;

import com.epam.jwd.domain.Sentence;

import java.util.Comparator;

public class NumberOfWordsInSentenceComparator implements Comparator<Sentence> {
    @Override
    public int compare(Sentence o1, Sentence o2) {
        if (o1.getNumberOfWordsInSentence() == o2.getNumberOfWordsInSentence()) {
            return 0;
        } else if (o1.getNumberOfWordsInSentence() > o2.getNumberOfWordsInSentence())
            return 1;
        else
            return -1;
    }
}
