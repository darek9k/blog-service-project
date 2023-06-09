package darek9k.invoice;

import java.time.LocalDate;
import java.time.LocalDateTime;

public record ReadInvoiceResponse(Long id, Integer version, LocalDateTime createdDateTime, LocalDate paymentDate, String buyer,
                                  String seller, InvoiceStatus status) {
    public static ReadInvoiceResponse from(Invoice invoice) {
        return new ReadInvoiceResponse(
                invoice.getId(),
                invoice.getVersion(),
                invoice.getCreatedDateTime(),
                invoice.getPaymentDate(),
                invoice.getBuyer(),
                invoice.getSeller(),
                invoice.getStatus()
        );
    }
}
