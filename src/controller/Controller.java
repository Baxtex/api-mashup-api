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
import externalAPIs.FBHandler;
import externalAPIs.GSHandler;
import externalAPIs.ICallbackPoliticians;
import externalAPIs.RDHandler;
import externalAPIs.TWHandler;

public class Controller {
	private DBHandler dbHandler;
	private RDHandler rdHandler;
	private FBHandler fbHandler;
	private TWHandler twHandler;
	private GSHandler gsHandler;

	private final String MSG_OK = "success";
	private final String HTTP_OK = "200";

	public Controller() {
		init();
	}

	private void init() {
		fbHandler = new FBHandler();
		twHandler = new TWHandler();
		dbHandler = new DBHandler();
		gsHandler = new GSHandler();
		rdHandler = new RDHandler();
		// rdHandler.registerCallback(new DBImplementer_Politicians(dbHandler));
		// rdHandler.addPoliticiansToDB();
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
			jsonObject.put("HTTP_CODE", HTTP_OK);
			jsonObject.put("MSG", "jsonArray successfully retrieved, v1");
			Iterator<Post> iter = posts.iterator();
			while (iter.hasNext()) {
				Post p = (Post) iter.next();
				postArray.put(new JSONObject().put("postID", p.getID()).put("politicanID", p.getPolitican())
						.put("date", p.getDate()).put("time", p.getTime()).put("text", p.getText())
						.put("source", p.getSource())
						.put("rank", p.getRank()));
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
		System.out.println(posts);
		JSONObject jsonObject = new JSONObject();
		JSONArray postArray = new JSONArray();
		// JSONObject jsonObject = new JSONObject();
		try {
			jsonObject.put("HTTP_CODE", HTTP_OK);
			jsonObject.put("MSG", "jsonArray successfully retrieved, v1");
			Iterator<Post> iter = posts.iterator();
			while (iter.hasNext()) {
				Post p = (Post) iter.next();
				postArray.put(new JSONObject().put("postID", p.getID()).put("politicanID", p.getPolitican())
						.put("date", p.getDate()).put("time", p.getTime()).put("text", p.getText())
						.put("source", p.getSource())
						.put("rank", p.getRank()));
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
			jsonObject.put("HTTP_CODE", HTTP_OK);
			jsonObject.put("message", MSG_OK);
			jsonObject.put("size", posts.size());
			for (int i = 0; i < posts.size(); i++) {
				Post p = posts.get(i);
				jsonPosts.put(new JSONObject().put("postID", p.getID()).put("politicanID", p.getPolitican())
						.put("date", p.getDate()).put("time", p.getTime()).put("text", p.getText())
						.put("source", p.getSource())
						.put("rank", p.getRank()));
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
			jsonObject.put("HTTP_CODE", HTTP_OK);
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
			jsonObject.put("HTTP_CODE", HTTP_OK);
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
			jsonObject.put("HTTP_CODE", HTTP_OK);
			jsonObject.put("message", MSG_OK);
			jsonObject.put("size", 1);
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
			jsonObject.put("HTTP_CODE", HTTP_OK);
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
		JSONArray jsonParties = new JSONArray();
		try {
			Party p = dbHandler.getParty(party);
			JSONObject jsonParty = new JSONObject();
			jsonObject.put("HTTP_CODE", HTTP_OK);
			jsonObject.put("message", MSG_OK);
			jsonObject.put("size", 1);
			jsonParty.put("name", p.getName());
			jsonParty.put("abbrev", p.getNameShort());
			jsonParty.put("logo_url", p.getParty_url());
			jsonParties.put(jsonParty);
			jsonObject.put("parties", jsonParties);
		} catch (JSONException e) {
			e.printStackTrace();
		}

		return jsonObject;
	}

	/**
	 * Don't know what this stuff is.
	 * 
	 * @author Anton Gustafsson
	 *
	 */
	private class DBImplementer_Politicians implements ICallbackPoliticians {
		private DBHandler dbHandler;

		public DBImplementer_Politicians(DBHandler dbHandler) {
			this.dbHandler = dbHandler;
		}

		public void callbackPoliticians(LinkedList<Politician> politicians) {

			dbHandler.addPoliticians(gsHandler.getPoliticians_SocialMedia(politicians));
			System.out.println("Politicians added to Database");
		}
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

	public void postLike(int postID, String email) {
		dbHandler.addLike(postID, email);

	}

	public void postDislike(int postID, String email) {
		dbHandler.addDislike(postID, email);

	}

}
