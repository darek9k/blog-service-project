package darek9k.invoice;

import jakarta.persistence.*;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
public class Invoice {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private LocalDateTime createdDate;

    private LocalDate paymentDate;

    private String buyer;

    private String seller;
    @Enumerated(EnumType.STRING)
    private InvoiceStatus status;

    public Invoice() {
    }

    public Invoice (LocalDate paymentDate, String buyer, String seller) {
        this.createdDate = LocalDateTime.now();
        this.paymentDate = paymentDate;
        this.buyer = buyer;
        this.seller = seller;
        this.status = InvoiceStatus.ACTIVE;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setCreatedDate(LocalDateTime createdDate) {
        this.createdDate = createdDate;
    }

    public void setPaymentDate(LocalDate paymentDate) {
        this.paymentDate = paymentDate;
    }

    public void setBuyer(String buyer) {
        this.buyer = buyer;
    }

    public void setSeller(String seller) {
        this.seller = seller;
    }

    public void setStatus(InvoiceStatus status) {
        this.status = status;
    }

    @Override
    public String toString() {
        return "Invoice{" +
                "id=" + id +
                ", createdDate=" + createdDate +
                ", paymentDate=" + paymentDate +
                ", buyer='" + buyer + '\'' +
                ", seller='" + seller + '\'' +
                ", status=" + status +
                '}';
    }

    public static class CreateInvoiceRequest {
    }
}
