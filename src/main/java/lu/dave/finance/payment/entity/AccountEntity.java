package lu.dave.finance.payment.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import lombok.Data;
import lu.dave.finance.payment.entity.enumaration.AccountType;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Data
@Entity
@Table(name = "ACCOUNT")
public class AccountEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "CUSTOMER_SEQ_GEN")
    @SequenceGenerator(name = "CUSTOMER_SEQ_GEN", sequenceName = "CUSTOMER_SEQ", allocationSize = 1)
    private Long id;

    @Column(name = "NAME")
    private String name;

    @Column(name = "SERIAL_NUMBER")
    private String serialNumber;

    @Column(name = "CURRENCY_CODE")
    private String currencyCode;

    @Column(name = "BALANCE")
    private Double balance;

    @Column(name = "ACCOUNT_TYPE")
    @Enumerated(EnumType.STRING)
    private AccountType type;

    @OneToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "OWNER_ID")
    private CustomerEntity customer;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "PARENT_ID")
    private AccountEntity parent;

    @OneToMany(mappedBy = "parent", cascade = CascadeType.ALL)
    private List<AccountEntity> children = new ArrayList<>();


//    @OneToMany(mappedBy = "fromAccount", cascade = CascadeType.ALL, orphanRemoval = true)
//    private List<MovementEntity> fromMovements;
//
//    @OneToMany(mappedBy = "toAccount", cascade = CascadeType.ALL, orphanRemoval = true)
//    private List<MovementEntity> toMovements;


    //@OneToMany(mappedBy = "account")
    //private List<ContributorEntity> contributors;

    @CreationTimestamp
    @Column(name = "CREATED_ON")
    private LocalDateTime createdOn;

    @UpdateTimestamp
    @Column(name = "LAST_UPDATED_ON")
    private LocalDateTime lastUpdatedOn;
}
