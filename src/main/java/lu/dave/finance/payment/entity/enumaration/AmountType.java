package lu.dave.finance.payment.entity.enumaration;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum AmountType {
    CREDIT("C"), DEBIT("");

    private String code;
}