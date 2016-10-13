package controller;

<<<<<<< HEAD
import java.util.LinkedList;

import database.*;
import databaseObjects.Politician;
import externalAPIs.*;
=======
import javax.ws.rs.core.Response;
>>>>>>> 782bd4a28c72647504a30267b312870c18998a41

public class Controller {
	private DBHandler dbHandler;
	private Riksdagen riksdagen;

	public Controller() {

		dbHandler = new DBHandler();
		riksdagen = new Riksdagen();
		riksdagen.registerCallback(new DBImplementer_Politicians(dbHandler));
	}

	
	
	
	
	
<<<<<<< HEAD
	private class DBImplementer_Politicians implements Callback_Politicians {
		private DBHandler dbHandler;
		
		public DBImplementer_Politicians(DBHandler dbHandler) {
			this.dbHandler = dbHandler;
		}

		public void callbackPoliticians(LinkedList<Politician> politicians) {
			dbHandler.addPoliticians(politicians);
		}
	}
=======
	
	
	
	
	
	
	
	
	// Metoder som Kajsa behöver
	
	/**
	 * Returns a JSON response containing images of all the party logos
	 * @return
	 */
	
	public Response getPartyLogos() {
		return null;
	}
	
>>>>>>> 782bd4a28c72647504a30267b312870c18998a41
}
