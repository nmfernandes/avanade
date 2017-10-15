import org.junit.Test;
import pageObjects.Home;

import static org.junit.Assert.assertEquals;

/**
 * Created by nnnn1 on 15/10/2017.
 */
public class HomeTest {

    String message = "Hello World";
    Home messageUtil;


    public HomeTest() throws Exception {
        Home messageUtil = new Home("session1", null);
    }

    @Test
    public void testPrintMessage() {
        assertEquals(message,messageUtil.printMessage());
    }
}
