public class hashORGANIC implements Ihash{
    public String typehash(String data) {
        int hash = 0;
        for (int i = 0; i < data.length(); i++) {
            hash = (hash * 31) + data.charAt(i);
        }
        return Integer.toString(hash);
    }
}
