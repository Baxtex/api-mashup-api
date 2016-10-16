package externalAPIs;

import java.util.LinkedList;

import databaseObjects.Politician;

public interface ICallbackPoliticians {
	public void callbackPoliticians(LinkedList<Politician> politicians);
}
