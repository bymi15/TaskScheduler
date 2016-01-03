package GUI;

import java.awt.Color;
import java.awt.Font;
import java.awt.SystemColor;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Calendar;
import java.util.GregorianCalendar;
import javax.swing.ButtonGroup;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.border.EmptyBorder;

import Logic.Task;
import javax.swing.JTextArea;

public class TaskGUI extends JFrame {

	private JPanel contentPane;
	private JTextField txtStartTime;
	private JTextField txtFinishTime;
	private JTextField txtDate;
	private JTextArea txtDescription;
	private JRadioButton rdbtn1;
	private JRadioButton rdbtn2;
	private JRadioButton rdbtn3;
	private JRadioButton rdbtn4;
	private JComboBox comboRepeat;

	public TaskGUI() {
		setResizable(false);
		loadGUI();
		clearForm();
		loadDateAndTime();
	}
	
	private void loadGUI(){
		setTitle("Add a Task");
		setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
		setBounds(100, 100, 526, 336);
		setLocationRelativeTo(null);
		contentPane = new JPanel();
		contentPane.setBackground(Color.DARK_GRAY);
		contentPane.setForeground(Color.DARK_GRAY);
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JLabel lblDescription = new JLabel("Description:");
		lblDescription.setHorizontalAlignment(SwingConstants.TRAILING);
		lblDescription.setForeground(Color.WHITE);
		lblDescription.setBounds(10, 44, 95, 14);
		contentPane.add(lblDescription);
		
		JLabel lblStartTime = new JLabel("Start Time:");
		lblStartTime.setHorizontalAlignment(SwingConstants.TRAILING);
		lblStartTime.setForeground(Color.WHITE);
		lblStartTime.setBounds(0, 142, 105, 14);
		contentPane.add(lblStartTime);
		
		JLabel lblDuration = new JLabel("Finish Time:");
		lblDuration.setHorizontalAlignment(SwingConstants.TRAILING);
		lblDuration.setForeground(Color.WHITE);
		lblDuration.setBounds(0, 167, 105, 14);
		contentPane.add(lblDuration);
		
		JLabel lblDate = new JLabel("Date:");
		lblDate.setHorizontalAlignment(SwingConstants.TRAILING);
		lblDate.setForeground(Color.WHITE);
		lblDate.setBounds(0, 192, 105, 14);
		contentPane.add(lblDate);
		
		txtStartTime = new JTextField();
		txtStartTime.setBounds(115, 139, 371, 20);
		contentPane.add(txtStartTime);
		txtStartTime.setColumns(10);
		
		txtFinishTime = new JTextField();
		txtFinishTime.setBounds(115, 164, 371, 20);
		contentPane.add(txtFinishTime);
		txtFinishTime.setColumns(10);
		
		txtDate = new JTextField();
		txtDate.setBounds(115, 189, 371, 20);
		contentPane.add(txtDate);
		txtDate.setColumns(10);
		
		JButton btnAdd = new JButton("Add");
		btnAdd.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				addTask();
			}
		});
		btnAdd.setBounds(26, 271, 88, 23);
		contentPane.add(btnAdd);
		
		JButton btnClear = new JButton("Clear");
		btnClear.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				clearForm();
			}
		});
		btnClear.setBounds(212, 271, 88, 23);
		contentPane.add(btnClear);
		
		JButton btnCancel = new JButton("Cancel");
		btnCancel.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				setVisible(false);
			}
		});
		btnCancel.setBounds(398, 271, 88, 23);
		contentPane.add(btnCancel);
		
		JPanel panel = new JPanel();
		panel.setBackground(SystemColor.textInactiveText);
		panel.setBounds(0, 0, 520, 31);
		contentPane.add(panel);
		
		JLabel lblTitle = new JLabel("Add a Task");
		panel.add(lblTitle);
		lblTitle.setForeground(Color.WHITE);
		lblTitle.setFont(new Font("Century Gothic", Font.BOLD, 16));
		lblTitle.setHorizontalAlignment(SwingConstants.CENTER);
		
		JLabel lblRemindMe = new JLabel("Remind me:");
		lblRemindMe.setHorizontalAlignment(SwingConstants.TRAILING);
		lblRemindMe.setForeground(Color.WHITE);
		lblRemindMe.setBounds(0, 246, 76, 14);
		contentPane.add(lblRemindMe);
		
		txtDescription = new JTextArea();
		txtDescription.setWrapStyleWord(true);
		txtDescription.setLineWrap(true);
		txtDescription.setBounds(115, 40, 371, 93);
		contentPane.add(txtDescription);
		
		rdbtn1 = new JRadioButton("At the time");
		rdbtn1.setSelected(true);
		rdbtn1.setBackground(Color.DARK_GRAY);
		rdbtn1.setForeground(Color.WHITE);
		rdbtn1.setBounds(78, 242, 88, 23);
		contentPane.add(rdbtn1);
		
		rdbtn2 = new JRadioButton("5 minutes before");
		rdbtn2.setBackground(Color.DARK_GRAY);
		rdbtn2.setForeground(Color.WHITE);
		rdbtn2.setBounds(168, 242, 122, 23);
		contentPane.add(rdbtn2);
		
		rdbtn3 = new JRadioButton("30 minutes before");
		rdbtn3.setBackground(Color.DARK_GRAY);
		rdbtn3.setForeground(Color.WHITE);
		rdbtn3.setBounds(285, 242, 130, 23);
		contentPane.add(rdbtn3);
		
		rdbtn4 = new JRadioButton("Never");
		rdbtn4.setBackground(Color.DARK_GRAY);
		rdbtn4.setForeground(Color.WHITE);
		rdbtn4.setBounds(417, 242, 68, 23);
		contentPane.add(rdbtn4);
		
		ButtonGroup group = new ButtonGroup();
		group.add(rdbtn1);
		group.add(rdbtn2);
		group.add(rdbtn3);
		group.add(rdbtn4);
		
		JLabel lblRepeat = new JLabel("Repeat:");
		lblRepeat.setHorizontalAlignment(SwingConstants.TRAILING);
		lblRepeat.setForeground(Color.WHITE);
		lblRepeat.setBounds(0, 221, 105, 14);
		contentPane.add(lblRepeat);
		
		comboRepeat = new JComboBox();
		comboRepeat.setModel(new DefaultComboBoxModel(new String[] {"1", "2", "3", "4", "5", "6", "7", "8", "9", "10"}));
		comboRepeat.setSelectedIndex(0);
		comboRepeat.setBounds(115, 220, 37, 20);
		contentPane.add(comboRepeat);
		
		JLabel lblWeeks = new JLabel("week(s)");
		lblWeeks.setForeground(Color.WHITE);
		lblWeeks.setBounds(156, 220, 68, 20);
		contentPane.add(lblWeeks);
		
	}
	
	private void clearForm(){
		txtDescription.setText("");
		txtStartTime.setText("");
		txtFinishTime.setText("");
		txtDate.setText("");
	}
	
	private void addTask(){
		String desc = txtDescription.getText().trim();
		String startTime = txtStartTime.getText().trim();
		String finishTime = txtFinishTime.getText().trim();
		String date = txtDate.getText().trim();
		int repeat = comboRepeat.getSelectedIndex() + 1;
		
		//validation checks
		
		//presence check
		if(desc.equals("") || startTime.equals("") || finishTime.equals("") || date.equals("")){
			JOptionPane.showMessageDialog(null, "Please make sure that the description, start time, "
					+ "finish time and date have been correctly entered.", "Error", 0);
			return;
		}
		
		//format time from string HH:mm to integer HHmm
		int startTimeVal = Integer.parseInt(startTime.replace(":", ""));
		int finishTimeVal = Integer.parseInt(finishTime.replace(":", ""));
		if(finishTimeVal < startTimeVal){
			JOptionPane.showMessageDialog(null, "Please make sure to enter a valid start"
					+ "and finish time.", "Error", 0);
			return;
		}
		
			//n = 1: at the time, n = 2: 5 minutes before, n = 3: 30 minutes before, n = 0: never
			int n = 0;
			if(rdbtn1.isSelected()) n = 1;
			if(rdbtn2.isSelected()) n = 2;
			if(rdbtn3.isSelected()) n = 3;
			
			//Retrieve day, month, year from a string 'date'
			int day = Integer.parseInt(date.substring(0,2));
			int month = Integer.parseInt(date.substring(3, 5));
			int year = Integer.parseInt(date.substring(6));
			//Instantiate a calendar object to handle date processing				
			//(Month - 1) since the Gregorian Calender starts at 0 = January, 1 = February, etc
			Calendar cal = new GregorianCalendar(year, month-1, day);
			SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
			date = formatter.format(cal.getTime());
			Task t = new Task(desc, startTime, finishTime, date);
			TableGUI.addTask(t, n);
				
				for(int i = 1; i < repeat; i++){
					//Add one week to the calendar
					cal.add(Calendar.WEEK_OF_YEAR, 1);
					// Convert date to String
					date = formatter.format(cal.getTime());
					//Add a task
					t = new Task(desc, startTime, finishTime, date);
					TableGUI.addTask(t, n);
				}
				this.setVisible(false);	
			
	}
	
	private void loadDateAndTime(){
		Date dateObj = new Date();
		String date = new SimpleDateFormat("dd/MM/yyyy").format(dateObj);
		String time = new SimpleDateFormat("HH:mm").format(dateObj);
		txtDate.setText(date);
		txtStartTime.setText(time);
	}
}
