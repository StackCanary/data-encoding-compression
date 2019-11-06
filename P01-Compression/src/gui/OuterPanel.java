package gui;

import java.awt.GridLayout;

import javax.swing.JPanel;

import gui.left.LPanel;
import gui.mid.MPanel;
import gui.right.RPanel;
import gui.table.Table;

public class OuterPanel extends JPanel {
	private static final long serialVersionUID = 1L;
	
	Table table = new Table();
	MPanel mPanel = new MPanel();
	
	public OuterPanel()
	{
		setLayout(new GridLayout(0, 2));

		/*
		 * Left Panel
		 */
		add(new LPanel(table));
		
		/*
		 * Middle Panel
		 */
		add(mPanel);
		
		/*
		 * Right Panel
		 */
	//	add(new RPanel(mPanel.encode, mPanel.decode, table));
		
	}
	
}
