package com.epam.jwd.service;

import com.epam.jwd.Main;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

public class FileReader {
    public static String readTextFile(String fileName) {
        try {
            Main.logger.debug("Reading file " + fileName); ;
            return new String(Files.readAllBytes(Paths.get(fileName)));
        } catch (IOException e) {
            Main.logger.error("FileReader IO exception");
           // e.printStackTrace();
        }
        return null;
    }
}
