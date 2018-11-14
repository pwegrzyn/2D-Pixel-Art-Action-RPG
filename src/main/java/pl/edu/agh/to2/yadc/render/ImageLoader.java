package pl.edu.agh.to2.yadc.render;

import java.awt.GraphicsConfiguration;
import java.awt.image.BufferedImage;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.Map;

import javax.imageio.ImageIO;


public class ImageLoader {

    private static Map<String, BufferedImage> cachedImages = new HashMap<>();
    private static final int MAX_IMAGE_CACHE_SIZE = 100;

    public static BufferedImage fetchImage(String imagePath) {
        
        if (cachedImages.get(imagePath) != null) {
            return cachedImages.get(imagePath);
        } 
        
        try {

            BufferedImage image = ImageIO.read(Paths.get(imagePath).toUri().toURL());
            GraphicsConfiguration graphConfig = RenderManager.getMainCanvas().getGraphicsConfiguration();
            BufferedImage formatted = graphConfig.createCompatibleImage(image.getWidth(), image.getHeight());

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