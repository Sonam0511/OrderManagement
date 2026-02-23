package example.OrderManagement;


import example.OrderManagement.dto.CreateOrderRequest;
import example.OrderManagement.exception.BadRequestException;
import example.OrderManagement.exception.OrderNotFoundException;
import example.OrderManagement.model.Order;
import example.OrderManagement.model.OrderStatus;
import example.OrderManagement.service.OrderService;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class OrderServiceTest {

  private OrderService orderService;

  @BeforeEach
  void setup() {
    orderService = new OrderService();
  }

  @Test
  void shouldCreateOrderSuccessfully() {
    CreateOrderRequest request = new CreateOrderRequest();
    request.setCustomerName("Sonam");
    request.setAmount(1000.0);

    Order order = orderService.createOrder(request);

    assertNotNull(order.getOrderId());
    assertEquals("Sonam", order.getCustomerName());
    assertEquals(OrderStatus.NEW, order.getStatus());
  }

  @Test
  void shouldThrowOrderNotFoundException() {
    assertThrows(OrderNotFoundException.class,
        () -> orderService.getOrder("invalid-id"));
  }

  @Test
  void shouldUpdateStatusValidTransition() {
    CreateOrderRequest request = new CreateOrderRequest();
    request.setCustomerName("Sonam");
    request.setAmount(1000.0);

    Order order = orderService.createOrder(request);

    Order updated = orderService.updateStatus(order.getOrderId(), OrderStatus.PROCESSING);

    assertEquals(OrderStatus.PROCESSING, updated.getStatus());
  }

  @Test
  void shouldThrowInvalidStatusTransition() {
    CreateOrderRequest request = new CreateOrderRequest();
    request.setCustomerName("Sonam");
    request.setAmount(1000.0);

    Order order = orderService.createOrder(request);

    assertThrows(BadRequestException.class,
        () -> orderService.updateStatus(order.getOrderId(), OrderStatus.COMPLETED));
  }
}
