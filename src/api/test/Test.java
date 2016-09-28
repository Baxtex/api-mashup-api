package api.test;


import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

@Path("/v1/foo")
public class Test {

	
	@GET
	@Produces(MediaType.TEXT_HTML)
	public String print1() {
		return "This should be printed if in foo";
	}
	
	@GET
	@Path("/bar")
	@Produces(MediaType.TEXT_HTML)
	public String print2() {
		return "This should be printed if in foo/bar";
	}

	
}
