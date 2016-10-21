package externalAPIs;

import java.util.List;

import org.codehaus.jettison.json.JSONArray;
import org.codehaus.jettison.json.JSONException;
import org.codehaus.jettison.json.JSONObject;

import twitter4j.Paging;
import twitter4j.Status;
import twitter4j.Twitter;
import twitter4j.TwitterException;
import twitter4j.TwitterFactory;
import twitter4j.conf.ConfigurationBuilder;

/**
 * Handles connection and integration with the twitter API.
 * 
 * @author Anton Gustafsson
 *
 */
public class TWHandler implements IExternalAPIs {
	private TwitterFactory tf;
	private Twitter twitter;

	/**
	 * Setup authentication for using the twitter api.
	 */
	public TWHandler() {
		ConfigurationBuilder cb = new ConfigurationBuilder();
		cb.setDebugEnabled(true).setOAuthConsumerKey("H3tHZ9FUpB2vmH9c61ZcfVyjg")
				.setOAuthConsumerSecret("WvwnarPgqKJ2sMDKxH0tXLvR2N1gMpzNN484JHaYCeeW0lyJ8i")
				.setOAuthAccessToken("4784213706-IqzIyd8oWhpYYrhaoNo18FmAg9BfjWRe79zk0gn")
				.setOAuthAccessTokenSecret("SQLAqbZtd2MixfhoVXWGiqmBl1osGRSYV2SB8wHG0Bo0w");
		tf = new TwitterFactory(cb.build());
		twitter = tf.getInstance();
	}

	/**
	 * Retrieves posts from twitter via the twitter api.
	 * 
	 * @param amount - the number of posts to retrieve.
	 * @param id - the id of the user we want posts from.
	 * @return - JSONArray containing posts.
	 */
	public JSONArray getPostsJSONArray(int amount, String id) {
		Paging paging = new Paging();
		paging.setCount(amount);
		JSONArray jArray = new JSONArray();
		try {
			List<Status> statuses = twitter.getUserTimeline(id, paging);
			for (Status status : statuses) {
				jArray.put(new JSONObject().put("post", status.getText()).put("date", status.getCreatedAt())
						.put("retweets", status.getRetweetCount()));
			}
		} catch (JSONException | TwitterException e) {
			e.printStackTrace();
		}
		return jArray;
	}

	public List<Status> getPostsList(int amount, String id) {
		Paging paging = new Paging();
		paging.setCount(amount);
		List<Status> statuses = null;
		try {
			statuses = twitter.getUserTimeline(id, paging);

		} catch (TwitterException e) {
			e.printStackTrace();
		}
		return statuses;
	}
}
