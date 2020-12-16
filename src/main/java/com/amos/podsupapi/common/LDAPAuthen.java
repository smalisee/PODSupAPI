package com.amos.podsupapi.common;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.Map;

import javax.naming.AuthenticationException;
import javax.naming.Context;
import javax.naming.NamingEnumeration;
import javax.naming.NamingException;
import javax.naming.directory.Attribute;
import javax.naming.directory.Attributes;
import javax.naming.directory.DirContext;
import javax.naming.directory.InitialDirContext;
import javax.naming.directory.SearchControls;
import javax.naming.directory.SearchResult;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.amos.podsupapi.config.AppConfig;

public class LDAPAuthen {

  private static final Logger LOGGER = LogManager.getLogger(LDAPAuthen.class);

  @SuppressWarnings("rawtypes")
  public String checkDataFromLDAP(String username, String password, String domain) {
    String empRet = "";
    try {
      Map dataMap = queryAD(username, password,
          domain,
          AppConfig.getLdapServerPrimary(),
          AppConfig.getLdapServerSecondary(),
          AppConfig.getLdapServerThridary(),
          AppConfig.getLdapDn());
      if (dataMap != null) {
        String empId = String.valueOf(dataMap.get("employeeID"));
        if (!"".equals(empId)) {
          empId = empId.substring(2).trim();
          if (empId.indexOf('-') > -1) {
            empId = empId.substring(1);
          }
          return empId;
        } else {
          return "";
        }
      }
    } catch (Exception e) {
      LOGGER.info("checkDataFromLDAP Exception", e);
    }
    return empRet;
  }

  // -----------------------------------------------------------------------------------------------------------------------

  @SuppressWarnings({ "unchecked", "rawtypes" })
  public static Map queryAD(String username,
      String pwd,
      String domainUrl,
      String ldapPriUrl,
      String ldapSecUrl,
      String ldapThirdUrl,
      String domainSearchBase) throws NamingException {

    Hashtable env = new Hashtable();
    env.put(Context.INITIAL_CONTEXT_FACTORY, "com.sun.jndi.ldap.LdapCtxFactory");
    env.put(Context.PROVIDER_URL, "ldap://" + ldapPriUrl + " ldap://" + ldapSecUrl + " ldap://" + ldapThirdUrl);
    env.put(Context.SECURITY_PRINCIPAL, username + "@" + domainUrl);
    env.put(Context.SECURITY_CREDENTIALS, pwd);
    env.put(Context.SECURITY_AUTHENTICATION, "simple");
    env.put("com.sun.jndi.ldap.read.timeout", "2000");
    Map amap = null;
    String searchBase = domainSearchBase;

    try {

      String searchFilter = "(sAMAccountName=" + username + ")";
      SearchControls searchCtls = new SearchControls();
      searchCtls.setSearchScope(SearchControls.SUBTREE_SCOPE);
      DirContext ctxGC = new InitialDirContext(env);

      NamingEnumeration answer = ctxGC.search(searchBase, searchFilter, searchCtls);

      SearchResult sr = (SearchResult) answer.next();

      Attributes attrs = sr.getAttributes();
      String key = "";
      Object value = "";
      if (attrs != null) {
        amap = new HashMap();
        NamingEnumeration ne = attrs.getAll();
        while (ne.hasMore()) {
          Attribute attr = (Attribute) ne.next();
          key = attr.getID();
          if (attr.size() > 1) {
            ArrayList temps = new ArrayList();
            NamingEnumeration gg = attr.getAll();
            while (gg.hasMore()) {
              String temp = (String) gg.next();
              temps.add(temp);
            }
            value = temps;
          } else {
            value = attr.get();
          }
          amap.put(key, value);
        }
      }
    } catch (AuthenticationException ex) {
      LOGGER.info("AuthenticationException", ex);
      throw (ex);
    }

    return amap;
  }

}
