package darek9k.invoice.detail;

import darek9k.invoice.ReadInvoiceResponse;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public class ReadInvoiceDetailResponse {

    private final Long id;

    private final Integer version;

    private final LocalDateTime createdDateTime;

    private final String productName;


    private final BigDecimal price;

    private final ReadInvoiceResponse invoice;

    public ReadInvoiceDetailResponse(Long id, Integer version, LocalDateTime createdDateTime, String productName, BigDecimal price, ReadInvoiceResponse invoice) {
        this.id = id;
        this.version = version;
        this.createdDateTime = createdDateTime;
        this.productName = productName;
        this.price = price;
        this.invoice = invoice;
    }

    public Long getId() {
        return id;
    }

    public Integer getVersion() {
        return version;
    }

    public LocalDateTime getCreatedDateTime() {
        return createdDateTime;
    }

    public String getProductName() {
        return productName;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public ReadInvoiceResponse getInvoice() {
        return invoice;
    }

    public static ReadInvoiceDetailResponse from(InvoiceDetail invoiceDetail){
        return new ReadInvoiceDetailResponse(
                invoiceDetail.getId(),
                invoiceDetail.getVersion(),
                invoiceDetail.getCreatedDateTime(),
                invoiceDetail.getProductName(),
                invoiceDetail.getPrice(),
                ReadInvoiceResponse.from(invoiceDetail.getInvoice())
        );
    }
}
