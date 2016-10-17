package controller;

import java.util.Iterator;
import java.util.LinkedList;

import database.*;
import databaseObjects.Politician;
import externalAPIs.*;
import javax.ws.rs.core.Response;

import org.codehaus.jettison.json.JSONArray;
import org.codehaus.jettison.json.JSONObject;

public class Controller {
	private DBHandler dbHandler;
	private RDHandler rdHandler;
	private FBHandler fbHandler;
	private TWHandler twHandler;
	private GSHandler gsHandler;

	public Controller() {
		init();
	}

	// public static void main(String[] args){
	// new Controller();
	// }

	private void init() {
		fbHandler = new FBHandler();
		System.out.println("fbHandler up");
		twHandler = new TWHandler();
		System.out.println("twHandler up");
		dbHandler = new DBHandler();
		System.out.println("dbHandler up");
		gsHandler = new GSHandler();
		System.out.println("gsHandler up");
		rdHandler = new RDHandler();
		System.out.println("rdHandler up");
		// rdHandler.registerCallback(new DBImplementer_Politicians(dbHandler));
		System.out.println("callback set");
		// rdHandler.addPoliticiansToDB();
		System.out.println("politicians retrieved");
	}

	/**
	 * Retrieves facebook and twitter post and mashes them up into a new
	 * JSONObject.
	 * 
	 * @return
	 */
	public JSONObject getSocialPosts(String fbId, String tId) {
		JSONArray jsonArrayFB = new JSONArray();
		JSONArray jsonArrayT = new JSONArray();
		JSONObject jsonObject = new JSONObject();
		try {
			jsonObject.put("HTTP_CODE", "200");
			jsonObject.put("MSG", "jsonArray successfully retrieved, v1");
			jsonArrayFB = fbHandler.getPosts(5, fbId);
			jsonArrayT = twHandler.getPosts(5, tId);
			jsonObject.put("fbposts", jsonArrayFB);
			jsonObject.put("twposts", jsonArrayT);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return jsonObject;
	}

	/**
	 * Retrieves post from a specific party
	 * 
	 * @param party
	 * @return
	 */
	public JSONObject getSocialPostsSpecificParty(String party) {
		JSONObject resultObject = new JSONObject();
		JSONArray pArray = new JSONArray();
		try {
			resultObject.put("HTTP_CODE", "200");
			resultObject.put("MSG", "jsonArray successfully retrieved, v1");

			LinkedList<Politician> politicians = dbHandler.getPoliticians(party);
			Iterator<Politician> iter = politicians.iterator();
			while (iter.hasNext()) {
				Politician p = (Politician) iter.next();
				pArray.put(new JSONObject().put("name", p.getName()));

				if (p.getFacebookId() != 0 && p.getTwitterId() != null) {
					pArray.put(new JSONObject().put("fbPosts",
							fbHandler.getPosts(3, String.valueOf(p.getFacebookId()).toString())));
					pArray.put(new JSONObject().put("twPosts", twHandler.getPosts(3, p.getTwitterId()).toString()));
				}

				if (p.getFacebookId() == 0 && p.getTwitterId() == null) {
					pArray.put(new JSONObject().put("fbPosts", "None"));
					pArray.put(new JSONObject().put("twPosts", "None"));
				}

				if (p.getFacebookId() != 0 && p.getTwitterId() == null) {
					pArray.put(new JSONObject().put("fbPosts",
							fbHandler.getPosts(3, String.valueOf(p.getFacebookId()).toString())));

				}
				if (p.getFacebookId() == 0 && p.getTwitterId() != null) {
					pArray.put(new JSONObject().put("twPosts", twHandler.getPosts(3, p.getTwitterId()).toString()));
				}
				resultObject.put("politicians", pArray);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return resultObject;
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

	/**
	 * Returns a JSON response containing images of all the party logos
	 * 
	 * @return
	 */

	public Response getPartyLogos() {
		return null;
	}

}
