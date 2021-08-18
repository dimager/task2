package com.epam.jwd;

import com.epam.jwd.comparator.NumberOfWordsInSentenceComparator;
import com.epam.jwd.service.FunctionWordsReader;
import com.epam.jwd.service.ParagraphAndCodeBlockParser;
import com.epam.jwd.service.Parser;
import com.epam.jwd.service.FileReader;
import com.epam.jwd.service.TextEditor;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.core.config.Configurator;

public class Main {
    public static Logger logger = LogManager.getLogger();

    public static void main(String[] args) {
        TextEditor textEditor = new TextEditor();
//        Configurator.setLevel("com.epam.jwd",Level.DEBUG);
        Configurator.setLevel("com.epam.jwd",Level.INFO);
        Parser parser = new ParagraphAndCodeBlockParser();
        FunctionWordsReader.loadFunctionWordsFromFile("functionwords.txt");
        textEditor.loadElements(parser.split(FileReader.readTextFile("file1.txt")));

        // functionality 1
          textEditor.findMaxNumberOfSentencesWithSameWord();

       // functionality 2
        textEditor.sortSentences(new NumberOfWordsInSentenceComparator()).forEach(System.out::println);

        // functionality 4
        textEditor.printWordsWithLengthFromInterrogativeSentences(4);

        // functionality 5
       // textEditor.swapFirstLastWordInSentence();

        textEditor.printText();
        //textEditor.getFullText().forEach(Text::print);
       //textEditor.getAllWords().stream().sorted(new WordComparator()).forEach(System.out::println);
    }
}
