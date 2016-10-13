package database;

import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.LinkedList;

import databaseObjects.*;

import java.sql.Connection;

public class DBHandler {
	
	private final String DRIVER ="com.mysql.jdbc.Driver";
	private final String URL = "jdbc:mysql://195.178.232.16:3306/AB7455";
	private final String USER = "AB7455";
	private final String PASSWORD = "kajsaecool";
	
	/**
	 * Opens connection to database
	 * @return
	 */
	
	public Connection getConnection() {
		Connection connection = null;
		try {
			Class.forName(DRIVER);
		} catch (ClassNotFoundException e) {
			System.out.println("DBHandler: Exception when assigning driver");
			e.printStackTrace();
		}
		try {
			connection = DriverManager.getConnection(URL, USER, PASSWORD);
			System.out.println("DBHandler: Connected to database: " + connection.toString());
		} catch (SQLException e) {
			System.out.println("DBHandler: Exception when connecting to database");
			e.printStackTrace();
		}
		return connection;
	} 
	
	/**
	 * Closes the connection to database
	 * @param connection
	 */
	
	public void closeConnection(Connection connection) {
		if(connection != null) {
			try {
				connection.close();
			} catch (SQLException e) {
				System.out.println("DBHandler: Exception trying to close the connection");
			}
		}
	}

	// Insert methods
	
	/**
	 * Adds a new party to the database
	 * @param party
	 */
	
	public void addParty(Party party) {
		String query = "insert into parties(name, nameShort) VALUES (?, ?)";
		Connection connection = getConnection();
		PreparedStatement statement = null;
		try {
			statement = connection.prepareStatement(query);
			statement.setString(1, party.getName());
			statement.setString(2, party.getNameShort());
			statement.executeUpdate();
			System.out.println("DBHandler: Added party to database");
		} catch (SQLException e) {
			System.out.print("DBHandler: Exception when adding party to database");
		} finally {
			closeConnection(connection);
		}
	}
	
	/**
	 * Adds a list of parties to the database
	 * @param parties
	 */
	
	public void addParties(LinkedList<Party> parties) {
		Connection connection = getConnection();
		PreparedStatement statement = null;
		String query = null;
		try {
			for(int i = 0; i < parties.size(); i++) {
				query = "insert into parties(name, nameShort) VALUES (?, ?)";
				statement = connection.prepareStatement(query);
				statement.setString(1, parties.get(i).getName());
				statement.setString(2, parties.get(i).getNameShort());
				statement.executeUpdate();
				System.out.println("DBHandler: Added a list of parties to database");
			} 
		} catch (SQLException e) {
			System.out.println("DBHandler: Exception when trying to add a list of parties");
		} finally {
			closeConnection(connection);
		}
	}
	
	/**
	 * Adds a new politican to database
	 * @param politican
	 */
	
	public void addPolitican(Politican politican) {
		String query = "insert into politicans(id, name, party) VALUES (?, ?, ?)";
		Connection connection = getConnection();
		PreparedStatement statement = null;
		try {
			statement = getConnection().prepareStatement(query);
			statement.setInt(1, politican.getID());
			statement.setString(2, politican.getName());
			statement.setString(3, politican.getParty());
			statement.executeUpdate();
			System.out.println("DBHandler: Added politican to database");
		} catch (SQLException e) {
			System.out.print("DBHandler: Exception when adding politican to database");
		} finally {
			closeConnection(connection);
		}
	}
	
	/**
	 * Adds a list of politicans to the database
	 * @param parties
	 */
	
	public void addPoliticans(LinkedList<Politican> politicians) {
		Connection connection = getConnection();
		PreparedStatement statement = null;
		String query = null;
		try {
			for(int i = 0; i < politicians.size(); i++) {
				query = "insert into politicans(id, name, party) VALUES (?, ?, ?)";
				statement = connection.prepareStatement(query);
				statement.setInt(1, politicians.get(i).getID());
				statement.setString(2, politicians.get(i).getName());
				statement.setString(3, politicians.get(i).getParty());
				statement.executeUpdate();
				System.out.println("DBHandler: Added a list of politicans to database");
			} 
		} catch (SQLException e) {
			System.out.println("DBHandler: Exception when trying to add a list of politicans");
		} finally {
			closeConnection(connection);
		}
	}
	
