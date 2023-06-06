package darek9k.invoice;

import darek9k.util.LogUtil;
import jakarta.persistence.EntityNotFoundException;
import jakarta.persistence.criteria.Predicate;
import org.jetbrains.annotations.NotNull;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
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

    public Page<FindInvoiceResponse> find(Pageable pageable) {
        Specification<Invoice> specification = prepareSpecUsingPredicates();
        return invoiceRepository.findAll(specification, pageable)
                .map(FindInvoiceResponse::from);
    }

    @NotNull
    private static Specification<Invoice> prepareSpecUsingPredicates() {
        return (root, query, criteriaBuilder) -> {
            Predicate paymentDatePred = criteriaBuilder.between(root.get("paymentDate"),
                    LocalDate.now().minusYears(1),
                    LocalDate.now().plusYears(1));
            Predicate sellerLikePred = criteriaBuilder.like(criteriaBuilder.lower(root.get("seller")),
                    "%" + "Sel".toLowerCase() + "%");
            Predicate statusInPred = root.get("status").in(InvoiceStatus.ACTIVE, InvoiceStatus.DELETED);

            return criteriaBuilder.and(paymentDatePred, sellerLikePred, statusInPred);
        };
    }

    public Page<FindInvoiceResponse> findWithSpecifications(Pageable pageable) {
        Specification<Invoice> invoiceSpecification = prepareSpec();

        return invoiceRepository.findAll(invoiceSpecification, pageable)
                .map(FindInvoiceResponse::from);
    }

    @NotNull
    private static Specification<Invoice> prepareSpec() {
        Specification<Invoice> paymentDataSpec = (root, query, criteriaBuilder) ->
                criteriaBuilder.between(root.get("paymentDate"),
                        LocalDate.now().minusYears(1),
                        LocalDate.now().plusYears(1));

        Specification<Invoice> sellerSpec = (root, query, criteriaBuilder) ->
                criteriaBuilder.like(root.get("seller"), "%" + "Sel" + "%");

        Specification<Invoice> statusInSpec = (root, query, criteriaBuilder) ->
                root.get("status").in(InvoiceStatus.ACTIVE, InvoiceStatus.DELETED);

        Specification<Invoice> invoiceSpecification =
                paymentDataSpec.and(sellerSpec).and(statusInSpec);
        return invoiceSpecification;
    }


    public Page<FindInvoiceResponse> find(String seller, String buyer, int page, int size) {
        return invoiceRepository.findByStatusAndSellerAndBuyer(
                Set.of(InvoiceStatus.ACTIVE, InvoiceStatus.DRAFT),
                seller,
                buyer,
                PageRequest.of(page, size, Sort.by(Sort.Order.asc("paymentDate")))
        ).map(FindInvoiceResponse::from);
    }

    public Page<FindInvoiceResponse> find2(String seller, String buyer, int page, int size){
        return invoiceRepository.findByBuyerContainingAndSellerContainingAndStatusInOrderByPaymentDate(
                buyer,
                seller,
                Set.of(InvoiceStatus.ACTIVE, InvoiceStatus.DRAFT),
                PageRequest.of(page, size)
        ).map(FindInvoiceResponse::from);
    }
    public void find2() {
        LogUtil.log(() -> invoiceRepository.findByPaymentDateBetweenAndSellerStartingWithIgnoreCaseAndStatusIn(
                        LocalDate.of(2023, 6, 19),
                        LocalDate.of(2023, 6, 25),
                        "se",
                        Set.of(InvoiceStatus.ACTIVE, InvoiceStatus.DRAFT)
                ),
                "findByPaymentDateBetweenAndSellerStartingWithAndStatusIn"
        );

        LogUtil.log(() -> invoiceRepository.findBy(
                        LocalDate.of(2023, 6, 19),
                        LocalDate.of(2023, 6, 25),
                        "se",
                        Set.of(InvoiceStatus.ACTIVE, InvoiceStatus.DRAFT)
                ),
                "findBy"
        );

        LogUtil.log(() -> invoiceRepository.findByPaymentDateLessThanEqual(
                        LocalDate.of(2023, 6, 25),
                        Sort.by(Sort.Order.desc("paymentDate"),
                                Sort.Order.asc("id")
                        )
                ),
                "findByPaymentDateLessThanEqual"
        );

        LogUtil.log(() -> invoiceRepository.findByAndSort(
                        LocalDate.of(2023, 6, 25),
                        Sort.by(Sort.Order.desc("paymentDate"),
                                Sort.Order.asc("id")
                        )
                ),
                "findByAndSort"
        );

        LogUtil.log(() -> invoiceRepository.findByPaymentDateLessThanEqualOrderByPaymentDateDesc(
                        LocalDate.of(2023, 6, 25)
                ),
                "findByPaymentDateLessThanEqualOrderByPaymentDateDesc"
        );

        LogUtil.log(() -> invoiceRepository.findAndOrderByPaymentDateDesc(
                        LocalDate.of(2023, 6, 25)
                ),
                "findAndOrderByPaymentDateDesc"
        );

        LogUtil.logPage(()->invoiceRepository.findByAndSort(LocalDate.of(2023,6,28), PageRequest.of(0,3, Sort.by(Sort.Order.desc("seller")))), "findAndSortPageable");
        LogUtil.logPage(()->invoiceRepository.findByAndSort(LocalDate.of(2023,6,28), PageRequest.of(1,3, Sort.by(Sort.Order.desc("seller")))), "findAndSortPageable");
        LogUtil.logPage(()->invoiceRepository.findByAndSort(LocalDate.of(2023,6,28), PageRequest.of(2,3, Sort.by(Sort.Order.desc("seller")))), "findAndSortPageable");
        LogUtil.logPage(()->invoiceRepository.findByAndSort(LocalDate.of(2023,6,28), PageRequest.of(3,3, Sort.by(Sort.Order.desc("seller")))), "findAndSortPageable");
    }
}
