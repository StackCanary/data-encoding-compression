package gui;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import javax.swing.BoxLayout;
import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JMenuBar;
import javax.swing.JRadioButton;
import javax.swing.JTextArea;

import coding.adaptive.AdaptiveHuffmanDecoder;
import coding.adaptive.AdaptiveHuffmanEncoder;
import coding.arithmetic.ArithmeticDecoder;
import coding.arithmetic.ArithmeticEncoder;
import coding.huffman.Huffman;
import exceptions.NotDecodableException;
import gui.mid.MPanel;
import gui.table.Table;
import util.Pair;

public class MenuBar extends JMenuBar {
	private static final long serialVersionUID = 1L;
	

	JButton random = new JButton("random");
	JButton encode = new JButton("encode");
	JButton decode = new JButton("decode");

	JTextArea eBox;
	JTextArea dBox;
	JTextArea lBox;
	JTextArea size = new JTextArea("500");
	
	Table table;
	
	static enum CodingType
	{
		Huffman,
		Arithmetic,
		Adaptive
	};
	
	ArrayList<JRadioButton> buttons = new ArrayList<JRadioButton>();
	ButtonGroup buttonGroup = new ButtonGroup();
	
	public MenuBar(MPanel mPanel, Table table)
	{
		setLayout(new BoxLayout(this, BoxLayout.LINE_AXIS));
		
		buttons.add(new JRadioButton("Huffman"));
		buttons.add(new JRadioButton("Arithmetic"));
		buttons.add(new JRadioButton("Adaptive Huffman"));
		buttons.get(0).setSelected(true);
		
		for (JRadioButton button : buttons) {
			buttonGroup.add(button);
			add(button);
		}
		
		this.table = table;
		this.eBox = mPanel.encode;
		this.dBox = mPanel.decode;
		this.lBox = mPanel.lowest;

		add(size);
		add(random);
		add(encode);
		add(decode);


		random.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				
				
				eBox.setText(getShuffledStringFromTable());
				
			}
			
		});

		encode.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent action2) {
				// TODO Auto-generated method stub
				
				List<Character> characters = new ArrayList<Character>();
				
				String[] splat = eBox.getText().split("");
				
				if (splat.length > 0 && !splat[0].equals(""))
				{
					for (String c : splat)
						characters.add(c.toCharArray()[0]);
				}
				
				StringBuilder statistics = new StringBuilder();
				
				switch(getCodingType())
				{
				case Adaptive:
					
					AdaptiveHuffmanEncoder adaptiveEncoder = new AdaptiveHuffmanEncoder();
					adaptiveEncoder.encode(characters);
					dBox.setText(adaptiveEncoder.getEncoded());

					statistics.append(adaptiveEncoder.huffmanState.root.toString());
					statistics.append("\n");
					
					break;
				case Arithmetic:
					
					ArithmeticEncoder arithmetic = new ArithmeticEncoder(table.getPairs());
					arithmetic.encode(characters);
					dBox.setText(arithmetic.getEncoded());
					
					statistics.append("H(k) " + arithmetic.entropy() + " bits\n");
					
					
					break;
				case Huffman:
					Huffman huffman = new Huffman(table.getDOHashTable());
					dBox.setText(huffman.encode(characters));
					
					statistics.append("H(k) " + huffman.entropy() + " bits\n");
					statistics.append("L(k) " + huffman.averageLength() + " \n");
					
					break;
				default:
					break;
				}
				
				if (eBox.getText().length() > 0)
				{
					statistics.append("Source length " + eBox.getText().length() + " symbols \n");
					statistics.append("Output length " + dBox.getText().length() + " bits\n");
					statistics.append("Compression Ratio: " + dBox.getText().length() / ((double) eBox.getText().length() * 8) + "\n");
					statistics.append("Bits per symbol: " + dBox.getText().length() / ((double) eBox.getText().length()) + "\n");
				}
				
				
				lBox.setText(statistics.toString());
			}
		});

		decode.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent action) {
				// TODO Auto-generated method stub
				List<String> tag = new ArrayList<String>();
				
				for (String character : dBox.getText().split(""))
				{
					tag.add(character);
				}
				
				
				switch(getCodingType())
				{
				case Adaptive:
					try {
						AdaptiveHuffmanDecoder adaptiveDecoder = new AdaptiveHuffmanDecoder();
						adaptiveDecoder.decode(tag);
						eBox.setText(adaptiveDecoder.getDecoded());
					} catch (NotDecodableException e1) {
						// TODO Auto-generated catch block
						eBox.setText(e1.getMessage());
					}
					break;
				case Arithmetic:
					ArithmeticDecoder arithmetic = new ArithmeticDecoder(table.getPairs(), tag);
					
					try {
						arithmetic.decode();
						StringBuilder result = new StringBuilder();
						
						for (Character c : arithmetic.getDecoded())
							result.append(c);
							
						eBox.setText(result.toString());
						
					} catch (NotDecodableException e) {
						eBox.setText(e.getMessage());
					}
					
					
					break;
				case Huffman:
					Huffman huffman = new Huffman(table.getDOHashTable());
					
					try {
						eBox.setText(huffman.decode(dBox.getText()));
					} catch (NotDecodableException ex)
					{
						eBox.setText(ex.getMessage());
					}
					
					break;
				default:
					break;
				}

			}
		});

		
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

		if (selected.getText().equals("Huffman"))
			return CodingType.Huffman;
		else if (selected.getText().equals("Arithmetic"))
			return CodingType.Arithmetic;
		else
			return CodingType.Adaptive;
	}
	
	public String getShuffledStringFromTable()
	{
		int tSize = table.model.getRowCount();
		
		if (tSize < 1)
			return "";
		
		StringBuilder sb = new StringBuilder();
		
		Character epsilon = 0;
		
		if (tSize > 0)
			epsilon = ((String)table.model.getValueAt(tSize - 1, 0)).charAt(0);
		
		for (int i = 0; i < tSize; i++)
		{
			String s = (String) table.model.getValueAt(i, 0);
			String n = (String) table.model.getValueAt(i, 1);
			
			Character  symbol = s.charAt(0);
			BigDecimal number = new BigDecimal(n);
			
			for (int j = 0; j < Integer.parseInt(size.getText()) * number.doubleValue(); j++)
				if (!symbol.equals(epsilon))
					sb.append(symbol);
		}
		
		List<Character> list = new ArrayList<Character>();
		for (Character c : sb.toString().toCharArray()) 
		{
			list.add(c);
		}
		
		Collections.shuffle(list);
		
		StringBuilder shuffled = new StringBuilder(); 

		for (Character c : list)
		{
			shuffled.append(c);
		}
		
		shuffled.append(table.model.getValueAt(tSize - 1, 0));

		return shuffled.toString();
	}
	
	
	
}

