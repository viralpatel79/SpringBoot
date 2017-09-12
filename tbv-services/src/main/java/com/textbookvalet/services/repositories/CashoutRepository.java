package com.textbookvalet.services.repositories;

import java.math.BigDecimal;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.textbookvalet.commons.Cashout;

@Repository
public interface CashoutRepository extends CrudRepository<Cashout, Integer> {

	@Query(nativeQuery = true, value = "select SUM(cashouts.amount) FROM cashouts WHERE cashouts.admin_id = ?1")
	BigDecimal findTotalCashout(Integer userId);

}
