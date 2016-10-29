package controller;

import java.sql.Time;
import java.util.Date;
import java.util.LinkedList;

import org.codehaus.jettison.json.JSONArray;
import org.codehaus.jettison.json.JSONException;
import org.codehaus.jettison.json.JSONObject;

import database.DBHandler;
import databaseObjects.Comment;
import databaseObjects.Party;
import databaseObjects.Politician;
import databaseObjects.Post;

public class Controller {
	private DBHandler dbHandler;

	private final String MSG_OK = "success";

	public Controller() {
		dbHandler = new DBHandler();
	}


	/**
	 * Should return posts from all the politicians with the specified amount
	 * from the database.
	 * 
	 * @return
	 */
	public JSONObject getAllPosts(String dateStr) {
		LinkedList<Post> posts = dbHandler.getAllPosts(dateStr);
		JSONObject jsonObject = new JSONObject();
		try {
			jsonObject.put("message", MSG_OK);
			jsonObject.put("size", posts.size());
			jsonObject.put("posts", loopPosts(posts));
		} catch (Exception e) {
			e.printStackTrace();
		}
		return jsonObject;

	}

	/**
	 * Returns 1 post from every politican in specific party.
	 * 
	 * @return
	 */
	public JSONObject getAllPostsParty(String party, String dateStr) {
		LinkedList<Post> posts = dbHandler.getAllPostsParty(party, dateStr);
		JSONObject jsonObject = new JSONObject();
		try {
			jsonObject.put("message", MSG_OK);
			jsonObject.put("size", posts.size());
			jsonObject.put("posts", loopPosts(posts));
		} catch (Exception e) {
			e.printStackTrace();
		}
		return jsonObject;

	}

	/**
	 * Returns data about all posts from a specified politician, formatted as a
	 * JSON object.
	 * 
	 * @param id the id of the politician stored in our database
	 * @return
	 */
	public JSONObject getPostsPolitician(String id) {
		LinkedList<Post> posts = dbHandler.getPostsPolitician(Integer.parseInt(id));
		JSONObject jsonObject = new JSONObject();
		try {
			jsonObject.put("message", MSG_OK);
			jsonObject.put("size", posts.size());
			jsonObject.put("posts", loopPosts(posts));
		} catch (JSONException e) {
			e.printStackTrace();
		}
		return jsonObject;
	}

	/**
	 * Returns data about all posts from a specified politician, formatted as a
	 * JSON object in a specific date
	 * 
	 * @param id the id of the politician stored in our database
	 * @return
	 */

	public JSONObject getPostsPolitician(String id, String dateStr) {
		LinkedList<Post> posts = dbHandler.getPostsPolitician(Integer.parseInt(id), dateStr);
		JSONObject jsonObject = new JSONObject();
		try {
			jsonObject.put("message", MSG_OK);
			jsonObject.put("size", posts.size());
			jsonObject.put("posts", loopPosts(posts));
		} catch (JSONException e) {
			e.printStackTrace();
		}
		return jsonObject;
	}
	
	/**
	 * Returns a JSONObject containing the 20 most up voted posts
	 * @return JSONObject containing the 20 most up voted posts
	 */
	
	public JSONObject getPostsMostUpvoted() {
		LinkedList<Post> posts = dbHandler.getPostsMostUpvoted();
		JSONObject jsonObject = new JSONObject();
		try {
			jsonObject.put("message", MSG_OK);
			jsonObject.put("size", posts.size());
			jsonObject.put("posts", loopPosts(posts));
		} catch (JSONException e) {
			e.printStackTrace();
		}
		return jsonObject;
	}
	
	/**
	 * Returns a JSONObject containing the 20 most down voted posts
	 * @return JSONObject containing the 20 most down voted posts
	 */
	
	public JSONObject getPostsMostDownvoted() {
		LinkedList<Post> posts = dbHandler.getPostsMostDownvoted();
		JSONObject jsonObject = new JSONObject();
		try {
			jsonObject.put("message", MSG_OK);
			jsonObject.put("size", posts.size());
			jsonObject.put("posts", loopPosts(posts));
		} catch (JSONException e) {
			e.printStackTrace();
		}
		return jsonObject;
	}

	

