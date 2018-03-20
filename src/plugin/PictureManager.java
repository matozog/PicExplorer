package plugin;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

public class PictureManager {

	public static double radians = 3.1415;
	protected BufferedImage bufferedImg = null;
	
	public PictureManager(String imagePath)
	{
		try {
			bufferedImg = ImageIO.read(new File(imagePath));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
}
