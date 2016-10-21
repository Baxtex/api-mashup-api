package externalAPIs;

import org.codehaus.jettison.json.JSONArray;

public interface IExternalAPIs {
	JSONArray getPostsJSONArray(int amount, String id);
}
