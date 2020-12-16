package com.amos.podsupapi.common;

import java.util.List;

public class JWTResultDTO  {
	private boolean result;
	private String issuser;
	private String subject;
	private List<String> audience;

	public boolean isVerify() {
		return result;
	}
	public void setResult(boolean result) {
		this.result = result;
	}

	public String getIssuser() {
		return issuser;
	}
	public void setIssuser(String issuser) {
		this.issuser = issuser;
	}
	public String getSubject() {
		return subject;
	}
	public void setSubject(String subject) {
		this.subject = subject;
	}
	public List<String> getAudience() {
		return audience;
	}
	public void setAudience(List<String> audience) {
		this.audience = audience;
	}
}