package entity.builder;

import entity.User;

public class UserBuilder {

		private User user;
		
		public UserBuilder(){
			this.user = new User();
		}
		
		public UserBuilder setUsername(String username){
			user.setUsername(username);
			return this;
		}
		
		public UserBuilder setPassword(String password){
			user.setPassword(password);
			return this;
		}
		
		public User build(){
			return this.user;
		}
}

