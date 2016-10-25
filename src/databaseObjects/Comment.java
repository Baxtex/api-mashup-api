package databaseObjects;

import java.util.Date;
public class Comment {
	
	private int id;
	private String text;
	private String email;
	private Date date;

	private int post;
	
	public Comment() {
	}
	
	public Comment(String text, String email, int post, Date date) {
		this.text = text;
		this.email = email;
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
	
	
	public String getEmail() {
		return email;
	}
	
	public int getPost() {
		return post;
	}

	public String toString() {
		return "\ncommID " + id + "\nText: " + text + "\nTime: " + date + "\nEmail: " + email + "\npostID " + post
				+ "\n";
	}
	
}
