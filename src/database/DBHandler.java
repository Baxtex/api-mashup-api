package database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.LinkedList;

import databaseObjects.Comment;
import databaseObjects.Party;
import databaseObjects.Politician;
import databaseObjects.Post;

public class DBHandler {

	private final String DRIVER = "com.mysql.jdbc.Driver";
	private final String URL = "jdbc:mysql://195.178.232.16:3306/AB7455";
	private final String USER = "AB7455";
	private final String PASSWORD = "kajsaecool";

	/**
	 * Opens connection to database
	 * 
	 * @return
	 */

	private Connection getConnection() {
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
	 * 
	 * @param connection
	 */

	private void closeConnection(Connection connection) {
		if (connection != null) {
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
	 * 
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
	 * 
	 * @param parties
	 */

	public void addParties(LinkedList<Party> parties) {
		Connection connection = getConnection();
		PreparedStatement statement = null;
		String query = null;
		try {
			for (int i = 0; i < parties.size(); i++) {
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
	 * 
	 * @param politican
	 */

	public void addPolitican(Politician politician) {
		String query = "insert into politicians(name, party, fb_url, twitter_url) VALUES (?, ?, ?, ?)";
		Connection connection = getConnection();
		PreparedStatement statement = null;
		try {
			statement = getConnection().prepareStatement(query);
			statement.setString(1, politician.getName());
			statement.setString(2, politician.getParty());
			statement.setString(3, politician.getFacebook_URL());
			statement.setString(4, politician.getTwitter_URL());
			statement.executeUpdate();
//			closeConnection(connection);
			System.out.println("DBHandler: Added politican to database");
		} catch (SQLException e) {
			System.out.print("DBHandler: Exception when adding politican to database");
		} finally {
			closeConnection(connection);
		}
	}

	/**
	 * Adds a list of politicians to the database
	 * 
	 * @param parties
	 */

	public void addPoliticians(LinkedList<Politician> politicians) {
		Connection connection = getConnection();
		PreparedStatement statement = null;
		String query = "insert into politicians(name, party, fb_url, twitter_url) VALUES (?, ?, ?, ?)";
		try {
			for (int i = 0; i < politicians.size(); i++) {
				statement = connection.prepareStatement(query);
				statement.setString(1, politicians.get(i).getName());
				statement.setString(2, politicians.get(i).getParty());
				statement.setString(3, politicians.get(i).getFacebook_URL());
				statement.setString(4, politicians.get(i).getTwitter_URL());
				statement.executeUpdate();
//				closeConnection(connection);
				System.out.println("DBHandler: Added a list of politicians to database");
			}
		} catch (SQLException e) {
			System.out.println("DBHandler: Exception when trying to add a list of politicians");
		} finally {
			closeConnection(connection);
		}
//		closeConnection(connection);
	}

	/**
	 * Adds a new post to the database
	 * 
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
	 * 
	 * @param posts
	 */

	public void addPosts(LinkedList<Post> posts) {
		Connection connection = getConnection();
		PreparedStatement statement = null;
		String query = null;
		try {
			for (int i = 0; i < posts.size(); i++) {
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
				System.out.println("DBHandler: Added a list of politicians to database");
			}
		} catch (SQLException e) {
			System.out.println("DBHandler: Exception when trying to add a list of politicians");
		} finally {
			closeConnection(connection);
		}
	}

	/**
	 * Adds a new comment to the database
	 * 
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

	// Get methods
	// -----------------------------------------------------------------------

	/**
	 * Returns a list of all parties in database
	 * 
	 * @return
	 */

	public LinkedList<Party> getParties() {
		LinkedList<Party> parties = new LinkedList<Party>();
		Connection connection = getConnection();
		try {
			String query = "select * from parties";
			PreparedStatement statement = connection.prepareStatement(query);
			ResultSet rs = statement.executeQuery();
			while (rs.next()) {
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
	 * Returns a list of all politicians in the database
	 * 
	 * @return
	 */

	public LinkedList<Politician> getPoliticians() {
		LinkedList<Politician> politicians = new LinkedList<Politician>();
		Connection connection = getConnection();
		try {
			String query = "select * from politicians";
			PreparedStatement statement = connection.prepareStatement(query);
			ResultSet rs = statement.executeQuery();
			while (rs.next()) {
				Politician politician = new Politician();
				politician.setName(rs.getString("name"));
				politician.setParty(rs.getString("party"));
				politicians.add(politician);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			closeConnection(connection);
		}
		return politicians;
	}

	/**
	 * Returns a list of all posts from the specified politican
	 * 
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
			while (rs.next()) {
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
	 * 
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
			while (rs.next()) {
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

	}

}
