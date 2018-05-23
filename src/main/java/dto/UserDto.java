package dto;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.MessageChannel;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Component;
import service.notify.Message;

import java.util.Observable;
import java.util.Observer;

@Component
public class UserDto implements service.notify.Observer {


	private int id;
	private String username;
	private String password;
	private String roles = "";
	@Autowired
	private SimpMessagingTemplate simpMessagingTemplate;
	
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public String getRoles() {
		return roles;
	}
	public void setRoles(String roles) {
		this.roles = roles;
	}


	@Override
	public void update(Message message) {
	simpMessagingTemplate.convertAndSendToUser(this.getUsername(),"/queue/reply", message);
	}
}
