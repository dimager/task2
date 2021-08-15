package com.epam.jwd.comparators;

import com.epam.jwd.domain.Sentence;
import com.epam.jwd.domain.Text;

import java.util.Comparator;

public class NumberOfWordsInSentenceComparator implements Comparator<Sentence> {
    @Override
    public int compare(Sentence o1, Sentence o2) {
        if (o1.getNumberOfWordsInSentense() == o2.getNumberOfWordsInSentense()){
            return 0;
        } else if (o1.getNumberOfWordsInSentense() > o2.getNumberOfWordsInSentense())
            return 1;
        else
            return -1;
    }

}
