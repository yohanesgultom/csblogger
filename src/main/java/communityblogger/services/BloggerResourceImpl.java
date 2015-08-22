package communityblogger.services;


import java.net.URI;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.atomic.AtomicLong;

import javax.ws.rs.QueryParam;
import javax.ws.rs.core.Response;

import org.apache.commons.lang3.StringUtils;
import org.joda.time.DateTime;

import communityblogger.domain.BlogEntry;
import communityblogger.domain.Comment;
import communityblogger.domain.User;
import communityblogger.dto.BlogEntriesDTO;
import communityblogger.dto.BlogEntryDTO;
import communityblogger.dto.CommentDTO;
import communityblogger.dto.CommentsDTO;
import communityblogger.dto.UserDTO;

/**
 * Implementation of the BloggerResource interface.
 *
 */
public class BloggerResourceImpl implements BloggerResource {

	/*
	 * Possible data structures to store the domain model objects.
	 * _users is a map whose key is username. Each User is assumed to have a
	 * unique username.
	 * _blogEntries is a map whose key is the ID of a BlogEntry. Each BlogEntry
	 * is assumed to have a unique ID.
	 * _idCounter is a thread-safe counter, which can be used to assign unique
	 * IDs to blogEntry objects as they are created. 
	 */
	private Map<String, User> _users;
	private Map<Long, BlogEntry> _blogEntries;
	private AtomicLong _idCounter;


	public BloggerResourceImpl() {
		// TO DO:
		// Initialise instance variables.
		this.initialiseContent();		
	}

	public void initialiseContent() {
		// TO DO:
		// (Re)-initialise data structures so that the Web service's state is
		// the same same as when the Web service was initially created.
		this._users = new HashMap<String, User>();
		this._blogEntries = new HashMap<Long, BlogEntry>();
		this._idCounter = new AtomicLong(0);		
		// TO DO remove later
		User user = new User("user1", "one", "first");
		User user2 = new User("user2", "two", "second");
		this.createUser(new UserDTO(user));			
		this.createUser(new UserDTO(user2));			
		BlogEntry blogEntry = new BlogEntry("This is a test blog entry", new HashSet<String>(Arrays.asList(new String[]{"test","unimportant"})));
		blogEntry.addComment(new Comment("This is an example of comment"), user2);
		blogEntry.addComment(new Comment("This is another meaningless comment"), user2);
		this.createBlogEntry(user.getUsername(), new BlogEntryDTO(blogEntry, true));
	}
		
	// TO DO:
	// Implement BloggerResource methods for the service contract.
	@Override
	public Response createUser(UserDTO newUserDTO) {
		try {
			if (StringUtils.isEmpty(newUserDTO.getUsername())) throw new Exception("Empty username");
			User existing = this._users.get(newUserDTO.getUsername());
			if (existing == null) {
				this._users.put(newUserDTO.getUsername(), newUserDTO.getUser());
				return Response.created(new URI("/services/blogger/retrieveUser/" + newUserDTO.getUsername())).build();
			} else {
				return Response.status(409).build();
			}
		} catch (Exception e) {
			e.printStackTrace();
			return Response.serverError().entity(e.getMessage()).build();
		}
	}
	
	@Override
	public Response retrieveUser(String username) {
		User existing = this._users.get(username);
		if (existing != null) {
			return Response.ok(existing).build();
		} else {
			return Response.status(404).build();
		}
	}
	
	@Override
	public Response createBlogEntry(String username, BlogEntryDTO blogEntryDTO) {
		try {
			BlogEntry blogEntry = blogEntryDTO.getBlogEntry();
			User existing = this._users.get(username);			
			if (existing == null) throw new IllegalArgumentException("Invalid username");
			blogEntry.setKeywords(blogEntryDTO.getKeywords());
			blogEntry.setId(this._idCounter.getAndIncrement());
			existing.addBlogEntry(blogEntry);
			this._blogEntries.put(blogEntry.getId(), blogEntry);
			return Response.created(new URI("/services/blogger/retrieveBlogEntry/" + blogEntry.getId())).build();
		} catch (IllegalArgumentException e) {
			return Response.status(412).entity(e.getMessage()).build();
		} catch (Exception e) {
			return Response.serverError().entity(e.getMessage()).build();
		}			
	}
	
