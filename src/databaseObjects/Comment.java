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
	private int post;
	private String text;
	private String alias;
	private Date date;
	private Time time;

	
	public Comment() {
	}
	

	public Comment(int id, String text, Time time, Date date, String alias, int post) {
		this.id = id;
		this.text = text;
		this.time = time;
		this.date = date;
		this.alias = alias;
		this.post = post;
	}
	
	public String getAlias() {
		return alias;
	}

	public void setAlias(String ip) {
		this.alias = ip;
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
		this.alias = email;
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
		return alias;
	}
	
	public int getPost() {
		return post;
	}

	public String toString() {
		return "\nKommentars-id: " + id + "\nText: " + text + "\nTid: " + time + 
				"\nAlias: " + alias + "\nTillh�r post-id: " + post + "\n";
	}
}
