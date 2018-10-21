import java.util.ArrayList;

import javax.swing.table.AbstractTableModel;

public class ModelTableResult extends AbstractTableModel{


	private ArrayList<String> name;
	private ArrayList<String> value;

	public ModelTableResult() {
		this.name = new ArrayList<String>();
		this.value = new ArrayList<String>();
	}
	
	public ModelTableResult(ArrayList<String> names, ArrayList<String> values) {
		this.name = new ArrayList<String>();
		this.value = new ArrayList<String>();		
		name = names;
		value = values;
	}

	public ArrayList<String> getName() {
		
		return name;
	}

	public void setName(ArrayList<String> name) {
		this.name = name;
	}

	public ArrayList<String> getValue() {
		return value;
	}

	public void setValue(ArrayList<String> value) {
		this.value = value;
	}

	@Override
	public int getColumnCount() {
		return 2;
	}



	@Override
	public int getRowCount() {
		return name.size();
	}


	@Override
	public Object getValueAt(int i, int j) {
		String val;
		if(j == 0)
			val = name.get(i);
		else
			val = value.get(i);
		return val;
		
	}
	
	public ArrayList<String> setYnull(ArrayList<String> value) {
		
		for(int i = 0; i < value.size(); i++) 
			value.set(i, "");	
		
		return value;
	}
}
