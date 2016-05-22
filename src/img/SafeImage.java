package img;

import java.io.InputStream;

import javafx.scene.image.Image;
import javafx.scene.image.WritableImage;

public class SafeImage {
    
    Image image;
    
    public SafeImage(String path) {
        this(path, 0, 0);
    }
    
    public SafeImage(String path, int width, int height) {
        InputStream imageStream = SafeImage.class.getResourceAsStream(path);
        if (imageStream != null) {
            image = new Image(SafeImage.class.getResourceAsStream(path));
        } else {
            image = new WritableImage(width, height);
        }
    }
    
    public Image getImage() {
        return image;
    }
}
