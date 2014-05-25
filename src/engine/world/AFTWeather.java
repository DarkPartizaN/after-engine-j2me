package engine.world;

import engine.api.AFTRuntime;
import engine.utils.AFTMathUtils;
import engine.utils.AFTResourceUtils;
import javax.microedition.lcdui.Graphics;
import javax.microedition.lcdui.Image;

/**
 *
 * @author KiQDominaN
 */
public final class AFTWeather {

    //Enviroiment
    private final int[] atmosphere;
    public static int temperature;
    private long lastTemperatureCalculate = AFTTime.time;
    //Weather
    public static AFTWeatherType currentWeather;

    public AFTWeather() {
        //Initial weather
        currentWeather = new Clear();

        atmosphere = new int[AFTRuntime.SCREEN_WIDTH * AFTRuntime.SCREEN_HEIGHT];
        calcAtmosphereColor();
    }

    public void draw(Graphics g) {
        //Draw weather
        currentWeather.processWeather(g);
        g.drawImage(Image.createRGBImage(atmosphere, AFTRuntime.SCREEN_WIDTH, AFTRuntime.SCREEN_HEIGHT, true), 0, 0, 0);
    }

    public void calculateEnviroiment() {
        //Random weather
        if (AFTTime.time > currentWeather.precStartTime + currentWeather.precDuration) {
            //Clear
            if (currentWeather.type != AFTWeatherType.CLEAR && AFTMathUtils.random_int(0, 4500) <= 2)
                setWeather(new Clear(), AFTMathUtils.random_long(450, 1800));
            else if (AFTMathUtils.random_int(0, 5000) <= 2)
                setWeather(new Rain(), AFTMathUtils.random_long(900, 2700));
            else if (AFTMathUtils.random_int(0, 10000) <= 2)
                setWeather(new AcidRain(), AFTMathUtils.random_long(600, 1800));
        }

        //Calculate temperature
        if (AFTTime.time - lastTemperatureCalculate > 300) {
            temperature = (AFTTime.hours < 20) ? AFTMathUtils.random_int(5, 10) : AFTMathUtils.random_int(-5, 5);
            if (currentWeather.type == AFTWeatherType.RAIN || currentWeather.type == AFTWeatherType.ACID_RAIN) temperature -= AFTMathUtils.random_int(0, 2);

            lastTemperatureCalculate = AFTTime.time;
        }

        //Calculate atmosphere color every hour
        if (AFTTime.minutes == 0 && AFTTime.seconds == 0) calcAtmosphereColor();
    }

    private void calcAtmosphereColor() {
        //Set transparency & color of atmosphere
        switch (AFTTime.hours) {
            case 16:
                setAtmosphereColor(0x08, 0x08, 0x50, 0.05f);
                break;
            case 17:
                setAtmosphereColor(0x08, 0x08, 0x50, 0.1f);
                break;
            case 18:
                setAtmosphereColor(0x08, 0x08, 0x50, 0.15f);
                break;
            case 19:
                setAtmosphereColor(0x08, 0x08, 0x50, 0.2f);
                break;
            case 20:
                setAtmosphereColor(0x08, 0x08, 0x50, 0.25f);
                break;
            case 21:
                setAtmosphereColor(0x08, 0x08, 0x50, 0.3f);
                break;
            case 22:
                setAtmosphereColor(0x08, 0x08, 0x50, 0.35f);
                break;
            case 23:
                setAtmosphereColor(0x08, 0x08, 0x50, 0.4f);
                break;
            case 0:
            case 1:
            case 2:
                setAtmosphereColor(0x08, 0x08, 0x50, 0.45f);
                break;
            case 3:
                setAtmosphereColor(0x08, 0x08, 0x50, 0.4f);
                break;
            case 4:
                setAtmosphereColor(0x08, 0x08, 0x50, 0.35f);
                break;
            case 5:
                setAtmosphereColor(0x08, 0x08, 0x50, 0.3f);
                break;
            case 6:
                setAtmosphereColor(0x08, 0x08, 0x50, 0.25f);
                break;
            case 7:
                setAtmosphereColor(0x08, 0x08, 0x50, 0.2f);
                break;
            case 8:
                setAtmosphereColor(0x08, 0x08, 0x50, 0.15f);
                break;
            case 9:
                setAtmosphereColor(0x08, 0x08, 0x50, 0.1f);
                break;
            case 10:
                setAtmosphereColor(0x08, 0x08, 0x50, 0.05f);
                break;
            case 11:
            case 12:
            case 13:
            case 14:
            case 15:
                setAtmosphereColor(0x08, 0x08, 0x50, 0.0f);
                break;
        }
    }

