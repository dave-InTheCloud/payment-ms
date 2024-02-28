package lu.dave.finance.payment.entity.enumaration;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;


@Getter
@AllArgsConstructor
public enum AmountType {
    CREDIT, DEBIT;
//    CREDIT("C"), DEBIT("");
//
//    private final String code;
}