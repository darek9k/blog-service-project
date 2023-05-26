package darek9k.invoice;

import jakarta.persistence.criteria.CriteriaBuilder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Optional;

@RestController
@RequestMapping("/api/invoice")
public class InvoiceController {

    private final InvoiceRepository invoiceRepository;

    public InvoiceController(InvoiceRepository invoiceRepository) {
        this.invoiceRepository = invoiceRepository;
    }
    @GetMapping
    public void read(){
        Optional<Invoice> invoiceOptional = invoiceRepository.findById(1L);
        Invoice invoice = invoiceOptional.get();
        System.out.println(invoice);
    }
    @PostMapping
    public void create(){
        for (int i = 0; i < 20; i++) {
            invoiceRepository.save(new Invoice(
                    null,
                    LocalDateTime.now(),
                    LocalDate.now(),
                    "Darek"+2*i,
                    "Jarek"+i+10,
                    (i<5)?InvoiceStatus.ACTIVE:InvoiceStatus.DRAFT
            ));
        }

    }

}
