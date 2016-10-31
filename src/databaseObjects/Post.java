package databaseObjects;


import java.sql.Time;
import java.util.Date;

/**
 * Post class representing a single Post. Only getters and setters.
 * 
 * @author Anton Gustafsson
 *
 */
public class Post {

	private String text;
	private String source;
	private Date date;
	private Time time;
	private int id;
	private int politican;
	private int retweets;
	private int likes;
	private int dislikes;

	public Post() {
	}

	public Post(String text, int politican, String source, Date date, Time time, int likes, int dislikes) {
		this.time = time;
		this.text = text;
		this.date = date;
		this.politican = politican;
		this.source = source;
		this.likes = likes;
		this.dislikes = dislikes;

	}

	public int getDislikes() {
		return dislikes;
	}

	public void setDislikes(int dislikes) {
		this.dislikes = dislikes;
	}

	public Time getTime() {
		return time;
	}

	public void setTime(Time time) {
		this.time = time;
	}

	public void setId(int id) {
		this.id = id;
	}

	public void setText(String text) {
		this.text = text;
	}

	public void setDate(Date date) {
		this.date = date;
	}

	public void setPolitican(int politican) {
		this.politican = politican;
	}

	public void setLikes(int likes) {
		this.likes = likes;
	}

	public void setRetweets(int retweets) {
		this.retweets = retweets;
	}

	public void setSource(String source) {
		this.source = source;
	}

	public int getID() {
		return id;
	}

	public String getText() {
		return text;
	}

	public Date getDate() {
		return date;
	}

	public int getPolitican() {
		return politican;
	}

	public int getLikes() {
		return likes;
	}

	public int getRetweets() {
		return retweets;
	}

	public String getSource() {
		return source;
	}

	public String toString() {
		return "\npID: " + politican + "\npostID: " + id + "\ntime: " + date + "\ntext: " + text + "\nsource: " + source
		;
	}
}
