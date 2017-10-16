package helpers;

import java.io.*;
import java.util.*;

public class Utils {

    private static final String localConf = "LocalConf";

    private static InputStream newFileInputStream(String file) {
        InputStream input = null;
        try {
            input = new FileInputStream("src" + File.separator + "main" + File.separator + "resources" +
                    File.separator + file + ".properties");
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

        return input;
    }

    public static String getData(String file, String element) {
        final Properties prop = new Properties();

        try {
            InputStream input = newFileInputStream(file);
            prop.load(input);
            return (prop.getProperty(element));
        }  catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static String getBrowser() {
        return getData(localConf, "BROWSER");
    }

    public static ResourceBundle getResourceBundle() {
        final String messagesBundle = "Messages";
        String language = Utils.getData(localConf, "LANGUAGE");
        String country = Utils.getData(localConf, "COUNTRY");
        String path = Utils.getData(localConf, "LANGUAGE_PROPERTIES_FILES_PATH");
        Locale currentLocale = new Locale(language, country);

        return ResourceBundle.getBundle(path + File.separator + messagesBundle, currentLocale);
    }
}
