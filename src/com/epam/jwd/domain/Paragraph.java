package com.epam.jwd.domain;

import java.util.LinkedList;
import java.util.List;

public class Paragraph extends BaseText {
    List<Sentence> sentences = new LinkedList<>();

    @Override
    public void print() {
        sentences.forEach(sentence -> sentence.print());
        System.out.println();
    }

    public void add(Text sentence) {
        sentences.add((Sentence) sentence);
    }

   public void remove(Text sentence) {
        sentences.remove((Sentence) sentence);
    }

   public List<Sentence> getSentencesFromParagraph() {
        return sentences;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;
        Paragraph paragraph = (Paragraph) o;
        return sentences != null ? sentences.equals(paragraph.sentences) : paragraph.sentences == null;
    }

    @Override
    public int hashCode() {
        int result = super.hashCode();
        result = 31 * result + (sentences != null ? sentences.hashCode() : 0);
        return result;
    }
}
