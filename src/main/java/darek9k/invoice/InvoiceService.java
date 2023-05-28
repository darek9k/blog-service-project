package darek9k.invoice;

import org.springframework.stereotype.Service;

@Service
public class InvoiceService {

    private final InvoiceRepository invoiceRepository;

    public InvoiceService(InvoiceRepository invoiceRepository) {
        this.invoiceRepository = invoiceRepository;
    }

    public void create(CreateInvoiceRequest createInvoiceRequest){
        Invoice invoice = new Invoice(
                createInvoiceRequest.paymentDate(),
                createInvoiceRequest.buyer(),
                createInvoiceRequest.seller()
        );
        invoiceRepository.save(invoice);
    }
}
