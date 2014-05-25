package engine.world;

/**
 *
 * @author DominaN
 */
public class AFTTime {

    public final static int DEFAULT_TIME_SPEED = 20;
    public static int timeSpeed = DEFAULT_TIME_SPEED;
    public static long lastTime = System.currentTimeMillis();
    public static long time;
    //Game time
    public static int seconds = 0, minutes = 0, hours = 16, days = 0, months = 0, years = 0;
    public static String timeOfDay;

    public static void checkTime() {
        long delta = (System.currentTimeMillis() - lastTime) / timeSpeed;

        if (delta >= 1) {
            lastTime = time;

            time += delta;
            seconds += delta;
        }

        if (seconds > 59) {
            minutes++;

            seconds = 0;
        }

        if (minutes > 59) {
            hours++;

            minutes = 0;
        }

        if (hours > 23) {
            days++;

            hours = 0;
        }

        if (days > 30) {
            months++;

            days = 0;
        }

        if (months > 12) {
            years++;

            months = 0;
        }

        timeOfDay = ((hours < 10) ? "0".concat(String.valueOf(hours)) : String.valueOf(hours)).concat((minutes < 10) ? ":0".concat(String.valueOf(minutes)) : ":".concat(String.valueOf(minutes)));
    }
}
