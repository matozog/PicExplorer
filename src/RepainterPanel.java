import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.Map;

import javax.imageio.ImageIO;
import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.SwingWorker;

public class RepainterPanel extends Thread implements MouseListener {

	private JPanel panelIcon;
	private ImagePanel panelImage;
	private File[] filesList;
	private boolean paint;
	private ArrayList<JLabel> listOfLabels;
	private ArrayList<Image> listOfImages;
	private JScrollPane scroll;
	private int numberOfPicture=-1;

	public RepainterPanel(JPanel panelIcon, ImagePanel panelImage, JScrollPane scroll)
	{
		this.panelIcon=panelIcon;
		this.panelImage = panelImage;
		listOfLabels = new ArrayList<JLabel>();
		listOfImages = new ArrayList<Image>();
		this.scroll = scroll;
	}

	@Override
	public void run() {
		while(true)
		{
			if(paint)
			{
				listOfImages.clear();
				listOfLabels.clear();
				numberOfPicture = -1;
				System.gc();
				int h=0,w=0;
				panelIcon.removeAll();
				panelIcon.setPreferredSize(new Dimension(201,50));
				for(int i=0; i<filesList.length; i++)
				{
					if (i % 3 == 0 && i != 0) {
						w=0;
						h++;
						panelIcon.setPreferredSize(new Dimension(panelIcon.getWidth(), (h+1)*58));
					}
					try {
						WeakReference<Image> wr1 = new WeakReference<Image>(ImageIO.read(new FileInputStream(filesList[i].getAbsolutePath())));
						Image img = (Image) wr1.get();
						WeakReference<JLabel> wr= new WeakReference<JLabel>(new JLabel());
						JLabel label = (JLabel) wr.get();
						label.setBounds(5 + w, 50 * h + 8 * h, 50, 50);
						label.setIcon(scaleImage(img, label.getWidth(), label.getHeight()));
						label.setBorder(BorderFactory.createLineBorder(Color.BLACK, 1));
						label.addMouseListener(this);
						listOfLabels.add(label);
						listOfImages.add(img);
						panelIcon.add(label);
						panelIcon.repaint();
					} catch (IOException e) {
						e.printStackTrace();
					}
					w += 55;
				}
				scroll.setViewportView(panelIcon);
				paint = false;
			}
			try {
				Thread.sleep(10);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}	
	}

	@Override
	public void mouseClicked(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mousePressed(MouseEvent e) {
		Object z = e.getSource();
		
		for(int i=0; i<listOfLabels.size();i++)
		{
			if(z==listOfLabels.get(i))
			{
				listOfLabels.get(i).setBorder(BorderFactory.createLineBorder(Color.RED, 1));
				panelImage.removeAll();
				panelImage.setImage(listOfImages.get(i));
				panelImage.setSelectionOnNorm();
				panelImage.repaint();
				numberOfPicture = i;
			}
			else listOfLabels.get(i).setBorder(BorderFactory.createLineBorder(Color.BLACK, 1));
		}
	}

	@Override
	public void mouseReleased(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseEntered(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseExited(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}
	
	public ImageIcon scaleImage(Image img, int width, int height)
	{
		Image scaleImg = img.getScaledInstance(width, height, Image.SCALE_SMOOTH);
		ImageIcon imgIcon= new ImageIcon(scaleImg);
		return imgIcon;
	}
	
	public boolean getPaint() {
		return paint;
	}


	public void setPaint(boolean paint) {
		this.paint = paint;
	}

	public void setFileList(File[] filesList)
	{
		this.filesList=filesList;
	}
	
	public File[] getFileList()
	{
		return filesList;
	}
	
	public int getNumberOfPic()
	{
		return numberOfPicture;
	}
}
