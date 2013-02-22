package temp;

import org.springframework.security.crypto.encrypt.Encryptors;
import org.springframework.security.crypto.encrypt.TextEncryptor;
import org.springframework.security.crypto.keygen.KeyGenerators;

/**
 * writeme: Should be the description of the class
 *
 * @author <a href="">Max Yasinski</a>
 * @updated 25.01.13 10:41
 */
public class TempTest {

    private static String password = "ttt";
    private static String salt = KeyGenerators.string().generateKey();

    public static void main(String[] args) {
        TextEncryptor textEncryptor = Encryptors.text(password, salt);
        String encryptedText = textEncryptor.encrypt("test");
        System.out.println(encryptedText);
    }
}
