package pick.ui;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

import javax.swing.table.AbstractTableModel;

import pick.Picker;

public class DataTableModel extends AbstractTableModel {
	
	private ArrayList<Picker> list = null;
	
	public DataTableModel(ArrayList<Picker> list) {
		MyComparator comparator = new MyComparator();
		Collections.sort(list, comparator);
		this.list = list;
	}
	
	public String getColumnName(int col){
		switch(col) {
			case 0:
				return "";
			case 1:
				return "名字";
			case 2:
				return "击杀数";
			default:
				return "死亡数";
		}
	}

	@Override
	public int getRowCount() {
		return list.size();
	}

	@Override
	public int getColumnCount() {
		return 4;
	}

	@Override
	public Object getValueAt(int rowIndex, int columnIndex) {
		if ((rowIndex + 1) > list.size())
			return null;
		switch(columnIndex) {
			case 0:
				return rowIndex + 1;
			case 1:
				return list.get(rowIndex).getName();
			case 2:
				return list.get(rowIndex).getKill();
			default:
				return list.get(rowIndex).getDie();
		}
	}
	
	public ArrayList<Picker> getList() {
		return list;
	}
	
	public void replaceList(ArrayList<Picker> list) {
		MyComparator comparator = new MyComparator();
		Collections.sort(list, comparator);
		this.list = list;
	}
}

class MyComparator implements Comparator {
	public int compare(Object o1, Object o2) {
		if (o1 == null || o2 == null || !(o1 instanceof Picker) || !(o2 instanceof Picker)) {
			return 0;
		}
		Picker po1 = (Picker)o1;
		Picker po2 = (Picker)o2;
		if (po1.getKill() > po2.getKill()) {
			return -1;
		} else if (po1.getKill() < po2.getKill()) {
			return 1;
		} else {
			if (po1.getDie() < po2.getDie()) {
				return -1;
			} else {
				return 1;
			}
		}
	}
}