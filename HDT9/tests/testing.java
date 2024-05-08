
/**
 * This class contains unit tests for the functionality of the Main class.
 */
public class testing {

    /**
     * This test case verifies the correctness of the compress and decompress functionality.
     */
    @Test
    public void testCompressAndDecompress() {
        // Test code here...
    }

    /**
     * This test case verifies the behavior when an invalid option is provided.
     */
    @Test
    public void testInvalidOption() {
        // Test code here...
    }
}
public class testing {

    @Test
    public void testCompressAndDecompress() {
        // Arrange
        String archivoEntrada = "resources/testInput.txt";
        String archivoComprimido = "resources/testCompressed.txt";
        String archivoDescomprimido = "resources/testDecompressed.txt";

        // Act
        Main.main(new String[]{});

        // Assert
        File compressedFile = new File(archivoComprimido);
        File decompressedFile = new File(archivoDescomprimido);

        assertTrue(compressedFile.exists());
        assertTrue(decompressedFile.exists());

        try (FileInputStream compressedInputStream = new FileInputStream(compressedFile);
             FileInputStream decompressedInputStream = new FileInputStream(decompressedFile);
             ByteArrayOutputStream compressedOutputStream = new ByteArrayOutputStream();
             ByteArrayOutputStream decompressedOutputStream = new ByteArrayOutputStream()) {

            int byteRead;
            while ((byteRead = compressedInputStream.read()) != -1) {
                compressedOutputStream.write(byteRead);
            }

            while ((byteRead = decompressedInputStream.read()) != -1) {
                decompressedOutputStream.write(byteRead);
            }

            String compressedText = compressedOutputStream.toString();
            String decompressedText = decompressedOutputStream.toString();

            assertEquals("01010101010101010101010101010101", compressedText);
            assertEquals("test", decompressedText);
        } catch (IOException e) {
            e.printStackTrace();
            fail("IOException occurred");
        }
    }

    @Test
    public void testInvalidOption() {
        // Arrange
        String archivoEntrada = "resources/testInput.txt";
        String archivoComprimido = "resources/testCompressed.txt";
        String archivoDescomprimido = "resources/testDecompressed.txt";

        // Act
        Main.main(new String[]{});

        // Assert
        File compressedFile = new File(archivoComprimido);
        File decompressedFile = new File(archivoDescomprimido);

        assertFalse(compressedFile.exists());
        assertFalse(decompressedFile.exists());
    }
}