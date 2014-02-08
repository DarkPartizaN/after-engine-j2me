package engine.api;

import javax.microedition.lcdui.Graphics;
import javax.microedition.lcdui.game.GameCanvas;

/**
 * @author KiQDominaN
 */
public abstract class AFTAbstractScreen extends GameCanvas implements Runnable {

    protected final Graphics g = getGraphics();

    public AFTAbstractScreen() {
        super(false);
        setFullScreenMode(true);

        AFTRuntime.SCREEN_WIDTH = getWidth();
        AFTRuntime.SCREEN_HEIGHT = getHeight();
        AFTRuntime.HALF_SCREEN_WIDTH = getWidth() / 2;
        AFTRuntime.HALF_SCREEN_HEIGHT = getHeight() / 2;
    }

    protected void keyPressed(int keyCode) {
        AFTRuntime.LAST_KEY = AFTRuntime.KEY;
        AFTRuntime.KEY = keyCode;
    }

    protected void keyReleased(int keyCode) {
        if (keyCode == AFTRuntime.LAST_KEY) AFTRuntime.LAST_KEY = AFTControllable.NULL_KEY;
        if (keyCode == AFTRuntime.KEY) {
            AFTRuntime.KEY = AFTRuntime.LAST_KEY;
            AFTRuntime.LAST_KEY = AFTControllable.NULL_KEY;
        }
    }

    protected void pointerPressed(int x, int y) {
        AFTRuntime.POINTER_X = x;
        AFTRuntime.POINTER_Y = y;
    }

    protected void pointerDragged(int x, int y) {
        AFTRuntime.POINTER_X = x;
        AFTRuntime.POINTER_Y = y;

        AFTRuntime.POINTER_DRAGGED = true;
    }

    protected void pointerReleased(int x, int y) {
        AFTRuntime.resetPointer();
    }

    protected void init() {
    }

    public void run() {
    }

    protected void draw() {
    }
}
