package controller;

import java.util.Date;
import java.util.Iterator;
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
	private final String HTTP_OK = "200";

	public Controller() {
		dbHandler = new DBHandler();
	}


	/**
	 * Should return posts from all the politicians with the specified amount
	 * from the database.
	 * 
	 * @return
	 */
	public JSONObject getAllPosts() {
		LinkedList<Post> posts = dbHandler.getAllPosts();
		JSONObject jsonObject = new JSONObject();
		JSONArray postArray = new JSONArray();
		try {
			jsonObject.put("message", MSG_OK);
			jsonObject.put("size", posts.size());
			Iterator<Post> iter = posts.iterator();
			while (iter.hasNext()) {
				Post p = (Post) iter.next();
				postArray.put(new JSONObject().put("postID", p.getID()).put("politicanID", p.getPolitican())
						.put("date", p.getDate()).put("time", p.getTime()).put("text", p.getText())
						.put("source", p.getSource())
						.put("likes", p.getLikes()).put("dislikes", p.getDislikes()));
			}
			jsonObject.put("posts", postArray);
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
	public JSONObject getAllPostsParty(String party) {
		LinkedList<Post> posts = dbHandler.getAllPostsParty(party);
		JSONObject jsonObject = new JSONObject();
		JSONArray postArray = new JSONArray();
		try {
			jsonObject.put("message", MSG_OK);
			jsonObject.put("size", posts.size());
			Iterator<Post> iter = posts.iterator();
			while (iter.hasNext()) {
				Post p = (Post) iter.next();
				postArray.put(new JSONObject().put("postID", p.getID()).put("politicanID", p.getPolitican())
						.put("date", p.getDate()).put("time", p.getTime()).put("text", p.getText())
						.put("source", p.getSource())
						.put("likes", p.getLikes()).put("dislikes", p.getDislikes()));
			}
			jsonObject.put("posts", postArray);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return jsonObject;

	}

	/**
	 * Returns data about all posts from a specified politician, formatted as a
	 * JSON object
	 * 
	 * @param id the id of the politician stored in our database
	 * @return
	 */

	public JSONObject getPostsPolitician(String id) {
		JSONObject jsonObject = new JSONObject();
		JSONArray jsonPosts = new JSONArray();
		try {
			LinkedList<Post> posts = dbHandler.getPostsPolitician(Integer.parseInt(id));
			jsonObject.put("message", MSG_OK);
			jsonObject.put("size", posts.size());
			for (int i = 0; i < posts.size(); i++) {
				Post p = posts.get(i);
				jsonPosts.put(new JSONObject().put("postID", p.getID()).put("politicanID", p.getPolitican())
						.put("date", p.getDate()).put("time", p.getTime()).put("text", p.getText())
						.put("source", p.getSource())
						.put("likes", p.getLikes()).put("dislikes", p.getDislikes()));
			}
			jsonObject.put("posts", jsonPosts);
		} catch (JSONException e) {
			e.printStackTrace();
		}
		return jsonObject;
	}

	/**
	 * Returns all politicians from db as JSONObjects.
	 * 
	 * @return
	 */
	public JSONObject getAllPoliticians() {
		JSONObject jsonObject = new JSONObject();
		JSONArray jsonPoliticians = new JSONArray();
		try {
			LinkedList<Politician> politicians = dbHandler.getPoliticians();
			jsonObject.put("message", MSG_OK);
			jsonObject.put("size", politicians.size());

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
			jsonObject.put("politicians", jsonPoliticians);
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
		JSONArray jsonPoliticians = new JSONArray();
		try {
			LinkedList<Politician> politicians = dbHandler.getPoliticiansParty(party);
			jsonObject.put("message", MSG_OK);
			jsonObject.put("size", politicians.size());
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
			jsonObject.put("politicians", jsonPoliticians);
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
		JSONObject jsonObject = new JSONObject();
		JSONArray jsonPoliticians = new JSONArray();
		try {
			Politician p = dbHandler.getPolitician(id);
			jsonObject.put("message", MSG_OK);
			if (p.getName() == null) {
				jsonObject.put("size", 0);
			} else {
				jsonObject.put("size", 1);
			}
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
			jsonObject.put("politicians", jsonPoliticians);
		} catch (JSONException e) {
			e.printStackTrace();
		}
		return jsonObject;
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
	public void postComment(int postID, String text, String email) {
		Date date = new Date();
		Comment comment = new Comment();
		comment.setDate(date);
		comment.setEmail(email);
		comment.setText(text);
		comment.setPost(postID);

		dbHandler.addComment(comment);
	}

	public boolean postLike(int postID, String email) {
		return (dbHandler.addLike(postID, email));

	}

	public boolean postDislike(int postID, String email) {
		return (dbHandler.addDislike(postID, email));

	}

	public boolean postRevertLike(int postID, String email) {
		return (dbHandler.revertLike(postID, email));

	}

	public boolean postRevertDislike(int postID, String email) {
		return (dbHandler.revertDislike(postID, email));

	}


}
