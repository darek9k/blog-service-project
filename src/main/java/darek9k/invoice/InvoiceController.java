package darek9k.invoice;

import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/invoices")
public class InvoiceController {

    private final InvoiceService invoiceService;

    public InvoiceController(InvoiceService invoiceService) {
        this.invoiceService = invoiceService;
    }

    @GetMapping
    public void read() {

    }

    @PostMapping
    public void create(@Valid @RequestBody CreateInvoiceRequest createInvoiceRequest) {
        invoiceService.create(createInvoiceRequest);
    }

}
