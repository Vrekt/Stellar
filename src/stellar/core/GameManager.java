package stellar.core;

import stellar.core.input.InputAdapter;
import stellar.core.sprite.SpriteManager;
import stellar.core.thread.FixedGameLoop;
import stellar.core.state.GameState;
import stellar.log.DebugLogger;

import java.awt.Graphics2D;
import java.awt.image.BufferStrategy;
import java.util.ArrayList;
import java.util.List;

public class GameManager {

    private GameWindow window;
    private GameWindow.DrawType type;

    private SpriteManager spriteManager;
    private InputAdapter inputAdapter;

    private int tickRate;
    private boolean isGameRunning;
    private final List<GameState> gameStack = new ArrayList<>();

    private BufferStrategy bufferStrategy;

    /**
     * Initialize the GameManager.
     *
     * @param gameTitle    the windows title.
     * @param windowWidth  the windows width.
     * @param windowHeight the windows height.
     * @param preferences  the settings which define how the window is setup.
     * @param type         the type of drawing to use.
     * @param tickRate     the "refresh rate" of the game loop.
     */
    public GameManager(String gameTitle, int windowWidth, int windowHeight, WindowPreferences preferences, GameWindow.DrawType type, int tickRate) {
        this.type = type;
        this.tickRate = tickRate;

        if (type == GameWindow.DrawType.PANEL) {
            // initialize the window with this manager.
            window = new GameWindow(gameTitle, windowWidth, windowHeight, preferences, this);
        } else {
            window = new GameWindow(gameTitle, windowWidth, windowHeight, preferences);
        }

        inputAdapter = new InputAdapter(window);
        spriteManager = new SpriteManager();
    }

    /**
     * Show the window and start the gameloop.
     */
    public void start() {
        isGameRunning = true;
        window.start(type);

        if (type == GameWindow.DrawType.WINDOW) {
            // initialize the bufferstrategy.
            bufferStrategy = window.getBufferStrategy();
        }
        new FixedGameLoop(this).start(tickRate);
    }

    /**
     * Stop the gameloop.
     */
    public void stop() {
        isGameRunning = false;
    }

    /**
     * Update the game.
     */
    public void onTick() {
        invokeTick();
    }

    /**
     * Draw the game.
     */
    public void onDraw() {
        // create the draw graphics from the buffer.
        final Graphics2D graphics = (Graphics2D) bufferStrategy.getDrawGraphics();
        graphics.clearRect(0, 0, window.getWidth(), window.getHeight());

        // iterate through all states in the stack and call their onDraw method.
        invokeDraw(graphics);

        // dispose and show.
        graphics.dispose();
        bufferStrategy.show();
    }

    /**
     * Draw with a direct graphics object.
     */
    public void drawWithGraphics(Graphics2D graphics) {
        graphics.clearRect(0, 0, window.getWidth(), window.getHeight());

        invokeDraw(graphics);
    }

    /**
     * Iterate through all states in the stack and call their onDraw method.
     *
     * @param graphics the draw graphics to use.
     */
    private void invokeDraw(final Graphics2D graphics) {
        gameStack.forEach(state -> state.onDraw(graphics));
    }

    /**
     * Iterate through all states in the stack and call their onTick method.
     */
    private void invokeTick() {
        gameStack.forEach(GameState::onTick);
    }

    /**
     * @return get the window the game is being drawn on.
     */
    public GameWindow getWindow() {
        return window;
    }

    /**
     * @return the drawing type to use.
     */
    public GameWindow.DrawType getType() {
        return type;
    }

    /**
     * @return if the game is running or not.
     */
    public boolean isGameRunning() {
        return isGameRunning;
    }

    /**
     * @return the input adapter for registering key/mouse listeners.
     */
    public InputAdapter getInputAdapter() {
        return inputAdapter;
    }

    /**
     * @return the sprite manager for handling images/tiles.
     */
    public SpriteManager getSpriteManager() {
        return spriteManager;
    }

    /**
     * Push a state to the stack.
     *
     * @param state the state to push.
     */
    public void push(GameState state) {
        if (gameStack.contains(state)) {
            DebugLogger.e("Could not add new GameState: " + state.getClass() + ", this already exists in the stack!");
            return;
        }
        gameStack.add(state);
    }

}
