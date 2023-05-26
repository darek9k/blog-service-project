package darek9k.invoice;

import jakarta.persistence.criteria.CriteriaBuilder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.Set;

@RestController
@RequestMapping("/api/invoice")
public class InvoiceController {

    private final InvoiceRepository invoiceRepository;

    public InvoiceController(InvoiceRepository invoiceRepository) {
        this.invoiceRepository = invoiceRepository;
    }
    @GetMapping
    public void read(){
        //R
        Optional<Invoice> invoiceOptional = invoiceRepository.findById(1L);
        Invoice invoice = invoiceOptional.get();
        System.out.println(invoice);

        //D
        invoiceRepository.delete(invoice);
        invoiceRepository.deleteById(2L);
        invoiceRepository.deleteAllById(Set.of(12L,15L));

        //List
        invoiceRepository.findAll().forEach(System.out::println);
        invoiceRepository.findAllById(List.of(18L,19L)).forEach(System.out::println);

        //U
        Optional<Invoice> optionalInvoice = invoiceRepository.findById(10L);
        Invoice invoice10 = optionalInvoice.get();
        invoice10.setBuyer("Arek");
        invoiceRepository.save(invoice10);
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
