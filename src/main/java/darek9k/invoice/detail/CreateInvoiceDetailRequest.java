package darek9k.invoice.detail;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import java.math.BigDecimal;

public class CreateInvoiceDetailRequest {
    @NotBlank
    @Size(max = 100)
    private final String productName;
    @NotNull
    private final BigDecimal price;

    @NotNull
    private final Long invoiceId;

    public CreateInvoiceDetailRequest(String productName, BigDecimal price, Long invoiceId) {
        this.productName = productName;
        this.price = price;
        this.invoiceId = invoiceId;
    }

    public String getProductName() {
        return productName;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public Long getInvoiceId() {
        return invoiceId;
    }
}