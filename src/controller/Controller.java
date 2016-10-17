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
	// 		new Controller();
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
//		rdHandler.registerCallback(new DBImplementer_Politicians(dbHandler));
		System.out.println("callback set");
//		rdHandler.addPoliticiansToDB();
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
	 * @param party
	 * @return
	 */
	public JSONObject getSocialPostsSpecificParty(String party) {
		JSONArray jsonArrayFB = new JSONArray();
		JSONArray jsonArrayT = new JSONArray();
		JSONArray jsonArray = new JSONArray();
		JSONObject jsonObject = new JSONObject();
		try {
			jsonObject.put("HTTP_CODE", "200");
			jsonObject.put("MSG", "jsonArray successfully retrieved, v1");
			
			LinkedList<Politician> politicians = dbHandler.getPoliticians(party);
			Iterator iter = politicians.iterator();
			while(iter.hasNext()){
				Politician p = (Politician) iter.next();
				System.out.println("HUHUH " + String.valueOf(p.getFacebookId()));
				System.out.println("KORV " + p.getTwitterId());
				System.out.println("KORV " + p.getName());
				jsonArrayFB = fbHandler.getPosts(3, String.valueOf(p.getFacebookId())); //FB
				jsonArrayT = twHandler.getPosts(3, p.getTwitterId()); //twitter
				
				jsonArray.put(jsonArrayFB);
				jsonArray.put(jsonArrayT);
				
				jsonObject.put("name", p.getName());
				jsonObject.put("posts", jsonArray);
			}
		} catch (Exception e) {
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
	/**
	 * Returns a JSON response containing images of all the party logos
	 * 
	 * @return
	 */
	
	public Response getPartyLogos() {
		return null;
	}

}
