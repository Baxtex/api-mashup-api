package databaseObjects;

public class Comment {
	
	private int id;
	private String text;
	private String time;
	private String email;
	private int post;
	
	public Comment() {
	}
	
	public Comment(int id, String text, String time, String email, int post) {
		this.id = id;
		this.text = text;
		this.time = time;
		this.email = email;
		this.post = post;
	}
	
	public void setId(int id) {
		this.id = id;
	}
	
	public void setText(String text) {
		this.text = text;
	}
	
	public void setTime(String time) {
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
	
	public String getTime() {
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
				"\nEmail: " + email + "\nTillhör post-id: " + post + "\n";
	}
	
}
