package helpers;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;
import org.openqa.selenium.ie.InternetExplorerDriver;
import org.openqa.selenium.ie.InternetExplorerOptions;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.safari.SafariDriver;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Drivers {
    private static List<WebDriver> drivers;
    private static WebDriver driver;
    private static List<String> sessions;
    private static String currentSession;

    public static WebDriver loadDriver() {
        return driver;
    }

    public static String getSession() {
        return currentSession;
    }

    public static WebDriver loadDriver(String session) {
        int index = sessions.indexOf(session);
        currentSession = session;
        return drivers.get(index);
    }

    public static WebDriver createOrLoadDriver(String session, String browser) throws Exception {
        if (session == null) {
            driver = loadDriver();
        } else if (drivers == null || drivers.size() == 0 || sessions.indexOf(session) == -1) {

            String browserParam = System.getProperty("browser");
            if (browserParam != null) {
                browser = browserParam;
            }

            Drivers.setProfile(browser);

            if (drivers == null)
                drivers = new ArrayList<>();
            drivers.add(driver);

            if (sessions == null)
                sessions = new ArrayList<>();
            sessions.add(session);
        } else {
            driver = Drivers.loadDriver(session);
            currentSession = session;
        }
        try {
            driver.manage().window().maximize();
        } catch (Exception ex) {
        }
        return driver;
    }

    public static void setProfile(String browser) throws Exception {
        if (browser == null)
            browser = Utils.getBrowser();

        String workingDir = System.getProperty("user.dir");
        if ("firefox".equals(browser.toLowerCase())) {
            setFirefoxProfile(workingDir);
        } else if ("edge".equals(browser.toLowerCase())) {
            setEdgeProfile(workingDir);
        } else if ("safari".equals(browser.toLowerCase())) {
            setSafariProfile(workingDir);
        } else if ("ie".equals(browser.toLowerCase())) {
            setIEProfile(workingDir);
        } else {
            setChromeProfile(workingDir);
        }
    }

    /**
     * Set profile to Firefox
     *
     * @param workingDir
     * @return firefoxProfile
     */
    private static void setFirefoxProfile(String workingDir) {
        Utils utils = new Utils();
        String os = utils.getData("LocalConf", "OS");
        System.setProperty("webdriver.gecko.driver", utils.getData("LocalConf", "PATH_FIREFOX_DRIVER_"+os));
        DesiredCapabilities capability = DesiredCapabilities.firefox();
        capability.acceptInsecureCerts();
        FirefoxOptions firefoxOptions = new FirefoxOptions(capability);
        driver = new FirefoxDriver(firefoxOptions);
    }

    /**
     * Set profile to Chorme
     *
     * @param workingDir
     */
    private static void setChromeProfile(String workingDir) {
        Map<String, Object> prefs = new HashMap<String, Object>();
        prefs.put("download.default_directory", workingDir + File.separator + "tmp");
        DesiredCapabilities caps = DesiredCapabilities.chrome();

        Utils utils = new Utils();

        ChromeOptions options = new ChromeOptions();
        options.addArguments("window-size=1936,1056");
        caps.setCapability(ChromeOptions.CAPABILITY, options);

        String os = Utils.getData("LocalConf", "OS");
        System.setProperty("webdriver.chrome.driver", Utils.getData("LocalConf", "PATH_CHROME_DRIVER_"+os));
        try {
            driver = new ChromeDriver(options);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    private static void setIEProfile(String workingDir) {
        Utils utils = new Utils();
        String os = Utils.getData("LocalConf", "OS");
        System.setProperty("webdriver.ie.driver", Utils.getData("LocalConf", "PATH_IE_DRIVER_"+os));
        DesiredCapabilities capability = DesiredCapabilities.internetExplorer();
        capability.setCapability("EnableNativeEvents", false);
        capability.setCapability("ignoreZoomSetting", true);
        capability.setCapability("trustAllSSLCertificates ", true);
        capability.setCapability("acceptSslCerts ", true);
        capability.setCapability("ignoreProtectedModeSettings ", true);

        capability.acceptInsecureCerts();
        InternetExplorerOptions options = new InternetExplorerOptions(capability);
        try {
            driver = new InternetExplorerDriver(options);
        } catch (Exception ex) {
        }
    }

    private static void setSafariProfile(String workingDir) throws InterruptedException {
        driver = new SafariDriver();
        Thread.sleep(4000);
    }

    private static void setEdgeProfile(String workingDir) {
        Utils utils = new Utils();
        String os = Utils.getData("LocalConf", "OS");
        System.setProperty("webdriver.edge.driver", Utils.getData("LocalConf", "PATH_EDGE_DRIVER_"+os));
        DesiredCapabilities capability = DesiredCapabilities.edge();
        capability.setCapability("EnableNativeEvents", false);
        capability.setCapability("ignoreZoomSetting", true);
        capability.setCapability("trustAllSSLCertificates ", true);
        capability.setCapability("acceptSslCerts ", true);
        capability.setCapability("ignoreProtectedModeSettings ", true);
        driver = new EdgeDriver();
    }


    public static void destroyDriver(String session) {
        try {

            int index = sessions.indexOf(session);
            drivers.get(index).quit();
            drivers.remove(index);
            sessions.remove(index);

        } catch (Exception ex) {
        }
    }

    public static void destroyLastDriver() {
        try {
            sessions.remove(sessions.size() - 1);
            drivers.get(drivers.size() - 1).quit();
            drivers.remove(drivers.size() - 1);
        } catch (Exception ex) {
        }
    }

}
