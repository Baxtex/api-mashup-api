package databaseObjects;


import java.sql.Time;
import java.util.Date;


/**
 * Comment class that represents a single comment. Only contains getters and
 * setters.
 * 
 * @author Anton Gustafsson
 *
 */
public class Comment {
	
	private int id;
	private String text;
	private String ip;

	public String getIp() {
		return ip;
	}

	public void setIp(String ip) {
		this.ip = ip;
	}

	private Date date;
	private Time time;
	private int post;
	
	public Comment() {
	}
	

	public Comment(int id, String text, Time time, Date date, String ip, int post) {
		this.id = id;
		this.text = text;
		this.time = time;
		this.date = date;

		this.ip = ip;
		this.post = post;
		this.date = date;
	}
	
	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
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
		this.ip = email;
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
		return ip;
	}
	
	public int getPost() {
		return post;
	}

	public String toString() {
		return "\nKommentars-id: " + id + "\nText: " + text + "\nTid: " + time + 
				"\nEmail: " + ip + "\nTillh�r post-id: " + post + "\n";

	}
	
}
