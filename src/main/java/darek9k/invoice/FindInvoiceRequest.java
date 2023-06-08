package darek9k.invoice;

import java.time.LocalDate;
import java.util.Set;

public record FindInvoiceRequest(String seller,
                                 Set<InvoiceStatus> invoiceStatuses,
                                 LocalDate paymentDateMax,
                                 LocalDate paymentDateMin) {
}
