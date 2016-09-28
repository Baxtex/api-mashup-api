package api.test;


import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

@Path("/v1/foo")
public class Test2 {

	@GET
	@Path("/bar")
	@Produces(MediaType.TEXT_HTML)
	public String getUsers() {
		return "This should be printed.";
	}

	
}
