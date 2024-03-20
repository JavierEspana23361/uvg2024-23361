public class hashFunctionsFactory implements IhashFunctions {
    private hashMD5 md5 = new hashMD5();
    private hashSHA1 sha1 = new hashSHA1();
    private hashORGANIC organic = new hashORGANIC();

    public String hashOrganica(String data) {
        return organic.hashOrganica(data);
    }

    public String md5(String data) {
        return md5.md5(data);
    }

    public String sha1(String data) {
        return sha1.sha1(data);
    }
}
