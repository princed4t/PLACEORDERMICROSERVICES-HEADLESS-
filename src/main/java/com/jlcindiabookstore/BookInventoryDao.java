package com.jlcindiabookstore;

import org.springframework.data.jpa.repository.JpaRepository;

public interface BookInventoryDao extends JpaRepository<BookInventory,Integer>{
	
}