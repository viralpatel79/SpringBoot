package com.textbookvalet.services.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.textbookvalet.commons.Order;

@Repository
public interface OrderRepository extends CrudRepository<Order, Integer> {

	@Query(nativeQuery = true, value = "select sum(referrer_commission) as totalEarning ,sum(total) as totalReferralLoopSale "
			+ " from orders o , users u where o.referrer_id = u.id and u.id=?1 and lower(u.referrer_type) = 'user' ")
	List<Object[]> findTotalEarningsAndTotalReferredLoopSales(Integer userId);

}
