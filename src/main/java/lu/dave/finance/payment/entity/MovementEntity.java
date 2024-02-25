package lu.dave.finance.payment.entity;

import jakarta.persistence.*;
import lombok.Data;
import lu.dave.finance.payment.entity.enumaration.AmountType;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDateTime;

@Data
@Entity
@Table(name = "MOVEMENT")
public class MovementEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "movementSeq")
    private Long id;

    private Long userId;

    private Long fromAccountId;

    private  Long toAccountId;

    private Double amount;

    @Enumerated(EnumType.STRING)
    private AmountType amountType;

    @CreationTimestamp
    @Column(name = "CREATED_ON")
    private LocalDateTime createdOn;

    @UpdateTimestamp
    @Column(name = "LAST_UPDATED_ON")
    private LocalDateTime lastUpdatedOn;
}
