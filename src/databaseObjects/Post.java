package databaseObjects;

public class Post {

	private int id;
	private String text;
	private String time;
	private int politican;
	private int likes;
	private int retweets;
	private String source;
	private int rank;


	public Post() {
	}

	public Post(int id, String text, String time, int politican, int likes, int retweets, String source) {
		this.id = id;
		this.text = text;
		this.time = time;
		this.politican = politican;
		this.likes = likes;
		this.retweets = retweets;
		this.source = source;
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

	public void setTime(String time) {
		this.time = time;
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

	public String getTime() {
		return time;
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
		return "\nby: " + politican + "\npostID: " + id + "\ntime: " + time + "\ntext: " + text + "\nsource: " + source
				+ "\nrank: " + rank;
	}
}
