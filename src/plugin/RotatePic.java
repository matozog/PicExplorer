package plugin;

import java.awt.Image;
import java.awt.geom.AffineTransform;
import java.awt.image.AffineTransformOp;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

public class RotatePic extends PictureManager {

	
	public RotatePic(String imagePath)
	{
		super(imagePath);
	}

	public Image rotatePic() {

		AffineTransform transform = new AffineTransform();
		transform.rotate(radians, bufferedImg.getWidth() / 2, bufferedImg.getHeight() / 2);
		AffineTransformOp op = new AffineTransformOp(transform, AffineTransformOp.TYPE_BILINEAR);
		bufferedImg = op.filter(bufferedImg, null);

		return bufferedImg;
	}
}
