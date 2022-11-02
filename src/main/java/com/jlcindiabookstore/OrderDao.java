package com.jlcindiabookstore;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface OrderDao extends JpaRepository<Order,Integer>{
	@Query("from Order myord where userId=?1")
	public List<Order> findorderbyuserid(String id);

}
