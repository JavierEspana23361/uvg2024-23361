import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * This class implements the Ihash interface and provides a method to compute the SHA-1 hash of a given string.
 */
public class hashSHA1 implements Ihash {

    /**
     * Computes the SHA-1 hash of the given data.
     *
     * @param data the string to compute the hash for
     * @return the SHA-1 hash of the input data as a hexadecimal string
     */
    public String typehash(String data) {
        try {
            MessageDigest md = MessageDigest.getInstance("SHA-1");
            byte[] hashBytes = md.digest(data.getBytes());
            StringBuilder sb = new StringBuilder();
            for (byte b : hashBytes) {
                sb.append(String.format("%02x", b));
            }
            return sb.toString();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
            return null;
        }
    }
}
