package com.epam.jwd.comparators;

import com.epam.jwd.domain.Text;

import java.util.Comparator;

public class WordComparator implements Comparator<Text> {
    @Override
    public int compare(Text o1, Text o2) {
        return o1.getValue().compareTo(o2.getValue());
    }
}
