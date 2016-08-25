package pick;

import java.io.BufferedInputStream;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Date;
import java.util.Properties;

public class PickManager {
	
	private String srcPath = null;
	
	private static PickManager mgr = null;
	private PickManager() {
	}
	
	public static PickManager getInstance() {
		if (mgr == null) {
			mgr = new PickManager();
		}
		return mgr;
	}
	
	public String GetSrcPath() {
		return srcPath;
	}

	public void init() {
		try {
			InputStream is = new BufferedInputStream(new FileInputStream("path.properties"));
			Properties p = new Properties();
			p.load(is);
			srcPath = p.getProperty("path");
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();

		}
	}
	
	public ArrayList<Picker> getPickerList(Date beginTime, Date endTime) {
		Parser pp = new Parser(srcPath, beginTime, endTime);
		pp.doParse();
		return pp.getResult();
	}
}
