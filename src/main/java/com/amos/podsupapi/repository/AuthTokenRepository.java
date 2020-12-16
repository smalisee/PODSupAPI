package com.amos.podsupapi.repository;

import com.amos.podsupapi.model.AuthToken;

public interface AuthTokenRepository {

	void add(AuthToken authToken);

	AuthToken find(String username, String token);

	void logoutDeleteToken(String username, String token);

	boolean checkToken(String username, String token);

	boolean checkToken(String username, String token, String device);

}
