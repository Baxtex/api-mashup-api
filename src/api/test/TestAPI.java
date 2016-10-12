package api.test;

import java.util.List;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.codehaus.jettison.json.JSONArray;
import org.codehaus.jettison.json.JSONException;
import org.codehaus.jettison.json.JSONObject;

import facebook4j.Facebook;
import facebook4j.FacebookException;
import facebook4j.FacebookFactory;
import facebook4j.Post;
import facebook4j.Reading;
import facebook4j.ResponseList;
//import facebook4j.conf.ConfigurationBuilder;
import twitter4j.Paging;
import twitter4j.Status;
import twitter4j.Twitter;
import twitter4j.TwitterException;
import twitter4j.TwitterFactory;
//import twitter4j.conf.ConfigurationBuilder;;

/**
 * This class acts as a simple API. The URI for accessing these resorces is:
 * localhost:8080/api-mashup-api/api/v1/
 * 
 * @author Anton Gustafsson
 *
 */

@Path("/v1/")
public class TestAPI {

	private TwitterFactory tf;
	private FacebookFactory ff;

	/**
	 * Initializes Twitter and Facebook framework by creating factories and
	 * providing tokens. Cannot import ConfigurationBuilder directl in the class
	 * as both twitter4j and facebook4j uses the same name.
	 */
	public TestAPI() {

		twitter4j.conf.ConfigurationBuilder cb = new twitter4j.conf.ConfigurationBuilder();
		cb.setDebugEnabled(true).setOAuthConsumerKey("H3tHZ9FUpB2vmH9c61ZcfVyjg")
				.setOAuthConsumerSecret("WvwnarPgqKJ2sMDKxH0tXLvR2N1gMpzNN484JHaYCeeW0lyJ8i")
				.setOAuthAccessToken("4784213706-IqzIyd8oWhpYYrhaoNo18FmAg9BfjWRe79zk0gn")
				.setOAuthAccessTokenSecret("SQLAqbZtd2MixfhoVXWGiqmBl1osGRSYV2SB8wHG0Bo0w");
		tf = new TwitterFactory(cb.build());

		facebook4j.conf.ConfigurationBuilder cb2 = new facebook4j.conf.ConfigurationBuilder();
		cb2.setDebugEnabled(true).setOAuthAppId("1781740452084874")
				.setOAuthAppSecret("cc2f66af625b9ce52adc1990b9050dc8")
				.setOAuthAccessToken("1781740452084874|HRv3MSZw2nVUf5j8C8G6ZsERxvo")
				.setOAuthPermissions("email,publish_stream");
		ff = new FacebookFactory(cb2.build());

	}

	/**
	 * Prints to the screen if we are in /v1/foo
	 * 
	 * @return
	 */
	@GET
	@Produces(MediaType.TEXT_HTML)
	public String print1() {
		return "Please specify what resource you need.";
	}

	/**
	 * Prints to the screen if we are in /v1/foo/bar
	 * 
	 * @return
	 */
	@GET
	@Path("/foo")
	@Produces(MediaType.TEXT_HTML)
	public String print2() {
		return "This should be printed if in foo/bar";
	}

	/**
	 * Prints to the screen if we are in /v1/foo/bar/1 else not.
	 * 
	 * @return
	 */
	@GET
	@Path("/foo/{specific}")
	@Produces(MediaType.TEXT_HTML)
	public String print3(@PathParam("specific") int specific) {
		if (specific == 1) {
			return "This should be printed if you looked for 1";
		} else {
			return "This should be printed if I found nothing, look for 1 instead!";
		}
	}

	/**
	 * Creates a custom response and sends it as a JSON object First we create a
	 * JSON object and we put boolean which is true into it. If something goes
	 * wrong we build a new Response which says server error. Else we build a
	 * response and returns our josnObject (In this case, the error message is
	 * redundat as this code can never throw an error. However, let's say we got
	 * data from a database, then that could throw all kinds of errors, and we
	 * want to tell the user of the API that something went wrong.
	 * 
	 * @return
	 * @throws Exception
	 */
	@GET
	@Path("/foo/response")
	@Produces(MediaType.APPLICATION_JSON)
	public Response createResponse() throws Exception {
		String returnString = null;
		JSONObject json = new JSONObject();
		try {
			json.append("working", true);
			returnString = json.toString();
		} catch (Exception e) {
			e.printStackTrace();
			return Response.status(500).entity("Server was not able to process your request").build();
		}
		return Response.ok(returnString).header("Access-Control-Allow-Origin", "*").build();
	}

