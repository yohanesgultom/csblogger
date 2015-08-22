package communityblogger.dto;

import java.util.HashSet;
import java.util.Set;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import communityblogger.domain.Comment;

@XmlRootElement(name = "comments")
public class CommentsDTO {
	private Set<Comment> comments;
	
	public CommentsDTO() {
		this.comments = new HashSet<Comment>();
	}
	
	public CommentsDTO(Set<Comment> comments) {
		this.comments = new HashSet<Comment>(comments);
	}
	
	@XmlElement(name = "comment")
	public Set<Comment> getComments() {
		return this.comments;
	}
	
	public void setComments(Set<Comment> comments) {
		this.comments = new HashSet<Comment>(comments);
	}
	
}
