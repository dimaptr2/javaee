package ru.velkomfood.mrp.book.server.config;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.annotation.PostConstruct;
import java.io.IOException;
import java.io.InputStream;
import java.util.Map;
import java.util.Properties;
import java.util.concurrent.ConcurrentHashMap;

public class ParametersHolder {

    private static final Logger LOGGER = LoggerFactory.getLogger(ParametersHolder.class);
    private Map<String, String> sapQueryParameters;

    public ParametersHolder() {
        sapQueryParameters = new ConcurrentHashMap<>();
    }

    @PostConstruct
    public void prepare() {
        addPropertiesToMap(sapQueryParameters, "/sap-query.properties");
    }

    public Map<String, String> getSapQueryParameters() {
        return sapQueryParameters;
    }


    // private section

    private void addPropertiesToMap(Map<String, String> map, String fileName) {

        Properties properties = new Properties();

        try (InputStream is = getClass().getResourceAsStream(fileName)) {
            properties.load(is);
        } catch (IOException ioe) {
            LOGGER.error(ioe.getMessage());
        }

        for (Map.Entry<Object, Object> entry : properties.entrySet()) {
            map.put(String.valueOf(entry.getKey()), String.valueOf(entry.getValue()));
        }

    }

}
