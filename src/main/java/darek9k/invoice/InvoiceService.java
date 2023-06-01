package darek9k.invoice;

import darek9k.post.Post;
import darek9k.post.PostStatus;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Set;

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
    public ReadInvoiceResponse findById(Long id){
        return invoiceRepository.findById(id).
                map(ReadInvoiceResponse::from).
                orElseThrow(EntityNotFoundException::new);
    }

    public void update(Long id, UpdateInvoiceRequest updateInvoiceRequest) {
        Invoice invoice = invoiceRepository.findById(id)
                .orElseThrow(EntityNotFoundException::new);

        Invoice newInvoice = new Invoice(invoice);

        newInvoice.setPaymentDate(updateInvoiceRequest.paymentDate());
        newInvoice.setBuyer(updateInvoiceRequest.buyer());
        newInvoice.setSeller(updateInvoiceRequest.seller());
        newInvoice.setVersion(updateInvoiceRequest.version());

        invoiceRepository.save(newInvoice);
    }

    public void delete(Long id) {
        invoiceRepository.deleteById(id);
    }

    public void archive(Long id) {
        Invoice invoice = invoiceRepository.findById(id)
                .orElseThrow(EntityNotFoundException::new);

        Invoice newInvoice = new Invoice(invoice);
        newInvoice.setStatus(InvoiceStatus.DELETED);
        invoiceRepository.save(newInvoice);
    }

    public void find() {
        log(invoiceRepository.findByPaymentDateBetweenAndSellerStartingWithAndStatusIn(
                LocalDate.of(2023, 6,19),
                LocalDate.of(2023,6,25),
                "Da",
                Set.of(InvoiceStatus.ACTIVE, InvoiceStatus.DRAFT)),
                "findByPaymentDateBetweenAndSellerStartingWithAndStatusIn"
        );
    }

    private void log(List<Invoice> invoices, String methodName){
        System.out.println("--------------------"+methodName+"----------------------");
        invoices.forEach(System.out::println);
    }
}
