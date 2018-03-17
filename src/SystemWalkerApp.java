import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.EventQueue;
import java.awt.Image;
import java.awt.ScrollPane;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FilenameFilter;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

import javax.imageio.ImageIO;
import javax.swing.BorderFactory;
import javax.swing.ButtonGroup;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTree;
import javax.swing.SwingWorker;
import javax.swing.event.TreeExpansionEvent;
import javax.swing.event.TreeExpansionListener;
import javax.swing.event.TreeSelectionEvent;
import javax.swing.event.TreeSelectionListener;
import javax.swing.event.TreeWillExpandListener;
import javax.swing.filechooser.FileNameExtensionFilter;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeModel;
import javax.swing.tree.ExpandVetoException;
import javax.swing.tree.TreePath;

import edu.stanford.nlp.trees.tregex.gui.FileTreeNode;

import javax.swing.JRadioButton;
import javax.swing.JButton;
import javax.swing.ScrollPaneConstants;

public class SystemWalkerApp implements TreeSelectionListener, TreeWillExpandListener, ActionListener {

	private JFrame frame;
	private JPanel panelJTree,panelMiniatures;
	private ImagePanel panelImage;
	private JTree tree;
	private DefaultTreeModel treeModel;
	private DefaultMutableTreeNode mutTreeNode;
	private JScrollPane scrollTree,scrollMiniature;
	private RepainterPanel workerIconPanel;
	private FileNameExtensionFilter jpgFilter;
	private JRadioButton rdbtnNormal,rdbtnRotation,rdbtnNegative,rdbtnBlurry;
	private ButtonGroup btnGroup;
	private PicturesClassLoader picClassLoader;
	private File[] actualFileList;
	private Image actualImage;
	
	/**
	 * Create the application.
	 */
	public SystemWalkerApp() {
		frame = new JFrame();
		frame.setBounds(100, 100, 813, 481);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(null);
		frame.setLocationRelativeTo(null);
		frame.setTitle("System walker");
		
		jpgFilter = new FileNameExtensionFilter("JPEG file", "jpg", "jpeg");
		
		panelJTree = new JPanel();
		panelJTree.setBounds(12, 13, 237, 421);
		frame.getContentPane().add(panelJTree);
		panelJTree.setLayout(null);
		
		
		File[] files = new File("C:/").listFiles();
		mutTreeNode = new DefaultMutableTreeNode("C:");
		createFilesHierarchy(files,mutTreeNode);
		treeModel = new DefaultTreeModel(mutTreeNode);
		
		scrollTree = new JScrollPane();
		scrollTree.setBounds(0, 0, 237, 424);
		scrollTree.setBorder(BorderFactory.createTitledBorder("Navigation"));
		tree = new JTree(mutTreeNode,true);
		tree.setShowsRootHandles(true);
		tree.addTreeSelectionListener(this);
		tree.addTreeWillExpandListener(this);
		tree.setToggleClickCount(1);
		scrollTree.setViewportView(tree);
		panelJTree.add(scrollTree);
		
		
		
		
		
		panelMiniatures = new JPanel();
//		panelMiniatures.setSize(new Dimension(201, 421));
		panelMiniatures.setPreferredSize(new Dimension(201,50));
		//panelMiniatures.setBounds(261, 13, 201, 421);
		panelMiniatures.setLayout(null);
		//panelMiniatures.setBorder(BorderFactory.createTitledBorder(""));

		//panelMiniatures.setBounds(261, 13, 201, 421);
		scrollMiniature = new JScrollPane();    
		scrollMiniature.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
		scrollMiniature.setBounds(261, 13, 201, 421);
		scrollMiniature.setBorder(BorderFactory.createTitledBorder("Miniatures"));
		//scrollMiniature.setHorizontalScrollBarPolicy(ScrollPane.SCROLLBARS_NEVER);
		//scrollMiniature.setPreferredScroable
		scrollMiniature.setViewportView(panelMiniatures);
		//scrollMiniature.setBorder(BorderLayout.CENTER);
		//panelMiniatures.add(scrollMiniature,BorderLayout.CENTER);
		frame.getContentPane().add(scrollMiniature, BorderLayout.CENTER);
		
		
		
		
		
		rdbtnNormal = new JRadioButton("Normal");
		rdbtnNormal.setSelected(true);
		rdbtnNormal.setBounds(660, 266, 123, 25);
		rdbtnNormal.addActionListener(this);
		frame.getContentPane().add(rdbtnNormal);
		
		rdbtnRotation = new JRadioButton("Rotation");
		rdbtnRotation.setBounds(484, 266, 123, 25);
		rdbtnRotation.addActionListener(this);
		frame.getContentPane().add(rdbtnRotation);
		
		rdbtnNegative = new JRadioButton("Negative");
		rdbtnNegative.setBounds(484, 296, 123, 25);
		rdbtnNegative.addActionListener(this);
		frame.getContentPane().add(rdbtnNegative);
		
		rdbtnBlurry = new JRadioButton("Blurry");
		rdbtnBlurry.setBounds(660, 296, 123, 25);
		rdbtnBlurry.addActionListener(this);
		frame.getContentPane().add(rdbtnBlurry);
		
		btnGroup = new ButtonGroup();
		btnGroup.add(rdbtnNormal);
		btnGroup.add(rdbtnRotation);
		btnGroup.add(rdbtnNegative);
		btnGroup.add(rdbtnBlurry);
		
		panelImage =  new ImagePanel(btnGroup,rdbtnNormal);
		panelImage.setBounds(474, 13, 309, 244);
		panelImage.setBorder(BorderFactory.createTitledBorder("Image"));
		frame.getContentPane().add(panelImage);
		panelImage.setLayout(null);
		
		workerIconPanel = new RepainterPanel(panelMiniatures, panelImage,scrollMiniature);
		

		workerIconPanel.start();
		picClassLoader = new PicturesClassLoader();
	}
	

