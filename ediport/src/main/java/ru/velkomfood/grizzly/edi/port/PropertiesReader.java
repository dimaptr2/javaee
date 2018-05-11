package ru.velkomfood.grizzly.edi.port;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.io.InputStream;
import java.util.Map;
import java.util.Properties;
import java.util.concurrent.ConcurrentHashMap;

public class PropertiesReader {

    private static final Logger LOG = LoggerFactory.getLogger(PropertiesReader.class);
    private static PropertiesReader instance;

    private PropertiesReader() { }

    public static PropertiesReader createInstance() {
        if (instance == null) {
            instance = new PropertiesReader();
        }
        return instance;
    }

    public Map<String, String> readFileIntoMap(String fileName) {

        Map<String, String> map = new ConcurrentHashMap<>();
        Properties properties = new Properties();

        InputStream is = getClass().getResourceAsStream(fileName);
        try {
            properties.load(is);
            is.close();
            for (Map.Entry<Object, Object> entry : properties.entrySet()) {
                map.put(String.valueOf(entry.getKey()), String.valueOf(entry.getValue()));
            }
        } catch (IOException ioe) {
            LOG.error(ioe.getMessage());
        }

        return map;
    }

}
