package gui.right;

import java.awt.Label;

import javax.swing.JLabel;
import javax.swing.JPanel;

public class RPanelMid extends JPanel {
	private static final long serialVersionUID = 1L;
	
	public JLabel label = new JLabel("Compression rate: ...");

	public RPanelMid()
	{
		add(label);
	}

}
