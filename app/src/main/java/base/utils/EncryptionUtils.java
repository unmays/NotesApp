package base.utils;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * Created by unmay.shroff91257 on 2/8/2019.
 */

public class EncryptionUtils {

    /**
     * Two step encryption using SHA 256 and BASE64 encoder
     *
     * @param input - input string to encrypt
     * @return - encrypted output for given input or null
     */
    public static String encrypt(String input) {
        if (input == null || input.equalsIgnoreCase(""))
            return "";
        else {
            byte[] key, iv;

            byte[] currentHash = new byte[0];
            MessageDigest md = null;
            try {
                md = MessageDigest.getInstance("SHA-256");
                if (md != null)
                    currentHash = md.digest();
            } catch (NoSuchAlgorithmException e) {
                e.printStackTrace();
            }
            return new String(currentHash);
        }
    }

}
