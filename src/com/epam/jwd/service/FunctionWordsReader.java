package com.epam.jwd.service;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

public class FunctionWordsReader {
    private static List<String> functionWordsList = new LinkedList<>();
    public static void loadFunctionWordsFromFile(String fileName){
        String functionWords = FileReader.readTextFile(fileName);
        functionWordsList = Arrays.asList(functionWords.split(";"));
    }
    public static boolean isFunctionWord (String word){
        for (String s : functionWordsList) {
            if (s.trim().equalsIgnoreCase(word.toLowerCase())){
                return true;
            }
        }
        return false;
    }
}
