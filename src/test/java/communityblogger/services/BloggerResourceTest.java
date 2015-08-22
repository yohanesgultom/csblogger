package communityblogger.services;

import static org.junit.Assert.*;

import java.io.StringReader;
import java.util.Arrays;
import java.util.HashSet;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;
import javax.xml.transform.stream.StreamSource;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import communityblogger.domain.BlogEntry;
import communityblogger.domain.Comment;
import communityblogger.domain.User;
import communityblogger.dto.BlogEntryDTO;
import communityblogger.dto.CommentDTO;
import communityblogger.dto.CommentsDTO;
import communityblogger.dto.UserDTO;

public class BloggerResourceTest {

	BloggerResource bloggerResource;
	
	@Before
	public void setUp() {
		bloggerResource = new BloggerResourceImpl();
		User user = new User("user1", "one", "first");
		bloggerResource.createUser(new UserDTO(user));			

		BlogEntry blogEntry = new BlogEntry("This is a test blog entry", new HashSet<String>(Arrays.asList(new String[]{"test","unimportant"})));
		bloggerResource.createBlogEntry(user.getUsername(), new BlogEntryDTO(blogEntry, true));
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

	@Test
	public void createBlogEntry() {
		BlogEntry blogEntry = new BlogEntry("This is a test blog entry", new HashSet<String>(Arrays.asList(new String[]{"test","unimportant"})));
		assertEquals(201, bloggerResource.createBlogEntry("user1", new BlogEntryDTO(blogEntry, true)).getStatus());
	}
	
	@Test
	public void retrieveBlogEntryTest() {
		assertEquals(200, bloggerResource.retrieveBlogEntry(new Long(0)).getStatus());
	}
	
	@Test
	public void blogEntryUnmarshalTest() {
		String xml = "<blogEntry><content>This is a test blog entry</content><id>0</id><keywords>test</keywords><keywords>unimportant</keywords></blogEntry>";
		try {
			JAXBContext ctx = JAXBContext.newInstance(BlogEntryDTO.class);
			Unmarshaller unmarshaller = ctx.createUnmarshaller();
			BlogEntryDTO res = (BlogEntryDTO) unmarshaller.unmarshal(new StreamSource(new StringReader(xml)));
			assertEquals("This is a test blog entry", res.getContent());
		} catch (JAXBException e) {
			e.printStackTrace();
		}
		
	}
	
	@Test
	public void createCommentTest() {
		CommentDTO commentDTO = new CommentDTO(new Comment("This is an example of comment"));
		assertEquals(200, bloggerResource.createComment("user1", new Long(1), commentDTO).getStatus());
	}
	
	@Test
	public void retrieveCommentsTest() {
		assertEquals(200, bloggerResource.retrieveComments(new Long(1)).getStatus());
	}
}
