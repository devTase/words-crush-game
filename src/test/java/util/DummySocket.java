package util;

import java.io.IOException;
import java.net.Socket;

/**
 * Test utility Socket extension with controllable close behavior.
 * Allows simulating socket closure conditions in unit tests.
 */
public class DummySocket extends Socket {

    private boolean shouldThrowOnClose;
    private boolean isClosed;
    private IOException closeException;

    public DummySocket() {
        this.shouldThrowOnClose = false;
        this.isClosed = false;
    }

    public DummySocket(boolean shouldThrowOnClose) {
        this.shouldThrowOnClose = shouldThrowOnClose;
        this.isClosed = false;
        if (shouldThrowOnClose) {
            this.closeException = new IOException("Simulated socket close error");
        }
    }

    public DummySocket(IOException closeException) {
        this.shouldThrowOnClose = true;
        this.isClosed = false;
        this.closeException = closeException;
    }

    @Override
    public void close() throws IOException {
        if (shouldThrowOnClose && closeException != null) {
            throw closeException;
        }
        this.isClosed = true;
    }

    @Override
    public boolean isClosed() {
        return isClosed;
    }

    // Test control methods
    public void setShouldThrowOnClose(boolean shouldThrow) {
        this.shouldThrowOnClose = shouldThrow;
    }

    public void setCloseException(IOException exception) {
        this.closeException = exception;
        this.shouldThrowOnClose = exception != null;
    }

    public boolean getShouldThrowOnClose() {
        return shouldThrowOnClose;
    }
}
