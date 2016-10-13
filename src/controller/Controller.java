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
	}

	
	
	
	
	
	private class DBImplementer_Politicians implements Callback_Politicians {
		private DBHandler dbHandler;
		
		public DBImplementer_Politicians(DBHandler dbHandler) {
			this.dbHandler = dbHandler;
		}

		public void callbackPoliticians(LinkedList<Politician> politicians) {
			dbHandler.addPoliticians(politicians);
		}
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
