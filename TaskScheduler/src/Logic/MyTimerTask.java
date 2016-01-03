package Logic;

import java.io.Serializable;
import java.util.TimerTask;

import GUI.ReminderGUI;

public class MyTimerTask extends TimerTask implements Serializable{

	private static final long serialVersionUID = 1L;
	private Task t;
	private int n;
	private Reminder rem;
	
	public MyTimerTask(Task _t, int _n, Reminder _rem){
		t = _t;
		n = _n;
		rem = _rem;
	}
	
	public void run() {
		ReminderGUI frame = new ReminderGUI(t, n);
		frame.setVisible(true);
		rem.cancel();
	}
	
	public Task getTask(){
		return t;
	}
	
	public int getN(){
		return n;
	}
}

