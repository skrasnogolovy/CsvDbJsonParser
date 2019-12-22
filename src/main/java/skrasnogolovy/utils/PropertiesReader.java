package skrasnogolovy.utils;

import java.util.Properties;

public interface PropertiesReader {
    public String getProperties(String propertiesKey);
    public Properties getAllProperties();
}
