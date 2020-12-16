package com.amos.podsupapi.services;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.Map;

import javax.naming.Context;
import javax.naming.NamingEnumeration;
import javax.naming.NamingException;
import javax.naming.directory.Attribute;
import javax.naming.directory.Attributes;
import javax.naming.directory.DirContext;
import javax.naming.directory.InitialDirContext;
import javax.naming.directory.SearchControls;
import javax.naming.directory.SearchResult;

import org.springframework.stereotype.Service;

import com.amos.podsupapi.common.ReturnCode;
import com.amos.podsupapi.config.AppConfig;
import com.amos.podsupapi.dto.ReturnStatusDTO;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

@Service
public class LdapService {

	private Hashtable<String, String> createDefaultLdapEnv() {
		Hashtable<String, String> env = new Hashtable<String, String>();
		env.put(Context.INITIAL_CONTEXT_FACTORY, "com.sun.jndi.ldap.LdapCtxFactory");
		env.put(Context.PROVIDER_URL, "ldap://" + AppConfig.getLdapServerPrimary());
		env.put(Context.SECURITY_PRINCIPAL, AppConfig.getLdapUsername() + "@" + AppConfig.getLdapDomain());
		env.put(Context.SECURITY_CREDENTIALS, AppConfig.getLdapPassword());
		env.put(Context.SECURITY_AUTHENTICATION, "simple");
		env.put("com.sun.jndi.ldap.read.timeout", "3000");
		return env;
	}
	
	public synchronized String getUserInfo(String userlan) throws NamingException, JsonProcessingException {

		Map<Object, Object> data = null;
		UserInfoDTO user = new UserInfoDTO();
		ObjectMapper objectMapper = new ObjectMapper();

		Hashtable<String, String> env = createDefaultLdapEnv();

		String searchFilter = "(sAMAccountName=" + userlan + ")";
		SearchControls searchCtls = new SearchControls();

		searchCtls.setSearchScope(SearchControls.SUBTREE_SCOPE);
		try {
			DirContext ctxGC = new InitialDirContext(env);

			NamingEnumeration<?> answer = ctxGC.search(AppConfig.getLdapDn(), searchFilter, searchCtls);

			SearchResult sr = (SearchResult) answer.next();
			Attributes attrs = sr.getAttributes();
			data = parseLdapQueryToObject(attrs);

		} catch (Exception ex) {
			user.setResult(new ReturnStatusDTO(ReturnCode.NO_DATA));
			return objectMapper.writeValueAsString(user);
		}

		if (data == null) {

			user.setResult(new ReturnStatusDTO(ReturnCode.NO_DATA));
			return objectMapper.writeValueAsString(user);
		}

		if (data.containsKey("mail")) {
			user.setEmail(data.get("mail").toString());
		}

		if (data.containsKey("telephoneNumber")) {
			user.setPhone(data.get("telephoneNumber").toString());
		}

		if (data.containsKey("displayName")) {
			if (data.get("displayName").toString().contains(" ")) {
				String[] name = data.get("displayName").toString().split(" ");

				if (name.length == 2) {
					user.setFirstname(name[0]);
					user.setLastname(name[1]);
				} else {
					user.setFirstname(data.get("displayName").toString());
				}

			} else {
				user.setFirstname(data.get("displayName").toString());
			}
		}

		user.setResult(new ReturnStatusDTO(ReturnCode.SUCCESS));

		return objectMapper.writeValueAsString(user);
	}

	public synchronized boolean auth(String username, String password) throws NamingException {

		Map<Object, Object> data = null;

		Hashtable<String, String> env = createDefaultLdapEnv();
		env.put("AttributeIndex", "sAMAccountName");

		String searchFilter = "(sAMAccountName=" + username + ")";
		SearchControls searchCtls = new SearchControls();
		searchCtls.setSearchScope(SearchControls.SUBTREE_SCOPE);
		DirContext ctxGC = new InitialDirContext(env);

		NamingEnumeration<?> answer = ctxGC.search(AppConfig.getLdapDn(), searchFilter, searchCtls);
		SearchResult sr = (SearchResult) answer.next();
		Attributes attrs = sr.getAttributes();
		data = parseLdapQueryToObject(attrs);

		if (data == null ||data.isEmpty())
			return false;

		return true;
	}

	private Map<Object, Object> parseLdapQueryToObject(Attributes attrs) throws NamingException {

		Map<Object, Object> data = null;

		String key = "";
		Object value = "";
		if (attrs != null) {
			data = new HashMap<>();
			NamingEnumeration<? extends Attribute> ne = attrs.getAll();
			while (ne.hasMore()) {
				Attribute attr = (Attribute) ne.next();
				key = attr.getID();
				// set value
				if (attr.size() > 1) {
					ArrayList<String> temps = new ArrayList<String>();
					NamingEnumeration<?> gg = attr.getAll();
					while (gg.hasMore()) {
						String temp = (String) gg.next();
						temps.add(temp);
					}
					value = temps;
				} else {
					value = attr.get();
				}
				data.put(key, value);
			}
		}

		return data;
	}

	class UserInfoDTO {

		private String firstname;

		public String getFirstname() {
			return firstname;
		}

		public void setFirstname(String firstname) {
			this.firstname = firstname;
		}

		public String getLastname() {
			return lastname;
		}

		public void setLastname(String lastname) {
			this.lastname = lastname;
		}

		public String getEmail() {
			return email;
		}

		public void setEmail(String emal) {
			this.email = emal;
		}

		public String getPhone() {
			return phone;
		}

		public void setPhone(String phone) {
			this.phone = phone;

		}

		public void setResult(ReturnStatusDTO result) {
			this.setReturn_result(result);
		}

		public ReturnStatusDTO getReturn_result() {
			return return_result;
		}

		public void setReturn_result(ReturnStatusDTO return_result) {
			this.return_result = return_result;
		}

		private String lastname;
		private String email;
		private String phone;
		private ReturnStatusDTO return_result;

	}
}
