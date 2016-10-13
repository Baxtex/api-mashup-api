package controller;

import java.util.LinkedList;

import database.*;
import databaseObjects.Politician;
import externalAPIs.*;
import javax.ws.rs.core.Response;

public class Controller {
	private DBHandler dbHandler;
	private Riksdagen riksdagen;

	public Controller() {

		dbHandler = new DBHandler();
		riksdagen = new Riksdagen();
		riksdagen.registerCallback(new DBImplementer_Politicians(dbHandler));
		riksdagen.addPoliticiansToDB();
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
