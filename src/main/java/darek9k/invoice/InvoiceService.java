package darek9k.invoice;

import darek9k.invoice.detail.InvoiceDetail;
import darek9k.util.LogUtil;
import jakarta.persistence.EntityNotFoundException;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.Path;
import jakarta.persistence.criteria.Predicate;
import org.jetbrains.annotations.NotNull;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service
public class InvoiceService {

    private final InvoiceRepository invoiceRepository;

    public InvoiceService(InvoiceRepository invoiceRepository) {
        this.invoiceRepository = invoiceRepository;
    }
    @Transactional
    public void create(CreateInvoiceRequest createInvoiceRequest){
        Invoice invoice = new Invoice(
                createInvoiceRequest.paymentDate(),
                createInvoiceRequest.buyer(),
                createInvoiceRequest.seller()
        );

        Set<InvoiceDetail> invoiceDetails = new HashSet<>();
        invoiceDetails.add(InvoiceDetail.builder().productName("product1").price(BigDecimal.ONE).invoice(invoice).build());
        invoiceDetails.add(InvoiceDetail.builder().productName("product2").price(new BigDecimal(23.67)).invoice(invoice).build());
        invoice.setInvoiceDetails(invoiceDetails);

        invoiceRepository.save(invoice);
    }
    public ReadInvoiceResponse findById(Long id){
        return invoiceRepository.findById(id).
                map(ReadInvoiceResponse::from).
                orElseThrow(EntityNotFoundException::new);
    }

    public Invoice findInvoiceById(Long id){
        return invoiceRepository.findById(id)
                        .orElseThrow(EntityNotFoundException::new);
    }
    @Transactional
    public void update(Long id, UpdateInvoiceRequest updateInvoiceRequest) {
        Invoice invoice = invoiceRepository.findById(id)
                .orElseThrow(EntityNotFoundException::new);

        Invoice newInvoice = new Invoice(invoice);

        newInvoice.setPaymentDate(updateInvoiceRequest.paymentDate());
        newInvoice.setBuyer(updateInvoiceRequest.buyer());
        newInvoice.setSeller(updateInvoiceRequest.seller());
        newInvoice.setVersion(updateInvoiceRequest.version());

        Set<InvoiceDetail> invoiceDetails = newInvoice.getInvoiceDetails();
        InvoiceDetail invoiceDetail = invoiceDetails.iterator().next();
        invoiceDetail.setPrice(new BigDecimal("-1"));
        /// ... update code

        invoiceDetails.add(InvoiceDetail.builder().productName("productNew").price(BigDecimal.TEN).invoice(newInvoice).build());

        invoiceRepository.save(newInvoice);
    }
    @Transactional
    public void delete(Long id) {
        invoiceRepository.deleteById(id);
    }
    @Transactional
    public void archive(Long id) {
        Invoice invoice = invoiceRepository.findById(id)
                .orElseThrow(EntityNotFoundException::new);

        Invoice newInvoice = new Invoice(invoice);
        newInvoice.setStatus(InvoiceStatus.DELETED);
        invoiceRepository.save(newInvoice);
    }

    public Page<FindInvoiceResponse> find(FindInvoiceRequest findInvoiceRequest, Pageable pageable) {
        Specification<Invoice> specification = prepareSpecUsingPredicates(findInvoiceRequest);
        return invoiceRepository.findAll(specification, pageable)
                .map(FindInvoiceResponse::from);
    }


    private static Specification<Invoice> prepareSpecUsingPredicates(FindInvoiceRequest findInvoiceRequest) {
        return (root, query, criteriaBuilder) -> {
            List<Predicate> predicates = new ArrayList<>();
            if (findInvoiceRequest.paymentDateMin() != null && findInvoiceRequest.paymentDateMax() != null) {
                predicates.add(criteriaBuilder.between(root.get("paymentDate"),
                        findInvoiceRequest.paymentDateMin(),
                        findInvoiceRequest.paymentDateMax())
                );
            }
            if (findInvoiceRequest.seller() != null) {
                predicates.add(likeIgnoreCase(criteriaBuilder, root.get("seller"), findInvoiceRequest.seller()));
            }
            if (findInvoiceRequest.invoiceStatuses() != null && !findInvoiceRequest.invoiceStatuses().isEmpty()) {
                predicates.add(root.get("status").in(findInvoiceRequest.invoiceStatuses()));
            }
            return criteriaBuilder.and(predicates.toArray(Predicate[]::new));
        };
    }

    private static Predicate likeIgnoreCase(CriteriaBuilder criteriaBuilder, Path<String> fieldPath, String text) {
        return criteriaBuilder.like(criteriaBuilder.lower(fieldPath),
                "%" + text.toLowerCase() + "%");
    }


    public Page<FindInvoiceResponse> findWithSpecifications(FindInvoiceRequest findInvoiceRequest, Pageable pageable) {
        Specification<Invoice> invoiceSpecification = prepareSpec(findInvoiceRequest);

        return invoiceRepository.findAll(invoiceSpecification, pageable)
                .map(FindInvoiceResponse::from);
    }

    @NotNull
    private static Specification<Invoice> prepareSpec(FindInvoiceRequest findInvoiceRequest) {
        Specification<Invoice> specification = Specification.where(null);
        if (findInvoiceRequest.paymentDateMin() != null && findInvoiceRequest.paymentDateMax() != null) {
            specification = specification.and((root, query, criteriaBuilder) ->
                    criteriaBuilder.between(root.get("paymentDate"),
                            findInvoiceRequest.paymentDateMin(),
                            findInvoiceRequest.paymentDateMax()));

        }
        if (findInvoiceRequest.seller() != null) {
            specification = specification.and((root, query, criteriaBuilder) ->
                    likeIgnoreCase(criteriaBuilder, root.get("seller"), findInvoiceRequest.seller()));
        }
        if (findInvoiceRequest.invoiceStatuses() != null && !findInvoiceRequest.invoiceStatuses().isEmpty()) {
            specification = specification.and((root, query, criteriaBuilder) ->
                    root.get("status").in(findInvoiceRequest.invoiceStatuses()));
        }
        return specification;
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
