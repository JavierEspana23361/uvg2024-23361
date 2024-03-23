/**
 * The `hashFactory` class is responsible for creating instances of different hash algorithms based on the given type.
 */
public class hashFactory {
    /**
     * Returns an instance of a hash algorithm based on the given type.
     *
     * @param type The type of hash algorithm to create.
     * @return An instance of the specified hash algorithm.
     * @throws IllegalArgumentException If an invalid type is provided.
     */
    Ihash gethash(int type) {
        switch (type) {
            case 1: 
                return new hashMD5();
            case 2:
                return new hashSHA1();
            case 3: 
                return new hashORGANIC();
            default:
                throw new IllegalArgumentException("Invalid type");
        }
    }
}
