package communityblogger.dto;

import java.util.HashSet;
import java.util.Set;

import javax.xml.bind.annotation.XmlRootElement;

import org.joda.time.DateTime;

import communityblogger.domain.BlogEntry;
import communityblogger.domain.Comment;
import communityblogger.domain.User;

@XmlRootElement(name = "blogEntry")
public class BlogEntryDTO {
	private BlogEntry blogEntry;
	private Set<String> keywords;
	private boolean withoutComments = true;
	
	public BlogEntryDTO() {
		this.blogEntry = new BlogEntry();
		this.keywords = new HashSet<String>();
		this.withoutComments = true;
	}

	public BlogEntryDTO(BlogEntry blogEntry, boolean withComments) {		
		this.blogEntry = blogEntry;		
		this.keywords = new HashSet<String>(blogEntry.getKeywords());
		this.withoutComments = withComments;
	}

	public BlogEntry getBlogEntry() {
		return this.blogEntry;
	}
	
	public String getContent() {
		return this.blogEntry.getContent();
	}
	
	public Set<String> getKeywords() {
		//return new HashSet<String>(this.blogEntry.getKeywords());
		return this.keywords;
	}

	public Set<Comment> getComments() {
		return (this.withoutComments) ? new HashSet<Comment>(this.blogEntry.getComments()) : null;
	}
	
	public Long getId() {
		return this.blogEntry.getId();
	}
	
	public DateTime getTimePosted() {
		return this.blogEntry.getTimePosted();
	}
	
	public User getAuthor() {
		return this.blogEntry.getAuthor();
	}
	
	public void setContent(String content) {
		this.blogEntry.setContent(content);
	}

	public void setKeywords(Set<String> keywords) {
		//this.blogEntry.setKeywords(keywords);
		this.setKeywords(keywords);
	}
	
	public void setComments(Set<Comment> comments) {
		// ignored
	}
	
	public void setId(Long id) {
		// ignored
	}
	
	public void setTimePosted(DateTime timePosted) {
		// ignored
	}
	
	public void setAuthor(User user) {
		// ignored
	}

}
