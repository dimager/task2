package com.epam.jwd;

import com.epam.jwd.service.AppService;
import org.apache.logging.log4j.Level;

public class Main {
    public static void main(String[] args) {
        AppService appService = new AppService();
        appService.start(Level.INFO);
    }
}
