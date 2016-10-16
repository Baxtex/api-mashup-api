package controller;

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

	public Controller() {
		init();
	}

	// public static void main(String[] args){
	// 		new Controller();
	// }
	
	private void init() {
		fbHandler = new FBHandler();
		twHandler = new TWHandler();
		dbHandler = new DBHandler();
		// rdHandler = new RDHandler();
		// rdHandler.registerCallback(new DBImplementer_Politicians(dbHandler));
		// rdHandler.addPoliticiansToDB();
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

	private class DBImplementer_Politicians implements ICallbackPoliticians {
		private DBHandler dbHandler;

		public DBImplementer_Politicians(DBHandler dbHandler) {
			this.dbHandler = dbHandler;
		}

		public void callbackPoliticians(LinkedList<Politician> politicians) {
			/**
			 * Ändra här för att byta vad som hämtas syso sker i klassen
			 */
			// new GoogleSearchHandler().getPoliticians_Twitter(politicians);
			new GSHandler().getPoliticians_Facebook(politicians);
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