	/*
	 * Returns a specific post with postID.
	 * 
	 */
	public JSONObject getSpecificPost(int postID) {
		LinkedList<Post> posts = dbHandler.getSpecificPost(postID);
		JSONObject jsonObject = new JSONObject();
		try {
			jsonObject.put("message", MSG_OK);
			jsonObject.put("size", posts.size());
			jsonObject.put("posts", loopPosts(posts));
		} catch (JSONException e) {
			e.printStackTrace();
		}
		return jsonObject;
	}

	/**
	 * Loops through a list of posts and puts them in a jsonArray.
	 * 
	 * @param posts
	 * @return
	 */
	private JSONArray loopPosts(LinkedList<Post> posts) {
		JSONArray jsonPosts = new JSONArray();
		for (int i = 0; i < posts.size(); i++) {
			Post p = posts.get(i);
			try {
				jsonPosts.put(new JSONObject().put("postID", p.getID()).put("politicanID", p.getPolitican())
						.put("date", p.getDate()).put("time", p.getTime()).put("text", p.getText())
						.put("source", p.getSource()).put("likes", p.getLikes()).put("dislikes", p.getDislikes()));
			} catch (JSONException e) {
				e.printStackTrace();
			}
		}

		return jsonPosts;

	}
	/**
	 * Returns all politicians from db as JSONObjects.
	 * 
	 * @return
	 */
	public JSONObject getAllPoliticians() {
		JSONObject jsonObject = new JSONObject();
		try {
			LinkedList<Politician> politicians = dbHandler.getPoliticians();
			jsonObject.put("message", MSG_OK);
			jsonObject.put("size", politicians.size());
			jsonObject.put("politicians", loopPoliticians(politicians));
		} catch (JSONException e) {
			e.printStackTrace();
		}
		return jsonObject;
	}

	/**
	 * Returns data about all politicians from a specified party, formatted as a
	 * JSON object
	 * 
	 * @param party the short name of the party, f.e. "s"
	 * @return
	 */

	public JSONObject getPoliticiansParty(String party) {
		JSONObject jsonObject = new JSONObject();
		try {
			LinkedList<Politician> politicians = dbHandler.getPoliticiansParty(party);
			jsonObject.put("message", MSG_OK);
			jsonObject.put("size", politicians.size());
			jsonObject.put("politicians", loopPoliticians(politicians));
		} catch (JSONException e) {
			e.printStackTrace();
		}
		return jsonObject;
	}

	/**
	 * Return specific politician as JSONObject
	 * 
	 * @param id
	 * @return
	 */
	public JSONObject getPolitician(String id) {
		LinkedList<Politician> politicians = dbHandler.getPolitician(id);
		JSONObject jsonObject = new JSONObject();
		try {
			jsonObject.put("message", MSG_OK);
			jsonObject.put("size", politicians.size());
			jsonObject.put("politicians", loopPoliticians(politicians));
		} catch (JSONException e) {
			e.printStackTrace();
		}
		return jsonObject;
	}

	private JSONArray loopPoliticians(LinkedList<Politician> politicians) {

		JSONArray jsonPoliticians = new JSONArray();
		try {
			for (int i = 0; i < politicians.size(); i++) {
				Politician p = politicians.get(i);
				JSONObject jsonPolitician = new JSONObject();
				jsonPolitician.put("id", p.getId());
				jsonPolitician.put("name", p.getName());
				jsonPolitician.put("party", p.getParty());
				jsonPolitician.put("fb_url", p.getFacebook_URL());
				jsonPolitician.put("twitter_url", p.getTwitter_URL());
				jsonPolitician.put("fID", p.getFacebookId());
				jsonPolitician.put("tID", p.getTwitterId());
				jsonPolitician.put("profile_url", p.getProfile_url());
				jsonPoliticians.put(jsonPolitician);
			}
		} catch (JSONException e) {
			e.printStackTrace();
		}

		return jsonPoliticians;

	}

