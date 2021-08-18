package com.epam.jwd.service;

import com.epam.jwd.Main;
import com.epam.jwd.comparator.NumberOfWordsInSentenceComparator;
import com.epam.jwd.domain.Paragraph;
import com.epam.jwd.domain.Sentence;
import com.epam.jwd.domain.Text;
import com.epam.jwd.domain.ContentWord;

import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;

public class TextEditor {
    private List<Text> fullText = new LinkedList<>();
    private final List<Sentence> allSentences = new LinkedList<>();
    private final HashSet<Text> allWords = new HashSet<>();
    private final HashSet<Text> allContentWords = new HashSet<>();

    public void loadElements(List<Text> list) {
        this.fullText = list;
        loadAllSentences();
        loadAllWords();
        loadContentWords();
    }

    public void printText() {
        fullText.forEach(Text::print);
    }

    private void loadAllSentences() {
        fullText.stream().filter(textElement -> textElement instanceof Paragraph).map(textElement -> ((Paragraph) textElement).getSentencesFromParagraph()).forEach(allSentences::addAll);
    }
    private void loadAllWords() {
        allSentences.stream().map(Sentence::getAllWords).forEach(allWords::addAll);
    }
    private void loadContentWords(){
        allSentences.stream().map(Sentence::getContentWords).forEach(allContentWords::addAll);
    }

    public List<Text> getFullText() {
        return fullText;
    }
    public List<Sentence> getAllSentences() {
        return allSentences;
    }
    public HashSet<Text> getAllWords() {
        return allWords;
    }
    public HashSet<Text> getAllContentWords() {
        return allContentWords;
    }



    public void findMaxNumberOfSentencesWithSameWord() {
        Main.logger.info("Task1");
        List<Sentence> temporarySentences = new LinkedList<>();
        HashSet<Sentence> sentencesWithWord = new HashSet<>();
        HashMap<ContentWord,Integer>  words = new HashMap<>();
        Iterator<Text> wordsIterator = allContentWords.iterator();
        Text checkedWord;
        while (wordsIterator.hasNext()) {
            checkedWord = wordsIterator.next();
            Iterator<Sentence> sentenceIterator = allSentences.iterator();
            while (sentenceIterator.hasNext()) {
                Sentence checkSentence = sentenceIterator.next();
                if (checkSentence.isContainWord(checkedWord)) {
                    temporarySentences.add(checkSentence);
                }
            }
            if (temporarySentences.size() > sentencesWithWord.size()) {
                words.clear();
                sentencesWithWord.clear();
                words.put((ContentWord) checkedWord,temporarySentences.size());
                sentencesWithWord.addAll(temporarySentences);
            }
            else if (temporarySentences.size() == sentencesWithWord.size()){
                words.put((ContentWord) checkedWord,temporarySentences.size());
                sentencesWithWord.addAll(temporarySentences);
            }
            temporarySentences.clear();
        }
        words.forEach((contentWord, numberOfSentences) -> System.out.println("Word - " + contentWord + "\nNumber of sentences: " + numberOfSentences ));
        System.out.println("Total number of sentences: " + sentencesWithWord.size() + "\n");
        sentencesWithWord.stream().sorted(new NumberOfWordsInSentenceComparator()).forEach(System.out::println);
    }
    public List<Text> sortSentences(Comparator<Sentence> sentenceComparator) {
        Main.logger.info("Sorting sentences (for task2)");
        return allSentences.stream().sorted(sentenceComparator).collect(Collectors.toList());
    }
    public void printWordsWithLengthFromInterrogativeSentences(int wordSize) {
        Main.logger.info("Task4");
        Iterator<Sentence> sentenceIterator = allSentences.listIterator();
        System.out.println("Size of word=" + wordSize + "\nSentence: ");
        while (sentenceIterator.hasNext()) {
            Sentence sentence = sentenceIterator.next();
            if (sentence.toString().endsWith("?")) {
                sentence.print();
                System.out.println("\nWord(s): ");
                Iterator<Text> wordIterator = sentence.getAllWords().listIterator();
                while (wordIterator.hasNext()) {
                    Text word = wordIterator.next();
                    if (word.getValue().length() == wordSize) {
                        word.print();
                        System.out.print(" ");
                    }
                }
            }
        }
    }
    public void swapFirstLastWordInSentence() {
        Main.logger.info("Task5");
        Iterator<Text> fullTextIterator = fullText.listIterator();
        while (fullTextIterator.hasNext()) {
            Text text = fullTextIterator.next();
            if (text instanceof Paragraph) {
                Iterator<Sentence> sentenceIterator = ((Paragraph) text).getSentencesFromParagraph().iterator();
                while (sentenceIterator.hasNext()) {
                    Sentence sentence = sentenceIterator.next();
                    ContentWord firstWord = (ContentWord) sentence.getAllWords().get(0);
                    ContentWord lastWord = (ContentWord) sentence.getAllWords().get(sentence.getNumberOfWordsInSentence() - 1);
                    sentence.swapFirstLastWord(firstWord, lastWord);
                }
            }
        }
    }
}



