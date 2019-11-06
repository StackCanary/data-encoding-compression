package gui.left;

import java.awt.Color;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.math.BigDecimal;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JTextField;

import gui.table.Table;

public class LUnderPanel extends JPanel {
	private static final long serialVersionUID = 1L;
	
	public JButton delete = new JButton("Delete");
	public JButton insert = new JButton("Insert");
	
	public JTextField symbol = new JTextField();
	public JTextField probab = new JTextField();

	Table table;
	
	public LUnderPanel(Table table)
	{
		this.table = table;
		
		delete.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				table.deleteSelectedRows();
			}
			
		});
		
		insert.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				String sContent = symbol.getText();
				String pContent = probab.getText();
				
				BigDecimal number = new BigDecimal(pContent);
				
				if (sContent.length() == 1 && number.compareTo(new BigDecimal(0)) > 0 && number.compareTo(new BigDecimal(1)) < 1)
					table.addProbability(sContent, pContent);
			}
			
		});
		
		setLayout(new GridBagLayout());
		
		GridBagConstraints gbc = new GridBagConstraints();
		gbc.fill = GridBagConstraints.BOTH;
		
		gbc.gridx = 0;
		gbc.gridy = 2;
		gbc.gridwidth = 2;
		gbc.gridheight = 1;
		gbc.weightx = 1;
		gbc.weighty = 1;
		add(delete, gbc);
		
		gbc.gridx = 0;
		gbc.gridy = 1;
		gbc.gridwidth = 2;
		gbc.gridheight = 1;
		gbc.weightx = 1;
		gbc.weighty = 1;
		add(insert, gbc);
		
		gbc.gridx = 0;
		gbc.gridy = 0;
		gbc.gridwidth = 1;
		gbc.gridheight = 1;
		gbc.weightx = 1;
		gbc.weighty = 1;
		add(symbol, gbc);
		
		gbc.gridx = 1;
		gbc.gridy = 0;
		gbc.gridwidth = 1;
		gbc.gridheight = 1;
		gbc.weightx = 1;
		gbc.weighty = 1;
		add(probab, gbc);

	}
	
}
