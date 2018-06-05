package stellar.core.utilities;

public class BasicTimer {

    private long now;

    public void start() {
        now = System.currentTimeMillis();
    }

    public long stop() {
        return System.currentTimeMillis() - now;
    }

}
