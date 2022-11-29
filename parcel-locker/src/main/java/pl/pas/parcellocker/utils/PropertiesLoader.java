package pl.pas.parcellocker.utils;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class PropertiesLoader implements AutoCloseable {

    private static Properties loadProperties() {

        try( InputStream inputStream = PropertiesLoader.class
            .getClassLoader()
            .getResourceAsStream("application.properties")){
            Properties configuration = new Properties();

            configuration.load(inputStream);
            return configuration;
        } catch (IOException e) {
            throw new RuntimeException("Properties can not be loaded");
        }

    }

    public static String getProperty(String key) {
        return loadProperties().getProperty(key);
    }

    @Override
    public void close() throws Exception {

    }
}
