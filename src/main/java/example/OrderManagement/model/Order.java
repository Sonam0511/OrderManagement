package example.OrderManagement.model;

import lombok.Data;

@Data
public class Order {

  private String orderId;
  private String customerName;
  private Double amount;
  private OrderStatus status;


}
