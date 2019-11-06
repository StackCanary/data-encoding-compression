package gui.right;

import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JTextArea;

import coding.arithmetic.ArithmeticDecoder;
import coding.arithmetic.ArithmeticEncoder;
import coding.huffman.Huffman;
import exceptions.NotDecodableException;
import gui.JDummy;
import gui.right.RPanelTop.CodingType;
import gui.table.Table;

public class RPanelLow extends JPanel{
	private static final long serialVersionUID = 1L;

	JButton random = new JButton("random");
	JButton encode = new JButton("encode");
	JButton decode = new JButton("decode");

	RPanelTop rPanelTop;
	
	JTextArea eBox;
	JTextArea dBox;
	Table table;
	
	public RPanelLow(JTextArea eBox, JTextArea dBox, RPanelTop rPanelTop, Table table)
	{
		this.table = table;
		this.eBox = eBox;
		this.dBox = dBox;
		this.rPanelTop = rPanelTop;
		
		setLayout(new GridLayout(3, 0));

		for (int i = 0; i < 0 ; i++)
			add(new JDummy());

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
				
				switch(rPanelTop.getCodingType())
				{
				case Adaptive:
					break;
				case Arithmetic:
					
					ArithmeticEncoder arithmetic = new ArithmeticEncoder(table.getPairs());
					
					List<Character> chars = new ArrayList<Character>();
					

					String[] splot = eBox.getText().split("");

					if (splot.length >= 1)
					{
						for (String c : splot)
							chars.add(c.toCharArray()[0]);
					}
					
					arithmetic.encode(chars);
					
					dBox.setText(arithmetic.getEncoded());
					
					
					
					break;
				case Huffman:
					Huffman huffman = new Huffman(table.getDOHashTable());
					
					List<Character> characters = new ArrayList<Character>();
					
					String[] splat = eBox.getText().split("");
					
					if (splat.length >= 1)
					{
						for (String c : splat)
							characters.add(c.toCharArray()[0]);
					}
					
					dBox.setText(huffman.encode(characters));
					
					break;
				default:
					break;
				}
				
			}
		});

		decode.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent action) {
				// TODO Auto-generated method stub
				
				switch(rPanelTop.getCodingType())
				{
				case Adaptive:
					break;
				case Arithmetic:
				
					List<String> tag = new ArrayList<String>();
					
					for (String character : dBox.getText().split(""))
					{
						tag.add(character);
					}
					
					ArithmeticDecoder arithmetic = new ArithmeticDecoder(table.getPairs(), tag);
					
					try {
						arithmetic.decode();
						StringBuilder result = new StringBuilder();
						
						for (Character c : arithmetic.getDecoded())
							result.append(c);
							
						eBox.setText(result.toString());
					} catch (NotDecodableException e) {
						e.printStackTrace();
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
	
	public String getShuffledStringFromTable()
	{
		int tSize = table.model.getRowCount();
		
		final int size = 1000;
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
			
			for (int j = 0; j < size * number.doubleValue(); j++)
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
