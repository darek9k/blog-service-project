package darek9k.invoice.detail;

import darek9k.invoice.ReadInvoiceResponse;
import lombok.Value;

import java.math.BigDecimal;
import java.time.LocalDateTime;
@Value
public class ReadInvoiceDetailResponse {

    Long id;

    Integer version;

    LocalDateTime createdDateTime;

    String productName;

    BigDecimal price;

    ReadInvoiceResponse invoice;

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
