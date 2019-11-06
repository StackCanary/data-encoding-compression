package gui.table;

import java.math.BigDecimal;

import javax.swing.table.DefaultTableModel;

public class MyModel extends DefaultTableModel {
	private static final long serialVersionUID = 1L;

	public MyModel()
	{
		addColumn("Symbol");
		addColumn("Probability");
	}
	
	@Override
	public void insertRow(int row, Object[] rowData) {
		super.insertRow(row, rowData);
	}
	
}
