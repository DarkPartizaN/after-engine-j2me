package engine.world;

/**
 *
 * @author KiQDominaN
 */
public final class AFTAnimation {

    private int[] frames;
    private float fps = 5f;
    private long last_time;
    private int current_frame = 0;

    public AFTAnimation() {
        //Do nothing
    }

    public AFTAnimation(float fps) {
        setFps(fps);
    }

    public void setSequence(AFTAnimation anim) {
        resetSequence();

        frames = anim.frames;
    }

    public void setSequence(int[] seq) {
        resetSequence();

        frames = seq;
    }

    public void setFps(float fps) {
        this.fps = fps + 0.1f;
    }

    public int getFrame(int num) {
        return frames[num];
    }

    public int getCurrentFrame() {
        return checkSequence();
    }

    public int getSequencePos() {
        return current_frame;
    }

    private int checkSequence() {
        long current_time = System.currentTimeMillis();
        if (current_time - last_time > 1000f / fps) {
            current_frame = (current_frame > frames.length - 1) ? 0 : current_frame + 1;
            last_time = current_time;
        }
        return frames[current_frame];
    }

    public void resetSequence() {
        current_frame = 0;
    }

}
