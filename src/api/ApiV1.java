package api;

import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.codehaus.jettison.json.JSONObject;

import controller.Controller;

/**
 * This class acts as the endpoint for the API. The URI for accessing these
 * resources is: http://localhost:8080/api-mashup-api/api/v1/<resource>
 * 
 * @author Anton Gustafsson
 *
 */

@Path("/v1/")
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
	public Response print1() {
		return Response.ok("Please specify your resource.").header("Access-Control-Allow-Origin", "*").build();
	}

	/**
	 * Return posts from all politicans
	 * 
	 * @return
	 */
	@GET
	@Path("posts")
	@Produces(MediaType.APPLICATION_JSON + ";charset=utf-8")
	public Response getAllPosts() {
		JSONObject jsonObject = new JSONObject();
		try {
			jsonObject = controller.getAllPosts();
			if (jsonObject.getInt("size") == 0) {
				jsonObject = new JSONObject();
				jsonObject.put("MSG", "No posts found");
			}
		} catch (Exception e) {
			e.printStackTrace();
			return Response.status(500).header("Access-Control-Allow-Origin", "*").entity(ERR_MSG).build();
		}
		return Response.ok(jsonObject.toString()).header("Access-Control-Allow-Origin", "*").build();
	}

	/**
	 * 
	 * Return posts from all politicans in specific party.
	 * 
	 * @param party the short name of the party, f.e. "s"
	 * @return
	 */
	@GET
	@Path("posts/{party}")
	@Consumes({ MediaType.TEXT_PLAIN })
	@Produces(MediaType.APPLICATION_JSON + ";charset=utf-8")
	public Response getPartyPosts(@PathParam("party") String party) {
		JSONObject jsonObject = new JSONObject();
		try {
			jsonObject = controller.getAllPostsParty(party);
			if (jsonObject.getInt("size") == 0) {
				jsonObject = new JSONObject();
				jsonObject.put("MSG", "No posts found");
			}
		} catch (Exception e) {
			e.printStackTrace();
			return Response.status(500).header("Access-Control-Allow-Origin", "*").entity(ERR_MSG).build();
		}
		return Response.ok(jsonObject.toString()).header("Access-Control-Allow-Origin", "*").build();
	}

	/**
	 * Return posts from specific politician
	 * 
	 * @param party the short name of the party, f.e. "s"
	 * @param id the id of the politician stored in our database
	 * @return
	 */

	@GET
	@Path("posts/id/{politicianID}")
	// @Consumes({ MediaType.TEXT_PLAIN })
	@Produces(MediaType.APPLICATION_JSON + ";charset=utf-8")
	public Response getPostsByPolitican(@PathParam("politicianID") String id) {
		JSONObject jsonObject = new JSONObject();
		try {
			jsonObject = controller.getPostsPolitician(id);
			if (jsonObject.getInt("size") == 0) {
				jsonObject = new JSONObject();
				jsonObject.put("MSG", "No posts found");
			}
		} catch (Exception e) {
			e.printStackTrace();
			return Response.status(500).header("Access-Control-Allow-Origin", "*").entity(ERR_MSG).build();
		}
		return Response.ok(jsonObject.toString()).header("Access-Control-Allow-Origin", "*").build();
	}


	/**
	 * Return all politician objects.
	 * 
	 * @param party
	 * @return
	 */
	@GET
	@Path("politicians")
	@Produces(MediaType.APPLICATION_JSON + ";charset=utf-8")
	public Response getPoliticians() {
		JSONObject jsonObject = new JSONObject();
		try {
			jsonObject = controller.getAllPoliticians();
			if (jsonObject.getInt("size") == 0) {
				jsonObject = new JSONObject();
				jsonObject.put("MSG", "No politician found");
			}
		} catch (Exception e) {
			e.printStackTrace();
			return Response.status(500).header("Access-Control-Allow-Origin", "*").entity(ERR_MSG).build();
		}
		return Response.ok(jsonObject.toString()).header("Access-Control-Allow-Origin", "*").build();
	}

	/**
	 * Return all politican objects from specific party.
	 * 
	 * @param party the short name of the party, f.e. "s"
	 * @return
	 */

	@GET
	@Path("politicians/{party}")
	@Consumes({ MediaType.TEXT_PLAIN })
	@Produces(MediaType.APPLICATION_JSON + ";charset=utf-8")
	public Response getPoliticiansByParty(@PathParam("party") String party) {
		JSONObject jsonObject = new JSONObject();
		try {
			jsonObject = controller.getPoliticiansParty(party);
			if (jsonObject.getInt("size") == 0) {
				jsonObject = new JSONObject();
				jsonObject.put("MSG", "No politicians found");
			}
		} catch (Exception e) {
			e.printStackTrace();
			return Response.status(500).header("Access-Control-Allow-Origin", "*").entity(ERR_MSG).build();
		}
		return Response.ok(jsonObject.toString()).header("Access-Control-Allow-Origin", "*").build();
	}

	/**
	 * Return specific politican object
	 * 
	 * @return
	 */

	@GET
	@Path("politicians/id/{politician}")
	@Produces(MediaType.APPLICATION_JSON + ";charset=utf-8")
	public Response getSpecificPoliticianByParty(@PathParam("politician") String id) {
		JSONObject jsonObject = new JSONObject();
		try {
			jsonObject = controller.getPolitician(id);
			if (jsonObject.getInt("size") == 0) {
				jsonObject = new JSONObject();
				jsonObject.put("MSG", "No politician found");
			}
		} catch (Exception e) {
			e.printStackTrace();
			return Response.status(500).header("Access-Control-Allow-Origin", "*").entity(ERR_MSG).build();
		}
		return Response.ok(jsonObject.toString()).header("Access-Control-Allow-Origin", "*").build();
	}

	/**
	 * Return all parties as objects.
	 * 
	 * @return
	 */
	@GET
	@Path("parties")
	@Produces(MediaType.APPLICATION_JSON + ";charset=utf-8")
	public Response getPartyInformation() {
		JSONObject jsonObject = new JSONObject();
		try {
			jsonObject = controller.getParties();
			if (jsonObject.getInt("size") == 0) {
				jsonObject = new JSONObject();
				jsonObject.put("MSG", "No parties found");
			}
		} catch (Exception e) {
			e.printStackTrace();
			return Response.status(500).header("Access-Control-Allow-Origin", "*").entity(ERR_MSG).build();
		}
		return Response.ok(jsonObject.toString()).header("Access-Control-Allow-Origin", "*").build();
	}


	/**
	 * Return a specific party object.
	 * 
	 * @return
	 */
	@GET
	@Path("parties/{party}")
	@Produces(MediaType.APPLICATION_JSON + ";charset=utf-8")
	public Response getSpecificPartyInformation(@PathParam("party") String party) {
		JSONObject jsonObject = new JSONObject();
		try {
			jsonObject = controller.getParty(party);
			if (jsonObject.getInt("size") == 0) {
				jsonObject = new JSONObject();
				jsonObject.put("MSG", "No party found");
			}
		} catch (Exception e) {
			e.printStackTrace();
			return Response.status(500).header("Access-Control-Allow-Origin", "*").entity(ERR_MSG).build();
		}
		return Response.ok(jsonObject.toString()).header("Access-Control-Allow-Origin", "*").build();
	}


	/**
	 * Methods that retrives comments for a specific post.
	 * 
	 * @param postID
	 * @return
	 */
	@GET
	@Path("comment/{postID}")
	@Consumes({ MediaType.TEXT_PLAIN })
	@Produces(MediaType.APPLICATION_JSON + ";charset=utf-8")
	public Response getComment(@PathParam("postID") int postID) {
		JSONObject jsonObject = new JSONObject();
		try {
			jsonObject = controller.getComments(postID);
			if (jsonObject.getInt("size") == 0) {
				jsonObject = new JSONObject();
				jsonObject.put("MSG", "No comments found");
			}
		} catch (Exception e) {
			e.printStackTrace();
			return Response.status(500).header("Access-Control-Allow-Origin", "*").entity(ERR_MSG).build();
		}
		return Response.ok(jsonObject.toString()).header("Access-Control-Allow-Origin", "*").build();
	}

	// -------------POSTS/PUTS/DELTES

	/**
	 * This method should be used when posting a comment.
	 */
	@PUT
	@Path("comment/{postID}/{text}/{email}")
	@Consumes({ MediaType.TEXT_PLAIN + ";charset=utf-8" })
	@Produces(MediaType.APPLICATION_JSON + ";charset=utf-8")
	public Response postComment(@PathParam("postID") int postID, @PathParam("text") String text,
			@PathParam("email") String email) {
		JSONObject jsonObject = new JSONObject();
		try {
			controller.postComment(postID, text, email);
			jsonObject.put("message", "success");
		} catch (Exception e) {
			e.printStackTrace();
			return Response.status(500).header("Access-Control-Allow-Origin", "*").entity(ERR_MSG).build();
		}

		return Response.ok(jsonObject.toString()).header("Access-Control-Allow-Origin", "*").build();
	}

	/**
	 * This method should be used when someone is liking a post.
	 */
	@PUT
	@Path("like/{postID}/{email}")
	@Consumes({ MediaType.TEXT_PLAIN + ";charset=utf-8" })
	@Produces(MediaType.APPLICATION_JSON + ";charset=utf-8")
	public Response postLike(@PathParam("postID") int postID, @PathParam("email") String email) {
		JSONObject jsonObject = new JSONObject();
		try {
			if (controller.postLike(postID, email)) {
				jsonObject.put("message", "success");
			} else {
				jsonObject.put("message", "Already liked");
			}
		} catch (Exception e) {
			Response.status(500).header("Access-Control-Allow-Origin", "*").entity(jsonObject.toString()).build();
			e.printStackTrace();
		}

		return Response.ok(jsonObject.toString()).header("Access-Control-Allow-Origin", "*").build();
	}

	/**
	 * This method should be used when someone is disliking a post.
	 */
	@PUT
	@Path("dislike/{postID}/{email}")
	@Consumes({ MediaType.TEXT_PLAIN + ";charset=utf-8" })
	@Produces(MediaType.APPLICATION_JSON + ";charset=utf-8")
	public Response postDislike(@PathParam("postID") int postID, @PathParam("email") String email) {

		JSONObject jsonObject = new JSONObject();
		try {
			if (controller.postDislike(postID, email)) {
				jsonObject.put("message", "success");
			} else {
				jsonObject.put("message", "Already disliked.");
			}
		} catch (Exception e) {
			e.printStackTrace();
			Response.status(500).header("Access-Control-Allow-Origin", "*").entity(jsonObject.toString()).build();

		}

		return Response.ok(jsonObject.toString()).header("Access-Control-Allow-Origin", "*").build();
	}

	/**
	 * This method should be used when someone wants to remove their like.
	 */
	@DELETE
	@Path("like/{postID}/{email}")
	@Consumes({ MediaType.TEXT_PLAIN + ";charset=utf-8" })
	@Produces(MediaType.APPLICATION_JSON + ";charset=utf-8")
	public Response postRevertLike(@PathParam("postID") int postID, @PathParam("email") String email) {

		JSONObject jsonObject = new JSONObject();
		try {

			if (controller.postRevertLike(postID, email)) {
				jsonObject.put("message", "success");
			} else {
				jsonObject.put("message", "Failed to revert like.");

			}
		} catch (Exception e) {
			e.printStackTrace();
			return Response.status(500).header("Access-Control-Allow-Origin", "*").entity(ERR_MSG).build();
		}

		return Response.ok(jsonObject.toString()).header("Access-Control-Allow-Origin", "*").build();
	}

	/**
	 * This method should be used when someone wants to remove their dislike
	 */
	@DELETE
	@Path("dislike/{postID}/{email}")
	@Consumes({ MediaType.TEXT_PLAIN + ";charset=utf-8" })
	@Produces(MediaType.APPLICATION_JSON + ";charset=utf-8")
	public Response postRevertDislike(@PathParam("postID") int postID, @PathParam("email") String email) {

		JSONObject jsonObject = new JSONObject();
		try {
			if (controller.postRevertDislike(postID, email)) {
				jsonObject.put("message", "success");
			} else {
				jsonObject.put("message", "Failed to revert dislike.");

			}
		} catch (Exception e) {
			e.printStackTrace();
			return Response.status(500).header("Access-Control-Allow-Origin", "*").entity(ERR_MSG).build();
		}

		return Response.ok(jsonObject.toString()).header("Access-Control-Allow-Origin", "*").build();
	}

}
