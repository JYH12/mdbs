package com.mdbs.pojo;

import org.springframework.stereotype.Component;

@Component
public class User {
	private Integer uid;
	private String email;
	private String username;
	private String password;
	private String headurl;
	public Integer getUid() {
		return uid;
	}
	public void setUid(Integer uid) {
		this.uid = uid;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
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
	public String getHeadurl() {
		return headurl;
	}
	public void setHeadurl(String headurl) {
		this.headurl = headurl;
	}
	@Override
	public String toString() {
		return "User{"+"uid="+uid+
				" username="+username+
				" email="+email+
				" password="+password+
				" headurl="+headurl+"}";
	}
}
