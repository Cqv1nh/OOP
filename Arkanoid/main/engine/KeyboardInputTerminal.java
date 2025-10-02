package engine;

import org.jline.terminal.Terminal;
import org.jline.terminal.TerminalBuilder;

import java.io.IOException;
import java.io.Reader;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

public class KeyboardInputTerminal implements Runnable {
    private final BlockingQueue<Character> keyPressQueue = new LinkedBlockingQueue<>();
    private volatile boolean running = false;
    private Thread workerThread;
    private Terminal terminal;

    public KeyboardInputTerminal() throws IOException {
        this.terminal = TerminalBuilder.builder()
                .system(true)
                .build();
    }

    /**
     * Starts the input listener thread.
     */
    public void start() {
        if (running) return;
        running = true;
        workerThread = new Thread(this);
        workerThread.start();
    }

    /**
     * Stops the input listener thread.
     */
    public void stop() {
        running = false;
        if (workerThread != null) {
            workerThread.interrupt();
        }
    }

    @Override
    public void run() {
        try {
            terminal.enterRawMode();
            try (Reader reader = terminal.reader()) {
                while (running && !Thread.currentThread().isInterrupted()) {
                    int character = reader.read();
                    if (character != -1) {
                        keyPressQueue.offer((char) character);
                    }
                }
            }
        } catch (IOException e) {
            if (running) {
                e.printStackTrace();
            }
        } finally {
            try {
                terminal.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * Returns the next key pressed, or null if none is available.
     */
    public Character pollNextKey() {
        return keyPressQueue.poll();
    }
}
