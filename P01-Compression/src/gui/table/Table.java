package gui.table;

import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import javax.swing.JTable;
import javax.swing.table.TableColumn;

import util.Pair;

public class Table extends JTable {
	private static final long serialVersionUID = 1L;

	public MyModel model = new MyModel();

	public Table()
	{
		super();
		this.setModel(model);
		
		addProbability("a", "0.3");
		addProbability("b", "0.2");
		addProbability("c", "0.1");
		addProbability("d", "0.05");
		addProbability("e", "0.05");
		addProbability("f", "0.299");
		addProbability("g", "0.001");
		
		this.addFocusListener(new FocusListener() {
			
			@Override
			public void focusLost(FocusEvent e) {
				
			}
			
			@Override
			public void focusGained(FocusEvent e) {
				// TODO Auto-generated method stub
			}
		});

	}
	
	public void deleteSelectedRows()
	{
		int count = this.getSelectedRows().length;

		for (int i = 0; i < count; i++)
			model.removeRow(this.getSelectedRow());

	}

	public void addProbability(String symbol, String probability)
	{
		Object obj[] = new Object[2];
		obj[0] = symbol;
		obj[1] = probability;
		model.addRow(obj);
	}


	public HashMap<Character, BigDecimal> getBDHashTable()
	{
		HashMap<Character, BigDecimal> map = new HashMap<Character, BigDecimal>();

		int tSize = model.getRowCount();

		for (int i = 0; i < tSize; i++)
		{
			String symbol = (String) model.getValueAt(i, 0);
			String number = (String) model.getValueAt(i, 1);

			map.put(symbol.charAt(0), new BigDecimal(number));
		}

		return map;
	}

	public HashMap<Character, Double> getDOHashTable()
	{
		HashMap<Character, Double> map = new HashMap<Character, Double>();

		int tSize = model.getRowCount();

		for (int i = 0; i < tSize; i++)
		{
			String symbol = (String) model.getValueAt(i, 0);
			String number = (String) model.getValueAt(i, 1);

			map.put(symbol.charAt(0), new BigDecimal(number).doubleValue());
		}

		return map;
	}

	public List<Pair<Character, Double>> getPairs()
	{
		List<Pair<Character, Double>> list = new ArrayList<Pair<Character, Double>>();

		int tSize = model.getRowCount();

		for (int i = 0; i < tSize; i++)
		{
			String symbol = (String) model.getValueAt(i, 0);
			String number = (String) model.getValueAt(i, 1);

			list.add(new Pair<Character, Double>(symbol.charAt(0), new BigDecimal(number).doubleValue()));
		}

		return list;
	}


}
