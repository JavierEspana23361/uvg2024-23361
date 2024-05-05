import java.io.IOException;
import java.io.OutputStream;

public class BitOutputStream {
    private OutputStream outputStream;
    private int buffer;
    private int bufferLength;

    public BitOutputStream(OutputStream outputStream) {
        this.outputStream = outputStream;
        this.buffer = 0;
        this.bufferLength = 0;
    }

    public void writeBit(boolean bit) throws IOException {
        buffer <<= 1;
        if (bit) {
            buffer |= 1;
        }
        bufferLength++;

        if (bufferLength == 8) {
            flush();
        }
    }

    public void flush() throws IOException {
        if (bufferLength > 0) {
            if (bufferLength < 8) {
                buffer <<= (8 - bufferLength);
            }
            outputStream.write(buffer);
            buffer = 0;
            bufferLength = 0;
        }
    }

    public void close() throws IOException {
        flush();
        outputStream.close();
    }
}
