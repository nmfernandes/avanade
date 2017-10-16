package helpers;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.io.*;
import java.util.*;

public class Utils {

    private static final String localConf = "LocalConf";
    public static final String USERS_FILE = "/src/main/resources/User.json";

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
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static Iterator<JSONObject> getJsonIterator() throws IOException, ParseException {
        FileReader fileReader = new FileReader(new File(".").getCanonicalPath() + USERS_FILE);
        JSONParser parser = new JSONParser();
        Object obj = parser.parse(fileReader);
        JSONArray jsonArray = (JSONArray) obj;
        return jsonArray.iterator();
    }

    public static JSONObject getJson(String file, String element) throws ParseException {
        try {
            FileReader fileReader = new FileReader(new File(".").getCanonicalPath() + file);
            JSONParser parser = new JSONParser();
            Object obj = parser.parse(fileReader);
            JSONArray jsonArray = (JSONArray) obj;
            Iterator<JSONObject> iterator = jsonArray.iterator();
            while (iterator.hasNext()) {
                JSONObject userToCompare = iterator.next();
                if (userToCompare.get("user").equals(element)) {
                    return userToCompare;
                }
            }
            return null;
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static List<String> getAllUserKeys(String userType, String enterprise) throws ParseException {

        List<String> users = new ArrayList<>();

        try {
            Iterator<JSONObject> iterator = getJsonIterator();
            while (iterator.hasNext()) {
                JSONObject userToCompare = iterator.next();
                if (userToCompare.get("enterprise") != null)
                    if (userToCompare.get("userType").equals(userType) && userToCompare.get("enterprise").equals(enterprise)) {
                        users.add((String) userToCompare.get("user"));
                    }
            }
            return users;
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static List<String> getUsersToDelete(String license, String enterpriseKey) {
        final Properties prop = new Properties();
        List<String> keys = new ArrayList<>();

        try {
            InputStream input = newFileInputStream("Properties");
            prop.load(input);
            int nMaxLicenses = Integer.parseInt(prop.getProperty("N_AVAILABLE_LICENSES"));

            Enumeration em = prop.keys();

            while (em.hasMoreElements()) {
                String str = (String) em.nextElement();

                if (prop.get(str).toString().contains(license) && prop.get(str).toString().contains(enterpriseKey)
                        && Integer.parseInt(str.substring(str.length() - 1)) > nMaxLicenses)
                    keys.add(str);

            }

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return keys;
    }

    public static String getNameJson(String userKey) throws ParseException {
        JSONObject jsonObject = getJsonFile(userKey);
        return (String) jsonObject.get("name");
    }

    public static String getLastNameJson(String userKey) throws ParseException {
        JSONObject jsonObject = getJsonFile(userKey);
        return (String) jsonObject.get("lastName");
    }

    public static String getTnJson(String userKey) throws ParseException {
        JSONObject jsonObject = getJsonFile(userKey);
        return (String) jsonObject.get("tn");
    }

    public static String getTnWithCCJson(String userKey) throws ParseException {
        JSONObject jsonObject = getJsonFile(userKey);
        String tn = (String) jsonObject.get("tn");
        return "+" + getCountryCode(getCountry()) + "-" + tn;
    }

    public static String getTnWithCCWithoutPlusJson(String userKey) throws ParseException {
        JSONObject jsonObject = getJsonFile(userKey);
        String tn = (String) jsonObject.get("tn");
        return getCountryCode(getCountry()) + tn;
    }

    public static String getTnWithCCWithoutHyphenJson(String userKey) throws ParseException {
        JSONObject jsonObject = getJsonFile(userKey);
        String tn = (String) jsonObject.get("tn");
        return "+" + getCountryCode(getCountry()) + tn;
    }

    public static String getTypeJson(String userKey) throws ParseException {
        JSONObject jsonObject = getJsonFile(userKey);
        return (String) jsonObject.get("type");
    }

    public static String getUserTypeJson(String userKey) throws ParseException {
        JSONObject jsonObject = getJsonFile(userKey);
        return (String) jsonObject.get("userType");
    }

    public static String getEmailJson(String userKey) throws ParseException {
        JSONObject jsonObject = getJsonFile(userKey);
        return (String) jsonObject.get("email");
    }

    public static String getTemplateJson(String userKey) throws ParseException {
        JSONObject jsonObject = getJsonFile(userKey);
        return (String) jsonObject.get("template");
    }

    public static String getDeviceTypeJson(String userKey) throws ParseException {
        JSONObject jsonObject = getJsonFile(userKey);
        return (String) jsonObject.get("deviceType");
    }

    public static String getDeviceModelJson(String userKey) throws ParseException {
        JSONObject jsonObject = getJsonFile(userKey);
        return (String) jsonObject.get("deviceModel");
    }

    public static String getMaxBridgeParticipantJson(String userKey) throws ParseException {
        JSONObject jsonObject = getJsonFile(userKey);
        return (String) jsonObject.get("maxBridgeParticipant");
    }

    public static String getMaxRoomParticipantsValueJson(String userKey) throws ParseException {
        JSONObject jsonObject = getJsonFile(userKey);
        return (String) jsonObject.get("maxRoomParticipantsValue");
    }

    public static String getSMSTnJson(String userKey) throws ParseException {
        JSONObject jsonObject = getJsonFile(userKey);
        return (String) jsonObject.get("smsTn");
    }

    private static JSONObject getJsonFile(String userKey) throws ParseException {
        return getJson(USERS_FILE, userKey);
    }

    public static String getAnnouncement(String announcement) {
        return getData("LocalConf", announcement);
    }

    public static String getAnnouncementFileName(String announcement) {

        return getData("LocalConf", announcement).split("[\\\\,.]")[1];

    }

    public static String getBrowser() {
        return getData(localConf, "BROWSER");
    }

    public static String getAnnouncementsName() {
        return getData(localConf, "ANNOUNCEMENTS_NAME");
    }

    public static String getCountry() {
        return getData("Country", "COUNTRY");
    }

    public static String getCountryCode(String country) {
        return getData("CountryCodes", country);
    }

    public static String getnFACs() {
        return getData("Properties", "N_FACS");
    }

    //public static String getLanguage() { return getData(localConf, ); }

    public static ResourceBundle getResourceBundle() {
        final String messagesBundle = "Messages";
        String language = Utils.getData(localConf, "LANGUAGE");
        String country = Utils.getData(localConf, "COUNTRY");
        String path = Utils.getData(localConf, "LANGUAGE_PROPERTIES_FILES_PATH");
        Locale currentLocale = new Locale(language, country);

        return ResourceBundle.getBundle(path + File.separator + messagesBundle, currentLocale);
    }
}
