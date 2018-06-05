package stellar.core;

import stellar.core.drawing.PanelCanvas;
import stellar.log.DebugLogger;

import javax.swing.JFrame;
import java.awt.Graphics;

public class GameWindow extends JFrame {

    private int width, height;
    private Graphics drawGraphics;
    private PanelCanvas canvas;

    private GameManager manager;

    /**
     * Initialize the window.
     *
     * @param title       the windows title.
     * @param width       the windows width.
     * @param height      the windows height.
     * @param preferences the settings which define how the window is setup.
     */
    public GameWindow(String title, int width, int height, WindowPreferences preferences) {
        this.width = width;
        this.height = height;

        setTitle(title);
        setSize(width, height);

        //noinspection MagicConstant
        setDefaultCloseOperation(preferences.getCloseOperation());
        setResizable(preferences.isResizable());
        setFocusable(preferences.isFocusable());

        setLocationRelativeTo(preferences.getRelativeLocation());
    }

    /**
     * Initialize the window.
     *
     * @param title       the windows title.
     * @param width       the windows width.
     * @param height      the windows height.
     * @param preferences the settings which define how the window is setup.
     */
    public GameWindow(String title, int width, int height, WindowPreferences preferences, GameManager manager) {
        this.width = width;
        this.height = height;
        this.manager = manager;

        setTitle(title);
        setSize(width, height);

        //noinspection MagicConstant
        setDefaultCloseOperation(preferences.getCloseOperation());
        setResizable(preferences.isResizable());
        setFocusable(preferences.isFocusable());

        setLocationRelativeTo(preferences.getRelativeLocation());
    }

    /**
     * Start the game and show the window, create the graphics too.
     *
     * @param type the drawing type to use.
     */
    public void start(DrawType type) {
        setVisible(true);
        createGraphicsType(type);
    }

    /**
     * If the DrawType is specified as DrawType#PANEL then create a new panel and add it.
     *
     * @param type the DrawType to use.
     */
    private void createGraphicsType(DrawType type) {
        // if we have the drawing type set to panel create a new one and add it.
        if (type == DrawType.PANEL) {
            DebugLogger.i("Using JPanel as alternate drawing method.");
            canvas = new PanelCanvas(manager, width, height);
            add(canvas);
        } else {
            DebugLogger.i("Using BufferStrategy(3) to create draw graphics.");
            createBufferStrategy(3);
            drawGraphics = getBufferStrategy().getDrawGraphics();
        }
    }

    /**
     * @return the graphics used to draw with.
     */
    public Graphics getDrawGraphics() {
        return drawGraphics;
    }

    /**
     * Repaint the canvas.
     */
    public void updateCanvas() {
        canvas.repaint();
    }

    /**
     * This enum defines which drawing method to use.
     */
    public enum DrawType {
        WINDOW, PANEL
    }
}
