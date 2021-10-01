package com.entis.service.impl;

import com.entis.service.PropertyLoader;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.Properties;

public class FilePropertiesLoader implements PropertyLoader {
    private static final Logger LOGGER = LoggerFactory.getLogger(FilePropertiesLoader.class);

    private void logError(Exception e){
        LOGGER.info("Error: " + e.getMessage());
    }

    public Properties getProperty(String filePath) {

        LOGGER.info("Trying to get properties...");
        Properties property = new Properties();
        try (BufferedReader input = new BufferedReader(new FileReader(filePath))) {
            property.load(input);
        } catch (IOException e) {
            logError(e);
        }
        return property;
    }
}
