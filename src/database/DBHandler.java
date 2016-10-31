package database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.LinkedList;

import databaseObjects.Comment;
import databaseObjects.Party;
import databaseObjects.Politician;
import databaseObjects.Post;

/**
 * DBHandler that handles the database(db).
 * 
 * @author Anton Gustafsson
 *
 */
public class DBHandler {
	private final String DRIVER = "com.mysql.cj.jdbc.Driver";
	private final String URL = "jdbc:mysql://195.178.232.16:3306/AB7455";
	private final String USER = "AB7455";
	private final String PASSWORD = "kajsaecool";
	private final DateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");

	/**
	 * Opens connection to database
	 * 
	 * @return - a conntection to the db.
	 */

	public Connection getConnection() {
		Connection connection = null;
		try {
			Class.forName(DRIVER);
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
		try {
			connection = DriverManager.getConnection(URL, USER, PASSWORD);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return connection;
	}

	/**
	 * Closes the connection to database
	 * 
	 * @param connection - the connection to close.
	 */

	public void closeConnection(Connection connection) {
		if (connection != null) {
			try {
				connection.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}

	/**
	 * Selects a list of posts from all politicians from a specific date.
	 * 
	 * @param dateStr - the date passed in from the url. Method builds a Date
	 * object out of it.
	 * @return - a list of posts.
	 */

	public LinkedList<Post> getAllPosts(String dateStr) {
		LinkedList<Post> posts = new LinkedList<Post>();
		Connection connection = getConnection();
		try {
			Date date = formatter.parse(dateStr);
			String query = "SELECT id,text,politican,source,date,time, likes, dislikes FROM posts  WHERE date=? GROUP BY TIME;";
			PreparedStatement statement = connection.prepareStatement(query);
			statement.setDate(1, new java.sql.Date(date.getTime()));
			ResultSet rs = statement.executeQuery();
			posts = loopRSPosts(rs);
		} catch (SQLException | ParseException e) {
			e.printStackTrace();
		} finally {
			closeConnection(connection);
		}
		return posts;
	}

	/**
	 * Selects a list of posts from all politicians from a specific date and
	 * party
	 * 
	 * @param dateStr - the date passed in from the url. Method builds a Date
	 * object out of it.
	 * @param party - the party abbreviation.
	 * @return - a list of posts.
	 */
	public LinkedList<Post> getAllPostsParty(String party, String dateStr) {
		LinkedList<Post> posts = new LinkedList<Post>();
		Connection connection = getConnection();
		try {
			Date date = formatter.parse(dateStr);
			String query = "SELECT * FROM posts WHERE politican in(select politicians.id from politicians where politicians.party = ?) AND posts.date = ?  GROUP BY TIME;";
			PreparedStatement statement = connection.prepareStatement(query);
			statement.setString(1, party);
			statement.setDate(2, new java.sql.Date(date.getTime()));
			ResultSet rs = statement.executeQuery();
			posts = loopRSPosts(rs);
		} catch (SQLException | ParseException e) {
			e.printStackTrace();
		} finally {
			closeConnection(connection);
		}
		return posts;
	}

	/**
	 * Select a list of post from a specific politician.
	 * 
	 * @param politicianID - the id of the politician.
	 */

	public LinkedList<Post> getPostsPolitician(int politicianID) {
		LinkedList<Post> posts = new LinkedList<Post>();
		Connection connection = getConnection();
		try {
			String query = "SELECT * FROM posts WHERE politican = ? GROUP BY TIME;";
			PreparedStatement statement = connection.prepareStatement(query);
			statement.setInt(1, politicianID);
			ResultSet rs = statement.executeQuery();
			posts = loopRSPosts(rs);
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			closeConnection(connection);
		}
		return posts;
	}

	/**
	 * Select a list of post from a specific politician.
	 * 
	 * @param politicianID - the id of the politician.
	 * @param dateStr - the date passed in from the url. Method builds a Date
	 * object out of it.
	 */

	public LinkedList<Post> getPostsPolitician(int politicanID, String dateStr) {
		LinkedList<Post> posts = new LinkedList<Post>();
		Connection connection = getConnection();
		try {
			Date date = formatter.parse(dateStr);
			String query = "SELECT * FROM posts WHERE politican = ? AND date = ? GROUP BY TIME;";
			PreparedStatement statement = connection.prepareStatement(query);
			statement.setInt(1, politicanID);
			statement.setDate(2, new java.sql.Date(date.getTime()));
			ResultSet rs = statement.executeQuery();
			posts = loopRSPosts(rs);
		} catch (SQLException | ParseException e) {
			e.printStackTrace();
		} finally {
			closeConnection(connection);
		}
		return posts;
	}

	/**
	 * Select a list of the 20 most up voted posts.
	 * 
	 * @return a list of the 20 most up voted posts
	 */

	public LinkedList<Post> getPostsMostUpvoted() {
		LinkedList<Post> posts = new LinkedList<Post>();
		Connection connection = getConnection();
		try {
			String query = "SELECT * FROM posts ORDER BY likes DESC LIMIT 20;";
			PreparedStatement statement = connection.prepareStatement(query);
			ResultSet rs = statement.executeQuery();
			posts = loopRSPosts(rs);
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			closeConnection(connection);
		}
		return posts;
	}

	/**
	 * Select a list of the 20 most down voted posts.
	 * 
	 * @return a list of the 20 most down voted posts
	 */
	public LinkedList<Post> getPostsMostDownvoted() {
		LinkedList<Post> posts = new LinkedList<Post>();
		Connection connection = getConnection();
		try {
			String query = "SELECT * FROM posts ORDER BY dislikes DESC LIMIT 20;";
			PreparedStatement statement = connection.prepareStatement(query);
			ResultSet rs = statement.executeQuery();
			posts = loopRSPosts(rs);
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			closeConnection(connection);
		}
		return posts;
	}

	/**
	 * Select a list of posts, but should only return a single post if it's id
	 * exists.
	 * 
	 * @param postID - the id of the post.
	 * @return - a list of posts.
	 */
	public LinkedList<Post> getSpecificPost(int postID) {
		LinkedList<Post> posts = new LinkedList<Post>();
		Connection connection = getConnection();
		try {
			String query = "SELECT * FROM posts WHERE id = ?;";
			PreparedStatement statement = connection.prepareStatement(query);
			statement.setInt(1, postID);
			ResultSet rs = statement.executeQuery();
			posts = loopRSPosts(rs);
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			closeConnection(connection);
		}
		return posts;
	}

	/**
	 * Loops through ResultSet with posts.
	 * 
	 * @param rs
	 * @return - a list containing posts.
	 */
	private LinkedList<Post> loopRSPosts(ResultSet rs) {
		LinkedList<Post> posts = new LinkedList<Post>();
		try {
			while (rs.next()) {
				Post post = new Post();
				post.setId(rs.getInt("id"));
				post.setText(rs.getString("text"));
				post.setPolitican(rs.getInt("politican"));
				post.setSource(rs.getString("source"));
				post.setDate(rs.getDate("date"));
				post.setTime(rs.getTime("time"));
				post.setLikes(rs.getInt("likes"));
				post.setDislikes(rs.getInt("dislikes"));
				posts.add(post);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return posts;
	}

	/**
	 * Selects all politicians.
	 * 
	 * @return - a list with politicians.
	 */

	public LinkedList<Politician> getPoliticians() {
		LinkedList<Politician> politicians = new LinkedList<Politician>();
		Connection connection = getConnection();
		try {
			String query = "SELECT * FROM politicians";
			PreparedStatement statement = connection.prepareStatement(query);
			ResultSet rs = statement.executeQuery();
			politicians = loopRSPolitcians(rs);
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			closeConnection(connection);
		}
		return politicians;
	}

	/**
	 * Selects a list of all politicians from a specific party
	 * 
	 * @param party - party abbreviation
	 * @return - a list with politicians.
	 */

	public LinkedList<Politician> getPoliticiansParty(String party) {
		LinkedList<Politician> politicians = new LinkedList<Politician>();
		Connection connection = getConnection();
		try {
			String query = "SELECT * FROM politicians WHERE party = ?";
			PreparedStatement statement = connection.prepareStatement(query);
			statement.setString(1, party);
			ResultSet rs = statement.executeQuery();
			politicians = loopRSPolitcians(rs);
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			closeConnection(connection);
		}
		return politicians;
	}

	/**
	 * Selects a specific politician.
	 * 
	 * @param PoliticianID - the id of the politician.
	 * @return - a list with a single politician.
	 */
	public LinkedList<Politician> getPolitician(String PoliticianID) {
		LinkedList<Politician> politicians = new LinkedList<Politician>();
		Connection connection = getConnection();
		try {
			String query = "SELECT * FROM politicians WHERE id = ?";
			PreparedStatement statement = connection.prepareStatement(query);
			statement.setString(1, PoliticianID);
			ResultSet rs = statement.executeQuery();
			politicians = loopRSPolitcians(rs);
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			closeConnection(connection);
		}
		return politicians;
	}

	/**
	 * Loops through ResultSet with politicians.
	 * 
	 * @param rs - the resultset to loop through
	 * @return - a list containing politicians.
	 */
	private LinkedList<Politician> loopRSPolitcians(ResultSet rs) {
		LinkedList<Politician> politicians = new LinkedList<Politician>();
		try {
			while (rs.next()) {
				Politician politician = new Politician();
				politician.setName(rs.getString("name"));
				politician.setParty(rs.getString("party"));
				politician.setFacebook_URL((rs.getString("fb_url")));
				politician.setTwitter_URL((rs.getString("twitter_url")));
				politician.setFacebookId((rs.getLong("fID")));
				politician.setTwitterId((rs.getString("tID")));
				politician.setId(rs.getInt("id"));
				politician.setProfile_url(rs.getString("profile_url"));
				politicians.add(politician);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return politicians;
	}

	/**
	 * Selects a list of all parties in database
	 * 
	 * @return - a list of parties.
	 */

	public LinkedList<Party> getParties() {
		LinkedList<Party> parties = new LinkedList<Party>();
		Connection connection = getConnection();
		try {
			String query = "SELECT * FROM parties";
			PreparedStatement statement = connection.prepareStatement(query);
			ResultSet rs = statement.executeQuery();
			while (rs.next()) {
				Party p = new Party();
				p.setName(rs.getString("name"));
				p.setNameShort(rs.getString("nameShort"));
				p.setParty_url((rs.getString("logo_url")));
				parties.add(p);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			closeConnection(connection);
		}
		return parties;
	}

	/**
	 * Selects specific party.
	 * 
	 * @param party - partty abbreviation.
	 * @return - a list of parties.
	 */
	public Party getParty(String party) {
		Connection connection = getConnection();
		Party p = new Party();
		try {
			String query = "SELECT * FROM parties WHERE nameShort = ?";
			PreparedStatement statement = connection.prepareStatement(query);
			statement.setString(1, party);
			ResultSet rs = statement.executeQuery();
			while (rs.next()) {
				p.setName(rs.getString("name"));
				p.setNameShort(rs.getString("nameShort"));
				p.setParty_url((rs.getString("logo_url")));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			closeConnection(connection);
		}
		return p;
	}

	/**
	 * Selects a list of all comments on the specified post.
	 * 
	 * @param postID - the post from which we want comments for
	 * @return - a list of comments.
	 */

	public LinkedList<Comment> getComments(int postID) {
		LinkedList<Comment> comments = new LinkedList<Comment>();
		Connection connection = getConnection();
		try {
			String query = "SELECT * FROM comments WHERE post = ? ORDER BY date AND time;";
			PreparedStatement statement = connection.prepareStatement(query);
			statement.setInt(1, postID);
			ResultSet rs = statement.executeQuery();
			while (rs.next()) {
				Comment comment = new Comment();
				comment.setText(rs.getString("text"));
				comment.setId(rs.getInt("id"));
				comment.setIp(rs.getString("ip"));
				comment.setPost(rs.getInt("post"));
				comment.setDate(rs.getDate("date"));
				comment.setTime(rs.getTime("time"));
				comments.add(comment);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			closeConnection(connection);
		}
		return comments;
	}

	/**
	 * Select the post ids from which a user liked or disliked.
	 * 
	 * @param ip - the identify of the user.
	 * @param likes - true if likes, false if dislikes.
	 * @return - a list of postIDs.
	 */
	public LinkedList<String> checkIPs(String ip, boolean likes) {
		LinkedList<String> ips = new LinkedList<String>();
		String query;
		if (likes) {
			query = "SELECT postID FROM rank WHERE ip=? AND liked=1;";
		} else {
			query = "SELECT postID FROM rank WHERE ip=? AND liked=0;";
		}
		Connection connection = getConnection();
		PreparedStatement statement;
		try {
			statement = connection.prepareStatement(query);
			statement.setString(1, ip);
			ResultSet rs = statement.executeQuery();
			while (rs.next()) {
				ips.add(rs.getString("postID"));
			}
			closeConnection(connection);
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			closeConnection(connection);
		}
		return ips;
	}

	/**
	 * Inserts a comment into the db.
	 * 
	 * @param comment - the comment to insert.
	 */

	public void addComment(Comment comment) {
		String query = "INSERT INTO comments(id, text, time, ip, post, date) VALUES (?, ?, ?, ?, ?, ?)";
		Connection connection = getConnection();
		PreparedStatement statement;
		try {
			statement = connection.prepareStatement(query);
			statement.setInt(1, comment.getID());
			statement.setString(2, comment.getText());
			statement.setTime(3, comment.getTime());
			statement.setString(4, comment.getIp());
			statement.setInt(5, comment.getPost());
			statement.setDate(6, new java.sql.Date(comment.getDate().getTime()));
			statement.executeUpdate();
			System.out.println("DBHandler: Added comment to database");
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			closeConnection(connection);
		}
	}

	/**
	 * Method that handles Likes. If it's a new Like, add it as a Row to the
	 * rank table and increase rank column by one.
	 * 
	 * @param postID - the post to like.
	 * @param ip - the user id.
	 */
	public boolean addLike(int postID, String ip) {
		String queryAddRank = "INSERT INTO rank(postID, ip, liked) VALUES (?, ?, ?)";
		String queryAddLike = "UPDATE posts SET likes=likes + 1 WHERE id = ?";
		boolean exceuteNext = true;
		Connection connection = getConnection();
		try {
			PreparedStatement statement;
			statement = connection.prepareStatement(queryAddRank);
			statement.setInt(1, postID);
			statement.setString(2, ip);
			statement.setBoolean(3, true);
			statement.executeUpdate();

		} catch (SQLException e) {
			exceuteNext = false;
			return false;
		}
		if (exceuteNext) {
			try {

				PreparedStatement statement2;
				statement2 = connection.prepareStatement(queryAddLike);
				statement2.setInt(1, postID);
				statement2.executeUpdate();

			} catch (SQLException e) {
				e.printStackTrace();
			} finally {
				closeConnection(connection);
			}
		}
		return true;

	}

	/**
	 * If Like already existsed and same resource is called again, remove the
	 * like.
	 * 
	 * @param postID - the post to like.
	 * @param ip - the user id.
	 */
	public boolean revertLike(int postID, String ip) {
		String queryAlreadyLiked = "DELETE FROM rank WHERE postID = ? AND ip = ? AND liked = ?;";
		String retractRank = "UPDATE posts SET likes=likes - 1 WHERE id = ?;";
		boolean exceuteNext = true;
		Connection connection = getConnection();
		if (checkIfRowExists(postID, ip, true)) {
			try {
				PreparedStatement statement;
				statement = connection.prepareStatement(queryAlreadyLiked);
				statement.setInt(1, postID);
				statement.setString(2, ip);
				statement.setBoolean(3, true);
				statement.executeUpdate();

			} catch (SQLException e) {
				e.printStackTrace();
			}
		} else {
			exceuteNext = false;
			return false;

		}

		if (exceuteNext) {

			try {

				PreparedStatement statement2;
				statement2 = connection.prepareStatement(retractRank);
				statement2.setInt(1, postID);
				statement2.executeUpdate();

			} catch (SQLException e) {
				e.printStackTrace();
			} finally {
				closeConnection(connection);
			}
		}
		return true;

	}

	/**
	 * Method that handles Dislikes. If it's a new disLike, add it as a Row to
	 * the rank table and decrease rank column by one.
	 * 
	 * @param postID - the post to like.
	 * @param ip - the user id.
	 */
	public boolean addDislike(int postID, String ip) {
		String queryAddRank = "INSERT INTO rank(postID, ip, liked) VALUES (?, ?, ?)";
		String queryAddLike = "UPDATE posts SET dislikes=dislikes + 1 WHERE id = ?";

		boolean exceuteNext = true;
		Connection connection = getConnection();
		try {
			PreparedStatement statement;
			statement = connection.prepareStatement(queryAddRank);
			statement.setInt(1, postID);
			statement.setString(2, ip);
			statement.setBoolean(3, false);
			statement.executeUpdate();

		} catch (SQLException e) {
			exceuteNext = false;
			return false;
		}
		if (exceuteNext) {
			try {

				PreparedStatement statement2;
				statement2 = connection.prepareStatement(queryAddLike);
				statement2.setInt(1, postID);
				statement2.executeUpdate();

			} catch (SQLException e) {
				e.printStackTrace();
			} finally {
				closeConnection(connection);
			}
		}
		return true;
	}

	/**
	 * If Dislike already existed and same resource is called again, remove the
	 * like.
	 * 
	 * @param postID - the post to like.
	 * @param ip - the user id.
	 */
	public boolean revertDislike(int postID, String ip) {
		String queryAlreadyDisliked = "DELETE FROM rank WHERE postID = ? AND ip = ? AND liked = ?;";
		String retractRank = "UPDATE posts SET dislikes=dislikes - 1 WHERE id = ?;";
		boolean exceuteNext = true;
		Connection connection = getConnection();
		if (checkIfRowExists(postID, ip, false)) {
			try {
				PreparedStatement statement;
				statement = connection.prepareStatement(queryAlreadyDisliked);
				statement.setInt(1, postID);
				statement.setString(2, ip);
				statement.setBoolean(3, false);
				statement.executeUpdate();

			} catch (SQLException e) {
				e.printStackTrace();
			}

		} else {
			exceuteNext = false;
			return false;
		}
		if (exceuteNext) {

			try {
				PreparedStatement statement2;
				statement2 = connection.prepareStatement(retractRank);
				statement2.setInt(1, postID);
				statement2.executeUpdate();

			} catch (SQLException e) {
				e.printStackTrace();
			} finally {
				closeConnection(connection);
			}

		}
		return true;

	}

	/**
	 * Generic method for adding data to database. Used by other methods in this
	 * class.
	 * 
	 * @param query - the statement to execute.
	 * @param id - id to add.
	 * @param url- the url corresponding each id.
	 */
	public void addDataDB(String query, String id, String url) {
		Connection connection = getConnection();
		PreparedStatement statement;
		try {
			statement = connection.prepareStatement(query);
			statement.setString(1, id);
			statement.setString(2, url);
			statement.executeUpdate();
			closeConnection(connection);
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			closeConnection(connection);
		}
	}

	/**
	 * Checks if a post already has been liked or disliked.
	 * 
	 * @param postID - the id of the post to check.
	 * @param ip - the user.
	 * @param liked - true if like, false if dislike.
	 * @return - true if the row exists.
	 */
	private boolean checkIfRowExists(int postID, String ip, Boolean liked) {
		String checkRow = "SELECT * FROM rank WHERE postID = ? AND ip = ? AND liked = ?;";
		boolean res = false;
		boolean exceuteNext = true;
		Connection connection = getConnection();
		try {
			PreparedStatement statement;
			statement = connection.prepareStatement(checkRow);
			statement.setInt(1, postID);
			statement.setString(2, ip);
			statement.setBoolean(3, liked);
			ResultSet rs = statement.executeQuery();
			if (rs.next()) {
				res = true;
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			closeConnection(connection);
		}
		return res;
	}
}
