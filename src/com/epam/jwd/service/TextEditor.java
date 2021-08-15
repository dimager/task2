package com.epam.jwd.service;

import com.epam.jwd.domain.Paragraph;
import com.epam.jwd.domain.Sentence;
import com.epam.jwd.domain.Text;
import com.epam.jwd.domain.Word;

import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Locale;
import java.util.stream.Collectors;

public class TextEditor {
    private final String specialwords = "of the in by on at by a up an to it as from for are is";
    private List<Text> fullText = new LinkedList<>();
    private List<Sentence> allSentences = new LinkedList<>();
    private HashSet<Text> allWords = new HashSet();
    private HashSet<String> allWordsString = new HashSet();

    public void loadElements(List<Text> list) {
        this.fullText = list;
        loadAllSentences();
        loadAllWords();
    }

    public void printText() {
        fullText.forEach(text -> text.print());
    }

    private void loadAllSentences() {
        allSentences.clear();
        for (Text text : fullText) {
            if (text instanceof Paragraph) {
                allSentences.addAll(((Paragraph) text).getSentencesFromParagraph());
            }
        }
    }

    private void loadAllWords() {
        for (Text sentence : allSentences) {
            allWords.addAll(((Sentence) sentence).getSentenceWordsList());
        }
        allWordsString.addAll(allWords.stream().map(Text::getValue).map(String::toLowerCase).collect(Collectors.toList()));
    }

    public void sortSentences(Comparator<Sentence> textComparator) {
        //allSentences.stream().sorted(textComparator).forEach(text -> System.out.println(text.getSentenceString()));
        allSentences.stream().sorted(textComparator).forEach(text -> text.print());
    }

    public List<Sentence> getAllSentences() {
        return allSentences;
    }

    public HashSet<String> getAllWords() {
        return allWordsString;
    }

    public void Task1() {
        HashMap<String, Sentence> hashMap = new HashMap<>();
        int hit = 0;
        int max = 0;
        for (String s : allWordsString) {
            if (!specialwords.contains(s) & s.length() > 1) {
                for (Text sentence : allSentences) {
                    hit = 0;
                    for (Text sentenceWord : ((Sentence) sentence).getSentenceWordsList()) {
                        if (sentenceWord.getValue().toLowerCase(Locale.ROOT).equals(s.toLowerCase(Locale.ROOT))) {
                            hit++;
                            if (hit > 0) {
                                if (max < hit) {
                                    max = hit;
                                    hashMap.clear();
                                    hashMap.put(s, (Sentence) sentence);
                                } else if (max == hit) {
                                    hashMap.put(s, (Sentence) sentence);
                                }
                            }
                        }
                    }
                }
            }
        }
        System.out.println(max);
        hashMap.forEach((s, sentence) -> System.out.println("Word: " + s + "\nSentence: " + sentence.getSentenceString()));
    }

    public void Task1a() {
        List<Sentence> temporaryList = new LinkedList<>();
        List<Sentence> sentencesWithWord = new LinkedList<>();
        String word = new String();
        String s = new String();
        Iterator<String> wordsIterator = allWordsString.iterator();
        while (wordsIterator.hasNext()) {
            s = wordsIterator.next();
            Iterator<Sentence> sentenceIterator = allSentences.iterator();
            while (sentenceIterator.hasNext()) {
                Sentence checkSentence = sentenceIterator.next();
                if (checkSentence.isContainWord(s) & !specialwords.contains(s) & s.length() > 1) {
                    temporaryList.add(checkSentence);
                }
            }
            if (temporaryList.size() > sentencesWithWord.size()) {
                word = s;
                sentencesWithWord.clear();
                sentencesWithWord.addAll(temporaryList);
            }
            temporaryList.clear();
        }
        System.out.println(word);
        System.out.println(sentencesWithWord.size());
        sentencesWithWord.forEach(sentence -> System.out.println(sentence.getSentenceString()));
    }

    public void Task3() {
        System.out.println(allSentences.iterator());
    }

    public void Task4(int wordSize) {
        Iterator<Sentence> sentenceIterator = allSentences.listIterator();
        while (sentenceIterator.hasNext()) {
            Sentence sentence = sentenceIterator.next();
            if (sentence.getSentenceString().endsWith("?")) {
                System.out.print("Size of word=" + wordSize + "\nSentence: ");
                sentence.print();
                System.out.println("\nWord(s): ");
                Iterator<Text> wordIterator = sentence.getSentenceWordsList().listIterator();
                while (wordIterator.hasNext()) {
                    Text word = wordIterator.next();
                    if (word.getValue().length() == wordSize) {
                        word.print();
                    }
                }
            }
        }
    }

    public void Task5() {
        Iterator<Text> fullTextIterator = fullText.listIterator();
        while (fullTextIterator.hasNext()) {
            Text text = fullTextIterator.next();
            if (text instanceof Paragraph) {
                Iterator<Sentence> sentenceIterator = ((Paragraph) text).getSentencesFromParagraph().iterator();
                while (sentenceIterator.hasNext()) {
                    Sentence sentence = sentenceIterator.next();
                    Word firstWord = (Word) sentence.getSentenceWordsList().get(0);
                    Word lastWord = (Word) sentence.getSentenceWordsList().get(sentence.getNumberOfWordsInSentense() - 1);
                    sentence.swapFirstLastWord(firstWord, lastWord);
                    //  sentence.print();
                }
            } else {
                // text.print();
            }
        }
    }
}



