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
	
	public BlogEntryDTO() {
		this.blogEntry = new BlogEntry();
		this.keywords = new HashSet<String>();
	}

	public BlogEntryDTO(BlogEntry blogEntry) {
		this.blogEntry = blogEntry;
		this.keywords = new HashSet<String>(blogEntry.getKeywords());
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
		return new HashSet<Comment>(this.blogEntry.getComments());
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
