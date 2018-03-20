import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.Map;
import java.util.WeakHashMap;

import javax.imageio.ImageIO;
import javax.swing.BorderFactory;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;

public class RepainterPanel extends Thread implements MouseListener {

	private JPanel panelIcon;
	private ImagePanel panelImage;
	private File[] filesList;
	private boolean paint;
	private WeakHashMap<File, JLabel> hashMap = new WeakHashMap<File, JLabel>();
	private JScrollPane scroll;
	private int numberOfPicture = -1;
	private Image img;
	private ImageIcon imgIcon;

	public RepainterPanel(JPanel panelIcon, ImagePanel panelImage, JScrollPane scroll) {
		this.panelIcon = panelIcon;
		this.panelImage = panelImage;
		this.scroll = scroll;
	}

	@Override
	public void run() {
		int h = 0, w = 0;
		while (true) {
			if (paint) {
				hashMap.clear();
				numberOfPicture = -1;
				h = 0;
				w = 0;
				panelIcon.removeAll();
				panelIcon.setPreferredSize(new Dimension(201, 50));
				for (int i = 0; i < filesList.length; i++) {
					if (i % 3 == 0 && i != 0) 
					{
						w = 0;
						h++;
						panelIcon.setPreferredSize(new Dimension(panelIcon.getWidth(), (h + 1) * 58));
					}
					imgIcon = new ImageIcon(filesList[i].getAbsolutePath());
					img = imgIcon.getImage();
					img = img.getScaledInstance(50, 50, Image.SCALE_SMOOTH);
					
					JLabel label = new JLabel();
					label.setBounds(5 + w, 50 * h + 8 * h, 50, 50);
					label.setIcon(new ImageIcon(img));
					label.setBorder(BorderFactory.createLineBorder(Color.BLACK, 1));
					label.addMouseListener(this);
					hashMap.put(filesList[i], label);
					panelIcon.add(label);
					panelIcon.repaint();
					scroll.setViewportView(panelIcon);
					w += 55;
				}
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

		for (int i = 0; i < hashMap.size(); i++) {
			if (z == hashMap.get(filesList[i])) {
				hashMap.get(filesList[i]).setBorder(BorderFactory.createLineBorder(Color.RED, 1));
				panelImage.removeAll();
				try {
					panelImage.setImage(ImageIO.read(new FileInputStream(filesList[i].getAbsolutePath())));
				} catch (FileNotFoundException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				} catch (IOException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				panelImage.setSelectionOnNorm();
				panelImage.repaint();
				numberOfPicture = i;
			} else
				hashMap.get(filesList[i]).setBorder(BorderFactory.createLineBorder(Color.BLACK, 1));
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

	public boolean getPaint() {
		return paint;
	}

	public void setPaint(boolean paint) {
		this.paint = paint;
	}

	public void setFileList(File[] filesList) {
		this.filesList = filesList;
	}

	public File[] getFileList() {
		return filesList;
	}

	public int getNumberOfPic() {
		return numberOfPicture;
	}
}