	/**
	 * Adds a new post to the database
	 * @param post
	 */
	
	public void addPost(Post post) {
		String query = "insert into posts(id, text, time, likes, retweets, politican, source) VALUES (?, ?, ?, ?, ?, ?, ?)";
		Connection connection = getConnection();
		PreparedStatement statement = null;
		try {
			statement = connection.prepareStatement(query);
			statement.setInt(1, post.getID());
			statement.setString(2, post.getText());
			statement.setString(3, post.getTime());
			statement.setInt(4, post.getLikes());
			statement.setInt(5, post.getRetweets());
			statement.setInt(6, post.getPolitican());
			statement.setString(7, post.getSource());
			System.out.println(post.getSource());
			statement.executeUpdate();
			System.out.println("DBHandler: Added post to database");
		} catch (SQLException e) {
			System.out.print("DBHandler: Exception when adding post to database");
		} finally {
			closeConnection(connection);
		}
	}
	
	/**
	 * Adds a list of posts to database
	 * @param posts
	 */
	
	public void addPosts(LinkedList<Post> posts) {
		Connection connection = getConnection();
		PreparedStatement statement = null;
		String query = null;
		try {
			for(int i = 0; i < posts.size(); i++) {
				query = "insert into posts(id, text, time, politican, source, likes, retweets) VALUES (?, ?, ?, ?, ?, ?, ?)";
				statement = connection.prepareStatement(query);
				statement.setInt(1, posts.get(i).getID());
				statement.setString(2, posts.get(i).getText());
				statement.setString(3, posts.get(i).getTime());
				statement.setInt(4, posts.get(i).getPolitican());
				statement.setString(5, posts.get(i).getSource());
				statement.setInt(6, posts.get(i).getLikes());
				statement.setInt(7, posts.get(i).getRetweets());
				statement.executeUpdate();
				System.out.println("DBHandler: Added a list of politicans to database");
			} 
		} catch (SQLException e) {
			System.out.println("DBHandler: Exception when trying to add a list of politicans");
		} finally {
			closeConnection(connection);
		}
	}
	
	/**
	 * Adds a new comment to the database
	 * @param comment
	 */
	
	public void addComment(Comment comment) {
		String query = "insert into comments(id, text, time, email, post) VALUES (?, ?, ?, ?, ?)";
		Connection connection = getConnection();
		PreparedStatement statement;
		try {
			statement = connection.prepareStatement(query);
			statement.setInt(1, comment.getID());
			statement.setString(2, comment.getText());
			statement.setString(3, comment.getTime());
			statement.setString(4, comment.getEmail());
			statement.setInt(5, comment.getPost());
			statement.executeUpdate();
			System.out.println("DBHandler: Added comment to database");
		} catch (SQLException e) {
			System.out.print("DBHandler: Exception when adding comment to database");
		} finally {
			closeConnection(connection);
		}
	}
	
	
	
	
	
	
	// Get methods -----------------------------------------------------------------------
	
	/**
	 * Returns a list of all parties in database
	 * @return
	 */
	
