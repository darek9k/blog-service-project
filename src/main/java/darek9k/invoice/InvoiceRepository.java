package darek9k.invoice;

import org.springframework.data.repository.CrudRepository;

import java.time.LocalDate;
import java.util.List;
import java.util.Set;

public interface InvoiceRepository extends CrudRepository<Invoice, Long> {

    List<Invoice> findByPaymentDateBetweenAndSellerStartingWithIgnoreCaseAndStatusIn(
            LocalDate startDate, LocalDate endDate,
            String seller,
            Set<InvoiceStatus> postStatuses
    );
}
