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

/**
 * Controller as part of MVC.
 * 
 * @author Anton Gustafsson
 *
 */

public class Controller {
	private DBHandler dbHandler;
	private final String MSG_OK = "success";

	public Controller() {
		dbHandler = new DBHandler();
	}

	/**
	 * Builds JSONObject from a list of posts.
	 * 
	 * @param dateStr - date as a string from the user.
	 * @return - Complete JSONObject with posts.
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
	 * Builds JSONObject from a list of posts.
	 * 
	 * @param party - party abbreviation.
	 * @param dateStr - date as a string from the user.
	 * @return - Complete JSONObject with posts.
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
	 * Builds JSONObject from a list of posts.
	 * 
	 * @param id - politician id.
	 * @return - Complete JSONObject with posts.
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
	 * Builds JSONObject from a list of posts.
	 * 
	 * @param id - politician id.
	 * @param dateStr - date as a string from the user.
	 * @return - Complete JSONObject with posts.
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
	 * Builds a JSONObject containing the 20 most up voted posts
	 * 
	 * @return - Complete JSONObject with posts.
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
	 * Builds a JSONObject containing the 20 most down voted posts
	 * 
	 * @return - Complete JSONObject with posts.
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

	

	/**
	 * Builds JSONObject from a list of posts.
	 * 
	 * @param id - post id.
	 * @return - Complete JSONObject with posts.
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
	 * @return - jsonarray of posts
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
	 * Builds JSONObject from a list of politicians
	 * 
	 * @return - Complete JSONObject with politicians.
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
	 * Builds JSONObject from a list of politicians
	 * 
	 * @param party - the party from which to return politicians from
	 * @return - Complete JSONObject with politicians.
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
	 * Builds JSONObject from a list of politicians
	 * 
	 * @param id - id of the politcian to return.
	 * @return - Complete JSONObject with a politician.
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

	/**
	 * Loops through a list of politicians and puts them in a JSONArray.
	 * 
	 * @param politicians - a list of politicians.
	 * @return - JSONArray with politicians.
	 */
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
				jsonPolitician.put("profile_url", p.getProfileURL());
				jsonPoliticians.put(jsonPolitician);
			}
		} catch (JSONException e) {
			e.printStackTrace();
		}
		return jsonPoliticians;
	}

	/**
	 * Builds JSONObject from a list of parties
	 * 
	 * @return - Complete JSONObject with all parties.
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
	 * Builds JSONObject from a list of parties
	 * 
	 * @param party - party abbreviation.
	 * @return - Complete JSONObject with specfic parties.
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
	 * Builds JSONObject from a list of comments
	 * 
	 * @param postID - the post to get comments for.
	 * @return - Complete JSONObject with all comments for specific post.
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
	 * Creates a new Comment
	 * 
	 * @param postID - to which post the comment belongs to.
	 * @param text - the text in the comment.
	 * @param ip - the ip address of the commentor.
	 */
	public void postComment(int postID, String text, String alias) {
		Date date = new Date();
		Time time = new Time(date.getTime());
		Comment comment = new Comment();
		comment.setDate(date);
		comment.setAlias(alias);
		comment.setText(text);
		comment.setPost(postID);
		comment.setTime(time);
		dbHandler.addComment(comment);
	}

	/**
	 * Calls on the dbHandler to post like.
	 * 
	 * @param postID - the post to like.
	 * @param ip - the ip behind the like.
	 * @return - true if insertion went well.
	 */
	public boolean postLike(int postID, String ip) {
		return (dbHandler.addLike(postID, ip));

	}

	/**
	 * Calls on the dbHandler to post dislike.
	 * 
	 * @param postID - the post to dislike.
	 * @param ip - the ip behind the dislike.
	 * @return - true if insertion went well.
	 */
	public boolean postDislike(int postID, String ip) {
		return (dbHandler.addDislike(postID, ip));

	}

	/**
	 * Calls on the dbHandler to revert like.
	 * 
	 * @param postID - the post to revert like.
	 * @param ip - the ip behind the revert like.
	 * @return - true if revert went well.
	 */
	public boolean postRevertLike(int postID, String ip) {
		return (dbHandler.revertLike(postID, ip));

	}

	/**
	 * Calls on the dbHandler to revert dislike.
	 * 
	 * @param postID - the post to revert dislike.
	 * @param ip - the ip behind the revert dislike.
	 * @return - true if revert went well.
	 */
	public boolean postRevertDislike(int postID, String ip) {
		return (dbHandler.revertDislike(postID, ip));

	}

	/**
	 * Builds JSONObject from a list of posts from a certain ip liked or
	 * disliked.
	 * 
	 * @param ip - the ip we want to check
	 * @param likes - true if we want to check liked post, otherwise false.
	 * @return - Complete JSONObject with posts that a ip liked or disliked.
	 */
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
