package externalAPIs;

import org.codehaus.jettison.json.JSONArray;

public interface ExternalAPIs {
	JSONArray getPosts(int amount, String id);
}
