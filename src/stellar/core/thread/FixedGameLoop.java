package stellar.core.thread;

import stellar.core.GameManager;
import stellar.core.GameWindow;
import stellar.log.DebugLogger;

public class FixedGameLoop implements Runnable {

    private final GameManager manager;
    private final Thread thread = new Thread(this);

    private boolean doTick, doDraw;

    private long lastTick, waitTime, now;
    private double TICK_RATE, tickDelta = 0;

    // TODO: track FPS
    private int frames = 0;

    public FixedGameLoop(GameManager manager) {
        this.manager = manager;
    }

    /**
     * Start the loop.
     */
    public void start(int tickRate) {
        // setup the ticking variables.

        TICK_RATE = 1000000000 / tickRate;
        lastTick = System.nanoTime();
        now = System.currentTimeMillis();

        thread.start();
    }

    /**
     * Stop the loop and join the thread.
     */
    private void stop() {
        try {
            thread.join();
        } catch (InterruptedException exception) {
            DebugLogger.i("Failed to join gameloop thread!");
            exception.printStackTrace();
        }
    }

    @Override
    public void run() {
        if (manager.getType() == GameWindow.DrawType.WINDOW) {
            while (manager.isGameRunning()) {
                // update tick.
                doTickJob();

                if (doTick) {
                    manager.onTick();
                }

                if (doDraw) {
                    manager.onDraw();
                }

            }
        } else {
            // were using a panel to draw, we keep drawing on the swing thread to not cause problems.
            while (manager.isGameRunning()) {
                // update tick.
                doTickJob();

                if (doTick) {
                    manager.onTick();
                }

                if (doDraw) {
                    manager.getWindow().updateCanvas();
                }

            }
        }
        stop();
    }

    /**
     * Calculate when to update again.
     */
    private void doTickJob() {

        doDraw = false;
        doTick = false;
        boolean readyForUpdate;

        if (waitTime == 0) {
            // the loop hasn't ran yet, so continue.
            readyForUpdate = true;
        } else {
            // make sure we have waited.
            long currentTime = System.currentTimeMillis();
            // ready to update!
            readyForUpdate = currentTime - now >= waitTime;
        }

        if (!readyForUpdate) {
            return;
        }

        long nowNano = System.nanoTime();
        long totalTickTime = nowNano - lastTick;
        long tickStart = System.currentTimeMillis();
        lastTick = nowNano;

        tickDelta += totalTickTime / TICK_RATE;
        while (tickDelta >= 1) {
            doTick = true;
            tickDelta--;
        }

        // TODO: Implement max fps.
        doDraw = true;
        frames++;

        now = System.currentTimeMillis();
        waitTime = ((long) TICK_RATE - (System.currentTimeMillis() - tickStart)) / (long) 1e6;
    }

}
