package databaseObjects;

import java.util.Date;

public class Post {

	private int id;
	private String text;
	private Date date;
	private int politican;
	private int likes;
	private int retweets;
	private String source;
	private int rank;


	public Post() {
	}



	public Post(String text, int politican, String source, Date date, int rank) {
		this.text = text;
		this.date = date;
		this.politican = politican;
		this.source = source;
		this.rank = rank;
	}

	public int getRank() {
		return rank;
	}

	public void setRank(int rank) {
		this.rank = rank;
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
		return "\nby: " + politican + "\npostID: " + id + "\ntime: " + date + "\ntext: " + text + "\nsource: " + source
				+ "\nrank: " + rank;
	}
}
