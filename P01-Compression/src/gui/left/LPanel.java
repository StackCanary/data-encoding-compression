package gui.left;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.Insets;

import javax.swing.JPanel;
import javax.swing.JScrollPane;

import gui.table.Table;

public class LPanel extends JPanel {
	private static final long serialVersionUID = 1L;

	
	public LPanel(Table table)
	{
		
		setLayout(new GridBagLayout());
		
		GridBagConstraints gbc = new GridBagConstraints();
		
	
		gbc.gridx = 0;
		gbc.gridy = 0;
		gbc.gridheight = 2;
		gbc.gridwidth = 1;
		gbc.weightx = 1;
		gbc.weighty = 20;
		gbc.fill = GridBagConstraints.BOTH;
		add(new JScrollPane(table), gbc);

		gbc = new GridBagConstraints();
		
		final int h = 5;
		final int v = 5;
 		
		gbc.insets = new Insets(h, v, h, v);
		gbc.gridx = 0;
		gbc.gridy = 2;
		gbc.gridheight = 1;
		gbc.gridwidth = 1;
		gbc.weightx = 1;
		gbc.weighty = 1;
		gbc.fill = GridBagConstraints.BOTH;
		add(new LUnderPanel(table), gbc);
	}
	
}
