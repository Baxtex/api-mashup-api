package api.test;


import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.codehaus.jettison.json.JSONArray;
import org.codehaus.jettison.json.JSONObject;



@Path("/v1/foo")
public class Test {

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
}
