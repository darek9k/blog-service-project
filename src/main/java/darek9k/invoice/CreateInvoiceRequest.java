package darek9k.invoice;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import java.time.LocalDate;

public record CreateInvoiceRequest(@NotNull LocalDate paymentDate,
                                   @NotNull @NotBlank @Size(max = 100) String buyer,
                                   @NotNull @NotBlank @Size(max = 100) String seller) {

}
