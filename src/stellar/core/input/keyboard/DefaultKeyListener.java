package stellar.core.input.keyboard;

import stellar.log.DebugLogger;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.HashMap;
import java.util.Map;

public class DefaultKeyListener implements KeyListener {

    private boolean keyData[] = new boolean[256];
    private final KeyDuration KEY_TIMES = new KeyDuration();

    /**
     * @param key the key to check
     * @return true if the key is down.
     */
    public boolean isKeyDown(int key) {
        if (key > keyData.length) {
            DebugLogger.w("Key " + key + " too big for the 256 array size.");
            return false;
        }
        return keyData[key];
    }

    /**
     * @param key the key
     * @return how long the key has been down.
     */
    public long keyDownDuration(int key) {
        long keyTime = KEY_TIMES.getKeyDownFor(key);
        if (keyTime == 0) {
            return 0;
        }
        return System.currentTimeMillis() - keyTime;
    }

    @Override
    public void keyPressed(KeyEvent event) {
        keyData[event.getKeyCode()] = true;
        KEY_TIMES.setKeyDown(event.getKeyCode());
    }

    @Override
    public void keyReleased(KeyEvent event) {
        keyData[event.getKeyCode()] = false;
        KEY_TIMES.removeKey(event.getKeyCode());
    }

    @Override
    public void keyTyped(KeyEvent event) {
        // TODO: Handle this event.
    }

    public class KeyDuration {
        private final Map<Integer, Long> KEY_TIME = new HashMap<>();

        /**
         * Add a key to the map of keys that are being held down.
         *
         * @param key the key.
         */
        public void setKeyDown(int key) {
            KEY_TIME.put(key, System.currentTimeMillis());
        }

        /**
         * @param key the key
         * @return the time the key was held down for.
         */
        public long getKeyDownFor(int key) {
            if (KEY_TIME.containsKey(key)) {
                return KEY_TIME.get(key);
            }
            return 0;
        }


        /**
         * Remove a key from the map.
         *
         * @param key the key
         */
        public void removeKey(int key) {
            if (KEY_TIME.containsKey(key)) {
                KEY_TIME.remove(key);
            }
        }

    }
}
