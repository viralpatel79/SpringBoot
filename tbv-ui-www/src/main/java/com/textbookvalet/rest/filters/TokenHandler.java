package com.textbookvalet.rest.filters;

import java.util.LinkedHashMap;
import java.util.Map;

import org.jboss.logging.Logger;
import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.SignatureException;

@Component
public class TokenHandler {

	private final static Logger logger = Logger.getLogger(TokenHandler.class);

	@Value("${token.secret:FIRE-left-POLAND-himself}")
	private String secret = "FIRE-left-POLAND-himself";

	public static void main1(String[] args) {
		/*
		 * TokenHandler handler = new TokenHandler(); String token =
		 * handler.createTokenForUser(3487); Integer userId =
		 * handler.parseUserFromToken(token); System.out.println("token : " +
		 * token); String appId = handler.parseAppId(
		 * "eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJBUFBfSUQiOiJBTUFaT04ifQ.vjN2Lhc8F7MU-gy2pImOUmcWnncmXvR26OY3r9i1TV8"
		 * ); String appId2 = handler.parseAppId(
		 * "eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJleHAiOjE0ODkwNDE3MTgsImRhdGEiOnsidXNlcl9pZCI6MzE2NX19.9cAaANfoUWsDO3Tvx0oYCOZmmvf99t-AB8VqZfFtM_4"
		 * ); appId = handler.parseAppId(token); System.out.println("User Id : "
		 * + appId);
		 */

		TokenHandler h = new TokenHandler();
		/*System.out.println("User Id : " + h.parseUserFromToken(
				"eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJleHAiOjE1MDM1Mjc5OTQsImRhdGEiOnsidXNlcl9pZCI6NDQ0NX19.dpvYwCZG3XT_Jvfl-1LnPrxLEyxSsiwtvtkS1xtA9do"));
*/
		String t = h.createTokenForUser(1);
		System.out.println("token : " + t);
		System.out.println("vvv: " + h.parseUserFromToken(t));
	}

	/**
	 * Parse the token and read the user_id value attached
	 *
	 * @param token
	 * @return
	 * @throws SignatureException
	 */
	public Integer parseUserFromToken(String token) throws SignatureException {
		Integer userId = null;
		Jws<Claims> JsonContent = null;

		if (secret != null) {
			try {
				JsonContent = Jwts.parser().setSigningKey(secret.getBytes()).parseClaimsJws(token);
			} catch (Exception ex) {
				logger.error(ex.getMessage(), ex);
				// throw new SignatureException(ex.getMessage());
			}
			if (JsonContent != null) {
				Claims clm = JsonContent.getBody();
				if (clm != null) {
					@SuppressWarnings("unchecked")
					Map<String, Object> clmVals = (Map<String, Object>) clm.get("data");
					if (clmVals != null) {
						Object user_id = clmVals.get("user_id");
						if (user_id != null)
							userId = (Integer) user_id;
					}
				}
			}
		}
		return userId;
	}

	/**
	 * Parse the token and read the APP_ID value
	 *
	 * @param token
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public String parseAppId(String token) {
		String appId = "";
		Jws<Claims> JsonContent = null;

		if (secret != null) {
			try {
				JsonContent = Jwts.parser().setSigningKey(secret.getBytes()).parseClaimsJws(token);
			} catch (Exception ex) {
				throw new SignatureException(ex.getMessage());
			}
			if (JsonContent != null) {
				Claims clm = JsonContent.getBody();
				if (clm != null) {
					if (clm.containsKey("APP_ID")) {
						appId = (String) clm.get("APP_ID");
					} else {
						LinkedHashMap<String, String> facebookData = (LinkedHashMap<String, String>) clm.get("data");
						appId = facebookData.get("auth_provider");
						if (null == appId) {
							appId = "basic_auth";
						}
					}
				}
			}
		}
		return appId;
	}

	/**
	 * Sample method to create token
	 *
	 * @param userId
	 * @return
	 */
	public String createTokenForUser(Integer userId) {

		JSONObject obj = null;
		try {
			// Date exp = new Date(System.currentTimeMillis() + 10000);
			// obj = new JSONObject("{'exp': 1852344152,'data':
			// {'APP_ID':'TextbookDrop 1.0'}}");
			// obj = new JSONObject("{'APP_ID':'TextbookValet 1.0'}");
			obj = new JSONObject("{'data':{'user_id':" + userId + "}}");
		} catch (JSONException e) {
			logger.error("Error creating JSONObject while generating Token: ", e);
		}

		return Jwts.builder().setHeaderParam("typ", "JWT").setHeaderParam("alg", "HS256").setPayload(obj.toString())
				.signWith(SignatureAlgorithm.HS256, secret.getBytes()).compact();
	}
}
