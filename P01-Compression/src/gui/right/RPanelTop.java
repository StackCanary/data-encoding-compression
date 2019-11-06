package gui.right;

import java.awt.Color;
import java.awt.GridLayout;
import java.util.ArrayList;

import javax.swing.BorderFactory;
import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JRadioButton;

import gui.JDummy;

public class RPanelTop extends JPanel {
	private static final long serialVersionUID = 1L;
	
	ArrayList<JRadioButton> buttons = new ArrayList<JRadioButton>();
	ButtonGroup buttonGroup = new ButtonGroup();
	
	static enum CodingType
	{
		Huffman,
		Arithmetic,
		Adaptive
	};
	

	public RPanelTop()
	{
		setLayout(new GridLayout(5, 0));
		
		buttons.add(new JRadioButton("Huffman Coding"));
		buttons.add(new JRadioButton("Arithmetic Coding"));
		buttons.add(new JRadioButton("Adaptive Huffman Coding"));
		
		buttons.get(0).setSelected(true);
		
		for (JRadioButton button : buttons) {
			buttonGroup.add(button);
			add(button);
		}
		
		for (int i = 0; i < 2; i++)
			add(new JDummy());
		
	}
	
	public JRadioButton getSelected()
	{
		
		for (JRadioButton button : buttons)
		{
			if (button.isSelected())
				return button;
		}
		
		return buttons.get(0);
		
	}
	
	public CodingType getCodingType()
	{
		JRadioButton selected = getSelected();

		if (selected.getText().equals("Huffman Coding"))
			return CodingType.Huffman;
		else if (selected.getText().equals("Arithmetic Coding"))
			return CodingType.Arithmetic;
		else
			return CodingType.Adaptive;
	}
	
	

}
