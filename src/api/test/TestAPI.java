package api.test;

import java.util.List;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.codehaus.jettison.json.JSONObject;
import org.glassfish.jersey.client.oauth2.ClientIdentifier;
import org.glassfish.jersey.client.oauth2.OAuth2ClientSupport;
import org.glassfish.jersey.client.oauth2.OAuth2CodeGrantFlow;
import org.glassfish.jersey.client.oauth2.TokenResult;

import twitter4j.Status;
import twitter4j.Twitter;
import twitter4j.TwitterException;
import twitter4j.TwitterFactory;
import twitter4j.conf.ConfigurationBuilder;

import org.glassfish.jersey.client.authentication.*;;

/**
 * This class acts as a simple API. The URI for accessing these resorces is:
 * localhost:8080/api-mashup-api/api/v1/foo/
 * 
 * @author Anton Gustafsson
 *
 */

@Path("/v1/foo")
public class TestAPI {
	
	private TwitterFactory tf;
	/**
	 * Initializes Twitter framework.
	 */
	public TestAPI(){
		ConfigurationBuilder cb = new ConfigurationBuilder();
		cb.setDebugEnabled(true).setOAuthConsumerKey("H3tHZ9FUpB2vmH9c61ZcfVyjg")
				.setOAuthConsumerSecret("WvwnarPgqKJ2sMDKxH0tXLvR2N1gMpzNN484JHaYCeeW0lyJ8i")
				.setOAuthAccessToken("4784213706-IqzIyd8oWhpYYrhaoNo18FmAg9BfjWRe79zk0gn")
				.setOAuthAccessTokenSecret("SQLAqbZtd2MixfhoVXWGiqmBl1osGRSYV2SB8wHG0Bo0w");
		tf = new TwitterFactory(cb.build());
	}

	/**
	 * Prints to the screen if we are in /v1/foo
	 * 
	 * @return
	 */
	@GET
	@Produces(MediaType.TEXT_HTML)
	public String print1() {
		return "This should be printed if in foo";
	}

	/**
	 * Prints to the screen if we are in /v1/foo/bar
	 * 
	 * @return
	 */
	@GET
	@Path("/bar")
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
	@Path("/bar/{specific}")
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
	@Path("/bar/response")
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
		return Response.ok(returnString).build();
	}

	/**
	 *  Gets user specific tweets if we are in /v1/foo/twitter 
	 * 
	 * @return
	 * @throws TwitterException
	 */
	@GET
	@Path("/twitter")
	@Produces(MediaType.TEXT_HTML + ";charset=utf-8" )
	public String print5() throws TwitterException {
		String result ="";
		Twitter twitter = tf.getInstance();
		List<Status> statuses = twitter.getUserTimeline("@annieloof");
		for (Status status : statuses) {
			result += status.getUser().getName() + ":" + status.getText();
		}
		return result;

	}
}