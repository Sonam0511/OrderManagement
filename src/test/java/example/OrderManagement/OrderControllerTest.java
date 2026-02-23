package example.OrderManagement;

import com.fasterxml.jackson.databind.ObjectMapper;

import example.OrderManagement.controller.OrderController;
import example.OrderManagement.dto.CreateOrderRequest;
import example.OrderManagement.model.Order;
import example.OrderManagement.model.OrderStatus;
import example.OrderManagement.service.OrderService;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(OrderController.class)
class OrderControllerTest {

  @Autowired
  private MockMvc mockMvc;

  @MockBean
  private OrderService orderService;

  @Autowired
  private ObjectMapper objectMapper;

  @Test
  void shouldCreateOrder() throws Exception {

    CreateOrderRequest request = new CreateOrderRequest();
    request.setCustomerName("Sonam");
    request.setAmount(1000.0);

    Order order = new Order();
    order.setOrderId("123");
    order.setCustomerName("Sonam");
    order.setAmount(1000.0);
    order.setStatus(OrderStatus.NEW);

    when(orderService.createOrder(request)).thenReturn(order);

    mockMvc.perform(post("/orders")
               .contentType(MediaType.APPLICATION_JSON)
               .content(objectMapper.writeValueAsString(request)))
           .andExpect(status().isOk())
           .andExpect(jsonPath("$.orderId").value("123"))
           .andExpect(jsonPath("$.status").value("NEW"));
  }

  @Test
  void shouldGetOrderById() throws Exception {

    Order order = new Order();
    order.setOrderId("123");
    order.setCustomerName("Sonam");
    order.setAmount(1000.0);
    order.setStatus(OrderStatus.NEW);

    when(orderService.getOrder("123")).thenReturn(order);

    mockMvc.perform(get("/orders/123"))
           .andExpect(status().isOk())
           .andExpect(jsonPath("$.customerName").value("Sonam"));
  }
}
