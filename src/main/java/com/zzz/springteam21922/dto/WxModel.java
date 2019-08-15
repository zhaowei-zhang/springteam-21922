package com.zzz.springteam21922.dto;

public class WxModel {
	private String token;
	private Integer expires_in;
	private String token_begin_str;
	public String getToken() {
		return token;
	}
	public void setToken(String token) {
		this.token = token;
	}
	public Integer getExpires_in() {
		return expires_in;
	}
	public void setExpires_in(Integer expires_in) {
		this.expires_in = expires_in;
	}
	public String getToken_begin_str() {
		return token_begin_str;
	}
	public void setToken_begin_str(String token_begin_str) {
		this.token_begin_str = token_begin_str;
	}
	@Override
	public String toString() {
		return "WxModel [token=" + token + ", expires_in=" + expires_in
				+ ", token_begin_str=" + token_begin_str + "]";
	}
	

}
