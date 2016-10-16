package externalAPIs;

import org.codehaus.jettison.json.JSONArray;

public interface IExternalAPIs {
	JSONArray getPosts(int amount, String id);
}
