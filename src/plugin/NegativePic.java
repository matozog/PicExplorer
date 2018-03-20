package plugin;

import java.awt.Color;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

public class NegativePic extends PictureManager {

	
	public NegativePic(String imagePath) {
		super(imagePath);
	}

	public Image negativePic() {
		
		for(int i=0; i<bufferedImg.getHeight();i++)
		{
			for(int j=0; j<bufferedImg.getWidth();j++)
			{
                int rgb = bufferedImg.getRGB(j, i);
                Color col = new Color(rgb, true);
                col = new Color(255 - col.getRed(),
                        255 - col.getGreen(),
                        255 - col.getBlue());
                bufferedImg.setRGB(j, i, col.getRGB());
			}
		}
		
		return bufferedImg;
	}
}