	/**
	 * Gets user specific tweets if we are in /v1/foo/twitter Mostly for testing
	 * purposes. TODO: If we have all politicians in a db, we need to loop
	 * through it and call this method for each one to get their tweets.
	 * 
	 * @return
	 * @throws TwitterException
	 */
	@GET
	@Path("/tweets")
	@Produces(MediaType.TEXT_PLAIN + ";charset=utf-8")
	public String print5() {
		int nbrTweets = 5;
		Paging p = new Paging();
		p.setCount(nbrTweets); // How many tweets we want
		String userID = "@annieloof";
		String preString = "We got " + nbrTweets + " tweets from " + userID + ":" + '\n';
		String result = "";

		Twitter twitter = tf.getInstance();
		List<Status> statuses;
		try {
			statuses = twitter.getUserTimeline(userID, p);
			for (Status status : statuses) {
				result += status.getUser().getName() + ":" + status.getText() + '\n';
			}
		} catch (TwitterException e) {
			e.printStackTrace();
		}
		if (result == "") {
			return "Nothing found";
		} else {
			return preString + result;
		}

	}

	/**
	 * Should return user specific posts. Mostly for testing purposes
	 * 
	 * @return
	 */
	@GET
	@Path("/fbPosts")
	@Produces(MediaType.TEXT_PLAIN + ";charset=utf-8")
	public String print6() {
		String id = "118943054880945"; // Annie l�fs facebook id
		String res = "";
		Facebook facebook = ff.getInstance();
		try {
			ResponseList<Post> feed = facebook.getPosts(id, new Reading().limit(4));
			for (Post post : feed) {
				res += post.getName() + ": " + post.getMessage() + '\n';
			}
		} catch (FacebookException e) {
			e.printStackTrace();
		}

		if (res == "") {
			res += "Nothing found";
		}

		return res;
	}

	/**
	 * This method is serious and it's purpose is to return a JSON Array
	 * containing posts from both twitter and facebook. The question is how we
	 * are going to retrieve the ids.
	 * 
	 * @return
	 */
	@GET
	@Path("/posts")
	@Produces(MediaType.APPLICATION_JSON + ";charset=utf-8") // +
																// ";charset=utf-8"
	public Response getPosts() {
		String fbId = "118943054880945"; // Annie l�fs fb id.
		String tId = "@annieloof"; // Annie L�fs Twitter id.

		JSONArray jsonArrayFB = new JSONArray();
		JSONArray jsonArrayT = new JSONArray();
		JSONObject jsonObject = new JSONObject();

		try {
			jsonObject.put("HTTP_CODE", "200");
			jsonObject.put("MSG", "jsonArray successfully retrieved, v1");
			jsonArrayFB = getFB(jsonArrayFB, fbId);
			jsonArrayT = getT(jsonArrayT, tId);
			jsonObject.put("fbposts", jsonArrayFB);
			jsonObject.put("twposts", jsonArrayT);

		} catch (Exception e) {
			e.printStackTrace();
			return Response.status(500).header("Access-Control-Allow-Origin", "*").entity("Server was not able to process your request").build();
		}

		return Response.ok(jsonObject).header("Access-Control-Allow-Origin", "*").build();

	}

	private JSONArray getFB(JSONArray jsonArray, String fbId) throws JSONException {
		Facebook facebook = ff.getInstance();
		try {
			ResponseList<Post> feed = facebook.getPosts(fbId, new Reading().limit(5));
			for (Post post : feed) {
				jsonArray.put(new JSONObject().put("FBpost", post.getMessage()));
			}
		} catch (FacebookException e) {
			e.printStackTrace();
			throw new IllegalArgumentException("Something happend with the FB retriever.");
		}
		return jsonArray;

	}

	private JSONArray getT(JSONArray jsonArray, String tId) throws JSONException {
		Paging p = new Paging();
		p.setCount(5);
		

		Twitter twitter = tf.getInstance();
		List<Status> statuses;
		try {
			statuses = twitter.getUserTimeline(tId, p);
			for (Status status : statuses) {
				jsonArray.put(new JSONObject().put("Tpost", status.getText()));
			}
		} catch (TwitterException e) {
			e.printStackTrace();
			throw new IllegalArgumentException("Something happend with the T retriever.");
		}

		return jsonArray;
	}

}
