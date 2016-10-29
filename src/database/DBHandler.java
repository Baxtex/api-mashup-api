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

public class DBHandler {
	// private final String DRIVER = "com.mysql.jdbc.Driver";
	private final String DRIVER = "com.mysql.cj.jdbc.Driver";
	private final String URL = "jdbc:mysql://195.178.232.16:3306/AB7455";
	private final String USER = "AB7455";
	private final String PASSWORD = "kajsaecool";
	private final DateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");

	/**
	 * Opens connection to database
	 * 
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
	 * 
	 * @param connection
	 */

	public void closeConnection(Connection connection) {
		if (connection != null) {
			try {
				connection.close();
			} catch (SQLException e) {
				System.out.println("DBHandler: Exception trying to close the connection");
			}
		}
	}

	// Get methods

	/**
	 * Returns a list of at least 1 post from every politican in the database.
	 * database.
	 * 
	 * @param politican
	 * @return
	 */

	public LinkedList<Post> getAllPosts(String dateStr) {
		LinkedList<Post> posts = new LinkedList<Post>();
		Connection connection = getConnection();
		try {
			Date date = formatter.parse(dateStr);
			String query = "SELECT id,text,politican,source,date,time, likes, dislikes FROM posts  WHERE date=? group by time;";
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
	 * Return 1 post from all politicans in specific party
	 * 
	 * @param party
	 * @return
	 */
	public LinkedList<Post> getAllPostsParty(String party, String dateStr) {

		LinkedList<Post> posts = new LinkedList<Post>();
		Connection connection = getConnection();
		try {
			Date date = formatter.parse(dateStr);
			String query = "SELECT * FROM posts WHERE politican in(select politicians.id from politicians where politicians.party = ?) AND posts.date = ?  group by time;";
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
	 * Returns a list of all posts from the specified politican in the db.
	 * 
	 * @param politican
	 * @return
	 */

	public LinkedList<Post> getPostsPolitician(int politican) {
		LinkedList<Post> posts = new LinkedList<Post>();
		Connection connection = getConnection();
		try {
			String query = "SELECT * FROM posts WHERE politican = ? group by time;";
			PreparedStatement statement = connection.prepareStatement(query);
			statement.setInt(1, politican);
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
	 * Returns a list of all posts from the specified politican in the db.
	 * 
	 * @param politican
	 * @return
	 */

	public LinkedList<Post> getPostsPolitician(int politican, String dateStr) {
		LinkedList<Post> posts = new LinkedList<Post>();
		Connection connection = getConnection();
		try {
			Date date = formatter.parse(dateStr);
			String query = "SELECT * FROM posts WHERE politican = ? AND date = ? group by time;";
			PreparedStatement statement = connection.prepareStatement(query);
			statement.setInt(1, politican);
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
	 * Returns a list of the 20 most up voted posts
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
	 * Returns a list of the 20 most down voted posts
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
	 * Returns specific post.
	 * 
	 * @param postID
	 * @return
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
	 * @return
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
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return posts;
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
	 * Returns a list of all politicians in the database from a specific party
	 * 
	 * @return
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
	 * Returns a specific politician from the database.
	 * 
	 * @param id
	 * @return
	 */
	public LinkedList<Politician> getPolitician(String id) {
		LinkedList<Politician> politicians = new LinkedList<Politician>();
		Connection connection = getConnection();
		try {
			String query = "SELECT * FROM politicians WHERE id = ?";
			PreparedStatement statement = connection.prepareStatement(query);
			statement.setString(1, id);
			ResultSet rs = statement.executeQuery();
			politicians = loopRSPolitcians(rs);
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			closeConnection(connection);
		}

		return politicians;
	}

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
	 * Retrieves all parties from the database.
	 * 
	 * @param party
	 * @return
	 */
	public Politician getParties(String party) {
		Politician politician = new Politician();
		Connection connection = getConnection();
		try {
			String query = "SELECT * FROM politicians WHERE id = ?";
			PreparedStatement statement = connection.prepareStatement(query);
			statement.setString(1, party);
			ResultSet rs = statement.executeQuery();
			while (rs.next()) {
				politician.setName(rs.getString("name"));
				politician.setParty(rs.getString("party"));
				politician.setFacebook_URL((rs.getString("fb_url")));
				politician.setTwitter_URL((rs.getString("twitter_url")));
				politician.setFacebookId((rs.getLong("fID")));
				politician.setTwitterId((rs.getString("tID")));
				politician.setId(rs.getInt("id"));
				politician.setProfile_url(rs.getString("profile_url"));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			closeConnection(connection);
		}

		return politician;
	}

	/**
	 * Returns a list of all parties in database
	 * 
	 * @return
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
	 * Retrives specific party from database.
	 * 
	 * @param party
	 * @return
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
	 * Returns a list of all comments on the specified post
	 * 
	 * @param post
	 * @return
	 */

	public LinkedList<Comment> getComments(int postID) {
		LinkedList<Comment> comments = new LinkedList<Comment>();
		Connection connection = getConnection();
		try {
			String query = "SELECT * FROM comments WHERE post = ? GROUP BY date AND time;";
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

	public LinkedList<String> checkIPs(String ip, boolean likes) {
		LinkedList<String> ips = new LinkedList<String>();
		System.out.println("Checking id");
		String query;
		if(likes){
			query = "SELECT postID FROM rank WHERE ip=? AND liked=1;";
		}else{
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
	// ------------------------------------------------------------------------------------
	// Insert methods

	/**
	 * Inserts a post into the database.
	 * 
	 * @param post
	 */

	public void addPost(Post post) {
		String query = "INSERT INTO posts (text, politican, source, date, time, likes, dislikes) VALUES (?, ?, ?, ?, ?, ?, ?)";
		Connection connection = getConnection();
		PreparedStatement statement = null;
		try {
			statement = connection.prepareStatement(query);
			statement.setString(1, post.getText());
			statement.setInt(2, post.getPolitican());
			statement.setString(3, post.getSource());
			statement.setDate(4, new java.sql.Date(post.getDate().getTime()));
			statement.setTime(5, post.getTime());
			statement.setInt(6, post.getLikes());
			statement.setInt(7, post.getDislikes());
			statement.executeUpdate();
			System.out.println("DBHandler: Added post to database");
		} catch (SQLException e) {
			System.out.print("DBHandler: Exception when adding post to database");
		} finally {
			closeConnection(connection);
		}
	}

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
			// closeConnection(connection);
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
				// closeConnection(connection);
				System.out.println("DBHandler: Added a list of politicians to database");
			}
		} catch (SQLException e) {
			System.out.println("DBHandler: Exception when trying to add a list of politicians");
		} finally {
			closeConnection(connection);
		}
		// closeConnection(connection);
	}

	/**
	 * Adds a new comment to the database
	 * 
	 * @param comment
	 */

	public void addComment(Comment comment) {
		String query = "insert into comments(id, text, time, ip, post, date) VALUES (?, ?, ?, ?, ?, ?)";
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
	 * @param postID
	 * @param email
	 */
	public boolean addLike(int postID, String ip) {
		System.out.println("ADD LIKE CALLED");
		String queryAddRank = "insert into rank(postID, ip, liked) VALUES (?, ?, ?)";
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
			System.out.println("DBHandler: Added Rank to database with LIKE");

		} catch (SQLException e) {
			exceuteNext = false;
			System.out.println("Already liked!");
			return false;
		}
		if (exceuteNext) {
			try {

				PreparedStatement statement2;
				statement2 = connection.prepareStatement(queryAddLike);
				statement2.setInt(1, postID);
				statement2.executeUpdate();
				System.out.println("DBHandler: Added Like to post");

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
	 * @param postID
	 * @param email
	 */
	public boolean revertLike(int postID, String ip) {
		System.out.println("REVERT LIKE CALLED");
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
				System.out.println("DBHandler: Deleted like row");
				
			} catch (SQLException e) {
				e.printStackTrace();
			}
		} else {
			exceuteNext = false;
			System.out.println("DBHandler: Cannot delete non existing like");
			return false;

		}

		if (exceuteNext) {

			try {

				PreparedStatement statement2;
				statement2 = connection.prepareStatement(retractRank);
				statement2.setInt(1, postID);
				statement2.executeUpdate();
				System.out.println("DBHandler: removed like from post");

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
	 * @param postID
	 * @param email
	 */
	public boolean addDislike(int postID, String ip) {
		System.out.println("ADD DISLIKE CALLED");
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
			System.out.println("DBHandler: Added Rank to database With DISLIKE");

		} catch (SQLException e) {
			exceuteNext = false;
			System.out.println("Already disliked!");
			return false;
		}
		if (exceuteNext) {
			try {

				PreparedStatement statement2;
				statement2 = connection.prepareStatement(queryAddLike);
				statement2.setInt(1, postID);
				statement2.executeUpdate();
				System.out.println("DBHandler: Added dislike to post");

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
	 * @param postID
	 * @param email
	 */
	public boolean revertDislike(int postID, String ip) {
		System.out.println("REVERTDISLIKE CALLED");
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
				System.out.println("DBHandler: Deleted dislike row");

			} catch (SQLException e) {
				e.printStackTrace();
			}

		} else {
			exceuteNext = false;
			System.out.println("DBHandler: Cannot delete non existing dislike");
			return false;
		}
		if (exceuteNext) {

			try {
				PreparedStatement statement2;
				statement2 = connection.prepareStatement(retractRank);
				statement2.setInt(1, postID);
				statement2.executeUpdate();
				System.out.println("DBHandler: removed dislike from post");

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
			System.out.println("DBHandler: Added data to db.");
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			closeConnection(connection);
		}
	}


	private boolean checkIfRowExists(int postID, String ip, Boolean rank) {
		System.out.println("INSIDE ROWEXISTS");
		String checkRow = "SELECT * FROM rank WHERE postID = ? AND ip = ? AND liked = ?;";
		boolean res = false;
		boolean exceuteNext = true;
		Connection connection = getConnection();
		try {
			PreparedStatement statement;
			statement = connection.prepareStatement(checkRow);
			statement.setInt(1, postID);
			statement.setString(2, ip);
			statement.setBoolean(3, rank);
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
