package darek9k.invoice;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.time.LocalDate;
import java.util.List;
import java.util.Set;

public interface InvoiceRepository extends CrudRepository<Invoice, Long> {

    @Query("select i from Invoice i where i.status in :invoiceStatuses and i.seller ilike %:seller% and i.buyer ilike %:buyer%")
    Page<Invoice> findByStatusAndSellerAndBuyer(
            Set<InvoiceStatus> invoiceStatuses,
            String seller,
            String buyer,
            Pageable pageable
    );

    Page<Invoice> findByBuyerContainingAndSellerContainingAndStatusInOrderByPaymentDate(
            String buyer, String seller, Set<InvoiceStatus> invoiceStatuses, Pageable pageable
    );

    List<Invoice> findByPaymentDateBetweenAndSellerStartingWithIgnoreCaseAndStatusIn(
            LocalDate startDate, LocalDate endDate,
            String seller,
            Set<InvoiceStatus> invoiceStatuses
    );

    @Query("select i from Invoice i where i.paymentDate between :startDate and :endDate " +
            "and i.seller ilike :seller% " +
            "and i.status in :invoiceStatuses")
    List<Invoice> findBy(LocalDate startDate, LocalDate endDate,
                         String seller,
                         Set<InvoiceStatus> invoiceStatuses);

    List<Invoice> findByPaymentDateLessThanEqualOrderByPaymentDateDesc(
            LocalDate paymentDate
    );

    @Query("select i from Invoice i where i.paymentDate <= :paymentDate order by i.paymentDate desc")
    List<Invoice> findAndOrderByPaymentDateDesc(LocalDate paymentDate);

    List<Invoice> findByPaymentDateLessThanEqual(
            LocalDate paymentDate, Sort sort
    );

    @Query("select i from Invoice i where i.paymentDate <= :paymentDate")
    List<Invoice> findByAndSort(
            LocalDate paymentDate, Sort sort
    );

    @Query("select i from Invoice i where i.paymentDate <= :paymentDate")
    Page<Invoice> findByAndSort(
            LocalDate paymentDate, Pageable pageable
    );
}
