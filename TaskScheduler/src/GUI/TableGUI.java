package GUI;

import java.awt.Color;
import java.awt.Font;
import java.awt.Image;
import java.awt.MenuItem;
import java.awt.PopupMenu;
import java.awt.SystemColor;
import java.awt.SystemTray;
import java.awt.Toolkit;
import java.awt.TrayIcon;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.File;
import java.text.DateFormat;
import java.text.MessageFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.TimerTask;

import javax.print.attribute.HashPrintRequestAttributeSet;
import javax.print.attribute.PrintRequestAttributeSet;
import javax.print.attribute.standard.OrientationRequested;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPopupMenu;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.ListSelectionModel;
import javax.swing.RowFilter;
import javax.swing.SwingConstants;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;
import javax.swing.table.TableRowSorter;

import Logic.LinkedList;
import Logic.MyTimerTask;
import Logic.Reminder;
import Logic.ReminderList;
import Logic.Task;

public class TableGUI extends JFrame {

	private static final long serialVersionUID = 1L;
	private static JPanel contentPane;
	private static JTable table;
	private static DefaultTableModel dtm;
	private static String[] columns = {"Task Description", "Start Time", "Finish Time", "Date", "Completed?"};
	private static Object[][] rows = {};
	private static int selectedRow = -1;
	public static String dataPath = "";
	public static String classPath;
	public static ReminderList remList;
	private TrayIcon icon;
	private JTextField txtQuery;
	private JComboBox comboBox;
	private JButton btnSearch;
	private TableRowSorter sorter;
	
	public static void main(String[] args) {
		classPath = ClassLoader.getSystemClassLoader().getResource(".").getPath();
		dataPath =  classPath + "data.dat";
		TableGUI frame = new TableGUI();
		frame.setVisible(true);
	}
	
	//Constructor
	public TableGUI() {
		//Instantiate the 'ReminderList' object
		remList = new ReminderList();
		//Trigger this event when window is closing
		addWindowListener(new WindowAdapter() {
			public void windowClosing(WindowEvent e) {
				try {
					ReminderList.writeToFile(remList, classPath + "rem.dat");
		        	SystemTray.getSystemTray().add(icon);
		            setVisible(false);
		        } catch (Exception ex) {
		            ex.printStackTrace();
		        }
				setVisible(false);
			}
		});
		loadSystemTrayIcon();
		loadGUI(); 
		loadData();
		loadReminders(); 
	}
	
