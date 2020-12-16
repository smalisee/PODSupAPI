package com.amos.podsupapi.repository;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;

import org.springframework.stereotype.Repository;

import com.amos.podsupapi.model.AuthToken;

@Transactional
@Repository
public class AuthTokenRepositoryImpl implements AuthTokenRepository {

	@PersistenceContext
	private EntityManager em;

	private static final String PARAM_TOKEN = "token";
	private static final String PARAM_USERNAME = "username";
	private static final String PARAM_DEVICE = "device";

	@Override
	public AuthToken find(String username, String token) {
		String hql = String.format("FROM AuthToken as tk WHERE tk.username = :%s AND tk.token = :%s", PARAM_USERNAME,
				PARAM_TOKEN);
		List<AuthToken> l = em.createQuery(hql, AuthToken.class).setParameter(PARAM_USERNAME, username)
				.setParameter(PARAM_TOKEN, token).getResultList();
		return (!l.isEmpty()) ? l.get(0) : null;
	}

	@Override
	public void add(AuthToken authToken) {
		this.deleteIfExist(authToken);
		em.persist(authToken);
	}

	@Override
	public void logoutDeleteToken(String username, String token) {
		AuthToken authToken = this.find(username, token);
		if (authToken != null) {
			em.remove(authToken);
		}

	}

	@Override
	public boolean checkToken(String username, String token) {
		String hql = String.format("FROM AuthToken as tk WHERE tk.username = :%s AND tk.token = :%s", PARAM_USERNAME,
				PARAM_TOKEN);
		List<AuthToken> tkL = em.createQuery(hql, AuthToken.class).setParameter(PARAM_USERNAME, username)
				.setParameter(PARAM_TOKEN, token).getResultList();
		return !tkL.isEmpty();
	}

	@Override
	public boolean checkToken(String username, String token, String device) {
		String hql = String.format(
				"FROM AuthToken as tk WHERE tk.username = :%s AND tk.token = :%s AND tk.device = :%s", PARAM_USERNAME,
				PARAM_TOKEN, PARAM_DEVICE);
		List<AuthToken> tkL = em.createQuery(hql, AuthToken.class).setParameter(PARAM_USERNAME, username)
				.setParameter(PARAM_TOKEN, token).setParameter(PARAM_DEVICE, device).getResultList();
		return !tkL.isEmpty();
	}

	public void deleteIfExist(AuthToken authToken) {
		String hql = String.format("FROM AuthToken as tk WHERE tk.username = :%s", PARAM_USERNAME);
		List<AuthToken> tkL = em.createQuery(hql, AuthToken.class).setParameter(PARAM_USERNAME, authToken.getUsername())
				.getResultList();
		if (!tkL.isEmpty()) {
			for (AuthToken tk : tkL) {
				em.remove(tk);
			}
		}
	}

}