	public LinkedList<Party> getParties() {
		LinkedList<Party> parties = new LinkedList<Party>();
		Connection connection = getConnection();
		try {
			String query = "select * from parties";
			PreparedStatement statement = connection.prepareStatement(query);
			ResultSet rs = statement.executeQuery();
			while(rs.next()) {
				Party party = new Party();
				party.setName(rs.getString("name"));
				party.setNameShort(rs.getString("nameShort"));
				parties.add(party);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			closeConnection(connection);
		}
		return parties;	
	}
	
	/**
	 * Returns a list of all politicans in the database
	 * @return
	 */
	
	public LinkedList<Politican> getPoliticans() {
		LinkedList<Politican> politicans = new LinkedList<Politican>();
		Connection connection = getConnection();
		try {
			String query = "select * from politicans";
			PreparedStatement statement = connection.prepareStatement(query);
			ResultSet rs = statement.executeQuery();
			while(rs.next()) {
				Politican politican = new Politican();
				politican.setId(rs.getInt("id"));
				politican.setName(rs.getString("name"));
				politican.setParty(rs.getString("party"));
				politicans.add(politican);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			closeConnection(connection);
		}
		return politicans;	
	}
	
	/**
	 * Returns a list of all posts from the specified politican
	 * @param politican
	 * @return
	 */
	
	public LinkedList<Post> getPosts(int politican) {
		LinkedList<Post> posts = new LinkedList<Post>();
		Connection connection = getConnection();
		try {
			String query = "select * from posts where politican = " + politican + ";";
			PreparedStatement statement = connection.prepareStatement(query);
			ResultSet rs = statement.executeQuery();
			while(rs.next()) {
				Post post = new Post();
				post.setId(rs.getInt("id"));
				post.setText(rs.getString("text"));
				post.setTime(rs.getString("time"));
				post.setLikes(rs.getInt("likes"));
				post.setRetweets(rs.getInt("retweets"));
				post.setPolitican(rs.getInt("politican"));
				post.setSource(rs.getString("source"));
				posts.add(post);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			closeConnection(connection);
		}
		return posts;	
	}
	
	/**
	 * Returns a list of all comments on the specified post
	 * @param post
	 * @return
	 */
	
	public LinkedList<Comment> getComments(int post) {
		LinkedList<Comment> comments = new LinkedList<Comment>();
		Connection connection = getConnection();
		try {
			String query = "select * from comments where post = " + post + ";";
			PreparedStatement statement = connection.prepareStatement(query);
			ResultSet rs = statement.executeQuery();
			while(rs.next()) {
				Comment comment = new Comment();
				comment.setId(rs.getInt("id"));
				comment.setText(rs.getString("text"));
				comment.setTime(rs.getString("time"));
				comment.setEmail(rs.getString("email"));
				comment.setPost(rs.getInt("post"));
				comments.add(comment);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			closeConnection(connection);
		}		
		return comments;	
	}
		
		

	
	
	
	
	
	public static void main(String[] args) {
		DBHandler db = new DBHandler();
		

//		ArrayList<Party> parties = new ArrayList<Party>();
//		parties.add(new Party(1000, "Kajsas parti", "K"));
//		parties.add(new Party(1001, "Christoffers parti", "GEEK"));
//		parties.add(new Party(1002, "Antons parti", "ERNST"));
//		db.addParties(parties);
//		
//		System.out.println(db.getParties().toString());
//		
//		ArrayList<Politican> politicans = new ArrayList<Politican>();
//		politicans.add(new Politican(1, "Kajsa Ornstein", "S"));
//		politicans.add(new Politican(2, "Chris THE GEEK", "M"));
//		politicans.add(new Politican(3, "Anton", "V"));
//		db.addPoliticans(politicans);
//		
//		System.out.println(db.getPoliticans().toString());
//		
//		Post post1 = new Post(1, "Text1", "2016-10-10, 01:32", 1, 0, 53, "Twitter" );
//		Post post2 = new Post(2, "Text2", "2016-10-12, 01:48", 2, 15, 0, "Facebook");
//		Post post3 = new Post(3, "Text3", "2016-10-12, 01:48", 2, 15, 0, "Facebook");
//		Post post4 = new Post(4, "Text4", "2016-10-12, 01:48", 3, 15, 0, "Facebook");
//		ArrayList<Post> posts = new ArrayList();
//		posts.add(post1);
//		posts.add(post2);
//		posts.add(post3);
//		posts.add(post4);
//		db.addPosts(posts);
//		
//		System.out.println(db.getPosts(2).toString());
//
//		Comment comment = new Comment(10, "Kommentar 1", "Timestamp", "kajsa@gmail.com", 3);
//		Comment comment2 = new Comment(11, "Kommentar 2", "2016-11-11, 11:11", "mrrobot@gmail.com", 3);
//		db.addComment(comment);
//		db.addComment(comment2);

//		System.out.println(db.getComments(3));
		

	}
	
	
	
}
