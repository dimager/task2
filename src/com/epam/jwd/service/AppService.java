package com.epam.jwd.service;

import com.epam.jwd.service.editor.TextEditor;
import com.epam.jwd.service.parser.BaseParser;
import com.epam.jwd.service.parser.ParagraphAndCodeBlockParser;
import com.epam.jwd.service.reader.FileReader;
import com.epam.jwd.service.reader.FunctionWordsReader;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.core.config.Configurator;

import java.util.Scanner;

public class AppService {
    private final static Logger logger = LogManager.getLogger(AppService.class);
    private final static String ENTER_WORD_LENGTH_MESSAGE = "Enter word length:";
    private final static String INCORRECT_CHOISE_MESSAGE = "Incorrect choice";
    private final static String TEXT_FILE_NAME = "text.txt";
    private final static String FUNCTION_WORDS_FILE_NAME = "functionwords.txt";
    private final static String INPUT_LETTER_MESSAGE = "Input letter:";
    private static boolean whileState = true;

    private final static String MENU_TEXT = "\nMenu:\n" +
            "1. - Print text\n" +
            "2. - Find max number of sentences with same word (func. 1)\n" +
            "3. - Sort sentences (func. 2)\n" +
            "4. - Print words with length from interrogative sentences (func. 4)\n" +
            "5. - Swap first and last word in sentence (func. 5)\n" +
            "6. - Vowel words are sorted by first consonant letter (func. 8)\n" +
            "7. - Words are sorted by letter frequency (func. 9)\n" +
            "8. - Exit\n" +
            "Select: ";

    public void start(Level logLevel) {
        TextEditor textEditor = new TextEditor();
        Configurator.setLevel("com.epam.jwd", logLevel);
        BaseParser parser = new ParagraphAndCodeBlockParser();
        FunctionWordsReader.loadFunctionWordsFromFile(FUNCTION_WORDS_FILE_NAME);
        textEditor.loadElements(parser.split(FileReader.readTextFile(TEXT_FILE_NAME)));
        while (whileState) {
            logger.info(MENU_TEXT);
            switch (readInt()) {
                case 1:
                    textEditor.printText();
                    break;
                case 2:
                    textEditor.findMaxNumberOfSentencesWithSameWord();
                    break;
                case 3:
                    textEditor.sortSentences();

                    break;
                case 4:
                    logger.info(ENTER_WORD_LENGTH_MESSAGE);
                    textEditor.printWordsWithLengthFromInterrogativeSentences(readInt());
                    break;
                case 5:
                    textEditor.swapFirstLastWordInSentence();
                    break;
                case 6:
                    textEditor.vowelWordsSortedByConsonants();
                    break;
                case 7:
                    textEditor.sortWordsByLetterFrequency(readLetter());
                    break;
                case 8:
                    whileState = false;
                    break;
                default:
                    break;
            }
        }
    }

    private  static int readInt() {
        Scanner scanner = new Scanner(System.in);
        int choise = 0;
        try {
            choise = scanner.nextInt();
        } catch (Exception e) {
            whileState = false;
            logger.error(INCORRECT_CHOISE_MESSAGE, e);
        }
        return choise;
    }

    private static String readLetter() {
        logger.info(INPUT_LETTER_MESSAGE);
        Scanner scanner = new Scanner(System.in);
        char c = 0;
        try {
            c = scanner.nextLine().toLowerCase().charAt(0);
        } catch (Exception e) {
            whileState = false;
            logger.error(INCORRECT_CHOISE_MESSAGE, e);
        }
        return String.valueOf(c);
    }
}
