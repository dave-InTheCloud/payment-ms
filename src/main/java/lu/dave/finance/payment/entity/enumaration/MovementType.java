package lu.dave.finance.payment.entity.enumaration;

import lombok.ToString;


@ToString
//replace by ref db table if larger model
public enum MovementType {
    CREDIT, DEBIT;
}