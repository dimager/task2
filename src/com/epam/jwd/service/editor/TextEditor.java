package com.epam.jwd.service.editor;

import com.epam.jwd.domain.ContentWord;
import com.epam.jwd.domain.Paragraph;
import com.epam.jwd.domain.Sentence;
import com.epam.jwd.domain.Text;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class TextEditor {
    private final static String WORD_BEGINNING_WITH_A_VOWEL_REGEX = "[a|e|i|o|u|y]";
    private final static String FIRST_CONSONANT_IN_WORD_REGEX = "[w|r|t|p|s|d|f|g|h|j|k|l|m|n|b|v|c|x|z]";
    private final static String FIND_MAX_NUMBER_OF_SENTENCES_WITH_SAME_WORD_MESSAGE = " Find max number of sentences with same word (func. 1)";
    private final static String SORT_SENTENCES_MESSAGE = " Sort sentences (func. 2)";
    private final static String PRINT_WORDS_WITH_LENGTH_FROM_INTERROGATIVE_SENTENCES_MESSAGE = " Print words with length from interrogative sentences (func. 4)";
    private final static String SWAP_FIRST_LAST_WORD_IN_SENTENCE_MESSAGE = " Swap first and last word in sentence (func. 5)";
    private final static String VOWEL_WORDS_ARE_SORTED_MESSAGE = "Vowel words are sorted by first consonant letter (func. 8)";
    private final static String SORTED_BY_LETTER_FREQUENCY = "Words are sorted by letter frequency (func. 9)";
    private final static String SIZE_OF_WORD_MESSAGE = " Size of word = ";
    private final static String SENTENCE_MESSAGE = " Sentence: ";
    private final static String SENTENCES_MESSAGE = " Sentences: ";
    private static final String GET_FISRT_CONSONANT_EXEPTION = "getFirstConsonant return null";
    private final static String WORDS_MESSAGE = " Words: ";
    private final static String WORD_MESSAGE = " Word - ";
    private final static String TOTAL_NUMBER_OF_SENTENCES_MESSAGE = " Total number of sentences: ";
    private final static String SWAP_FIRST_LAST_MESSAGE = " Swap words (Before and after sentence)";
    private final static Logger logger = LogManager.getLogger(TextEditor.class);
    private final List<Sentence> allSentences = new LinkedList<>();
    private final HashSet<Text> allWords = new HashSet<>();
    private final HashSet<Text> allContentWords = new HashSet<>();
    private List<Text> fullText = new LinkedList<>();

    public void loadElements(List<Text> parsedText) {
        this.fullText = parsedText;
        loadAllSentences();
        loadAllWords();
        loadContentWords();
    }

    public void printText() {
        fullText.stream().forEach(Text::print);
    }

    private void loadAllSentences() {
        fullText.stream()
                .filter(textElement -> textElement instanceof Paragraph)
                .map(textElement -> ((Paragraph) textElement)
                .getSentencesFromParagraph())
                .forEach(allSentences::addAll);
    }

    private void loadAllWords() {
        allSentences.stream()
                .map(Sentence::getAllWords)
                .forEach(allWords::addAll);
    }

    private void loadContentWords() {
        allSentences.stream()
                .map(Sentence::getContentWords)
                .forEach(allContentWords::addAll);
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
        logger.info(FIND_MAX_NUMBER_OF_SENTENCES_WITH_SAME_WORD_MESSAGE);
        List<Sentence> temporarySentences = new LinkedList<>();
        HashSet<Sentence> sentencesWithWord = new HashSet<>();
        HashMap<ContentWord, Integer> words = new HashMap<>();
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
                words.put((ContentWord) checkedWord, temporarySentences.size());
                sentencesWithWord.addAll(temporarySentences);
            } else if (temporarySentences.size() == sentencesWithWord.size()) {
                words.put((ContentWord) checkedWord, temporarySentences.size());
                sentencesWithWord.addAll(temporarySentences);
            }
            temporarySentences.clear();
        }
        words.forEach((contentWord, numberOfSentences) -> logger.info(WORD_MESSAGE + contentWord + TOTAL_NUMBER_OF_SENTENCES_MESSAGE + numberOfSentences));
        logger.info(TOTAL_NUMBER_OF_SENTENCES_MESSAGE + sentencesWithWord.size() + SENTENCES_MESSAGE);
        sentencesWithWord.forEach(logger::info);
    }

    public void sortSentences() {
        logger.info(SORT_SENTENCES_MESSAGE);
        allSentences.stream()
                .sorted(Comparator.comparingInt(Sentence::getNumberOfWordsInSentence))
                .forEach(logger::info);
    }

    public void printWordsWithLengthFromInterrogativeSentences(int wordSize) {
        logger.info(PRINT_WORDS_WITH_LENGTH_FROM_INTERROGATIVE_SENTENCES_MESSAGE);
        Iterator<Sentence> sentenceIterator = allSentences.listIterator();
        logger.info(SIZE_OF_WORD_MESSAGE + wordSize );
        while (sentenceIterator.hasNext()) {
            Sentence sentence = sentenceIterator.next();
            if (sentence.toString().trim().endsWith("?")) {
                logger.info(SENTENCE_MESSAGE + sentence);
                Iterator<Text> wordIterator = sentence.getAllWords().listIterator();
                StringBuilder builder = new StringBuilder();
                while (wordIterator.hasNext()) {
                    Text word = wordIterator.next();
                    if (word.getValue().length() == wordSize) {
                        builder.append(word.getValue()).append(" ");
                    }
                }
                logger.info(WORDS_MESSAGE + builder);
            }
        }
    }

    public void swapFirstLastWordInSentence() {
        logger.info(SWAP_FIRST_LAST_WORD_IN_SENTENCE_MESSAGE);
        Iterator<Text> fullTextIterator = fullText.listIterator();
        while (fullTextIterator.hasNext()) {
            Text text = fullTextIterator.next();
            if (text instanceof Paragraph) {
                Iterator<Sentence> sentenceIterator = ((Paragraph) text).getSentencesFromParagraph().iterator();
                while (sentenceIterator.hasNext()) {
                    Sentence sentence = sentenceIterator.next();
                    logger.info(SWAP_FIRST_LAST_MESSAGE);
                    logger.info(sentence);
                    Collections.swap(sentence.getSentenceElements(),
                            sentence.getSentenceElements().indexOf(sentence.getAllWords().get(0)),
                            sentence.getSentenceElements().lastIndexOf(sentence.getAllWords().get(sentence.getNumberOfWordsInSentence()-1)));
                    logger.info(sentence);
                }
            }
        }
    }

    public void sortWordsByLetterFrequency(String value) {
        logger.info(SORTED_BY_LETTER_FREQUENCY);
        getAllContentWords().stream()
                .sorted(Comparator.comparingInt(o -> TextEditor.getCountOfLetterInWord(o, value)))
                .forEach(word -> logger.info(word.getValue()));
    }

    public static int getCountOfLetterInWord(Text word, String value){
        Pattern pattern = Pattern.compile(value,Pattern.CASE_INSENSITIVE);
        Matcher matcher = pattern.matcher(word.getValue());
        int i = 0;
        while (matcher.find()){
            i++;
        }
       return i;
    }

    public void vowelWordsSortedByConsonants(){
            logger.info(VOWEL_WORDS_ARE_SORTED_MESSAGE);
        try {
            getAllContentWords().stream()
                    .filter(word -> isFirstLetterVowel(word.getValue()))
                    .sorted((o1, o2) -> getFirstConsonant(o1.getValue()).compareToIgnoreCase(getFirstConsonant(o2.getValue())))
                    .forEach(word -> logger.info(word.getValue()));
        } catch (NullPointerException e) {
            logger.error(GET_FISRT_CONSONANT_EXEPTION, e);
        }
    }

    private static boolean isFirstLetterVowel(String s) {
        Pattern pattern = Pattern.compile(WORD_BEGINNING_WITH_A_VOWEL_REGEX, Pattern.CASE_INSENSITIVE);
        Matcher matcher = pattern.matcher(s);
        return matcher.region(0, 1).find();
    }

    private static String getFirstConsonant(String s) {
        Pattern pattern = Pattern.compile(FIRST_CONSONANT_IN_WORD_REGEX, Pattern.CASE_INSENSITIVE);
        Matcher matcher = pattern.matcher(s);
        if (matcher.find()) {
            return matcher.group();
        }
        return null;
    }

}





