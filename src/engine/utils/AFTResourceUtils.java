package engine.utils;

import java.io.IOException;
import java.util.Hashtable;
import javax.microedition.lcdui.Graphics;
import javax.microedition.lcdui.Image;

/**
 *
 * @author KiQDominaN
 */
public final class AFTResourceUtils {

    private static final Hashtable cached_images = new Hashtable(); //Image cache
    private static final Image null_image = create_null_image(); //Emo-texture for missed images :)

    private static Image create_null_image() {
        Image tmp = Image.createImage(32, 32);
        Graphics g = tmp.getGraphics();

        g.setColor(0);
        g.fillRect(0, 0, 32, 32);

        g.setColor(0xff00ff);
        for (int y = 0; y < 32; y += 16) {
            for (int x = 0; x < 32; x += 16) {
                g.fillRect(x, y, 8, 8);
                g.fillRect(x + 8, y + 8, 8, 8);
            }
        }

        return tmp;
    }

    public static boolean image_exists(String url) {
        try {
            Image.createImage(url);
            return true;
        } catch (IOException ex) {
            return false;
        }
    }

    public static Image load_image(String imageID) {
        if (!cached_images.containsKey(imageID)) {
            try {
                cached_images.put(imageID, Image.createImage(imageID));
            } catch (IOException ex) {
                return null_image;
            }
        }
        return (Image) cached_images.get(imageID);
    }

    public static Image load_image(String imageID, int width, int height) {
        if (!image_exists(imageID)) return resize_image(null_image, width, height, false);

        return resize_image(load_image(imageID), width, height, true);
    }

    //Resize
    public static Image resize_image(Image img, int w2, int h2, boolean interpolation) {
        return (interpolation) ? resize_image_bilinear(img, w2, h2) : resize_image_nearest(img, w2, h2);
    }

    private static Image resize_image_nearest(Image img, int w2, int h2) {
        int w = img.getWidth(), h = img.getHeight();

        if (w == w2 && h == h2) return img;

        int[] pixels = new int[w * h], tmp = new int[w2 * h2];
        img.getRGB(pixels, 0, w, 0, 0, w, h);

        int x_diff, y_diff;

        for (int y = 0; y < h2; y++) {
            y_diff = (y * h) / h2;
            for (int x = 0; x < w2; x++) {
                x_diff = (x * w) / w2;
                tmp[w2 * y + x] = pixels[w * y_diff + x_diff];
            }
        }

        return Image.createRGBImage(tmp, w2, h2, true);
    }

    private static Image resize_image_bilinear(Image img, int w2, int h2) {
        int w = img.getWidth(), h = img.getHeight();

        if (w == w2 && h == h2) return img;

        int[] pixels = new int[w * h], tmp = new int[w2 * h2];
        img.getRGB(pixels, 0, w, 0, 0, w, h);

        int a, b, c, d, x, y, index;
        float x_ratio = ((float) (w - 1)) / w2;
        float y_ratio = ((float) (h - 1)) / h2;
        float x_diff, y_diff, blue, red, green, alpha;
        int offset = 0;

        for (int i = 0; i < h2; i++) {
            for (int j = 0; j < w2; j++) {
                x = (int) (x_ratio * j);
                y = (int) (y_ratio * i);
                x_diff = (x_ratio * j) - x;
                y_diff = (y_ratio * i) - y;
                index = (y * w + x);

                a = pixels[index];
                b = pixels[index + 1];
                c = pixels[index + w];
                d = pixels[index + w + 1];

                alpha = ((a >> 24) & 0xff) * (1 - x_diff) * (1 - y_diff) + ((b >> 24) & 0xff) * (x_diff) * (1 - y_diff) + ((c >> 24) & 0xff) * (y_diff) * (1 - x_diff) + ((d >> 24) & 0xff) * (x_diff * y_diff);
                blue = (a & 0xff) * (1 - x_diff) * (1 - y_diff) + (b & 0xff) * (x_diff) * (1 - y_diff) + (c & 0xff) * (y_diff) * (1 - x_diff) + (d & 0xff) * (x_diff * y_diff);
                green = ((a >> 8) & 0xff) * (1 - x_diff) * (1 - y_diff) + ((b >> 8) & 0xff) * (x_diff) * (1 - y_diff) + ((c >> 8) & 0xff) * (y_diff) * (1 - x_diff) + ((d >> 8) & 0xff) * (x_diff * y_diff);
                red = ((a >> 16) & 0xff) * (1 - x_diff) * (1 - y_diff) + ((b >> 16) & 0xff) * (x_diff) * (1 - y_diff) + ((c >> 16) & 0xff) * (y_diff) * (1 - x_diff) + ((d >> 16) & 0xff) * (x_diff * y_diff);

                tmp[offset++] = ((int) blue) | (((int) green) << 8) | ((((int) red) << 16) | (((int) alpha) << 24));
            }
        }

        return Image.createRGBImage(tmp, w2, h2, true);
    }

