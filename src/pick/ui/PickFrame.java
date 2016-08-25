package pick.ui;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.ListSelectionModel;
import javax.swing.UIManager;

import pick.PickManager;
import pick.Picker;
import pick.PickerNum;

public class PickFrame extends JFrame {

	private String title = "统计";

	private Dimension dimension = new Dimension(700, 500);
	private JButton searchBut = new JButton("查询");
	private JTextField beginTxt = new JTextField();
	private JTextField endTxt = new JTextField();
	private JComboBox typelist = new JComboBox();
	
	private String halfhour = "最近半小时";
	private String onehour = "最近一小时";
	private String twohour = "最近两小时";
	private String sixhour = "最近六小时";
	private String onedate = "最近一天";
	private String hour1 = "整1点";
	private String hour2 = "整2点";
	private String hour3 = "整3点";
	private String hour4 = "整4点";
	private String hour5 = "整5点";
	private String hour6 = "整6点";
	private String day1 = "整1天";
	private SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd H:m:s");
	
	
	private JTable table = null;
	private DataTableModel tableModel = null;
	private JTextArea killArea = null;
	private JTextArea dieArea = null;

	public static void main(String [] args) {
		try {
			UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
		} catch (Exception e) {
			e.printStackTrace();
		}
		PickManager.getInstance().init();
		new PickFrame();
	}
	
