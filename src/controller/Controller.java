package controller;

import java.util.Iterator;
import java.util.LinkedList;

import org.codehaus.jettison.json.JSONArray;
import org.codehaus.jettison.json.JSONException;
import org.codehaus.jettison.json.JSONObject;

import database.DBHandler;
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
						.put("date", p.getDate()).put("text", p.getText()).put("source", p.getSource())
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
						.put("date", p.getDate()).put("text", p.getText()).put("source", p.getSource())
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
						.put("date", p.getDate()).put("text", p.getText()).put("source", p.getSource())
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

	// TODO: Fix img_url and add more data about politician in jsonPolitician
	// object

	public JSONObject getPoliticiansParty(String party) {
		JSONObject jsonObject = new JSONObject();
		JSONArray jsonPoliticians = new JSONArray();
		try {
			LinkedList<Politician> politicians = dbHandler.getPoliticians(party);
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
			System.out.println("Controller: Error while loading jsonObject with politicians from specific party");
			e.printStackTrace();
		}
		return jsonObject;
	}




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





}
