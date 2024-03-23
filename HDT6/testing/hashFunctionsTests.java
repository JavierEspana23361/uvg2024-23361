import static org.junit.Assert.assertEquals;
import org.junit.Test;

public class hashFunctionsTests {
    @Test
    public void testMd5() {
        hashMD5 hasher = new hashMD5();

        // Test empty input
        String emptyInput = "";
        String emptyHash = hasher.md5(emptyInput);
        assertEquals("d41d8cd98f00b204e9800998ecf8427e", emptyHash);

        // Test non-empty input
        String input = "Hello, World!";
        String expectedHash = "65a8e27d8879283831b664bd8b7f0ad4";
        String actualHash = hasher.md5(input);
        assertEquals(expectedHash, actualHash);
    }

    @Test
    public void testHashOrganica() {
        hashORGANIC hash = new hashORGANIC();
        
        String input = "Hello, World!";
        String expectedOutput = "Hello, World!";
        String actualOutput = hash.hashOrganica(input);
        
        assertEquals(expectedOutput, actualOutput);
    }

    @Test
    public void testSHA1() {
        hashSHA1 hash = new hashSHA1();
        
        String input = "Hello, World!";
        String expectedOutput = "2ef7bde608ce5404e97d5f042f95f89f1c232871";
        String actualOutput = hash.sha1(input);
        
        assertEquals(expectedOutput, actualOutput);
    }
}
