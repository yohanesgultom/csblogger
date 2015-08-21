package communityblogger.dto;

import javax.xml.bind.annotation.XmlRootElement;

import communityblogger.domain.User;

@XmlRootElement(name = "user")
public class UserDTO {
	private User user;
	
	public UserDTO() {
		this.user = new User();
	}
	
	public UserDTO(User user) {
		this.user = user;
	}
	
	public User getUser() {
		return this.user;
	}
	
	public void setUsername(String username) {
		this.user.setUsername(username);
	}
	
	public void setFirstname(String firstname) {
		this.user.setFirstname(firstname);
	}
	
	public void setLastname(String lastname) {
		this.user.setLastname(lastname);
	}
	
	public String getUsername() {
		return this.user.getUsername();
	}
	
	public String getFirstname() {
		return this.user.getFirstname();
	}
	
	public String getLastname() {
		return this.user.getLastname();
	}
}


