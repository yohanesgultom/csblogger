package communityblogger.dto;

import java.util.HashSet;
import java.util.Set;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

import communityblogger.domain.BlogEntry;

@XmlRootElement(name = "blogEntries")
public class BlogEntriesDTO {
	private Set<BlogEntryDTO> blogEntries;
	
	public BlogEntriesDTO() {
		this.blogEntries = new HashSet<BlogEntryDTO>();
	}
	
	public BlogEntriesDTO(Set<BlogEntry> blogEntries, boolean withComments) {
		HashSet<BlogEntryDTO> dtos = new HashSet<BlogEntryDTO>();
		for (BlogEntry entry:blogEntries) {
			dtos.add(new BlogEntryDTO(entry, withComments));
		}
		this.blogEntries = dtos;
	}
	
	public Set<BlogEntryDTO> getBlogEntries() {
		return this.blogEntries;
	}
	
	@XmlElement(name = "blogEntry")
	public void setBlogEntries(Set<BlogEntryDTO> blogEntries) {
		this.blogEntries = blogEntries;
	}
}
