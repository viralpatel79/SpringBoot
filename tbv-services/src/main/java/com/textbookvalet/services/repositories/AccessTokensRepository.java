package com.textbookvalet.services.repositories;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.textbookvalet.commons.AccessToken;

@Repository
public interface AccessTokensRepository extends CrudRepository<AccessToken, Integer> {

	@Query("select a from AccessToken a where a.userId = ?1")
	public AccessToken findByUserId(Integer userId);

	@Modifying
	@Transactional
	@Query("UPDATE AccessToken a SET a.token = ?1 WHERE a.userId = ?2")
	public int updateTokenByUserId(String token, int userId);

}
