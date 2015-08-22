package communityblogger.services;

import java.util.Set;

import javax.ws.rs.Consumes;
import javax.ws.rs.CookieParam;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.container.AsyncResponse;
import javax.ws.rs.container.Suspended;
import javax.ws.rs.core.Cookie;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import communityblogger.domain.BlogEntry;
import communityblogger.domain.Comment;
import communityblogger.domain.User;
import communityblogger.dto.BlogEntryDTO;
import communityblogger.dto.CommentDTO;
import communityblogger.dto.CommentsDTO;
import communityblogger.dto.UserDTO;

// TO DO:
// Configure the relative URI path for this resource.
@Path("/blogger")
public interface BloggerResource {

	// TO DO:
	// Define one method for each of the operations in the Community Blogger
	// Web service contract. In each case, use appropriate JAX-RS annotations
	// to specify the URI pattern, media  type and HTTP method.
	//
	// The service contract comprises the 8 operations:
	// - Create user
	// - Retrieve user
	// - Create blog entry
	// - Retrieve blog entry
	// - Create comment
	// - Retrieve comments
	// - Retrieve blog entries
	// - Follow blog entry

	
	/**
	 * Useful operation to initialise the state of the Web service. This operation 
	 * can be invoked prior to executing each unit test.
	 */
	@PUT
	@Produces(MediaType.APPLICATION_XML)
	void initialiseContent();
	
	@PUT
	@Path("createUser")
	@Consumes(MediaType.APPLICATION_XML)
	@Produces(MediaType.APPLICATION_XML)
	Response createUser(UserDTO newUserDTO);
	
	@GET
	@Path("retrieveUser/{username}")
	@Produces(MediaType.APPLICATION_XML)
	Response retrieveUser(@PathParam("username")String username);
	
	@PUT
	@Path("createBlogEntry")
	@Consumes(MediaType.APPLICATION_XML)
	@Produces(MediaType.APPLICATION_XML)
	Response createBlogEntry(@CookieParam("username")String username, BlogEntryDTO blogEntryDTO);
	
	@GET
	@Path("retrieveBlogEntry/{id}")
	@Produces(MediaType.APPLICATION_XML)
	Response retrieveBlogEntry(@PathParam("id")Long id);

	@PUT
	@Path("createComment/{id}")
	@Consumes(MediaType.APPLICATION_XML)
	@Produces(MediaType.APPLICATION_XML)
	Response createComment(@CookieParam("username")String username, @PathParam("id")Long id, CommentDTO commentDTO);

	@GET
	@Path("retrieveComments/{id}")
	@Produces(MediaType.APPLICATION_XML)
	Response retrieveComments(@PathParam("id")Long id);
	
}
