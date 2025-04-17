package toolbox;

import javax.swing.*;
import java.awt.*;
import java.net.URL;

public class ImageLoader {
    public static ImageIcon loadImage(String filename, int width, int height) {
        URL resource = ImageLoader.class.getClassLoader().getResource("images/" + filename);
        if (resource == null) return new ImageIcon(); // fallback
        ImageIcon icon = new ImageIcon(resource);
        Image scaledImage = icon.getImage().getScaledInstance(width, height, Image.SCALE_SMOOTH);
        return new ImageIcon(scaledImage);
    }
}