	private void loadSystemTrayIcon(){
		// System tray icon
		if(!SystemTray.isSupported()){ //If the OS does not support system tray
			return;
		}
		Image img = Toolkit.getDefaultToolkit().getImage(classPath + "icon.png");
		PopupMenu popup = new PopupMenu();
        MenuItem item = new MenuItem("Open Scheduler");
        item.addActionListener(new ActionListener(){
        	public void actionPerformed(ActionEvent e){
        		try {
                	SystemTray.getSystemTray().remove(icon);
                    setVisible(false);
                } catch (Exception ex) {
                    ex.printStackTrace();
                }
        		setVisible(true);
        	}
        });
        popup.add(item);
        item = new MenuItem("Exit");
        item.addActionListener(new ActionListener(){
        	public void actionPerformed(ActionEvent e) {
        		System.exit(0);
			}
        });
        popup.add(item);
		icon = new TrayIcon(img, "Task Scheduler", popup);
		icon.setImageAutoSize(true);
		
		
	}
	private void loadGUI(){
		setResizable(false);
		setTitle("Task Scheduler - by Brian Min");
		setBounds(100, 100, 880, 596);
		setLocationRelativeTo(null); //Position the window at the center of the screen
		contentPane = new JPanel();
		contentPane.setBackground(Color.DARK_GRAY);
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		contentPane.setLayout(null);
		setContentPane(contentPane);
		
		//Set up JTable to display the revision table
		dtm = new DefaultTableModel(rows, columns){
			//Override method
			public boolean isCellEditable(int row, int col){
				return false;
			}			
		};
		table = new JTable(dtm);
		table.setFont(new Font("Tahoma", Font.PLAIN, 12));
		table.setForeground(Color.BLACK);
		table.setBackground(Color.WHITE);
		table.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		table.setRowSelectionAllowed(true);
		
		//Set up the popup menu to display when the table is 'right-clicked'
		final JPopupMenu popup = new JPopupMenu();
		JMenuItem menuEdit = new JMenuItem("Edit");
		JMenuItem menuCompleted = new JMenuItem("Mark as complete");	
		JMenuItem menuRemove = new JMenuItem("Remove");	
		popup.add(menuEdit);
		popup.add(menuCompleted);
		popup.add(menuRemove);
		table.setComponentPopupMenu(popup);
		table.addMouseListener(new MouseAdapter() {
			public void mousePressed(MouseEvent e) {
				int i = table.rowAtPoint(e.getPoint());
				if (i >= 0) {
					table.setRowSelectionInterval(i, i);
				} else {
					table.clearSelection();
				}
			}
		});
		JScrollPane scroll = new JScrollPane(table);
		scroll.setBounds(38, 146, 807, 393);
		menuEdit.addActionListener(new ActionListener() {
			//When the 'Add a task' button is clicked:
			public void actionPerformed(ActionEvent e) {
				editTask();
			}
		});
		menuCompleted.addActionListener(new ActionListener() {
			//When the 'Add a task' button is clicked:
			public void actionPerformed(ActionEvent e) {
				markAsCompleted();
			}
		});
		menuRemove.addActionListener(new ActionListener() {
			//When the 'Add a task' button is clicked:
			public void actionPerformed(ActionEvent e) {
				removeTask();
			}
		});
		contentPane.add(scroll);
		
		
		JButton btnAdd = new JButton("Add a task");
		btnAdd.setFont(new Font("Arial", Font.BOLD, 12));
		btnAdd.addActionListener(new ActionListener() {
			//When the 'Add a task' button is clicked:
			public void actionPerformed(ActionEvent e) {
				TaskGUI task = new TaskGUI();
				task.setVisible(true);
			}
		});
		btnAdd.setBounds(38, 60, 101, 33);
		contentPane.add(btnAdd);
		
		JButton btnRemove = new JButton("Remove");
		btnRemove.setFont(new Font("Arial", Font.BOLD, 12));
		btnRemove.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				removeTask();
			}
		});
		btnRemove.setBounds(141, 60, 101, 33);
		contentPane.add(btnRemove);
		
		JButton btnSave = new JButton("Save Data");
		btnSave.setForeground(Color.WHITE);
		btnSave.setFont(new Font("Arial", Font.BOLD, 12));
		btnSave.setBackground(SystemColor.textHighlight);
		btnSave.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				saveList();
				JOptionPane.showMessageDialog(null, "The data has been saved.", "Success!", JOptionPane.INFORMATION_MESSAGE);
			}
		});
		btnSave.setBounds(733, 60, 112, 26);
		contentPane.add(btnSave);
		
		JButton btnEdit = new JButton("Edit");
		btnEdit.setFont(new Font("Arial", Font.BOLD, 12));
		btnEdit.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				editTask();
			}
		});
		btnEdit.setBounds(244, 60, 101, 33);
		contentPane.add(btnEdit);
		
		JButton btnReload = new JButton("Reload");
		btnReload.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				saveList();
				loadData();
			}
		});
		btnReload.setForeground(Color.WHITE);
		btnReload.setFont(new Font("Arial", Font.BOLD, 12));
		btnReload.setBackground(new Color(50, 205, 50));
		btnReload.setBounds(600, 60, 123, 26);
		contentPane.add(btnReload);
		
		JButton btnPrintTable = new JButton("Print Table");
		btnPrintTable.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				printTable();
			}
		});
		btnPrintTable.setForeground(Color.WHITE);
		btnPrintTable.setFont(new Font("Arial", Font.BOLD, 12));
		btnPrintTable.setBackground(Color.ORANGE);
		btnPrintTable.setBounds(479, 60, 112, 26);
		contentPane.add(btnPrintTable);
		
		btnSearch = new JButton("Search");
		btnSearch.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if(txtQuery.getText().equals("")){
					return;
				}
				if(btnSearch.getText().equals("Search")){ //Search is clicked
					btnSearch.setText("Cancel");
					searchTable();
				}else{ //Cancel is clicked
					btnSearch.setText("Search");
					sorter.setRowFilter(null);
					txtQuery.setText("");
				}
			}
		});
		btnSearch.setForeground(Color.WHITE);
		btnSearch.setFont(new Font("Arial", Font.BOLD, 12));
		btnSearch.setBackground(new Color(255, 0, 0));
		btnSearch.setBounds(722, 103, 123, 32);
		contentPane.add(btnSearch);
		
		txtQuery = new JTextField();
		txtQuery.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				btnSearch.doClick();
			}
		});
		txtQuery.setBounds(228, 104, 484, 31);
		contentPane.add(txtQuery);
		txtQuery.setColumns(10);
		
		comboBox = new JComboBox();
		comboBox.setModel(new DefaultComboBoxModel(new String[] {"Task Description", "Start Time", "Finish Time", "Date", "Completed?"}));
		comboBox.setBounds(38, 104, 180, 31);
		contentPane.add(comboBox);
		
		JLabel lblIbExamRevision = new JLabel("Your Personal Task Schedule");
		lblIbExamRevision.setHorizontalAlignment(SwingConstants.CENTER);
		lblIbExamRevision.setForeground(new Color(173, 255, 47));
		lblIbExamRevision.setFont(new Font("Gill Sans Nova", Font.PLAIN, 34));
		lblIbExamRevision.setBounds(167, 11, 512, 36);
		contentPane.add(lblIbExamRevision);
	}
	
	//This method loads the data onto the program
	private static void loadData(){
		File f = new File(dataPath);
		if(!f.exists()){ //Path does not exist
			return; //Exit method
		}
		clearTable();
		//Read data from text file
		LinkedList tasks = new LinkedList(); //Instantiate a linked list to store tasks
		tasks = LinkedList.readFile(dataPath);
		
		//Sort the tasks in order of date/time (most recent first) by calling the function: sortList
		tasks.sort();
		
		//Loop through the linked list
		for(int i = 0; i < tasks.size(); i++){
			DefaultTableModel model = (DefaultTableModel) table.getModel(); //get the table model
			Task t = tasks.get(i);
			//add a row to the table model
			model.addRow(new Object[]{t.getDescription(), t.getStartTime(), t.getFinishTime(), t.getDate(), t.isCompleted()});
		}
		
	}
	
	private static void loadReminders(){
		// Read reminders from file: rem.dat
		File f = new File(classPath + "rem.dat");
		if (f.exists()) {
			remList = ReminderList.readFile(classPath + "rem.dat");
			//Traverse through linked list of reminders
			for(int i = 0; i < remList.size(); i++){
				Reminder r = remList.get(i);
				createReminder(r.getTimerTask().getTask(), r.getTimerTask().getN(), false);
				remList.remove(i); //Remove from list
			}
		}
	}
	
	//This method retrieves the task from the selected row and transfers it to the 'EditGUI' form. 
	public void editTask(){
		if(table.getRowCount() == 0 || table.getSelectedRow() == -1) return; //Validation check: exit the method if there are no rows selected
		selectedRow = table.getSelectedRow(); //get the selected row number
		DefaultTableModel model = (DefaultTableModel) table.getModel(); //get the table model
		//retrieve the individual components of the selected row, storing it as strings
		String description = (String)model.getValueAt(selectedRow, 0);
		String startTime = (String)model.getValueAt(selectedRow, 1);
		String endTime = (String)model.getValueAt(selectedRow, 2);
		String date = (String)model.getValueAt(selectedRow, 3);
		String completed = (String)model.getValueAt(selectedRow, 4);
		//Instantiate a new task object using the stored components as arguments
		Task t = new Task(description, startTime, endTime, date, completed);
		//Create a EditGUI form and display it
		EditGUI editForm = new EditGUI();
		editForm.setVisible(true);
		//Load the task onto the 'EditGUI' form
		editForm.loadTask(t);
	}
	
	//This method retrieves the task from the selected row and sets 'Completed' to true. 
	public void markAsCompleted(){
		if(table.getRowCount() == 0) return; //Validation check: exit the method if there are no rows selected
		DefaultTableModel model = (DefaultTableModel) table.getModel(); //get the table model
		model.setValueAt("Y", table.getSelectedRow(), 4);
	}
	
	//This method removes the selected row (a task) from the revision table
	public void removeTask(){
		if(table.getRowCount() == 0 || table.getSelectedRow() == -1) return; //Validation check: exit the method if there are no rows selected
		int n = table.getSelectedRow(); //get the selected row number
		DefaultTableModel model = (DefaultTableModel) table.getModel(); //get the table model
		model.removeRow(n); //remove the row from the table model
		if(table.getRowCount() >= 1 && n>=1){
			table.setRowSelectionInterval(n-1, n-1);
		}
	}
	
	//This method retrieves the data from the table and writes it to a file
	private static void saveList(){
		LinkedList tasks = new LinkedList();
		DefaultTableModel model = (DefaultTableModel) table.getModel(); //get the table model
		
		for(int i = 0; i < model.getRowCount(); i++){
			String description = (String)model.getValueAt(i, 0);
			String startTime = (String)model.getValueAt(i, 1);
			String endTime = (String)model.getValueAt(i, 2);
			String date = (String)model.getValueAt(i, 3);
			String completed = (String)model.getValueAt(i, 4);
			Task temp = new Task(description, startTime, endTime, date, completed);
			tasks.add(temp);
		}
		//write tasks to file: data.dat
		LinkedList.writeToFile(tasks, dataPath);
		//write reminders to file: rem.dat
		ReminderList.writeToFile(remList, classPath + "rem.dat");
	}
	
	//This method adds a task to the revision table
	public static void addTask(Task t, int n){
		DefaultTableModel model = (DefaultTableModel) table.getModel(); //get the table model
		//add a row to the table model
		model.addRow(new Object[]{t.getDescription(), t.getStartTime(), t.getFinishTime(), t.getDate(), t.isCompleted()});
		table.setRowSelectionInterval(model.getRowCount() - 1, model.getRowCount() - 1);
		
		//create a reminder
		if(n != 0){
			createReminder(t, n, true);			
		}
	}
	
	//This method updates the values at the specified row
	public static void updateTask(Task t){
		DefaultTableModel model = (DefaultTableModel) table.getModel(); //get the table model
		model.setValueAt(t.getDescription(), selectedRow, 0);
		model.setValueAt(t.getStartTime(), selectedRow, 1);
		model.setValueAt(t.getFinishTime(), selectedRow, 2);
		model.setValueAt(t.getDate(), selectedRow, 3);
		table.setRowSelectionInterval(selectedRow, selectedRow);
	}
	
	//This method clears the table
	private static void clearTable(){
		DefaultTableModel model = (DefaultTableModel)table.getModel();
		model.setRowCount(0);
	}
	
	//This method sends the JTable to the printer
	private static void printTable(){
		MessageFormat header = new MessageFormat("My Personal Task Schedule");
		PrintRequestAttributeSet set = new HashPrintRequestAttributeSet();
		set.add(OrientationRequested.LANDSCAPE);
		try {
			table.print(JTable.PrintMode.FIT_WIDTH, header, null, true, set, true);
		} catch (java.awt.print.PrinterException e) {
		    System.err.print(e.getMessage());
		}
	}
	
	// This method is called when the 'Search' button is clicked - it searches
	// for the query string in the table at a certain column.
	private void searchTable() {
		// instantiate a table model (used to make changes to the JTable)
		DefaultTableModel model = (DefaultTableModel) table.getModel();
		// retrieve the search string(query) from the text field
		String query = txtQuery.getText();
		// retrieve the index of the selected combobox
		int column = comboBox.getSelectedIndex();

		// Initialize and set TableSorter
		sorter = new TableRowSorter<TableModel>(model);
		table.setRowSorter(sorter);

		// if the search string is null
		if (query.length() == 0) {
			sorter.setRowFilter(null); // remove the row filter
		} else {
			// filter the search string in a specific column
			RowFilter rf = RowFilter.regexFilter("(?i)" + query, column);
			sorter.setRowFilter(rf);
		}

	}

	private static void createReminder(Task t, int n, boolean addToList){
		DateFormat format = new SimpleDateFormat("dd/MM/yy HH:mm");
		int offset = 0;;
		if(n == 1){ //Remind now
			offset = 0;
		}
		else if(n == 2){ //Remind 5 minutes before
			offset = 5;
		}
		else if(n == 3){ //Remind 30 minutes before
			offset = 30;
		}
		int hours = Integer.parseInt(t.getStartTime().substring(0, 2));
		int minutes = Integer.parseInt(t.getStartTime().substring(3));
		int remainder = minutes - offset;
		if(remainder < 0){
			hours--;
			minutes = 60 + remainder;
		}else{
			minutes = remainder;
		}
		String time = hours + ":" + minutes;
		Date date = new Date();
		try {
			date = format.parse(t.getDate() + " " + time);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		
		Reminder rem = new Reminder();
		TimerTask timerTask = new MyTimerTask(t, n, rem);
		rem.setReminder(timerTask, date);
		if(addToList){
			remList.add(rem);			
		}
	}
}

