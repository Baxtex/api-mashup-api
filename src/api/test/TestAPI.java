package api.test;


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


/**
 * This class acts as a simple API.
 * The URI for accessing these resorces is:
 * localhost:8080/api-mashup-api/api/v1/foo/
 * 
 * @author Anton Gustafsson
 *
 */

@Path("/v1/foo")
public class TestAPI {

	/**
	 * Prints to the screen if we are in /v1/foo
	 * @return
	 */
	@GET
	@Produces(MediaType.TEXT_HTML)
	public String print1() {
		return "This should be printed if in foo";
	}
	
	/**
	 * Prints to the screen if we are in /v1/foo/bar
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
	 * @return
	 */
	@GET
	@Path("/bar/{specific}")
	@Produces(MediaType.TEXT_HTML)
	public String print3(@PathParam("specific") int specific) {
		if (specific == 1){
			return "This should be printed if you looked for 1";
		}else{
			return "This should be printed if I found nothing, look for 1 instead!";
		}
	}
	
	/**
	 * Creates a custom response and sends it as a JSON object
	 * First we create a JSON object and we put boolean which is true into it.
	 * If something goes wrong we build a new Response which says server error.
	 * Else we build a response and returns our josnObject
	 * (In this case, the error message is redundat as this code can never throw an error.
	 * However, let's say we got data from a database, then that could
	 * throw all kinds of errors, and we want to tell the user of the API
	 * that something went wrong.
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
		}
		catch (Exception e) {
			e.printStackTrace();
			return Response.status(500).entity("Server was not able to process your request").build();
		}
		return Response.ok(returnString).build();	
	}
	
	/**
	 * Retrives bearer token and gets tweets if we are in /v1/foo/twitter
	 * 
	 * @return
	 */
	@GET
	@Path("/twitter")
	@Produces(MediaType.TEXT_HTML)
	public String print5() {
		// clientID, clientSecrect
		ClientIdentifier ci = new ClientIdentifier("H3tHZ9FUpB2vmH9c61ZcfVyjg",
				"WvwnarPgqKJ2sMDKxH0tXLvR2N1gMpzNN484JHaYCeeW0lyJ8i");

		// (clientId, authUri, accessTokenUri) på authcodegrantdflow
		OAuth2CodeGrantFlow flow = OAuth2ClientSupport.authorizationCodeGrantFlowBuilder(ci,
				"https://api.twitter.com/oauth2/token", "https://api.twitter.com/oauth2/token").scope("contact")
				.build();

		String finalAuthorizationUri = flow.start();

		return "This is the bearer: " + finalAuthorizationUri.toString();
	}
	
	
	
}
