package communityblogger.services;

import static org.junit.Assert.*;

import org.junit.Test;

import communityblogger.domain.User;
import junit.framework.Assert;

public class BloggerResourceTest {

	@Test
	public void testRetrieveUser() {
		BloggerResource bloggerResource = new BloggerResourceImpl();
		assertEquals(new User("yohanesgultom", "Gultom", "Yohanes"), bloggerResource.retrieveUser());
	}
}
