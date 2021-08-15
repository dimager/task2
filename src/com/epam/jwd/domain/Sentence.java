package com.epam.jwd.domain;

import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Locale;
import java.util.stream.Collectors;

public class Sentence extends BaseText {
    List<Text> sentenceElements = new LinkedList<>();

    public void add(Text sentenceElement) {
        this.sentenceElements.add(sentenceElement);
    }
    public void remove(Text sentenceElement) {
        this.sentenceElements.remove(sentenceElement);
    }

    public String getSentenceString(){
        StringBuilder builder = new StringBuilder();
        sentenceElements.forEach(text -> builder.append(text.getValue()));
        return builder.toString();
    }

    public int getSentenceLenght(){
        return sentenceElements.size();
    }

    public int getNumberOfWordsInSentense(){
        return  getSentenceWordsList().size();
    }

    public List<Text> getSentenceWordsList() {
        return sentenceElements.stream().filter(text -> text instanceof Word).collect(Collectors.toList());
    }

    public void swapFirstLastWord(Word firstWord, Word lastWord){
        int indexOfFirstWord = sentenceElements.indexOf(firstWord);
        int indexOfLastWord = sentenceElements.lastIndexOf(lastWord);
        sentenceElements.remove(indexOfFirstWord);
        sentenceElements.add(indexOfFirstWord,lastWord);
        sentenceElements.remove(indexOfLastWord);
        sentenceElements.add(indexOfLastWord,firstWord);
    }

    public boolean isContainWord(String word){
        Iterator<Text> wordIterator = getSentenceWordsList().iterator();
        while (wordIterator.hasNext()){
            if (wordIterator.next().getValue().toLowerCase(Locale.ROOT).contains(word)){
                return true;
            }
        }
        return false;
        //return getSentenceWordsList().forEach();
       // return getSentenceString().toLowerCase().contains(word);
    }

    @Override
    public void print() {
        sentenceElements.forEach(sentenceElement -> System.out.print(sentenceElement.getValue()));
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
}
