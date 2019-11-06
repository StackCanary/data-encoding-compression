package gui;

import javax.swing.JFrame;
import javax.swing.SwingUtilities;

public class Gooey extends JFrame {
	private static final long serialVersionUID = 1L;
	
	public OuterPanel oPanel = new OuterPanel();
	
	public Gooey()
	{
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		// setUndecorated(true);
		setTitle("Data Encoding - Compression");
		setResizable(false);
		setSize(640, 600);
		
		add(oPanel);
		setJMenuBar(new MenuBar(oPanel.mPanel, oPanel.table));
		
		Drag drag = new Drag();
		addMouseListener( drag );
		addMouseMotionListener( drag );
		
	}
	
	public static void main(String...strings)
	{
		
		SwingUtilities.invokeLater(() -> {
				new Gooey().setVisible(true);
			}
		);
		
	}

}