	/**
	 * Returns all parties as JSONObject.
	 * 
	 * @return
	 */
	public JSONObject getParties() {
		JSONObject jsonObject = new JSONObject();
		JSONArray jsonParties = new JSONArray();
		try {
			LinkedList<Party> parties = dbHandler.getParties();
			jsonObject.put("message", MSG_OK);
			jsonObject.put("size", parties.size());
			for (int i = 0; i < parties.size(); i++) {
				Party p = parties.get(i);
				JSONObject jsonParty = new JSONObject();
				jsonParty.put("name", p.getName());
				jsonParty.put("abbrev", p.getNameShort());
				jsonParty.put("logo_url", p.getParty_url());
				jsonParties.put(jsonParty);
			}
			jsonObject.put("parties", jsonParties);
		} catch (JSONException e) {
			e.printStackTrace();
		}
		return jsonObject;
	}

	/**
	 * Returns specific party as JSONObject.
	 * 
	 * @param party
	 * @return
	 */
	public JSONObject getParty(String party) {

		JSONObject jsonObject = new JSONObject();
		try {
			Party p = dbHandler.getParty(party);
			jsonObject.put("message", MSG_OK);
			if (p.getName() == null) {
				jsonObject.put("size", 0);
			} else {

				jsonObject.put("size", 1);
			}
			jsonObject.put("name", p.getName());
			jsonObject.put("abbrev", p.getNameShort());
			jsonObject.put("logo_url", p.getParty_url());
		} catch (JSONException e) {
			e.printStackTrace();
		}

		return jsonObject;
	}

	/**
	 * Retrieves comments for a specific post.
	 * 
	 * @return
	 */
	public JSONObject getComments(int postID) {

		JSONObject jsonObject = new JSONObject();
		JSONArray jsonComments = new JSONArray();
		try {
			LinkedList<Comment> comments = dbHandler.getComments(postID);
			jsonObject.put("message", MSG_OK);
			jsonObject.put("size", comments.size());
			for (int i = 0; i < comments.size(); i++) {
				Comment c = comments.get(i);
				JSONObject jsonComment = new JSONObject();
				jsonComment.put("id", c.getID());
				jsonComment.put("text", c.getText());
				jsonComment.put("email", c.getEmail());
				jsonComment.put("post", c.getPost());
				jsonComment.put("date", c.getDate());
				jsonComment.put("time", c.getTime());
				jsonComments.put(jsonComment);
			}
			jsonObject.put("comments", jsonComments);
		} catch (JSONException e) {
			e.printStackTrace();
		}
		return jsonObject;
	}


	/**
	 * Used when posting a comment.
	 * 
	 * @param text
	 * @param postID
	 */
	public void postComment(int postID, String text, String ip) {
		Date date = new Date();
		Time time = new Time(date.getTime());
		Comment comment = new Comment();
		comment.setDate(date);
		comment.setIp(ip);
		comment.setText(text);
		comment.setPost(postID);
		comment.setTime(time);

		dbHandler.addComment(comment);
	}

	public boolean postLike(int postID, String ip) {
		return (dbHandler.addLike(postID, ip));

	}

	public boolean postDislike(int postID, String ip) {
		return (dbHandler.addDislike(postID, ip));

	}

	public boolean postRevertLike(int postID, String ip) {
		return (dbHandler.revertLike(postID, ip));

	}

	public boolean postRevertDislike(int postID, String ip) {
		return (dbHandler.revertDislike(postID, ip));

	}


	public JSONObject checkIPs(String ip, boolean likes) {
		LinkedList<String> ips = dbHandler.checkIPs(ip, likes);
		JSONObject jsonObject = new JSONObject();
		JSONArray jsonParties = new JSONArray();
		try {
			jsonObject.put("message", MSG_OK);
			jsonObject.put("size", ips.size());
			for (int i = 0; i < ips.size(); i++) {
				JSONObject jsonParty = new JSONObject();
				jsonParty.put("postID", ips.get(i));
				jsonParties.put(jsonParty);
			}
			jsonObject.put("postids", jsonParties);
		} catch (JSONException e) {
			e.printStackTrace();
		}
		return jsonObject;

	}

}
