package stellar.core.thread;

import stellar.core.GameManager;
import stellar.core.GameWindow;
import stellar.log.DebugLogger;

import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class FixedGameLoop {

    private final GameManager manager;

    private boolean doTick, doDraw, canvasDraw, readyForUpdate;
    private UpdateMethod method;
    private ExecutorService service;

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
    public void start(int tickRate, UpdateMethod method) {

        // set the method and and ticking stuff.
        this.method = method;
        this.canvasDraw = manager.getType() == GameWindow.DrawType.PANEL;
        TICK_RATE = 1000000000 / tickRate;
        lastTick = System.nanoTime();
        now = System.currentTimeMillis();

        readyForUpdate = true;
        // setup the thread.
        setupThread();
    }

    private void setupThread() {
        UpdateMethod.UpdateType type = method.getType();
        if (type == UpdateMethod.UpdateType.MANUAL) {
            service = Executors.newSingleThreadExecutor();
            service.submit(() -> {
                while (manager.isGameRunning()) {
                    if (readyForUpdate) {
                        readyForUpdate = false;
                        doThreadActions();
                    }
                }
            });
        }

        if (type == UpdateMethod.UpdateType.INTERVAL) {
            // create a new ExecutorService and schedule it with the method delay.
            service = Executors.newScheduledThreadPool(1);
            ((ScheduledExecutorService) service).scheduleWithFixedDelay(this::doThreadActions, 0, method.getInterval(), TimeUnit.MILLISECONDS);
        }

        if (type == UpdateMethod.UpdateType.CONSTANT) {
            service = Executors.newSingleThreadExecutor();
            service.submit(() -> {
                while (manager.isGameRunning()) {
                    doThreadActions();
                }
            });
        }
    }

    /**
     * Tell the thread we are ready for a drawing/tick update.
     */
    public void readyForUpdate() {
        readyForUpdate = true;
    }

    /**
     * Shutdown the executor service.
     */
    private void stop() {
        List<Runnable> r = service.shutdownNow();
        for (Runnable runnable : r) {
            DebugLogger.i("Runnable: " + runnable.toString() + " is still running.");
        }
        DebugLogger.i(r.size() + " threads are still being processed.");
        r.clear();
    }

    private void doThreadActions() {
        // update tick.
        doTickJob();

        if (doTick) {
            manager.onTick();
        }

        if (doDraw) {
            if (canvasDraw) {
                manager.getWindow().updateCanvas();
            } else {
                manager.onDraw();
            }
        }
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
