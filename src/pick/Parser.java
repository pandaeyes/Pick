package pick;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;

public class Parser {

	private String fileName = null;
	private HashMap<String, Picker> map = new HashMap<String, Picker> ();
	private String beginTime = null;
	private String endTime = null;

	public Parser(String fileName) {
		this.fileName = fileName;
	}

	public void doParse() {
		InputStreamReader fr = null;
		BufferedReader bufReader = null;
		try {
			Picker picker = null;
			fr = new InputStreamReader(new FileInputStream(fileName), "UTF-8");
			bufReader = new BufferedReader(fr);
			String line;
		    while((line = bufReader.readLine())!=null){
		    	picker = new Picker(line);
		    	if (picker.getName() != null && picker.getName().trim().length() > 0
		    			&& picker.getTarget() != null && picker.getTarget().trim().length() > 0) {
		    		if (beginTime == null) {
		    			beginTime = picker.getTime();
		    		}
		    		endTime = picker.getTime();
		    		Picker p = map.get(picker.getName());
		    		if (p == null) {
		    			picker.setKill(1);
		    			map.put(picker.getName(), picker);
		    		} else {
		    			p.setKill(p.getKill() + 1);
		    		}
		    		p = map.get(picker.getTarget());
		    		if (p == null) {
		    			Picker tp = new Picker();
		    			tp.setName(picker.getTarget());
		    			tp.setDie(1);
		    			map.put(tp.getName(), tp);
		    		} else {
		    			p.setDie(p.getDie() + 1);
		    		}
		    	}
		    }
			fr.close();
			bufReader.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		printResult();
	}
	
	public void printResult() {
		System.out.println("\u3000开始时间:" + beginTime);
		System.out.println("\u3000结束时间:" + endTime);
		System.out.println("\u3000名称  \u3000\u3000\u3000  击杀数\u3000 死亡数");
		ArrayList<Picker> list = new ArrayList<Picker>();
		for(Picker p : map.values()) {
			list.add(p);
		}
		list.sort(new Comparator() {
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
		});
		for(Picker p : list) {
			System.out.print("\u3000");
			System.out.print(printName(p));
			// System.out.println(p.getName() + "\t" + p.getKill() + "\t" + p.getDie());
			System.out.printf("%-8d", p.getKill());
			System.out.printf("%-3d", p.getDie());
			System.out.println();
		}
	}
	
	private String printName(Picker p) {
		String name = p.getName();
		int length = name.length();
		if (length < 7) {
			for(int i = 0; i < (7 - length); i++) {
				name = name + "\u3000";
			}
		}
		return name;
	}
}
