package engine.world;

import java.util.Vector;
import javax.microedition.lcdui.Graphics;

/**
 *
 * @author KiQDominaN
 */
public class AFTWorld {

    public int width, height;
    public float world_x, world_y;
    private Vector objects, visible_objects;
    private AFTWeather weather;

    public void load(String name) {
        weather = new AFTWeather();
    }

    public void update() {
        //Update weather
        weather.calculateEnviroiment();
    }

    public void draw(Graphics g) {
        weather.draw(g);
    }

    public void addObject(AFTPointMapObject obj) {
        objects.addElement(obj);
    }

    //Some old code :(
    //But nice code :)
    private void sortObjects() {
        AFTPointMapObject obj1, obj2;
        for (int j = 0; j < visible_objects.size(); j++) {
            for (int i = visible_objects.size() - 2; i >= j; i--) {
                obj1 = (AFTPointMapObject) visible_objects.elementAt(i);
                obj2 = (AFTPointMapObject) visible_objects.elementAt(i + 1);

                if (obj1.world_z < obj2.world_z) {
                    visible_objects.setElementAt(obj2, i);
                    visible_objects.setElementAt(obj1, i + 1);
                }
            }
        }
    }

    //Physics bleat!
    public final static boolean collides(AFTPointMapObject obj1, AFTPointMapObject obj2) {
        return (obj1.world_x + obj1.bbox_a <= obj2.world_x + obj2.bbox_c
                && obj2.world_x + obj2.bbox_a <= obj1.world_x + obj1.bbox_c
                && obj1.world_y + obj1.bbox_b <= obj2.world_y + obj2.bbox_d
                && obj2.world_y + obj2.bbox_b <= obj1.world_y + obj1.bbox_d
                && obj1.world_z == obj2.world_z);
    }

}
