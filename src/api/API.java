package api;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.codehaus.jettison.json.JSONException;
import org.codehaus.jettison.json.JSONObject;

import controller.Controller;

/**
 * This class acts as a simple API. The URI for accessing these resources is:
 * http://localhost:8080/api-mashup-api/api/v1
 * 
 * @author Anton Gustafsson
 *
 */

@Path("/v1")
public class API {
	private Controller controller = new Controller();

	/**
	 * Prints if we don't specify any resource
	 * 
	 * @return
	 */
	@GET
	@Produces(MediaType.TEXT_HTML)
	public String print1() {
		return "Please specify what resource you need.";
	}

	/**
	 * Prints to the screen if we are in /foobar
	 * 
	 * @return
	 * @throws JSONException
	 */
	@GET
	@Path("/foobar")
	@Produces(MediaType.APPLICATION_JSON)
	public String print2() throws JSONException {
		JSONObject jsonObject = new JSONObject();
		jsonObject.append("Is this working?", "YES");
		return jsonObject.toString();
	}

	/**
	 * 
	 * This method should return posts from specific party.
	 * 
	 * @return
	 */
	@GET
	@Path("/posts/{party}")
	@Produces(MediaType.APPLICATION_JSON + ";charset=utf-8")
	public Response getPartyPosts(@PathParam("party") String party) {

		
		JSONObject jsonObject = new JSONObject();
		try {
			jsonObject = controller.getSocialPostsSpecificParty(party);
		} catch (Exception e) {
			e.printStackTrace();
			return Response.status(500).header("Access-Control-Allow-Origin", "*")
					.entity("Server was not able to process your request").build();
		}

		return Response.ok(jsonObject.toString()).header("Access-Control-Allow-Origin", "*").build();
	}
	
	
	
	
	
	
	/**
	 * 
	 * This method is super cereal and it's purpose is to return a JSON Array
	 * containing posts from both twitter and facebook.
	 * 
	 * @return
	 */
	@GET
	@Path("/testPosts")
	@Produces(MediaType.APPLICATION_JSON + ";charset=utf-8")
	public Response getPostsTest() {

		String fbId = "1064287767"; // Facebook id.
		String tId = "@ingemarnilsson_"; // Twitter id.
		JSONObject jsonObject = new JSONObject();
		try {
			jsonObject = controller.getSocialPosts(fbId, tId);
		} catch (Exception e) {
			e.printStackTrace();
			return Response.status(500).header("Access-Control-Allow-Origin", "*")
					.entity("Server was not able to process your request").build();
		}

		return Response.ok(jsonObject.toString()).header("Access-Control-Allow-Origin", "*").build();
	}
}
