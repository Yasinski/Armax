package temp;

import org.springframework.security.crypto.keygen.KeyGenerators;

/**
 * writeme: Should be the description of the class
 *
 * @author <a href="">Max Yasinski</a>
 * @updated 25.01.13 10:41
 */
public class TempTest {

    public static void main(String[] args) {
        System.out.println(KeyGenerators.string().generateKey());
    }
}
