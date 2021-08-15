package com.epam.jwd;

import com.epam.jwd.comparators.NumberOfWordsInSentenceComparator;
import com.epam.jwd.domain.Text;
import com.epam.jwd.service.ParagraphAndCodeBlcokParser;
import com.epam.jwd.service.Parser;
import com.epam.jwd.service.ReadFile;
import com.epam.jwd.service.TextEditor;

import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;

public class Main {
    public static void main(String[] args) {
        TextEditor textEditor = new TextEditor();
        Parser parser = new ParagraphAndCodeBlcokParser();
        List<Text> list = parser.split(ReadFile.readUsingBufferedReader("file1.txt"));
        textEditor.loadElements(list);
        //textEditor.getAllWords().forEach(x -> System.out.println(x));
       //textEditor.getAllWords().stream().sorted().forEach(x-> System.out.println(x));

      //  textEditor.getAllWords().stream().sorted().collect(Collectors.toList());

        //textEditor.Task5();
       // textEditor.printText();
       // textEditor.printText();


         textEditor.sortSentences(new NumberOfWordsInSentenceComparator());
    }
}