	@Override
	public Response retrieveBlogEntry(Long id) {
		try {
			if (id == null) throw new IllegalArgumentException("Empty id");
			BlogEntry blogEntry = this._blogEntries.get(id);
			if (blogEntry == null) throw new IllegalArgumentException("Invalid id");
			return Response.ok(new BlogEntryDTO(blogEntry, false)).build();
		} catch (IllegalArgumentException e) {
			return Response.status(404).entity(e.getMessage()).build();
		} catch (Exception e) {
			e.printStackTrace();
			return Response.serverError().entity(e.getStackTrace()).build();
		}		
	}

	@Override
	public Response createComment(String username, Long id, CommentDTO commentDTO) {
		try {
			if (id == null) throw new IllegalArgumentException("Empty id");		
			User existing = this._users.get(username);			
			if (existing == null) throw new IllegalArgumentException("Invalid username");
			BlogEntry blogEntry = this._blogEntries.get(id);
			if (blogEntry == null) throw new javax.ws.rs.NotFoundException("Invalid id");
			Comment comment = commentDTO.getComment();
			blogEntry.addComment(comment, existing);
			return Response.ok().build();
		} catch (javax.ws.rs.NotFoundException e) {
			return Response.status(404).entity(e.getMessage()).build();
		} catch (IllegalArgumentException e) {
			return Response.status(412).entity(e.getMessage()).build();
		} catch (Exception e) {
			e.printStackTrace();
			return Response.serverError().entity(e.getStackTrace()).build();
		}
	}

	@Override
	public Response retrieveComments(Long id) {
		try {
			if (id == null) throw new IllegalArgumentException("Empty id");		
			BlogEntry blogEntry = this._blogEntries.get(id);
			if (blogEntry == null) throw new javax.ws.rs.NotFoundException("Invalid id");			
			return Response.ok(new CommentsDTO(blogEntry.getComments())).build();
		} catch (Exception e) {
			e.printStackTrace();
			return Response.status(404).entity(e.getMessage()).build();
		}
	}

	@Override
	public Response retrieveBlogEntries(@QueryParam("author")String author, @QueryParam("maxCount")Integer maxCount, @QueryParam("minTime")String minTime, @QueryParam("maxTime")String maxTime, @QueryParam("maxBlogId")Integer maxBlogId, @QueryParam("minBlogId")Integer minBlogId) {
		Set<BlogEntry> blogEntries = new HashSet<BlogEntry>();
		int count = 0;
		for (BlogEntry blogEntry : this._blogEntries.values()) {
			if (maxCount != null && count >= maxCount) break;
			if (author != null && !blogEntry.getAuthor().getUsername().equals(author)) continue;
			if (minTime != null) {
				try {
					DateTime minDateTime = new DateTime(minTime);
					if (minDateTime != null && blogEntry.getTimePosted().isBefore(minDateTime.getMillis())) continue; 
				} catch (Exception e) {
					// continue
				}
			}
			if (maxTime != null) {
				try {
					DateTime maxDateTime = new DateTime(maxTime);
					if (maxDateTime != null && blogEntry.getTimePosted().isAfter(maxDateTime.getMillis())) continue;					
				} catch (Exception e) {
					// continue
				}
			}
			if (minBlogId != null && minBlogId < blogEntry.getId()) continue;
			if (maxBlogId != null && maxBlogId > blogEntry.getId()) continue;
			blogEntries.add(blogEntry);
			count++;
		}
		BlogEntriesDTO blogEntriesDTO = new BlogEntriesDTO(blogEntries, true); 
		return Response.ok(blogEntriesDTO).build();
	}
		
}
