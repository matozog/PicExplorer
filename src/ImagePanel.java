import java.awt.Graphics;
import java.awt.Image;

import javax.swing.ButtonGroup;
import javax.swing.JPanel;
import javax.swing.JRadioButton;

public class ImagePanel extends JPanel {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Image image;
	private ButtonGroup btnGroup;
	private JRadioButton btnNorm;
	
	public ImagePanel(ButtonGroup btnGroup, JRadioButton btnNorm)
	{
		this.btnGroup=btnGroup;
		this.btnNorm=btnNorm;
	}
	
	 @Override
	protected void paintComponent(Graphics g) {
		super.paintComponent(g);
		if(image==null)
			g.clearRect(454, 13, this.getWidth(), this.getHeight());
		else
			g.drawImage(image, 0, 0, this);
	 }

	 public void deleteImage()
	 {
		 image = null;
	 }

	 public void setSelectionOnNorm()
	 {
		 btnNorm.setSelected(true);
	 }
	 
	 void setImage(Image image)
	{
		if(image!=null)
		{
			Image scaledImage = image.getScaledInstance(this.getWidth(), this.getHeight(), Image.SCALE_AREA_AVERAGING);
			this.image = scaledImage;
		}
	}
	
}
