public class hashFunctionsFactory implements IhashFunctions {


    public String hash(String data, String type) {
        switch (type) {
            case "md5":
                return md5(data);
            case "sha1":
                return sha1(data);
            case "organic":
                return hashOrganica(data);
            default:
                return "Invalid hash type";
        }
    }
}
