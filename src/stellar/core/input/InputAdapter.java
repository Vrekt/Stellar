package stellar.core.input;

import stellar.core.GameWindow;
import stellar.core.input.keyboard.DefaultKeyListener;
import stellar.core.input.mouse.DefaultMouseListener;

import java.awt.event.KeyListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseListener;

public class InputAdapter {

    private final GameWindow window;

    private final DefaultKeyListener keyListener = new DefaultKeyListener();
    private final DefaultMouseListener mouseListener = new DefaultMouseListener();

    public InputAdapter(GameWindow window) {
        this.window = window;

        // register default listeners
        window.addKeyListener(keyListener);
        window.addMouseListener(mouseListener);
    }

    /**
     * Add a key listener
     */
    public void addKeyListener(KeyListener listener) {
        window.addKeyListener(listener);
    }

    /**
     * Clear all registered key listeners, including the default one.
     */
    public void clearKeyListeners() {
        KeyListener[] listeners = window.getKeyListeners();
        for (KeyListener listener : listeners) {
            removeKeyListener(listener);
        }
    }

    /**
     * Remove the given key listener.
     */
    public void removeKeyListener(KeyListener listener) {
        window.removeKeyListener(listener);
    }

    /**
     * Add a mouse listener.
     */
    public void addMouseListener(MouseListener listener) {
        window.addMouseListener(listener);
    }

    /**
     * Clear all registered mouse listeners, including the default one.
     */
    public void clearMouseListeners() {
        MouseListener[] listeners = window.getMouseListeners();
        for (MouseListener listener : listeners) {
            removeMouseListener(listener);
        }
    }

    /**
     * Remove the given mouse listener.
     */
    public void removeMouseListener(MouseListener listener) {
        window.removeMouseListener(listener);
    }

    /**
     * Register a mouse adapter.
     */
    public void addMouseAdapter(MouseAdapter adapter) {
        window.addMouseMotionListener(adapter);
    }

    /**
     * Remove a registered mouse adapter.
     */
    public void removeMouseAdapter(MouseAdapter adapter) {
        window.removeMouseMotionListener(adapter);
    }

    /**
     * @return the keyboard listener for checking keyboard keys and states.
     */
    public DefaultKeyListener getKeyboard() {
        return keyListener;
    }

    /**
     * @return the mouse listener.
     */
    public DefaultMouseListener getMouse() {
        return mouseListener;
    }
}
