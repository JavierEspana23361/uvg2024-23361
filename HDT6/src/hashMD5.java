import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * This class implements the Ihash interface and provides a method to compute the MD5 hash of a given string.
 */
public class hashMD5 implements Ihash {
    /**
     * Computes the MD5 hash of the given data.
     *
     * @param data The string to compute the hash for.
     * @return The MD5 hash of the input data as a hexadecimal string.
     */
    public String typehash(String data) {
        try {
            MessageDigest md = MessageDigest.getInstance("MD5");
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
