package api;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.ResponseBuilder;

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
public class ApiV1 {
	
	private final String ERR_MSG = "Server was not able to process your request";
	
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
			System.out.println(party);
			jsonObject = controller.getSocialPostsSpecificParty(party);
		} catch (Exception e) {
			e.printStackTrace();
			return Response.status(500).header("Access-Control-Allow-Origin", "*")
					.entity("Server was not able to process your request").build();
		}

		return Response.ok(jsonObject.toString()).header("Access-Control-Allow-Origin", "*").build();
	}
	
	/**
	 * Returns data about all politicians from a specified party, formatted as a JSON response
	 * @param party the short name of the party, f.e. "s"
	 * @return
	 */
	
	@GET
	@Path("politicians/{party}")
	@Produces(MediaType.APPLICATION_JSON + ";charset=utf-8")
	public Response getPoliticiansByParty(@PathParam("party") String party) {
		JSONObject jsonObject = new JSONObject();
		try {
			jsonObject = controller.getPoliticiansByParty(party);
		} catch (Exception e) {
			e.printStackTrace();
			return Response.status(500).header("Access-Control-Allow-Origin", "*").entity(ERR_MSG).build();
		}
		return Response.ok(jsonObject.toString()).header("Access-Control-Allow-Origin", "*").build();
	}
	
	/**
	 * Returns posts from a specified politician, formatted as a JSON response
	 * @param party the short name of the party, f.e. "s"
	 * @param id the id of the politician stored in our database
	 * @return 
	 */

	@GET
	@Path("posts/{party}/{politician}")
	@Produces(MediaType.APPLICATION_JSON + ";charset=utf-8")
	public Response getPostsByPolitican(@PathParam ("party") String party, @PathParam("politician") String id) {
		JSONObject jsonObject = new JSONObject();
		try {
			//jsonObject = controller.getPostsByPolitician(id);
			jsonObject = controller.getPostByPolitican();
		} catch (Exception e) {
			e.printStackTrace();
			return Response.status(500).header("Access-Control-Allow-Origin", "*").entity(ERR_MSG).build();
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
