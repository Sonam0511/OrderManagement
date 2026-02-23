package example.OrderManagement.service;

import example.OrderManagement.dto.CreateOrderRequest;
import example.OrderManagement.exception.BadRequestException;
import example.OrderManagement.exception.OrderNotFoundException;
import example.OrderManagement.model.Order;
import example.OrderManagement.model.OrderStatus;
import java.util.Map;
import java.util.HashMap;
import java.util.List;
import java.util.ArrayList;
import java.util.UUID;

import org.springframework.stereotype.Service;
import java.util.concurrent.ConcurrentHashMap;
@Service
public class OrderService {

  // Thread-safe in-memory storage (better than HashMap)
  private final Map<String, Order> orderStore = new ConcurrentHashMap<>();

  // ✅ Create Order using DTO
  public Order createOrder(CreateOrderRequest request) {

    Order order = new Order();
    order.setOrderId(UUID.randomUUID().toString());
    order.setCustomerName(request.getCustomerName());
    order.setAmount(request.getAmount());
    order.setStatus(OrderStatus.NEW);

    orderStore.put(order.getOrderId(), order);
    return order;
  }

  // ✅ Get Order by ID
  public Order getOrder(String orderId) {
    Order order = orderStore.get(orderId);

    if (order == null) {
      throw new OrderNotFoundException("Order not found with id " + orderId);
    }

    return order;
  }

  // ✅ Get all orders
  public List<Order> getAllOrders() {
    return new ArrayList<>(orderStore.values());
  }

  // ✅ Update order status
  public Order updateStatus(String orderId, OrderStatus newStatus) {

    Order order = getOrder(orderId);

    if (!isValidTransition(order.getStatus(), newStatus)) {
      throw new BadRequestException("Invalid status transition");
    }

    order.setStatus(newStatus);
    return order;
  }

  // ✅ Status transition rules
  private boolean isValidTransition(OrderStatus current, OrderStatus next) {
    return (current == OrderStatus.NEW && next == OrderStatus.PROCESSING)
        || (current == OrderStatus.PROCESSING && next == OrderStatus.COMPLETED);
  }
}