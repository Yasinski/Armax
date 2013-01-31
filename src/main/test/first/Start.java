package first;

/**
 * writeme: Should be the description of the class
 *
 * @author <a href="a.kasinski@itision.com">Arthur Kasinskiy</a>
 */
public class Start {

    public static void main(String[] args) {

        String adr = "http://google.com";

        PageSaverImpl0 ps1 = new PageSaverImpl0();
        PageSaverImpl1 ps2 = new PageSaverImpl1();
        PageSaverImpl2 ps3 = new PageSaverImpl2();
        PageSaverImpl3 ps4 = new PageSaverImpl3();

        ps1.savePage(adr);
        ps2.savePage(adr);
        ps3.savePage(adr);
//        ps4.savePage(adr);

    }
}