package externalAPIs;

import java.util.LinkedList;

import databaseObjects.Politician;

public interface Callback_Politicians {
	public void callbackPoliticians(LinkedList<Politician> politicians);
}
