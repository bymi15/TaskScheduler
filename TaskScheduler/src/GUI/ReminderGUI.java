package GUI;

import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.File;

import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.Line;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;
import javax.swing.SwingUtilities;
import javax.swing.border.EmptyBorder;

import Logic.Task;
import javax.swing.JTextArea;
import javax.swing.UIManager;

public class ReminderGUI extends JFrame {

	private JPanel contentPane;
	private JLabel txtTime;
	private JLabel lblRevise;
	private JTextArea txtDesc;
	
	
	public ReminderGUI(Task t, int n) {
		loadGUI();
		fillText(t, n);
		
		 try {
			final Clip sound = (Clip) AudioSystem.getLine(new Line.Info(Clip.class));
			sound.open(AudioSystem.getAudioInputStream(new File(TableGUI.classPath + "sound.wav")));
			
			Runnable r = new Runnable() {
	            public void run() {
	            	addWindowListener(new WindowAdapter() {
	        	        public void windowClosed(WindowEvent e) {
	        	        	sound.stop();
	        	            TableGUI.remList.remove(TableGUI.remList.size());
	        	        }
	        	    });
	            	sound.start();
	            }
	        };
	        SwingUtilities.invokeLater(r);
	        
			sound.start();
		} catch (Exception e){
			e.printStackTrace();
		}
	}
	

	private void loadGUI(){
		setResizable(false);
		setTitle("Reminder");
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(100, 100, 395, 266);
		setLocationRelativeTo(null);
		contentPane = new JPanel();
		contentPane.setBackground(Color.DARK_GRAY);
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JPanel panel = new JPanel();
		panel.setBackground(new Color(105, 105, 105));
		panel.setBounds(0, 0, 389, 42);
		contentPane.add(panel);
		
		JLabel lblAlert = new JLabel("ALERT! ");
		lblAlert.setHorizontalAlignment(SwingConstants.CENTER);
		lblAlert.setForeground(new Color(255, 0, 0));
		lblAlert.setFont(new Font("Nirmala UI", Font.BOLD, 18));
		lblAlert.setBackground(Color.WHITE);
		panel.add(lblAlert);
		
		lblRevise = new JLabel("30 minutes remaining!");
		lblRevise.setForeground(new Color(0, 255, 0));
		panel.add(lblRevise);
		lblRevise.setBackground(Color.WHITE);
		lblRevise.setFont(new Font("Nirmala UI", Font.BOLD, 18));
		lblRevise.setHorizontalAlignment(SwingConstants.CENTER);
		
		JLabel lblDescription = new JLabel("Description:");
		lblDescription.setHorizontalAlignment(SwingConstants.TRAILING);
		lblDescription.setForeground(new Color(173, 255, 47));
		lblDescription.setFont(new Font("Rockwell", Font.BOLD, 14));
		lblDescription.setBackground(Color.WHITE);
		lblDescription.setBounds(8, 68, 122, 18);
		contentPane.add(lblDescription);
		
		JButton btnOk = new JButton("OK");
		btnOk.setBackground(Color.ORANGE);
		btnOk.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				dispose();
			}
		});
		btnOk.setBounds(152, 195, 89, 23);
		contentPane.add(btnOk);
		
		JLabel lblTime = new JLabel("Time:");
		lblTime.setHorizontalAlignment(SwingConstants.TRAILING);
		lblTime.setForeground(new Color(173, 255, 47));
		lblTime.setFont(new Font("Rockwell", Font.BOLD, 14));
		lblTime.setBackground(Color.WHITE);
		lblTime.setBounds(12, 151, 122, 18);
		contentPane.add(lblTime);
		
		txtTime = new JLabel("12:50 - 14:20");
		txtTime.setForeground(Color.WHITE);
		txtTime.setFont(new Font("Gill Sans Nova", Font.PLAIN, 14));
		txtTime.setBackground(new Color(105, 105, 105));
		txtTime.setBounds(142, 151, 208, 18);
		contentPane.add(txtTime);
		
		txtDesc = new JTextArea();
		txtDesc.setWrapStyleWord(true);
		txtDesc.setLineWrap(true);
		txtDesc.setForeground(Color.WHITE);
		txtDesc.setBackground(new Color(105, 105, 105));
		txtDesc.setEditable(false);
		txtDesc.setBounds(142, 68, 208, 70);
		contentPane.add(txtDesc);
	}
	
	private void fillText(Task t, int n) {
		txtDesc.setText(t.getDescription());
		txtTime.setText(t.getStartTime() + " - " + t.getFinishTime());
		if(n == 1) lblRevise.setText("It's time to begin!");
		if(n == 2) lblRevise.setText("5 minutes remaining!");
		if(n == 3) lblRevise.setText("30 minutes remaining!");
	}
	
}
