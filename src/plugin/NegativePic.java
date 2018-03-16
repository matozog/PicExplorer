package plugin;

import java.awt.Color;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

public class NegativePic {

	
	public Image negativePic(String imagePath) {
		BufferedImage bufferedImg = null;
		File fileIn = null;
		File fileOut = null;
		
		try {
			bufferedImg = ImageIO.read(new File(imagePath));
		}catch(IOException e)
		{
			e.printStackTrace();
		}
		
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
		/*try {
			fileOut = new File("C:\\Users\\Mateusz\\workspace\\System-walker\\convertedImg\\" + fileIn.getName());
			System.out.println("C:\\Users\\Mateusz\\workspace\\System-walker\\convertedImg\\" + fileIn.getName());
			ImageIO.write(bufferedImg, "jpg", fileOut);
		}catch(IOException e)
		{
			e.printStackTrace();
		}*/
	}
}
