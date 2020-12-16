package com.amos.podsupapi.common;

import java.util.Date;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.amos.podsupapi.config.AppConfig;
import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTCreator.Builder;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.interfaces.DecodedJWT;

public class CommonJWT {

	public static final String DEFAULT_TEST_HMAC256_SECRET = "SECRET";
	public static final int DEFAULT_TEST_TOKEN_EXPIRE_HOURS = 3;

	private static final Logger LOGGER  = LogManager.getLogger(CommonJWT.class);

	private CommonJWT() {}

	public static String jwtTokenGenerate(String issUser) {
		String token = null;
		try {
			Algorithm algorithmHS = Algorithm.HMAC256(AppConfig.getSecretHMAC256());
			Integer expireHours = AppConfig.getDefaultTokenExpireHours();

			token = JWT.create().withIssuer(issUser)
					.withExpiresAt(CommonUtils.getTimeAfter(expireHours))
					.sign(algorithmHS);
		} catch (Exception e) {
			LOGGER.error("JWT Token Generate Error",e);
		}
		return token;
	}
	public static String jwtTokenGenerateNoExpire(String issUser, String subject, List<String> audiences) {
		return CommonJWT.jwtTokenGenerate(issUser, subject, audiences, null);
	}
	public static String JWTTokenGenerateBaseExpire(String issUser, String subject, List<String> audiences) {
		return CommonJWT.jwtTokenGenerate(issUser, subject, audiences, AppConfig.getDefaultTokenExpireHours());
	}
	public static String jwtTokenGenerate(String issUser, String subject, List<String> audiences, Integer expireHours) {
		String token = null;
		try {
			Algorithm algorithmHS = Algorithm.HMAC256(AppConfig.getSecretHMAC256());
			Builder builder = JWT.create();
			if(CommonUtils.isNullOrEmpty(issUser)) {
				return null;
			}
			if(!CommonUtils.isNullOrEmpty(subject)) {
				builder.withSubject(subject);
			}
			if(audiences != null) {
				builder.withSubject(subject);
			}
			if(audiences != null) {
				String[] aud = new String[audiences.size()];
				for (int i=0;i<audiences.size();i++) {
					aud[i]=audiences.get(i);
				}
				builder.withAudience(aud);
			}
			if(expireHours != null) {
				builder.withExpiresAt(CommonUtils.getTimeAfter(expireHours));
			}
			else {
				builder.withClaim("createdAt", new Date());
			}
			token = builder.sign(algorithmHS);
		} catch (Exception e) {
			LOGGER.error("JWTTokenGenerate Exception !!",e);
		}
		return token;
	}

	public static JWTResultDTO jwtTokenVerify(String token) {
		JWTResultDTO result = new JWTResultDTO();
		try {
			Algorithm algorithm = Algorithm.HMAC256(AppConfig.getSecretHMAC256());
			JWTVerifier verifier = JWT.require(algorithm).build();
			DecodedJWT jwt = verifier.verify(token);

			result.setIssuser(jwt.getIssuer());
			result.setSubject(jwt.getSubject());
			result.setAudience(jwt.getAudience());

			result.setResult(true);

		} catch (JWTVerificationException e) {
			result.setResult(false);
		} catch (Exception e) {
			LOGGER.error("JWTTokenVerify Exception !!",e);
		}
		return result;
	}


}

