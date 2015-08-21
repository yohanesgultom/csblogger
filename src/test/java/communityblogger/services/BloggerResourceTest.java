package communityblogger.services;

import static org.junit.Assert.*;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import communityblogger.domain.User;
import communityblogger.dto.UserDTO;

public class BloggerResourceTest {

	BloggerResource bloggerResource;
	
	@Before
	public void setUp() {
		bloggerResource = new BloggerResourceImpl();
		bloggerResource.createUser(new UserDTO(new User("user1", "one", "first")));
	}

	@After
	public void tearDown() {
		bloggerResource.initialiseContent();
	}
	
	@Test
	public void createUserTest() {
		assertEquals(201, bloggerResource.createUser(new UserDTO(new User("newUser", "new", "brand"))).getStatus());
	}
	
	@Test
	public void retrieveUserTest() {		
		assertEquals(200, bloggerResource.retrieveUser("user1").getStatus());
	}
}
