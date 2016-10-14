package controller;

import java.util.LinkedList;

import database.*;
import databaseObjects.Politician;
import externalAPIs.*;
import javax.ws.rs.core.Response;

import org.codehaus.jettison.json.JSONArray;
import org.codehaus.jettison.json.JSONObject;

import api.API;

public class Controller {
	private DBHandler dbHandler;
	private Riksdagen riksdagen;
	private FBHandler fbHandler;
	private TWHandler twHandler;

	public Controller() {
		init();
	}
	
	private void init(){
		fbHandler = new FBHandler();
		twHandler = new TWHandler();
		dbHandler = new DBHandler();
		riksdagen = new Riksdagen();
		riksdagen.registerCallback(new DBImplementer_Politicians(dbHandler));
		riksdagen.addPoliticiansToDB();
	}

	/**
	 * Retrieves facebook and twitter post and mashes them up
	 * into a new JSONObject.
	 * @return
	 */
	public JSONObject getSocialPosts(String fbId, String tId){
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
	
	
	
	
	
	
	private class DBImplementer_Politicians implements Callback_Politicians {
		private DBHandler dbHandler;
		
		public DBImplementer_Politicians(DBHandler dbHandler) {
			this.dbHandler = dbHandler;
		}

		public void callbackPoliticians(LinkedList<Politician> politicians) {
			/**
			 * Ändra här för att byta vad som hämtas
			 * syso sker i klassen
			 */
//			new GoogleSearchHandler().getPoliticians_Twitter(politicians);
			new GoogleSearchHandler().getPoliticians_Facebook(politicians);
		}
	}
	
	public static void main(String[] args){
		new Controller();
	}
	
	
	
	
	
	
	
	
	// Metoder som Kajsa beh�ver
	
	/**
	 * Returns a JSON response containing images of all the party logos
	 * @return
	 */
	
	public Response getPartyLogos() {
		return null;
	}
	
}
