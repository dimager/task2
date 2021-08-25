package com.epam.jwd.domain;

import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;

public class Sentence extends BaseText {
    private List<Text> sentenceElements = new LinkedList<>();

    public void add(Text sentenceElement) {
        this.sentenceElements.add(sentenceElement);
    }

    public void remove(Text sentenceElement) {
        this.sentenceElements.remove(sentenceElement);
    }

    public int getSentenceLength() {
        return sentenceElements.size();
    }

    public int getNumberOfWordsInSentence() {
        return getAllWords().size();
    }
    public int getNumberOfContentWordsInSentence() {
        return getContentWords().size();
    }

    public List<Text> getAllWords() {
        return sentenceElements.stream()
                .filter(word -> word instanceof ContentWord || word instanceof FunctionWord)
                .collect(Collectors.toList());
    }

    public List<Text> getContentWords() {
        return sentenceElements.stream()
                .filter(word -> word instanceof ContentWord)
                .collect(Collectors.toList());
    }

    public List<Text> getSentenceElements() {
        return sentenceElements;
    }

    public boolean isContainWord(Text word) {
        return getContentWords().stream()
                .anyMatch(sentenceWords -> sentenceWords.getValue().equalsIgnoreCase(word.getValue()));
    }

    @Override
    public void print() {
        sentenceElements.forEach(Text::print);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;
        Sentence sentence = (Sentence) o;
        return sentenceElements != null ? sentenceElements.equals(sentence.sentenceElements) : sentence.sentenceElements == null;
    }

    @Override
    public int hashCode() {
        int result = super.hashCode();
        result = 31 * result + (sentenceElements != null ? sentenceElements.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        StringBuilder sentence = new StringBuilder();
        sentenceElements.forEach(text -> sentence.append(text.getValue()));
        return sentence.toString();
    }
}