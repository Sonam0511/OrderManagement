package example.OrderManagement.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import example.OrderManagement.dto.CreateOrderRequest;
import example.OrderManagement.model.Order;
import example.OrderManagement.model.OrderStatus;
import example.OrderManagement.service.OrderService;
import jakarta.validation.Valid;

import java.util.List;

@RestController
@RequestMapping("/orders")
public class OrderController {

  private final OrderService orderService;

  public OrderController(OrderService orderService) {
    this.orderService = orderService;
  }


  @PostMapping
  public Order createOrder(@Valid @RequestBody CreateOrderRequest request) {
    return orderService.createOrder(request);
  }


  @GetMapping("/{orderId}")
  public Order getOrder(@PathVariable String orderId) {
    return orderService.getOrder(orderId);
  }


  @PutMapping("/{orderId}/status")
  public Order updateStatus(
      @PathVariable String orderId,
      @RequestParam OrderStatus status) {
    return orderService.updateStatus(orderId, status);
  }


  @GetMapping
  public List<Order> getAllOrders() {
    return orderService.getAllOrders();
  }
}
