package com.github.yuriiyukh.reader;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

import com.github.yuriiyukh.dao.DaoException;

public class PropertiesReader implements Reader {
    private static final String IAE_MESSAGE_EMPTY_STRING_PASSED = "Empty string is passed";
    private static final String IAE_MESSAGE_NULL_PASSED = "Null is passed";
    private String propertiesFileName;
    
    public PropertiesReader(String propertiesFileName) {
        this.propertiesFileName = propertiesFileName;
    }


    public String readData(String key) throws DaoException {
        validateInput(key);
        try (InputStream input = this.getClass().getClassLoader().getResourceAsStream(propertiesFileName)) {
            Properties prop = new Properties();
            prop.load(input);
            return prop.getProperty(key);

        } catch (IOException e) {
            throw new DaoException("Cant open the file", e);
        }
    }

    private void validateInput(String fileName) {
        if (fileName == null) {
            throw new IllegalArgumentException(IAE_MESSAGE_NULL_PASSED);
        }
        if (fileName.trim().length() == 0) {
            throw new IllegalArgumentException(IAE_MESSAGE_EMPTY_STRING_PASSED);
        }
    }

}
