package GUI;

import java.awt.Color;
import java.awt.Font;
import java.awt.SystemColor;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.border.EmptyBorder;

import Logic.Task;
import javax.swing.JTextArea;

public class EditGUI extends JFrame {

	private JPanel contentPane;
	private JTextField txtStartTime;
	private JTextField txtFinishTime;
	private JTextField txtDate;
	private JTextArea txtDescription;

	public EditGUI() {
		setResizable(false);
		loadGUI();
		clearForm();
	}
	
	private void loadGUI(){
		setTitle("Edit a Task");
		setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
		setBounds(100, 100, 526, 309);
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
		lblDescription.setBounds(0, 44, 105, 14);
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
		txtStartTime.setBounds(115, 139, 370, 20);
		contentPane.add(txtStartTime);
		txtStartTime.setColumns(10);
		
		txtFinishTime = new JTextField();
		txtFinishTime.setBounds(115, 164, 370, 20);
		contentPane.add(txtFinishTime);
		txtFinishTime.setColumns(10);
		
		txtDate = new JTextField();
		txtDate.setBounds(115, 189, 371, 20);
		contentPane.add(txtDate);
		txtDate.setColumns(10);
		
		JButton btnAdd = new JButton("Update");
		btnAdd.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				updateTask();
			}
		});
		btnAdd.setBounds(24, 232, 88, 23);
		contentPane.add(btnAdd);
		
		JButton btnClear = new JButton("Clear");
		btnClear.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				clearForm();
			}
		});
		btnClear.setBounds(212, 232, 88, 23);
		contentPane.add(btnClear);
		
		JButton btnCancel = new JButton("Cancel");
		btnCancel.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				setVisible(false);
			}
		});
		btnCancel.setBounds(398, 232, 88, 23);
		contentPane.add(btnCancel);
		
		JPanel panel = new JPanel();
		panel.setBackground(SystemColor.textInactiveText);
		panel.setBounds(0, 0, 520, 31);
		contentPane.add(panel);
		
		JLabel lblTitle = new JLabel("Edit a Task");
		panel.add(lblTitle);
		lblTitle.setForeground(Color.WHITE);
		lblTitle.setFont(new Font("Century Gothic", Font.BOLD, 16));
		lblTitle.setHorizontalAlignment(SwingConstants.CENTER);
		
		txtDescription = new JTextArea();
		txtDescription.setWrapStyleWord(true);
		txtDescription.setLineWrap(true);
		txtDescription.setBounds(115, 44, 370, 88);
		contentPane.add(txtDescription);
	}
	
	public void loadTask(Task t){
		//Load the task components into each corresponding textbox
		txtDescription.setText(t.getDescription());
		txtStartTime.setText(t.getStartTime());
		txtFinishTime.setText(t.getFinishTime());
		txtDate.setText(t.getDate());
	}
	
	private void clearForm(){
		txtStartTime.setText("");
		txtFinishTime.setText("");
		txtDate.setText("");
	}
	
	
	private void updateTask(){
		String desc = txtDescription.getText();
		String startTime = txtStartTime.getText();
		String finishTime = txtFinishTime.getText();
		String date = txtDate.getText();
		
		//validation checks
		if(startTime.equals("") || finishTime.equals("") || date.equals("")){
			JOptionPane.showMessageDialog(null, "Please make sure that the start time, finish time and date has been correctly entered.", "Error", 0);
		}else{
			Task t = new Task(desc, startTime, finishTime, date);
			TableGUI.updateTask(t);
			this.setVisible(false);
		}

	}
}
