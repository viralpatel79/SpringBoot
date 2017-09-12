package com.textbookvalet.services;

import com.textbookvalet.commons.AccessToken;

public interface AccessTokensService extends BaseService<AccessToken, Integer> {

	public AccessToken findByUserId(Integer userId);

	public int updateTokenByUserId(String token, Integer userId);

}
