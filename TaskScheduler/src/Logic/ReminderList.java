package Logic;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;

import Logic.LinkedList.Node;

public class ReminderList implements Serializable{
	
	private static final long serialVersionUID = 1L;
	private Node head;
	private int listCount;

	//This linked list begins at index 1, as index 0 is the head node which is null.
	public ReminderList() // Constructor
	{
		head = new Node(null);
		listCount = 0;
	}

	public void add(Reminder data)
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

	public void add(Reminder data, int index) {
		
		Node temp = new Node(data); 
		Node current = head; 
		
		
		for (int i = 1; i < index && current.getNext() != null; i++) {
			current = current.getNext(); 
		}
		temp.setNext(current.getNext()); 
		current.setNext(temp); 
		listCount++;
	}

	public Reminder get(int index) { 
		
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
			output += "[" + current.getData().getTimerTask().getTask().getStartTime() + "]";
			current = current.getNext();
		}
		
		return output; 
	}

	//Static method: read a ReminderList object from a file
	public static ReminderList readFile(String path) {
		ReminderList temp = new ReminderList();
		try {
			FileInputStream fis = new FileInputStream(path);
			ObjectInputStream ois = new ObjectInputStream(fis);
			temp = (ReminderList) ois.readObject();
			fis.close();
			ois.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return temp;
	}

	//Static method: write a ReminderList object to a file
	public static void writeToFile(ReminderList Reminders, String path){
		try{
			FileOutputStream fos = new FileOutputStream(path);
			ObjectOutputStream oos = new ObjectOutputStream(fos);
			oos.writeObject(Reminders);
			fos.close();
			oos.close();
		} catch (Exception e){
			e.printStackTrace();
		}
	}
	
	class Node implements Serializable{
		
		private static final long serialVersionUID = 1L;
		Node next;
		Reminder data;

		public Node(Reminder _data) {
			next = null;
			data = _data;
		}

		public Node(Reminder _data, Node _next) {
			next = _next;
			data = _data;
		}

		public Reminder getData() {
			return data;
		}

		public void setData(Reminder _data) {
			data = _data;
		}

		public Node getNext() {
			return next;
		}

		public void setNext(Node _next) {
			next = _next;
		}
	}
}
