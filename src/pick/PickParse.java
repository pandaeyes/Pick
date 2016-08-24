package pick;

public class PickParse {
	
	public static void main(String [] args) {
		Parser pp = new Parser("list.txt");
		pp.doParse();
	}
}
