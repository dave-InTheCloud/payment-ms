package lu.dave.finance.payment.entity;

import jakarta.persistence.*;
import lombok.Data;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
@Entity
@Table(name = "CUSTOMER")
public class CustomerEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "customerSeq")
    private Long id;

    private String name;

    private String email;

    private LocalDate dateOfBirth;

    @CreationTimestamp
    @Column(name = "CREATED_ON")
    private LocalDateTime createdOn;

    @UpdateTimestamp
    @Column(name = "LAST_UPDATED_ON")
    private LocalDateTime lastUpdatedOn;
}
