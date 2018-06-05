package stellar.log;

public class DebugLogger {

    /**
     * Log information.
     *
     * @param i the string to log.
     */
    public static void i(String i) {
        System.out.println("Stellar: " + i);
    }

    /**
     * Log a warning.
     *
     * @param w the string to log.
     */
    public static void w(String w) {
        System.out.println("Stellar [WARNING]: " + w);
    }

    /**
     * Log an error.
     *
     * @param e the string to log.
     */
    public static void e(String e) {
        System.out.println("Stellar [ERROR]: " + e);
    }

}