    private void setAtmosphereColor(int r, int g, int b, float transparency) {
        int color = (b | g << 8 | r << 16 | (int) (0xff * transparency) << 24);

        for (int i = 0; i < atmosphere.length; i++) atmosphere[i] = color;
    }

    private void setWeather(AFTWeatherType t, long duration) {
        currentWeather = t;

        currentWeather.precStartTime = AFTTime.time;
        currentWeather.precDuration = duration;
    }

    //WEATHER TYPES
    public abstract class AFTWeatherType {

        public static final byte CLEAR = -1, RAIN = 0, SNOW = 1, ACID_RAIN = 2; //Particles types defines
        public byte type = CLEAR;
        protected int precIntensity;
        public long precStartTime, precDuration;

        public AFTWeatherType() {
        }

        public void processWeather(Graphics g) {
        }
    }

    public class Clear extends AFTWeatherType {
    }

    public class Rain extends AFTWeatherType {

        protected Image rain, drops;

        public Rain() {
            type = RAIN;

            precIntensity = AFTRuntime.SCREEN_WIDTH / 10;
            precStartTime = AFTTime.time;

            rain = AFTResourceUtils.load_image("/res/gfx/effects/rain.png", 2, 80);
            rain = AFTResourceUtils.set_transparency(rain, 0.6f, true);

            drops = AFTResourceUtils.load_image("/res/gfx/effects/rain_drop.png");
            drops = AFTResourceUtils.set_transparency(drops, 1.1f, true);

        }

        public void processWeather(Graphics g) {
            for (int i = 0; i < precIntensity; i++) {
                g.drawImage(rain, AFTMathUtils.random_int(0, AFTRuntime.SCREEN_WIDTH), AFTMathUtils.random_int(0, AFTRuntime.SCREEN_HEIGHT), 0);
                g.drawImage(drops, AFTMathUtils.random_int(0, AFTRuntime.SCREEN_WIDTH), AFTMathUtils.random_int(0, AFTRuntime.SCREEN_HEIGHT), 0);
            }
        }
    }

    public class AcidRain extends Rain {

        public AcidRain() {
            type = ACID_RAIN;

            precIntensity = AFTRuntime.SCREEN_WIDTH / 15;
            precStartTime = AFTTime.time;

            rain = AFTResourceUtils.load_image("/res/gfx/effects/rain_acid.png", 2, 80);
            rain = AFTResourceUtils.set_transparency(rain, 0.75f, true);

            drops = AFTResourceUtils.load_image("/res/gfx/effects/rain_drop.png");
            drops = AFTResourceUtils.set_transparency(drops, 1.5f, true);
        }

        public void processWeather(Graphics g) {
            for (int i = 0; i < precIntensity; i++) {
                g.drawImage(rain, AFTMathUtils.random_int(0, AFTRuntime.SCREEN_WIDTH), AFTMathUtils.random_int(0, AFTRuntime.SCREEN_HEIGHT), 0);
                g.drawImage(drops, AFTMathUtils.random_int(0, AFTRuntime.SCREEN_WIDTH), AFTMathUtils.random_int(0, AFTRuntime.SCREEN_HEIGHT), 0);
            }
        }
    }

}
