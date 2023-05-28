package darek9k.invoice;

import java.time.LocalDate;

public record CreateInvoiceRequest(LocalDate paymentDate, String buyer, String seller) {

}
