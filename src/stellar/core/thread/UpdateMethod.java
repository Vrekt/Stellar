package stellar.core.thread;

import stellar.log.DebugLogger;

public class UpdateMethod {

    public enum UpdateType {
        CONSTANT, MANUAL, INTERVAL
    }

    private UpdateType type;
    private long interval;

    public UpdateMethod(UpdateType type) {
        this.type = type;
        if (type == UpdateType.INTERVAL) {
            DebugLogger.e("Interval update type without any interval specified. Defaulting to 500ms.");
            interval = 500;
        }
    }

    public UpdateMethod(UpdateType type, long interval) {
        this.type = type;
        if (interval == 0) {
            DebugLogger.e("Interval for gameloop update cannot be 0. Defaulting to 500ms.");
            interval = 500;
        }
        this.interval = interval;
    }

    /**
     * @return THE UPDATE TYPE.
     */
    public UpdateType getType() {
        return type;
    }

    /**
     * @return the interval of when to update.
     */
    public long getInterval() {
        return interval;
    }
}
