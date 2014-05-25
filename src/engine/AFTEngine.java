package engine;

import engine.api.AFTControllable;
import engine.api.AFTRuntime;
import javax.microedition.lcdui.Graphics;
import javax.microedition.lcdui.Image;

/**
 *
 * @author KiQDominaN
 */
public final class AFTEngine {

    //Engine states
    public static final byte IN_LOADING = 0, IN_PAUSED = 1, IN_RUNNING = 2, IN_STOPPING = 3, IN_GUI = 4;
    public byte engine_state;
    //Virtual graphics
    private final Image virtual_screen;
    public Graphics virtual_graphics;
    //FPS
    private int fps, tmp_fps;
    private long fpsUpdate;
    //Delta
    private long lastFrameTime;

    public AFTEngine() {
        //Virtual screen
        virtual_screen = Image.createImage(AFTRuntime.SCREEN_WIDTH, AFTRuntime.SCREEN_HEIGHT);
        virtual_graphics = virtual_screen.getGraphics();
    }

    public void start() {
        lastFrameTime = System.currentTimeMillis();
    }

    public void frame(Graphics g) {
        switch (engine_state) {
            case IN_RUNNING:
                processGame();
                processKeys();
                break;
            case IN_GUI:
                processGui();
                break;
            case IN_PAUSED:
                pause();
                processKeys();
                break;
            case IN_STOPPING:
                stop();
                break;
        }

        //FPS
        long currentFrameTime = System.currentTimeMillis();
        if (currentFrameTime - fpsUpdate >= 1000) {
            fpsUpdate = currentFrameTime;
            fps = tmp_fps;
            tmp_fps = 0;
        } else tmp_fps++;

        //Delta time
        AFTRuntime.frametime = (currentFrameTime - lastFrameTime) / 10f;
        lastFrameTime = currentFrameTime;
    }

    public void saveGame(boolean quickave) {
    }

    public void loadGame(boolean quickave) {
    }

    private void processGame() {
    }

    private void processGui() {
    }

    private void processKeys() {
    }

    private void pause() {
    }

    private void stop() {
    }

    private void saveSettings() {
        AFTControllable.saveKeyLayout();
    }

    private void loadSettings() {
        AFTControllable.loadKeyLayout();
    }
}
