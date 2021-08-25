package com.epam.jwd.service.reader;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

public class FunctionWordsReader {
    private final static Logger logger = LogManager.getLogger(FunctionWordsReader.class);
    private final static String FUNCTION_WORD_SPLIT_EXEPTION = "functionWords.split return null";
    private static List<String> functionWordsList = new LinkedList<>();

    public static void loadFunctionWordsFromFile(String fileName) {
        String functionWords = FileReader.readTextFile(fileName);
        try {
            functionWordsList = Arrays.asList(functionWords.split(";"));
        } catch (NullPointerException e) {
            logger.error(FUNCTION_WORD_SPLIT_EXEPTION, e);
        }
    }

    public static List<String> getFunctionWordsList() {
        return functionWordsList;
    }
}
