package Logic;

import java.io.Serializable;
import java.util.Date;
import java.util.Timer;
import java.util.TimerTask;

public class Reminder implements Serializable{
	
	private static final long serialVersionUID = 1L;
	private transient Timer timer;
	private TimerTask timerTask;
	
	public Reminder(){
		timer = new Timer(true);
	}
	
	public void setReminder(TimerTask task, Date time){
		timer.schedule(task, time);
		timerTask = task;
	}
	
	public void cancel(){
		timer.cancel();
	}
	
	public MyTimerTask getTimerTask(){
		return (MyTimerTask)timerTask;
	}
}
