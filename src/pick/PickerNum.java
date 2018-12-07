package pick;

import java.util.Comparator;


public class PickerNum {
	private String name = null;
	private int num = 0;

	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public int getNum() {
		return num;
	}
	public void setNum(int num) {
		this.num = num;
	}
}

class PnComparator implements Comparator {
	public int compare(Object o1, Object o2) {
		if (o1 == null || o2 == null || !(o1 instanceof PickerNum) || !(o2 instanceof PickerNum)) {
			return 0;
		}
		PickerNum po1 = (PickerNum)o1;
		PickerNum po2 = (PickerNum)o2;
		if (po1.getNum() > po2.getNum()) {
			return -1;
		} else if (po1.getNum() < po2.getNum()) {
			return 1;
		} else {
			return 0;
		}
	}
}