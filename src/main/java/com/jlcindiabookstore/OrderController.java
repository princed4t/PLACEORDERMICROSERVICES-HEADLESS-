package com.jlcindiabookstore;

import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import io.swagger.annotations.ApiOperation;

@CrossOrigin // CORS
@RestController
public class OrderController {
	static Logger log = LoggerFactory.getLogger(OrderController.class);
	@Autowired
	OrderService orderService;

	@GetMapping("/myorders/{userId}")
	@ApiOperation(value = " getOrdersByUserId", response = List.class, notes = "ReturnOrders belongs User")
	public List<Order> getOrdersByUserId(@PathVariable String userId) {
		log.info("---OrderController---getOrdersByUserId()-----");
		List<Order> myoders = orderService.getOrdersByUserId(userId);
		return myoders;
	}
}