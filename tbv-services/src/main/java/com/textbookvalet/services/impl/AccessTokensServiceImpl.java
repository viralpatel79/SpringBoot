package com.textbookvalet.services.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.textbookvalet.commons.AccessToken;
import com.textbookvalet.services.AccessTokensService;
import com.textbookvalet.services.repositories.AccessTokensRepository;

@Service
public class AccessTokensServiceImpl extends BaseServiceImpl implements AccessTokensService {

	@Autowired
	private AccessTokensRepository accessTokensRepository;

	@Override
	public List<AccessToken> getAll() {
		return (List<AccessToken>) accessTokensRepository.findAll();
	}

	@Override
	public AccessToken save(AccessToken AccessTokens) {
		return accessTokensRepository.save(AccessTokens);
	}

	@Override
	public AccessToken getById(Integer id) {
		return accessTokensRepository.findOne(id);
	}

	@Override
	public void delete(Integer id) {
		accessTokensRepository.delete(id);
	}

	@Override
	public AccessToken findByUserId(Integer userId) {
		return accessTokensRepository.findByUserId(userId);
	}

	@Override
	public int updateTokenByUserId(String token, Integer userId) {
		return accessTokensRepository.updateTokenByUserId(token, userId);
	}

}