	public PickFrame() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		JPanel mainPane = new JPanel();
		getContentPane().setLayout(new BorderLayout());
		getContentPane().add(mainPane, BorderLayout.CENTER);
		mainPane.setLayout(new BorderLayout());
		JPanel upPane = getNorthPane();
		JPanel tablePane = getTablePane();
		mainPane.add(upPane, BorderLayout.NORTH);
		mainPane.add(tablePane, BorderLayout.CENTER);
		setSize(dimension);
		setTitle(title);
		GUIUtils.centerWindow(this);
		setVisible(true);
	}
	
	private JPanel getNorthPane() {
		JPanel mpane = new JPanel();
		mpane.setLayout(new BorderLayout());
		mpane.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));
		JPanel pane = new JPanel();
		pane.setLayout(new BoxLayout(pane, BoxLayout.X_AXIS));
		pane.add(new JLabel("开始时间:"));
		beginTxt.setColumns(20);
		pane.add(beginTxt);
		pane.add(Box.createHorizontalStrut(10));
		pane.add(new JLabel("结束时间:"));
		pane.add(endTxt);
		endTxt.setColumns(20);
		typelist.addItem(hour1);
		typelist.addItem(hour2);
		typelist.addItem(hour3);
		typelist.addItem(hour4);
		typelist.addItem(hour5);
		typelist.addItem(hour6);
		typelist.addItem(day1);
		typelist.addItem(halfhour);
		typelist.addItem(onehour);
		typelist.addItem(twohour);
		typelist.addItem(sixhour);
		typelist.addItem(onedate);
		typelist.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				Object select = typelist.getSelectedItem();
				int minute = 30;
				if (halfhour.equals(select)) {
					minute = 30;
				} else if (onehour.equals(select)) {
					minute = 60;
				} else if (twohour.equals(select)) {
					minute = 120;
				} else if (sixhour.equals(select)) {
					minute = 360;
				} else if (onedate.equals(select)) {
					minute = 1440;
				} else if (hour1.equals(select)) {
					Calendar c1 = Calendar.getInstance();
					c1.setTime(new Date());
					minute = c1.get(Calendar.MINUTE);
				} else if (hour2.equals(select)) {
					Calendar c1 = Calendar.getInstance();
					c1.setTime(new Date());
					minute = c1.get(Calendar.MINUTE) + 60;
				} else if (hour3.equals(select)) {
					Calendar c1 = Calendar.getInstance();
					c1.setTime(new Date());
					minute = c1.get(Calendar.MINUTE) + 120;
				} else if (hour4.equals(select)) {
					Calendar c1 = Calendar.getInstance();
					c1.setTime(new Date());
					minute = c1.get(Calendar.MINUTE) + 180;
				} else if (hour5.equals(select)) {
					Calendar c1 = Calendar.getInstance();
					c1.setTime(new Date());
					minute = c1.get(Calendar.MINUTE) + 240;
				} else if (hour6.equals(select)) {
					Calendar c1 = Calendar.getInstance();
					c1.setTime(new Date());
					minute = c1.get(Calendar.MINUTE) + 300;
				} else if (day1.equals(select)) {
					Calendar c1 = Calendar.getInstance();
					c1.setTime(new Date());
					int m = c1.get(Calendar.MINUTE);
					int h = c1.get(Calendar.HOUR_OF_DAY);
					minute = c1.get(Calendar.MINUTE) + h * 60;
				}
				setBeginTime(minute);
			}
		});
		pane.add(Box.createHorizontalStrut(10));
		pane.add(typelist);
		pane.add(Box.createHorizontalStrut(10));
		pane.add(searchBut);
		searchBut.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent ae) {
				doSearch();
			}
		});
		
		mpane.add(pane, BorderLayout.WEST);
		return mpane;
	}
	
	public void doSearch() {
		if (beginTxt.getText().trim().length() == 0) {
			JOptionPane.showMessageDialog(this, "请填写开始时间", "提示信息",1);
			return;
		}
		String btime = beginTxt.getText().trim();
		Date bDate = null;
		try {  
		    bDate = format.parse(btime);  
		} catch (ParseException e) {  
			JOptionPane.showMessageDialog(this, "开始时间格式有误", "提示信息",1);
			return;
		} 
		String etime = endTxt.getText().trim();
		Date eDate = null;
		if (etime.length() > 0) {
			try {  
				eDate = format.parse(etime);  
			} catch (ParseException e) {  
				JOptionPane.showMessageDialog(this, "结束时间格式有误", "提示信息",1);
				return;
			} 
		}
		ArrayList<Picker> list = PickManager.getInstance().getPickerList(bDate, eDate);
		tableModel.replaceList(list);
		tableModel.fireTableDataChanged();
	}
	
	private void setBeginTime(int minute) {
		Calendar c1 = Calendar.getInstance();
        c1.setTime(new Date());
        c1.add(Calendar.MINUTE, (0 - minute));
        c1.add(Calendar.SECOND, (0 - c1.get(Calendar.SECOND)));
        beginTxt.setText(format.format(c1.getTime()));
        endTxt.setText("");
	}

	private JPanel getTablePane() {
		JPanel pane = new JPanel();
		pane.setLayout(new BorderLayout());
		tableModel = new DataTableModel(new ArrayList<Picker>());
		table = new JTable(tableModel);
		table.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		table.getTableHeader().setReorderingAllowed(false);
		// table.setPreferredScrollableViewportSize(new Dimension(50, 150));
		table.getColumnModel().getColumn(0).setMaxWidth(22);
		table.getColumnModel().getColumn(2).setMaxWidth(100);
		table.getColumnModel().getColumn(2).setMinWidth(100);
		table.getColumnModel().getColumn(3).setMaxWidth(100);
		table.getColumnModel().getColumn(3).setMinWidth(100);
		table.addMouseListener(new MouseAdapter() {
			public void mouseClicked(MouseEvent e){
				int row =((JTable)e.getSource()).getSelectedRow();
				Picker picker = ((DataTableModel)table.getModel()).getList().get(row);
				refreshArea(picker);
			}
		});
		JScrollPane scrollPane = new JScrollPane(table);
		pane.add(scrollPane, BorderLayout.CENTER);
		
		JPanel boxPane = new JPanel();
		boxPane.setLayout(new BoxLayout(boxPane, BoxLayout.X_AXIS));
		JPanel killPane = new JPanel();
		killPane.setLayout(new BorderLayout());
		killArea = new JTextArea();
		killArea.setEditable(false);
		killArea.setColumns(20);
		killPane.add(new JScrollPane(killArea), BorderLayout.CENTER);
		killPane.setBorder(BorderFactory.createTitledBorder("击杀列表"));
		boxPane.add(killPane);
		JPanel diePane = new JPanel();
		diePane.setLayout(new BorderLayout());
		dieArea = new JTextArea();
		dieArea.setEditable(false);
		dieArea.setColumns(20);
		diePane.add(new JScrollPane(dieArea), BorderLayout.CENTER);
		diePane.setBorder(BorderFactory.createTitledBorder("凶手列表"));
		boxPane.add(diePane);
		pane.add(BorderLayout.EAST, boxPane);
		return pane;
	}
	
	private void refreshArea(Picker picker) {
		setAreaValue(picker.getKillList(), killArea);
		setAreaValue(picker.getMurderList(), dieArea);
	}
	
	private void setAreaValue(ArrayList<PickerNum> list, JTextArea area) {
		area.setText("");
		StringBuffer sb = new StringBuffer();
		for(PickerNum pn : list) {
			sb.append(pn.getName() + "(" + pn.getNum() + ")\n");
		}
		area.setText(sb.toString());
	}
}
