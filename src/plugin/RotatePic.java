package plugin;

import java.awt.Image;
import java.awt.geom.AffineTransform;
import java.awt.image.AffineTransformOp;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

public class RotatePic {

	double radians = 3.1415;
	public Image rotatePic(String imagePath) {
		BufferedImage inImg = null;

		try {
			inImg = ImageIO.read(new File(imagePath));
		} catch (IOException e) {
			e.printStackTrace();
		}
		AffineTransform transform = new AffineTransform();
		transform.rotate(radians, inImg.getWidth() / 2, inImg.getHeight() / 2);
		AffineTransformOp op = new AffineTransformOp(transform, AffineTransformOp.TYPE_BILINEAR);
		inImg = op.filter(inImg, null);

		return inImg;
	}
}
