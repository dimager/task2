package com.epam.jwd.domain;

import java.util.LinkedList;
import java.util.List;

public class Paragraph extends BaseText {
    List<Sentence> paragraphSentences = new LinkedList<>();

    @Override
    public void print() {
        paragraphSentences.forEach(Sentence::print);
        System.out.println();
    }

    public void add(Text sentence) {
        paragraphSentences.add((Sentence) sentence);
    }

    public void remove(Text sentence) {
        paragraphSentences.remove((Sentence) sentence);
    }

    public List<Sentence> getSentencesFromParagraph() {
        return paragraphSentences;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;
        Paragraph paragraph = (Paragraph) o;
        return paragraphSentences != null ? paragraphSentences.equals(paragraph.paragraphSentences) : paragraph.paragraphSentences == null;
    }

    @Override
    public int hashCode() {
        int result = super.hashCode();
        result = 31 * result + (paragraphSentences != null ? paragraphSentences.hashCode() : 0);
        return result;
    }
}
