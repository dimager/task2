package com.epam.jwd.service.reader;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

public class FileReader {
    private final static Logger logger = LogManager.getLogger(FileReader.class);
    public static String readTextFile(String fileName) {
        try {
            logger.debug("Reading file " + fileName);
            return new String(Files.readAllBytes(Paths.get(fileName)));
        } catch (IOException e) {
            logger.error("FileReader IO exception", e);
        }
        return null;
    }
}
