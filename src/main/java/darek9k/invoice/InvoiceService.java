package darek9k.invoice;

import darek9k.post.Post;
import jakarta.persistence.EntityNotFoundException;
import jakarta.persistence.OrderBy;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Set;
import java.util.function.Supplier;

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
        log(() -> invoiceRepository.findByPaymentDateBetweenAndSellerStartingWithIgnoreCaseAndStatusIn(
                        LocalDate.of(2023, 6, 19),
                        LocalDate.of(2023, 6, 25),
                        "se",
                        Set.of(InvoiceStatus.ACTIVE, InvoiceStatus.DRAFT)
                ),
                "findByPaymentDateBetweenAndSellerStartingWithAndStatusIn"
        );

        log(() -> invoiceRepository.findBy(
                        LocalDate.of(2023, 6, 19),
                        LocalDate.of(2023, 6, 25),
                        "se",
                        Set.of(InvoiceStatus.ACTIVE, InvoiceStatus.DRAFT)
                ),
                "findBy"
        );

        log(() -> invoiceRepository.findByPaymentDateLessThanEqual(
                        LocalDate.of(2023, 6, 25),
                        Sort.by(Sort.Order.desc("paymentDate"),
                                Sort.Order.asc("id")
                        )
                ),
                "findByPaymentDateLessThanEqual"
        );

        log(() -> invoiceRepository.findByAndSort(
                        LocalDate.of(2023, 6, 25),
                        Sort.by(Sort.Order.desc("paymentDate"),
                                Sort.Order.asc("id")
                        )
                ),
                "findByAndSort"
        );

        log(() -> invoiceRepository.findByPaymentDateLessThanEqualOrderByPaymentDateDesc(
                        LocalDate.of(2023, 6, 25)
                ),
                "findByPaymentDateLessThanEqualOrderByPaymentDateDesc"
        );

        log(() -> invoiceRepository.findAndOrderByPaymentDateDesc(
                        LocalDate.of(2023, 6, 25)
                ),
                "findAndOrderByPaymentDateDesc"
        );

        logPage(()->invoiceRepository.findByAndSort(LocalDate.of(2023,6,28), PageRequest.of(0,3, Sort.by(Sort.Order.desc("seller")))), "findAndSortPageable");
        logPage(()->invoiceRepository.findByAndSort(LocalDate.of(2023,6,28), PageRequest.of(1,3, Sort.by(Sort.Order.desc("seller")))), "findAndSortPageable");
        logPage(()->invoiceRepository.findByAndSort(LocalDate.of(2023,6,28), PageRequest.of(2,3, Sort.by(Sort.Order.desc("seller")))), "findAndSortPageable");
        logPage(()->invoiceRepository.findByAndSort(LocalDate.of(2023,6,28), PageRequest.of(3,3, Sort.by(Sort.Order.desc("seller")))), "findAndSortPageable");

    }

    private void log(Supplier<List<Invoice>> listSupplier, String methodName) {
        System.out.println("--------------------" + methodName + "----------------------");
        listSupplier.get().forEach(System.out::println);
    }

    private void logPage(Supplier<Page<Invoice>> pageSupplier, String methodName) {
        System.out.println("--------------------" + methodName + "----------------------");
        Page<Invoice> invoice = pageSupplier.get();

        System.out.println("getContent(): " + invoice.getContent());
        System.out.println("getTotalPages(): " + invoice.getTotalPages());
        System.out.println("getTotalElements(): " + invoice.getTotalElements());
        System.out.println("getNumber(): " + invoice.getNumber());
        System.out.println("getNumberOfElements(): " + invoice.getNumberOfElements());
        System.out.println("getSize(): " + invoice.getSize());
    }
}
