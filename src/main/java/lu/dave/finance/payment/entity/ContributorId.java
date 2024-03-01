package lu.dave.finance.payment.entity;

import jakarta.persistence.Embeddable;
import jakarta.persistence.Id;
import jakarta.persistence.IdClass;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
@Data
@AllArgsConstructor
@NoArgsConstructor
@Embeddable
public class ContributorId implements Serializable {
    private static final long serialVersionUID = 1688130648871926066L;
    private Long accountId;
    private Long customerId;
}
