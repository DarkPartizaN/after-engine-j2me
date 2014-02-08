package engine.api;

import javax.microedition.lcdui.Display;

/**
 *
 * @author KiQDominaN
 */
public final class AFTRuntime {

    public static Display DEVICE_DISPLAY; //For change Displayable from some places
    public static int SCREEN_WIDTH;
    public static int SCREEN_HEIGHT;
    public static int HALF_SCREEN_WIDTH; //We use this value many times, so it's clever idea to store this value
    public static int HALF_SCREEN_HEIGHT; //Vide supra :)
    public static int KEY = AFTControllable.NULL_KEY, LAST_KEY = AFTControllable.NULL_KEY; //Pressed buttons will be stored here
    public static int POINTER_X = AFTControllable.NULL_KEY, POINTER_Y = AFTControllable.NULL_KEY; //For touchscreen. Sadly, but it seems there are no more cell phones with buttons in the world
    public static boolean POINTER_DRAGGED = false; //Vide supra :)
    public static float frametime; //For stable work with different FPS

    public static boolean keyPressed(int key) {
        return (KEY == key || LAST_KEY == key);
    }

    public static void resetKeys() {
        KEY = LAST_KEY = AFTControllable.NULL_KEY;
    }

    public static void resetPointer() {
        POINTER_X = POINTER_Y = AFTControllable.NULL_KEY;
        POINTER_DRAGGED = false;
    }
}
