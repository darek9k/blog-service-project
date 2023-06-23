package darek9k.invoice.detail;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import java.math.BigDecimal;

public class UpdateInvoiceDetailRequest {

    @NotNull
    private final Integer version;
    @NotBlank
    @Size(max = 100)
    private final String productName;
    @NotNull
    private final BigDecimal price;

    public UpdateInvoiceDetailRequest(String productName, BigDecimal price, Long invoiceId, Integer version) {
        this.productName = productName;
        this.price = price;
        this.version = version;
    }

    public String getProductName() {
        return productName;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public Integer getVersion() {
        return version;
    }
}