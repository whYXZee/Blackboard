package whyxzee.blackboard.utils;

/**
 * A general-use package for logging into the console.
 */
public class Loggy {
    /* Variables */
    private boolean isTelemetryOn;

    public Loggy(boolean isTelemetryOn) {
        this.isTelemetryOn = isTelemetryOn;
    }

    // #region Logging Methods
    public final void log(String msg) {
        if (isTelemetryOn) {
            System.out.println(msg);
        }
    }

    public final void logHeader(String msg) {
        log("---- " + msg + " ----");
    }

    public final void logDetail(String msg) {
        log("- " + msg);
    }

    public final <T> void logVal(String valueName, T value) {
        // <T> declared the general type
        log(valueName + ": " + value.toString());
    }

    public final <T> void logArray(T[] arr) {
        log("-- Contents of Array --");
        for (T i : arr) {
            logDetail(i.toString());
        }
    }

    // #endregion
}
