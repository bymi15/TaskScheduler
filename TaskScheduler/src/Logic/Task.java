 package Logic;

import java.io.Serializable;

public class Task implements Serializable{

	private static final long serialVersionUID = 1L;
	
	//Declare member fields
	private String description;
	private String startTime;
	private String finishTime;
	private String date;
	private String completed;
	
	//Constructor 1, empty parameters
	public Task(){
		description = "";
		date = "";
		startTime = null;
		finishTime = null;
		completed = "N";
	}
	
	//Constructor 2, param: description, date
	public Task(String _description, String _date){
		description = _description;
		date = _date;
		
		startTime = null;
		finishTime = null;
		completed = "N";
	}
	
	//Constructor 3, param: date
	public Task(String _date) {
		date = _date;

		description = "";
		startTime = null;
		finishTime = null;
		completed = "N";
	}
	
	//Constructor 4: param: full param excluding bool completed
	public Task(String _description, String _startTime, String _finishTime, String _date){
		description = _description;
		startTime = _startTime;
		finishTime = _finishTime;
		date = _date;
		completed = "N";
	}
	
	//Constructor 5: full param
	public Task(String _description, String _startTime, String _finishTime, String _date, String _completed){
		description = _description;
		startTime = _startTime;
		finishTime = _finishTime;
		date = _date;
		completed = _completed;
	}

	//Accessors 
	public String getDescription() {
		return description;
	}

	public String getStartTime() {
		return startTime;
	}

	public String getFinishTime() {
		return finishTime;
	}

	public String getDate() {
		return date;
	}

	public String isCompleted() {
		return completed;
	}
	
	//Mutators
	public void setDescription(String _description) {
		description = _description;
	}

	public void setStartTime(String _startTime) {
		startTime = _startTime;
	}

	public void setFinishTime(String _finishTime) {
		finishTime = _finishTime;
	}

	public void setDate(String _date) {
		date = _date;
	}

	public void setCompleted(String _completed) {
		completed = _completed;
	}
	
	
}
