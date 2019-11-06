package gui.mid;

import java.awt.Color;
import java.awt.GridBagLayout;
import java.awt.GridLayout;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;

public class MPanel extends JPanel {
	
	public JTextArea encode = new JTextArea();
	public JTextArea decode = new JTextArea();
	public JTextArea lowest = new JTextArea();
	
	public MPanel()
	{
		
		this.setLayout(new GridLayout(3, 0));
		
		encode.setLineWrap(true);
		decode.setLineWrap(true);
		
		lowest.setEditable(false);
		
		add(new JScrollPane(encode));
		add(new JScrollPane(decode));
		add(new JScrollPane(lowest));

	}
}
