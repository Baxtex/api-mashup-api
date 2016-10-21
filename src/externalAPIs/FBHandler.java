package externalAPIs;

import org.codehaus.jettison.json.JSONArray;
import org.codehaus.jettison.json.JSONException;
import org.codehaus.jettison.json.JSONObject;

import facebook4j.Facebook;
import facebook4j.FacebookException;
import facebook4j.FacebookFactory;
import facebook4j.Post;
import facebook4j.Reading;
import facebook4j.ResponseList;
import facebook4j.conf.ConfigurationBuilder;

/**
 * Handles connection and integration to Facebook API.
 * 
 * @author Anton Gustafsson
 *
 */
public class FBHandler implements IExternalAPIs {
	private FacebookFactory ff;
	private Facebook facebook;

	/**
	 * Setup authentication for using the Facebook api.
	 */
	public FBHandler() {
		ConfigurationBuilder cb = new ConfigurationBuilder();
		cb.setDebugEnabled(true).setOAuthAppId("1781740452084874").setOAuthAppSecret("cc2f66af625b9ce52adc1990b9050dc8")
				.setOAuthAccessToken("1781740452084874|HRv3MSZw2nVUf5j8C8G6ZsERxvo")
				.setOAuthPermissions("user_posts,email,publish_stream");
		ff = new FacebookFactory(cb.build());
		facebook = ff.getInstance();
	}

	/**
	 * Retrieves posts from facebook via the facebook api.
	 * 
	 * @param amount - the number of posts to retrieve.
	 * @param id - the id of the user we want posts from.
	 * @return - JSONArray containing posts.
	 */
	public JSONArray getPostsJSONArray(int amount, String id) {
		Reading reader = new Reading().limit(amount);
		JSONArray jArray = new JSONArray();
		try {
			ResponseList<Post> feed = facebook.getPosts(id, reader);
			for (Post post : feed) {
				jArray.put(new JSONObject().put("post", post.getMessage()).put("date", post.getCreatedTime()));
			}
		} catch (FacebookException | JSONException e) {
			e.printStackTrace();
		}
		return jArray;
	}

	/**
	 * Retrieves posts from facebook via the facebook api.
	 * 
	 * @param amount - the number of posts to retrieve.
	 * @param id - the id of the user we want posts from.
	 * @return - JSONArray containing posts.
	 */
	public ResponseList<Post> getPostsList(int amount, String id) {
		Reading reader = new Reading().limit(amount);
		ResponseList<Post> feed = null;
		try {
			feed = facebook.getPosts(id, reader);
		} catch (FacebookException e) {
			e.printStackTrace();
		}
		return feed;
	}
}
