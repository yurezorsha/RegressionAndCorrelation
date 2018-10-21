import java.util.Vector;

import javax.swing.table.AbstractTableModel;

public class ModelTable extends AbstractTableModel{

	private XY xy;
	

	private Vector<String> columnNames;

	public ModelTable(XY xY) {
		
		xy = xY;
		columnNames = new Vector<String>();
		columnNames.add("X");
		columnNames.add("Yфакт");
		columnNames.add("Yрасч");
	}

	@Override
	public int getColumnCount() {
		return 3;
	}
	
	public XY getXY() {
		return xy;
	}
	
	public void setXY(XY xy) {
		this.xy = xy;
	}
	
	@Override
	public String getColumnName(int column)
    {
          return columnNames.get(column);
    }

	@Override
	public int getRowCount() {
		return xy.getX().size();
	}

	@Override
	public Object getValueAt(int i, int j) {
		double value;
		if(j == 0)
			value = xy.getX().get(i);
		else 
			if (j==1) {
				value = xy.getY().get(i);
			} else {
				value = xy.getY_solve().get(i);
			}
			
		return value;
	}

	@Override
	public void setValueAt(Object aValue, int i, int j){	
		if(j == 0) 
			xy.getX().set(i, (Double) aValue); 
		else
			xy.getY().set(i, (Double) aValue); 
		super.fireTableDataChanged();
		}
	
	@Override
	public boolean isCellEditable(int rowIndex, int columnIndex) {
		if(columnIndex==0 || columnIndex==1) {
            return true;
		} else {
			return false;
		}
	}
}
