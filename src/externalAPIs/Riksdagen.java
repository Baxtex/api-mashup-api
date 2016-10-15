package externalAPIs;

import java.util.LinkedList;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;

import org.codehaus.jettison.json.JSONArray;
import org.codehaus.jettison.json.JSONException;
import org.codehaus.jettison.json.JSONObject;

//import databaseObjects.AbbreviationTable;
import databaseObjects.Politician;

public class Riksdagen {

	private final String URL = "http://data.riksdagen.se/personlista/?fnamn=&enamn=&parti=&utformat=json&charset=UTF-8";
	private Callback_Politicians callback;

	public Riksdagen() {

	}

	public void registerCallback(Callback_Politicians callback) {
		this.callback = callback;
	}

	public void addPoliticiansToDB() {
		WebTarget target = getWebTarget();
		callback.callbackPoliticians(
				convertFromJSON(target.request(MediaType.APPLICATION_JSON).get(String.class).toString()));
	}

	private LinkedList<Politician> convertFromJSON(String response) {
		LinkedList<Politician> tempList = new LinkedList<Politician>();
		try {
			JSONObject jsonResponse = new JSONObject(response);

			JSONObject search = (JSONObject) jsonResponse.get("personlista");
			JSONArray entryArray = (JSONArray) search.get("person");

			for (int i = 0; i < entryArray.length(); i++) {
				JSONObject tempObj = entryArray.getJSONObject(i);
				tempList.add(
						new Politician((String) tempObj.get("tilltalsnamn") + " " + (String) tempObj.get("efternamn"),
								(String) tempObj.get("parti")));
			}
		} catch (JSONException e) {
			e.printStackTrace();
		}
		return tempList;

	}

	private WebTarget getWebTarget() {
		Client client = ClientBuilder.newClient();
		WebTarget target = client.target(URL);
		return target;
	}

	// public LinkedList<Politician> getPoliticians(){
	// return politicians;
	// }

}
