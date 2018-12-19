package pl.edu.agh.to2.yadc.render;

import java.awt.AlphaComposite;
import java.awt.Canvas;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.GraphicsConfiguration;
import java.awt.image.BufferedImage;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.Map;

import javax.imageio.ImageIO;


public class ImageLoader {

    private Map<String, BufferedImage> cachedImages;
    private final int MAX_IMAGE_CACHE_SIZE = 100;
    private Canvas canvas;

    public ImageLoader(Canvas canvas) {
        this.canvas = canvas;
        this.cachedImages = new HashMap<>();
    }

    public BufferedImage fetchImage(String imagePath) {
        
        if (cachedImages.get(imagePath) != null) {
            return cachedImages.get(imagePath);
        } 
        
        try {

            BufferedImage image = ImageIO.read(Paths.get(imagePath).toUri().toURL());
            GraphicsConfiguration graphConfig = this.canvas.getGraphicsConfiguration();
            BufferedImage formatted = graphConfig.createCompatibleImage(image.getWidth(), image.getHeight());

            // Transparency Tests
            // Graphics2D graphics2d = formatted.createGraphics();
            // graphics2d.setComposite(AlphaComposite.Clear);
            // graphics2d.fillRect(0, 0, image.getWidth(), image.getHeight());
            // graphics2d.setComposite(AlphaComposite.Src);
            // graphics2d.drawImage(image, 0, 0, image.getWidth(), image.getHeight(), null);
            // graphics2d.dispose();
            
            formatted.getGraphics().drawImage(image, 0, 0, image.getWidth(), image.getHeight(), null);

            if(cachedImages.size() > MAX_IMAGE_CACHE_SIZE) {
                cachedImages.clear();
            }
            cachedImages.put(imagePath, formatted);

            return formatted;
            
        } catch (Exception e) {
            System.err.println("Error while loading image");
            e.printStackTrace();
            return null;
		}
    }

}