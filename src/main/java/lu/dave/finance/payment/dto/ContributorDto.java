package lu.dave.finance.payment.dto;

import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import lombok.Data;
import lu.dave.finance.payment.entity.ContributorId;
import lu.dave.finance.payment.entity.enumaration.ContributorType;


@Data
public class ContributorDto {

    private ContributorId contributorId;

    @Enumerated(EnumType.STRING)
    private ContributorType type;

    private Double amount;

}
