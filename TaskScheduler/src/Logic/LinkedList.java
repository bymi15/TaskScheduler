package Logic;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class LinkedList implements Serializable{
	
	private static final long serialVersionUID = 1L;
	private Node head;
	private int listCount;

	//This linked list begins at index 1, as index 0 is the head node which is null.
	public LinkedList() // Constructor
	{
		head = new Node(null);
		listCount = 0;
	}

	public void add(Task data)
	{
		Node temp = new Node(data);
		Node current = head;

		while (current.getNext() != null)
		{
			current = current.getNext();
		}

		current.setNext(temp); 
		listCount++;
	}

	public void add(Task data, int index) {
		
		Node temp = new Node(data); 
		Node current = head; 
		
		
		for (int i = 1; i < index && current.getNext() != null; i++) {
			current = current.getNext(); 
		}
		temp.setNext(current.getNext()); 
		current.setNext(temp); 
		listCount++;
	}

	public Task get(int index) { 
		
		if (index < 0)
			return null;

		Node current = head.getNext();
		
		for (int i = 1; i <= index; i++) {
			if (current.getNext() == null) 
				return null;

			current = current.getNext();
		}
		
		return current.getData();
	}

	public boolean remove(int index) { 
		
		if (index < 0 || index > size()) 
			return false;

		Node current = head.getNext();
		
		if(current.getNext() == null){
			current = null;
			listCount--;
			return true;
		}else if(current.getNext().getNext() == null){
			current.setNext(null);
			listCount--;
			return true;
		}
		
		for (int i = 1; i < index; i++) {
			current = current.getNext();
		}
		Node nodeToRemove = current.getNext();
		current.setNext(nodeToRemove.getNext());
		listCount--; 
		
		return true; 
	}
	
	public int size() { 
		return listCount; 
	}

	public String toString() { 
		
		Node current = head.getNext(); 
		String output = ""; 
		
		while (current != null) { 
			output += "[" + current.getData().getDescription() + "]"; 
			current = current.getNext();
		}
		
		return output;
	}
	
	
	public void sort(){ //sorts the LinkedList in order of date/time using insertion sort
		//Loop through the linked list
		for(int i = 0; i < size() - 1; i++){
				//Traverse through the linked list
				for(Node n = head.getNext(); n.getNext() != null; n = n.getNext()){
					//Parse the date - Format: dd/MM/yyyy				
					//Separate the day, month and years
					Task t = n.getData();
					String date = t.getDate();
					String time = t.getStartTime();
					
					Date dateObj = null;
					try {
						dateObj = new SimpleDateFormat("dd/MM/yyyy").parse(date);
					} catch (ParseException e) {
						e.printStackTrace();
					}
					String formattedDate = new SimpleDateFormat("yyyy/MM/dd").format(dateObj);
					String dateAndTime = formattedDate.replaceAll("/", "") + time.replace(":", "");
					long dateVal = Long.parseLong(dateAndTime);
					
					//Repeat for the next node
					Task _t = n.getNext().getData();
					String _date = _t.getDate();
					String _time = _t.getStartTime();
					
					Date _dateObj = null;
					try {
						_dateObj = new SimpleDateFormat("dd/MM/yyyy").parse(_date);
					} catch (ParseException e) {
						e.printStackTrace();
					}
					String _formattedDate = new SimpleDateFormat("yyyy/MM/dd").format(_dateObj);
					String _dateAndTime = _formattedDate.replaceAll("/", "") + _time.replace(":", "");
					long _dateVal = Long.parseLong(_dateAndTime);
					
					//Compare date values
					if(_dateVal < dateVal){
						//Swap data of node n and node n+1
						Task temp = n.getData();
						n.setData(n.getNext().getData());
						n.getNext().setData(temp);
					}
			}
		}
	}


	//Static method: read a LinkedList object from a file
	public static LinkedList readFile(String path) {
		LinkedList temp = new LinkedList();
		try {
			//Declare and instantiate input streams
			FileInputStream fis = new FileInputStream(path);
			ObjectInputStream ois = new ObjectInputStream(fis);
			//Read object, casting it as a LinkedList, and store in temp.
			temp = (LinkedList) ois.readObject();
			//Close input streams
			fis.close();
			ois.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return temp;
	}

	//Static method: write a LinkedList object to a file
	public static void writeToFile(LinkedList tasks, String path){
		try{
			//Declare and instantiate output streams
			FileOutputStream fos = new FileOutputStream(path);
			ObjectOutputStream oos = new ObjectOutputStream(fos);
			//Write LinkedList object
			oos.writeObject(tasks);
			//Close output streams
			fos.close();
			oos.close();
		} catch (Exception e){
			e.printStackTrace();
		}
	}
	
	//Node: task
	class Node implements Serializable{
		
		private static final long serialVersionUID = 1L;
		Node next; //reference to the next node
		Task data; //data stored in the current node

		//Constructor 1, Param: data
		public Node(Task _data) {
			next = null;
			data = _data;
		}

		//Constructor 2, Param: data, next
		public Node(Task _data, Node _next) {
			next = _next;
			data = _data;
		}

		//Accessor: retrieve the data from the current node
		public Task getData() {
			return data;
		}

		//Mutator: set the data of the current node
		public void setData(Task _data) {
			data = _data;
		}

		//Accessor: retrieve the next node
		public Node getNext() {
			return next;
		}

		//Mutator: set the next node
		public void setNext(Node _next) {
			next = _next;
		}
	}
}
