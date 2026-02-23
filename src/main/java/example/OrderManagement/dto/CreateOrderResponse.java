package example.OrderManagement.dto;

import lombok.Data;
import example.OrderManagement.model.OrderStatus;
@Data
public class CreateOrderResponse {
  private String orderId;
  private String customerName;
  private Double amount;
  private OrderStatus status;

}
