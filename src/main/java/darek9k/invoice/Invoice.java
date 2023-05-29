package darek9k.invoice;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
public class Invoice {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @NotNull
    private LocalDateTime createdDate;
    @NotNull
    private LocalDate paymentDate;
    @NotBlank
    @NotNull
    @Size(max = 100)
    private String buyer;
    @NotBlank
    @NotNull
    @Size(max = 100)
    private String seller;
    @NotNull
    @Enumerated(EnumType.STRING)
    private InvoiceStatus status;

    public Invoice() {
    }

    public Invoice(LocalDate paymentDate, String buyer, String seller) {
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