	@Override
	public void actionPerformed(ActionEvent e) {
		Object z = e.getSource();
		
		if(z==rdbtnNormal)
		{
			try {
				actualImage = ImageIO.read(actualFileList[workerIconPanel.getNumberOfPic()].getAbsoluteFile());
				panelImage.setImage(actualImage);
				panelImage.repaint();
			} catch (IOException e1) {
				e1.printStackTrace();
			}
		}
		else if(z==rdbtnNegative)
		{
			setPictureModification("plugin.NegativePic", "negativePic");
		}
		else if(z==rdbtnBlurry)
		{
			setPictureModification("plugin.BlurryPic", "blurryPic");
		}
		else if(z==rdbtnRotation)
		{
			setPictureModification("plugin.RotatePic", "rotatePic");
		}
	}

	public void setPictureModification(String className, String methodName)
	{
		try {
			if(workerIconPanel.getNumberOfPic()!=-1)
			{
				actualImage = (Image) picClassLoader.loadPictureClass(className, methodName, actualFileList[workerIconPanel.getNumberOfPic()].getAbsolutePath());
				panelImage.setImage(actualImage);
				panelImage.repaint();
			}
		} catch (IllegalArgumentException | SecurityException e1) {
			e1.printStackTrace();
		}
	}
	
	@Override
	public void valueChanged(TreeSelectionEvent e) {
		TreePath treePath = tree.getSelectionPath();

		String treeSelectedPath = "";
		Object[] paths = tree.getSelectionPath().getPath();
		for (int i = 0; i < paths.length; i++) {
			treeSelectedPath += paths[i];
			if (i + 1 < paths.length) {
				treeSelectedPath += File.separator;
			}
		}
		
		if(tree.getSelectionPath().getPath()!=null)
		{
			DefaultMutableTreeNode selectedNode = (DefaultMutableTreeNode) tree.getLastSelectedPathComponent();
			File[] files = new File(treeSelectedPath).listFiles();
			createFilesHierarchy(files, selectedNode);
		}
		repaintMiniaturePanel(treeSelectedPath);
	}

	@Override
	public void treeWillExpand(TreeExpansionEvent event) throws ExpandVetoException {
		TreePath treePath = event.getPath();

		DefaultMutableTreeNode selectedNode = (DefaultMutableTreeNode) treePath.getLastPathComponent();
		String treeSelectedPath = "";
		
		Object[] paths = treePath.getPath();
		for (int i = 0; i < paths.length; i++) {
			treeSelectedPath += paths[i];
			if (i + 1 < paths.length) {
				treeSelectedPath += File.separator;
			}
		}
		if (selectedNode.getChildCount() == 0) {
			
			File[] files = new File(treeSelectedPath).listFiles();
			createFilesHierarchy(files, selectedNode);
			
		}
		repaintMiniaturePanel(treeSelectedPath);
	}

	public void repaintMiniaturePanel(String path)
	{
		File folder = new File(path);
		if (folder.isDirectory()) {
			if (getImageFiles(folder) != null) {
				if (getImageFiles(folder).length != 0) {
					actualFileList= getImageFiles(folder);
					workerIconPanel.setFileList(actualFileList);
					workerIconPanel.setPaint(true);
				} else {
					panelMiniatures.removeAll();
					panelMiniatures.repaint();
					panelImage.deleteImage();
					panelImage.repaint();
				}
			} else {
				panelImage.deleteImage();
				panelImage.repaint();
				panelMiniatures.removeAll();
				panelMiniatures.repaint();
			}
		}
	}

	@Override
	public void treeWillCollapse(TreeExpansionEvent event) throws ExpandVetoException {
		// TODO Auto-generated method stub
		
	}
	
	public void createFilesHierarchy(File[] files,DefaultMutableTreeNode mutTree)
	{
		if(files != null)
		for(File file:files)
		{
			if(file.isDirectory())
			{
				DefaultMutableTreeNode treeNode = new DefaultMutableTreeNode(file.getName());
				treeNode.setAllowsChildren(true);
				mutTree.add(treeNode);
			}
			else
			{
				DefaultMutableTreeNode mtn = new DefaultMutableTreeNode(file.getName());
				mtn.setAllowsChildren(false);
				mutTree.add(mtn);
			}
		}
	}
	
	public File[] getImageFiles(File folder)
	{
		return folder.listFiles(new FilenameFilter() {public boolean accept (File folder, String filename) {return filename.endsWith(".jpg");}});
	}
	
	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					SystemWalkerApp window = new SystemWalkerApp();
					window.frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}
}