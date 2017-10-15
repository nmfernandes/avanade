package pageObjects;

import helpers.Utils;

/**
 * Created by nnnn1 on 15/10/2017.
 */
public class Home extends PageObject{
    private String message;
    private static final String TITLE = "LOGIN_PORTALS_PAGE";


    public Home(String message){
        this.message = message;
    }

    public Home(String session, String browser) throws Exception {
        super(session, browser);

        String urlPortal = Utils.getData("Endpoints", "PORTALS");

        try {
            driver.get(urlPortal);
            driver.navigate().to("javascript:document.getElementById(invalidcert_continue).click()");
        } catch (Exception nothing) {
            System.out.println(nothing.toString());
        } finally {
        }

        verifyPageOpenIsExpected(TITLE);
    }

    // prints the message
    public String printMessage(){
        System.out.println(message);
        return message;
    }

}
