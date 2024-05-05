import java.io.*;

public class BitInputStream {
    private InputStream input;
    private int currentByte;
    private int numBitsRemaining;

    public BitInputStream(InputStream in) {
        if (in == null) {
            throw new NullPointerException("El flujo de entrada no puede ser nulo.");
        }
        input = in;
        currentByte = 0;
        numBitsRemaining = 0;
    }

    public int readBit() throws IOException {
        if (currentByte == -1) {
            return -1;
        }
        if (numBitsRemaining == 0) {
            currentByte = input.read();
            if (currentByte == -1) {
                return -1;
            }
            numBitsRemaining = 8;
        }
        numBitsRemaining--;
        return (currentByte >>> numBitsRemaining) & 1;
    }

    public void close() throws IOException {
        input.close();
    }
}
