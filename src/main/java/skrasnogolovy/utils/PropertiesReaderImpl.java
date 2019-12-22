package skrasnogolovy.utils;

import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

@Service
public class PropertiesReaderImpl implements PropertiesReader {
    private Properties properties = new Properties();

    private PropertiesReaderImpl() {
        try {
            properties.load(new FileInputStream(new File(getClass().getResource("/").getPath() + "application.properties")));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public String getProperties(String propertiesKey) {
        return properties.getProperty(propertiesKey);

    }

    public Properties getAllProperties() {
        return properties;
    }

}
