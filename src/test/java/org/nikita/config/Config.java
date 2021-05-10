package org.nikita.config;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

public class Config {
    private static Properties properties;

    static {
        String configPath = Config.class.getClassLoader().getResource("config.properties").getFile();
        try (FileInputStream fileInputStream = new FileInputStream(configPath)) {
            properties = new Properties();
            properties.load(fileInputStream);

            /**
             * Iterate through properties to
             * find env variable values and
             * replace them
             */
            for (String key : properties.stringPropertyNames()) {
                String value = properties.getProperty(key);

                if (value.startsWith("$")) {
                    properties.setProperty(key, System.getenv(value.substring(1)));
                }
            }
        } catch (IOException err) {
            System.err.println(err);
        }
    }

    private Config() {}

    public static String getProperty(String key) {
        return properties.getProperty(key);
    }
}
