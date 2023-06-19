package darek9k.invoice.detail;

import darek9k.comment.ReadCommentResponse;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/invoice-details")
public class InvoiceDetailController {
    private final InvoiceDetailService invoiceDetailService;

    public InvoiceDetailController(InvoiceDetailService invoiceDetailService) {
        this.invoiceDetailService = invoiceDetailService;
    }

    @PostMapping
    public void create(@Valid @RequestBody CreateInvoiceDetailRequest createInvoiceDetailRequest) {
        invoiceDetailService.create(createInvoiceDetailRequest);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ReadInvoiceDetailResponse> read(@PathVariable("id") Long id) {
        ReadInvoiceDetailResponse invoiceDetailResponse = invoiceDetailService.findById(id);
        return ResponseEntity.ok(invoiceDetailResponse);
    }
}
