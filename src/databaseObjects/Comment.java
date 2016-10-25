package databaseObjects;

import java.sql.Time;
import java.util.Date;

public class Comment {
	
	private int id;
	private String text;
	private String email;
	private Date date;

	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
	}

	private Time time;
	private int post;
	
	public Comment() {
	}
	
	public Comment(int id, String text, Time time, Date date, String email, int post) {
		this.id = id;
		this.text = text;
		this.time = time;
		this.date = date;
		this.email = email;
		this.post = post;
	}
	
	public void setId(int id) {
		this.id = id;
	}
	
	public void setText(String text) {
		this.text = text;
	}
	
	public void setTime(Time time) {
		this.time = time;
	}
	
	public void setEmail(String email) {
		this.email = email;
	}
	
	public void setPost(int post) {
		this.post = post;
	}
	
	public int getID() {
		return id;
	}
	
	public String getText() {
		return text;
	}
	
	public Time getTime() {
		return time;
	}
	
	public String getEmail() {
		return email;
	}
	
	public int getPost() {
		return post;
	}

	public String toString() {
		return "\nKommentars-id: " + id + "\nText: " + text + "\nTid: " + time + 
				"\nEmail: " + email + "\nTillh�r post-id: " + post + "\n";
	}
	
}
