public class hashFactory {
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
