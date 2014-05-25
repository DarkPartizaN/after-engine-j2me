package engine.world;

import javax.microedition.lcdui.Image;
import javax.microedition.lcdui.game.Sprite;

/**
 *
 * @author KiQDominaN
 */
public class AFTModel extends Sprite {

    private AFTAnimation current_animation;

    public AFTModel(Image image, int frameWidth, int frameHeight) {
        super(image, frameWidth, frameHeight);

        current_animation = new AFTAnimation();
    }

    public void setAnimation(AFTAnimation anim) {
        if (!current_animation.equals(anim)) current_animation = anim;
    }

    public void playAnimation() {
        setFrame(current_animation.getCurrentFrame());
    }

    public void playAnimation(AFTAnimation anim, float fps) {
        current_animation.setFps(fps);
        setFrame(current_animation.getCurrentFrame());
    }

}
