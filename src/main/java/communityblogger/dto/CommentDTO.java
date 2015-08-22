package communityblogger.dto;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

import communityblogger.domain.Comment;

@XmlRootElement(name = "comment")
public class CommentDTO {
	private Comment comment;
	
	public CommentDTO() {
		this.comment = new Comment();
	}
	
	public CommentDTO(Comment comment) {
		this.comment = comment;
	}
	
	public Comment getComment() {
		return this.comment;
	}
	
	@XmlElement
	public void setContent(String content) {
		this.comment = new Comment(content);
	}
		
	
}
