/**
 * This class implements the Ihash interface and provides a hash function using the ORGANIC algorithm.
 */
public class hashORGANIC implements Ihash {
    /**
     * Calculates the hash value for the given data using the ORGANIC algorithm.
     *
     * @param data the input data to be hashed
     * @return the hash value as a string
     */
    public String typehash(String data) {
        int hash = 0;
        for (int i = 0; i < data.length(); i++) {
            hash = (hash * 31) + data.charAt(i);
        }
        return Integer.toString(hash);
    }
}
