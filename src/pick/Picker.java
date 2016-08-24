package pick;

import java.util.regex.Matcher;
import java.util.regex.Pattern;


public class Picker {
	
	private String regEx = "玩家:(.+) 被玩家:(.+) 杀死 地图:(.+) 时间是: (.+)";
	
	private String txt;
	
	private String name;
	private int kill;
	private int die;
	
	private String target;
	private String time;
	
	public String getName() {
		return name;
	}
	public int getKill() {
		return kill;
	}
	public int getDie() {
		return die;
	}
	public String getTarget() {
		return target;
	}
	public String getTime() {
		return time;
	}
	public void setKill(int kill) {
		this.kill = kill;
	}
	public void setDie(int die) {
		this.die = die;
	}
	public void setName(String name) {
		this.name = name;
	}

	public Picker() {
	}

	public Picker(String txt) {
		this.txt = txt;
		Read();
	}
	
	private void Read() {
		Pattern p = Pattern.compile(regEx);
		Matcher m = p.matcher(txt);
		if (m.find()) {
			if (m.group(1) != null) {
				target = m.group(1);
			}
			if (m.group(2) != null) {
				name = m.group(2);
			}
			if (m.group(4) != null) {
				time = m.group(4);
			}
		}
	}
	
	public boolean equals(Object other){
		if (other == null) {
			return false;
		}

		if (other instanceof Picker) {
			return ((Picker)other).name.equals(name);
		} else {
			return false;
		}
	}
	
	public int hashCode(){ 
		if (name != null) {
			return name.hashCode();
		} else {
			return 0;
		}
	}
}
