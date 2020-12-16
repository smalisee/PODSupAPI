package com.amos.podsupapi.filter;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.StringTokenizer;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.util.AntPathMatcher;
import org.springframework.web.filter.OncePerRequestFilter;

import com.amos.podsupapi.common.CommonJWT;
import com.amos.podsupapi.common.JWTResultDTO;

public class SecurityFilter extends OncePerRequestFilter {

	private static final Logger LOGGER = LogManager.getLogger(SecurityFilter.class);
	private static final String BASIC_AUTH_KEY = "YW1vczpzaG9w";

	@Override
	protected boolean shouldNotFilter(HttpServletRequest request) throws ServletException {

		AntPathMatcher pathMatcher = new AntPathMatcher();
		Collection<String> excludeUrlPatterns = new ArrayList<>();
		excludeUrlPatterns.add("/home");
		excludeUrlPatterns.add("/config/reload");
		excludeUrlPatterns.add("/changeLogLevel");
		excludeUrlPatterns.add("/login");
		excludeUrlPatterns.add("/get_upn_domain_list");
		excludeUrlPatterns.add("/getDeliveryImage_file/**");
		excludeUrlPatterns.add("/help");

		return excludeUrlPatterns.stream().anyMatch(p -> pathMatcher.match(p, request.getServletPath()));
	}

	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain)
			throws ServletException, IOException {
		String authHeader = request.getHeader("Authorization");

		if (authHeader != null) {
			authorize(request, response, chain, authHeader);
		} else {
			unauthorized(response, String.format("Request [%s] with NO Authorization Header ", request.getPathInfo()));
		}

	}

	private void authorize(HttpServletRequest request, HttpServletResponse response, FilterChain chain,
			String authHeader) throws IOException, ServletException {
		StringTokenizer st = new StringTokenizer(authHeader);
		if (st.hasMoreTokens()) {
			String type = st.nextToken();
			if (type.equalsIgnoreCase("Bearer")) {
				authorizeWithToken(request, response, chain, st);
			} else if (type.equalsIgnoreCase("Basic")) {
				authorizeWithBasicAuth(request, response, chain, st);

			} else {				
				unauthorized(response, String.format("Authorize with '%s' is not support", type));
			}
		}
		else {
			unauthorized(response, String.format("Authorize Header is Missing"));
		}
	}

	private void authorizeWithBasicAuth(HttpServletRequest request, HttpServletResponse response, FilterChain chain,
			StringTokenizer st) throws IOException, ServletException {
		if (st.nextToken().equals(BASIC_AUTH_KEY)) {
			chain.doFilter(request, response);
		}
	}

	private void authorizeWithToken(HttpServletRequest request, HttpServletResponse response, FilterChain chain,
			StringTokenizer st) throws IOException, ServletException {
		String authToken = st.nextToken();
		JWTResultDTO result = CommonJWT.jwtTokenVerify(authToken);

		if (result.isVerify()) {
			chain.doFilter(request, response);
		} else {
			unauthorized(response, String.format("Request [%s] with INVALID TOKEN ", request.getPathInfo()));
		}
	}

	private void unauthorized(HttpServletResponse response, String message) throws IOException {
		LOGGER.warn(message);
		response.sendError(401, message);
	}
}
