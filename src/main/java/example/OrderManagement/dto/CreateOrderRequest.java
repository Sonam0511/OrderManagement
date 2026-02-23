package example.OrderManagement.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.Data;

@Data
public class CreateOrderRequest {
  @NotBlank(message = "customerName is required")
  private String customerName;

  @NotNull(message = "amount is required")
  @Positive(message = "amount must be greater than 0")
  private Double amount;


}