    public final static Image set_transparency(Image img, float transparency, boolean save_alpha) {
        int w = img.getWidth(), h = img.getHeight();
        int[] tmp = new int[w * h];
        img.getRGB(tmp, 0, w, 0, 0, w, h);

        if (save_alpha)
            for (int i = 0; i < tmp.length; i++)
                tmp[i] &= ~((int) ((0xff & tmp[i] >> 24) * transparency) << 24);
        else {
            int a = ~(int) (0xff * transparency) << 24;
            for (int i = 0; i < tmp.length; i++) tmp[i] |= a;
        }

        return Image.createRGBImage(tmp, w, h, true);
    }

    //Blur
    public static Image smooth_image(Image img, int blurHor, int blurVer) {
        int w = img.getWidth(), h = img.getHeight();
        int[] pixels = new int[w * h], pixels2 = new int[pixels.length];
        img.getRGB(pixels, 0, w, 0, 0, w, h);

        pixels2 = box_blur(pixels, pixels2, w, h, blurHor);
        pixels = box_blur(pixels2, pixels, h, w, blurVer);

        return Image.createRGBImage(pixels, w, h, false);
    }

    private static int[] box_blur(int[] in, int[] out, int width, int height, int radius) {
        if (radius <= 0) return in;

        int widthMinus1 = width - 1;
        int tableSize = 2 * radius + 1;
        int divideLength = 256 * tableSize;
        int[] divide = new int[divideLength];

        for (int i = 0; i < divideLength; i++) divide[i] = i / tableSize;

        int inIndex = 0;

        for (int y = 0; y < height; y++) {
            int outIndex = y;
            int ta = 0, tr = 0, tg = 0, tb = 0;

            for (int i = -radius; i <= radius; i++) {
                int rgb = in[inIndex + clamp(i, 0, widthMinus1)];

                ta += (rgb >> 24) & 0xff;
                tr += (rgb >> 16) & 0xff;
                tg += (rgb >> 8) & 0xff;
                tb += rgb & 0xff;
            }

            for (int x = 0; x < width; x++) {
                out[outIndex] = (ta << 24) | (divide[tr] << 16) | (divide[tg] << 8) | divide[tb];

                int i1 = x + radius + 1;
                if (i1 > widthMinus1) i1 = widthMinus1;

                int i2 = x - radius;
                if (i2 < 0) i2 = 0;

                int rgb1 = in[inIndex + i1];
                int rgb2 = in[inIndex + i2];

                ta += ((rgb1 & rgb1 & 0xff000000) - (rgb2 & 0xff000000)) >> 24;
                tr += ((rgb1 & 0xff0000) - (rgb2 & 0xff0000)) >> 16;
                tg += ((rgb1 & 0xff00) - (rgb2 & 0xff00)) >> 8;
                tb += (rgb1 & 0xff) - (rgb2 & 0xff);
                outIndex += height;
            }

            inIndex += width;
        }

        return out;
    }

    private static int clamp(int x, int a, int b) {
        return (x < a) ? a : (x > b) ? b : x;
    }
}
