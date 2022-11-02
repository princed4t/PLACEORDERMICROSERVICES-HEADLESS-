package com.jlcindiabookstore;

import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.RestTemplate;

import com.jlcindiarabbitmq.BookInventoryInfo;
import com.jlcindiarabbitmq.OrderFullInfo;
import com.jlcindiarabbitmq.OrderInfo;
import com.jlcindiarabbitmq.OrderItemInfo;

@Service
@Transactional
@RabbitListener(queues = "myorder.queue")
public class OrderServiceImpl implements OrderService {
	static Logger log = LoggerFactory.getLogger(OrderServiceImpl.class);
	@Autowired
	OrderDao orderDAO;
	@Autowired
	OrderItemDao orderItemDAO;
	@Autowired
	BookInventoryDao bookInventoryDAO;
     @Autowired
	RabbitTemplate rabbitTemplate;
     @RabbitListener(queues = "myorder.queue")
	public void placeOrder(OrderFullInfo orderInfo1) {
		log.info("---OrderServiceImpl---placeOrder()-----");
//1. Save the Order
		OrderInfo orderInfo = orderInfo1.getOrder();
		Order myorder=new Order(orderInfo.getOrderDate(), orderInfo.getUserId(),
				orderInfo.getTotalQty(), orderInfo.getTotalCost(), orderInfo.getStatus());
				myorder=orderDAO.save(myorder);//1002
				int orderId=myorder.getOrderId();
//2.Save OrderItems
				List<OrderItemInfo> itemsListInfo=orderInfo1.getItemsList();
				for(OrderItemInfo orderItemInfo:itemsListInfo) {
				OrderItem myorderItem=new OrderItem(orderId, orderItemInfo.getBookId(),
				orderItemInfo.getQty(), orderItemInfo.getCost());
				orderItemDAO.save(myorderItem);
				}
//3.Update Inventory at Two Places
				for(OrderItemInfo orderItemInfo:itemsListInfo) {
					Integer bookId=orderItemInfo.getBookId(); //103
					BookInventory mybookInventory = bookInventoryDAO.findById(bookId).get();
					Integer currentStock=mybookInventory.getBooksAvailable();
					currentStock = currentStock - orderItemInfo.getQty();
					mybookInventory.setBooksAvailable(currentStock);
					// Update Local Inventory
					bookInventoryDAO.save(mybookInventory);
					//Update Inventory of BookSearchMS by Sending Message
					BookInventoryInfo bookInventoryInfo=new BookInventoryInfo();
					bookInventoryInfo.setBookId(mybookInventory.getBookId());
					bookInventoryInfo.setBooksAvailable(mybookInventory.getBooksAvailable());
					rabbitTemplate.convertAndSend("mybook.search.exchange","myinventory.key",bookInventoryInfo);
					} //end of for loop
					}
	

	@Override
	public List<Order> getOrdersByUserId(String userId) {
		log.info("---OrderServiceImpl---getOrdersByUserId()-----");
		List<Order> orderList = orderDAO.findorderbyuserid(userId);
		return orderList;
	}
}
