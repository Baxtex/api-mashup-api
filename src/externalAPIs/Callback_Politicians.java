package externalAPIs;

import java.util.LinkedList;

import databaseObjects.Politician;

public interface Callback_Politicians {
	public void getPoliticians(LinkedList<Politician> politicians);
}
