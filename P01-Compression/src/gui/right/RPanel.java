package gui.right;

import java.awt.Color;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.util.ArrayList;

import javax.swing.BorderFactory;
import javax.swing.ButtonGroup;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JTextArea;

import gui.table.Table;

public class RPanel extends JPanel {
	
	RPanelTop rPanelTop = new RPanelTop();
	RPanelMid rPanelMid = new RPanelMid();
	
	public RPanel(JTextArea eBox, JTextArea dBox, Table table)
	{
		setLayout(new GridLayout(3, 0));

		add(rPanelTop);
		add(rPanelMid);
		add(new RPanelLow(eBox, dBox, rPanelTop, table));
	}
	
}
