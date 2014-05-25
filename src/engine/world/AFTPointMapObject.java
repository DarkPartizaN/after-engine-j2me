package engine.world;

/**
 *
 * @author KiQDominaN
 */
public abstract class AFTPointMapObject {

    //Movetype
    public static final byte MOVETYPE_STATIC = 0, MOVETYPE_DYNAMIC = 1, MOVETYPE_FOLLOW = 2;
    public byte movetype;
    //Basic moving constants
    public static final short MOVE_DOWN = 90, MOVE_UP = 270, MOVE_LEFT = 180, MOVE_RIGHT = 0, MOVE_RIGHT_DOWN = 45, MOVE_LEFT_DOWN = 135, MOVE_RIGHT_UP = 315, MOVE_LEFT_UP = 225;
    //Available states
    public static final byte IN_IDLE = 0, IN_WALKING = 1, IN_RUNNING = 2, IN_TURN = 3, IN_SLEEPING = 4, IN_DEAD = 5;
    public byte state;
    //Types of damage
    public static final byte DMG_GENERIC = -1, DMG_BULLET = 0, DMG_BURN = 1, DMG_ACID = 2, DMG_FROST = 3, DMG_KNIFE = 4;
    //Needs collision?
    public boolean solid;
    //Collision flag
    public boolean collides = false;
    //Visible flag
    public boolean visible;
    //Model
    public AFTModel model;
    //World coordinates
    public float world_x, world_y, world_z; //world_z is just layer number! Not a full coordinate!
    //Size of object
    public int width, height;
    public int half_width, half_height;
    //Size for collision
    public float bbox_a, bbox_b, bbox_c, bbox_d; //x, y, x2, y2
    //Some parameters of game object
    public float life;
    public float protection = 0f;
    public float speed;
    public float current_speed;
    public float angle; //Rotating

    public void spawn() {
    }

    public void setState(byte state) {
        this.state = state;
    }

    public void setModel(AFTModel model) {
        this.model = model;
    }

    public void moveObject(float x, float y, float z) {
        world_x += x;
        world_y += y;
        world_z += z;
    }

    public void setWorldPosition(float x, float y, float z) {
        world_x = x;
        world_y = y;
        world_z = z;
    }

    public void Think() {
    }

    public void Touch(AFTPointMapObject obj) {
    }

    public void Die() {
    }

}
