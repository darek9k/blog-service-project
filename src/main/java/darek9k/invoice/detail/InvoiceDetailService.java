package darek9k.invoice.detail;

import darek9k.invoice.Invoice;
import darek9k.invoice.InvoiceService;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class InvoiceDetailService {
    private final InvoiceDetailRepository invoiceDetailRepository;

    private final InvoiceService invoiceService;

    public InvoiceDetailService(InvoiceDetailRepository invoiceDetailRepository, InvoiceService invoiceService) {
        this.invoiceDetailRepository = invoiceDetailRepository;
        this.invoiceService = invoiceService;
    }

    public void create(CreateInvoiceDetailRequest invoiceDetailRequest) {
        Invoice invoice = invoiceService.findInvoiceById(invoiceDetailRequest.getInvoiceId());

        InvoiceDetail invoiceDetail = new InvoiceDetail(
                invoiceDetailRequest.getProductName(),
                invoiceDetailRequest.getPrice(),
                invoice
        );
        invoiceDetailRepository.save(invoiceDetail);
    }

    public ReadInvoiceDetailResponse findById(Long id) {
        Optional<InvoiceDetail> maybeInvoiceDetail = invoiceDetailRepository.findById(id);
        Optional<ReadInvoiceDetailResponse> readInvoiceDetailResponse = maybeInvoiceDetail.map(ReadInvoiceDetailResponse::from);
        return readInvoiceDetailResponse.orElseThrow(EntityNotFoundException::new);
    }
}
